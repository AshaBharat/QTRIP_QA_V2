package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage {
    RemoteWebDriver d;
    public static String loginurl = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";
    @FindBy(name = "email")
    private WebElement emailtextbox;
    @FindBy(name = "password")
    private WebElement passwordtextbox;
    @FindBy(xpath = "//button[text()='Login to QTrip']")
    private WebElement loginButton;

    public LoginPage(RemoteWebDriver driver){
        d = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(d, 1000);
        PageFactory.initElements(factory, this);
    }

    public void loginpage(String username, String password){
        emailtextbox.sendKeys(username);
        passwordtextbox.sendKeys(password);
        loginButton.click();
    }
}
