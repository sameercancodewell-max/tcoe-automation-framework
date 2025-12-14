# ❓ Frequently Asked Questions (FAQ)

[🏠 Home](../Home.md) > [Getting Started](./Environment-Setup.md) > FAQ

← [Previous: Running Tests](Running-Tests.md) | [Home](../Home.md)

---

**Last Updated:** December 13, 2025

---

## General Questions

### What is TCOE?

**Test Center of Excellence (TCOE)** is a centralized framework for managing test automation, quality metrics, and continuous improvement in software testing.

**Our TCOE Journey:**
- **Duration:** 16-20 weeks
- **Goal:** Enterprise-grade test automation framework
- **Current Status:** Week 1 Day 1 - Foundation setup complete

---

### What technologies are we using?

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 17 | Programming language |
| Gradle | 9.2.1 | Build automation |
| JUnit 5 | 5.10.0 | Test framework |
| JaCoCo | 0.8.11 | Code coverage |
| Allure | 2.24.1 | Test reporting |
| Python | 3.11+ | HTTP server for dashboards |
| VS Code | Latest | IDE |

---

### Why Java 17 and not Java 8 or 21?

**Java 17 is the sweet spot:**
- ✅ **LTS Release** (Long-Term Support until 2029)
- ✅ **Modern Features** (Records, Pattern Matching, Text Blocks)
- ✅ **Industry Standard** (Most enterprises migrated from 8/11 to 17)
- ✅ **Stable** (Released Sept 2021, well-tested)
- ❌ Java 8 is too old (released 2014, missing modern features)
- ❌ Java 21 is newer (Sept 2023, less enterprise adoption yet)

---

### Why Gradle and not Maven?

**Gradle Advantages:**
- ⚡ **Faster builds** (incremental compilation, build cache)
- 📝 **Groovy DSL** (more concise than XML)
- 🔌 **Better plugin ecosystem** (Allure, JaCoCo integration)
- 🏗️ **Modern** (released 2012, actively developed)

**Maven is still great**, but Gradle offers performance benefits for growing test suites.

---

## Setup & Configuration

### Do I need to install all these tools manually?

**Required Manual Installs:**
- ✅ Java 17 JDK
- ✅ VS Code
- ✅ Python

**Auto-Managed by Gradle:**
- ✅ JUnit 5
- ✅ JaCoCo
- ✅ Allure
- ✅ Dependencies

Gradle downloads test libraries automatically via `build.gradle`.

---

### Why do I need Python if this is a Java project?

**Python serves one purpose:** Running the HTTP server for JaCoCo reports.

```powershell
python -m http.server 8080
```

This simple command lets you view HTML reports on any device (TV, phone, tablet) on your network.

**Alternatives:**
- Node.js `http-server`
- Nginx
- Apache HTTP Server

But Python's built-in server is the simplest (one-line command, no config).

---

### Can I use IntelliJ IDEA instead of VS Code?

**Yes!** IntelliJ IDEA works great with Gradle projects.

**Pros:**
- Better Java code completion
- Advanced refactoring tools
- Built-in Gradle integration

**Cons:**
- Heavier resource usage
- Community Edition has limitations
- Our documentation assumes VS Code

**Setup in IntelliJ:**
1. Open → Select `C:\AutomationProject\java`
2. IntelliJ auto-detects Gradle
3. Run tests from Test Explorer

---

### What if I'm on Mac or Linux?

Most commands work cross-platform! Changes needed:

**PowerShell → Bash/Zsh:**
```bash
# PowerShell
$env:JAVA_HOME

# Mac/Linux
echo $JAVA_HOME
```

**Paths:**
```bash
# Windows
C:\AutomationProject\java

# Mac/Linux
/Users/yourname/AutomationProject/java
```

**HTTP Server:**
Same command works everywhere:
```bash
python3 -m http.server 8080
```

---

## Testing Questions

### How do I write my first test?

**5 Steps:**

1. **Create test class** in `app/src/test/java/aClass/`:
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyFirstTest {
    @Test
    void myTest() {
        assertEquals(5, 2 + 3);
    }
}
```

2. **Save file** (Ctrl+S)

3. **Run test** in VS Code (click Run icon)

4. **Check output** (should see PASSED)

5. **View coverage** (`http://localhost:8080/jacoco/index.html`)

---

### What's the difference between @Test and @Tag?

**@Test** - Marks a method as a test case (JUnit)
```java
@Test
void addNumbers() {
    // Test code
}
```

**@Tag** - Categorizes tests for selective execution (JUnit)
```java
@Tag("critical")
@Test
void importantTest() {
    // Runs only with: gradle test -Dtags="critical"
}
```

**@Epic, @Feature, @Story** - Allure annotations for reporting organization
```java
@Epic("Calculator")
@Feature("Addition")
@Story("Add two positive numbers")
@Test
void test() { }
```

---

### Why are Allure annotations separate from JUnit?

**Different purposes:**

**JUnit annotations** → Test **execution**
- Which tests to run
- How to run them
- When to run them

**Allure annotations** → Test **reporting**
- How to organize results
- What to display in reports
- Categorization for stakeholders

They work together but serve different needs.

---

### How many tests should I write per class?

**Best Practice:**
- **5-15 tests per class** (one class per production class)
- **1-5 assertions per test** (test one thing at a time)
- **Test names describe behavior** (`addPositiveNumbers` not `test1`)

**Example:**
```
Calculator.java (production)
└── CalculatorTest.java (tests)
    ├── addPositiveNumbers()
    ├── addNegativeNumbers()
    ├── addZero()
    ├── divideByZero_ThrowsException()
    └── ... (5-10 more)
```

---

## Reports & Dashboards

### Why do I need TWO report servers (JaCoCo + Allure)?

**They serve different purposes:**

**JaCoCo = Code Coverage**
- Shows which lines of code are tested
- Highlights untested branches
- Helps find gaps in testing
- **Audience:** Developers

**Allure = Test Results**
- Shows which tests passed/failed
- Groups by Epic/Feature/Story
- Displays execution trends
- **Audience:** Managers, stakeholders

Think of it like:
- **JaCoCo:** "Did we test all the code?"
- **Allure:** "Did all the tests pass?"

---

### Why does Allure use a different port each time?

Allure's Jetty server **auto-assigns an available port** (usually 60000-65535 range).

**Why?**
- Avoids port conflicts
- Runs multiple Allure instances simultaneously
- Enterprise-safe (no hardcoded ports)

**Check current port:**
Look at terminal output when running:
```powershell
gradle allureServe --no-configuration-cache
```

Output shows:
```
Server started at <http://192.168.1.71:64196>
```

---

### Can I customize the Allure report?

**Yes!** Create `allure.properties` in `src/test/resources/`:

```properties
allure.results.directory=build/allure-results
allure.link.issue.pattern=https://jira.company.com/{}
allure.link.tms.pattern=https://testmanager.com/{}
```

**Customize in Java:**
```java
@Link(name = "JIRA-123", url = "https://jira.com/JIRA-123")
@Test
void testWithLink() { }
```

---

### How do I share dashboards with my team?

**Option 1: Live Server (Current Method)**
1. Keep Python/Allure servers running
2. Share your IP: `192.168.1.71:8080`
3. Team browses from any device on network

**Option 2: Cloud Hosting (Week 8+)**
- Deploy to AWS S3 / Azure Blob / Netlify
- Public URL: `https://tcoe-reports.company.com`

**Option 3: CI/CD Integration (Week 6+)**
- GitHub Actions auto-publishes reports
- Comment on PRs with report links

---

## Errors & Troubleshooting

### Error: "Execution failed for task ':app:test'"

**Cause:** Test failed (assertion error)

**Solution:**
1. Read error message carefully
2. Note failing test name
3. Open test file (click stack trace link)
4. Fix assertion or code
5. Re-run test

**Example:**
```
aClassTest > addNumbers() FAILED
    expected: <5> but was: <4>
```
→ Check Calculator.add() method logic

---

### Error: "Address already in use: bind"

**Cause:** Port 8080 already occupied

**Solution:**
```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Output shows PID (e.g., 12345)
# Kill process
taskkill /PID 12345 /F

# Restart server
python -m http.server 8080
```

---

### Error: "Could not find or load main class"

**Cause:** Gradle build corrupted or incomplete

**Solution:**
```powershell
# Clean rebuild
gradle clean build --refresh-dependencies

# Reset Gradle cache
gradle --stop
```

---

### Allure report shows "Loading..." forever

**Cause:** Using file:// protocol (CORS restriction)

**Solution:**
❌ **Don't do this:**
```powershell
start C:\AutomationProject\java\app\build\allure-report\index.html
```

✅ **Do this:**
```powershell
gradle allureServe --no-configuration-cache
```

The server provides HTTP protocol, not file://.

---

### VS Code doesn't recognize Java code

**Symptoms:**
- Red squiggles everywhere
- No code completion
- "Import cannot be resolved"

**Solutions:**

**1. Clean Java Workspace:**
- Press `Ctrl+Shift+P`
- Type: `Java: Clean Java Language Server Workspace`
- Click and restart VS Code

**2. Rebuild Project:**
```powershell
gradle clean build
```

**3. Check Java Extension:**
- Ensure "Extension Pack for Java" is installed
- Check bottom-right corner shows Java version

---

## Best Practices

### Should I commit build/ folder to Git?

**NO!** The `build/` folder contains generated files.

**Add to `.gitignore`:**
```gitignore
# Gradle
.gradle/
build/
**/build/

# IDE
.vscode/
.idea/
*.iml

# OS
.DS_Store
Thumbs.db
```

**Commit these:**
✅ Source code (`.java`)
✅ Build config (`build.gradle`)
✅ Documentation (`.md`)
✅ Test data

---

### How often should I run tests?

**Frequency:**
- **Before committing code:** Always
- **After fixing bugs:** Always
- **During development:** Every 10-15 minutes
- **On CI/CD:** Every commit (automated)

**Commands:**
```powershell
# Quick check (5s)
gradle test

# Full verification (10s)
gradle clean test jacocoTestReport
```

---

### What code coverage should I aim for?

**Industry Standards:**
- 80%+ = Excellent ✅
- 60-79% = Good 👍
- 40-59% = Needs Improvement ⚠️
- <40% = Poor ❌

**Our Goal:**
- **Week 1-4:** 80%+ (unit tests)
- **Week 5-8:** 70%+ (adding integration tests)
- **Week 9+:** 75%+ (stable across layers)

**Note:** 100% coverage doesn't mean bug-free code!

---

### Should I test private methods?

**No!** Test public methods only.

**Why?**
- Private methods are implementation details
- They're tested indirectly via public methods
- Testing private methods makes refactoring harder

**Example:**
```java
public int add(int a, int b) {
    return sum(a, b);  // private method
}

private int sum(int a, int b) {
    return a + b;
}

// Test this ✅
@Test
void testAdd() {
    assertEquals(5, calculator.add(2, 3));
}

// Don't test this ❌
@Test
void testSum() {
    // Can't access private method anyway!
}
```

---

## Metrics & KPIs

### What's the difference between TCOE-Metrics.md and Allure Reports?

**TCOE-Metrics.md** = Operational log (manual tracking)
- Daily progress
- Cumulative metrics
- Learnings & notes
- Week-over-week trends
- **Updated by:** You (manually)

**Allure Reports** = Test execution results (auto-generated)
- Test pass/fail status
- Execution time
- Test categorization
- **Generated by:** Gradle plugin (automatically)

They complement each other!

---

### What are DORA metrics?

**DORA = DevOps Research and Assessment**

**4 Key Metrics:**
1. **Deployment Frequency** - How often you release
2. **Lead Time for Changes** - Code → Production time
3. **Change Failure Rate** - % of releases causing issues
4. **Time to Restore Service** - Recovery time from failures

**Our Tracking (Week 1):**
- **Cycle Time:** 5s (tests run time)
- **Test Velocity:** 5 tests/day
- **Change Failure Rate:** 0% (no failed tests)
- **Defect Density:** 0 defects/KLOC

---

## Roadmap Questions

### What's coming in future weeks?

**Week 2-4: Integration Testing**
- Selenium WebDriver
- API testing (REST Assured)
- Database testing

**Week 5-8: CI/CD**
- GitHub Actions
- Automated test runs
- Slack notifications

**Week 9-12: Advanced Reporting**
- Extent Reports
- TestRail integration
- Custom dashboards

**Week 13-16: Performance**
- JMeter load testing
- Performance benchmarks

**Week 17-20: Polish**
- Documentation complete
- Team training
- Production-ready

---

### Will we learn API testing?

**Yes!** Starting Week 3-4.

**Tools:**
- **REST Assured** - API testing framework
- **Postman/Newman** - Manual/automated API tests
- **WireMock** - API mocking

**Example test:**
```java
@Test
void getUserById() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users/123")
    .then()
        .statusCode(200)
        .body("name", equalTo("John"));
}
```

---

### When will we add Selenium?

**Week 2-3** - UI automation begins!

**Setup includes:**
- WebDriver installation
- ChromeDriver setup
- Page Object Model pattern
- Screenshot capture
- Headless execution

**First UI test:**
```java
@Test
void testGoogleSearch() {
    driver.get("https://google.com");
    driver.findElement(By.name("q")).sendKeys("TCOE");
    driver.findElement(By.name("q")).submit();
    assertTrue(driver.getTitle().contains("TCOE"));
}
```

---

## Need More Help?

**Documentation:**
- [Home](../Home.md) - Documentation portal
- [System Architecture](../architecture/System-Architecture.md) - Technical overview
- [Running Tests](Running-Tests.md) - Detailed test guide
- [Environment Setup](Environment-Setup.md) - Installation guide

**Troubleshooting:**
- [Common Issues](../troubleshooting/Common-Issues.md) - Known problems
- [Solutions Database](../troubleshooting/Solutions-Database.md) - Quick fixes

**Operations:**
- [Daily Operations](../runbooks/Daily-Operations.md) - Daily workflows
- [Server Management](../runbooks/Server-Management.md) - Server commands

---

**Document Status:** ✅ Complete  
**Last Reviewed:** December 13, 2025  
**Maintainer:** TCOE Team

**Have a question not answered here?** Add it to this FAQ and answer it yourself - that's how we build knowledge! 🚀