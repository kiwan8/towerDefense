package src.Monsters;

import src.*;

public class EarthBrute extends Ennemy {
    public EarthBrute(double spawnTime, int x, int y) {
        super(30, 5, 1.0, 3, Element.Terre, null, ModeAttaque.NEAREST, 1.0, spawnTime, x, y);
    }
}
