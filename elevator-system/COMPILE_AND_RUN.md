# How to Compile and Run the Elevator System

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven 3.6+ OR Gradle 7.0+ (optional, for build automation)

## Method 1: Using Maven (Recommended)

### Compile
```bash
mvn clean compile
```

### Run
```bash
mvn exec:java
```

### Package as JAR
```bash
mvn clean package
java -jar target/elevator-system-1.0.0.jar
```

## Method 2: Using Gradle

### Compile
```bash
gradle build
```

### Run
```bash
gradle run
```

### Create executable JAR
```bash
gradle jar
java -jar build/libs/elevator-system-1.0.0.jar
```

## Method 3: Using Java Compiler Directly

### Step 1: Compile all Java files
```bash
# Create output directory
mkdir -p bin

# Compile all source files
javac -d bin src/main/java/com/elevator/**/*.java
```

### Step 2: Run the application
```bash
java -cp bin com.elevator.Main
```

## Method 4: Using an IDE

### IntelliJ IDEA
1. Open the project folder
2. Right-click on `Main.java`
3. Select "Run 'Main.main()'"

### Eclipse
1. Import as Maven or Gradle project
2. Right-click on `Main.java`
3. Select "Run As" → "Java Application"

### VS Code
1. Install Java Extension Pack
2. Open the project folder
3. Press F5 or click "Run" above the `main` method

## Expected Output

```
============================================================
ELEVATOR SYSTEM - SOLID PRINCIPLES DEMONSTRATION
============================================================

--- Testing Nearest Elevator Strategy ---

=== Request: Floor 5, Direction UP ===
Selected Elevator 1 for floor 5
Elevator 1 at floor 2
[DISPLAY] Elevator 1: Floor 2, UP, MOVING
Elevator 1 at floor 3
[DISPLAY] Elevator 1: Floor 3, UP, MOVING
...

============================================================
SWITCHING TO OPTIMAL DIRECTION STRATEGY
============================================================
...

============================================================
SOLID PRINCIPLES DEMONSTRATED:
============================================================
✓ SRP: Each class has single, well-defined responsibility
✓ OCP: Open for extension, closed for modification
✓ LSP: Implementations are substitutable
✓ ISP: Small, focused interfaces
✓ DIP: Dependencies on abstractions
============================================================
```

## Troubleshooting

### "javac: command not found"
- Install JDK and ensure JAVA_HOME is set
- Add JDK bin directory to PATH

### "package does not exist" errors
- Ensure you're compiling from the project root
- Check that the package structure matches the directory structure

### Maven/Gradle not found
- Install Maven from https://maven.apache.org/
- Install Gradle from https://gradle.org/
- Or use Java compiler directly (Method 3)

## Project Structure Verification

Ensure your directory structure looks like this:
```
.
├── pom.xml
├── build.gradle
└── src/
    └── main/
        └── java/
            └── com/
                └── elevator/
                    ├── Main.java
                    ├── enums/
                    ├── interfaces/
                    ├── models/
                    ├── controllers/
                    ├── handlers/
                    ├── strategies/
                    └── observers/
```
