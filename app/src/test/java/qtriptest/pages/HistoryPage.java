
package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HistoryPage {
    static RemoteWebDriver d;

    
    @FindBy(xpath = "//tbody/tr/th")
    private List<WebElement> transactionidelementlist;
       
    Actions act;

    public HistoryPage(RemoteWebDriver driver) {
        d = driver;
        act = new Actions(d);
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(d, 500);
        PageFactory.initElements(factory, this);
    }
    
    public void cancelreservation(String transactionid) throws InterruptedException {
        WebElement cancelbutton = d.findElement(By.xpath("//button[@id='"+ transactionid +"']"));
        //cancelbutton.click();
        //Thread.sleep(500);
        SeleniumWrapper.Click(d, cancelbutton);
    }

    public boolean verifytransactiondelete(String transactionid) {
        boolean status=false;
        
        try {
            WebElement transactionidelement = d.findElement(By.xpath("//tbody/tr/th[text()='"+transactionid+"']"));
            if (!(transactionidelement.isDisplayed())) {
                System.out.println("Element is not present.");
                status = true;
            } else {
                System.out.println("Element is present.");
                status = false;
                return status;
            }
        } catch (Exception e) {
            System.out.println("Element is not present.");
            status = true;
        }
        return status;
    }
    public String gettransactionid(String adventure){
        String transactionid = d.findElement(By.xpath("//tbody/tr/td[text()='"+ adventure +"']/preceding-sibling::th")).getText();
        return transactionid;
    }

    public int gettotalbookingnumbers() {
        int totalbookings = transactionidelementlist.size();
        return totalbookings;
    }
}