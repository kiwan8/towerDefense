package src.Monsters;

import java.util.List;
import java.util.stream.Collectors;

import src.*;

public class WaterBrute extends Ennemy {
    /**
     * Constructeur pour Water Brute.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public WaterBrute(double spawnTime, double x, double y, Map map) {
        super(30, 5, 1.0, 3, Element.Eau, 1.0, spawnTime, x, y, map, 3, ModeAttaque.LEAST_HP_TOWER);
    }

    @Override
    public void attaquer(Warrior cible) {
        super.attaquer(cible); // Inflige des dégâts à la cible principale

        double cellSize = Game.getCurrentLevel().getMap().getCellSize(); // Taille d'une case
        double maxDistanceInPixels = 1.5 * cellSize; // Distance maximale en pixels pour les tours voisines

        // Trouver les tours voisines à moins de 1.5 case de la cible
        List<Warrior> toursVoisines = Game.getActiveTower().stream()
                .filter(t -> t != cible) // Exclure la cible principale
                .filter(t -> calculatePixelDistance(cible, t) <= maxDistanceInPixels) // Vérifie la distance
                .collect(Collectors.toList());

        // Infliger les mêmes dégâts aux tours voisines
        for (Warrior tour : toursVoisines) {
            super.attaquer(tour); // Inflige les mêmes dégâts
        }

    }

}
