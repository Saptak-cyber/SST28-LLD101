import java.util.*;

public class OnboardingService {
    private final StudentRepository repository;
    private final InputParser parser;
    private final StudentValidator validator;
    private final OutputFormatter formatter;

    public OnboardingService(StudentRepository repository) {
        this.repository = repository;
        this.parser = new InputParser();
        this.validator = new StudentValidator();
        this.formatter = new OutputFormatter();
    }

    public void registerFromRawInput(String raw) {
        formatter.printInput(raw);

        Map<String, String> data = parser.parse(raw);

        ValidationResult validationResult = validator.validate(data);
        if (!validationResult.isValid()) {
            formatter.printValidationErrors(validationResult.getErrors());
            return;
        }

        String name = data.get("name");
        String email = data.get("email");
        String phone = data.get("phone");
        String program = data.get("program");

        String id = IdUtil.nextStudentId(repository.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        repository.save(rec);

        formatter.printSuccess(id, repository.count(), rec);
    }
}
