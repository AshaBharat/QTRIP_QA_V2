package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.FindBy;

public class HomePage {
    RemoteWebDriver d;
    String url ="https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(xpath = "//a[text()='Register']")
    private WebElement registerButton;
    @FindBy(xpath = "//div[text()='Logout']")
    private WebElement logoutButton;


    public HomePage(RemoteWebDriver driver){
        d = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(d, 10);
        PageFactory.initElements(factory, this);
    }
    public void navigateToHomePage(){
        if(!(d.getCurrentUrl().equals(url))){
            d.get(url);
        }
    }
    public void clickRegister(){
        registerButton.click();
    }
    
    public boolean verifyuserloggedin(){
        if(logoutButton.isDisplayed()){
            return true;
        }
        return false;
    }
    public void clickLogout() {
        logoutButton.click();
    }

}
