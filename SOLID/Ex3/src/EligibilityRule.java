public interface EligibilityRule {
    boolean isViolated(StudentProfile student);
    String getReason();
}
