package src.Towers;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import src.*;

/**
 * La classe PoisonCaster représente une tour de type lanceur de poison dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class PoisonCaster extends Tower {

    /**
     * Constructeur de la classe PoisonCaster.
     *
     * @param position La position de la tour sur la carte.
     */
    public PoisonCaster(Tile position) {
        super(50, 1, 2.0, 5, Element.Air, position, ModeAttaque.NEAREST_ADVANCED_UNPOISONED, 80, new Color(128, 0, 128));
    }

    /**
     * Sélectionne les cibles en fonction du mode d'attaque.
     *
     * @param mode Le mode d'attaque.
     * @param ennemis La liste des ennemis à portée.
     * @return La liste des cibles sélectionnées.
     */
    @Override
    public List<Warrior> selectTargets(ModeAttaque mode, List<Warrior> ennemis) {
        switch (mode) {
            case NEAREST_ADVANCED_UNPOISONED:
                // Trouve l'ennemi le plus avancé dans son chemin qui n'est pas empoisonné
                Ennemy cible = ennemis.stream()
                        .filter(e -> e instanceof Ennemy && !((Ennemy) e).isPoisoned()) // Filtre les ennemis non empoisonnés
                        .map(e -> (Ennemy) e)
                        .filter(e -> this.calculateDistance(e) <= this.getRange()) // Ennemis dans la portée
                        .max(Comparator.comparingInt(Ennemy::getCurrentStep)) // Le plus avancé dans son chemin
                        .orElse(null);

                return cible != null ? List.of(cible) : Collections.emptyList();
            default:
                return super.selectTargets(mode, ennemis);
        }
    }

    /**
     * Attaque la cible spécifiée.
     *
     * @param cible La cible à attaquer.
     */
    @Override
    public void attaquer(Warrior cible) {
        if (!(cible instanceof Ennemy)) return;

        // Infliger des dégâts directs
        super.attaquer(cible);

        // Appliquer l'effet de poison
        Ennemy ennemi = (Ennemy) cible;
        ennemi.applyPoison(this.getATK(), this.getATKSpeed());
    }
}
