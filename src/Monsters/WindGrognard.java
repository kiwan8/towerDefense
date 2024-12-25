package src.Monsters;

import src.*;

public class WindGrognard extends Ennemy {
    public WindGrognard(double spawnTime, int x, int y) {
        super(1, 7, 2.0, 5, Element.Air, null, ModeAttaque.NEAREST, 2.0, spawnTime, x, y);
    }
}
