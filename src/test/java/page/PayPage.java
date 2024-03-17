package page;

import data.DataHelper;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PayPage {

    private WebDriver driver;

    public PayPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(css = "[placeholder='0000 0000 0000 0000']")
    private WebElement number;
    @FindBy(css = "[placeholder='08']")
    private WebElement month;
    @FindBy(css = "[placeholder='22']")
    private WebElement year;
    @FindBy(xpath = "(//span[@class= 'input__inner']/span/input)[4]")
    private WebElement holder;
    @FindBy(xpath = "(//button)[3]")
    private WebElement continueButton;

    @FindBy(css = "[placeholder='999']")
    private WebElement cvc;
    @FindBy(css =  ".notification_visible")
    private WebElement visibleNotification;
    @FindBy(css = ".notification_status_ok div.notification__title")
    private WebElement successNotificationTitle;
    @FindBy(css = ".notification_status_ok div.notification__content")
    private WebElement successNotificationContent;

    @FindBy(css = ".notification_status_error div.notification__title")
    private WebElement errorNotificationTitle;
    @FindBy(css = ".notification_status_error div.notification__content")
    private WebElement errorNotificationContent;

    @FindBy(xpath = "//*[contains(text(),'Неверный формат')]")
    private WebElement errorValueFormat;
    @FindBy(css = ".input__sub")
    private WebElement ErrorValueNumber;

    @Step("Ввод в форму данных: {card}")
    public void inputData(DataHelper.bankCard card) {
        if (card.getNumber() != null) {
            number.sendKeys(card.getNumber());
        }
        if (card.getMonth() != null) {
            month.sendKeys(card.getMonth());
        }
        if (card.getYear() != null) {
            year.sendKeys(card.getYear());
        }
        if (card.getHolder() != null) {
            holder.sendKeys(card.getHolder());
        }
        if (card.getCvc() != null) {
            cvc.sendKeys(card.getCvc());
        }
    }

    @Step("Нажатие кнопки 'Продолжить'")
    public void clickContinueButton() {
        continueButton.click();
    }

    @Step("Проверка отображения сообщения об успешной операции по оплате")
    public void getSuccessPayNotification() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(successNotificationTitle));
        Assertions.assertEquals("Успешно", successNotificationTitle.getText());
        Assertions.assertEquals("Операция одобрена Банком.", successNotificationContent.getText());
    }
    @Step("Ожидание загрузки сообщения об ошибки операции по оплате")
    public void waitErrorNotification() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(visibleNotification));
    }
    @Step("Проверка отображения сообщения об ошибки операции по оплате")
    public void getErrorNotification() {
        try {
            Assertions.assertEquals("Ошибка", errorNotificationTitle.getText());
            Assertions.assertEquals("Ошибка! Банк отказал в проведении операции.", errorNotificationContent.getText());
        } catch (RuntimeException exception){
            Assertions.fail("Сообщение об ошибки оплаты остутствует");
        }
    }

    @Step("Проверка появления ошибки 'Неверный формат'")
    public void checkErrorWrongFormatField() {
        Assertions.assertEquals("Неверный формат", ErrorValueNumber.getText());
    }

    @Step("Проверка появления ошибки 'Должно состоять из цифр'")
    public void checkErrorNeedNumbers() {
        Assertions.assertEquals("Должно состоять из цифр", ErrorValueNumber.getText());
    }

    @Step("Проверка появления ошибки 'Должно содержать валидный номер карты'")
    public void checkErrorNeedValidCard() {
        try {
            Assertions.assertEquals("Должно содержать валидный номер карты", ErrorValueNumber.getText());
        } catch (RuntimeException ignored) {
            Assertions.fail("Сообщение об ошибки под полем отсутсвет");
        }
    }

    @Step("Проверка появления ошибки 'Поле обязательно для заполнения'")
    public void checkErrorNeedFillField() {
        try {
            Assertions.assertEquals("Поле обязательно для заполнения", ErrorValueNumber.getText());
        } catch (RuntimeException ignored) {
            Assertions.fail("Сообщение об ошибки под полем отсутсвет");
    }
}

@Step("Проверка появления ошибки 'Неверно указан срок действия карты'")
public void checkErrorCardExpiryDate() {
    Assertions.assertEquals("Неверно указан срок действия карты", ErrorValueNumber.getText());
}

@Step("Проверка появления ошибки 'Неверно указан срок действия карты'")
public void checkErrorCardHasExpired() {
    Assertions.assertEquals("Истёк срок действия карты", ErrorValueNumber.getText());
}

@Step("Проверка появления ошибки 'Должно состоять из букв'")
public void checkErrorNeedLetters() {
    try {
        Assertions.assertEquals("Должно состоять из букв", ErrorValueNumber.getText());
    } catch (RuntimeException ignored) {
        Assertions.fail("Сообщение об ошибки под полем отсутсвет");
    }
}

@Step("Проверка появления ошибки 'Должно содержать 3 цифры'")
public void checkErrorNeedTwoNumbers() {
    Assertions.assertEquals("Должно содержать 3 цифры", ErrorValueNumber.getText());

}

}