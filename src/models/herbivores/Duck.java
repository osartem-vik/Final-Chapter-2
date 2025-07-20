package models.herbivores;

import models.Animal;
import models.Herbivore;

public class Duck extends Herbivore {
    public Duck(int x, int y) {
        super(x, y, 70, 15, 1, 200, 4, 0.15, 0.85, 4, "🦆");
    }

    @Override
    protected double getPlantEatProbability() {
        return 100.0; // Качка їсть рослини з 100% ймовірністю
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Duck(x, y);
    }
}
