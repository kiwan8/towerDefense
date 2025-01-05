package src;

import java.util.List;

/**
 * Classe abstraite représentant un ennemi générique dans le jeu.
 * Gère les attributs et comportements communs à tous les ennemis, tels que le déplacement
 * et l'interaction avec la carte du jeu.
 */
public abstract class Ennemy extends Warrior {

    // Propriétés immuables

    /**
     * Temps d'apparition de l'ennemi dans la vague.
     */
    private final double spawnTime; // Temps d'apparition dans la vague

    /**
     * Chemin de l'ennemi en coordonnées de tuiles.
     */
    private final List<int[]> path; // Chemin en coordonnées de tuiles

    /**
     * Chemin de l'ennemi en coordonnées de pixels pour l'affichage graphique.
     */
    private final List<double[]> pixelPath; // Chemin en coordonnées de pixels

    /**
     * Référence à la carte du jeu.
     */
    private final Map map; // Référence à la carte du jeu

    /**
     * Récompense accordée au joueur pour avoir tué cet ennemi.
     */
    private final int reward; // Récompense pour avoir tué l'ennemi

    // Propriétés mutables

    /**
     * Vitesse de déplacement de l'ennemi.
     */
    private double movingSpeed; // Vitesse de déplacement de l'ennemi

    /**
     * Multiplicateur global pour la vitesse de déplacement des ennemis.
     * Ce multiplicateur affecte la vitesse de déplacement de toutes les instances d'Ennemy.
     */
    private static double baseSpeedMultiplier = 1.0; // Multiplicateur global pour la vitesse de déplacement

    /**
     * Coordonnée X actuelle de l'ennemi en pixels.
     */
    private double x; // Coordonnée X actuelle (en pixels)

    /**
     * Coordonnée Y actuelle de l'ennemi en pixels.
     */
    private double y; // Coordonnée Y actuelle (en pixels)

    /**
     * Étape actuelle dans le chemin de l'ennemi.
     */
    private int currentStep; // Étape actuelle dans le chemin

    /**
     * Indique si l'ennemi est actuellement empoisonné.
     */
    private boolean poisoned = false;

    /**
     * Temps restant avant la prochaine application des dégâts de poison.
     */
    private double poisonCooldown = 0;

    /**
     * Temps écoulé depuis la dernière application des dégâts de poison.
     */
    private double poisonTimer = 0; // Temps écoulé depuis la dernière application du poison

    /**
     * Dégâts infligés par le poison à chaque intervalle.
     */
    private double poisonDamage = 0; // Dégâts infligés par le poison

    /**
     * Constructeur de la classe Ennemy.
     *
     * @param PV          Points de vie de l'ennemi.
     * @param ATK         Points d'attaque de l'ennemi.
     * @param ATKSpeed    Vitesse d'attaque de l'ennemi.
     * @param range       Portée d'attaque de l'ennemi (en tuiles).
     * @param element     Type d'élément de l'ennemi.
     * @param movingSpeed Vitesse de déplacement de l'ennemi.
     * @param spawnTime   Temps d'apparition de l'ennemi dans la vague.
     * @param x           Coordonnée X initiale (en pixels).
     * @param y           Coordonnée Y initiale (en pixels).
     * @param map         Référence à la carte du jeu.
     * @param reward      Récompense pour avoir tué l'ennemi.
     * @param modeAttaque Mode d'attaque de l'ennemi.
     */
    public Ennemy(int PV, int ATK, double ATKSpeed, int range, Element element,
                 double movingSpeed, double spawnTime, double x, double y, Map map, int reward, ModeAttaque modeAttaque) {
        super(PV, ATK, ATKSpeed, range, element, null, modeAttaque);
        this.movingSpeed = movingSpeed * baseSpeedMultiplier; // Applique le multiplicateur global
        this.spawnTime = spawnTime;
        this.x = x;
        this.y = y;
        this.map = map;
        this.pixelPath = map.getPixelPath();
        this.path = map.getPath();
        this.currentStep = 0;
        this.reward = reward;
    }

    /**
     * Définit un nouveau multiplicateur global pour la vitesse de déplacement des ennemis.
     *
     * @param multiplier Le nouveau multiplicateur de vitesse.
     */
    public static void setBaseSpeedMultiplier(double multiplier) {
        baseSpeedMultiplier = multiplier;
    }

    /**
     * Retourne le multiplicateur global actuel pour la vitesse de déplacement des ennemis.
     *
     * @return Le multiplicateur de vitesse.
     */
    public static double getBaseSpeedMultiplier() {
        return baseSpeedMultiplier;
    }

    /**
     * Générer en partie avec l'IA
     * Met à jour la position de l'ennemi sur la carte en fonction de son déplacement.
     * 
     * Cette méthode gère le déplacement d'un ennemi sur son chemin défini (pixelPath)
     * et met à jour ses coordonnées (x, y) ainsi que sa case actuelle (Tile)
     * lorsque l'ennemi traverse une frontière de case.
     * Elle prend en compte la vitesse de déplacement de l'ennemi et le temps écoulé
     * depuis la dernière mise à jour pour calculer la progression.
     * 
     *
     * @param deltaTimeSec Le temps écoulé en secondes depuis la dernière mise à jour.
     */
    public void updatePosition(double deltaTimeSec) {
        if (currentStep >= pixelPath.size()) {
            return; // Ennemi a atteint la fin du chemin
        }

        // Calcul de la taille d'un carreau en pixels
        double cellSize = map.getCellSize();

        // Distance totale que l'ennemi peut parcourir cette frame
        double distanceToTravel = movingSpeed * cellSize * deltaTimeSec; // Distance en pixels

        // Tant qu'il reste de la distance à parcourir et des étapes à atteindre
        while (distanceToTravel > 0 && currentStep < pixelPath.size()) {
            // Récupérer la prochaine position cible en pixels
            double[] target = pixelPath.get(currentStep);
            double targetX = target[0];
            double targetY = target[1];

            // Calcul du vecteur directionnel et de la distance à la cible
            double dx = targetX - x;
            double dy = targetY - y;
            double distanceToTarget = Math.sqrt(dx * dx + dy * dy);

            if (distanceToTravel >= distanceToTarget) {
                // Atteint la prochaine étape
                x = targetX;
                y = targetY;
                currentStep++; // Avancer dans le chemin
                distanceToTravel -= distanceToTarget; // Réduire la distance restante
            } else {
                // Déplacement partiel
                x += distanceToTravel * dx / distanceToTarget;
                y += distanceToTravel * dy / distanceToTarget;
                distanceToTravel = 0; // Fin du déplacement pour cette frame
            }
        }

        // Calculer la case actuelle après mise à jour de la position
        int currentRow = (int) ((350 + 350 - y) / cellSize);
        int currentCol = (int) ((x - (350 - 350)) / cellSize);

        // Si l'ennemi change de case, mettre à jour sa position sur la carte
        Tile currentTile = map.getTile(currentRow, currentCol);
        if (!currentTile.equals(this.getPosition())) {
            this.setPosition(currentTile);
        }
    }

    /**
     * Retourne la coordonnée X actuelle de l'ennemi.
     *
     * @return La coordonnée X (en pixels).
     */
    public double getX() {
        return x;
    }

    /**
     * Retourne la coordonnée Y actuelle de l'ennemi.
     *
     * @return La coordonnée Y (en pixels).
     */
    public double getY() {
        return y;
    }

    /**
     * Retourne la vitesse de déplacement de l'ennemi.
     *
     * @return La vitesse de déplacement.
     */
    public double getMovingSpeed() {
        return movingSpeed;
    }

    /**
     * Retourne le temps d'apparition de l'ennemi dans la vague.
     *
     * @return Le temps d'apparition.
     */
    public double getSpawnTime() {
        return spawnTime;
    }

    /**
     * Vérifie si l'ennemi a atteint la fin de son chemin.
     *
     * @return True si l'ennemi a terminé son chemin, sinon false.
     */
    public boolean hasReachedEnd() {
        return currentStep >= pixelPath.size();
    }

    /**
     * Retourne le chemin en coordonnées de tuiles de l'ennemi.
     *
     * @return La liste des coordonnées de tuiles représentant le chemin.
     */
    public List<int[]> getPath() {
        return path;
    }

    /**
     * Retourne le chemin en coordonnées de pixels de l'ennemi.
     *
     * @return La liste des coordonnées de pixels représentant le chemin.
     */
    public List<double[]> getPixelPath() {
        return pixelPath;
    }

    /**
     * Retourne la récompense pour avoir tué l'ennemi.
     *
     * @return La récompense.
     */
    public int getReward() {
        return reward;
    }

    /**
     * Retourne l'étape actuelle dans le chemin de l'ennemi.
     *
     * @return L'étape actuelle.
     */
    public int getCurrentStep() {
        return currentStep;
    }

    /**
     * Définit la vitesse de déplacement de l'ennemi.
     *
     * @param movingSpeed La nouvelle vitesse de déplacement.
     */
    public void setMovingSpeed(double movingSpeed) {
        this.movingSpeed = movingSpeed;
    }

    /**
     * Vérifie si l'ennemi est actuellement empoisonné.
     *
     * @return True si l'ennemi est empoisonné, sinon false.
     */
    public boolean isPoisoned() {
        return poisoned;
    }

    /**
     * Applique un effet de poison à l'ennemi.
     *
     * @param damage   Dégâts infligés par le poison.
     * @param interval Intervalle de temps entre chaque application de dégâts.
     */
    public void applyPoison(double damage, double interval) {
        if (poisoned)
            return;

        poisoned = true;
        poisonCooldown = interval;
        poisonDamage = damage;
    }

    /**
     * Met à jour l'état du poison sur l'ennemi.
     *
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour.
     */
    public void updatePoison(double deltaTime) {
        if (!poisoned)
            return;

        poisonTimer += deltaTime;

        if (poisonTimer >= poisonCooldown) {
            this.takeDamage(poisonDamage); // Inflige les dégâts
            poisonTimer -= poisonCooldown; // Réinitialise le timer

            if (this.getPV() <= 0) {
                poisoned = false; // Arrête le poison si l'ennemi est mort
            }
        }
    }
}
