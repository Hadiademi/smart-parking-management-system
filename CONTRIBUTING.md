# Contributing to Smart Parking Management System

## 📝 Development Guidelines

### Project Overview
This is an educational Spring Boot project created as a final assignment. It demonstrates backend development skills including REST API design, business logic implementation, and database management.

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Gradle
- IDE (IntelliJ IDEA recommended)
- PostgreSQL (optional)

### Setup
1. Clone the repository
2. Open in IntelliJ IDEA
3. Wait for Gradle to download dependencies
4. Run `DemoApplication.java`

---

## 🏗️ Code Structure

### Adding New Features

#### 1. Create Entity
```java
@Entity
@Table(name = "your_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Add fields with validation
}
```

#### 2. Create Repository
```java
@Repository
public interface YourRepository extends JpaRepository<YourEntity, Long> {
    // Add custom queries
}
```

#### 3. Create DTO
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YourDTO {
    private Long id;
    // Add fields
}
```

#### 4. Create Service
```java
@Service
@RequiredArgsConstructor
@Transactional
public class YourService {
    private final YourRepository repository;
    private final ModelMapper modelMapper;
    
    // Add business logic
}
```

#### 5. Create Controller
```java
@RestController
@RequestMapping("/api/your-resource")
@RequiredArgsConstructor
public class YourController {
    private final YourService service;
    
    // Add endpoints
}
```

#### 6. Create Tests
```java
@ExtendWith(MockitoExtension.class)
class YourServiceTest {
    @Mock
    private YourRepository repository;
    
    @InjectMocks
    private YourService service;
    
    @Test
    void testYourFeature() {
        // Add test
    }
}
```

---

## 🎨 Coding Standards

### Naming Conventions
- Classes: PascalCase (e.g., `UserService`)
- Methods: camelCase (e.g., `getUserById`)
- Variables: camelCase (e.g., `userName`)
- Constants: UPPER_SNAKE_CASE (e.g., `BASE_RATE_PER_HOUR`)

### Java Conventions
- Use Lombok annotations to reduce boilerplate
- Always add `@RequiredArgsConstructor` for dependency injection
- Use `@Transactional` on service methods that modify data
- Add proper validation annotations

### REST API Guidelines
- Use proper HTTP methods (GET, POST, PUT, PATCH, DELETE)
- Return appropriate status codes
- Use plural nouns for resources (e.g., `/users`, `/vehicles`)
- Use sub-resources for relationships (e.g., `/users/{id}/vehicles`)

---

## ✅ Testing Guidelines

### Unit Tests
- Test all service methods
- Mock dependencies using Mockito
- Test both success and failure cases
- Test edge cases and validations

### Controller Tests
- Use `@WebMvcTest` for controller tests
- Mock service layer
- Test all endpoints
- Verify HTTP status codes and response body

### Test Naming
```java
methodName_scenario_expectedBehavior()
// Example:
createUser_WithValidData_ReturnsCreatedUser()
createUser_WithDuplicateEmail_ThrowsException()
```

---

## 📚 Documentation

### Code Comments
- Add JavaDoc for public methods
- Explain complex business logic
- Document assumptions and edge cases

### API Documentation
- Update README.md with new endpoints
- Add examples to API_EXAMPLES.md
- Document request/response formats

---

## 🐛 Bug Reporting

### Bug Report Template
```
**Description**
Brief description of the bug

**Steps to Reproduce**
1. Step 1
2. Step 2
3. Step 3

**Expected Behavior**
What should happen

**Actual Behavior**
What actually happens

**Environment**
- Java version:
- Database:
- OS:
```

---

## 🔧 Common Tasks

### Running Tests
```bash
./gradlew test
```

### Building Project
```bash
./gradlew build
```

### Running Application
```bash
./gradlew bootRun
```

### Checking Code Style
```bash
./gradlew check
```

---

## 📋 Checklist Before Committing

- [ ] Code compiles without errors
- [ ] All tests pass
- [ ] New features have tests
- [ ] Code follows naming conventions
- [ ] Documentation is updated
- [ ] No commented-out code
- [ ] No debug statements left
- [ ] Git commit message is descriptive

---

## 🎓 Learning Resources

- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

---

## 📞 Questions?

For questions about this project, please refer to:
- README.md - Project overview
- ARCHITECTURE.md - System architecture
- API_EXAMPLES.md - API usage examples

---

## 📄 License

This project is created for educational purposes as part of a Spring Boot course assignment.
