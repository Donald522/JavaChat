package Client;

import Exceptions.PrinterAppendException;

/**
 * Created by Java_10 on 01.09.2016.
 */
public class runner {
    public static void main(String[] args) throws PrinterAppendException {
        Client client = new Client("localhost");
        client.openSession();
        client.closeSession();
    }
}

