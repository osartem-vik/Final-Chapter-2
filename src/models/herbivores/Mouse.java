package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Mouse extends Herbivore {
    public Mouse(int x, int y) {
        super(x, y, 50, 10, 0.05, 500, 1, 0.01, 0.95, 5, "🐁");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Mouse(x, y);
    }
}
