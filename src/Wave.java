package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import src.GameExceptions.*;

public class Wave{
    private List<WavePair> tuple;
    private String filePath;

    public Wave(String filePath) throws IOException, InvalidWaveFileException { // Constructeur
        this.filePath = filePath;
        this.tuple = new ArrayList<>();
        loadWaveFromFile();
    }

    private void loadWaveFromFile() throws IOException, InvalidWaveFileException { // Charge les vagues depuis un fichier
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {  
            String ligne;
            while ((ligne = reader.readLine()) != null) {  // Lit chaque ligne du fichier
                String[] partie = ligne.split("\\|");
                if (partie.length != 2) {
                    throw new InvalidWaveFileException("format de ligne invalide dans le fichier de vague");
                }
                try {
                    double temps = Double.parseDouble(partie[0].trim()); // Temps de spawn dans la vague
                    String typeEnemie = partie[1].trim();                // Type d'ennemi
                    tuple.add(new WavePair(temps, typeEnemie));
                } catch (NumberFormatException e) {
                    throw new InvalidWaveFileException("format de temps invalide dans le fichier de vague");
                }
            }
        }
    }

    public List<WavePair> getPairs() {
        return new ArrayList<>(tuple);
    }

    private static class WavePair {
        private final double time;
        private final String enemyType;

        public WavePair(double time, String enemyType) {
            this.time = time;
            this.enemyType = enemyType;
        }

        public double getTime() {
            return time;
        }

        public String getEnemyType() {
            return enemyType;
        }
    }

    

}
