package src;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L; 

    private int argent; // Argent du joueur
    private int HP;     // Points de vie du joueur

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

    public void takeDamage(int damage) {
        this.HP -= damage;
        if (this.HP < 0) {
            this.HP = 0;
        }
    }
}
