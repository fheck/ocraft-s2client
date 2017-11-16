package com.github.ocraft.s2client.protocol.game;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.github.ocraft.s2client.protocol.game.MultiplayerOptions.multiplayerSetup;
import static com.github.ocraft.s2client.protocol.game.MultiplayerOptions.multiplayerSetupFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MultiplayerOptionsTest {

    @Test
    void throwsExceptionWhenSharedPortIsNotSet() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> fullAccessTo(multiplayerSetup()).build())
                .withMessage("shared port is required");
    }

    private MultiplayerOptions.Builder fullAccessTo(Object obj) {
        return (MultiplayerOptions.Builder) obj;
    }

    @Test
    void throwsExceptionWhenServerPortIsNotSet() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> fullAccessTo(multiplayerSetup().sharedPort(1)).build())
                .withMessage("server port is required");
    }

    @Test
    void throwsExceptionWhenClientPortsAreEmpty() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> fullAccessTo(multiplayerSetup().sharedPort(1).serverPort(PortSet.of(1, 2))).build())
                .withMessage("client port list is required");
    }

    @Test
    void throwsExceptionWhenSharedPortLowerThanOne() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> multiplayerSetup().sharedPort(0))
                .withMessage("shared port must be greater than 0");
    }

    @Test
    void createsDefaultPortSetupFromGivenPortAndPlayerCount() {
        MultiplayerOptions multiplayerOptions = multiplayerSetupFor(5000, 2);

        assertThat(multiplayerOptions.getSharedPort()).as("multiplayer port setup: shared port").isEqualTo(5001);
        assertThat(multiplayerOptions.getServerPort()).as("multiplayer port setup: server port")
                .isEqualTo(PortSet.of(5002, 5003));
        assertThat(multiplayerOptions.getClientPorts()).as("multiplayer port setup: client ports")
                .containsExactly(PortSet.of(5004, 5005), PortSet.of(5006, 5007));
    }

    @Test
    void fulfillsEqualsContract() {
        EqualsVerifier.forClass(MultiplayerOptions.class).withNonnullFields("serverPort", "clientPorts").verify();
    }
}