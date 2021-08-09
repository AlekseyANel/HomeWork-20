package ua.com.rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    @FindBy(xpath = "//input[@id='auth_email']")
    WebElement loginInputField;

    @FindBy(xpath = "//input[@formcontrolname='password']")
    WebElement passwordInputField;

    @FindBy(xpath = "//button[contains(text(),' Войти ')]")
    WebElement loginButton;

    @FindBy(css = "button[aria-label='Закрыть модальное окно']")
    WebElement closeButton;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void login(String login, String password)  {

        loginInputField.sendKeys(login);
        passwordInputField.sendKeys(password);
        loginButton.click();
        closeButton.click();
    }

}
