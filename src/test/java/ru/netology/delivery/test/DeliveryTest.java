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
//        open("http://localhost:9999/");
        $("[type = text]").setValue(generateCity());
        $("[data-test-id=date]").click();
        $("[data-test-id=date] [value]").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] [value]").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [value]").setValue(firstMeetingDate);
        $("[name = name]").setValue(generateName());
        $("[name = phone]").setValue(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(2)).shouldBe(Condition.visible);
        $("[data-test-id=date]").click();
        $("[data-test-id=date] [value]").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] [value]").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [value]").setValue(secondMeetingDate);
        $(".button__text").click();
        $(".notification__content").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(Condition.visible);
        $x("Перепланировать").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + secondMeetingDate)).shouldBe(Condition.visible);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
    }
}
