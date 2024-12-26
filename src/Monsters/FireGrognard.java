package src.Monsters;

import src.*;

public class FireGrognard extends Ennemy {
    public FireGrognard(double spawnTime, double x, double y) {
        super(1, 7, 2.0, 5, Element.Feu, null, ModeAttaque.NEAREST, 2.0, spawnTime, x, y);
    }
}
