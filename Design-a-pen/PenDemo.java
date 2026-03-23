// Demo class to show the usage
public class PenDemo {
    public static void main(String[] args) {
        System.out.println("=== Pen System Demo ===\n");
        
        // Create ink
        Ink blueInk = new StandardInk("Blue", 100);
        Ink redInk = new StandardInk("Red", 50);
        
        // Create a ballpoint pen
        AbstractPen myPen = new BallpointPen(blueInk);
        
        System.out.println("1. Trying to write with closed pen:");
        myPen.write("Hello World");
        
        System.out.println("\n2. Opening the pen:");
        myPen.start();
        
        System.out.println("\n3. Writing with open pen:");
        myPen.write("Hello World");
        System.out.println("Ink remaining: " + myPen.getInkLevel());
        
        System.out.println("\n4. Writing more text:");
        myPen.write("SOLID Principles are great!");
        System.out.println("Ink remaining: " + myPen.getInkLevel());
        
        System.out.println("\n5. Closing the pen:");
        myPen.close();
        
        System.out.println("\n6. Refilling with red ink:");
        myPen.refill(redInk);
        
        System.out.println("\n7. Opening and writing with red ink:");
        myPen.start();
        myPen.write("Now writing in red!");
        
        System.out.println("\n8. Creating a fountain pen:");
        AbstractPen fountainPen = new FountainPen(new StandardInk("Black", 80));
        fountainPen.start();
        fountainPen.write("Elegant fountain pen writing");
        
        System.out.println("\n=== Demo Complete ===");
    }
}
