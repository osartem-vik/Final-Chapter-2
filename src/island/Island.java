package island;

import engine.SimulationParameters;
import models.*;
import models.herbivores.*;
import models.predators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Island {
    private final int width;
    private final int height;
    private final List<Creature>[][] grid;
    private final Random rand = new Random();

    @SuppressWarnings("unchecked")
    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new List[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new CopyOnWriteArrayList<>();
            }
        }
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
        if (creature.isAlive() && grid[creature.getX()][creature.getY()].size() <
                (creature instanceof Animal ? ((Animal) creature).getMaxPerCell() : SimulationParameters.MAX_CREATURES_PER_CELL)) {
            grid[creature.getX()][creature.getY()].add(creature);
        }
    }

    public void moveCreature(Creature creature, int newX, int newY) {
        if (creature.isAlive() && grid[newX][newY].size() <
                (creature instanceof Animal ? ((Animal) creature).getMaxPerCell() : SimulationParameters.MAX_CREATURES_PER_CELL)) {
            grid[creature.getX()][creature.getY()].remove(creature);
            creature.setX(newX);
            creature.setY(newY);
            grid[newX][newY].add(creature);
        }
    }

    public List<Creature> getLocation(int x, int y) {
        return grid[x][y];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public List<Creature> getAllCreatures() {
        List<Creature> allCreatures = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                allCreatures.addAll(grid[i][j]);
            }
        }
        return allCreatures;
    }

    public void printStatistics(int tick) {
        int plants = 0, herbivores = 0, predators = 0;
        for (Creature creature : getAllCreatures()) {
            if (creature.isAlive()) {
                if (creature instanceof Plant) plants++;
                else if (creature instanceof Herbivore) herbivores++;
                else if (creature instanceof Predator) predators++;
            }
        }
        System.out.println("Tick " + tick + ": Plants=" + plants + ", Herbivores=" + herbivores + ", Predators=" + predators);

        // Псевдографіка
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean isRiver = false;
                for (int riverX : SimulationParameters.RIVER_CELLS) {
                    if (x == riverX) {
                        isRiver = true;
                        break;
                    }
                }
                if (isRiver) {
                    sb.append("🌊");
                } else {
                    List<Creature> location = grid[x][y];
                    boolean found = false;
                    for (Creature creature : location) {
                        if (creature.isAlive()) {
                            sb.append(creature.getUnicode());
                            found = true;
                            break;
                        }
                    }
                    if (!found) sb.append("⬜");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}
