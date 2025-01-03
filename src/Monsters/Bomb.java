package src.Monsters;

import java.util.List;
import java.util.stream.Collectors;
import src.*;

public class Bomb extends Ennemy {

    /**
     * Constructeur pour Bomb.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public Bomb(double spawnTime, double x, double y, Map map) {
        super(1, 5, 2.0, 3, Element.Feu, 2.0, spawnTime, x, y, map, 2, ModeAttaque.LEAST_HP_TOWER);
    }


    @Override
    public void attaquer(Warrior cible) {
        // Inflige des dégâts à la cible principale
        super.attaquer(cible);

        double cellSize = Game.getCurrentLevel().getMap().getCellSize();
        double maxDistanceInPixels = 1.5 * cellSize;

        // Trouver les tours dans la zone d'effet
        List<Warrior> toursDansZone = Game.getActiveTower().stream()
                .filter(t -> calculatePixelDistance(cible, t) <= maxDistanceInPixels)
                .collect(Collectors.toList());

        // Inflige des dégâts aux tours dans la zone d'effet
        for (Warrior tour : toursDansZone) {
            tour.takeDamage(this.getATK() * 10); // Inflige 10 fois les dégâts à chaque tour dans la portée
        }

        /
    }

   
}
