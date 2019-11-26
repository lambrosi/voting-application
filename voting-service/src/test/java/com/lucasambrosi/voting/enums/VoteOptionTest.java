package com.lucasambrosi.voting.enums;

import com.lucasambrosi.voting.exception.VoteOptionNotAllowedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VoteOptionTest {

    @Test
    void voteOptionSimTest() {
        Assertions.assertEquals(VoteOption.SIM, VoteOption.of("SIM"));
    }

    @Test
    void voteOptionNaoTest() {
        Assertions.assertEquals(VoteOption.NAO, VoteOption.of("NAO"));
    }

    @Test
    void voteOptionNotFoundTest() {
        Assertions.assertThrows(
                VoteOptionNotAllowedException.class,
                () -> VoteOption.of("TALVEZ")
        );
    }
}
