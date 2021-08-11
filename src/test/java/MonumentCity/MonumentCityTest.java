package MonumentCity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MonumentCityTest {
    WebDriver driver;
    @BeforeMethod
    public void startUp() {
        //Initializing Driver
        System.setProperty("webDriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        //Opening search engine
        driver.get("https://google.com");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test (dataProvider = "search", dataProviderClass=DataProviderClass.class)
    public void paramByDataProvider(String monument, String city) throws InterruptedException {
//    Ищем по городу + памятник в гугле,
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(monument + " " + city);
        System.out.println("You are trying to search " + monument + " which is in " + city);

        WebElement srchBtn = driver.findElement(By.xpath("//input[@aria-label='Поиск в Google']"));
        srchBtn.submit();
        Thread.sleep(2000);
        System.out.println("The page title is: " + driver.getTitle());

    }

    @Test (dataProvider = "search", dataProviderClass=DataProviderClass.class)
    public void mapSearch(String monument, String city) throws InterruptedException {
        //Ищем сначала по городу в гугле,
        driver.findElement(By.name("q")).sendKeys(city);
        driver.findElement(By.xpath("//input[@aria-label='Поиск в Google']")).submit();
//переходим в Гугл-карты
        var dynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Карты']")));
        dynamicElement.click();
// затем добавляем пямятник в поиск на карте города
        var inputElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='"+city+"']")));
        inputElement.sendKeys(" "+ monument+"\n");
        Thread.sleep(2000);


    }

}
