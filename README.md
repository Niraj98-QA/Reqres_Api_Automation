# API Automation Framework – Rest Assured + TestNG

This is a scalable API automation framework built with Java, Rest Assured, and TestNG.  
It includes reporting, logging, data-driven testing, and CI/CD integration.

---

## 📌 Features
✅ Modular design with reusable utilities  
✅ TestNG based with `@DataProvider` for data-driven testing  
✅ Centralized Routes management  
✅ Custom Logging with Request/Response filters  
✅ Extent Reports and Allure integrated  
✅ Retry logic for flaky tests  
✅ Jenkins pipeline ready (Jenkinsfile included)  
✅ Example tests: Auth, User CRUD, Negative, Performance  
✅ WireMock for mocking external services

---

## 📂 Project Structure
```
src
├── main
│ ├── java
│ │ ├── Core # BaseTest and setup
│ │ ├── Enum # StatusCode
│ │ ├── Pojo # Request/response POJOs
│ │ └── Utils # Routes, Logging, Report Manager, etc.
│ └── resources # Config and data
└── test
├── java
│ ├── AuthTests.java
│ ├── UserTests.java
│ ├── NegativeTests.java
│ ├── PerformanceTests.java
│ └── MockTests (WireMock)
└── resources
└── allure.properties
```
---

## How to Run
1. Clone this repo:

```bash
git clone <https://github.com/Niraj98-QA/Reqres_Api_Automation.git>
```

## Run tests with Maven:

```bash
mvn clean test
```

## View Allure Report:

```bash
allure serve allure-results
```
📊 Sample Reports

## Allure report screenshots

```markdown
<img width="1800" height="1039" alt="Screenshot 2025-07-27 at 11 07 33 AM" src="https://github.com/user-attachments/assets/03600886-68e9-4d70-b58a-ea3da451f3c1" />
<img width="1800" height="1040" alt="Screenshot 2025-07-27 at 11 07 56 AM" src="https://github.com/user-attachments/assets/af00af40-caf8-4ca3-aff9-dbcef4fb9f4a" />
<img width="1800" height="1038" alt="Screenshot 2025-07-27 at 11 08 05 AM" src="https://github.com/user-attachments/assets/f2664995-aa19-48a9-8f3a-adfde0de6ae4" />
```

## Extent report screenshots
```markdown
<img width="1800" height="1034" alt="Screenshot 2025-07-27 at 11 11 56 AM" src="https://github.com/user-attachments/assets/62d859cf-3c14-490c-b3ae-9bdcc48c3844" />
<img width="1800" height="1037" alt="Screenshot 2025-07-27 at 11 12 04 AM" src="https://github.com/user-attachments/assets/11127d9a-bda9-42ef-ae5b-905f566c51b4" />
```

## Tech Stack
```
Language: Java
Build Tool: Maven
Test Runner: TestNG
Frameworks: Rest Assured, WireMock
Reports: Allure, Extent
CI/CD: Jenkinsfile included
```
