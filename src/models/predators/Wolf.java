package models.predators;

import engine.SimulationParameters;
import island.Island;
import models.Animal;
import models.Creature;
import models.Predator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wolf extends Predator {
    public Wolf(int x, int y) {
        super(x, y, 120, 40, 500, 2, 2, 80, 0.9, 2, "🐺");
    }


    @Override
    public void move(Island island) {
        if (!isAlive || new Random().nextDouble() > moveProbability)
            return;
        // Зграйна поведінка: вовки рухаються разом
        if (new Random().nextDouble() < SimulationParameters.WOLF_PACK_PROBABILITY) {
            List<Creature> location = island.getLocation(x, y);
            List<Wolf> pack = new ArrayList<>();
            for (Creature creature : location) {
                if (creature instanceof Wolf && creature.isAlive()) {
                    pack.add((Wolf) creature);
                }
            }
            Random rand = new Random();
            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            int[] direction = directions[rand.nextInt(directions.length)];
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (newX >= 0 && newX < island.getWidth() && newY >= 0 && newY < island.getHeight() && !isRiverCell(newX)) {
                for (Wolf wolf : pack) {
                    island.moveCreature(wolf, newX, newY);
                }
            }
        } else {
            super.move(island);
        }
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Wolf(x, y);
    }
}