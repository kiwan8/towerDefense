package src.Monsters;

import src.*;

public class Healer extends Ennemy {
    public Healer(double spawnTime, double x, double y) {
        super(10, 1, 1.0, 2, Element.Neutre, null, ModeAttaque.NEAREST, 0.5, spawnTime, x, y);
    }
}

