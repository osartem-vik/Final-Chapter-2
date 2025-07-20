package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Sheep extends Herbivore {
    public Sheep(int x, int y) {
        super(x, y, 80, 20, 70, 3, 3, 15, 0.7, 3, "🐑");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Sheep(x, y);
    }
}
