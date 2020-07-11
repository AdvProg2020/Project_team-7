package Main.server.model;

public class IDGenerator {

    public static String getNewID(StringBuilder lastUsedID) {
        createNewID(lastUsedID);
        return lastUsedID.toString();
    }

    private static void createNewID(StringBuilder lastUsedID) {
        int lastIndex = lastUsedID.length() - 1;
        if (lastUsedID.charAt(lastIndex) == 'z') {
            lastUsedID.append('A');
        } else {
            lastUsedID.setCharAt(lastIndex, (char) (lastUsedID.charAt(lastIndex) + 1));
        }
    }
}
