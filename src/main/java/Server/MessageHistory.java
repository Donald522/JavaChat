package Server;

import Exceptions.PrinterAppendException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;

/**
 * Created by lew on 9/1/16.
 */
public class MessageHistory {

    private String fileName = "logger.log";
    OutputStreamWriter outStream;

    public MessageHistory(String fileName) {
        this.fileName = fileName;
    }

    public void print(String message) throws IOException {
        try {
                openSession();
            outStream.append(message);
        } catch (IOException e) {
            throw new IOException("io", e);
        }
    }

    public void openSession() throws IOException {
        try {
            outStream = new OutputStreamWriter(
                    new BufferedOutputStream(
                            new FileOutputStream(fileName,true)));
        } catch (IOException e) {
            throw new IOException("io", e);
        }
    }


    public void closeSession() throws IOException{
        try {
            outStream.close();
        } catch (IOException e) {
            throw new IOException("io", e);
        }
    }

    public List<String> readLines() {
        try {
            return FileUtils.readLines(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
