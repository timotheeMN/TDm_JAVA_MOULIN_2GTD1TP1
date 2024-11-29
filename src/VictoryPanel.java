import javax.swing.*;
import java.awt.*;

/**
 * Displays a victory screen overlay with a congratulatory message.
 */

public class VictoryPanel extends JPanel {
    private final String mainMessage = "Félicitations";
    private final String subMessage = "Le froid ne tue pas !";

    public VictoryPanel() {
        setOpaque(false);
    }

    /**
     * Draws the victory message and background overlay.
     *
     * @param g The graphics context.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw main message
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.setColor(Color.WHITE);
        drawCenteredText(g2d, mainMessage, getWidth(), getHeight() / 2 - 50);

        // Draw sub-message
        g2d.setFont(new Font("Arial", Font.PLAIN, 28));
        g2d.setColor(new Color(200, 200, 200));
        drawCenteredText(g2d, subMessage, getWidth(), getHeight() / 2 + 10);
    }

    private void drawCenteredText(Graphics2D g2d, String text, int w, int h) {
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (w - metrics.stringWidth(text)) / 2;
        int y = h + metrics.getAscent() / 2;
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, x + 2, y + 2);
        g2d.setColor(Color.WHITE); // Main text color
        g2d.drawString(text, x, y);
    }
}

