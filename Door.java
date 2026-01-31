package app;

public class Door {
    public double x, y;
    public boolean isVertical;
    
    public Door(double x, double y, boolean isVertical) {
        this.x = x; this.y = y; this.isVertical = isVertical;
    }
    
    public double getScaledX(double scale) { return x * scale; }
    public double getScaledY(double scale) { return y * scale; }
}
