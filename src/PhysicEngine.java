import java.util.ArrayList;
import java.util.List;

/**
 * Handles the physics of the game, including movement and collision detection.
 */

public class PhysicEngine implements Engine {
    private final List<DynamicSprite> movingSpriteList;
    private List<SolidSprite> environment;
    private Playground playground;
    private RenderEngine renderEngine;

    public void setPlayground(Playground playground) {
        this.playground = playground;
    }

    public void addToMovingSpriteList(DynamicSprite sprite) {
        movingSpriteList.add(sprite);
    }

    public void setEnvironment(List<SolidSprite> environment) {
        this.environment = environment;
    }

    public void setRenderEngine(RenderEngine renderEngine) {this.renderEngine = renderEngine;}

    public PhysicEngine() {
        this.movingSpriteList = new ArrayList<>();
        this.environment = new ArrayList<>();
    }

    /**
     * Updates the physics engine. Handles sprite movement, collision detection,
     * and game state changes such as reaching the exit point.
     */

    @Override
    public void update() {
        int screenWidth = 400;
        int screenHeight = 600;

        for (DynamicSprite sprite : movingSpriteList) {
            // Attempt to move the sprite if possible
            sprite.moveIfPossible(new ArrayList<>(environment), screenWidth, screenHeight);

            // Check for collisions with lightning items
            if (playground != null) {
                playground.handleLightningCollisions(sprite, renderEngine);
            }

            // Refresh the render engine's render list
            if (renderEngine != null) {
                renderEngine.updateRenderList(playground.getSpriteList());
            }

            // Check for collision with the exit point
            if (playground != null && playground.getExitPoint() != null) {
                ExitPoint exit = playground.getExitPoint();
                if (exit.checkCollision(sprite)) {
                    showVictoryScreen();
                }
            }
        }
    }

    /**
     * Displays the victory screen when the game is completed.
     */

    private void showVictoryScreen() {
        VictoryPanel victoryPanel = new VictoryPanel();
        victoryPanel.setSize(renderEngine.getSize());
        renderEngine.addOverlay(victoryPanel);
        renderEngine.repaint();
    }
}