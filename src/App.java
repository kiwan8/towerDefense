package src;

import java.io.IOException;
import src.GameExceptions.GameException;

/**
 * Classe principale de l'application.
 * Sert de point d'entrée pour lancer le jeu.
 */
public class App
{
    /**
     * Méthode principale de l'application.
     *
     * @param args Les arguments de la ligne de commande.
     * @throws GameException Si une erreur spécifique au jeu survient lors du lancement.
     * @throws IOException    Si une erreur d'entrée/sortie survient lors du lancement.
     */
    public static void main(String[] args) throws GameException, IOException
    {
        Game g = new Game();
        g.launch();
    }
}
