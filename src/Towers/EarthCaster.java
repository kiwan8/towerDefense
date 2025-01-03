package src.Towers;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import src.*;

/**
 * La classe EarthCaster représente une tour de type lanceur de terre dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class EarthCaster extends Tower {

    /**
     * Constructeur de la classe EarthCaster.
     *
     * @param position La position de la tour sur la carte.
     */
    public EarthCaster(Tile position) {
        super(50, 7, 0.5, 2.5, Element.Terre, position, ModeAttaque.STRONGEST_PERCENT_PV, 100, Color.GREEN);
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
        if (mode == ModeAttaque.STRONGEST_PERCENT_PV) {
            // Trouve l'ennemi ayant le plus de PV dans la portée
            Warrior ciblePrincipale = ennemis.stream()
                    .max((e1, e2) -> Integer.compare(e1.getPV(), e2.getPV()))
                    .orElse(null);

            if (ciblePrincipale == null) {
                return List.of(); // Aucun ennemi trouvé
            }

            // Trouve tous les ennemis à moins de 1.0 case de la cible principale
            List<Warrior> ciblesAoE = Game.getActiveEnemies().stream()
                    .filter(e -> calculatePixelDistance(e, ciblePrincipale) <= 1)
                    .collect(Collectors.toList());

            // Ajouter la cible principale si elle n'est pas déjà incluse
            if (!ciblesAoE.contains(ciblePrincipale)) {
                ciblesAoE.add(ciblePrincipale);
            }

            return ciblesAoE;
        }

        // Logique par défaut si le mode d'attaque ne correspond pas
        return super.selectTargets(mode, ennemis);
    }

}
