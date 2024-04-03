package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.FindBy;

public class HomePage {
    public static RemoteWebDriver d;
    public String url ="https://qtripdynamic-qa-frontend.vercel.app";
    Actions act;
    @FindBy(xpath = "//a[text()='Register']")
    private WebElement registerButton;
    @FindBy(xpath = "//div[text()='Logout']")
    private WebElement logoutButton;
    @FindBy(xpath = "//input[@placeholder='Search a City ']")
    private WebElement searchtextbox;
    @FindBy(xpath = "//ul/a/li")
    private WebElement searchcityoption;
    @FindBy(xpath = "//h5[text()='No City found']")
    private WebElement noelement;
    @FindBy(xpath = "//a[text()='Reservations']")
    private WebElement reservationbutton;

    public HomePage(RemoteWebDriver driver){
        d = driver;
        act = new Actions(d);
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(d, 10);
        PageFactory.initElements(factory, this);
    }
    public boolean navigateToHomePage(){
        // if(!(d.getCurrentUrl().equals(url))){
        //     d.get(url);
        // }
        SeleniumWrapper.navigate(d, url);
        return d.getCurrentUrl().contains(url);
    }
    public boolean clickRegister() throws InterruptedException{
        // registerButton.click();
        SeleniumWrapper.Click(d, registerButton);
        Thread.sleep(3000);
        return d.getCurrentUrl().contains("pages/register");
    }
    
    public boolean verifyuserloggedin(){
        if(logoutButton.isDisplayed()){
            return true;
        }
        return false;
    }
    public void clickLogout() throws InterruptedException {
        // WebDriverWait wait = new WebDriverWait(d,10);
        // wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        // logoutButton.click();
        // Thread.sleep(500);
        SeleniumWrapper.Click(d,logoutButton);
    }

    public void searchcity(String cityname) throws InterruptedException{
        searchtextbox.clear();
        act.click(searchtextbox).sendKeys(searchtextbox ,cityname);
        act.build().perform();
        Thread.sleep(1000);
    }
    public boolean verifynocityfound() {
        if(noelement.isDisplayed()){
            return true;
        }
        return false;
    }
    
    public boolean verifycityfound(String cityname) {
        WebDriverWait wait = new WebDriverWait(d,10);
        wait.until(ExpectedConditions.visibilityOf(searchcityoption));
        String city = searchcityoption.getText();
        if(city.toLowerCase().contains(cityname.toLowerCase())){
            return true;
        }
        return false;
    }
    public boolean selectcity(String cityname) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(d,10);
        wait.until(ExpectedConditions.visibilityOf(searchcityoption));
        // searchcityoption.click();
        SeleniumWrapper.Click(d, searchcityoption);
        Thread.sleep(1000);
        if(d.getCurrentUrl().endsWith(cityname.toLowerCase())){
            return true;
        }
        return false;
    }
    public boolean verifyuserloggedout() {
        if(registerButton.isDisplayed()){
            return true;
        }
        return false;
    }
    public boolean clickhistorypage() throws InterruptedException {
        boolean status=false;
        //reservationbutton.click();
        SeleniumWrapper.Click(d, reservationbutton);
        Thread.sleep(4000);
        if(d.getCurrentUrl().contains("pages/adventures/reservations")){
            status = true;
            return status;
        }
        return status;
        
    }

}
