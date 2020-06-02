package hw19;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeWork19 {

    public static String browser = "chrome";
    private WebDriver driver;
    private WebDriverWait waiter;

    @BeforeClass
    public void start(){

        if (browser.equals("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            waiter = new WebDriverWait(driver,10);
        }else if (browser.equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            waiter = new WebDriverWait(driver,10);
        }else if (browser.equals("ie")){
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
            waiter = new WebDriverWait(driver,10);
        }
    }

    @Test (description = "some basic test activity with sorting and checkboxes, including waiters and xpath search")
    public void findUsedGermanBuses(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://auto.ria.com/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//a[@class='ext-end']/child::span")).click();
        driver.findElement(By.xpath("//label[@class='c-notifier-btn']")).click();
        driver.findElement(By.xpath("//label[@for='custom-used']")).click();
        Select transportType = new Select(driver.findElement(By.xpath("//select[@aria-label='Тип транспорта']")));
        transportType.selectByValue("7");
        List<WebElement> carTypes = driver.findElements(By.xpath("//label[starts-with(@for, \"body.id\")]"));
        for (WebElement el: carTypes) {
            el.click();
        }
        Select country = new Select(driver.findElement(By.id("at_country")));
        country.selectByVisibleText("Германия");
        waiter.until(ExpectedConditions.elementToBeClickable(By.id("autocompleteInput-brand-0"))).sendKeys("Volkswagen");
        WebElement carMakeUL = driver.findElement(By.xpath("//div[@id='autocomplete-brand-0']/ul"));
        List<WebElement> carMakes = carMakeUL.findElements(By.tagName("li"));
        for (WebElement li: carMakes) {
            if(li.getText().equals("Volkswagen")){
                li.submit();
            }

        }
        driver.findElement(By.id("at_price-from")).sendKeys("5000");
        driver.findElement(By.id("at_price-to")).sendKeys("10000");
        driver.findElement(By.xpath("//button[@class='button small']")).click();
    }

    @AfterClass
    public void stop(){
        driver.quit();
        driver = null;
    }
}
