package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Boar extends Herbivore {
    public Boar(int x, int y) {
        super(x, y, 100, 25, 400, 2, 2, 50, 0.65, 3, "🐗");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Boar(x, y);
    }
}
