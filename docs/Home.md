# 🏠 Test Center of Excellence (TCOE) - Home

**Welcome to the TCOE Documentation Portal**

> *Building enterprise-grade test automation infrastructure from the ground up*

---

## 🚀 Quick Start

New to the project? Start here:

1. 📖 [Environment Setup Guide](getting-started/Environment-Setup.md)
2. ▶️ [Running Your First Tests](getting-started/Running-Tests.md)
3. ❓ [Frequently Asked Questions](getting-started/FAQ.md)

---

## 📊 Current Status

| Metric | Value | Status |
|--------|-------|--------|
| **Week** | 1 - Foundation | 🟢 In Progress |
| **Tests** | 5 | ✅ 100% Pass |
| **Code Coverage** | 100% | ✅ Excellent |
| **Build Time** | 5 seconds | ✅ Fast |

**Last Updated:** December 13, 2025

---

## 📚 Documentation Sections

### 🏗️ Architecture
Understand the technical design and structure

- [System Architecture](architecture/System-Architecture.md) - Overall system design
- [Framework Design](architecture/Framework-Design.md) - Test framework structure  
- [Technology Stack](architecture/Technology-Stack.md) - Tools and versions
- [Architecture Diagrams](architecture/diagrams/) - Visual representations

### 📊 Metrics & Reports
Track progress and quality metrics

- [KPI Dashboard](metrics-reports/KPI-Dashboard.md) - Key performance indicators
- [Weekly Reports](metrics-reports/Weekly-Reports.md) - Progress summaries
- [Trend Analysis](metrics-reports/Trend-Analysis.md) - Historical data

### 🛠️ Runbooks
Daily operations and procedures

- [Daily Operations](runbooks/Daily-Operations.md) - Routine tasks
- [Server Management](runbooks/Server-Management.md) - Keep systems running
- [Report Generation](runbooks/Report-Generation.md) - Generate and view reports

### 🔧 Troubleshooting
Solutions to common problems

- [Common Issues](troubleshooting/Common-Issues.md) - Problems and fixes
- [Solutions Database](troubleshooting/Solutions-Database.md) - Known solutions

### 📈 Roadmap
Future plans and goals

- [20-Week Plan](roadmap/Weekly-Plans.md) - Complete training roadmap
- [Feature Backlog](roadmap/Feature-Backlog.md) - Upcoming features

---

## 🎯 Quick Links

### Live Dashboards
- 📊 [JaCoCo Coverage Report](http://192.168.1.71:8080/app/build/reports/jacoco/index.html)
- 🎯 [Allure Test Reports](http://192.168.1.71:64196/index.html)

### Key Commands
```bash
# Run all tests
gradle clean test --no-configuration-cache

# Run only critical tests
gradle test -Dtags=critical --no-configuration-cache

# Start servers for TV display
python -m http.server 8080
[allure.bat](http://_vscodecontentref_/0) serve C:\AutomationProject\java\app\build\allure-results