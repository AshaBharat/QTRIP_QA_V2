package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.io.ObjectInputFilter.Status;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_02 {
    public static RemoteWebDriver driver;

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException{
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();
    }

    public static void logStatus(String type, String message, String status){
        System.out.println(String.format(" %s | %s | %s | %s " , String.valueOf(java.time.LocalDateTime.now()), type, message, status ));
    }


    @Test(description="Verify that search and filter work fine", dataProvider = "data-provider", dataProviderClass = DP.class , enabled = true, priority = 2, groups={"Search and Filter flow"})
    public static void TestCase02(String cityname, String category, String hour, String filtereddata, String unfiltereddata) throws InterruptedException{
        //try{
            boolean status;
            
            HomePage home = new HomePage(driver);
            home.navigateToHomePage();
            logStatus("Page Test", "homepage navigated", "PASS");
            
            String revstr = new StringBuilder(cityname).reverse().toString();
            home.searchcity(revstr);
            status = home.verifynocityfound();
            if(status){
                logStatus("Page Test", "Verify No city Found ", "PASS");
            } 
            else{
                logStatus("Page Test", "Verify No city Found ", "FAIL");
            }

            String shortstr = cityname.substring(0,3);
            home.searchcity(shortstr);
            status = home.verifycityfound(cityname);
            Assert.assertTrue(status, "aucompletetext test failed");
            logStatus("verify", "autocompletetext for searchbox", status? "PASS" : "FAIL");

            home.searchcity(cityname);
            status = home.verifycityfound(cityname);
            Assert.assertTrue(status, "serach for city test failed");
            logStatus("verify", "serach for city test ", status? "PASS" : "FAIL");

            status = home.selectcity(cityname);
           // Assert.assertTrue(status, "city not selected and not navigated to adventure page");
            logStatus("verify", "navigated to Adventure page", status? "PASS" : "FAIL");

            AdventurePage adventure = new AdventurePage(driver);
            adventure.clearhourfilter();
            adventure.clearcategoryfilter();

            adventure.setvalueforhourfilter(hour);
            status = adventure.verifyhourfilterdata(hour);
            logStatus("verify", "Hour filtered data ", status? "PASS" : "FAIL");

            adventure.setvalueforcategoryfilter(category);
            status = adventure.verifycategoryfilterdata(category);
            logStatus("verify", "Category filtered data ", status? "PASS" : "FAIL");

            Assert.assertEquals(adventure.noOfFilterResults(), Integer.parseInt(filtereddata));

            adventure.clearcategoryfilter();
            adventure.clearhourfilter();

            Assert.assertEquals(adventure.noOfFilterResults(), Integer.parseInt(unfiltereddata));
            
            home.navigateToHomePage();
        //}
        // catch(Exception e){
        //     e.getMessage();
        //     System.out.println("Error in TestCase02");
        // }
    }

   
}
