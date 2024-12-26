package src;

import java.util.List;

public abstract class Ennemy extends Warrior {

    private final double MovingSpeed; // Vitesse de déplacement
    private final double SpawnTime; // Temps d'apparition
    private double x; // Coordonnée X (sert uniquement au mouvement sur la carte)
    private double y; // Coordonnée Y (idem que x)
    private List<int[]> path; // Chemin que suit l'ennemi
    private int currentStep = 0; // Étape actuelle sur le chemin

    /**
     * Constructeur pour la classe Ennemy.
     *
     * @param PV          Points de vie.
     * @param ATK         Points d'attaque.
     * @param ATKSpeed    Vitesse d'attaque.
     * @param Range       Portée.
     * @param element     Élément de l'ennemi.
     * @param position    Position initiale.
     * @param modeAttaque Mode d'attaque.
     * @param MovingSpeed Vitesse de déplacement.
     * @param SpawnTime   Temps d'apparition.
     * @param x           Coordonnée X initiale.
     * @param y           Coordonnée Y initiale.
     * 
     */
    public Ennemy(int PV, int ATK, double ATKSpeed, int Range, Element element, Tile position, ModeAttaque modeAttaque,
            double MovingSpeed, double SpawnTime, double x, double y) {
        super(PV, ATK, ATKSpeed, Range, element, position, modeAttaque);
        this.MovingSpeed = MovingSpeed;
        this.SpawnTime = SpawnTime;
        this.x = x;
        this.y = y;
    }

    public void setPath(List<int[]> path) {
        this.path = path;
    }
    //TODO : A fortement optimiser car hardcoder de fou
    public void updatePosition(double deltaTimeSec, Map map) {
        if (currentStep >= path.size()) {
            return; // L'ennemi a atteint la fin du chemin
        }

        // Convertir la prochaine case du chemin en coordonnées en pixels
        int[] nextTile = path.get(currentStep);

        // Convertir les indices de la case en coordonnées pixels
        double targetX = convertToPixel(nextTile[1], true, map); // Colonne -> X
        double targetY = convertToPixel(nextTile[0], false, map); // Ligne -> Y

        // Calculer la distance que l'ennemi peut parcourir pendant cette frame
        double distanceToTravel = MovingSpeed; // TODO : Pourquoi cela ne fonctionne pas ?? double distanceToTravel =
                                               // MovingSpeed * deltaTimeSec;

        // Calculer le vecteur vers la prochaine case
        double dx = targetX - x;
        double dy = targetY - y;
        double distanceToTarget = Math.sqrt(dx * dx + dy * dy);

        if (distanceToTravel >= distanceToTarget) {
            // Atteint la case suivante
            x = targetX;
            y = targetY;
            currentStep++; // Passe à la case suivante
        } else {
            // Déplacement partiel vers la prochaine case
            x += distanceToTravel * dx / distanceToTarget;
            y += distanceToTravel * dy / distanceToTarget;
        }
    }

    private double convertToPixel(int tileIndex, boolean isColumn, Map map) {
        // Centre de la carte en pixels
        double centerX = 350;
        double centerY = 350;
        double halfLength = 350; // Taille visuelle de la carte (assumez qu'elle est carrée)

        // Taille d'une cellule (calculée dynamiquement en fonction des dimensions de la
        // carte)
        double cellSize = Math.min(2 * halfLength / map.getRows(), 2 * halfLength / map.getCols());

        // Calcul en fonction de si c'est une colonne (X) ou une ligne (Y)
        if (isColumn) {
            return centerX - halfLength + tileIndex * cellSize + cellSize / 2;
        } else {
            return centerY + halfLength - tileIndex * cellSize - cellSize / 2;
        }
    }

    // Getter pour MovingSpeed
    public double getMovingSpeed() {
        return MovingSpeed;
    }

    // Getter pour SpawnTime
    public double getSpawnTime() {
        return SpawnTime;
    }

    // Getter pour x
    public double getX() {
        return x;
    }

    // Setter pour x
    public void setX(int x) {
        this.x = x;
    }

    // Getter pour y
    public double getY() {
        return y;
    }

    // Setter pour y
    public void setY(int y) {
        this.y = y;
    }

    // Vérifie si l'ennemi a atteint la fin du chemin
    public boolean hasReachedEnd() {
        return currentStep >= path.size();
    }
}
