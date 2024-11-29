import java.util.Random;

/**
 * The WeatherEngine handles weather-related effects in the game, such as snowstorms.
 * It periodically toggles weather conditions based on random probabilities.
 */

public class WeatherEngine implements Engine {
    private boolean snowstormActive = false;
    private final Random random = new Random();

    public boolean isSnowstormActive() {
        return snowstormActive;
    }

    public void toggleSnowstorm() {
        snowstormActive = !snowstormActive;
    }

    /**
     * Updates the weather engine. Occasionally triggers a snowstorm based on probability.
     */

    @Override
    public void update() {
        if (random.nextInt(1000) < 25) {
            toggleSnowstorm();
            System.out.println(snowstormActive ? "Tempête de neige activée !" : "Tempête de neige terminée !");
        }
    }
}

