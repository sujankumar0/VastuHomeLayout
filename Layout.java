package app;
import java.util.*;

public class Layout {
    public String name, style;
    public double originalTotalArea;
    public List<Room> rooms = new ArrayList<>();
    public List<Door> doors = new ArrayList<>();
    public List<Window> windows = new ArrayList<>();
    public int bedrooms;
    public boolean hasMasterBathroom;
    private double plotLength, plotWidth, scaleFactor;
    
    public Layout(String name, String style, double totalArea, int bedrooms, int masterBath) {
        this.name = name; this.style = style; this.originalTotalArea = totalArea;
        this.bedrooms = bedrooms; this.hasMasterBathroom = masterBath == 1;
    }
    
    public void addRoom(Room room) { rooms.add(room); }
    public void addDoor(double x, double y, boolean isVertical) { doors.add(new Door(x, y, isVertical)); }
    public void addWindow(double x, double y, double width, boolean isVertical) { 
        windows.add(new Window(x, y, width, isVertical)); 
    }
    
    // 🔥 PERFECT SCALING FOR VISUAL APPEAL
 // In Layout.java - REPLACE scaleToFit method ONLY
   /* public void scaleToFit(double userLength, double userWidth) {
        this.plotLength = userLength;
        this.plotWidth = userWidth;
        
        double maxX = 0, maxY = 0;
        for (Room r : rooms) {
            maxX = Math.max(maxX, r.x + r.width);
            maxY = Math.max(maxY, r.y + r.height);
        }
        
        // MAKE ROOMS MUCH BIGGER - FIT PERFECTLY
        this.scaleFactor = Math.min(userLength/maxX, userWidth/maxY) * 1.2;
    }

    
    public double getScaleFactor() { return scaleFactor; }
    public double getPlotLength() { return plotLength; }
    public double getPlotWidth() { return plotWidth; }*/
 // In Layout.java - REMOVE scaleToFit method completely
 // Keep only these getters:
 public double getPlotLength() { return 50; }  // Default plot size
 public double getPlotWidth() { return 30; }
 public double getScaleFactor() { return 1.0; }  // Not used anymore

    public List<Room> getRooms() { return rooms; }
    public List<Door> getDoors() { return doors; }
    public List<Window> getWindows() { return windows; }
}
