package br.com.fatec.easyDrive.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import br.com.fatec.easyDrive.exception.anotations.CPF;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public void initialize(CPF constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }
        return isValidCPF(cpf);
    }

    private boolean isValidCPF(String cpf) {
        int sum = 0, remainder;
        if (cpf.matches("(\\d)\\1{10}")) return false; 

        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        remainder = 11 - (sum % 11);
        int firstDigit = (remainder == 10 || remainder == 11) ? 0 : remainder;

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        remainder = 11 - (sum % 11);
        int secondDigit = (remainder == 10 || remainder == 11) ? 0 : remainder;

        return firstDigit == Character.getNumericValue(cpf.charAt(9)) &&
               secondDigit == Character.getNumericValue(cpf.charAt(10));
    }
}
