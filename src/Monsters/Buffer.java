package src.Monsters;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import src.*;

public class Buffer extends Ennemy {
    /**
     * Constructeur pour Buffer.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public Buffer(double spawnTime, double x, double y, Map map) {
        super(10, 2, 1.0, 3, Element.Air, 0.5, spawnTime, x, y, map, 5, ModeAttaque.RANDOM);
    }

    @Override
    public List<Warrior> selectTargets(ModeAttaque mode, List<Warrior> ennemis) {
        // Sélectionne une tour aléatoire à attaquer
        return ennemis.isEmpty() ? Collections.emptyList() : List.of(ennemis.get((int) (Math.random() * ennemis.size())));
    }

    @Override
    public void attaquer(Warrior cible) {
        // Augmente passivement la vitesse d'attaque et de déplacement des ennemis proches
        double cellSize = Game.getCurrentLevel().getMap().getCellSize();
        double maxDistanceInPixels = 1.5 * cellSize;

        List<Ennemy> ennemisProches = Game.getActiveEnemies().stream()
                .filter(e -> !e.equals(this)) // Exclure le Buffer lui-même
                .filter(e -> calculatePixelDistance(e) <= maxDistanceInPixels)
                .collect(Collectors.toList());

        for (Ennemy ennemi : ennemisProches) {
            ennemi.setATKSpeed(ennemi.getATKSpeed() * 1.5); // Augmente la vitesse d'attaque
            ennemi.setMovingSpeed(ennemi.getMovingSpeed() * 1.5);       // Augmente la vitesse de déplacement
        }

        // Appelle l'attaque normale sur la cible
        super.attaquer(cible);
    }
}
