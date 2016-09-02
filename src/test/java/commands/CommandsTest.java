package commands;

import org.junit.Test;

/**
 * Created by Java_6 on 02.09.2016.
 */
public class CommandsTest {

    @Test
    public void shouldCheckSndCorrect () {
        Commands tst = new Commands();
        assert (Commands.checkSndCommand("/snd /snd"));
        assert (!Commands.checkSndCommand("snd /snd"));
    }

    @Test
    public void shouldCheckHistCommand () {
        Commands tst = new Commands();
        assert (Commands.checkHistCommand("/hist /snd"));
        assert (!Commands.checkHistCommand("/snd     "));
    }

    @Test
    public void shouldCheckExitCommand () {
        Commands tst = new Commands();
        assert (Commands.checkExitCommand("/exit"));
        assert (!Commands.checkExitCommand("/snd /snd"));
    }

    @Test
    public void shouldCheckLengthCorrect () {
        Commands tst = new Commands();
        assert (!Commands.checkLength(""));
        assert (!Commands.checkLength("/snd 123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"));
        assert (Commands.checkLength("alalalalala"));
    }

}
