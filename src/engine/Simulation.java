package engine;

import island.Island;
import models.Animal;
import models.Creature;
import models.Plant;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {
    private final Island island;
    private final ScheduledExecutorService scheduler;
    private int tick;

    public Simulation() {
        this.island = new Island(SimulationParameters.ISLAND_WIDTH, SimulationParameters.ISLAND_HEIGHT);
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.tick = 0;
    }

    public void start() {
        // Завдання для росту рослин
        scheduler.scheduleAtFixedRate(() -> {
            List<Creature> plants = island.getAllCreatures().stream().filter(creature -> creature instanceof Plant && creature.isAlive()).toList();
            for (Creature plant : plants) {
                plant.act(island);
            }
        }, 0, SimulationParameters.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
        // Завдання для життєвого циклу тварин
        scheduler.scheduleAtFixedRate(() -> {
            ExecutorService animalExecutor = Executors.newFixedThreadPool(4);
            List<Creature> animals = island.getAllCreatures().stream().
                    filter(creature -> creature instanceof Animal && creature.isAlive()).toList();
            for (Creature animal : animals) {
                animalExecutor.submit(() -> animal.act(island));
            }
            animalExecutor.shutdown();
            try {
                animalExecutor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Очистка мертвих істот
            for (int i = 0; i < island.getWidth(); i++) {
                for (int j = 0; j < island.getHeight(); j++) {
                    island.getLocation(i, j).removeIf(creature -> !creature.isAlive());
                }
            }
        }, 0, SimulationParameters.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
        // Завдання для статистики
        scheduler.scheduleAtFixedRate(() -> {
            island.printStatistics(tick);
            tick++;
            if (tick >= SimulationParameters.SIMULATION_END_TICKS) {
                scheduler.shutdown();
                System.out.println("Simulation ended after " + tick + " ticks.");
            }
        }, 0, SimulationParameters.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
    }


}

