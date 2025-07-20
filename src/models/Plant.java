package models;

import island.Island;

import java.util.Random;

public class Plant extends Creature {
    private final PlantType type;

    public enum PlantType {
        GRASS(50, "🌿"),
        BUSH(80, "🌳");

        private final int energy;
        private final String unicode;

        PlantType(int energy, String unicode) {
            this.energy = energy;
            this.unicode = unicode;
        }

        public int getEnergy() { return energy; }
        public String getUnicode() { return unicode; }
    }

    public Plant(int x, int y, PlantType type) {
        super(x, y, type.getEnergy(), type.getUnicode());
        this.type = type;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public void act(Island island) {
        if (!isAlive) return;
        if (new Random().nextDouble() < 0.1) {
            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            int[] direction = directions[new Random().nextInt(directions.length)];
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (newX >= 0 && newX < island.getWidth() && newY >= 0 && newY < island.getHeight()) {
                island.addCreature(new Plant(newX, newY, type));
            }
        }
    }
}
