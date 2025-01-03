package src.Towers;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import src.*;

/**
 * La classe Railgun représente une tour de type railgun dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class Railgun extends Tower {

    /**
     * Constructeur de la classe Railgun.
     *
     * @param position La position de la tour sur la carte.
     */
    public Railgun(Tile position) {
        super(20, 1, 0.0, 0, Element.Feu, position, ModeAttaque.Railgun, 150, Color.CYAN); // Range et vitesse d'attaque à 0
    }

    /**
     * Surcharge la méthode selectTargets pour empêcher le Railgun de sélectionner des cibles automatiquement.
     *
     * @param mode    Le mode d'attaque de la tour (ignoré ici).
     * @param ennemis La liste des ennemis disponibles (ignorée ici).
     * @return Une liste vide, car le Railgun ne sélectionne pas de cibles automatiquement.
     */
    @Override
    public List<Warrior> selectTargets(ModeAttaque mode, List<Warrior> ennemis) {
        return Collections.emptyList(); // Retourne toujours une liste vide
    }
}
