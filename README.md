# Hybrid Automation Framework v1

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Framework](https://img.shields.io/badge/Framework-Hybrid-blue)](https://github.com/topics/test-automation)
[![Version](https://img.shields.io/badge/Version-1.0-green)](https://github.com/yourusername/hybrid-automation-framework-v1)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen)](https://github.com/yourusername/hybrid-automation-framework-v1)

Welcome to the **Hybrid Automation Framework version 1** repository! This framework provides a versatile solution for automating testing tasks by combining the strengths of both **keyword-driven** and **data-driven** approaches, delivering maximum flexibility and maintainability for your test automation needs.

## 🎯 Overview

The Hybrid Automation Framework v1 is designed to provide QA teams with a comprehensive, scalable, and maintainable test automation solution. By leveraging the best practices from both keyword-driven and data-driven methodologies, this framework offers unparalleled flexibility for testing web applications, APIs, mobile apps, and more.

## ✨ Key Features

### 🔧 Hybrid Architecture
- **Keyword-Driven Testing**: Reusable keywords for common test actions
- **Data-Driven Testing**: External data sources for test parameterization
- **Modular Design**: Easily extensible and maintainable components
- **Cross-Platform Support**: Web, API, Mobile, and Desktop testing

### 🚀 Advanced Capabilities
- **Multi-Browser Support**: Chrome, Firefox, Safari, Edge
- **Parallel Execution**: Run tests concurrently for faster feedback
- **CI/CD Integration**: Jenkins, GitHub Actions, Azure DevOps ready
- **Comprehensive Reporting**: HTML, XML, and custom report formats
- **Screenshot & Video Capture**: Automatic failure documentation
- **Database Testing**: Built-in database validation capabilities

### 📊 Smart Test Management
- **Dynamic Test Data**: Runtime data generation and management
- **Environment Configuration**: Multiple environment support
- **Test Prioritization**: Critical path and risk-based testing
- **Retry Mechanisms**: Automatic retry for flaky tests
- **Real-time Monitoring**: Live test execution tracking

## 🏗️ Framework Architecture

```
hybrid-automation-framework-v1/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   ├── 📁 keywords/          # Keyword implementations
│   │   │   ├── 📁 utilities/         # Helper utilities
│   │   │   ├── 📁 drivers/           # WebDriver management
│   │   │   ├── 📁 pages/             # Page Object Models
│   │   │   └── 📁 config/            # Configuration management
│   │   └── 📁 resources/
│   │       ├── 📁 testdata/          # Test data files
│   │       ├── 📁 keywords/          # Keyword definitions
│   │       └── 📁 config/            # Environment configs
│   └── 📁 test/
│       ├── 📁 java/
│       │   ├── 📁 testcases/         # Test case implementations
│       │   ├── 📁 suites/            # Test suite definitions
│       │   └── 📁 listeners/         # Test listeners
│       └── 📁 resources/
│           ├── 📁 testsuites/        # TestNG XML files
│           └── 📁 reports/           # Generated reports
├── 📁 drivers/                       # Browser drivers
├── 📁 logs/                          # Execution logs
├── 📁 screenshots/                   # Test screenshots
├── 📁 reports/                       # Test reports
├── 📄 pom.xml                        # Maven dependencies
├── 📄 testng.xml                     # TestNG configuration
└── 📄 config.properties              # Global configuration
```

## 🚀 Getting Started

### Prerequisites
- **Java 8+** - Programming language
- **Maven 3.6+** - Build and dependency management
- **TestNG** - Testing framework
- **Selenium WebDriver** - Web automation
- **Git** - Version control

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/hybrid-automation-framework-v1.git
   cd hybrid-automation-framework-v1
   ```

2. **Install dependencies**:
   ```bash
   mvn clean install
   ```

3. **Configure environment**:
   ```bash
   # Copy sample configuration
   cp config.properties.sample config.properties
   
   # Edit configuration file
   nano config.properties
   ```

4. **Download browser drivers**:
   ```bash
   # Automatic driver management (recommended)
   mvn test -Dtest=DriverSetup
   ```

### Quick Start Example

```java
// Sample test using hybrid approach
@Test(dataProvider = "loginData")
public void testUserLogin(String username, String password, String expectedResult) {
    // Keyword-driven actions
    KeywordLibrary.openBrowser("chrome");
    KeywordLibrary.navigateToURL("https://example.com/login");
    KeywordLibrary.enterText("username", username);
    KeywordLibrary.enterText("password", password);
    KeywordLibrary.clickElement("loginButton");
    
    // Data-driven validation
    String actualResult = KeywordLibrary.getText("welcomeMessage");
    Assert.assertEquals(actualResult, expectedResult);
    
    KeywordLibrary.closeBrowser();
}
```

## 📋 Framework Components

### 1. Keyword Library
Pre-built keywords for common test actions:
- **Navigation**: `openBrowser()`, `navigateToURL()`, `closeBrowser()`
- **Interactions**: `clickElement()`, `enterText()`, `selectDropdown()`
- **Validations**: `verifyText()`, `verifyElementPresent()`, `verifyTitle()`
- **Waits**: `waitForElement()`, `waitForPageLoad()`, `implicitWait()`

### 2. Data Management
- **Excel Integration**: Read/write test data from Excel files
- **JSON Support**: JSON-based test data management
- **Database Connectivity**: Direct database operations
- **API Data**: Fetch test data from REST APIs

### 3. Reporting System
- **ExtentReports**: Rich HTML reports with screenshots
- **TestNG Reports**: Built-in TestNG reporting
- **Allure Integration**: Advanced reporting with trends
- **Custom Dashboards**: Real-time test execution monitoring

### 4. Utility Classes
- **Screenshot Utility**: Automatic screenshot capture
- **Email Utility**: Send test reports via email
- **File Utility**: File operations and management
- **Date Utility**: Date/time operations for testing

## 🔧 Configuration

### Environment Configuration
```properties
# Browser Configuration
browser=chrome
headless=false
implicit.wait=10
explicit.wait=20

# Application URLs
app.url.dev=https://dev.example.com
app.url.staging=https://staging.example.com
app.url.prod=https://prod.example.com

# Database Configuration
db.url=jdbc:mysql://localhost:3306/testdb
db.username=testuser
db.password=testpass

# Reporting Configuration
reports.path=./reports
screenshots.path=./screenshots
enable.screenshots=true
```

### TestNG Configuration
```xml
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="HybridAutomationSuite" parallel="methods" thread-count="3">
    <test name="RegressionTests">
        <classes>
            <class name="com.automation.tests.LoginTests"/>
            <class name="com.automation.tests.CheckoutTests"/>
            <class name="com.automation.tests.UserManagementTests"/>
        </classes>
    </test>
</suite>
```

## 🧪 Test Execution

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test suite
mvn test -DsuiteXmlFile=testng.xml

# Run tests with specific browser
mvn test -Dbrowser=firefox

# Run tests in headless mode
mvn test -Dheadless=true

# Run tests with custom environment
mvn test -Denvironment=staging

# Parallel execution
mvn test -Dparallel=methods -DthreadCount=5
```

### Command Line Options
- `-Dbrowser=chrome|firefox|safari|edge` - Browser selection
- `-Denvironment=dev|staging|prod` - Environment selection
- `-Dheadless=true|false` - Headless execution
- `-DthreadCount=n` - Number of parallel threads
- `-Dgroups=smoke|regression|sanity` - Test group execution

## 📊 Reporting & Analytics

### Report Types
1. **ExtentReports**: Interactive HTML reports with charts and graphs
2. **TestNG Reports**: Standard TestNG HTML and XML reports
3. **Allure Reports**: Advanced reporting with historical trends
4. **Custom Dashboards**: Real-time execution monitoring

### Sample Report Features
- Test execution summary with pass/fail statistics
- Detailed test steps with screenshots
- Performance metrics and execution time analysis
- Browser and environment information
- Failed test analysis with error details
- Historical trend analysis

## 🔄 CI/CD Integration

### Jenkins Pipeline
```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/yourusername/hybrid-automation-framework-v1.git'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn clean test -Dbrowser=chrome -Dheadless=true'
            }
        }
        stage('Reports') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'index.html',
                    reportName: 'Automation Test Report'
                ])
            }
        }
    }
}
```

### GitHub Actions
```yaml
name: Automated Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
    - name: Run tests
      run: mvn clean test -Dbrowser=chrome -Dheadless=true
    - name: Upload reports
      uses: actions/upload-artifact@v2
      with:
        name: test-reports
        path: reports/
```

## 🤝 Contributing

We welcome contributions to improve the Hybrid Automation Framework! Here's how you can contribute:

### Getting Started
1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/amazing-feature`
3. **Make your changes**: Add new keywords, utilities, or improvements
4. **Write tests**: Ensure your changes are well-tested
5. **Commit your changes**: `git commit -m 'Add amazing feature'`
6. **Push to the branch**: `git push origin feature/amazing-feature`
7. **Open a Pull Request**

### Contribution Guidelines
- Follow existing code style and conventions
- Add comprehensive documentation for new features
- Include unit tests for new functionality
- Update README.md if necessary
- Ensure all tests pass before submitting PR

### Types of Contributions Welcome
- New keyword implementations
- Additional utility functions
- Enhanced reporting features
- Performance improvements
- Bug fixes and optimizations
- Documentation improvements

## 📚 Documentation

### Additional Resources
- **[Wiki](https://github.com/yourusername/hybrid-automation-framework-v1/wiki)** - Detailed documentation
- **[API Documentation](https://yourusername.github.io/hybrid-automation-framework-v1/)** - JavaDoc API reference
- **[Examples](./examples/)** - Sample test implementations
- **[Best Practices](./docs/best-practices.md)** - Framework usage guidelines

### Tutorials
- [Getting Started Guide](./docs/getting-started.md)
- [Writing Your First Test](./docs/first-test.md)
- [Advanced Framework Features](./docs/advanced-features.md)
- [CI/CD Integration Guide](./docs/cicd-integration.md)

## 🐛 Troubleshooting

### Common Issues
1. **Driver Issues**: Ensure correct driver versions for your browser
2. **Element Not Found**: Check element locators and wait conditions
3. **Timeout Errors**: Adjust wait times in configuration
4. **Parallel Execution Issues**: Review thread-safe implementations

### Support
- **Issues**: [GitHub Issues](https://github.com/yourusername/hybrid-automation-framework-v1/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yourusername/hybrid-automation-framework-v1/discussions)
- **Wiki**: [Framework Wiki](https://github.com/yourusername/hybrid-automation-framework-v1/wiki)

## 📈 Roadmap

### Version 1.1 (Upcoming)
- [ ] Mobile testing support (Appium integration)
- [ ] API testing enhancements
- [ ] Advanced reporting features
- [ ] Performance testing capabilities

### Version 1.2 (Future)
- [ ] AI-powered test generation
- [ ] Visual testing integration
- [ ] Cloud testing platform support
- [ ] Advanced analytics and insights

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Selenium Community** - For the robust WebDriver framework
- **TestNG Team** - For the powerful testing framework
- **ExtentReports** - For beautiful test reporting
- **Contributors** - Thank you to all contributors who help improve this framework

## 📞 Contact & Support

- **Author**: [Your Name](https://github.com/yourusername)
- **Email**: your.email@example.com
- **LinkedIn**: [Your LinkedIn Profile](https://linkedin.com/in/yourprofile)
- **Project Link**: [https://github.com/yourusername/hybrid-automation-framework-v1](https://github.com/yourusername/hybrid-automation-framework-v1)

---

**⭐ Star this repository if you find it helpful!**

*Built with ❤️ for the QA automation community*
