package Client;

import Exceptions.PrinterAppendException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lew on 9/1/16.
 */
public class UserConsole {
    public static void main(String[] args) {
        String message = "";
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        while(!message.equals("quit")){
            try {
                message = buff.readLine();
                System.out.print(message);
                //call some pipe
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
