// Database interface - assumed to be available
public interface Database {
    String fetch(String key);
    void save(String key, String value);
}
