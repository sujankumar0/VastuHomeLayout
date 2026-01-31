package app;
import javax.swing.*;
import java.awt.*;

public class LayoutCanvas extends JPanel {
    private Layout currentLayout;
    private double plotLength = 40, plotWidth = 30;
    
    public void setCurrentLayoutWithDimensions(Layout layout, double length, double width) {
        this.currentLayout = layout;
        this.plotLength = length;
        this.plotWidth = width;
        repaint();
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        setBackground(Color.WHITE);

        if (currentLayout == null || currentLayout.getRooms().isEmpty()) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Arial", Font.BOLD, 22));
            g2d.drawString("Select a layout to view floor plan", 120, 300);
            return;
        }

        // ================= PLOT BORDER =================
        int borderX = 100;
        int borderY = 120;
        int borderW = 520;
        int borderH = 380;

        // Draw green plot
        g2d.setColor(new Color(0, 230, 0));
        g2d.fillRect(borderX, borderY, borderW, borderH);

        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(borderX, borderY, borderW, borderH);

        // ================= SCALING =================
        // Logical grid is ALWAYS 100 x 100
        double scaleX = borderW / 100.0;
        double scaleY = borderH / 100.0;

        // ================= DRAW ROOMS =================
        for (Room room : currentLayout.getRooms()) {

            int x = borderX + (int) (room.x * scaleX);
            int y = borderY + (int) (room.y * scaleY);
            int w = (int) (room.width * scaleX);
            int h = (int) (room.height * scaleY);

            // Fill room
            g2d.setColor(room.getColor());
            g2d.fillRect(x, y, w, h);

            // Room border
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x, y, w, h);

            // ================= ROOM NAME (CENTERED) =================
            Font font = new Font("Arial", Font.BOLD, 14);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();

            int textWidth = fm.stringWidth(room.name);
            int textHeight = fm.getAscent();

            if (w > textWidth + 10 && h > textHeight + 10) {
                int textX = x + (w - textWidth) / 2;
                int textY = y + (h + textHeight) / 2 - 4;

                g2d.setColor(Color.BLACK);
                g2d.drawString(room.name, textX, textY);
            }
        }

        // ================= TITLE =================
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString(currentLayout.name, borderX, borderY - 70);

        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString(
            "Plot: " + (int) plotLength + " x " + (int) plotWidth + " ft",
            borderX, borderY - 35
        );
    }
}
