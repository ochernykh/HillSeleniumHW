package seleniumhw1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

public class TestExample {

    public static String browser = "chrome";
    private WebDriverWait waiter;
    WebDriver driver;

    @BeforeClass
    public void start(){

        if (browser.equals("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if (browser.equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }else if (browser.equals("ie")){
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }
    }

    @Test
    public void checkMostViewedCourses(){
        driver.get("https://udemy.com/ru/");
        driver.manage().window().maximize();
        WebElement inputFieldId = driver.findElement(By.id("header-search-field"));
        inputFieldId.sendKeys("java ");
        driver.findElement(By.className("input-group-btn")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Select select = new Select(driver.findElement(By.id("sort-select")));
        select.selectByValue("most-reviewed");
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "Больше всего отзывов");
    }

    @AfterClass
    public void stop(){
        driver.quit();
        driver = null;
    }
}
