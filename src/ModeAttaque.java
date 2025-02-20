package src;

/**
 * L'énumération ModeAttaque représente les différents modes d'attaque des tours dans le jeu.
 */
public enum ModeAttaque {
    /**
     * Attaque une seule cible.
     */
    SINGLE_TARGET,

    /**
     * Attaque plusieurs cibles.
     */
    MULTIPLE_TARGET,

    /**
     * Attaque une cible aléatoire.
     */
    RANDOM,

    /**
     * Attaque la cible la plus proche.
     */
    NEAREST,

    /**
     * Attaque la cible la plus proche de la base.
     */
    NEAREST_FROM_BASE,

    /**
     * Attaque la cible avec l'attaque la plus élevée.
     */
    STRONGEST_ATK,

    /**
     * Attaque la cible avec le pourcentage de PV le plus élevé.
     */
    STRONGEST_PERCENT_PV,

    /**
     * Attaque avec un railgun.
     */
    RAILGUN,

    /**
     * N'attaque personne.
     */
    NOBODY,

    /**
     * Attaque la tour avec le moins de PV.
     */
    LEAST_HP_TOWER,

    /**
     * Attaque l'ennemi le plus avancé qui n'est pas empoisonné.
     */
    NEAREST_ADVANCED_UNPOISONED;
}