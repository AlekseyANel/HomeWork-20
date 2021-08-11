package CrossBrowser;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.ENTER;

public class CrossBrTest extends Assert{
    WebDriver driver;
    //SoftAssert asert=new SoftAssert();

    @DataProvider (name = "browserData"/*, parallel = true*/)
    public Object[][] browserData() {
        return new Object[][]{
                {"chrome", "https://rozetka.com.ua/","ноутбук", 15000},
                /*               {"edge", "https://rozetka.com.ua/","ноутбук", 5000},
                               {"chromium", "https://rozetka.com.ua/","ноутбук", 3000},*/

        };
    }

    /*@BeforeClass
    public void startUp() {
        //Initializing Driver
        System.setProperty("webDriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        //Opening search engine
        driver.get("https://www.etsy.com/");
    }*/

/*    @AfterTest
    public void tearDown() {
         driver.quit();
        }*/

    //Test01
    @Test (dataProvider = "browserData")
    public void testBro(String browser, String url, String searchWord, long t) {
        Configuration.browser = browser;
        System.setProperty("Selenide.browser", browser);
        open (url);
        $("input[name='search']").should(Condition.visible).sendKeys(searchWord, ENTER);
        Configuration.timeout=t;
        var element = $("head > title").should(Condition.appear);



    }






}
