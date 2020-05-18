package Main.model.sorting;

import Main.model.requests.Request;

import java.util.Comparator;

public abstract class RequestsSort implements Comparator {
    public static class requestsSortById implements Comparator<Request> {
        public int compare(Request one, Request two) {
            return one.getRequestId().compareTo(two.getRequestId());
        }
    }

    public static class requestsSortByType implements Comparator<Request> {
        public int compare(Request one, Request two) {
            return one.getType().compareToIgnoreCase(two.getType());
        }
    }
}
