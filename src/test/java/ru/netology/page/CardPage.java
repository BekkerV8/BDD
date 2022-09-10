package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class CardPage {
    private SelenideElement transferSumField = $("[data-test-id=amount] input");
    private SelenideElement transferCardField = $("[data-test-id=from] input");
    private SelenideElement applyButton = $("[data-test-id=action-transfer]");
    private SelenideElement notification = $("[data-test-id=error-notification]");


    public void importTransferData(int value, String cardNumber) {
        transferSumField.setValue(Integer.toString(value));
        transferCardField.setValue(String.valueOf(cardNumber));
        applyButton.click();
    }

    public void getNotification() {
        notification.shouldHave(exactText("Перевод не возможен. Баланс карты превышен")).shouldBe(visible);
    }
}