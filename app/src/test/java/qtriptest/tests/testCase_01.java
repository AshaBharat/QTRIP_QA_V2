package qtriptest.tests;

import qtriptest.DP;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class testCase_01 {
    static RemoteWebDriver driver;
    static String loginusername;
   
    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException{
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        System.out.println("Browser created");
        driver.manage().window().maximize();
        System.out.println("Window maximised");
    }

    @Test(description="Verify User Registration - login - logout", dataProvider= "data-provider" , dataProviderClass = DP.class, priority = 1 , enabled = true)
    public static void TestCase01(String username, String password) throws InterruptedException{
        try{
            HomePage home = new HomePage(driver);
            home.navigateToHomePage();
            home.clickRegister();
            Thread.sleep(5000);
            Assert.assertTrue(driver.getCurrentUrl().equals(RegisterPage.registerurl));
            System.out.println("navigated to Register page");

            RegisterPage register = new RegisterPage(driver);
            register.registerUser(username, password, password, true);
            Thread.sleep(5000);
            loginusername = RegisterPage.lastgeneratedemailid;
            Assert.assertTrue(driver.getCurrentUrl().endsWith("login"));
            System.out.println("navigated to login page");

            //loginusername = RegisterPage.lastgeneratedemailid;
            LoginPage login = new LoginPage(driver);
            login.loginpage(loginusername, password);
            Thread.sleep(5000);

            Assert.assertTrue(home.verifyuserloggedin());
            System.out.println("successfully logged in");

            home.clickLogout();
            Thread.sleep(2000);
            Assert.assertFalse(home.verifyuserloggedin());
            System.out.println("successfully logged out");
            System.out.println("testcase01 executed successfully");
        }
        catch(Exception e){
            System.out.println("error in testcase01");
        }
    }

}
