# Server Management

[🏠 Home](../Home.md) > [Runbooks](./Daily-Operations.md) > Server Management

← [Previous: Daily Operations](Daily-Operations.md) | [Home](../Home.md)

---

Guide for managing HTTP servers, Allure report servers, and troubleshooting server-related issues.

---

## Overview

The TCOE framework uses two types of servers to deliver dashboards and reports:
1. **Python HTTP Server** - Serves static JaCoCo coverage reports
2. **Allure Jetty Server** - Serves dynamic Allure test reports

This guide covers starting, stopping, monitoring, and troubleshooting both servers.

---

## Python HTTP Server (JaCoCo Reports)

### Starting the Server

**Basic start:**
```powershell
cd C:\AutomationProject\java\app\build
python -m http.server 8080
```

**Expected output:**
```
Serving HTTP on :: port 8080 (http://[::]:8080/) ...
```

**What it serves:**
- JaCoCo coverage reports at `/jacoco/index.html`
- All files in `app/build/` directory

**Access URLs:**
- Local: http://localhost:8080/jacoco/index.html
- Network: http://192.168.1.71:8080/jacoco/index.html

### Custom Port

If port 8080 is in use, choose another:
```powershell
python -m http.server 8081
```

### Binding to Specific Interface

**Bind to all interfaces (default):**
```powershell
python -m http.server 8080
```

**Bind to specific IP:**
```powershell
python -m http.server 8080 --bind 192.168.1.71
```

### Running in Background

**PowerShell background job:**
```powershell
Start-Job -ScriptBlock {
    cd C:\AutomationProject\java\app\build
    python -m http.server 8080
}
```

**Check status:**
```powershell
Get-Job
```

**Stop background job:**
```powershell
Stop-Job -Name "Job1"
Remove-Job -Name "Job1"
```

### Stopping the Server

**If running in foreground:**
```
Press Ctrl+C
```

**If running in background:**
```powershell
# Find process
netstat -ano | findstr :8080

# Kill process (replace <PID> with actual number)
taskkill /PID <PID> /F
```

### Server Logs

The terminal shows access logs in real-time:
```
192.168.1.71 - - [13/Dec/2025 10:30:15] "GET /jacoco/index.html HTTP/1.1" 200 -
192.168.1.71 - - [13/Dec/2025 10:30:16] "GET /jacoco/style.css HTTP/1.1" 200 -
```

**Log format:**
```
<client-ip> - - [<timestamp>] "<method> <path> <protocol>" <status-code> -
```

**Common status codes:**
- `200` - Success
- `304` - Not Modified (cached)
- `404` - Not Found
- `500` - Server Error

---

## Allure Jetty Server

### Starting the Server

**Generate and serve reports:**
```powershell
gradle allureServe --no-configuration-cache
```

**What happens:**
1. Gradle generates Allure report from test results
2. Starts Jetty server on random available port (60000-65535)
3. Opens default browser automatically
4. Terminal shows server URL

**Expected output:**
```
Generating report to temp directory...
Report successfully generated to C:\Users\...\AppData\Local\Temp\...
Starting web server...
Server started at <http://192.168.1.71:64196>
```

### Finding the Server Port

**Check terminal output:**
Look for line: `Server started at <http://...>`

**Using netstat:**
```powershell
netstat -ano | findstr LISTENING | findstr java
```

**Output example:**
```
TCP    0.0.0.0:64196    0.0.0.0:0    LISTENING    12345
```

Port is `64196`, process ID is `12345`.

### Static Report Generation

**Generate without starting server:**
```powershell
gradle allureReport --no-configuration-cache
```

**Output location:**
```
app/build/allure-report/index.html
```

**Open manually:**
```powershell
start C:\AutomationProject\java\app\build\allure-report\index.html
```

**Note:** This uses `file://` protocol, which has CORS limitations. Some features won't work. Use `allureServe` instead.

### Stopping the Server

**If using allureServe:**
```
Press Ctrl+C in terminal
```

**Force kill Java processes:**
```powershell
taskkill /F /IM java.exe
```

**Kill specific Gradle process:**
```powershell
# Find Gradle process
Get-Process | Where-Object {$_.ProcessName -like "*java*"}

# Kill by PID
taskkill /PID <PID> /F
```

### Configuration

**Allure server port range:**
By default, Allure uses ports 60000-65535. To specify a port:

Create `allure.properties` in `src/test/resources/`:
```properties
allure.serve.port=8081
```

**Note:** Fixed ports can cause conflicts. Random assignment is recommended.

---

## Network Configuration

### Finding Your IP Address

**PowerShell:**
```powershell
ipconfig | Select-String "IPv4"
```

**Output example:**
```
IPv4 Address. . . . . . . . . . . : 192.168.1.71
```

**Using different method:**
```powershell
(Get-NetIPAddress -AddressFamily IPv4 | Where-Object {$_.IPAddress -like "192.168.*"}).IPAddress
```

### Firewall Configuration

**Allow Python HTTP Server (Port 8080):**

1. Open Windows Defender Firewall
2. Click "Advanced settings"
3. Click "Inbound Rules" → "New Rule"
4. Select "Port" → Next
5. Select "TCP", enter "8080" → Next
6. Select "Allow the connection" → Next
7. Check "Private" → Next
8. Name: "TCOE Python HTTP Server" → Finish

**Allow Allure Server (Dynamic Ports):**

1. Follow steps above, but select "Port Range"
2. Enter: "60000-65535"
3. Name: "TCOE Allure Server"

**Using PowerShell (Admin required):**
```powershell
# Allow port 8080
New-NetFirewallRule -DisplayName "TCOE HTTP Server" -Direction Inbound -LocalPort 8080 -Protocol TCP -Action Allow

# Allow Allure port range
New-NetFirewallRule -DisplayName "TCOE Allure Server" -Direction Inbound -LocalPort 60000-65535 -Protocol TCP -Action Allow
```

### Testing Network Access

**From another device (TV, phone):**
1. Ensure both devices on same WiFi network
2. Open browser on device
3. Navigate to: http://192.168.1.71:8080/jacoco/index.html

**If it doesn't load:**
- Check firewall settings
- Verify server is running: `netstat -ano | findstr :8080`
- Ping PC from device: `ping 192.168.1.71`
- Check WiFi network settings (guest networks may be isolated)

---

## Troubleshooting

### Port Already in Use

**Symptom:**
```
OSError: [WinError 10048] Only one usage of each socket address is normally permitted
```

**Solution 1: Find and kill process**
```powershell
# Find process using port
netstat -ano | findstr :8080

# Kill process
taskkill /PID <PID> /F

# Restart server
python -m http.server 8080
```

**Solution 2: Use different port**
```powershell
python -m http.server 8081
```

### Can't Access from TV/Phone

**Symptom:** Reports work on PC but not on other devices

**Diagnostics:**
```powershell
# 1. Verify server running
netstat -ano | findstr :8080

# 2. Check your IP
ipconfig | Select-String "IPv4"

# 3. Test firewall
Test-NetConnection -ComputerName 192.168.1.71 -Port 8080
```

**Solutions:**
1. Configure firewall (see Network Configuration above)
2. Ensure same WiFi network
3. Restart router if needed
4. Check WiFi isolation settings (disable guest network isolation)

### Allure Report Shows "Loading..."

**Symptom:** Allure opens but shows spinner forever

**Cause:** Using `file://` protocol instead of HTTP server

**Solution:**
```powershell
# Don't do this (file:// protocol)
start C:\AutomationProject\java\app\build\allure-report\index.html

# Do this instead (HTTP server)
gradle allureServe --no-configuration-cache
```

### Server Crashes or Stops

**Symptom:** Server exits unexpectedly

**Check logs:**
- Look at terminal output for error messages
- Check Windows Event Viewer (Application logs)

**Common causes:**
- Out of memory
- Python/Java process killed
- Network adapter disabled
- PC went to sleep

**Prevention:**
```powershell
# Prevent PC sleep while server runs
powercfg /change standby-timeout-ac 0
powercfg /change standby-timeout-dc 30

# Restore default (sleep after 30 min)
powercfg /change standby-timeout-ac 30
```

### Server Running but Reports Not Updating

**Symptom:** Old test results shown, new results not reflected

**For JaCoCo:**
```powershell
# Regenerate coverage report
gradle clean test jacocoTestReport

# Hard refresh browser
Ctrl+Shift+R (or Ctrl+F5)
```

**For Allure:**
```powershell
# Stop server
Ctrl+C

# Clean and regenerate
gradle allureReport --clean --no-configuration-cache
gradle allureServe --no-configuration-cache
```

---

## Monitoring Servers

### Check Server Status

**Python HTTP Server:**
```powershell
# Check if running
netstat -ano | findstr :8080

# Test response
curl http://localhost:8080/jacoco/index.html -UseBasicParsing
```

**Expected:** Should return HTML content

**Allure Server:**
```powershell
# Check if running
Get-Process | Where-Object {$_.ProcessName -like "*java*"}

# Test response (replace port)
curl http://localhost:64196 -UseBasicParsing
```

### Server Health Check Script

Create `check-servers.ps1`:
```powershell
# Check Python HTTP Server
$http_status = netstat -ano | findstr :8080
if ($http_status) {
    Write-Host "✓ Python HTTP Server: Running on port 8080" -ForegroundColor Green
} else {
    Write-Host "✗ Python HTTP Server: Not running" -ForegroundColor Red
}

# Check Allure Server
$allure_status = Get-Process | Where-Object {$_.ProcessName -eq "java"}
if ($allure_status) {
    Write-Host "✓ Allure Server: Running" -ForegroundColor Green
} else {
    Write-Host "✗ Allure Server: Not running" -ForegroundColor Red
}
```

**Run:**
```powershell
.\check-servers.ps1
```

---

## Server Management Commands

### Quick Reference

**Python HTTP Server:**
```powershell
# Start
cd C:\AutomationProject\java\app\build
python -m http.server 8080

# Stop
Ctrl+C

# Kill if stuck
taskkill /F /IM python.exe

# Check status
netstat -ano | findstr :8080
```

**Allure Server:**
```powershell
# Start
gradle allureServe --no-configuration-cache

# Stop
Ctrl+C

# Kill if stuck
taskkill /F /IM java.exe

# Check status
Get-Process java
```

**Both Servers:**
```powershell
# Start both (separate terminals)
# Terminal 1:
cd C:\AutomationProject\java\app\build
python -m http.server 8080

# Terminal 2:
cd C:\AutomationProject\java
gradle allureServe --no-configuration-cache

# Stop both
# Press Ctrl+C in each terminal
```

---

## Advanced Configuration

### Python Server with Custom MIME Types

Create `server.py`:
```python
import http.server
import socketserver

class MyHTTPRequestHandler(http.server.SimpleHTTPRequestHandler):
    extensions_map = {
        '.html': 'text/html',
        '.css': 'text/css',
        '.js': 'application/javascript',
        '.json': 'application/json',
    }

PORT = 8080
Handler = MyHTTPRequestHandler

with socketserver.TCPServer(("", PORT), Handler) as httpd:
    print(f"Serving at port {PORT}")
    httpd.serve_forever()
```

**Run:**
```powershell
python server.py
```

### Allure with Environment Info

Add `environment.properties` in `app/build/allure-results/`:
```properties
Browser=Chrome
OS=Windows 11
Java.Version=17
Stand=Test
```

This adds environment details to Allure report.

---

## Future Enhancements (Week 5+)

### Docker Containerization

Package servers in Docker containers:
```dockerfile
FROM python:3.11-slim
WORKDIR /reports
COPY app/build/jacoco ./jacoco
EXPOSE 8080
CMD ["python", "-m", "http.server", "8080"]
```

### Nginx Reverse Proxy

Route both servers through single port:
```nginx
server {
    listen 80;
    
    location /jacoco {
        proxy_pass http://localhost:8080;
    }
    
    location /allure {
        proxy_pass http://localhost:64196;
    }
}
```

### Cloud Hosting

Deploy reports to cloud (AWS S3, Azure Blob, Netlify):
- Static hosting
- No server management
- Global CDN distribution
- HTTPS by default

---

## Related Documentation

- [Daily Operations](Daily-Operations.md) - Daily workflows
- [Running Tests](../getting-started/Running-Tests.md) - Test execution
- [Common Issues](../troubleshooting/Common-Issues.md) - Troubleshooting
- [System Architecture](../architecture/System-Architecture.md) - Technical overview

---

**Last Updated:** December 13, 2025  
**Next Review:** December 20, 2025 (Week 2)  
**Maintainer:** TCOE Team