package Main.model;

public class IDGenerator {
    //TODO : change all Constructors ID due to this class

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
