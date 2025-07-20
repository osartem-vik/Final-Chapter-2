package models.predators;

import models.Animal;
import models.Predator;

public class Bear extends Predator {
    public Bear(int x, int y) {
        super(x, y, 150, 50, 400, 4, 2, 60, 0.7, 2, "🐻");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Bear(x, y);
    }
}
