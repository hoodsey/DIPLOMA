package page;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//*[contains(text(),'Купить')]")
    private WebElement payButton;
    @FindBy(xpath ="//*[contains(text(),'Купить в кредит')]")
    private WebElement creditButton;

    @Step("Нажать на кнопку Купить")
    public void paymentButtonClick() {
        payButton.click();
    }
    @Step("Нажать на кнопку Купить в кредит")
    public void creditButtonClick() {
        creditButton.click();
    }

}