package statistics;

import engine.SimulationParameters;
import island.Island;
import models.Creature;
import models.Herbivore;
import models.Plant;
import models.Predator;

import java.util.List;

public class IslandStatistics {
    private final Island island;

    public IslandStatistics(Island island) {
        this.island = island;
    }

    public void printStatistics(int tick) {
        int plants = 0, herbivores = 0, predators = 0;
        for (Creature creature : island.getAllCreatures()) {
            if (creature.isAlive()) {
                if (creature instanceof Plant) plants++;
                else if (creature instanceof Herbivore) herbivores++;
                else if (creature instanceof Predator) predators++;
            }
        }
        System.out.println("Tick " + tick + ": Plants=" + plants + ", Herbivores=" + herbivores + ", Predators=" + predators);

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
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
                    List<Creature> location = island.getLocation(x, y);
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
