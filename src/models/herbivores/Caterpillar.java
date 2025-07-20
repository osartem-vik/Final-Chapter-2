package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Caterpillar extends Herbivore {
    public Caterpillar(int x, int y) {
        super(x, y, 40, 5, 0.01, 1000, 0, 0, 0.5, 6, "🐛");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Caterpillar(x, y);
    }
}