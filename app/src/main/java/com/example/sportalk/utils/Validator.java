package com.example.sportalk.utils;

import android.util.Patterns;
import android.widget.EditText;

public class Validator {

    public boolean validateEmail(String emailInput, EditText email) {
        boolean valid = true;
        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            valid= false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Email isn't valid");
            valid= false;
        } else {
            email.setError(null);
        }
        return valid;
    }

    public boolean validatePassword(String fieldInput, EditText field) {
        boolean valid = true;
        if (fieldInput.isEmpty()) {
            field.setError("Field can't be empty");
            valid=false;
        } else if (fieldInput.length() < 6) {
            field.setError("Password must contain at least 6 characters");
            valid=false;
        } else {
            field.setError(null);
        }
        return valid;
    }

    public boolean validateField(String fieldInput, EditText field) {
        boolean valid = true;
        if (fieldInput.isEmpty()) {
            field.setError("Field can't be empty");
            valid=false;
        } else {
            field.setError(null);
        }
        return valid;
    }
}
