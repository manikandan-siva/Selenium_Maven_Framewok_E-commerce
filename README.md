# Selenium_Maven_framework_ecommerce - Just download and execute(Windows OS only)
This Selenium maven framework is to automate testing of e-commerce webpage functionality. 
You can just download and execute the framework with a single command(Make sure whether pre-requisites mentioned at the end are in place)

## Scenario-1
Read user details from excel and then create account for each user in an e-commerce portal and then validate the account creation

### Steps Automated:
1. Open URL
2. Click on sign in link.
3. Enter email address in 'Create and account' section.
4. Click on Create an Account button.
5. Enter personal details
6. Click on Register button.
7. Validate that user is created by checking the name in the loginpage.


## Folder structure and class details :

### src/main folder

1) **PageObjects** 
   - Each file contains webelements details about each page in e-commerce site(SiteHomePage -> CreateAccountPage -> PersonalInfoPage -> UserHomePage).
     - File design - Constructor to get the driver first, then webelement locator, methods to return that locator to test cases, hybrid methods to call other page object's class,which will be redirected from first pageobjects operations like login button in home screen to login screen and return corresponding object.

2) **Resources**
   - Base file to define driver, read property file, read input excel and take test reuslt screenshots.
     - Multi-browser(chrome or firefox) and multi-execution feature(UI or headless) added in driver initiation
   - ExtentreporterNG file containing the configuration details of extent reports.
   - Chrome and gecko exe driver files.
   - Data property with generic parameters like URL
   - log4j2 xml file to configure log capture set-up

### src/test folder 

3) **Listeners** 
   - Handled screenshot capture and intergrating it with extent reports functionality after every test success or failure.

4) **UsrAccCreateAndValdTest** 
   - TestNG file with actual test case having step-by-step execution.
   - DataProvider is utilized to multi-run a same method for different inputs.

### Other folder

5) **Testdata** 
   - Contains source excel file.

6) **Reports** 
   - Holds generated extent html report.
   - Stores captured screenshots for each test run.

7) **logs** 
   - log file for each run are generated here.
   
8) **target** 
   - Maven auto-generated folder with test resultes
     - surifire reports folder - contains auto generated TestNG reports, including an email formatted one. 
	 
### Other config files
9) **pom xml** 
   - Contatins all java library which we used and TestNG & log4j config file linkage
   
10) **testng xml** 
   - Contains test case details
	


## To execute framework from command prompt
Download the main folder Selenium_Maven_framework_E-commerce from Git.(Click on Code (green)button->Download Zip)
Navigate to the main folder downloaded (```bash cd C:\Users\...\Downloads\Selenium_Maven_framework_E-commerce-main```) and then execute below commands based on the options provided.

**Using Chrome**
**UI mode**
```bash 
mvn -DBrowser="chrome" test
```

***headless mode***
```bash
mvn -DBrowser="chromeheadless" test
```

**Using Firefox**
**UI mode**
```bash
mvn -DBrowser="firefox" test
```

**headless mode**
```bash
mvn -DBrowser="firefoxheadless" test
```


## Pre-requisite
1) **Java jdk-14.0.2** 
2) **apache-maven-3.6.3 and above** 
3) **Set java and maven home path in environment variable**
4) **Add java and maven bin path in path environment variable**
5) **Chrome or firefox app in windows**

