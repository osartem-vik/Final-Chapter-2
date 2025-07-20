package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Deer extends Herbivore {
    public Deer(int x, int y) {
        super(x, y, 90, 25, 150, 4, 4, 3, 0.7, 2, "🦌");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Deer(x, y);
    }
}
