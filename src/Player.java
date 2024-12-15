package src;

public class Player {
    private int argent; // Argent du joueur
    private int HP; // Vie du joueur

    public Player(int argent, int HP) { // Constructeur
        this.argent = argent;
        this.HP = HP;
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public boolean isAlive() { // Vérifie si le joueur est encore en vie
        return HP > 0;
    }
}