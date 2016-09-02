package server;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Java_6 on 02.09.2016.
 */
public class MessageTest {

    @Test
    public void shouldDecorateCorrectly() {
        Message tst = new Message("proverka", null, "maxim");
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        assert (tst.decoratedMessage().contains("proverka"));
        assert (tst.decoratedMessage().contains("maxim"));
    }

    @Test
    public void shouldSetTypesForSndCorrectly() {
        Message tst = new Message("/snd proverka", null, "durak");
        assert (tst.getTextLine().contains("proverka"));
        assert (tst.publicMessage);
        assert (tst.historical);
    }

    @Test
    public void shouldSetTypesForHistCorrectly() {
        Message tst = new Message("/hist proverka", null, "durak");
        assert (tst.historyReqest);
    }

    @Test
    public void shouldSetTypesForTooLongMessages() {
        Message tst = new Message("/snd 123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",
                null, "durak");
        assert (tst.getTextLine().equals("=========>your message over 150 chars <==============" + System.lineSeparator()));
        assert (tst.publicMessage == false);
        assert (tst.historical == false);
        assert (tst.errorMessage == true);
    }
}
