# Daily Operations

[🏠 Home](../Home.md) > [Runbooks](./Daily-Operations.md) > Daily Operations

← [Home](../Home.md) | [Next: Server Management →](Server-Management.md)

---

Guide for daily test automation workflows and routine tasks.

---

## Overview

This runbook covers the standard daily operations for the Test Center of Excellence (TCOE). Follow these workflows to maintain quality standards and keep the testing infrastructure running smoothly.

---

## Morning Routine (Start of Day)

### 1. Start Development Environment

**Open VS Code**
```powershell
cd C:\AutomationProject\java
code .
```

**Verify Java and Gradle**
```powershell
java -version    # Should show Java 17
gradle --version # Should show Gradle 9.2.1
```

### 2. Pull Latest Changes

Once Git is initialized (Week 2+):
```powershell
git pull origin main
```

### 3. Clean Build
```powershell
gradle clean build
```

**Expected result:** BUILD SUCCESSFUL in ~5s

### 4. Run Test Suite
```powershell
gradle test
```

**Expected result:** All tests PASSED

### 5. Start Dashboard Servers

**Terminal 1 - JaCoCo HTTP Server:**
```powershell
cd C:\AutomationProject\java\app\build
python -m http.server 8080
```

**Terminal 2 - Allure Report Server:**
```powershell
cd C:\AutomationProject\java
gradle allureServe --no-configuration-cache
```

**Verify dashboards accessible:**
- JaCoCo: http://192.168.1.71:8080/jacoco/index.html
- Allure: http://192.168.1.71:<dynamic-port>

---

## Development Workflow

### Writing New Tests

**Step 1: Create test method**

Open test file: [aClassTest.java](../../app/src/test/java/aClass/aClassTest.java)

```java
@Epic("Calculator Functionality")
@Feature("Basic Arithmetic")
@Story("Multiplication")
@Severity(SeverityLevel.CRITICAL)
@Description("Verifies that multiplying two numbers works correctly")
@Tag("critical")
@Test
void multiplyNumbers() {
    Calculator calc = new Calculator();
    assertEquals(6, calc.multiply(2, 3));
}
```

**Step 2: Implement production code**

Open production file: [Calculator.java](../../app/src/main/java/aClass/Calculator.java)

```java
public int multiply(int a, int b) {
    return a * b;
}
```

**Step 3: Run test**
```powershell
gradle test --tests "aClass.aClassTest.multiplyNumbers"
```

**Step 4: Verify coverage**

Open: http://localhost:8080/jacoco/index.html

Check that new code is covered (green highlighting).

### Test-Driven Development (TDD) Cycle

```
1. Write failing test (RED)
   ↓
2. Write minimal code to pass (GREEN)
   ↓
3. Refactor code (REFACTOR)
   ↓
4. Repeat
```

**Commands:**
```powershell
# Run tests continuously (watch mode)
gradle test --continuous

# Run specific test repeatedly
gradle test --tests "*multiply*" --continuous
```

---

## Running Tests

### Run All Tests
```powershell
gradle test
```

### Run Tests by Category
```powershell
# Critical tests only
gradle test -Dtags="critical"

# Unit tests only
gradle test -Dtags="unit"

# Multiple tags
gradle test -Dtags="critical | unit"
```

### Run Specific Test Class
```powershell
gradle test --tests "aClass.aClassTest"
```

### Run Specific Test Method
```powershell
gradle test --tests "aClass.aClassTest.addPositiveNumbers"
```

### Run with Pattern Matching
```powershell
# All tests with "add" in name
gradle test --tests "*add*"

# All tests in package
gradle test --tests "aClass.*"
```

---

## Report Generation

### Generate Coverage Report
```powershell
gradle jacocoTestReport
```

**Output location:** `app/build/jacoco/index.html`

### Generate Allure Report
```powershell
gradle allureReport --no-configuration-cache
```

**Output location:** `app/build/allure-report/index.html`

### Serve Reports Live
```powershell
# JaCoCo
cd app\build
python -m http.server 8080

# Allure
gradle allureServe --no-configuration-cache
```

---

## Metrics Tracking

### Update Daily Metrics

After each test run, update [TCOE-Metrics.md](../../TCOE-Metrics.md):

**Metrics to record:**
- Total tests executed
- Tests passed/failed
- Code coverage percentage
- Build time
- New tests added
- Bugs found

**Example entry:**
```markdown
| Date   | Tests | Pass | Fail | Coverage | Build Time | New Tests | Bugs |
|--------|-------|------|------|----------|------------|-----------|------|
| Dec 13 | 5     | 5    | 0    | 100%     | 3s         | 5         | 0    |
```

### Calculate Key Performance Indicators (KPIs)

**Test Velocity:** Tests added per day
```
Week 1: 5 tests = 5 tests/day
```

**Pass Rate:** Percentage of passing tests
```
(Passed / Total) × 100 = (5/5) × 100 = 100%
```

**Change Failure Rate:** Failed builds / Total builds
```
(0 failures / 1 build) × 100 = 0%
```

**Cycle Time:** Time from code change to test results
```
gradle test execution time = ~3s
```

---

## Quality Gates

### Before Committing Code (Week 2+)

Run this checklist:

- [ ] All tests pass: `gradle test`
- [ ] Coverage ≥ 80%: Check JaCoCo report
- [ ] No compilation errors: `gradle build`
- [ ] Code formatted: Follow Java conventions
- [ ] Allure annotations present: @Epic, @Feature, @Story

**Quick verification:**
```powershell
gradle clean test jacocoTestReport
```

### Pre-Release Checklist

Run critical tests only:
```powershell
gradle test -Dtags="critical"
```

**Requirements:**
- 100% critical tests pass
- 0 compilation warnings
- Documentation updated
- Metrics logged in TCOE-Metrics.md

---

## Troubleshooting Common Issues

### Tests Failing After Code Change

**Symptom:** Previously passing tests now fail

**Resolution:**
1. Read error message carefully
2. Check recent code changes
3. Run specific failing test: `gradle test --tests "<TestName>"`
4. Debug in VS Code (set breakpoint, press F5)
5. Fix code or test
6. Re-run: `gradle test`

### Coverage Dropped Below 80%

**Symptom:** JaCoCo shows <80% coverage

**Resolution:**
1. Open JaCoCo report: `app/build/jacoco/index.html`
2. Find red (uncovered) lines
3. Write tests for uncovered code paths
4. Run tests: `gradle test`
5. Verify coverage improved

### Build Taking Too Long

**Symptom:** `gradle test` takes >10s

**Resolution:**
```powershell
# Clean Gradle cache
gradle clean --refresh-dependencies

# Stop Gradle daemon
gradle --stop

# Restart daemon
gradle test
```

### Server Port Conflicts

**Symptom:** "Address already in use: bind"

**Resolution:**
```powershell
# Find process on port 8080
netstat -ano | findstr :8080

# Kill process (replace <PID> with actual number)
taskkill /PID <PID> /F

# Restart server
python -m http.server 8080
```

---

## End of Day Routine

### 1. Stop Servers

Press `Ctrl+C` in terminals running:
- Python HTTP server (JaCoCo)
- Allure server

### 2. Update Metrics

Complete daily entry in [TCOE-Metrics.md](../../TCOE-Metrics.md).

### 3. Commit Changes (Week 2+)

```powershell
git add .
git commit -m "Day 1: Added 5 unit tests with 100% coverage"
git push origin main
```

### 4. Document Learnings

Add notes to TCOE-Metrics.md under "Learnings" section:
- What worked well
- What challenged you
- What to improve tomorrow

### 5. Verify Environment

```powershell
# Ensure clean state
gradle clean

# Stop Gradle daemon (frees memory)
gradle --stop
```

---

## Weekly Tasks

### Monday: Planning

- Review previous week metrics
- Set goals for current week
- Update [Weekly-Plans.md](../roadmap/Weekly-Plans.md)
- Prioritize test cases to write

### Wednesday: Mid-Week Check

- Review test pass rate (target: 95%+)
- Check coverage trend (target: maintaining 80%+)
- Address any failing tests
- Update documentation if needed

### Friday: Week Wrap-Up

- Calculate weekly metrics:
  - Total tests added this week
  - Average coverage %
  - Total bugs found and fixed
  - Time spent on testing
- Update cumulative metrics in TCOE-Metrics.md
- Plan next week's goals

---

## Emergency Procedures

### All Tests Failing

**Immediate actions:**
1. Don't commit/push code
2. Run last known good commit: `git checkout <commit-hash>`
3. Verify tests pass on that commit
4. Compare changes: `git diff <commit-hash> HEAD`
5. Identify breaking change
6. Fix or revert change

### Production Bug Found

**Response workflow:**
1. Create bug report with details
2. Write failing test that reproduces bug
3. Verify test fails: `gradle test`
4. Fix production code
5. Verify test passes: `gradle test`
6. Check coverage maintained: Review JaCoCo
7. Commit fix with test
8. Update TCOE-Metrics.md (increment "Bugs" column)

### Server Crashes

**Recovery steps:**
```powershell
# Kill all Java processes
taskkill /F /IM java.exe

# Kill Python processes
taskkill /F /IM python.exe

# Restart VS Code
# Close and reopen

# Clean rebuild
gradle clean build

# Restart servers
python -m http.server 8080
gradle allureServe --no-configuration-cache
```

---

## Automation Opportunities

### Current Manual Tasks (Week 1)

Tasks we perform manually today:
- Starting HTTP servers
- Updating metrics in markdown
- Checking coverage thresholds
- Opening reports in browser

### Future Automation (Week 5+)

Tasks we'll automate:
- **CI/CD Pipeline:** Auto-run tests on git push
- **Scheduled Reports:** Daily email with test results
- **Coverage Enforcement:** Build fails if <80% coverage
- **Slack Notifications:** Alert team on test failures

---

## Command Reference

### Essential Daily Commands

```powershell
# Build and test
gradle clean build
gradle test

# Generate reports
gradle jacocoTestReport
gradle allureReport --no-configuration-cache
gradle allureServe --no-configuration-cache

# Start servers
python -m http.server 8080         # JaCoCo
gradle allureServe                 # Allure

# Run specific tests
gradle test --tests "<ClassName>"
gradle test -Dtags="critical"

# Gradle maintenance
gradle --stop                      # Stop daemon
gradle clean                       # Clean build
gradle --refresh-dependencies      # Refresh deps
```

### Quick Status Check

```powershell
# One-liner to verify everything works
gradle clean test jacocoTestReport && echo "All systems operational"
```

---

## Related Documentation

- [Running Tests Guide](../getting-started/Running-Tests.md) - Detailed test execution
- [Server Management](Server-Management.md) - Server operations
- [Common Issues](../troubleshooting/Common-Issues.md) - Problem resolution
- [TCOE Metrics](../../TCOE-Metrics.md) - Current metrics dashboard

---

**Last Updated:** December 13, 2025  
**Next Review:** December 20, 2025 (Week 2)  
**Maintainer:** TCOE Team