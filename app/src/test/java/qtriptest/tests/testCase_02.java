package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_02 {
    public static RemoteWebDriver driver;
    static ExtentReports report;
    // static boolean status;
    static ExtentTest test;

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        ReportSingleton RS = ReportSingleton.getInstanceOfSingletonReportClass();
        report = RS.getReport();
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();
    }

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format(" %s | %s | %s | %s ",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }


    @Test(description = "Verify that search and filter work fine", dataProvider = "data-provider",
            dataProviderClass = DP.class, enabled = true, priority = 2,
            groups = {"Search and Filter flow"})
    public static void TestCase02(String cityname, String category, String hour,
            String filtereddata, String unfiltereddata) throws InterruptedException, IOException {
        try {
            boolean status;
            test = report.startTest("TestCase02");
            HomePage home = new HomePage(driver);
            home.navigateToHomePage();
            // logStatus("Page Test", "homepage navigated", "PASS");

            String revstr = new StringBuilder(cityname).reverse().toString();
            home.searchcity(revstr);
            status = home.verifynocityfound();
            if (status) {
                test.log(LogStatus.PASS, "Verify No city Found : PASS");
                // logStatus("Page Test", "Verify No city Found ", "PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "Verify No city Found : FAIL");
                // logStatus("Page Test", "Verify No city Found ", "FAIL");
            }

            String shortstr = cityname.substring(0, 3);
            home.searchcity(shortstr);
            status = home.verifycityfound(cityname);
            if (status) {
                test.log(LogStatus.PASS, "Verify city Found : PASS");
                // logStatus("Page Test", "Verify No city Found ", "PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "Verify city Found : FAIL");
                // logStatus("Page Test", "Verify No city Found ", "FAIL");
            }
            Assert.assertTrue(status, "autocompletetext test failed");
            // logStatus("verify", "autocompletetext for searchbox", status? "PASS" : "FAIL");

            home.searchcity(cityname);
            status = home.verifycityfound(cityname);
            if (status) {
                test.log(LogStatus.PASS, "serach for city test : PASS");
                // logStatus("Page Test", "Verify No city Found ", "PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "serach for city test : FAIL");
                // logStatus("Page Test", "Verify No city Found ", "FAIL");
            }
            Assert.assertTrue(status, "serach for city test failed");
            // logStatus("verify", "serach for city test ", status? "PASS" : "FAIL");

            status = home.selectcity(cityname);
            if (status) {
                test.log(LogStatus.PASS, "navigated to Adventure page : PASS");
                // logStatus("Page Test", "Verify No city Found ", "PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "navigated to Adventure page : FAIL");
                // logStatus("Page Test", "Verify No city Found ", "FAIL");
            }
            // Assert.assertTrue(status, "city not selected and not navigated to adventure page");
            // logStatus("verify", "navigated to Adventure page", status? "PASS" : "FAIL");

            AdventurePage adventure = new AdventurePage(driver);
            adventure.clearhourfilter();
            adventure.clearcategoryfilter();

            adventure.setvalueforhourfilter(hour);
            status = adventure.verifyhourfilterdata(hour);
            if (status) {
                test.log(LogStatus.PASS, "Hour filtered data : PASS");
                // logStatus("Page Test", "Verify No city Found ", "PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "Hour filtered data : FAIL");
                // logStatus("Page Test", "Verify No city Found ", "FAIL");
            }
            // logStatus("verify", "Hour filtered data ", status? "PASS" : "FAIL");

            adventure.setvalueforcategoryfilter(category);
            status = adventure.verifycategoryfilterdata(category);
            if (status) {
                test.log(LogStatus.PASS, "Category filtered data : PASS");
                // logStatus("Page Test", "Verify No city Found ", "PASS");
            } else {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                        "Category filtered data : FAIL");
                // logStatus("Page Test", "Verify No city Found ", "FAIL");
            }
            // logStatus("verify", "Category filtered data ", status? "PASS" : "FAIL");

            Assert.assertEquals(adventure.noOfFilterResults(), Integer.parseInt(filtereddata));

            adventure.clearcategoryfilter();
            adventure.clearhourfilter();

            Assert.assertEquals(adventure.noOfFilterResults(), Integer.parseInt(unfiltereddata));

            home.navigateToHomePage();
        } catch (Exception e) {
            test.log(LogStatus.FAIL,
                    test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)),
                    "Error in TestCase02");
            System.out.println(e.getMessage());
        }
    }
}
