package Server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lew on 9/2/16.
 */
public class Message {

    private String textLine;
    private String decoratedText;
    ClientSession client;
    Date date;

    boolean publicMessage = false;
    boolean historical = false;
    boolean historyReqest = false;
    boolean errorMessage = false;

    public Message(String textLine,ClientSession client) {

        this.date = new Date();
        this.textLine = textLine;
        this.client = client;
        setTypes();
    }

    private void setTypes(){

        if(textLine.startsWith("/snd")){
            textLine=textLine.substring(4);
            publicMessage = true;
            historical = true;
        }

        if(textLine.equals("/hist")){
            historyReqest= true;
        }

        if(textLine.length() > 15){
            errorMessage = true;
            publicMessage = false;
            historical = false;
            textLine = "=========>your message over 150 chars <==============" + System.lineSeparator();
        }
    }

    public String decoratedMessage() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date) + ":" + textLine + System.lineSeparator();
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
}
