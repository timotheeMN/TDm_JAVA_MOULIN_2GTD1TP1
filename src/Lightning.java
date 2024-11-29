import java.awt.Image;

/**
 * Represents a lightning item that restores stamina when collected by the hero.
 */

public class Lightning extends SolidSprite {
    private final int staminaRestore = 50;

    public Lightning(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    /**
     * Gets the amount of stamina restored by this lightning item.
     *
     * @return The stamina restoration value.
     */

    public int getStaminaRestore() {
        return staminaRestore;
    }
}
