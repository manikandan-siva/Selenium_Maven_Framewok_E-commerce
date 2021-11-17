package ecommerceTesting;




import java.io.IOException;

import org.apache.logging.log4j.*; // to import all log4j classes
import org.openqa.selenium.*; //to import all selenium classes
import org.openqa.selenium.support.ui.Select; //for drop down operations
import org.testng.annotations.*; // to use all testng classes
import org.testng.annotations.Test;


import pageObjects.*; //accessing page object factory
import resources.*; //accessing base cls for driver details
 
public class UsrAccCreateAndValdTest extends Base {
    
	public Logger logs = LogManager.getLogger(UsrAccCreateAndValdTest.class.getName());
	//Logger logs; //logger variable
	WebDriver testDriver; 
	String usr; //to capture user name to add it in the report
	int i=0; //to differentiate the tab name for each run of the test in the report
	
	@BeforeMethod
	public void testDriverInit() throws IOException
	{
		logs.info("Initiating driver");		
		testDriver = driverInit();
		logs.info("Driver initiation comlplete"); //did you find it?
		i++;
	}
	
	@Test(dataProvider="userinfoxl")
	public void newUsrTest(String email, String title, String firstname, String lastname, String pwd,
    		String addrs, String city, String state, String post, String addninfo, String mobile ) throws IOException, InterruptedException{
	
	usr = firstname + " " + lastname;	
	logs.info("Opening URL - " + readPropFile().getProperty("URL"));
	testDriver.get(readPropFile().getProperty("URL"));
	logs.info("URL loaded");
	
	UserHomePage usrHome; 
	SiteHomePage home = new SiteHomePage(testDriver); //accessing site home page
	logs.info("Clicking on Sign in");
	
	Thread.sleep(2000); //Before moving to next page- for initial phase- need to handle better
	CreateAccountPage acc = home.signIn(); //signing in
	logs.info("Entering Email details "+email);
	acc.putEmail().sendKeys(email); //Entering email
	logs.info("Registering");
	PersonalInfoPage usrDet = acc.register(); //accesing user registeration page
	
	Thread.sleep(2000);
	logs.info("Entering user details for "+title+" "+firstname+" "+lastname);
	if (title.trim().equals("Mr")) //Title
		usrDet.selectMr().click();
	else
		usrDet.selectMrs().click();
	
	usrDet.putFirstName().sendKeys(firstname); //firstname
	usrDet.putLastName().sendKeys(lastname); //lastname
	usrDet.putPwd().sendKeys(pwd); //password
	
	logs.info("Entering addrs-"+addrs+" city-"+city+" state-"+state+" Postal code-"+post);
	usrDet.putAddrs().sendKeys(addrs); //address
	usrDet.putCity().sendKeys(city); //city
	
	Select dd = new Select(usrDet.putState()); //selecting state from list
	dd.selectByVisibleText(state);
	
	usrDet.putPost().sendKeys(post);  //postal code
	logs.info("Entering addninfo "+addninfo);
	usrDet.putOther().sendKeys(addninfo); //additional info
	logs.info("Entering  mobile no "+mobile);
	usrDet.putMobile().sendKeys(mobile); //mobile no
	logs.info("Submitting user details");
	usrHome = usrDet.submit(); //submitting and accessing the user home page
	
	Thread.sleep(2000);
	logs.info("Validating user creation");
    //if (lastname.contains(usrHome.getUserTitle().getText())) 
    if ((usrHome.getUserTitle().getText()).contains(lastname)) 
		logs.info("User creation success");
    else
    	logs.error("User creation failed");
    usrHome.clickPersonalLink(); //to take screenshot of all details entered going to user info page
    }

	//to read excel
	@DataProvider
    public String[][] userinfoxl() throws IOException
    {
    	String[][] usrdetails = excelRead(System.getProperty("user.dir")+"\\testData\\Userdata.xlsx","Sheet1");
    	return usrdetails;
    }
    
    
    @AfterMethod
    public void driverclose() {
    	testDriver.close();
    }

}

