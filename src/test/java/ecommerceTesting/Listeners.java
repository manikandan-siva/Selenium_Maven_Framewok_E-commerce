package ecommerceTesting;

import java.io.IOException;

import org.openqa.selenium.WebDriver; //selenium class
//ITest infos
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports; // Extentreports class access
import com.aventstack.extentreports.ExtentTest; //ExtentTest class access
import com.aventstack.extentreports.Status;//to get status class to capture pass or fail

import resources.*; // to get base details


//inheriting base to access driver
//implementing testng listener interface
public class Listeners extends Base implements ITestListener {
	
	//declaring all global to access it in all scenarios - pass, fail, skip
	ExtentReports finalReport = ExtentReporterNG.extentReporterConfig(); 
	ExtentTest testStepLogger;
	
	//in case of parallel run(when thread>1), then we need to make sure overriding of variables across multiple runs
	ThreadLocal<ExtentTest> localTest = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		//String name = "name";
		int i=0;
		String testName = result.getMethod().getMethodName();
		try {
			//name = (String)result.getTestClass().getRealClass().getDeclaredField("usr").get(result.getInstance());
			i = (int)result.getTestClass().getRealClass().getDeclaredField("i").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//testStepLogger = finalReport.createTest(result.getMethod().getMethodName()); 
		testStepLogger = finalReport.createTest(testName+"_"+ i);//creating test report in the test method name
		//assigning to the local thread
		localTest.set(testStepLogger);
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
		WebDriver localDriver = null; // initialising so that we can get the driver from testmethod and assign to base for screenshot
		String name = "name";
		String testName = result.getMethod().getMethodName();
		//String testName1= result.getMethod().getId();
		try { //exception handling
			//assigning driver from test class to base class at that moment
			localDriver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("testDriver").get(result.getInstance());
			  name = (String)result.getTestClass().getRealClass().getDeclaredField("usr").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//adding screenshot to the report
		try {//IO exception
			//localTest.get().addScreenCaptureFromPath(getScreenshot(testName.concat(testName1), localDriver));
			localTest.get().addScreenCaptureFromPath(getScreenshot(testName+" "+ name, localDriver));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		localTest.get().log(Status.PASS, "User Created and validated");
		finalReport.flush();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		String name = "name";
		WebDriver localDriver = null; // initialising so that we can get the driver from testmethod and assign to base for screenshot
		String testName = result.getMethod().getMethodName();
		try { //exception handling
			//assigning driver from test class to base class at that moment
			localDriver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("testDriver").get(result.getInstance());
			name = (String)result.getTestClass().getRealClass().getDeclaredField("usr").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//adding screenshot to the report
		try {//IO exception
			localTest.get().addScreenCaptureFromPath(getScreenshot(testName+" "+ name, localDriver));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		localTest.get().fail(result.getThrowable());
		finalReport.flush();

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		//to write test logs captured in the report
		//finalReport.flush();
	}
	
	public void captureInfo()
	{
		
	}

}
