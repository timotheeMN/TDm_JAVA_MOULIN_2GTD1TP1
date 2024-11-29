import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Represents a dynamic, movable sprite, such as the hero.
 * Supports running, stamina management, and direction-based animation.
 */

public class DynamicSprite extends SolidSprite {
    private boolean isRunning = false;
    private double baseSpeed = 5.0;
    private double runningSpeed = 10.0;
    private double speed = baseSpeed;
    private int stamina = 100;
    private final int maxStamina = 100;
    private final int baseStaminaCost = 2;
    private int staminaCost = baseStaminaCost;
    private final int staminaRegen = 1;
    private final int spriteSheetNumberOfColumn = 3;
    private int timeBetweenFrame = 200;
    private Direction direction;
    private WeatherEngine weatherEngine;
    private BufferedImage runningSpriteSheet;

    public DynamicSprite(BufferedImage walkingSpriteSheet, int x, int y, int width, int height) {
        super(walkingSpriteSheet, x, y, width, height);
    }

    public void setRunningSpriteSheet(BufferedImage runningSpriteSheet) {
        this.runningSpriteSheet = runningSpriteSheet;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Toggles the running state based on stamina availability.
     *
     * @param isRunning Whether the sprite is running.
     */

    public void setRunning(boolean isRunning) {
        if (stamina > 0) {
            this.isRunning = isRunning;
        } else {
            this.isRunning = false;
        }
        updateSpeed();
    }

    private void updateSpeed() {
        speed = isRunning ? runningSpeed : baseSpeed;
    }

    /**
     * Manages the sprite's stamina, including regeneration and depletion.
     */

    public void updateStamina() {
        if (weatherEngine != null && weatherEngine.isSnowstormActive()) {
            staminaCost = baseStaminaCost * 2;
        } else {
            staminaCost = baseStaminaCost;
        }

        if (isRunning) {
            stamina -= staminaCost;
            if (stamina < 0) {
                stamina = 0;
                isRunning = false;
                updateSpeed();
            }
        } else if (stamina < maxStamina) {
            stamina += staminaRegen;
            if (stamina > maxStamina) {
                stamina = maxStamina;
            }
        }
    }

    public void setStamina(int stamina) {
        this.stamina = Math.max(0, Math.min(stamina, maxStamina));
    }
    public int getStamina() {
        return stamina;
    }

    public void setWeatherEngine(WeatherEngine weatherEngine) {
        this.weatherEngine = weatherEngine;
    }


    /**
     * Draws the sprite on the screen, including its animation based on direction.
     *
     * @param g The graphics context.
     */

    @Override
    public void draw(Graphics g) {
        if (direction == null) {
            super.draw(g);
            return;
        }

        BufferedImage currentSpriteSheet = isRunning && runningSpriteSheet != null ? runningSpriteSheet : (BufferedImage) getImage();

        if (currentSpriteSheet instanceof BufferedImage spriteSheet) {
            long currentTime = System.currentTimeMillis();
            int index = (int) ((currentTime / timeBetweenFrame) % spriteSheetNumberOfColumn);
            int attitude = direction.getFrameLineNumber();

            int frameWidth = (int) getWidth();
            int frameHeight = (int) getHeight();

            int sourceX = index * frameWidth;
            int sourceY = attitude * frameHeight;
            int sourceX2 = sourceX + frameWidth;
            int sourceY2 = sourceY + frameHeight;

            g.drawImage(
                    spriteSheet,
                    (int) getX(), (int) getY(),
                    (int) (getX() + getWidth()), (int) (getY() + getHeight()),
                    sourceX, sourceY,
                    sourceX2, sourceY2,
                    null
            );
        } else {
            super.draw(g);
        }
    }


    private void move() {
        switch (direction) {
            case NORTH -> this.setY(this.getY() - speed);
            case SOUTH -> this.setY(this.getY() + speed);
            case WEST -> this.setX(this.getX() - speed);
            case EAST -> this.setX(this.getX() + speed);
        }
    }


    /**
     * Determines if the sprite can move without colliding with solid objects.
     *
     * @param environment The list of solid objects in the environment.
     * @param screenWidth The screen width.
     * @param screenHeight The screen height.
     * @return True if movement is possible, false otherwise.
     */

    private boolean isMovingPossible(ArrayList<Sprite> environment, int screenWidth, int screenHeight) {
        double futureX = this.getX();
        double futureY = this.getY();

        switch (direction) {
            case NORTH -> futureY -= speed;
            case SOUTH -> futureY += speed;
            case WEST -> futureX -= speed;
            case EAST -> futureX += speed;
        }

        if (futureX < 0 || futureX + getWidth() > screenWidth || futureY < 0 || futureY + getHeight() > screenHeight) {
            return false;
        }

        Rectangle2D.Double hitBox = new Rectangle2D.Double(
                futureX, futureY, getWidth(), getHeight()
        );

        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite && sprite != this) {
                Rectangle2D.Double otherHitBox = new Rectangle2D.Double(
                        sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight()
                );

                if (hitBox.intersects(otherHitBox)) {
                    return false;
                }
            }
        }

        return true;
    }


    public void moveIfPossible(ArrayList<Sprite> environment, int screenWidth, int screenHeight) {
        if (isMovingPossible(environment, screenWidth, screenHeight)) {
            move();
        }
    }

    public void setX(double x) {
        super.x = x;
    }

    public void setY(double y) {
        super.y = y;
    }
}

