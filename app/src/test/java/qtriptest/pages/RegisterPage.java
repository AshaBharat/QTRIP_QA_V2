package qtriptest.pages;

import java.util.UUID;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class RegisterPage {
    RemoteWebDriver d;
    public  static String lastgeneratedemailid;
    public  static String registerurl = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    @FindBy(id = "floatingInput")
    private WebElement emailtextbox;
    @FindBy(name ="password")
    private WebElement passwordtextbox;
    @FindBy(name="confirmpassword")
    private WebElement confirmpasswordtextbox;
    @FindBy(xpath = "//button[text()='Register Now']")
    private WebElement registerNowButton;

    public RegisterPage(RemoteWebDriver driver){
        d = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(d, 10);
        PageFactory.initElements(factory, this);
    }

    public void registerUser(String username, String password, String confirmPassword, Boolean makeuserNameDynamic) throws InterruptedException{
        String emailid;
        if(makeuserNameDynamic){
            emailid = username.split("@")[0] + UUID.randomUUID().toString() + "@" + username.split("@")[1];
        }else{
            emailid = username;       
        }
        lastgeneratedemailid = emailid;
        emailtextbox.sendKeys(emailid);
        passwordtextbox.sendKeys(password);
        confirmpasswordtextbox.sendKeys(confirmPassword);
        registerNowButton.click();
        Thread.sleep(5000);
    }

}
