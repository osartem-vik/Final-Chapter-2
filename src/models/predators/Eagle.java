package models.predators;

import models.Animal;
import models.Predator;

public class Eagle extends Predator {
    public Eagle(int x, int y) {
        super(x, y, 110, 40, 2, 150, 3,1 , 0.9, 2, "🦅");
    }

    @Override
    protected Animal createOffspring(int x, int y) {
        return new Eagle(x, y);
    }
}