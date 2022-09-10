package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.CardPage;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public class TransferMoneyTest {
    @BeforeEach
    void shouldLogin() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }


    @Test
    void shouldTransferMoneySecondToFirstCard() {
        int value = 1_000;
        String cardNumber = String.valueOf(DataHelper.getSecondCardNumber());
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonSecondToFirst();
        val transferPage = new CardPage();
        transferPage.importTransferData(value, cardNumber);
        var firstCardBalance1 = dashboardPage.getFirstCardBalance();
        var secondCardBalance1 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(secondCardBalance - value, secondCardBalance1);
        Assertions.assertEquals(firstCardBalance + value, firstCardBalance1);

    }

    @Test
    void shouldTransferMoneyFirstToSecondCard() {
        int value = 2_000;
        String cardNumber = String.valueOf(DataHelper.getFirstCardNumber());
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonFirstToSecond();
        val transferPage = new CardPage();
        transferPage.importTransferData(value, cardNumber);
        var firstCardBalance1 = dashboardPage.getFirstCardBalance();
        var secondCardBalance1 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalance - value, firstCardBalance1);
        Assertions.assertEquals(secondCardBalance + value, secondCardBalance1);

    }

    @Test
    void doNotShouldTransferMoneyFirstToSecondCardAfterLimit() {
        int value = 10_000;
        String cardNumber = String.valueOf(DataHelper.getSecondCardNumber());
        val dashboardPage = new DashboardPage();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonSecondToFirst();
        val transferPage = new CardPage();
        transferPage.importTransferData(value + secondCardBalance, cardNumber);
        transferPage.getNotification();
    }
}
