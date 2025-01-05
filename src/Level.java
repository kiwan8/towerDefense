package src;

import java.io.*;
import java.util.*;

/**
 * Classe représentant un niveau dans le jeu.
 * Gère le chargement de la carte et des vagues d'ennemis à partir de fichiers de configuration.
 */
public class Level {

    /**
     * Carte associée au niveau.
     */
    private Map map; // Carte associée au niveau

    /**
     * Liste des vagues d'ennemis du niveau.
     */
    private List<Wave> waves; // Liste des vagues du niveau

    /**
     * Chemin vers le fichier de configuration du niveau.
     */
    private final String LevelfilePath;

    /**
     * Retourne le chemin vers le fichier de configuration du niveau.
     *
     * @return Le chemin du fichier de niveau.
     */
    public String getLevelfilePath() {
        return LevelfilePath;
    }

    /**
     * Constructeur de la classe Level.
     * Initialise le niveau en chargeant la carte et les vagues à partir du fichier spécifié.
     *
     * @param filepath Chemin vers le fichier de configuration du niveau.
     * @throws GameException Si la validation de la carte échoue ou si une erreur spécifique au jeu survient.
     * @throws IOException    Si une erreur de lecture de fichier survient.
     */
    public Level(final String filepath) throws GameExceptions.GameException, IOException { 
        this.waves = new ArrayList<>();
        this.LevelfilePath = filepath;
        loadLevel(filepath);
    }

    /**
     * Charge un niveau à partir d'un fichier de configuration.
     * <p>
     * La première ligne du fichier correspond au nom de la carte. Les lignes suivantes correspondent aux fichiers de vagues.
     * </p>
     *
     * @param filePath Chemin vers le fichier de configuration du niveau.
     * @throws GameException Si la validation de la carte échoue ou si une erreur spécifique au jeu survient.
     * @throws IOException    Si une erreur de lecture de fichier survient.
     */
    private void loadLevel(String filePath) throws GameExceptions.GameException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Lire la première ligne pour charger la carte
            if ((line = reader.readLine()) != null) {
                this.map = new Map("resources/maps/" + line + ".mtp", filePath);
            }

            // Lire les lignes suivantes pour charger les vagues
            while ((line = reader.readLine()) != null) {
                String wavePath = "resources/waves/" + line + ".wve";
                Wave wave = new Wave(this, wavePath);
                wave.loadWave(wavePath);
                waves.add(wave);
            }
        }
    }

    /**
     * Retourne la carte associée au niveau.
     *
     * @return La carte du niveau.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Retourne la liste des vagues associées au niveau.
     *
     * @return Liste des vagues.
     */
    public List<Wave> getWaves() {
        return waves;
    }
}
