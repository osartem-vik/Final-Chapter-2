package island;

import engine.SimulationParameters;
import models.*;
import models.herbivores.*;
import models.predators.*;
import statistics.IslandStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Island {
    private final int width;
    private final int height;
    private final Location[][] grid;
    private final Random rand = new Random();
    private final IslandStatistics statistics;

    @SuppressWarnings("unchecked")
    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Location[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Location(SimulationParameters.MAX_CREATURES_PER_CELL);
            }
        }
        this.statistics = new IslandStatistics(this);
        populate();
    }

    private void populate() {
        // Додаємо рослини
        for (int i = 0; i < SimulationParameters.INITIAL_PLANTS; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            Plant.PlantType type = rand.nextBoolean() ? Plant.PlantType.GRASS : Plant.PlantType.BUSH;
            addCreature(new Plant(x, y, type));
        }
        // Додаємо травоїдних
        for (int i = 0; i < SimulationParameters.INITIAL_HERBIVORES / 10; i++) {
            addCreature(new Horse(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Deer(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Rabbit(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Mouse(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Goat(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Sheep(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Boar(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Buffalo(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Duck(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Caterpillar(rand.nextInt(width), rand.nextInt(height)));
        }
        // Додаємо хижаків
        for (int i = 0; i < SimulationParameters.INITIAL_PREDATORS / 5; i++) {
            addCreature(new Wolf(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Boa(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Fox(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Bear(rand.nextInt(width), rand.nextInt(height)));
            addCreature(new Eagle(rand.nextInt(width), rand.nextInt(height)));
        }
    }

    public void addCreature(Creature creature) {
        int x = creature.getX();
        int y = creature.getY();
        if (x >= 0 && x < width && y >= 0 && y < height) {
            int maxCapacity = (creature instanceof Animal) ? ((Animal) creature).getMaxPerCell() : grid[x][y].getMaxCapacity();
            if (!grid[x][y].isFull() && grid[x][y].addCreature(creature)) {
                creature.setX(x);
                creature.setY(y);
            }
        }
    }

    public void moveCreature(Creature creature, int newX, int newY) {
        if (creature.isAlive() && newX >= 0 && newX < width && newY >= 0 && newY < height) {
            int currentX = creature.getX();
            int currentY = creature.getY();
            if (currentX != newX || currentY != newY) {
                int maxCapacity = (creature instanceof Animal) ? ((Animal) creature).getMaxPerCell() : grid[newX][newY].getMaxCapacity();
                if (!grid[newX][newY].isFull() && grid[currentX][currentY].removeCreature(creature) && grid[newX][newY].addCreature(creature)) {
                    creature.setX(newX);
                    creature.setY(newY);
                }
            }
        }
    }

    public List<Creature> getLocation(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y].getCreatures();
        }
        return new ArrayList<>();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public List<Creature> getAllCreatures() {
        List<Creature> allCreatures = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                allCreatures.addAll(grid[i][j].getCreatures());
            }
        }
        return allCreatures;
    }

    public void printStatistics(int tick) {
        statistics.printStatistics(tick);
    }
}
