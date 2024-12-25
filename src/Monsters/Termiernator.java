package src.Monsters;

import src.*;

public class Termiernator extends Ennemy {
    public Termiernator(double spawnTime, int x, int y) {
        super(999, 999, 15.0, 0, Element.Neutre, null, ModeAttaque.NEAREST, 0.5, spawnTime, x, y);
    }
}
