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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_04 {
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

    // public static void logStatus(String type, String message, String status) {
    //     System.out.println(String.format(" %s | %s | %s | %s ",
    //             String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    // }

    @Test(description = "Verify that Booking history can be viewed", dataProvider = "data-provider",
            dataProviderClass = DP.class, enabled = true, priority = 4, groups = {"Reliability Flow"})
    public static void TestCase04(String user, String password, String details1, String details2,
            String details3) throws InterruptedException, IOException {
        try {
            test = report.startTest("TestCase04");
            AdventurePage adventurepg;
            AdventureDetailsPage adventureDetails;

            HomePage home = new HomePage(driver);
            home.navigateToHomePage();
            home.clickRegister();

            RegisterPage register = new RegisterPage(driver);
            register.registerUser(user, password, password, true);
            String loginusername = RegisterPage.lastgeneratedemailid;
            //logStatus("PageTest", "user registration completed", "PASS");

            LoginPage login = new LoginPage(driver);
            login.loginpage(loginusername, password);
            Thread.sleep(1000);
            //logStatus("PageTest", "user logged in", "PASS");

            String[] bookings = new String[3];
            bookings[0] = details1;
            bookings[1] = details2;
            bookings[2] = details3;


            for (int i = 0; i < bookings.length; i++) {
                String[] bookingsdetails = bookings[i].split(";");

                home.searchcity(bookingsdetails[0]);
                status = home.selectcity(bookingsdetails[0]);
                if (status) {
                    test.log(LogStatus.PASS, "city selected : PASS");
                } else {
                    test.log(LogStatus.FAIL,
                            test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                            "city selected : FAIL");
                }
                //logStatus("Verify", "city selected", status ? "PASS" : "FAIL");

                adventurepg = new AdventurePage(driver);
                adventurepg.searchadventure(bookingsdetails[1]);
                status = adventurepg.selectadventure(bookingsdetails[1]);
                if (status) {
                    test.log(LogStatus.PASS, "navigated to adventure details page : PASS");
                } else {
                    test.log(LogStatus.FAIL,
                            test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                            "navigated to adventure details page : FAIL");
                }
                //logStatus("Verify", "navigated to adventure details page",status ? "PASS" : "FAIL");

                adventureDetails = new AdventureDetailsPage(driver);

                status = adventureDetails.bookadventure(bookingsdetails[2], bookingsdetails[3],
                        bookingsdetails[4]);
                        if (status) {
                            test.log(LogStatus.PASS, "Booking adventure successful : PASS");
                        } else {
                            test.log(LogStatus.FAIL,
                                    test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                                    "Booking adventure successful : FAIL");
                        }
                Assert.assertTrue(status, "Booking adventure failure");

                //logStatus("Verify", "Booking adventure successful", status ? "PASS" : "FAIL");

                home.navigateToHomePage();
            }

            status = home.clickhistorypage();
            if (status) {
                test.log(LogStatus.PASS, "navigated to history page: PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "navigated to history page: FAIL");
            }
            Assert.assertTrue(status, "navigation to history page failed");
            //logStatus("Verify", "navigated to history page", status ? "PASS" : "FAIL");

            HistoryPage history = new HistoryPage(driver);
            int totalbookings = history.gettotalbookingnumbers();
            Assert.assertEquals(totalbookings, 3,"all bookings created correctly");
            //logStatus("Verify", "all bookings are available","PASS" );

            home.clickLogout();

        } catch (Exception e) {
            test.log(LogStatus.FAIL,
                    test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                    "Error in TestCase04");
            System.out.println(e.getMessage());
            
        }
    }

    @AfterTest(alwaysRun = true)
    public static void QuitBrowser() {
        driver.close();
        report.endTest(test);
        report.flush();
    }
    
}
