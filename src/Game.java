package src;
public class Game {
    //test sdqdqs
    // Méthode principale pour lancer le jeu
    public void launch() {
        init();
        long previousTime = System.currentTimeMillis();
        while (isGameRunning()) {
            long currentTime = System.currentTimeMillis();
            double deltaTimeSec = (double) (currentTime - previousTime) / 1000;
            previousTime = currentTime;
            update(deltaTimeSec);
        }
    }

    // Vérifie si le jeu est encore en cours d'exécution
    private boolean isGameRunning() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Initialise le jeu
    private void init() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Met à jour l'état du jeu en fonction du temps écoulé
    private void update(double deltaTimeSec) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
