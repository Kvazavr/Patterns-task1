package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.data.DataGenerator.generateCity;
import static ru.netology.delivery.data.DataGenerator.generateName;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[type = text]").setValue(generateCity());
        $("[data-test-id=date]").click();
        $("[data-test-id=date] [value]").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] [value]").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [value]").setValue(firstMeetingDate);
        $("[name = name]").setValue(generateName());
        $("[name = phone]").setValue(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(Condition.visible);
        $("[data-test-id=date]").click();
        $("[data-test-id=date] [value]").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] [value]").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [value]").setValue(secondMeetingDate);
        $(".button__text").click();
        $x("//*[contains(text(), 'Необходимо подтверждение')]").shouldBe(Condition.visible);
        $x("//*[contains(text(), 'Перепланировать')]").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate)).shouldBe(Condition.visible);

    }
}
