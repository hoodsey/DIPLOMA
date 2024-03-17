package test;

import data.DataBaseHelper;
import data.DataHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import page.CreditPage;
import page.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardUITest {
    private WebDriver driver;
    public MainPage mainPage;
    public CreditPage creditPage;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        Allure.addAttachment("Browser Info", "Chrome Browser");
        mainPage = PageFactory.initElements(driver, MainPage.class);
        creditPage = PageFactory.initElements(driver, CreditPage.class);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    // позитивный тест оплаты
    @Test
    @DisplayName("Отправка формы оплаты в кредит при валидных данных карты")
    void shouldSuccessPayByApprovedCard() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardApproved());
        creditPage.clickContinueButton();
        creditPage.getSuccessPayNotification();
        var actual = DataBaseHelper.getStatusPayCreditInDataBase();
        assertEquals("APPROVED", actual);
    }


    // негативный тест оплаты кредит
    @DisplayName("Отправка формы оплаты в кредит при невалидных данных карты")
    @Test
    void shouldErrorPayByDeclinedCard() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardDeclined());
        creditPage.clickContinueButton();
        creditPage.getErrorNotification(); // БАГ
        var actual = DataBaseHelper.getStatusPayCreditInDataBase();
        assertEquals("DECLINED", actual);
    }

    // негативные проверки заполнение формы оплаты
    @DisplayName("Отправка формы оплаты в кредит при вводе букв в поле Номер карты")
    @Test
    void shouldErrorPayWithLettersInNumber() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithLettersInNumber());
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedNumbers(); // БАГ
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе нулей в поле Номер карты")
    @Test
    void shouldErrorPayWithNullInNumber() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithNullInNumber());
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedValidCard(); //БАГ
    }

    @DisplayName("Отправка формы оплаты в кредит при пустом поле Номер карты")
    @Test
    void shouldErrorPayWithEmptyNumber() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithEmptyNumber());
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedFillField(); // БАГ
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе букв в поле Месяц")
    @Test
    void shouldErrorPayWithLettersInMonth() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithLettersInMonth());
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedNumbers(); // БАГ
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе нулей в поле Месяц")
    @Test
    void shouldErrorPayWithNullInMonth() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithNullInMonth());
        creditPage.clickContinueButton();
        creditPage.checkErrorCardExpiryDate(); //БАГ
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе пустом поле месяц")
    @Test
    void shouldErrorPayWithEmptyInMonth() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithEmptyInMonth());
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedFillField(); // БАГ
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе невалидного числа в поле месяц")
    @Test
    void shouldErrorPayWithInCorrectMonth() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectMonth());
        creditPage.clickContinueButton();
        creditPage.checkErrorCardExpiryDate();
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе невалидного года в поле Год")
    @Test
    void shouldErrorPayWithInCorrectYear() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectYear("23"));
        creditPage.clickContinueButton();
        creditPage.checkErrorCardHasExpired();
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе букв в поле Год")
    @Test
    void shouldErrorPayWithLettersInYear() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithLettersInYear());
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedNumbers(); // БАГ
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе нулей в поле Год")
    @Test
    void shouldErrorPayWithNullInYear() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithNullInYear());
        creditPage.clickContinueButton();
        creditPage.checkErrorCardHasExpired(); // БАГ
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе 30го года в поле Год")
    @Test
    void shouldErrorPayWithInCorrectFutureYear() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectYear("30"));
        creditPage.clickContinueButton();
        creditPage.checkErrorCardExpiryDate();
    }
    @DisplayName("Отправка формы оплаты в кредит при пустом поле Год")
    @Test
    void shouldErrorPayWithEmptyYear() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectYear(null));
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedFillField(); // БАГ
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе цифр в поле Владелец")
    @Test
    void shouldErrorPayWithNumberInHolder() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectHolder("44"));
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedLetters(); // БАГ
    }

    @DisplayName("Отправка формы оплтаты в кредит при пустом поле Владелец")
    @Test
    void shouldErrorPayWithNullHolder() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectHolder(null));
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedFillField();
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе букв в поле CVC/CVV")
    @Test
    void shouldErrorPayWithLettersInCVC() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectCVC("AAA"));
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedNumbers(); // БАГ
    }

    @DisplayName("Отправка формы формы оплаты в кредит при вводе двух цифр в поле CVC/CVV")
    @Test
    void shouldErrorPayWithTwoNumbersInCVC() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectCVC("11"));
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedTwoNumbers(); // БАГ
    }


    @Test
    @DisplayName("Отправка формы оплаты в кредит при пустом в поле CVC/CVV")
    void shouldErrorPayEmptyCVC() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectCVC(null));
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedFillField();
    }

    @DisplayName("Отправка формы оплаты в кредит при вводе нулей CVC/CVV")
    @Test
    void shouldErrorPayWithNullInCVC() {
        mainPage.creditButtonClick();
        creditPage.inputData(DataHelper.generateBankCardWithInCorrectCVC("000"));
        creditPage.clickContinueButton();
        creditPage.checkErrorNeedFillField(); // БАГ
    }

}


