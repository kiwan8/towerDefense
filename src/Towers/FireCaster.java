package src.Towers;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import src.*;

/**
 * La classe FireCaster représente une tour de type lanceur de feu dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class FireCaster extends Tower {

    /**
     * Constructeur de la classe FireCaster.
     *
     * @param position La position de la tour sur la carte.
     */
    public FireCaster(Tile position) {
        super(30, 10, 0.5, 2.5, Element.Feu, position, ModeAttaque.NEAREST, 100, Color.RED);
    }

    @Override
    public void attaquer(Warrior cible) {
        // Attaque la cible principale
        super.attaquer(cible);

        // Calculer la distance maximale en pixels correspondant à 0.75 case
        double cellSize = Game.getCurrentLevel().getMap().getCellSize();
        double maxDistanceInPixels = 0.75 * cellSize;


        // Trouver les ennemis proches (excluant la cible principale)
        List<Warrior> ennemisProches = Game.getActiveEnemies().stream()
                .filter(e -> e != cible) // Exclure la cible principale
                .filter(e -> Warrior.calculatePixelDistance(cible, e) <= maxDistanceInPixels)
                .collect(Collectors.toList());

        // Infliger les mêmes dégâts aux ennemis proches
        for (Warrior ennemi : ennemisProches) {
            super.attaquer(ennemi);
        }
    }
}
