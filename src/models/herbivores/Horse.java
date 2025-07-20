package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Horse extends Herbivore {
    public Horse(int x, int y) {
        super(x, y, 100, 30, 400, 2, 3, 8, 0.8, 2, "🐎");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Horse(x, y);
    }
}
