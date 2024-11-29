import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The RenderEngine is responsible for rendering all game objects and visual effects.
 * It manages the display list, overlays, and weather effects like snowstorms.
 */

public class RenderEngine extends JPanel implements Engine {
    private List<Displayable> renderList; // List of objects to render
    private DynamicSprite hero; // Reference to the hero sprite
    public RenderEngine() {
        renderList = new ArrayList<>();
    }
    private List<Point> snowflakes = new ArrayList<>(); // Positions of snowflakes during snowstorms
    private final Random random = new Random();
    private boolean snowstormActive = false;// Indicates if a snowstorm is active
    private List<JPanel> overlays = new ArrayList<>();

    public void addOverlay(JPanel overlay) {
        overlays.add(overlay);
    }

    /**
     * Toggles the snowstorm effect.
     *
     * @param active True to activate the snowstorm, false to deactivate.
     */

    public void toggleSnowstorm(boolean active) {
        snowstormActive = active;
        if (active) {
            generateSnowflakes();
        } else {
            snowflakes.clear();
        }
    }

    /**
     * Generates a new set of snowflake positions for the snowstorm.
     */

    private void generateSnowflakes() {
        snowflakes.clear();
        for (int i = 0; i < 100; i++) {
            snowflakes.add(new Point(random.nextInt(400), random.nextInt(600)));
        }
    }

    public void setHero(DynamicSprite hero) {
        this.hero = hero;
    }

    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }
    public void setRenderList(List<Displayable> renderList) {
        this.renderList = renderList;
    }

    public void updateRenderList(List<Displayable> updatedRenderList) {
        this.renderList = updatedRenderList;
        repaint();
    }


    @Override
    public void update() {
        repaint();
    }

    /**
     * Renders all game objects and effects on the screen.
     *
     * @param g The graphics context.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Displayable displayable : renderList) {
            if (displayable != hero) {
                displayable.draw(g);
            }
        }
        if (hero != null) {
            hero.draw(g);
        }
        if (hero != null) {
            drawStaminaBar(g);
        }
        for (JPanel overlay : overlays) {
            overlay.paint(g);
        }

        // Draw snowflakes during a snowstorm
        if (snowstormActive) {
            g.setColor(Color.WHITE);
            for (Point snowflake : snowflakes) {
                g.fillOval(snowflake.x, snowflake.y, 5, 5);
            }

            // Move snowflakes downward and reset them when they exit the screen
            for (Point snowflake : snowflakes) {
                snowflake.y += 2;
                if (snowflake.y > 600) {
                    snowflake.y = 0;
                    snowflake.x = random.nextInt(400);
                }
            }
        }
    }

    /**
     * Draws the stamina bar at the bottom center of the screen.
     *
     * @param g The graphics context.
     */

    private void drawStaminaBar(Graphics g) {
        int maxStamina = 100;
        int stamina = hero.getStamina();
        int barWidth = 150;
        int barHeight = 15;
        int xPosition = (400 - barWidth) / 2;
        int yPosition = 600 - barHeight - 40;

        // Draw background
        g.setColor(Color.GRAY);
        g.fillRect(xPosition, yPosition, barWidth, barHeight);

        // Draw filled portion
        g.setColor(Color.GREEN);
        g.fillRect(xPosition, yPosition, (stamina * barWidth) / maxStamina, barHeight);

        // Draw border
        g.setColor(Color.BLACK);
        g.drawRect(xPosition, yPosition, barWidth, barHeight);

        // Draw stamina text
        String staminaText = stamina + " / " + maxStamina;
        int textWidth = g.getFontMetrics().stringWidth(staminaText);
        int textHeight = g.getFontMetrics().getHeight();
        g.setColor(Color.BLACK);
        g.drawString(staminaText, xPosition + (barWidth - textWidth) / 2, yPosition + (barHeight + textHeight) / 2 - 4);
    }

}
