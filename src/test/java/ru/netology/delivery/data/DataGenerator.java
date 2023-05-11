package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity() {
        String city = faker.address().city();
        return city;
    }

    public static String generateName() {
        String name = faker.name().name();
        return name;
    }

    public static String generatePhone() {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser() {
            UserInfo user = new UserInfo(generateCity(), generateName(), generatePhone());
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
