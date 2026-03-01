public class CreditsRule implements EligibilityRule {
    private final int minCredits;

    public CreditsRule(int minCredits) {
        this.minCredits = minCredits;
    }

    @Override
    public boolean isViolated(StudentProfile student) {
        return student.earnedCredits < minCredits;
    }

    @Override
    public String getReason() {
        return "credits below " + minCredits;
    }
}
