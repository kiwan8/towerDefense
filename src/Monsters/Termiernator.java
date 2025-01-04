package src.Monsters;

import src.*;
import src.libraries.StdDraw;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Termiernator extends Ennemy {

    private double timeSinceLastAttack = 0.0; // Temps écoulé depuis la dernière attaque
    private double timeSinceLastMessage = 0.0; // Temps écoulé depuis le dernier message
    private static final double ATTACK_INTERVAL = 15.0; // Temps entre deux attaques (en secondes)
    private static final double MESSAGE_INTERVAL = 10.0; // Temps entre deux messages (en secondes)
    private static final double MESSAGE_DURATION = 2.0; // Durée d'affichage du message (en secondes)
    private boolean messageDisplayed = false; // Indique si le message est actuellement affiché

    /**
     * Constructeur pour Termiernator.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public Termiernator(double spawnTime, double x, double y, Map map) {
        super(999, 999, 15.0, 0, Element.Neutre, 0.5, spawnTime, x, y, map, 100, ModeAttaque.NOBODY);
    }

    @Override
    public List<Warrior> selectTargets(ModeAttaque mode, List<Warrior> ennemis) {
        if (mode == ModeAttaque.NOBODY) {
            return Collections.emptyList(); // Ne cible personne par défaut
        }

        // Cible une tour aléatoire si des tours sont disponibles
        List<Tower> towers = Game.getActiveTower();
        if (!towers.isEmpty()) {
            Random random = new Random();
            Tower target = towers.get(random.nextInt(towers.size()));
            return List.of(target);
        }

        return Collections.emptyList();
    }


    /**
     * Attaque la cible spécifiée.
     *
     * @param cible La cible à attaquer.
     */
    @Override
    public void attaquer(Warrior cible) {
        Random random = new Random();
            Tower target = Game.getActiveTower().get(random.nextInt(Game.getActiveTower().size()));
            target.setPV(0); // Élimine la tour
            System.out.println("Termiernator a détruit une tour !");
    }
}
