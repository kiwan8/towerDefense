package src;

public enum ModeAttaque { // Enumération des modes d'attaque
    SINGLE_TARGET, // Attaque une seule cible
    MULTIPLE_TARGET, // Attaque plusieurs cibles
    RANDOM, // Attaque une cible aléatoire
    NEAREST, // Attaque la cible la plus proche
    NEAREST_FROM_BASE, // Attaque la cible la plus proche de la base
    STRONGEST_ATK, // Attaque la cible avec l'attaque la plus élevée
    STRONGEST_PERCENT_PV, // Attaque la cible avec le pourcentage de PV le plus élevé
    NEAREST_ADVANCED_UNPOISONED;
};