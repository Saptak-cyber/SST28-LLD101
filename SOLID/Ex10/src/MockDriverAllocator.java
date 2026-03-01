public class MockDriverAllocator implements IDriverAllocator {
    private final String mockDriverId;
    
    public MockDriverAllocator(String mockDriverId) {
        this.mockDriverId = mockDriverId;
    }
    
    public String allocate(String studentId) {
        return mockDriverId;
    }
}
