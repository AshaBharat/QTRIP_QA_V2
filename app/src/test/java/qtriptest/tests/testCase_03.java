package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.Alert;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_03 {
    public static RemoteWebDriver driver;
    static boolean status;
    static ExtentReports report;
    static ExtentTest test;

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        ReportSingleton RS = ReportSingleton.getInstanceOfSingletonReportClass();
        report = RS.getReport();
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();
    }

    // public static void logStatus(String type, String message, String status){
    // System.out.println(String.format(" %s | %s | %s | %s " ,
    // String.valueOf(java.time.LocalDateTime.now()), type, message, status ));
    // }

    @Test(description = "Verify that adventure Booking and cancellation works fine!",
            dataProvider = "data-provider", dataProviderClass = DP.class, enabled = true,
            priority = 3, groups = {"Booking and Cancellation Flow"})
    public static void TestCase03(String user, String password, String city, String adventurevalue,
            String guestname, String date, String count) throws InterruptedException, IOException {
        try {
            test = report.startTest("TestCase03");
            HomePage home = new HomePage(driver);
            home.navigateToHomePage();
            home.clickRegister();

            RegisterPage register = new RegisterPage(driver);
            register.registerUser(user, password, password, true);
            String loginusername = RegisterPage.lastgeneratedemailid;
            // logStatus("PageTest", "user registration completed", "PASS");

            LoginPage login = new LoginPage(driver);
            login.loginpage(loginusername, password);
            Thread.sleep(1000);
            // logStatus("PageTest", "user logged in", "PASS");

            home.searchcity(city);
            status = home.selectcity(city);
            if (status) {
                test.log(LogStatus.PASS, "city selected : PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "city selected : FAIL");
            }
            // logStatus("Verify", "city selected", status ? "PASS" : "FAIL");

            AdventurePage adventurepg = new AdventurePage(driver);
            adventurepg.searchadventure(adventurevalue);
            status = adventurepg.selectadventure(adventurevalue);
            if (status) {
                test.log(LogStatus.PASS, "navigated to adventure details page : PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "navigated to adventure details page : FAIL");
            }
            // logStatus("Verify", "navigated to adventure details page", status ? "PASS" : "FAIL");

            AdventureDetailsPage adventureDetails = new AdventureDetailsPage(driver);

            status = adventureDetails.bookadventure(guestname, date, count);
            if (status) {
                test.log(LogStatus.PASS, "Booking adventure successful : PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "Booking adventure successful : FAIL");
            }
            Assert.assertTrue(status, "Booking adventure failure");
            // logStatus("Verify", "Booking adventure successful", status ? "PASS" : "FAIL");


            status = adventureDetails.clickhistorypage();
            if (status) {
                test.log(LogStatus.PASS, "navigation to history page failed : PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "navigation to history page failed : FAIL");
            }
            Assert.assertTrue(status, "navigation to history page failed");
            //logStatus("Verify", "navigated to history page", status ? "PASS" : "FAIL");

            HistoryPage history = new HistoryPage(driver);

            String transactionid = history.gettransactionid(adventurevalue);
            history.cancelreservation(transactionid);

            driver.navigate().refresh();
            status = history.verifytransactiondelete(transactionid);
            if (status) {
                test.log(LogStatus.PASS, "order deleted : PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "order deleted : FAIL");
            }
            Assert.assertTrue(status, "order deletion failure");
            //logStatus("verify", "order deleted ", status ? "PASS" : "FAIL");

            try {
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.alertIsPresent());
                //System.out.println("Alert is present.");
                Alert alert = driver.switchTo().alert();
                // System.out.println("Alert Text: " + alert.getText());
                alert.accept();

            } catch (Exception e) {
                //System.out.println("No alert is present.");
                test.log(LogStatus.PASS,
                    test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                    "No alert is present.");
            }

            home.clickLogout();

        } catch (Exception e) {
            test.log(LogStatus.FAIL,
                    test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                    "Error in TestCase03");
            System.out.println(e.getMessage());
        }
    }
}

