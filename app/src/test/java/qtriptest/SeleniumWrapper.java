package qtriptest;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;

public class SeleniumWrapper {
    public static boolean sendKeys(WebElement element, String parameter){
        try{
            element.clear();
            element.sendKeys(parameter);
            return true;
        }
        catch(Exception e){
            return false;
        }      
    }

    public static boolean Click(WebDriver driver, WebElement element ) throws InterruptedException{
        if(element.isDisplayed()){
            if(element.isEnabled()){
                JavascriptExecutor js = (JavascriptExecutor)driver;
                js.executeScript("arguments[0].scrollIntoView(true)", element);
                element.click();
                Thread.sleep(2000);
                return true;
            }
        }
        return false;
    }

    public static boolean navigate(WebDriver driver, String url){
        
        try{
            if(driver.getCurrentUrl().equals(url)){
                return true;
            }else{
                driver.get(url);
                return driver.getCurrentUrl()==url;
            }  
        }
        catch(Exception e){
            return false;
        }
    }

    public static String captureScreenshot(WebDriver driver) throws IOException {
        File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destfile = new File(System.getProperty("user.dir") + "/QTRIPImages/" + System.currentTimeMillis() + ".png");
        FileUtils.copyFile(srcfile, destfile);
        String errfilepath = destfile.getAbsolutePath();
        return errfilepath;
    }

    // public static WebElement findElementWithRetry(WebDriver d, int retryCount, By by){
    //     WebDriverWait wait = new WebDriverWait(d, retryCount);
    //     wait.until(ExpectedConditions.visi)

    } 

