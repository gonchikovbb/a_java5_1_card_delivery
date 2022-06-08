package ru.netology;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

import java.util.Locale;

public class TestWithFaker {

    private static  Faker faker;

    @BeforeAll
    static void setUpAll() {faker = new Faker(new Locale("ru"));}

    @Test
    void shouldGenerateTestData(){
    }
}
