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

    public void updatePosition(double deltaTimeSec, Map map) {
        if (currentStep >= path.size()) {
            return; // L'ennemi a atteint la fin du chemin
        }
    
        // Convertir la prochaine case du chemin en coordonnées en pixels
        int[] nextTile = path.get(currentStep);
        System.out.println("le path :"+path.toString());
        double targetX = convertToPixel(nextTile[1], map.getCols(), map); // Colonne -> X
        double targetY = convertToPixel(nextTile[0], map.getRows(), map); // Ligne -> Y
    
        // Calculer la distance que l'ennemi peut parcourir pendant cette frame
        double distanceToTravel = MovingSpeed * deltaTimeSec;
    
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

    private double convertToPixel(int tileIndex, int gridSize, Map map) {
        double centerX = 350; // Centre de la carte
        double centerY = 350;
        double halfLength = 350; // Taille visuelle de la carte
        double cellSize = Math.min(2 * halfLength / map.getRows(), 2 * halfLength / map.getCols());
    
        return centerX - halfLength + tileIndex * cellSize + cellSize / 2;
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
}
