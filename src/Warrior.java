package src;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public abstract class Warrior { // Classe abstraite Entite (représente les entités du jeu : tours, ennemis)

    private int PV; // Points de vie
    private int ATK; // Points d'attaque
    private double ATKSpeed; // Vitesse d'attaque
    private int Range; // Portée d'attaque
    private Element element; // Element de l'entité
    private Tile position; // Position de l'entité
    private ModeAttaque modeAttaque; // Mode d'attaque

    public Warrior(int PV, int ATK, double ATKSpeed, int Range, Element element, Tile position,
            ModeAttaque modeAttaque) { // Constructeur
        this.PV = PV;
        this.ATK = ATK;
        this.ATKSpeed = ATKSpeed;
        this.Range = Range;
        this.element = element;
        this.position = position;
        this.modeAttaque = modeAttaque;
    }

    public int getPV() { // Getter
        return PV;
    }

    public void setPV(int PV) { // Setter
        this.PV = PV;
    }

    public int getATK() { // Getter
        return ATK;
    }

    public void setATK(int ATK) { // Setter
        this.ATK = ATK;
    }

    public double getATKSpeed() { // Getter
        return ATKSpeed;
    }

    public void setATKSpeed(double ATKSpeed) { // Setter
        this.ATKSpeed = ATKSpeed;
    }

    public int getRange() { // Getter
        return Range;
    }

    public void setRange(int Range) { // Setter
        this.Range = Range;
    }

    public Element getElement() { // Getter
        return element;
    }

    public void setElement(Element element) { // Setter
        this.element = element;
    }

    public Tile getPosition() { // Getter
        return position;
    }

    public void setPosition(Tile position) { // Setter
        this.position = position;
    }

    public ModeAttaque getModeAttaque() { // Getter
        return modeAttaque;
    }

    public void setModeAttaque(ModeAttaque modeAttaque) { // Setter
        this.modeAttaque = modeAttaque;
    }

    public double CalculateVulnerability(Element element) { // Méthode qui retourne le coefficient de vulnérabilité de l'entité
                                                   // par rapport à un élément
        if (this.element == Element.Feu) { // Si l'entité est de type feu
            if (element == Element.Eau) {
                return 1.5;
            } else if (element == Element.Terre) {
                return 0.5;
            }
        } else if (this.element == Element.Eau) { // Si l'entité est de type eau
            if (element == Element.Air) {
                return 1.5;
            } else if (element == Element.Feu) {
                return 0.5;
            }
        } else if (this.element == Element.Terre) { // Si l'entité est de type terre
            if (element == Element.Feu) {
                return 1.5;
            } else if (element == Element.Air) {
                return 0.5;
            }
        } else if (this.element == Element.Air) { // Si l'entité est de type air
            if (element == Element.Terre) {
                return 1.5;
            } else if (element == Element.Eau) {
                return 0.5;
            }
        }
        return 1;
    }

    public void attaquer(Warrior cible) { // Méthode qui permet à l'entité d'attaquer
        cible.PV -= (int) (this.ATK * cible.CalculateVulnerability(this.element));
    }

    /**
     * Calculates the Euclidean distance between the current entity and another
     * entity.
     *
     * @param target The target entity whose distance from the current entity is
     *               calculated.
     * @return The Euclidean distance rounded to an integer.
     * @throws IllegalArgumentException If the target or its position is invalid.
     */
    public int calculateDistance(Warrior target) {

        return calculateDistance(this, target);
    }

    /**
     * Calculates the Euclidean distance between two Warriors based on their
     * positions.
     *
     * @param warrior1 The first Warrior.
     * @param warrior2 The second Warrior.
     * @return The Euclidean distance rounded to an integer.
     * @throws IllegalArgumentException If one or both Warriors or their positions
     *                                  are null.
     */
    public int calculateDistance(Warrior warrior1, Warrior warrior2) {
        Tile pos1 = warrior1.getPosition();
        Tile pos2 = warrior2.getPosition();

        return (int) Math.sqrt(
                Math.pow(pos1.getCol() - pos2.getCol(), 2) +
                        Math.pow(pos1.getRow() - pos2.getRow(), 2));
    }

    /**
     * Sélectionne des cibles pour une entité à attaquer en fonction du mode
     * d'attaque.
     *
     * @param mode    Le mode d'attaque de l'entité qui détermine la logique de
     *                sélection des cibles.
     *                - NEAREST : cible l'ennemi le plus proche en distance.
     *                - MULTIPLE_TARGET : cible tous les ennemis à portée.
     *                - STRONGEST_ATK : cible l'ennemi avec l'attaque la plus
     *                élevée.
     *                - DEFAULT : cible le premier ennemi ou aucune cible si la
     *                liste est vide.
     * @param ennemis La liste des ennemis à portée.
     * @return Une liste contenant les cibles sélectionnées. Si aucune cible n'est
     *         trouvée, retourne une liste vide.
     */
    public List<Warrior> selectTargets(ModeAttaque mode, List<Warrior> ennemis) {
        switch (mode) {
            case NEAREST:
                // Cherche l'ennemi le plus proche via stream().min().
                // .stream() transforme la liste en flux pour manipulations.
                // .min(Comparator.comparingInt(e -> distance(this, e))) retourne l'ennemi le
                // plus proche.
                // .map(Collections::singletonList) met l'ennemi trouvé dans une liste
                // (singleton).
                // .orElse(Collections.emptyList()) retourne une liste vide si aucun ennemi
                // n'est trouvé.
                return ennemis.stream()
                        .min(Comparator.comparingInt(e -> calculateDistance(this, e))) // Compare les distances pour trouver le
                                                                              // minimum
                        .map(Collections::singletonList) // Transforme le résultat en une liste avec un seul élément
                        .orElse(Collections.emptyList()); // Si rien trouvé, renvoie une liste vide
            case MULTIPLE_TARGET:
                // Retourne tous les ennemis sans transformation
                return ennemis;
            case STRONGEST_ATK:
                // Cherche l'ennemi avec l'attaque la plus forte via stream().max().
                // .max(Comparator.comparingInt(Entite::getATK)) compare les attaques pour
                // trouver le maximum.
                // .map(Collections::singletonList) met l'ennemi trouvé dans une liste
                // (singleton).
                // .orElse(Collections.emptyList()) retourne une liste vide si aucun ennemi
                // n'est trouvé.
                return ennemis.stream()
                        .max(Comparator.comparingInt(Warrior::getATK)) // Compare les attaques pour trouver le maximum
                        .map(Collections::singletonList) // Transforme le résultat en liste singleton
                        .orElse(Collections.emptyList()); // Si rien trouvé, renvoie une liste vide
            default:
                // Retourne le premier ennemi si présent, sinon une liste vide
                return ennemis.isEmpty() ? Collections.emptyList() : List.of(ennemis.get(0));
        }
    }

}
