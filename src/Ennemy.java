package src;

public abstract class Ennemy extends Warrior {

    private final double MovingSpeed; // Vitesse de déplacement
    private final double SpawnTime;   // Temps d'apparition
    private int x;              // Coordonnée X
    private int y;              // Coordonnée Y

    /**
     * Constructeur pour la classe Ennemy.
     *
     * @param PV         Points de vie.
     * @param ATK        Points d'attaque.
     * @param ATKSpeed   Vitesse d'attaque.
     * @param Range      Portée.
     * @param element    Élément de l'ennemi.
     * @param position   Position initiale.
     * @param modeAttaque Mode d'attaque.
     * @param MovingSpeed Vitesse de déplacement.
     * @param SpawnTime   Temps d'apparition.
     * @param x          Coordonnée X initiale.
     * @param y          Coordonnée Y initiale.
     */
    public Ennemy(int PV, int ATK, double ATKSpeed, int Range, Element element, Tile position, ModeAttaque modeAttaque, 
                  double MovingSpeed, double SpawnTime, int x, int y) {
        super(PV, ATK, ATKSpeed, Range, element, position, modeAttaque);
        this.MovingSpeed = MovingSpeed;
        this.SpawnTime = SpawnTime;
        this.x = x;
        this.y = y;
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
    public int getX() {
        return x;
    }

    // Setter pour x
    public void setX(int x) {
        this.x = x;
    }

    // Getter pour y
    public int getY() {
        return y;
    }

    // Setter pour y
    public void setY(int y) {
        this.y = y;
    }
}
