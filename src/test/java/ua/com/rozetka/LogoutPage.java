package ua.com.rozetka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage {

//условный логаут
    @FindBy(xpath = "//button[contains(text(),' Выйти ')]")
    WebElement logoutButton;

    public LogoutPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout()  {

        logoutButton.click();

    }

}
