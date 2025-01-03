package src.Towers;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import src.*;

/**
 * La classe GoldDigger représente une tour de type chercheur d'or dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class GoldDigger extends Tower {

    /**
     * Constructeur de la classe GoldDigger.
     *
     * @param position La position de la tour sur la carte.
     */
    public GoldDigger(Tile position) {
        super(20, 10, 2.0, 10, Element.Terre, position, ModeAttaque.NEAREST_FOR_GOLD, 20, new Color(255, 223, 0)); // Couleur or
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
        if (mode == ModeAttaque.NEAREST_FOR_GOLD) {
            // Trouve l'ennemi le plus proche
            Warrior cible = ennemis.stream()
                    .min((e1, e2) -> Integer.compare(this.calculateDistance(e1), this.calculateDistance(e2)))
                    .orElse(null);
            return cible != null ? List.of(cible) : Collections.emptyList();
        }

        // Si le mode d'attaque ne correspond pas, utilisez la logique par défaut
        return super.selectTargets(mode, ennemis);
    }

    /**
     * Attaque la cible spécifiée.
     *
     * @param cible La cible à attaquer.
     */
    @Override
    public void attaquer(Warrior cible) {
        // Infliger des dégâts directs
        super.attaquer(cible);

        // Générer 1 pièce pour le joueur
        Game.getPlayer().setArgent(Game.getPlayer().getArgent() + 1);
        System.out.println("Gold Digger génère 1 pièce pour le joueur !");
    }
}
