package commands;

public class Commands {
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
        if (message != null && (message.length() < 150 && message.length() > 0)) {
            return true;
        }
        return false;
    }
}
