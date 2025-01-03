package src.Monsters;

import java.util.List;
import java.util.stream.Collectors;
import src.*;

public class Healer extends Ennemy {
    private double healCooldown; // Cooldown pour le soin

    /**
     * Constructeur pour Healer.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public Healer(double spawnTime, double x, double y, Map map) {
        super(10, 1, 1.0, 2, Element.Neutre, 0.5, spawnTime, x, y, map, 3, ModeAttaque.NEAREST);
        this.healCooldown = 5.0; // Initialisation du cooldown à 5 secondes
    }

    /**
     * Met à jour l'état du Healer, en gérant le soin toutes les 5 secondes.
     *
     * @param deltaTime Temps écoulé depuis la dernière mise à jour (en secondes).
     */
    public void update(double deltaTime) {
        healCooldown -= deltaTime;

        if (healCooldown <= 0) {
            this.healNearbyEnemies(); // Active l'effet de soin
            healCooldown = 5.0; // Réinitialise le cooldown
        }
    }

    /**
     * Soigne les ennemis proches de 5 PV.
     */
    private void healNearbyEnemies() {
        double cellSize = Game.getCurrentLevel().getMap().getCellSize(); // Taille d'une case
        double maxDistanceInPixels = 1.5 * cellSize; // Distance maximale en pixels pour le soin

        List<Warrior> ennemisProches = Game.getActiveEnemies().stream()
                .filter(e -> e != this) // Exclure le Healer lui-même
                .filter(e -> calculatePixelDistance(e,this) <= maxDistanceInPixels) // Vérifie la distance
                .collect(Collectors.toList());

        for (Warrior ennemi : ennemisProches) {
            ennemi.setPV(ennemi.getPV() + 5); // Soigne de 5 PV
        }
    }
}