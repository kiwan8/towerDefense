package src;

import java.awt.Color;

/**
 * La classe abstraite Tower représente une tour dans le jeu.
 * Elle hérite de la classe Warrior.
 */
public abstract class Tower extends Warrior {

    // Multiplicateurs statiques pour les bonus globaux
    private static double baseAttackMultiplier = 1.0; // Multiplicateur pour l'attaque
    private static double baseAttackSpeedMultiplier = 1.0; // Multiplicateur pour la vitesse d'attaque

    /**
     * Le coût de la tour.
     */
    private final int cout;

    /**
     * La couleur de la tour.
     */
    private final Color color;

    /**
     * L'argent généré par point de vie de la tour.
     */
    private final double argentParPv;

    /**
     * Constructeur de la classe Tower.
     *
     * @param PV Les points de vie de la tour.
     * @param ATK Les points d'attaque de la tour.
     * @param ATKSpeed La vitesse d'attaque de la tour.
     * @param Range La portée d'attaque de la tour.
     * @param element L'élément de la tour.
     * @param position La position de la tour sur la carte.
     * @param modeAttaque Le mode d'attaque de la tour.
     * @param cout Le coût de la tour.
     * @param color La couleur de la tour.
     */
    public Tower(int PV, double ATK, double ATKSpeed, double Range, Element element, Tile position,
            ModeAttaque modeAttaque, int cout, Color color) {
        super(PV, 
            (ATK * baseAttackMultiplier), // Applique le multiplicateur d'attaque
              ATKSpeed * baseAttackSpeedMultiplier, // Applique le multiplicateur de vitesse d'attaque
              Range, 
              element, 
              position, 
              modeAttaque);
        this.cout = cout;
        this.color = color;
        this.argentParPv = (double) cout / (double) PV;
    }

    /**
     * Retourne le coût de la tour.
     *
     * @return Le coût de la tour.
     */
    public int getCout() {
        return cout;
    }

    /**
     * Retourne la couleur de la tour.
     *
     * @return La couleur de la tour.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retourne l'argent généré par point de vie de la tour.
     *
     * @return L'argent généré par point de vie de la tour.
     */
    public double getArgentParPv() {
        return argentParPv;
    }

    /**
     * Attaque la cible spécifiée.
     *
     * @param cible La cible à attaquer.
     */
    @Override
    public void attaquer(Warrior cible) {
        if (cible instanceof Ennemy) {
            super.attaquer(cible);
        }
    }

    // Méthodes statiques pour gérer les multiplicateurs globaux

    /**
     * Définit un nouveau multiplicateur global pour l'attaque.
     *
     * @param multiplier Le nouveau multiplicateur d'attaque.
     */
    public static void setBaseAttackMultiplier(double multiplier) {
        baseAttackMultiplier = multiplier;
    }

    /**
     * Retourne le multiplicateur global actuel pour l'attaque.
     *
     * @return Le multiplicateur d'attaque.
     */
    public static double getBaseAttackMultiplier() {
        return baseAttackMultiplier;
    }

    /**
     * Définit un nouveau multiplicateur global pour la vitesse d'attaque.
     *
     * @param multiplier Le nouveau multiplicateur de vitesse d'attaque.
     */
    public static void setBaseAttackSpeedMultiplier(double multiplier) {
        baseAttackSpeedMultiplier = multiplier;
    }

    /**
     * Retourne le multiplicateur global actuel pour la vitesse d'attaque.
     *
     * @return Le multiplicateur de vitesse d'attaque.
     */
    public static double getBaseAttackSpeedMultiplier() {
        return baseAttackSpeedMultiplier;
    }
}
