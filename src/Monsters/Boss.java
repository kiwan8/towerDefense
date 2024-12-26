package src.Monsters;
import src.*;

public class Boss extends Ennemy {
    public Boss(double spawnTime, double x, double y) {
        super(150, 100, 10.0, 2, Element.Feu, null, ModeAttaque.STRONGEST_ATK, 0.5, spawnTime, x, y);
    }
}
