package app;

public class Window {
    public double x, y, width;
    public boolean isVertical;
    
    public Window(double x, double y, double width, boolean isVertical) {
        this.x = x; this.y = y; this.width = width; this.isVertical = isVertical;
    }
    
    public double getScaledX(double scale) { return x * scale; }
    public double getScaledY(double scale) { return y * scale; }
    public double getScaledWidth(double scale) { return width * scale; }
}
