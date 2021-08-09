package ua.com.rozetka;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.Assert;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.ENTER;


public class TestSuit01 {
    public ChromeDriver webDriver;
    String email = "email@here.com";
    String password = "password_here";
    String url = "https://rozetka.com.ua/";
    public Robot r;
    public Actions action;


    @BeforeSuite//открываем мейнпедж и регистрируемся неполностью
    public void startUp() throws AWTException, InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");//Отключить уведомления-permissions
        System.setProperty("webDriver.chrome.driver", "chromedriver.exe");
        webDriver = new ChromeDriver(options);
        webDriver.get(url);
        webDriver.findElement(By.cssSelector("rz-header.header-component rz-user.header-actions__component")).click();

        LoginPage loginPage = new LoginPage(webDriver);
        PageFactory.initElements(webDriver, LoginPage.class);
        loginPage.login(email, password);
    }

    @AfterSuite
    public void totalQuit() {
        webDriver.quit();
        //Далее условный логаут
        webDriver.findElement(By.cssSelector("rz-header.header-component rz-user.header-actions__component")).click();
        LogoutPage logoutPage = new LogoutPage(webDriver);
        PageFactory.initElements(webDriver, LogoutPage.class);
        logoutPage.logout();

    }


    @Test //Ноутбуки и компьютеры -> Ноутбуки -> Поиск бренда НР -> Сортировка от дешёвых к дорогим
    public void Test01() throws InterruptedException {
        //search by full css selector
        WebElement dynamicElement = (new WebDriverWait(webDriver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/div/div/rz-main-page/div/aside/main-page-sidebar/sidebar-fat-menu/div/ul/li[1]")));
        dynamicElement.click();
        Thread.sleep(3000);//без ожидания почемуто не работает(

        //search by attribute value in html tag
        var element1 = webDriver.
                findElement(By.cssSelector("img[alt='Ноутбуки']"));
        //Assert.assertTrue(element1.isDisplayed());
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element1);
        element1.click();

        //search by element class
        var element2 = webDriver.findElement(By.cssSelector("input.sidebar-search__input"));
        Assert.assertTrue(element2.isDisplayed());
        element2.sendKeys("hp");
        element2.click();
        Thread.sleep(3000);
        //search by attribute value in html tag
        webDriver.findElement(By.cssSelector("label[for='HP']")).click();
        //search by xpath "contains text"
        webDriver.findElement(By.xpath("//*[contains(text(),'От дешевых к дорогим')]")).click();

    }


    @Test //В бургер-меню поменять город-локацию
    public void Test02() throws InterruptedException {
        webDriver.get(url);
        webDriver.findElement(By.xpath("//button[@aria-label='Открыть меню']")).click();
        Thread.sleep(1000);
        var element = webDriver.findElement(By.xpath("//rz-city/button/span"));
        Assert.assertTrue(element.isDisplayed());
        element.click();

        var element1 = webDriver.findElement(By.cssSelector("input.autocomplete__input"));
        Assert.assertTrue(element1.isDisplayed());
        element1.click();
        element1.sendKeys("Донецк");
        Thread.sleep(1000);//без ожидания почемуто не проходит

        webDriver.findElements(By.cssSelector("#cityinput > div > ul > li")).get(2).click();
        webDriver.findElement(By.xpath("//button[contains(text(),'Применить')]")).click();
        Thread.sleep(1000);
    }


    @Test//Регистрация неполная. Этот тест  вынес в бифореметод
    public void Test03() throws InterruptedException {
        webDriver.get(url);
        webDriver.findElement(By.cssSelector("rz-header.header-component rz-user.header-actions__component")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//input[@id='auth_email']")).sendKeys(email);
        webDriver.findElement(By.xpath("//input[@formcontrolname='password']")).sendKeys(password);
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//a[contains(text(),'Зарегистрироваться')]")).click();

    }


    @Test//Cart testing: add product from "акционный товар", check the cart
    public void Test04() throws AWTException, InterruptedException {
        webDriver.get(url);
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        var element1 = webDriver.findElement(By.cssSelector("goods-sections > section:nth-child(1) > goods-section > ul > li:nth-child(3)"));
        Assert.assertTrue(element1.isDisplayed());
        element1.click();

        var element2 = webDriver.findElement(By.xpath("//button[@aria-label='Купить']"));

        element2.click();
        for (int i = 1; i < 5; i++) {//add to the cart another 4 items
            var element3 = webDriver.findElement(By.xpath("//button[@aria-label='Добавить ещё один товар']"));
            Assert.assertTrue(element3.isDisplayed());
            element3.click();
        }
        webDriver.findElement(By.partialLinkText("жить покупки")).click();
        //check the cart
        webDriver.findElement(By.xpath("//button[@opencart]")).click();
        Thread.sleep(3000);
    }


    @Test//Hover pointer on the PLP items
    public void Test05() throws InterruptedException, AWTException {
        webDriver.get(url);
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        webDriver.findElement(By.tagName("input")).sendKeys("ноутбу", ENTER);
        //element.sendKeys(ENTER);
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_ENTER);
        webDriver.manage().window().maximize();

        Actions action = new Actions(webDriver);


        for (int i = 1; i < 5; i++) {
            var we = webDriver.findElement(By.xpath("/html/body/app-root/div/div/rz-search/rz-catalog/div/div[2]/section/rz-grid/ul/li[" + i + "]"));
            action.moveToElement(we).build().perform();
            Thread.sleep(3000);

        }
    }
}