package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Goat extends Herbivore {
    public Goat(int x, int y) {
        super(x, y, 80, 20, 60, 3, 3, 10, 0.75, 3, "🐐");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Goat(x, y);
    }
}
