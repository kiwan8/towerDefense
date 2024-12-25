package src.Monsters;

import src.*;

public class MerchantKing extends Ennemy {
    public MerchantKing(double spawnTime, int x, int y) {
        super(100, 0, 0.0, 0, Element.Terre, null, ModeAttaque.NEAREST, 2.0, spawnTime, x, y);
    }
}
