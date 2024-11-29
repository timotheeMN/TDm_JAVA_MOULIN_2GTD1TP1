import java.awt.Image;
import java.awt.geom.Rectangle2D;

/**
 * Represents the exit point in the game. The player must reach this point to win.
 */

public class ExitPoint extends SolidSprite {
    public ExitPoint(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    /**
     * Checks if the hero collides with the exit point.
     *
     * @param hero The hero object.
     * @return True if there is a collision, false otherwise.
     */

    public boolean checkCollision(DynamicSprite hero) {
        Rectangle2D.Double heroHitBox = new Rectangle2D.Double(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
        Rectangle2D.Double exitHitBox = new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        return heroHitBox.intersects(exitHitBox);
    }
}

