package src.Monsters;

import src.*;

public class Buffer extends Ennemy {
    public Buffer(double spawnTime, int x, int y) {
        super(10, 2, 2.0, 3, Element.Air, null, ModeAttaque.NEAREST, 0.5, spawnTime, x, y);
    }
}
