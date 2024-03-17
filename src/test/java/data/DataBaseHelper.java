package data;

import io.qameta.allure.Step;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;


public class DataBaseHelper {

    private DataBaseHelper() {
    }

    public static Connection getConn() throws SQLException {
        return getConnection(System.getProperty("db.url"), "app", "pass");
    }


    @SneakyThrows
    public static String getStatusPayCreditInDataBase() {
        var connector = getConn();
        var countStatement = connector.createStatement();
        var resultSet = countStatement.executeQuery("SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;");
        if (resultSet.next()) {
            return resultSet.getString("status");
        }
        return null;
    }

    @SneakyThrows
    @Step("Запрос статуса оплаты по карте из БД")
    public static String getStatusPayInDataBase() {
        var connector = getConn();
        var countStatement = connector.createStatement();
        var resultSet = countStatement.executeQuery("SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;");
        if (resultSet.next()) {
            return resultSet.getString("status");
        }
        return null;
    }


}