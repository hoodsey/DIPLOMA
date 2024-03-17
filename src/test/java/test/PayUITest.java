package test;

import data.DataBaseHelper;
import data.DataHelper;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import page.MainPage;
import page.PayPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayUITest {
    private WebDriver driver;
    public MainPage mainPage;
    public PayPage payPage;

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
        payPage = PageFactory.initElements(driver, PayPage.class);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    // позитивный тест оплаты
    @Test
    @DisplayName("Отправка формы оплтаты по карте при валидных данных карты")
    void shouldSuccessPayByApprovedCard() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardApproved());
        payPage.clickContinueButton();
        payPage.getSuccessPayNotification();
        var actual = DataBaseHelper.getStatusPayInDataBase();
        assertEquals("APPROVED", actual);
    }

//    @Step("Отправка формы оплтаты в кредит при валидных данных карты")
//    @Test
//    void shouldSuccessCreditByApprovedCard(){
//        mainPage.creditButtonClick();
//        payPage.inputData(DataHelper.generateBankCardApproved());
//        payPage.clickContinueButton();
//        payPage.getSuccessPayNotification();
//        //var actual = DataBaseHelper.getStatusPaymentRequest();
//        assertEquals("APPROVED", "APPROVED");
//    }

    // негативный тест оплаты
    @DisplayName("Отправка формы оплтаты по карте при невалидных данных карты")
    @Test
    void shouldErrorPayByDeclinedCard() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardDeclined());
        payPage.clickContinueButton();
        payPage.getErrorNotification(); // БАГ
        var actual = DataBaseHelper.getStatusPayInDataBase();
        assertEquals("DECLINED", actual);
    }

    // негативные проверки заполнение формы оплаты
    @DisplayName("Отправка формы при вводе букв в поле Номер карты")
    @Test
    void shouldErrorPayWithLettersInNumber() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithLettersInNumber());
        payPage.clickContinueButton();
        payPage.checkErrorNeedNumbers(); // БАГ
    }

    @DisplayName("Отправка формы при вводе нулей в поле Номер карты")
    @Test
    void shouldErrorPayWithNullInNumber() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithNullInNumber());
        payPage.clickContinueButton();
        payPage.checkErrorNeedValidCard(); //БАГ
    }

    @DisplayName("Отправка формы при пустом поле Номер карты")
    @Test
    void shouldErrorPayWithEmptyNumber() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithEmptyNumber());
        payPage.clickContinueButton();
        payPage.checkErrorNeedFillField();
    }

    @DisplayName("Отправка формы при вводе букв в поле Месяц")
    @Test
    void shouldErrorPayWithLettersInMonth() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithLettersInMonth());
        payPage.clickContinueButton();
        payPage.checkErrorNeedNumbers();
    }

    @DisplayName("Отправка формы при вводе нулей в поле Месяц")
    @Test
    void shouldErrorPayWithNullInMonth() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithNullInMonth());
        payPage.clickContinueButton();
        payPage.checkErrorCardExpiryDate();
    }

    @DisplayName("Отправка формы при вводе пустом поле месяц")
    @Test
    void shouldErrorPayWithEmptyInMonth() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithEmptyInMonth());
        payPage.clickContinueButton();
        payPage.checkErrorNeedFillField(); // БАГ
    }

    @DisplayName("Отправка формы при вводе невалидного числа в поле месяц")
    @Test
    void shouldErrorPayWithInCorrectMonth() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectMonth());
        payPage.clickContinueButton();
        payPage.checkErrorCardExpiryDate();
    }

    @DisplayName("Отправка формы при вводе невалидного года в поле Год")
    @Test
    void shouldErrorPayWithInCorrectYear() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectYear("23"));
        payPage.clickContinueButton();
        payPage.checkErrorCardHasExpired();
    }

    @DisplayName("Отправка формы при вводе букв в поле Год")
    @Test
    void shouldErrorPayWithLettersInYear() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithLettersInYear());
        payPage.clickContinueButton();
        payPage.checkErrorNeedNumbers(); // БАГ
    }

    @DisplayName("Отправка формы при вводе нулей в поле Год")
    @Test
    void shouldErrorPayWithNullInYear() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithNullInYear());
        payPage.clickContinueButton();
        payPage.checkErrorCardHasExpired(); // БАГ
    }

    @DisplayName("Отправка формы при вводе 30го года в поле Год")
    @Test
    void shouldErrorPayWithInCorrectFutureYear() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectYear("30"));
        payPage.clickContinueButton();
        payPage.checkErrorCardExpiryDate();
    }
    @DisplayName("Отправка формы при пустом поле Год")
    @Test
    void shouldErrorPayWithEmptyYear() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectYear(null));
        payPage.clickContinueButton();
        payPage.checkErrorNeedFillField(); // БАГ
    }

    @DisplayName("Отправка формы при вводе цифр в поле Владелец")
    @Test
    void shouldErrorPayWithNumberInHolder() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectHolder("44"));
        payPage.clickContinueButton();
        payPage.checkErrorNeedLetters(); // БАГ
    }

    @DisplayName("Отправка формы при пустом поле Владелец")
    @Test
    void shouldErrorPayWithNullHolder() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectHolder(null));
        payPage.clickContinueButton();
        payPage.checkErrorNeedFillField();
    }

    @DisplayName("Отправка формы при вводе букв в поле CVC/CVV")
    @Test
    void shouldErrorPayWithLettersInCVC() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectCVC("AAA"));
        payPage.clickContinueButton();
        payPage.checkErrorNeedNumbers(); // БАГ
    }

    @DisplayName("Отправка формы при вводе двух цифр в поле CVC/CVV")
    @Test
    void shouldErrorPayWithTwoNumbersInCVC() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectCVC("11"));
        payPage.clickContinueButton();
        payPage.checkErrorNeedTwoNumbers(); // БАГ
    }


    @Test
    @DisplayName("Отправка формы при пустом в поле CVC/CVV")
    void shouldErrorPayEmptyCVC() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectCVC(null));
        payPage.clickContinueButton();
        payPage.checkErrorNeedFillField();
    }

    @DisplayName("Отправка формы при вводе нулей CVC/CVV")
    @Test
    void shouldErrorPayWithNullInCVC() {
        mainPage.paymentButtonClick();
        payPage.inputData(DataHelper.generateBankCardWithInCorrectCVC("000"));
        payPage.clickContinueButton();
        payPage.checkErrorNeedFillField(); // БАГ
    }

}


