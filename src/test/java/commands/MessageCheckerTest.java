package commands;

import org.junit.Test;

/**
 * Created by Java_6 on 02.09.2016.
 */
public class MessageCheckerTest {

    @Test
    public void shouldCheckSndCorrect () {
        assert (CheckCommands.checkSndCommand("/snd /snd"));
        assert (!CheckCommands.checkSndCommand("snd /snd"));
    }

    @Test
    public void shouldCheckHistCommand () {
        assert (CheckCommands.checkHistCommand("/hist /snd"));
        assert (!CheckCommands.checkHistCommand("/snd     "));
    }

    @Test
    public void shouldCheckExitCommand () {
        assert (CheckCommands.checkExitCommand("/exit"));
        assert (!CheckCommands.checkExitCommand("/snd /snd"));
    }

    @Test
    public void shouldCheckLengthCorrect () {
        assert (!CheckCommands.checkLength(""));
        assert (!CheckCommands.checkLength("/snd 123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"));
        assert (CheckCommands.checkLength("alalalalala"));
    }

}
