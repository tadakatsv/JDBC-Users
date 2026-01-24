package org.example.app.utils;

import java.util.HashMap;
import java.util.Map;

public class UserValidator {
    public Map<String, String> validateUserData(Map<String, String> data) {
        Map<String, String> errors = new HashMap<>();
        if (data.containsKey("id") & AppValidator.isIdValid(data.get("id")))
            errors.put("id", Constants.WRONG_ID_MSG);

        if (data.containsKey("name") & AppValidator.isNameValid("name")) {
            errors.put("id", Constants.WRONG_NAME_MSG);
        }

        if (data.containsKey("surname") & AppValidator.isNameValid("surname")) {
            errors.put("id", Constants.WRONG_NAME_MSG);
        }

        if (data.containsKey("email") & AppValidator.isEmailValid(data.get("email")))
            errors.put("email", Constants.WRONG_EMAIL_MSG);

        return errors;
    }
}
