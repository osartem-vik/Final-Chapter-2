package models;

import island.Island;
import models.herbivores.Caterpillar;
import models.herbivores.Duck;

import java.util.List;
import java.util.Random;

public abstract class Herbivore extends Animal {
    public Herbivore(int x, int y, int energy, int maxAge, double weightKg, int maxPerCell, int speed, double foodNeeded,
                     double moveProbability, int offspringCount, String unicode) {
        super(x, y, energy, maxAge, weightKg, maxPerCell, speed, foodNeeded, moveProbability, offspringCount, unicode);
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
            if (creature instanceof Plant && creature.isAlive()) {
                double probability = getPlantEatProbability();
                if (rand.nextDouble() < probability / 100.0) {
                    int energyGained = ((Plant) creature).getEnergy();
                    hungerLevel = Math.max(0, hungerLevel - energyGained / 10);
                    energy += energyGained;
                    creature.isAlive = false;
                    break;
                }
            }

            if (this instanceof Duck && creature instanceof Caterpillar && creature.isAlive()) {
                double probability = 90;
                if (rand.nextDouble() < probability / 100.0) {
                    int energyGained = ((Caterpillar) creature).energy / 2;
                    hungerLevel = Math.max(0, hungerLevel - energyGained / 10);
                    energy += energyGained;
                    creature.isAlive = false;
                    break;
                }
            }
        }
    }

    protected double getPlantEatProbability() {
        return 100.0;
    }

    @Override
    public Animal reproduce(Island island) {
        if (!isAlive || energy < 50 || hungerLevel > 0) return null;

        List<Creature> location = island.getLocation(x, y);
        Animal partner = null;
        for (Creature creature : location) {
            if (creature.getClass() == this.getClass() && creature != this && creature.isAlive()) {
                partner = (Animal) creature;
                break;
            }
        }

        if (partner != null && new Random().nextDouble() < 0.3) {
            energy -= 30;
            partner.energy -= 30;
            return createOffspring(x, y);
        }
        return null;
    }

    protected abstract Animal createOffspring(int x, int y);
}