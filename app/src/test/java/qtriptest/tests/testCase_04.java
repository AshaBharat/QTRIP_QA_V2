package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_04 {
    public static RemoteWebDriver driver;

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();
    }

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format(" %s | %s | %s | %s ",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @Test(description = "Verify that Booking history can be viewed", dataProvider = "data-provider",
            dataProviderClass = DP.class, enabled = true, priority = 4, groups = {"Reliability Flow"})
    public static void TestCase04(String user, String password, String details1, String details2,
            String details3) throws InterruptedException {
        try {
            boolean status;
            AdventurePage adventurepg;
            AdventureDetailsPage adventureDetails;

            HomePage home = new HomePage(driver);
            home.navigateToHomePage();
            home.clickRegister();

            RegisterPage register = new RegisterPage(driver);
            register.registerUser(user, password, password, true);
            String loginusername = RegisterPage.lastgeneratedemailid;
            logStatus("PageTest", "user registration completed", "PASS");

            LoginPage login = new LoginPage(driver);
            login.loginpage(loginusername, password);
            Thread.sleep(1000);
            logStatus("PageTest", "user logged in", "PASS");

            String[] bookings = new String[3];
            bookings[0] = details1;
            bookings[1] = details2;
            bookings[2] = details3;


            for (int i = 0; i < bookings.length; i++) {
                String[] bookingsdetails = bookings[i].split(";");

                home.searchcity(bookingsdetails[0]);
                status = home.selectcity(bookingsdetails[0]);
                logStatus("Verify", "city selected", status ? "PASS" : "FAIL");

                adventurepg = new AdventurePage(driver);
                adventurepg.searchadventure(bookingsdetails[1]);
                status = adventurepg.selectadventure(bookingsdetails[1]);
                logStatus("Verify", "navigated to adventure details page",
                        status ? "PASS" : "FAIL");

                adventureDetails = new AdventureDetailsPage(driver);

                status = adventureDetails.bookadventure(bookingsdetails[2], bookingsdetails[3],
                        bookingsdetails[4]);
                Assert.assertTrue(status, "Booking adventure failure");
                logStatus("Verify", "Booking adventure successful", status ? "PASS" : "FAIL");

                home.navigateToHomePage();
            }

            status = home.clickhistorypage();
            Assert.assertTrue(status, "navigation to history page failed");
            logStatus("Verify", "navigated to history page", status ? "PASS" : "FAIL");

            HistoryPage history = new HistoryPage(driver);
            int totalbookings = history.gettotalbookingnumbers();
            Assert.assertEquals(totalbookings, 3,"all bookings created correctly");
            logStatus("Verify", "all bookings are available","PASS" );

            home.clickLogout();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterTest(alwaysRun = true)
    public static void QuitBrowser() {
        driver.close();
        
    }
}
