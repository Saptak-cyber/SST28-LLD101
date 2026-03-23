// Another concrete implementation - Liskov Substitution Principle
public class FountainPen extends AbstractPen {
    
    public FountainPen(Ink ink) {
        super(ink, new StandardWritingStrategy());
    }

    public FountainPen(Ink ink, WritingStrategy writingStrategy) {
        super(ink, writingStrategy);
    }
}
