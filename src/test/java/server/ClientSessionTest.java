package server;

import client.ClientWriter;
import exceptions.ClientSessionException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    @Test
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
    }

    /*@Test
    public void shouldTryToOpenOutputStream () throws ClientSessionException, IOException {
        LinkedBlockingQueue<Message> tstQueue;
        tstQueue = new LinkedBlockingQueue();
        Socket mockSocket2 = mock(Socket.class);

        InputStream mockInputStream = mock(InputStream.class);
        when(mockSocket2.getInputStream()).thenReturn(mockInputStream);

        OutputStream mockOutputStream = mock(OutputStream.class);
        when(mockSocket2.getOutputStream()).thenReturn(mockOutputStream);

        ClientSession tst = new ClientSession(mockSocket2, tstQueue);
        verify(mockSocket2.getOutputStream());
    }*/
}
