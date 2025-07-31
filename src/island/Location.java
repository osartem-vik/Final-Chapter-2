package island;

import models.Creature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {
    private final List<Creature> creatures;
    private final int maxCapacity;

    public Location(int maxCapacity) {
        this.creatures = new CopyOnWriteArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    public boolean addCreature(Creature creature) {
        if (creature != null && creature.isAlive() && creatures.size() < maxCapacity) {
            return creatures.add(creature);
        }
        return false;
    }

    public boolean removeCreature(Creature creature) {
        return creature != null && creatures.remove(creature);
    }

    public List<Creature> getCreatures() {
        return new ArrayList<>(creatures);
    }

    public int getCreatureCount() {
        return creatures.size();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isFull() {
        return creatures.size() >= maxCapacity;
    }
}