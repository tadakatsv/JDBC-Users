package org.example.app.utils;

public class AppValidator {

    // Regex для id.
    // Регулярний вираз для позитивних цілих чисел без нулів, мінусів або ком на початку.
    // ^ вказує на початок рядка
    // [1-9] відповідає будь-якій цифрі від 1 до 9 і гарантує відсутність нулів на початку.
    // $ вказує на кінець рядка
    public final static String ID_RGX = "^[1-9]$";

    // Regex для email
    public final static String EMAIL_RGX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    // Regex для name surname
    public final static String NAMES_RGX = "^[a-zA-Zа-яА-ЯіІїЇєЄґҐ'\\-]{2,30}$";
    // Валідація вхідного значення id
    public static boolean isIdValid(String id) {
        if (id != null)
            return id.isEmpty() || !id.matches(ID_RGX);
        return false;
    }

    // Валидация имени
    public static boolean isNameValid(String name) {
        if (name != null)
            return name.isEmpty() || !name.matches(NAMES_RGX);
        return false;
    }

    // Валідація вхідного значення email
    public static boolean isEmailValid(String email) {
        if (email != null)
            return email.isEmpty() || !email.matches(EMAIL_RGX);
        return false;
    }
}
