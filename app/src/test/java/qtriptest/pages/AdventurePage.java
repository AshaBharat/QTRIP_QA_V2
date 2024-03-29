package qtriptest.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

public class AdventurePage {
    static RemoteWebDriver d;

    @FindBy(id = "duration-select")
    private WebElement hourDropdown;
    @FindBy(id = "category-select")
    private WebElement categoryDropdown;
    @FindBy(xpath = "//div[@onclick='clearCategory(event)']")
    private WebElement clearcategoryfilter;
    @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
    private WebElement clearhourfilter;
    @FindBy(xpath = "//div[@class='content']//div[@class='activity-card']")
    private List<WebElement> filteredresult;
    @FindBy(id = "search-adventures")
    private WebElement adventuresearchbox;
    @FindBy(xpath = "//div[@onclick='resetAdventuresData()']")
    private WebElement clearadventurefilter;

    Actions act;
    public AdventurePage(RemoteWebDriver driver){
        d = driver;
        act = new Actions(d);
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(d, 10);
        PageFactory.initElements(factory, this);
    }

    public void setvalueforhourfilter(String hour) throws InterruptedException {
        Select drpdown = new Select(hourDropdown);
        drpdown.selectByVisibleText(hour);
        Thread.sleep(2000);
    }

    public boolean verifyhourfilterdata(String hour){
        boolean status = false;
        int minhour = Integer.parseInt(hour.split(" ")[0].split("-")[0]);
        int maxhour = Integer.parseInt(hour.split(" ")[0].split("-")[1]);
        List<WebElement> hourfiltereddata = d.findElements(By.xpath("//div[@class='content']//div[@class='activity-card']//h5[text()='Duration']/following-sibling::p"));
        for(WebElement ele : hourfiltereddata){
            int resulthour = Integer.parseInt(ele.getText().split(" ")[0]);
            if (resulthour<=maxhour && resulthour>=minhour){
                status = true;
            }
            else{
                status = false;
                return status;
            }
        }
        return status;
    }

    public boolean verifycategoryfilterdata(String category) {
        boolean status = false;
        List<WebElement> categoryfiltereddata = d.findElements(By.xpath("//div[@class='content']//div[@class='activity-card']/preceding-sibling::div"));
        for(WebElement ele : categoryfiltereddata){
            if (category.contains(ele.getText())){
                status = true;
            }
            else{
                status = false;
                return status;
            }
        }
        return status;
    }

    public void setvalueforcategoryfilter(String category) throws InterruptedException {
        Select drpdown = new Select(categoryDropdown);
        drpdown.selectByVisibleText(category);
        Thread.sleep(2000);
    }

    public void clearcategoryfilter() {
        clearcategoryfilter.click();
    }

    public void clearhourfilter() {
        clearhourfilter.click();
    }

    public void clearadventurefilter() {
        clearadventurefilter.click();
    }

    public int noOfFilterResults() {
        return filteredresult.size();
    }

    public void searchadventure(String adventurevalue) throws InterruptedException {
        clearcategoryfilter();
        clearhourfilter();
        clearadventurefilter();
        act.click(adventuresearchbox).sendKeys(adventuresearchbox,adventurevalue);
        act.build().perform();
        Thread.sleep(1000);
    }

    public boolean bookadventure(String guestname, String date, String count) {
        boolean status = false;
        
        return status;
    }

    public boolean selectadventure(String adventurevalue) throws InterruptedException {
        boolean status = false;
        WebElement selectadventure = d.findElement(By.xpath("//h5[text()='"+ adventurevalue +"']"));
        selectadventure.click();
        Thread.sleep(1000);
        if(d.getCurrentUrl().contains("/pages/adventures/detail/")){
            status = true;
            return status;
        }
        return status;
    }
}