package models.predators;

import models.Animal;
import models.Predator;

public class Fox extends Predator {
    public Fox(int x, int y) {
        super(x, y, 90, 30, 6, 20, 2, 1, 0.85, 3, "🦊");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Fox(x, y);
    }
}