package src.Monsters;
import src.*;

public class Minion extends Ennemy {
    public Minion(double spawnTime, int x, int y) {
        super(10, 3, 0.0, 0, Element.Neutre, null, ModeAttaque.NEAREST, 1.0, spawnTime, x, y);
    }
}