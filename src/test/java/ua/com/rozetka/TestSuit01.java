package ua.com.rozetka;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;


public class TestSuit01 {
    public ChromeDriver webDriver;
    String email = "email@here.com";
    String password = "passwowrd_here";
    public Robot r;
    public Actions action;

    @Before
    public void startUp() throws AWTException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");//Отключить уведомления-permissions
        System.setProperty("webDriver.chrome.driver", "chromedriver.exe");
        webDriver = new ChromeDriver(options);
    }

    @After
    public void totalQuit() {
        webDriver.quit();
    }


    @Test //Ноутбуки и компьютеры -> Ноутбуки -> Поиск бренда НР -> Сортировка от дешёвых к дорогим
    public void Test01() throws InterruptedException {
        webDriver.get("https://rozetka.com.ua/");
        //search by full css selector
        var element = webDriver.findElement(By.cssSelector("body > app-root > div > div > rz-main-page > div > aside > main-page-sidebar > sidebar-fat-menu > div > ul > li:nth-child(1) > a"));
        element.click();
        Thread.sleep(3000);//без ожидания почемуто не работает(

        //search by attribute value in html tag
        var element1 = webDriver.findElement(By.cssSelector("img[alt='Ноутбуки']"));
        Assert.assertTrue(element1.isDisplayed());
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element1);
        Thread.sleep(1000);
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
        webDriver.get("https://rozetka.com.ua/");
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


   @Test//Регистрация неполная
    public void Test03() throws InterruptedException {
        webDriver.get("https://rozetka.com.ua/");
        webDriver.findElement(By.cssSelector("rz-header.header-component rz-user.header-actions__component")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//input[@id='auth_email']")).sendKeys(email);
        webDriver.findElement(By.xpath("//input[@formcontrolname='password']")).sendKeys(password);
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//a[contains(text(),'Зарегистрироваться')]")).click();

    }


    @Test//Cart testing: add product from "акционный товар", check the cart
    public void Test04() throws AWTException, InterruptedException {
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        webDriver.get("https://rozetka.com.ua/");
        webDriver.findElement(By.cssSelector("goods-section.ng-star-inserted > ul.main-goods__grid > li:nth-child(2)")).click();
        var element = webDriver.findElement(By.xpath("//button[@aria-label='Купить']"));
        Assert.assertTrue(element.isDisplayed());
        webDriver.manage().window().maximize();
        element.click();
        for (int i = 1; i < 5; i++) {//add to the cart another 4 items
            var element1 = webDriver.findElement(By.xpath("//button[@aria-label='Добавить ещё один товар']"));
            Assert.assertTrue(element.isDisplayed());
        element1.click();}
        webDriver.findElement(By.partialLinkText("жить покупки")).click();
        //check the cart
        webDriver.findElement(By.xpath("//button[@opencart]")).click();
        Thread.sleep(3000);
    }


    @Test//Hover pointer on the PLP items
    public void Test05() throws InterruptedException, AWTException {
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        webDriver.get("https://rozetka.com.ua/");
        webDriver.findElement(By.tagName("input")).sendKeys("ноутбу");
        Robot r = new Robot();
        Actions action = new Actions(webDriver);
        r.keyPress(KeyEvent.VK_ENTER);
        webDriver.manage().window().maximize();
        //r.keyPress(KeyEvent.VK_F11);
        Thread.sleep(1000);
        for (int i = 1; i < 6; i++) {
        var we = webDriver.findElement(By.xpath("//section/rz-grid/ul/li["+i+"]"));
        action.moveToElement(we).build().perform();
            Thread.sleep(2000);
        }

    }
}