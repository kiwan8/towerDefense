package src.Monsters;

import java.util.List;
import java.util.stream.Collectors;

import src.*;

public class FireGrognard extends Ennemy {
    /**
     * Constructeur pour Fire Grognard.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public FireGrognard(double spawnTime, double x, double y, Map map) {
        super(1, 7, 2.0, 3, Element.Feu, 2.0, spawnTime, x, y, map, 1, ModeAttaque.NEAREST);
    }

    /**
     * Attaque la cible spécifiée et inflige des dégâts aux tours voisines.
     *
     * @param cible La cible principale à attaquer.
     */
    @Override
    public void attaquer(Warrior cible) {
        if (cible instanceof Tower) { // Vérifie que la cible est une tour
            super.attaquer(cible); // Inflige des dégâts à la cible principale

            double cellSize = Game.getCurrentLevel().getMap().getCellSize();
            double maxDistanceInPixels = 1.5 * cellSize; // Distance maximale en pixels pour les dégâts aux voisins

            // Trouver les tours voisines à moins de 1.5 case de la cible
            List<Warrior> toursVoisines = Game.getActiveTower().stream()
                    .filter(t -> t != cible) // Exclure la cible principale
                    .filter(t -> calculatePixelDistance(cible, t) <= maxDistanceInPixels) // Vérifie la distance
                    .collect(Collectors.toList());

            // Infliger les mêmes dégâts à toutes les tours voisines
            for (Warrior tour : toursVoisines) {
                super.attaquer(tour);
            }
        }
    }
}
