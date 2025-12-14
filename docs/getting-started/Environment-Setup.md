# 🛠️ Environment Setup Guide

[🏠 Home](../Home.md) > [Getting Started](./Environment-Setup.md) > Installation

← [Home](../Home.md) | [Next: Running Tests →](Running-Tests.md)

---

**Last Updated:** December 13, 2025  
**Estimated Time:** 30-45 minutes

---

## Overview

This guide walks you through setting up your development environment for the Test Center of Excellence (TCOE). Follow these steps to get your machine ready for test automation.

---

## Prerequisites Checklist

Before starting, ensure you have:
- [ ] Windows 10/11 (64-bit)
- [ ] Administrator access to install software
- [ ] Internet connection for downloads
- [ ] At least 10 GB free disk space

---

## Step 1: Install Java 17 JDK

### Download and Install

1. Visit [Adoptium Temurin Downloads](https://adoptium.net/temurin/releases/)
2. Select:
   - **Version:** 17 LTS
   - **Operating System:** Windows
   - **Architecture:** x64
   - **Package Type:** JDK
3. Download the `.msi` installer
4. Run installer with default options
5. ✅ **Important:** Check "Set JAVA_HOME variable" and "Add to PATH"

### Verify Installation

Open PowerShell and run:
```powershell
java -version
```

**Expected Output:**
```
openjdk version "17.0.x" 2024-xx-xx
OpenJDK Runtime Environment Temurin-17.0.x (build 17.0.x+x)
OpenJDK 64-Bit Server VM Temurin-17.0.x (build 17.0.x+x, mixed mode, sharing)
```

---

## Step 2: Install Visual Studio Code

### Download and Install

1. Visit [VS Code Downloads](https://code.visualstudio.com/download)
2. Download Windows installer
3. Run installer and select:
   - ✅ Add "Open with Code" to context menu
   - ✅ Add to PATH
   - ✅ Register Code as editor for supported file types

### Install Essential Extensions

Open VS Code and install these extensions (Ctrl+Shift+X):

1. **Extension Pack for Java** (vscjava.vscode-java-pack)
   - Includes Language Support, Debugger, Test Runner, Maven/Gradle support
   
2. **Gradle for Java** (vscjava.vscode-gradle)
   - Gradle task management and execution
   
3. **Markdown Preview Mermaid Support** (bierner.markdown-mermaid)
   - View diagrams in documentation

---

## Step 3: Install Gradle

### Using SDKMAN (Recommended for Version Management)

**Skip this if you prefer manual installation below.**

1. Install SDKMAN in PowerShell:
```powershell
Invoke-WebRequest -Uri https://get.sdkman.io -OutFile sdkman-install.ps1
.\sdkman-install.ps1
```

2. Install Gradle:
```bash
sdk install gradle 9.2.1
```

### Manual Installation

1. Download [Gradle 9.2.1](https://gradle.org/releases/)
2. Extract to `C:\Gradle\gradle-9.2.1`
3. Add to PATH:
   - Open System Properties → Environment Variables
   - Edit System PATH variable
   - Add: `C:\Gradle\gradle-9.2.1\bin`

### Verify Installation

```powershell
gradle --version
```

**Expected Output:**
```
Gradle 9.2.1
Build time: 2024-xx-xx
Revision: xxxxxxxx
Kotlin: 1.9.x
Groovy: 3.0.x
JVM: 17.0.x (Temurin)
OS: Windows 11 10.0 amd64
```

---

## Step 4: Install Python (For HTTP Server)

### Download and Install

1. Visit [Python Downloads](https://www.python.org/downloads/)
2. Download Python 3.11 or later
3. Run installer:
   - ✅ **IMPORTANT:** Check "Add Python to PATH"
   - Select "Install Now"

### Verify Installation

```powershell
python --version
```

**Expected Output:**
```
Python 3.11.x
```

---

## Step 5: Clone the Project

### Using Git

If you have Git installed:
```powershell
cd C:\
git clone <repository-url> AutomationProject
cd AutomationProject\java
```

### Manual Setup (Current Method)

Our project is currently located at:
```
C:\AutomationProject\java\
```

No action needed if you're already working in this directory.

---

## Step 6: Verify Project Setup

### Build the Project

```powershell
cd C:\AutomationProject\java
gradle clean build
```

**Expected Output:**
```
BUILD SUCCESSFUL in 5s
7 actionable tasks: 7 executed
```

### Run Tests

```powershell
gradle test
```

**Expected Output:**
```
aClassTest > addPositiveNumbers() PASSED
aClassTest > addNegativeNumbers() PASSED
aClassTest > addZero() PASSED
aClassTest > subtractNumbers() PASSED
aClassTest > subtractResultingInNegative() PASSED

BUILD SUCCESSFUL in 3s
5 tests, 5 passed
```

---

## Step 7: Configure Network Access (Optional)

To view dashboards on Smart TV or other devices:

### Find Your IP Address

```powershell
ipconfig | Select-String "IPv4"
```

**Example Output:**
```
IPv4 Address. . . . . . . . . . . : 192.168.1.71
```

### Configure Firewall

1. Open Windows Defender Firewall
2. Click "Advanced settings"
3. Create Inbound Rule:
   - **Port:** 8080 (Python HTTP Server)
   - **Action:** Allow
   - **Profile:** Private
   - **Name:** "TCOE HTTP Server"

4. Create another rule for Allure (ports 60000-65000 range)

---

## Step 8: Start Servers

### JaCoCo Coverage Server

```powershell
cd C:\AutomationProject\java\app\build
python -m http.server 8080
```

Keep this terminal open. Access at: `http://localhost:8080/jacoco/index.html`

### Allure Report Server

```powershell
cd C:\AutomationProject\java
gradle allureServe --no-configuration-cache
```

Keep this terminal open. Allure will auto-open in browser.

---

## Troubleshooting

### Java Not Found
**Error:** `'java' is not recognized`

**Solution:**
1. Verify JAVA_HOME: `echo $env:JAVA_HOME`
2. Should show: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x.x-hotspot`
3. If empty, set manually:
```powershell
[System.Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Program Files\Eclipse Adoptium\jdk-17.x.x.x-hotspot', 'Machine')
```
4. Restart PowerShell

### Gradle Build Fails
**Error:** `Could not resolve dependencies`

**Solution:**
```powershell
gradle clean --refresh-dependencies
gradle build
```

### Port Already in Use
**Error:** `Address already in use: bind`

**Solution:**
```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID)
taskkill /PID <PID> /F
```

### VS Code Java Extension Issues
**Solution:**
1. Press Ctrl+Shift+P
2. Type: "Java: Clean Java Language Server Workspace"
3. Reload VS Code

---

## Verification Checklist

Run through this checklist to ensure everything works:

- [ ] `java -version` shows Java 17
- [ ] `gradle --version` shows Gradle 9.2.1
- [ ] `python --version` shows Python 3.11+
- [ ] VS Code opens project without errors
- [ ] `gradle test` runs successfully (5 tests pass)
- [ ] JaCoCo report opens: `http://localhost:8080/jacoco/index.html`
- [ ] Allure report opens: `http://localhost:<port>`
- [ ] Documentation preview works in VS Code (Ctrl+Shift+V)

---

## Next Steps

Once your environment is set up:
1. Read [Running Tests Guide](Running-Tests.md)
2. Review [System Architecture](../architecture/System-Architecture.md)
3. Check [FAQ](FAQ.md) for common questions
4. Start writing your first test!

---

## Quick Reference

**Key Directories:**
```
C:\AutomationProject\java\          # Project root
├── app\src\main\java\aClass\       # Production code
├── app\src\test\java\aClass\       # Test code
├── app\build\jacoco\               # Coverage reports
├── app\build\allure-report\        # Test reports
└── docs\                           # Documentation
```

**Essential Commands:**
```powershell
gradle clean build              # Clean and build
gradle test                     # Run tests
gradle jacocoTestReport        # Generate coverage
gradle allureReport            # Generate Allure report
gradle allureServe             # Start Allure server
```

**Dashboard URLs:**
- JaCoCo: `http://localhost:8080/jacoco/index.html`
- Allure: Check terminal for dynamic port

---

**Document Status:** ✅ Complete  
**Last Reviewed:** December 13, 2025  
**Maintainer:** TCOE Team