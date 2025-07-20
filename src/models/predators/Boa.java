package models.predators;

import models.Animal;
import models.Predator;

public class Boa extends Predator {
    public Boa(int x, int y) {
        super(x, y, 100, 35, 6, 20, 1, 1, 0.6, 3, "🐍");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Boa(x, y);
    }
}
