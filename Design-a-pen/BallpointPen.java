// Concrete implementation - Liskov Substitution Principle
public class BallpointPen extends AbstractPen {
    
    public BallpointPen(Ink ink) {
        super(ink, new StandardWritingStrategy());
    }

    public BallpointPen(Ink ink, WritingStrategy writingStrategy) {
        super(ink, writingStrategy);
    }
}
