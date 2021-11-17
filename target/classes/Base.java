package resources;

import java.io.File; //to 
import java.io.FileInputStream; //to read files
import java.io.IOException;
import java.util.Properties; //to read values from files and to assign
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.*; //EXCEL READ CLASSES
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot; //selenium webdriver to take screenshots
import org.openqa.selenium.WebDriver; // selenium webdriver to use webdriver interface
import org.openqa.selenium.chrome.ChromeDriver; //to use chromedriver class to execute cases in chrome
import org.openqa.selenium.chrome.ChromeOptions; //to set options in chrome like headless
import org.openqa.selenium.firefox.FirefoxDriver; //to use firefoxdriver class to execute cases in FF
import org.openqa.selenium.firefox.FirefoxOptions; //to set options in firefox like headless

//public here so that we can access driver methods in our test cases and other classes
 public class Base {
	 
	 //Declaring it protected to restrict it till package-level
	 protected WebDriver baseDriver; //to initiate webdriver
	 protected Properties baseProp; //to read file with basic property details like browser, URL in base class and test case classes(different package - reason for protected)
	 
	 //method to initiate driver with its options and return the driver
	 //if config file is not found or issue during reading value from file then throws keyword will handle it(FileInputStream,load)
	 protected WebDriver driverInit() throws IOException 
	 {
		
		 //Reading browser at runtime from maven
		 String browser = System.getProperty("Browser");
		 
		 //assigning driver based on the browser value we get
		 if (browser.contains("chrome"))
		 {
			 //Assigning parameterized browser driver property file
			 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\main\\java\\resources\\chromedriver.exe");
			 
			 //to set options like headless
			 ChromeOptions options = new ChromeOptions();
			 if (browser.contains("headless"))
				 options.addArguments("headless");
			 
			 baseDriver = new ChromeDriver(options);			 
		 }
		 else if (browser.contains("firefox"))
		 {
			 System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\main\\java\\resources\\geckodriver.exe");
			 FirefoxOptions options = new FirefoxOptions();
			 if (browser.contains("headless"))
				 options.addArguments("headless"); //reuse the code
			 baseDriver = new FirefoxDriver(options);
		 }
		 
		 baseDriver.manage().window().maximize();
		 baseDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); //implicit wait for entire framework
		 
		 return baseDriver;
	 }
	 
	 //common function to read data from properties file like URL
	 protected Properties readPropFile() throws IOException
	 {
		 
		 //Reading prop file to get browser and URL
		 FileInputStream configFile = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\data.properties");
		 
		 //Reading the properties from the config file
		 baseProp = new Properties();
		 baseProp.load(configFile);
		 return baseProp;
	 }

	 //Take screenshot and return the screenshot file path	
	 //if any issue during writing the capture to screenshot file, it is handled using throws keyword(fileutils)
	 protected String getScreenshot(String fileName, WebDriver localDriver) throws IOException
	 {
		 //take screenshot and store it virtually
		 File capture = ((TakesScreenshot)localDriver).getScreenshotAs(OutputType.FILE);
		 String filePath = System.getProperty("user.dir")+"\\reports\\"+fileName+".png";
		 FileUtils.copyFile(capture, new File(filePath));
		 return filePath;
	 }
	 
	 //To read excel file
	 //io found to handle  input issues FileINputStream,WORKBOOK
	 //return multi dimensional array
	 //Declaring them global to access across methods
	 XSSFWorkbook book;
	 XSSFSheet sheet;
	 
	 
	 protected String[][] excelRead(String filePath, String sheetName) throws IOException {
		 
		 //to store values from excel
		 //String usrInfo[][]=null;
		 
		 //locating  file 
		 FileInputStream usrInfoXl = new FileInputStream(filePath);
		 
		 //reading workbook
		  book = new XSSFWorkbook(usrInfoXl);
		 //Go to the sheet
		  sheet = book.getSheet(sheetName);
		 
		  //variables to decide the position of object and maxrow and col and var to store data read from excel
		 int ci=0,maxRow = sheet.getLastRowNum(),maxCol = sheet.getRow(1).getLastCellNum();
				 //maxRow = 1, maxCol = 11;//
		 String usrInfo[][] = new String[maxRow][maxCol-1];
		 
		 for(int i=1; i<maxRow+1; i++,ci++)
		 {
			 int cj=0;
			 for(int j=1; j<maxCol; j++,cj++)
			 {
				 
				 usrInfo[ci][cj] = getCellData(i,j);
			 }
		 }
		 		 
		 return usrInfo;
		 
		 
	 }
	 
	 //to read a cell in excel
	 protected String getCellData(int i, int j) {
		 //to get the specific record
		 XSSFCell cell = sheet.getRow(i).getCell(j); 
		 
		 //to store it as string
		 DataFormatter formatter = new DataFormatter();
		 String cellData = formatter.formatCellValue(cell);
		 //String cellData = cell.getStringCellValue();
		 return cellData;
	 }
	 
 }
