package src;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Warrior { // Classe abstraite Warrior (représente les entités du jeu : tours, ennemis)

    private int PV; // Points de vie
    private final int maxPV;
    private double ATK; // Points d'attaque
    private double ATKSpeed; // Vitesse d'attaque
    private double Range; // Portée d'attaque
    private Element element; // Element de l'entité
    private Tile position; // Position de l'entité
    private ModeAttaque modeAttaque; // Mode d'attaque
    private double attackCooldown;
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Warrior(int PV, double ATK, double ATKSpeed, double Range, Element element, Tile position,
            ModeAttaque modeAttaque) { // Constructeur
        this.PV = PV;
        this.maxPV = PV;
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

    public int getMaxPV() {
        return maxPV;
    }

    /**
     * Définit les points de vie (PV) de l'entité.
     * Si la valeur est inférieure à 0, elle est forcée à 0.
     * Si la valeur dépasse maxPV, elle est forcée à maxPV.
     *
     * @param PV La nouvelle valeur des points de vie.
     */
    public void setPV(int PV) {
        if (PV < 0) {
            this.PV = 0; // Si les PV sont inférieurs à 0, force à 0
        } else if (PV > this.maxPV) {
            this.PV = this.maxPV; // Si les PV dépassent maxPV, force à maxPV
        } else {
            this.PV = PV; // Sinon, assigne la nouvelle valeur
        }
    }

    public double getATK() { // Getter
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

    public double getRange() { // Getter
        return Range;
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

    public void takeDamage(double poisonDamage) {
        this.PV -= poisonDamage;
        if (PV < 0) {
            PV = 0;
        }
    }

    public double CalculateVulnerability(Element element) { // Méthode qui retourne le coefficient de vulnérabilité de
                                                            // l'entité
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
        if (cible.PV < 0) {
            cible.PV = 0;
        }
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
     * Calculates the Euclidean distance between two Warriors based on their pixel
     * coordinates.
     *
     * @param target The target Warrior whose distance from the current Warrior is
     *               calculated.
     * @return The Euclidean distance.
     */
    public double calculatePixelDistance(Warrior target) {
        return Math.sqrt(Math.pow(this.x - target.getX(), 2) + Math.pow(this.y - target.getY(), 2));
    }

    public static double calculatePixelDistanceX(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Calculates the Euclidean distance between two Warriors based on their pixel
     * coordinates.
     *
     * @param w1 The first Warrior.
     * @param w2 The second Warrior.
     * @return The Euclidean distance between the two Warriors.
     */
    public static double calculatePixelDistance(Warrior w1, Warrior w2) {
        if (w1 == null || w2 == null) {
            throw new IllegalArgumentException("Both warriors must be non-null.");
        }
        return Math.sqrt(Math.pow(w1.getX() - w2.getX(), 2) + Math.pow(w1.getY() - w2.getY(), 2));
    }

    /**
     * Sélectionne des cibles pour une entité à attaquer en fonction du mode
     * d'attaque.
     *
     * @param mode    Le mode d'attaque de l'entité qui détermine la logique de
     *                sélection des cibles.
     * @param ennemis La liste des ennemis à portée.
     * @return Une liste contenant les cibles sélectionnées. Si aucune cible n'est
     *         trouvée, retourne une liste vide.
     */
    public List<Warrior> selectTargets(ModeAttaque mode, List<Warrior> ennemis) {
        switch (mode) {
            case NEAREST:
                // Trouver l'ennemi le plus proche en pixels
                return ennemis.stream()
                        .min(Comparator.comparingDouble(this::calculatePixelDistance)) // Compare les distances en
                                                                                       // pixels
                        .map(Collections::singletonList) // Transforme en liste contenant un seul élément
                        .orElse(Collections.emptyList()); // Si aucun ennemi trouvé, retourne une liste vide
            case NEAREST_FROM_BASE:
                // Étape 1 : Filtrer les ennemis dans la portée
                Ennemy ciblePrincipale = ennemis.stream()
                        .map(e -> (Ennemy) e) // Cast vers Ennemy
                        .max(Comparator.comparingInt(Ennemy::getCurrentStep)) // Étape 2 : Trouver le plus avancé
                        .orElse(null);

                // Étape 3 : Vérifier si une cible principale est trouvée
                if (ciblePrincipale == null) {
                    return Collections.emptyList(); // Aucun ennemi trouvé
                }
                return List.of(ciblePrincipale); // Retourne une liste contenant uniquement la cible principale
            case LEAST_HP_TOWER:
                if (mode == ModeAttaque.LEAST_HP_TOWER) {
                    // Trouve la tour ayant le moins de PV dans sa portée
                    Warrior cible = ennemis.stream()
                            .filter(t -> this.calculatePixelDistance(t) <= this.getRange()) // Vérifie que la tour est
                                                                                            // dans la portée
                            .min((t1, t2) -> Integer.compare(t1.getPV(), t2.getPV())) // Compare les PV
                            .orElse(null);

                    return cible != null ? List.of(cible) : List.of(); // Retourne la cible ou une liste vide
                }

            default:
                // Retourne le premier ennemi si présent, sinon une liste vide
                return ennemis.isEmpty() ? Collections.emptyList() : List.of(ennemis.get(0));
        }
    }

    // Vérifie si la tour peut attaquer
    public boolean canAttack(double deltaTime) {
        attackCooldown -= deltaTime;
        return attackCooldown <= 0;
    }

    // Réinitialise le cooldown après une attaque
    public void resetAttackCooldown() {
        this.attackCooldown = this.getATKSpeed();
    }

}
