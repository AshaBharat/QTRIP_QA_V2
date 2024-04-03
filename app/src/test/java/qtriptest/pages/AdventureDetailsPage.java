package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage {
    static RemoteWebDriver d;

    @FindBy(name = "name")
    private WebElement nametextbox;
    @FindBy(xpath = "//input[@name='person']")
    private WebElement persontextbox;
    @FindBy(xpath = "//a[@href='../reservations/']")
    private WebElement clickherelink;
    @FindBy(xpath = "//button[text()='Reserve']")
    private WebElement reservebutton;
    @FindBy(xpath = "//input[@type='date']")
    private WebElement datetextbox;
    @FindBy(xpath = "//div[contains(text(),'Reservation for this adventure is successful')]")
    private WebElement bookingsuccessalert;
    @FindBy(xpath = "//a[text()='Reservations']")
    private WebElement reservationbutton;

    Actions act;

    public AdventureDetailsPage(RemoteWebDriver driver) {
        d = driver;
        act = new Actions(d);
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(d, 10);
        PageFactory.initElements(factory, this);
    }

    public boolean bookadventure(String guestname, String date, String count) throws InterruptedException{
        boolean status=false;
        Thread.sleep(500);
        nametextbox.clear();
        act.sendKeys(nametextbox,guestname).perform();
        datetextbox.clear();
        act.sendKeys(datetextbox, date).perform();
        // persontextbox.clear();
        // persontextbox.sendKeys(count);
        //act.sendKeys(persontextbox, ).perform();
        //reservebutton.click();
        SeleniumWrapper.sendKeys(persontextbox, count);
        SeleniumWrapper.Click(d, reservebutton);
        Thread.sleep(4000);
        if(bookingsuccessalert.isDisplayed()) {
            status = true;
            return status;
        }
        return status;
    }

    public boolean clickhistorypage() throws InterruptedException{
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