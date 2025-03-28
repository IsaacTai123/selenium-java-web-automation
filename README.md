# Selenium Java Web Automation

Automated UI testing framework built with Java + Selenium + TestNG, designed to simulate user interactions across academic services at Northeastern University.

---

## 🧠 Description

This project automates the testing of several core student-facing web features including:

- Transcript downloads via Chrome DevTools Protocol (CDP)
- Library dataset file downloads
- Canvas calendar event creation
- Library study room reservation

It follows the Page Object Model (POM) pattern, with Excel-driven configuration to separate test data and locators from test logic. It also generates HTML reports using ExtentReports and includes built-in screenshot capturing at key checkpoints.

---

## 🏷️ Badges

![License](https://img.shields.io/github/license/IsaacTai123/selenium-java-web-automation)
![Repo size](https://img.shields.io/github/repo-size/IsaacTai123/selenium-java-web-automation)
![Last Commit](https://img.shields.io/github/last-commit/IsaacTai123/selenium-java-web-automation)

---

## 📸 Visuals

![Extent Report](images/extent_report.jpg)

![Extent Report](images/extent_report2.jpg)

![Duo Login](images/duo_authentication.png)

---

## ⚙️ Installation

🧱 Requirements

- Java 17+ (tested with Java 19)
- Maven
- Chrome (122) + matching ChromeDriver
- IntelliJ IDEA (or any IDE with TestNG support)

### 📥 Setup

1. Clone the repo:

    ```shell
    git clone https://github.com/IsaacTai123/selenium-java-web-automation.git
    cd selenium-java-web-automation
    ```

2. Download Chrome for Testing (version 122) and remember the path.
    - Change the configuration in BaseTest.java to point to the downloaded ChromeDriver. Line #60, #66

3. Gate your local chromedriver path in BaseTest.java.
4. (Important) Update dataTable.xlsx in src/test/resources with:
   - Your login username and password
   - Your own transcript preferences, URLs, and other test data
   (This is required for the tests to run properly)

---

## 🚀 Usage

To run all test scenarios: right-click on the `src/test/resources` and choose `testng.xml` and select "Run".

You can also run individual test classes via TestNG in your IDE.

## 📝 Reports

After running the tests, an HTML report will be generated under:

```plain
test-output/ExtentReport.html
```

Screenshots are saved under screenshots/{TestClassName}.

---

## 📂 Project Structure

```plain
├── README.md
├── images
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── isaactai
│   │   │           └── selenium
│   │   │               ├── base
│   │   │               │   ├── BasePage.java
│   │   │               │   └── BaseTest.java
│   │   │               ├── pages
│   │   │               │   ├── AcademicCalendarPage.java
│   │   │               │   ├── CanvasPage.java
│   │   │               │   ├── MicrosoftLoginPage.java
│   │   │               │   ├── NeuLibraryBostonPage.java
│   │   │               │   ├── NeuLoginPage.java
│   │   │               │   ├── SnellLibraryPage.java
│   │   │               │   ├── StudentHubPage.java
│   │   │               │   └── TranscriptPage.java
│   │   │               └── utils
│   │   │                   ├── AssertUtil.java
│   │   │                   ├── ExcelUtil.java
│   │   │                   ├── ExtentReportManager.java
│   │   │                   ├── FileUtil.java
│   │   │                   └── ScreenshotUtil.java
│   │   └── resources
│   └── test
│       ├── java
│       │   └── com
│       │       └── isaactai
│       │           └── selenium
│       │               └── tests
│       │                   ├── AcademicCalendarTest.java
│       │                   ├── CanvasCalendarTest.java
│       │                   ├── DatasetsTest.java
│       │                   ├── LoginTest.java
│       │                   ├── ReserveStudyRoomTest.java
│       │                   ├── SampleTest.java
│       │                   └── TranscriptTest.java
│       └── resources
│           ├── dataTable.xlsx
│           ├── simplelogger.properties
│           └── testing.xml
└── test-output
    └── ExtentReport.html

20 directories, 28 files

```
---

## 👤 Author

Isaac Tai

- 💻 Software Engineer