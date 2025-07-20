package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Buffalo extends Herbivore {
    public Buffalo(int x, int y) {
        super(x, y, 120, 30, 700, 3, 3, 100, 0.6, 2, "🐃");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Buffalo(x, y);
    }
}
