public class MockPaymentGateway implements IPaymentGateway {
    private final String mockTxnId;
    
    public MockPaymentGateway(String mockTxnId) {
        this.mockTxnId = mockTxnId;
    }
    
    public String charge(String studentId, double amount) {
        return mockTxnId;
    }
}
