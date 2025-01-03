package src.Monsters;

import java.util.Collections;
import java.util.List;

import src.*;

public class Minion extends Ennemy {
    /**
     * Constructeur pour Minion.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public Minion(double spawnTime, double x, double y, Map map) {
        super(10, 3, 0, 0, Element.Neutre, 1.0, spawnTime, x, y, map,1,ModeAttaque.NOBODY);
    }

    @Override
public List<Warrior> selectTargets(ModeAttaque mode, List<Warrior> ennemis) {
    return Collections.emptyList();
}

}
