package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Rabbit extends Herbivore {
    public Rabbit(int x, int y) {
        super(x, y, 60, 15, 2, 150, 2, 0.45, 0.9, 4, "🐇");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Rabbit(x, y);
    }
}