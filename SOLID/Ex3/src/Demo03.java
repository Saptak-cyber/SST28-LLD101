import java.util.*;

public class Demo03 {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");
        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);
        
        RuleInput config = new RuleInput();
        
        List<EligibilityRule> rules = new ArrayList<>();
        rules.add(new DisciplinaryFlagRule());
        rules.add(new CgrRule(config.minCgr));
        rules.add(new AttendanceRule(config.minAttendance));
        rules.add(new CreditsRule(config.minCredits));
        
        EligibilityEngine engine = new EligibilityEngine(new FakeEligibilityStore(), rules);
        engine.runAndPrint(s);
    }
}
