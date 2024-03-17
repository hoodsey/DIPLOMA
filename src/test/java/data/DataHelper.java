package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker fakerEn = new Faker(new Locale("en"));

    private DataHelper() {
    }

    //ДОРАБОТКА
    public static String generateHolderName() {
        return fakerEn.name().lastName() + " " + fakerEn.name().firstName();
    }

    public static String generateMonth(int month) {
        return LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear(int year) {
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }

    @Value
    public static class bankCard {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }

    public static String getApprovedNumberCard() {
        return ("4444 4444 4444 4441");
    }

    public static String getDeclinedCardNumber() {
        return ("4444 4444 4444 4442");
    }

    public static bankCard generateBankCardApproved() {
        return new bankCard(getApprovedNumberCard(),
                generateMonth(0),
                generateYear(0),
                generateHolderName(),
                fakerEn.numerify("###"));
    }

    public static bankCard generateBankCardDeclined() {
        return new bankCard(getDeclinedCardNumber(),
                generateMonth(0),
                generateYear(0),
                generateHolderName(),
                fakerEn.numerify("###"));
    }

    public static bankCard generateBankCardWithLettersInNumber() {
        return new bankCard("AA",
                generateMonth(0),
                generateYear(0),
                generateHolderName(),
                fakerEn.numerify("###"));
    }

    public static bankCard generateBankCardWithNullInNumber() {
        return new bankCard("0000000000000000",
                generateMonth(0),
                generateYear(0),
                generateHolderName(),
                fakerEn.numerify("###"));
    }
    public static bankCard generateBankCardWithEmptyNumber(){
        return new bankCard(null,
                generateMonth(0),
                generateYear(0),
                generateHolderName(),
                fakerEn.numerify("###"));
    }

    public static bankCard generateBankCardWithLettersInMonth() {
        return new bankCard(getApprovedNumberCard(),
                "AA",
                generateYear(0),
                generateHolderName(),
                fakerEn.numerify("###"));
    }
    public static bankCard generateBankCardWithNullInMonth() {
        return new bankCard(getApprovedNumberCard(),
                "00",
                generateYear(0),
                generateHolderName(),
                fakerEn.numerify("###"));
    }
    public static bankCard generateBankCardWithEmptyInMonth() {
        return new bankCard(getApprovedNumberCard(),
                null,
                generateYear(0),
                generateHolderName(),
                fakerEn.numerify("###"));
    }
    public static bankCard generateBankCardWithInCorrectMonth() {
        return new bankCard(getApprovedNumberCard(),
                "13",
                generateYear(0),
                generateHolderName(),
                fakerEn.numerify("###"));
    }
    public static bankCard generateBankCardWithInCorrectYear(String year) {
        return new bankCard(getApprovedNumberCard(),
                generateMonth(0),
                year,
                generateHolderName(),
                fakerEn.numerify("###"));
    }
    public static bankCard generateBankCardWithLettersInYear() {
        return new bankCard(getApprovedNumberCard(),
                generateMonth(0),
                "AA",
                generateHolderName(),
                fakerEn.numerify("###"));
    }
    public static bankCard generateBankCardWithNullInYear() {
        return new bankCard(getApprovedNumberCard(),
                generateMonth(0),
                null,
                generateHolderName(),
                fakerEn.numerify("###"));
    }
    public static bankCard generateBankCardWithInCorrectHolder(String holder) {
        return new bankCard(getApprovedNumberCard(),
                generateMonth(0),
                generateYear(0),
                holder,
                fakerEn.numerify("###"));
    }
    public static bankCard generateBankCardWithInCorrectCVC(String cvc) {
        return new bankCard(getApprovedNumberCard(),
                generateMonth(0),
                generateYear(0),
                generateHolderName(),
                cvc);
    }

}

