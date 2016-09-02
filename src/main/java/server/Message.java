package server;

import commands.CheckCommands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lew on 9/2/16.
 */
public class Message {

    private String textLine;
    private String name;

    ClientSession client;
    Date date;

    boolean publicMessage = false;
    boolean historical = false;
    boolean historyReqest = false;
    boolean errorMessage = false;
    boolean nameCommand = false;

    public Message(String textLine,ClientSession client,String name) {

        this.date = new Date();
        this.textLine = textLine;
        this.client = client;
        this.name = name;
        setTypes();
    }

    public Message(String textLine,ClientSession client) {
        this(textLine,client,null);
    }

    private void setTypes(){

        if(CheckCommands.checkSndCommand(textLine)){
            textLine=textLine.substring(4);
            publicMessage = true;
            historical = true;
        }

        if(CheckCommands.checkHistCommand(textLine)){
            historyReqest= true;
        }

        if(!CheckCommands.checkLength(textLine)){
            errorMessage = true;
            publicMessage = false;
            historical = false;
            textLine = "=========>your message over 150 chars <==============" + System.lineSeparator();
        }

        if(CheckCommands.checkNameCommand(textLine)){
            textLine=textLine.substring(5).trim();
            nameCommand = true;
        }

        if(!nameCommand && this.name == null)
        {
            errorMessage = true;
            publicMessage = false;
            historical = false;
            textLine = "=========> please set yout name<==============" + System.lineSeparator();
        }
    }

    public String decoratedMessage() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return this.name + dateFormat.format(date) + ":" + textLine + System.lineSeparator();
    }
    public String getTextLine() {
        return textLine;
    }

    public void setTextLine(String textLine) {
        this.textLine = textLine;
    }

    public ClientSession getClient() {
        return client;
    }

    public void setClient(ClientSession client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public boolean isPublic() {
        return publicMessage;
    }

    public boolean isHistorical() {
        return historical;
    }

    public boolean isHistoryReqest() {
        return historyReqest;
    }

    public boolean isErrorMessage() {
        return errorMessage;
    }

    public boolean isNameCommand() {
        return nameCommand;
    }
}
