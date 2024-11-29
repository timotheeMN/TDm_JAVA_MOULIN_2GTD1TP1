import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.io.InputStreamReader;

/**
 * Manages the game's map, including static elements (trees, grass, rocks),
 * lightning items, and the exit point.
 */

public class Playground {
    private final ArrayList<Sprite> environment = new ArrayList<>();
    private final ArrayList<Lightning> lightnings = new ArrayList<>();
    private ExitPoint exitPoint;

    public Playground(String pathName) {
        try {
            final Image imageTree = ImageIO.read(getClass().getResource("/img/arbre.png"));
            final Image imageGrass = ImageIO.read(getClass().getResource("/img/grass.png"));
            final Image imageRock = ImageIO.read(getClass().getResource("/img/dragon.png"));
            final Image imageLightning = ImageIO.read(getClass().getResource("/img/éclair.png"));
            final Image imageExit = ImageIO.read(getClass().getResource("/img/exit.png"));

            final double imageTreeWidth = imageTree.getWidth(null);
            final double imageTreeHeight = imageTree.getHeight(null);
            final double imageGrassWidth = imageGrass.getWidth(null);
            final double imageGrassHeight = imageGrass.getHeight(null);
            final double imageRockWidth = imageRock.getWidth(null);
            final double imageRockHeight = imageRock.getHeight(null);
            final double imageLightningWidth = imageLightning.getWidth(null);
            final double imageLightningHeight = imageLightning.getHeight(null);

            // Parse the map file
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(pathName)));
            String line = bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;

            while (line != null) {
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    // Create objects based on character type
                    switch (element) {
                        case 'T' -> environment.add(new SolidSprite(imageTree, columnNumber * imageTreeWidth, lineNumber * imageTreeHeight, imageTreeWidth, imageTreeHeight));
                        case ' ' -> environment.add(new Sprite(imageGrass, columnNumber * imageGrassWidth, lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight));
                        case 'R' -> environment.add(new SolidSprite(imageRock, columnNumber * imageRockWidth, lineNumber * imageRockHeight, imageRockWidth, imageRockHeight));
                        case 'L' -> lightnings.add(new Lightning(imageLightning, columnNumber * imageLightningWidth, lineNumber * imageLightningHeight, imageLightningWidth, imageLightningHeight));
                    }
                    columnNumber++;
                }
                columnNumber = 0;
                lineNumber++;
                line = bufferedReader.readLine();
            }
            // Place the exit point on the map
            exitPoint = new ExitPoint(imageExit, 300, 400, 32, 32);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ExitPoint getExitPoint() {
        return exitPoint;
    }
    public void activateExit(RenderEngine renderEngine) {
        renderEngine.addToRenderList(exitPoint);
        environment.add(exitPoint);
    }

    /**
     * Filters and retrieves all solid sprites (e.g., trees, rocks) in the environment.
     *
     * @return A list of solid sprites.
     */

    public List<SolidSprite> getSolidSpriteList() {
        List<SolidSprite> solidSpriteList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite solidSprite) {
                solidSpriteList.add(solidSprite);
            }
        }
        return solidSpriteList;
    }

    /**
     * Retrieves all displayable objects in the playground, including lightnings.
     *
     * @return A list of displayable objects.
     */

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add(sprite);
        }
        displayableArrayList.addAll(lightnings);
        return displayableArrayList;
    }

    /**
     * Handles collisions between the hero and lightning items. If a collision
     * is detected, the hero regains stamina and the lightning is removed.
     *
     * @param hero The hero object.
     * @param renderEngine The engine responsible for rendering updates.
     */

    public void handleLightningCollisions(DynamicSprite hero, RenderEngine renderEngine) {
        Iterator<Lightning> iterator = lightnings.iterator();

        while (iterator.hasNext()) {
            Lightning lightning = iterator.next();
            Rectangle2D.Double heroHitBox = new Rectangle2D.Double(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
            Rectangle2D.Double lightningHitBox = new Rectangle2D.Double(lightning.getX(), lightning.getY(), lightning.getWidth(), lightning.getHeight());

            if (heroHitBox.intersects(lightningHitBox)) {
                // Restore stamina and remove the lightning
                hero.setStamina(Math.min(hero.getStamina() + lightning.getStaminaRestore(), 100));
                iterator.remove();
                renderEngine.updateRenderList(getSpriteList());
            }
        }
    }

}

