package models;

import engine.SimulationParameters;
import island.Island;
import models.herbivores.Duck;
import models.predators.Eagle;

import java.util.List;
import java.util.Random;

public abstract class Animal extends Creature {
    protected int age;
    protected int maxAge;
    protected double moveProbability;
    protected int offspringCount;
    protected double weightKg; // Вага в кг

    public int getMaxPerCell() {
        return maxPerCell;
    }

    protected int maxPerCell; // Максимальна кількість на клітинку
    protected int speed; // Швидкість пересування
    protected double foodNeeded; // Кількість їжі для насичення
    protected int hungerLevel;

    public Animal(int x, int y, int energy, int maxAge, double weightKg, int maxPerCell, int speed, double foodNeeded,
                  double moveProbability, int offspringCount, String unicode) {
        super(x, y, energy, unicode);
        this.age = 0;
        this.maxAge = maxAge;
        this.weightKg = weightKg;
        this.maxPerCell = maxPerCell;
        this.speed = speed;
        this.foodNeeded = foodNeeded;
        this.moveProbability = moveProbability;
        this.offspringCount = offspringCount;
        this.hungerLevel = 0; // Початковий рівень голоду
    }

    public void move(Island island) {
        if (!isAlive || new Random().nextDouble() > moveProbability || hungerLevel >= foodNeeded) return;

        Random rand = new Random();
        int moves = Math.min(speed, rand.nextInt(speed + 1)); // Кількість клітинок за хід
        for (int i = 0; i < moves; i++) {
            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            int[] direction = directions[rand.nextInt(directions.length)];
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (isRiverCell(newX) && !canCrossRiver()) continue;

            if (newX >= 0 && newX < island.getWidth() && newY >= 0 && newY < island.getHeight() &&
                    island.getLocation(newX, newY).size() < maxPerCell) {
                island.moveCreature(this, newX, newY);
                break; // Рух на одну клітинку за хід
            }
        }
    }

    protected boolean isRiverCell(int x) {
        for (int riverX : SimulationParameters.RIVER_CELLS) {
            if (x == riverX) return true;
        }
        return false;
    }

    protected boolean canCrossRiver() {
        return this instanceof Eagle || this instanceof Duck;
    }

    public void eat(Island island) {
        if (!isAlive) return;
        hungerLevel++;
        if (hungerLevel >= foodNeeded) {
            isAlive = false; // Тварина вмирає від голоду
            return;
        }
        // Логіка поїдання реалізується в підкласах
    }

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

    public void ageOneStep() {
        if (!isAlive) return;
        age++;
        energy -= 5;
        if (age > maxAge || energy <= 0) {
            isAlive = false;
        }
    }

    @Override
    public void act(Island island) {
        if (!isAlive) return;
        eat(island);
        move(island);
        Animal offspring = reproduce(island);
        if (offspring != null) {
            island.addCreature(offspring);
        }
        ageOneStep();
    }

    protected abstract Animal createOffspring(int x, int y);
}