package online.ondemandtutor.be.validation;

public class LoginValidation {
    public boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
//            System.out.println("Invalid: Username is null or empty");
            return false;
        }
        if (!username.contains("@")) {
//            System.out.println("Invalid: Username does not contain @");
            return false;
        }
        if (username.length() > 256) {
//            System.out.println("Invalid: Username length exceeds 256 characters");
            return false;
        }
        if (username.charAt(0) == ' ') {
//            System.out.println("Invalid: Username starts with a space");
            return false;
        }
        String invalidCharacters = "!#$%&'*+/=?^_`{|}~";
        for (char c : invalidCharacters.toCharArray()) {
            if (username.contains(String.valueOf(c))) {
//                System.out.println("Invalid: Username contains invalid character " + c);
                return false;
            }
        }
//        System.out.println("Valid: Username passed all checks");
        return true;
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
//        System.out.println("Invalid: Password is null or empty");
            return false;
        }
        if (password.length() < 8) {
//        System.out.println("Invalid: Password length is less than 8 characters");
            return false;
        }
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;
        String specialCharacters = "!@#$%^&*()-_=+[{]}|;:',<.>/?";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else {
                System.out.println("Invalid: Password does not contain an uppercase letter");
                return hasUppercase = false;
            }
        }
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
            } else {
                System.out.println("Invalid: Password does not contain a number");
                return hasNumber = false;
            }
        }
        for (char c : password.toCharArray()) {
            if (specialCharacters.contains(String.valueOf(c))) {
                hasSpecialChar = true;
            } else {
                System.out.println("Invalid: Password does not contain a special character");
                return hasSpecialChar = false;
            }
        }

//        if (!hasUppercase) {
//        System.out.println("Invalid: Password does not contain an uppercase letter");
//            return false;
//        }
//        if (!hasLowercase) {
//        System.out.println("Invalid: Password does not contain a lowercase letter");
//            return false;
//        }
//        if (!hasNumber) {
//        System.out.println("Invalid: Password does not contain a number");
//            return false;
//        }
//        if (!hasSpecialChar) {
//        System.out.println("Invalid: Password does not contain a special character");
//            return false;
//        }
        System.out.println("Valid: Password passed all checks");
        return true;
    }
}
