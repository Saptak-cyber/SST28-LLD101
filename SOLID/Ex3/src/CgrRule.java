public class CgrRule implements EligibilityRule {
    private final double minCgr;

    public CgrRule(double minCgr) {
        this.minCgr = minCgr;
    }

    @Override
    public boolean isViolated(StudentProfile student) {
        return student.cgr < minCgr;
    }

    @Override
    public String getReason() {
        return "CGR below " + minCgr;
    }
}
