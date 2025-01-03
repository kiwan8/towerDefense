package src.Towers;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import src.*;

/**
 * La classe IceCaster représente une tour de type lanceur de glace dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class IceCaster extends Tower {

    /**
     * Constructeur de la classe IceCaster.
     *
     * @param position La position de la tour sur la carte.
     */
    public IceCaster(Tile position) {
        super(40, 1, 2.0, 5, Element.Eau, position, ModeAttaque.NEAREST_FROM_BASE, 70, new Color(173, 216, 230));
    }

    @Override
    public void attaquer(Warrior cible) {
        // Infliger des dégâts à la cible principale
        super.attaquer(cible);

        // Calculer la distance maximale en pixels correspondant à 1.0 case
        double cellSize = Game.getCurrentLevel().getMap().getCellSize();
        double maxDistanceInPixels = 1.0 * cellSize;

        // Trouver tous les ennemis à moins de maxDistanceInPixels de la cible principale
        List<Warrior> ennemisProches = Game.getActiveEnemies().stream()
                .filter(e -> Warrior.calculatePixelDistance(cible, e) <= maxDistanceInPixels)
                .collect(Collectors.toList());

        // Réduction de la vitesse d'attaque et de déplacement de 30 % pour tous les ennemis proches
        for (Warrior ennemi : ennemisProches) {
            super.attaquer(ennemi); // Inflige les mêmes dégâts qu'à la cible principale
            if (ennemi instanceof Ennemy) {
                Ennemy enemy = (Ennemy) ennemi;
                enemy.setMovingSpeed(enemy.getMovingSpeed() * 0.7); // Réduit la vitesse de déplacement
                enemy.setATKSpeed(enemy.getATKSpeed() * 0.7); // Réduit la vitesse d'attaque
            }
        }
    }
}
