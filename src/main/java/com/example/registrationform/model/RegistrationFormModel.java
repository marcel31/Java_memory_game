package com.example.registrationform.model;

import java.util.ArrayList;

public class RegistrationFormModel {
    public ArrayList<User> users;

    public RegistrationFormModel() {
        users = new ArrayList<User>();
    }

    public static String checkNameField(String text) {
        if (text.isEmpty()){
            return  "Please enter your name";
        }else{
            return null;
        }
    }

    private static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static String checkEmailField(String text) {
        if(text.isEmpty()) {
            return "Please enter your email";
        }
        else if(!isValidEmail(text)){
            return       "Please enter a valid email";
        }
        else {
            return null;
        }
    }

    public static String checkPasswordlField(String password) {

        if (password.length() < 8) return "Password must have at least eight characters";

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return "Password consists of only letters and digits";
        }


        if (charCount >= 2 && numCount >= 2)
            return null;
        else
            return "Password must contain at least two digits";
    }

    private static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }


    private static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

    public void addUser(User newUser){
        this.users.add(newUser);
    }

    public String displayUsers() {
        String message ="\n\r";
        for (User usr : users) {
            message += usr.toString() + "\n\r";
        }
        return message;
    }
}
