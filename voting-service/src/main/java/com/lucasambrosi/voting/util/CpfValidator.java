package com.lucasambrosi.voting.util;

public class CpfValidator {

    private static final int CPF_SIZE = 11;
    private static final int[] CPF_PESO = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean isValid(String cpf) {
        if (!isCpf(cpf)) {
            return false;
        }
        Integer digito1 = calculateDigit(cpf.substring(0, 9));
        Integer digito2 = calculateDigit(cpf.substring(0, 9) + digito1);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private static boolean isCpf(String cpf) {
        return cpf != null && cpf.length() == CPF_SIZE;
    }

    private static int calculateDigit(String str) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * CPF_PESO[CPF_PESO.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }
}
