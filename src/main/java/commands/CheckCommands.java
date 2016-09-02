package commands;

/**
 * Created by Dmitriy on 02.09.2016.
 */
public class CheckCommands {
    public static boolean checkSndCommand(String message) {
        if (message != null && message.startsWith("/snd")) {
            return true;
        }
        return false;
    }

    public static boolean checkHistCommand(String message) {
        if (message != null && message.startsWith("/hist")) {
            return true;
        }
        return false;
    }

    public static boolean checkExitCommand(String message) {
        if (message != null && message.startsWith("/exit")) {
            return true;
        }
        return false;
    }

    public static boolean checkLength(String message) {
        if (message != null && (message.length() < 154 && message.length() > 0)) {
            return true;
        }
        return false;
    }

    public static boolean checkNameCommand(String message) {
        if (message != null && message.startsWith("/name")) {
            return true;
        }
        return false;
    }

    public static boolean checkNameChangedCommand(String message) {
        if (message != null && message.equals("nameChanged")) {
            return true;
        }
        return false;
    }

    public static boolean checkNameNotChangedCommand(String message) {
        if (message != null && message.equals("nameNotChanged")) {
            return true;
        }
        return false;
    }
}
