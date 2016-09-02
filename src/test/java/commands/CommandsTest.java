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
    }

    @Test
    public void shouldNotCheckSndForUncorrect () {
        Commands tst = new Commands();
        assert (!Commands.checkSndCommand("snd /snd"));
    }

    @Test
    public void shouldCheckHistCommand () {
        Commands tst = new Commands();
        assert (Commands.checkHistCommand("/hist /snd"));
    }

    @Test
    public void shouldNotCheckHistCommandForUncorrect () {
        Commands tst = new Commands();
        assert (!Commands.checkHistCommand("/snd     "));
    }

    @Test
    public void shouldCheckExitCommand () {
        Commands tst = new Commands();
        assert (Commands.checkExitCommand("/exit"));
    }

    @Test
    public void shouldNotCheckExitCommandForUncorrect () {
        Commands tst = new Commands();
        assert (!Commands.checkExitCommand("/snd /snd"));
    }
}
