# Cloud-Native QA Automation Framework

## Overview
This is a **comprehensive QA automation framework** for web UI, API, and integration testing.  
It leverages **Java, Playwright, TestNG, RestAssured, and Allure** to deliver robust, maintainable, and scalable automation.

The framework is designed for **end-to-end testing** of web applications, including:

- **UI Tests**: Functional and data-driven validation of web pages.
- **API Tests**: REST API validation with request/response checks.
- **Integration Tests**: Data consistency validation between backend APIs and UI layers.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 22 |
| UI Automation | Playwright |
| API Testing | RestAssured, JSON Path |
| Test Framework | TestNG |
| Reporting | Allure Reports |
| Build & Dependency Management | Maven |
| CI/CD | GitHub Actions |

---
## Why This Framework?

This framework demonstrates real-world QA automation practices including:
- CI-first execution
- API + UI validation in a single pipeline
- Production-style reporting
- Secure handling of environment configuration

---
## Project Structure
```
cloud-native-qa-automation-framework
│
├── src
│   ├── test
│   │   ├── java
│   │   │   ├── api
│   │   │   │   └── ProductApiTests.java
│   │   │   ├── ui
│   │   │   └── UiApiDataConsistencyTests.java
│   │   └── resources
│   └── main
│
├── target
│   ├── allure-results
│   └── site
│       └── allure-maven-plugin
│
├── .github
│   └── workflows
│       └── qa-automation.yml
│
├── pom.xml
└── README.md
```
---

## Test Coverage

🔹 **API Tests**
- Endpoint accessibility validation
- HTTP status code verification (200 / 404)
- Mandatory field validation
- Negative test scenarios

🔹 **UI Tests**
- UI flow validation using Playwright
- Browser-based functional checks
- Headless execution for CI

🔹 **UI ↔ API Data Consistency**
- Price and data comparison between UI and API
- End-to-end validation of backend ↔ frontend integrity
---

## Running Tests Locally

1. **Prerequisites**

    Make sure you have:

   - Java 22 (LTS-ready configuration)
   - Maven 3.9+
   - Node.js 18+ (for Playwright)
   - Git

2. **Install dependencies**
    ```
    mvn clean install
    ```
3. **Install playwright browsers**
    ```
       mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install --with-deps"
    ```
4. **Run tests**
    ```
    mvn clean test
    ```
---

## Allure Reporting
Generate allure report
```
mvn allure:report
```
View allure report
```
cd target/site/allure-maven-plugin
allure open
```
⚠️ Do not double-click index.html.
Allure reports must be opened via a local web server.
---

## CI/CD with GitHub Actions
This framework runs automatically on:
- **Push to** main 
- **Manual trigger** (workflow_dispatch)

**CI Features**
- Headless Playwright execution
- API & UI tests
- Allure report generation
- Allure artifact upload

> Note: Tests run in headed mode locally (optional) and headless mode in CI for stability.

You can download the Allure report from:
```
GitHub → Actions → Workflow Run → Artifacts
```
---

## Environment Configuration

Sensitive values should be configured using GitHub Secrets:
- API_BASE_URL
- API_TOKEN / API_KEY

These are injected at runtime during CI execution.

---

## Key Design Principles

- Cloud-native & CI-friendly
- Headless browser execution 
- Clear separation of UI and API tests 
- Defensive assertions for stable CI runs 
- Enterprise-style reporting with Allure
---

## Future Enhancements
- GitHub Pages hosting for Allure reports 
- Parallel test execution 
- Dockerized execution 
- Contract testing integration 
- Environment-based test configuration

---

## 👤 Author

Abhijeet Singh

QA Automation Engineer | UI & API Automation | CI/CD | Playwright | RestAssured

GitHub: https://github.com/95abhijeet