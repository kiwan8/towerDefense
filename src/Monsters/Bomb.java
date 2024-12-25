package src.Monsters;

import src.*;

public class Bomb extends Ennemy {
    public Bomb(double spawnTime, int x, int y) {
        super(1, 5, 2.0, 3, Element.Feu, null, ModeAttaque.NEAREST, 2.0, spawnTime, x, y);
    }
}
