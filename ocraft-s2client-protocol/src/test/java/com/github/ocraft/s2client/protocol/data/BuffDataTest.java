package com.github.ocraft.s2client.protocol.data;

import SC2APIProtocol.Data;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.github.ocraft.s2client.protocol.Constants.nothing;
import static com.github.ocraft.s2client.protocol.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BuffDataTest {
    @Test
    void throwsExceptionWhenSc2ApiBuffDataIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> BuffData.from(nothing()))
                .withMessage("sc2api buff data is required");
    }

    @Test
    void convertsAllFieldsFromSc2ApiUnit() {
        assertThatAllFieldsAreConverted(BuffData.from(sc2ApiBuffData()));
    }

    private void assertThatAllFieldsAreConverted(BuffData buff) {
        assertThat(buff.getBuff()).as("buff: id").isNotNull();
        assertThat(buff.getName()).as("buff: name").isEqualTo(BUFF_NAME);
    }

    @Test
    void throwsExceptionWhenBuffIsNotProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> BuffData.from(without(
                        () -> sc2ApiBuffData().toBuilder(),
                        Data.BuffData.Builder::clearBuffId).build()))
                .withMessage("buff is required");
    }

    @Test
    void throwsExceptionWhenNameIsNotProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> BuffData.from(without(
                        () -> sc2ApiBuffData().toBuilder(),
                        Data.BuffData.Builder::clearName).build()))
                .withMessage("name is required");
    }

    @Test
    void fulfillsEqualsContract() {
        EqualsVerifier.forClass(BuffData.class).withNonnullFields("buff", "name").verify();
    }

}