package hw4;

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
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

public class HomeWork18 {

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
        }else if (browser.equals("ie")){
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }
    }

    @Test (description = "some basic test activity with sorting and checkboxes, including waiters and xpath search")
    public void checkCoursesSortingButton(){
        SoftAssert softAssert = new SoftAssert();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://udemy.com/ru/");
        driver.findElement(By.id("header-search-field")).sendKeys("java ");
        driver.findElement(By.className("input-group-btn")).submit();
        Select select = new Select(driver.findElement(By.xpath("//select[@name='sort']")));
        select.selectByValue("most-reviewed");
        softAssert.assertEquals(select.getFirstSelectedOption().getText(),
                "Больше всего отзывов" , "check if courses list is sorted by most reviewed");
        waiter.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='beginner' and @type='checkbox']/parent::label"))).click();
        waiter.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='en' and @type='checkbox']/parent::label"))).click();
        WebElement checkBox = driver.findElement(By.xpath("//input[@value='en' and @type='checkbox']"));
        softAssert.assertEquals(checkBox.isEnabled(),
                true , "check if english speaking courses checkbox is enabled");
        softAssert.assertAll();
    }

    @AfterClass
    public void stop(){
        driver.quit();
        driver = null;
    }
}
