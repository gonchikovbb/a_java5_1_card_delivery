package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardDeliveryInfo;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selectors.withText;
import org.openqa.selenium.Keys;

class TestWithFaker {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void openUrl() {
        Configuration.holdBrowserOpen = true;
        open ("http://localhost:9999/");
    }

    @Epic(value = "Банк")
    @Feature(value = "Функционал карт")
    @Story(value = "Юзер стори 1")
    @Description(value = "Тестируетсе фэйкер")
    @Link(name = "ссылка на кейс", value = "https://github.com/gonchikovbb/a_java5_1_card_delivery" )
    @Test
    void shouldGenerateTestData(){
        String date = DataGenerator.generateDate(5);
        CardDeliveryInfo cardDeliveryInfo = DataGenerator.generateInfo("ru",5);
        $("[data-test-id=city] input").setValue(cardDeliveryInfo.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(cardDeliveryInfo.getName());
        $("[data-test-id=phone] input").setValue(cardDeliveryInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//*[text()=\"Запланировать\"]").click();

        $(withText("Успешно!"));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + date));

        String newDate = DataGenerator.generateDate(6);
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(newDate);
        $x("//*[text()=\"Запланировать\"]").click();
        $x("//*[text()=\"У вас уже запланирована встреча на другую дату. Перепланировать?\"]").click();
    }
}
