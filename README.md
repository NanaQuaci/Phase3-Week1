
📊 CI Status
🧪 Dockerized UI Automation Testing – Swag Labs Web App

This project showcases Dockerized UI automation testing using Selenium WebDriver with the Page Object Model (POM) design pattern. It automates essential user flows such as login and cart interactions and integrates Allure Reports for detailed result visualization.

![UI Tests](https://github.com/NanaQuaci/Phase3-Week1/actions/workflows/run-tests.yml/badge.svg)

🚀 Technologies Used

- Java 21
- Selenium WebDriver – UI automation framework
- JUnit 5 – Test framework
- Allure – Report generation
- Maven – Build and dependency management
- Docker – Containerization
- Git & GitHub – Version control and CI/CD

📦 Project Structure

dockerization/
├── src
│   ├── main
│   │   └── java
│   │       └── com.example
│   │           ├── base       # BasePage class
│   │           └── pages      # Page classes (LoginPage, CartPage)
│   └── test
│       └── java
│           └── com.example
│               ├── base       # BaseTest class
│               ├── testdata   # Test data constants
│               └── tests      # LoginTest, CartTest
├── allure-results             # Allure test result outputs
├── Dockerfile                 # Docker instructions
├── .github/workflows          # GitHub Actions CI configuration
├── pom.xml                    # Maven dependencies and plugins
└── README.md

✅ Test Coverage

🔐 Login Feature
- Valid login with correct credentials
- Invalid login with:
    - Wrong username
    - Wrong password
    - Empty fields
    - Username only
    - Password only

🛒 Cart Feature
- Add item to cart
- Remove item from cart
- Verify cart badge updates
- Remove item without visiting cart page
- Checkout button visibility

📄 How to Run Tests

Locally (if not using Docker):
```
mvn clean test
```

Using Docker:
```
docker build -t swaglabs_ui_tests .
docker run --rm swaglabs_ui_tests
```

📊 How to Generate Allure Report

Serve the report locally:
```
allure serve allure-results
```

📊 CI/CD Integration

Allure reports are automatically generated and published to GitHub Pages via GitHub Actions.

🌐 View Deployed Report (CI)
➡️ [Allure Report (GitHub Pages)](https://nanaquaci.github.io/Phase3-Week1/)

🔗 Useful Links

- [Swag Labs Demo Site](https://www.saucedemo.com/)
- [Selenium WebDriver Docs](https://www.selenium.dev/documentation/)
- [Allure Documentation](https://allurereport.org/docs/)
- [JUnit](https://docs.junit.org/current/user-guide/)

👤 Author: Nana Quaci

Week 1 Lab Activity – Phase 3
Graduate Trainee Program

🏁 License
This project is for educational and demonstration purposes only.
