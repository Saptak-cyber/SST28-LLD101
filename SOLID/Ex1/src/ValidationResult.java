import java.util.*;

public class ValidationResult {
    private final List<String> errors;

    public ValidationResult(List<String> errors) {
        this.errors = new ArrayList<>(errors);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
