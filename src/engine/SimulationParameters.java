package engine;

public class SimulationParameters {
    public static final int ISLAND_WIDTH = 100;
    public static final int ISLAND_HEIGHT = 20;
    public static final int TICK_DURATION_MS = 1000; // 1 секунда на такт
    public static final int MAX_CREATURES_PER_CELL = 50;
    public static final int SIMULATION_END_TICKS = 30; // Зупинка після 100 тактів
    public static final int INITIAL_PLANTS = 1000;
    public static final int INITIAL_HERBIVORES = 100;
    public static final int INITIAL_PREDATORS = 20;
    public static final double WOLF_PACK_PROBABILITY = 0.7; // Імовірність руху зграєю
    public static final int[] RIVER_CELLS = {50, 51, 52}; // Клітинки з річкою
}
