package src.Towers;

import java.awt.Color;
import java.util.List;
import java.util.Collections;
import src.*;

public class GoldDigger extends Tower {
    public GoldDigger(Tile position) {
        super(20, 10, 2.0, 10, Element.Terre, position, ModeAttaque.NEAREST_FOR_GOLD, 20, new Color(255, 223, 0)); // Couleur or
    }

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

    @Override
    public void attaquer(Warrior cible) {

        // Infliger des dégâts directs
        super.attaquer(cible);

        // Générer 1 pièce pour le joueur
        Game.getPlayer().setArgent(Game.getPlayer().getArgent() + 1);
        System.out.println("Gold Digger génère 1 pièce pour le joueur !");
    }
}
