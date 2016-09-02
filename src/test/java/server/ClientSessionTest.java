package server;

import client.ClientWriter;
import exceptions.ClientSessionException;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Java_6 on 02.09.2016.
 */
public class ClientSessionTest {

    @Test (expected = ClientSessionException.class)
    public void shouldCheckStreamsOpening () throws ClientSessionException {
        LinkedBlockingQueue<Message> tstQueue;
        tstQueue = new LinkedBlockingQueue();
        Socket tstSocket = new Socket();
        ClientSession tst = new ClientSession(tstSocket, tstQueue);
        //assert (!tst.isConnected());
    }

    /*@Test
    public void shouldTryToOpenInputStream () throws ClientSessionException, IOException {
        LinkedBlockingQueue<Message> tstQueue;
        tstQueue = new LinkedBlockingQueue();
        Socket mockSocket = mock(Socket.class);

        InputStream mockInputStream = mock(InputStream.class);
        when(mockSocket.getInputStream()).thenReturn(mockInputStream);

        //OutputStream mockOutputStream = mock(OutputStream.class);
        //when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);

        ClientSession tst = new ClientSession(mockSocket, tstQueue);
        verify(mockSocket.getInputStream());
    }*/

    @Test
    public void shouldTryToOpenOutputStream () throws ClientSessionException, IOException {
        LinkedBlockingQueue tstQueue2;
        tstQueue2 = new LinkedBlockingQueue();
        Socket mockSocket2 = mock(Socket.class);

        BufferedReader readerFromSocket = mock(BufferedReader.class);


        //InputStream mockInputStream = mock(InputStream.class);
        //BufferedInputStream mockBuffInput = mock(BufferedInputStream.class);
        //InputStreamReader mockInputReader = mock(InputStreamReader.class);
        //BufferedReader mockBr = mock(BufferedReader.class);

        //when(mockSocket2.getInputStream()).thenReturn(mockInputStream);
        //when(new BufferedInputStream(mockInputStream)).thenReturn(mockBuffInput);
        //when(new InputStreamReader(mockBuffInput)).thenReturn(mockInputReader);
        //when (new BufferedReader(mockInputReader)).thenReturn(mockBr);

        OutputStream mockOutputStream2 = mock(OutputStream.class);
        when(mockSocket2.getOutputStream()).thenReturn(mockOutputStream2);

        ClientSession tst = new ClientSession(mockSocket2, tstQueue2);
        verify(mockSocket2.getOutputStream());
    }
}
