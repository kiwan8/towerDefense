package src;

import java.io.*;
import java.util.*;

public class Level {

    private Map map;               // Carte associée au niveau
    private List<Wave> waves;      // Liste des vagues du niveau

    /**
     * Constructeur de la classe Level.
     */
    public Level(String filepath) throws GameExceptions.GameException, IOException{ // TODO : Ajouter un paramètre pour load avec un filePath 
        this.waves = new ArrayList<>();
        loadLevel(filepath);
    }

    /**
     * Charge un niveau à partir d'un fichier de niveau.
     *
     * @param filePath Chemin vers le fichier de niveau.
     * @throws IOException Si une erreur de lecture se produit.
     */
    private void loadLevel(String filePath) throws GameExceptions.GameException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Lire la première ligne pour charger la carte
            if ((line = reader.readLine()) != null) {
                this.map = new Map("resources/maps/" + line + ".mtp");
            }

            // Lire les lignes suivantes pour charger les vagues
            while ((line = reader.readLine()) != null) {
                Wave wave = new Wave(this);
                wave.loadWave("resources/waves/" + line + ".wve");
                waves.add(wave);
            }
        }
    }

    /**
     * Récupère la carte associée au niveau.
     *
     * @return La carte du niveau.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Récupère la liste des vagues associées au niveau.
     *
     * @return Liste des vagues.
     */
    public List<Wave> getWaves() {
        return waves;
    }

    /**
     * Affiche les informations du niveau pour vérification.
     */
    public void displayLevelInfo() {
        System.out.println("Carte du niveau : " + map);
        System.out.println("Nombre de vagues : " + waves.size());
        for (int i = 0; i < waves.size(); i++) {
            System.out.println("Vague " + (i + 1) + " : " + waves.get(i).getEnemies().size() + " ennemis");
        }
    }

}
