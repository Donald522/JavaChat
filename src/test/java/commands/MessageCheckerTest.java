package commands;

import org.junit.Test;

/**
 * Created by Java_6 on 02.09.2016.
 */
public class MessageCheckerTest {

    @Test
    public void shouldCheckSndCorrect () {
        assert (MessageChecker.checkSndCommand("/snd /snd"));
        assert (!MessageChecker.checkSndCommand("snd /snd"));
    }

    @Test
    public void shouldCheckHistCommand () {
        assert (MessageChecker.checkHistCommand("/hist /snd"));
        assert (!MessageChecker.checkHistCommand("/snd     "));
    }

    @Test
    public void shouldCheckExitCommand () {
        assert (MessageChecker.checkExitCommand("/exit"));
        assert (!MessageChecker.checkExitCommand("/snd /snd"));
    }

    @Test
    public void shouldCheckLengthCorrect () {
        assert (!MessageChecker.checkLength(""));
        assert (!MessageChecker.checkLength("/snd 123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"));
        assert (MessageChecker.checkLength("alalalalala"));
    }

}
