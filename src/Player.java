package src;

import java.io.Serializable;

/**
 * La classe Player représente le joueur dans le jeu.
 * Elle gère l'argent et les points de vie du joueur.
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * L'argent du joueur.
     */
    private int argent;

    /**
     * Les points de vie du joueur.
     */
    private int HP;

    /**
     * Constructeur de la classe Player.
     *
     * @param argent L'argent initial du joueur.
     * @param HP     Les points de vie initiaux du joueur.
     */
    public Player(int argent, int HP) {
        this.argent = argent;
        this.HP = HP;
    }

    /**
     * Retourne l'argent du joueur.
     *
     * @return L'argent du joueur.
     */
    public int getArgent() {
        return argent;
    }

    /**
     * Définit l'argent du joueur.
     *
     * @param argent Le nouvel argent du joueur.
     */
    public void setArgent(int argent) {
        this.argent = argent;
        if (this.argent < 0) {
            this.argent = 0;
        }
    }

    /**
     * Retourne les points de vie du joueur.
     *
     * @return Les points de vie du joueur.
     */
    public int getHP() {
        return HP;
    }

    /**
     * Définit les points de vie du joueur.
     *
     * @param HP Les nouveaux points de vie du joueur.
     */
    public void setHP(int HP) {
        this.HP = HP;
        if (this.HP < 0) {
            this.HP = 0;
        }
    }

    /**
     * Vérifie si le joueur est encore en vie.
     *
     * @return true si le joueur est en vie, false sinon.
     */
    public boolean isAlive() {
        return HP > 0;
    }

    /**
     * Inflige des dégâts au joueur.
     *
     * @param damage Les dégâts à infliger.
     */
    public void takeDamage(double damage) {
        this.HP -= damage;
        if (this.HP < 0) {
            this.HP = 0;
        }
    }
}