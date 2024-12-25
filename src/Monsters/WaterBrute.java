package src.Monsters;

import src.*;

public class WaterBrute extends Ennemy {
    public WaterBrute(double spawnTime, int x, int y) {
        super(30, 5, 1.0, 3, Element.Eau, null, ModeAttaque.NEAREST, 1.0, spawnTime, x, y);
    }
}
