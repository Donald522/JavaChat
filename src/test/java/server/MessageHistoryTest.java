package server;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.mockito.Mockito.*;

/**
 * Created by Java_6 on 02.09.2016.
 */
public class MessageHistoryTest {

    @Test (expected = NullPointerException.class)
    public void shouldNotPrintWhenNoFile () throws IOException {
        MessageHistory tstHistory = new MessageHistory(null);
        tstHistory.print("lala");
    }

   /* @Test
    public void shouldPrintCorrectly () throws IOException {
        MessageHistory tstHist = mock(MessageHistory.class);
        verify(tstHist.outStream.append("Call back"));
    }*/
}
