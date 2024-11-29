import java.awt.Graphics;
import java.awt.Image;

public class Sprite implements Displayable {
    private Image image;
    protected double x;
    protected double y;
    private double width;
    private double height;

    public Sprite(Image image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    protected void setX(double x) {
        this.x = x;
    }

    protected void setY(double y) {
        this.y = y;
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(
                image,
                (int) x,
                (int) y,
                (int) width,
                (int) height,
                null
        );
    }
}
