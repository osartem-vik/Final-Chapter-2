package models;

import island.Island;
import models.herbivores.*;
import models.predators.*;

import java.util.List;
import java.util.Random;

public abstract class Predator extends Animal {
        public Predator(int x, int y, int energy, int maxAge, int weightKg, int maxPerCell, int speed, int foodNeeded,
                        double moveProbability, int offspringCount, String unicode) {
            super(x, y, energy, maxAge, weightKg, maxPerCell, speed, foodNeeded, moveProbability, offspringCount, unicode);
        }

        public boolean canEat(Animal prey, Random rand) {
            if (prey instanceof Wolf && this instanceof Wolf) return false;
            if (prey instanceof Boa && this instanceof Boa) return false;
            if (prey instanceof Fox && this instanceof Fox) return false;
            if (prey instanceof Bear && this instanceof Bear) return false;
            if (prey instanceof Eagle && this instanceof Eagle) return false;

            double probability = getEatProbability(prey);
            return rand.nextDouble() < probability / 100.0;
        }

        protected double getEatProbability(Animal prey) {

            if (this instanceof Wolf) {
                if (prey instanceof Horse) return 10;
                if (prey instanceof Deer) return 15;
                if (prey instanceof Rabbit) return 60;
                if (prey instanceof Mouse) return 80;
                if (prey instanceof Goat) return 60;
                if (prey instanceof Sheep) return 70;
                if (prey instanceof Boar) return 15;
                if (prey instanceof Buffalo) return 10;
                if (prey instanceof Duck) return 40;
            } else if (this instanceof Boa) {
                if (prey instanceof Fox) return 15;
                if (prey instanceof Rabbit) return 20;
                if (prey instanceof Mouse) return 40;
                if (prey instanceof Duck) return 10;
            } else if (this instanceof Fox) {
                if (prey instanceof Eagle) return 10;
                if (prey instanceof Rabbit) return 70;
                if (prey instanceof Mouse) return 90;
                if (prey instanceof Duck) return 60;
                if (prey instanceof Caterpillar) return 40;
            } else if (this instanceof Bear) {
                if (prey instanceof Boa) return 80;
                if (prey instanceof Horse) return 40;
                if (prey instanceof Deer) return 80;
                if (prey instanceof Rabbit) return 80;
                if (prey instanceof Mouse) return 90;
                if (prey instanceof Goat) return 70;
                if (prey instanceof Sheep) return 70;
                if (prey instanceof Boar) return 50;
                if (prey instanceof Buffalo) return 20;
                if (prey instanceof Duck) return 10;
            } else if (this instanceof Eagle) {
                if (prey instanceof Fox) return 10;
                if (prey instanceof Rabbit) return 90;
                if (prey instanceof Mouse) return 90;
                if (prey instanceof Duck) return 80;
            }
            return 0;
        }

        @Override
        public void eat(Island island) {
            if (!isAlive) return;
            hungerLevel++;
            if (hungerLevel >= foodNeeded) {
                isAlive = false;
                return;
            }
            List<Creature> location = island.getLocation(x, y);
            Random rand = new Random();
            for (Creature creature : location) {
                if (creature instanceof Animal && creature.isAlive()) {
                    Animal prey = (Animal) creature;
                    if (canEat(prey, rand)) {
                        int energyGained = prey.energy / 2;
                        hungerLevel = Math.max(0, hungerLevel - energyGained / 10);
                        energy += energyGained;
                        prey.isAlive = false;
                        break;
                    }
                }
            }
        }

        @Override
        public Animal reproduce(Island island) {
            if (!isAlive || energy < 70 || hungerLevel > 0) return null;

            List<Creature> location = island.getLocation(x, y);
            Animal partner = null;
            for (Creature creature : location) {
                if (creature.getClass() == this.getClass() && creature != this && creature.isAlive()) {
                    partner = (Animal) creature;
                    break;
                }
            }

            if (partner != null && new Random().nextDouble() < 0.2) {
                energy -= 40;
                partner.energy -= 40;
                return createOffspring(x, y);
            }
            return null;
        }

        protected abstract Animal createOffspring(int x, int y);
    }
