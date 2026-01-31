package app;
import java.awt.Color;

public class Room {
    public String name, type;
    public double x, y, width, height;
    
    public Room(String name, double x, double y, double width, double height, String type) {
        this.name = name; this.x = x; this.y = y; this.width = width; this.height = height; this.type = type;
    }
    
    public double getScaledX(double scale) { return x * scale; }
    public double getScaledY(double scale) { return y * scale; }
    public double getScaledWidth(double scale) { return width * scale; }
    public double getScaledHeight(double scale) { return height * scale; }
    
    public Color getColor() {
        if (type.contains("Pooja")) return new Color(255, 200, 100);
        else if (type.contains("Bedroom")) return new Color(173, 216, 230);
        else if (type.contains("Kitchen")) return new Color(255, 218, 185);
        else if (type.contains("Bathroom") || type.contains("Bath")) return new Color(221, 160, 221);
        else if (type.contains("Living")) return new Color(152, 251, 152);
        else if (type.contains("Dining")) return new Color(255, 250, 205);
        else return Color.LIGHT_GRAY;
    }
}
