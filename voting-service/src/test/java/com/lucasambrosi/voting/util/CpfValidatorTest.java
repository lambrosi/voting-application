package com.lucasambrosi.voting.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CpfValidatorTest {

    @Test
    void validCpfTest() {
        final String VALID_CPF = "97211264039";
        Assertions.assertTrue(CpfValidator.isValid(VALID_CPF));
    }

    @Test
    void invalidCpfTest() {
        final String INVALID_CPF = "87542169854";
        Assertions.assertFalse(CpfValidator.isValid(INVALID_CPF));
    }

    @Test
    void invalidCpfLessThanElevenCharactersTest() {
        final String INVALID_CPF = "54540544";
        Assertions.assertFalse(CpfValidator.isValid(INVALID_CPF));
    }
}
