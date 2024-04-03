package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class testCase_01 {
    static RemoteWebDriver driver;
    static String loginusername;
    static ExtentReports report;
    static boolean status;
    static ExtentTest test;
   
    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException{
        ReportSingleton RS = ReportSingleton.getInstanceOfSingletonReportClass();
        report = RS.getReport();
                
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();
    }

    @Test(description="Verify User Registration - login - logout", dataProvider= "data-provider" , dataProviderClass = DP.class, priority = 1 , enabled = true, groups = {"Login Flow"})
    public static void TestCase01(String username, String password) throws InterruptedException, IOException{
        try{
            test = report.startTest("TestCase01");
            HomePage home = new HomePage(driver);
            status = home.navigateToHomePage();
            if(status){
                test.log(LogStatus.PASS, "navigated to home page successfully");
            }else{
                test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) ,"navigated to home page unsuccessful");
            }
            status = home.clickRegister();
            if(status){
                test.log(LogStatus.PASS, "navigated to Register page successfully");
            }else{
                test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) ,"navigated to Register page unsuccessful");
            }
            
            Assert.assertTrue(driver.getCurrentUrl().equals(RegisterPage.registerurl));
            //System.out.println("navigated to Register page");

            RegisterPage register = new RegisterPage(driver);
            status = register.registerUser(username, password, password, true);
            loginusername = RegisterPage.lastgeneratedemailid;
            if(status){
                test.log(LogStatus.PASS, "navigated to login page successfully");
            }else{
                test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) ,"navigated to login page unsuccessful");
            }
            Assert.assertTrue(driver.getCurrentUrl().endsWith("login"));
            //System.out.println("navigated to login page");

            //loginusername = RegisterPage.lastgeneratedemailid;
            LoginPage login = new LoginPage(driver);
            login.loginpage(loginusername, password);
            status = home.verifyuserloggedin();
            if(status){
                test.log(LogStatus.PASS, "successfully logged in");
            }else{
                test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) ,"unsuccessfully logged in");
            }
            Assert.assertTrue(home.verifyuserloggedin(),"unsuccessfully logged in");
            //System.out.println("successfully logged in");

            home.clickLogout();
            Thread.sleep(2000);
            status = home.verifyuserloggedout();
            if(status){
                test.log(LogStatus.PASS, "successfully logged out");
            }else{
                test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) ,"unsuccessfully logged out");
            }
            Assert.assertTrue(home.verifyuserloggedout());
            test.log(LogStatus.PASS, "testcase01 executed successfully");
            //System.out.println("successfully logged out");
            //System.out.println("testcase01 executed successfully");
        }
        catch(Exception e){
            test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) ,"Error in TestCase01");
            System.out.println(e.getMessage());
        }
    }
    

}
