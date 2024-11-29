import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame displayZoneFrame = new JFrame("Dungeon Crawler");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RenderEngine renderEngine = new RenderEngine();
        PhysicEngine physicEngine = new PhysicEngine();
        WeatherEngine weatherEngine = new WeatherEngine();

        BufferedImage walkingSpriteSheet  = ImageIO.read(new File("C:/Users/Utilisateur/Dropbox/Timo/Scolaire_Timo/4 ENSEA/2eme_année/Java/TD/FISE_2024_2025_Dungeon_Crawler-master/img/myhero.png"));
        BufferedImage runningSpriteSheet = ImageIO.read(new File("C:/Users/Utilisateur/Dropbox/Timo/Scolaire_Timo/4 ENSEA/2eme_année/Java/TD/FISE_2024_2025_Dungeon_Crawler-master/img/myherorun.png"));

        DynamicSprite hero = new DynamicSprite(walkingSpriteSheet, 10, 10, 31, 32);
        hero.setDirection(Direction.EAST);
        hero.setRunningSpriteSheet(runningSpriteSheet);
        hero.setWeatherEngine(weatherEngine);

        Playground playground = new Playground("C:/Users/Utilisateur/Dropbox/Timo/Scolaire_Timo/4 ENSEA/2eme_année/Java/TD/FISE_2024_2025_Dungeon_Crawler-master/img/map.txt");
        renderEngine.setRenderList(playground.getSpriteList()); // Render all map elements
        renderEngine.setHero(hero); // Set the hero for rendering
        GameEngine gameEngine = new GameEngine(hero);

        physicEngine.setEnvironment(playground.getSolidSpriteList());
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setPlayground(playground);
        physicEngine.setRenderEngine(renderEngine);

        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer weatherTimer = new Timer(500, (time) -> {weatherEngine.update();renderEngine.toggleSnowstorm(weatherEngine.isSnowstormActive());});
        Timer exitTimer = new Timer(15000, (time) -> playground.activateExit(renderEngine));


        exitTimer.start();
        renderTimer.start();
        physicTimer.start();
        gameTimer.start();
        weatherTimer.start();
        exitTimer.setRepeats(false);

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.setVisible(true);
    }
}