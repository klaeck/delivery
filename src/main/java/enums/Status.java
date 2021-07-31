package enums;

import exceptions.ErrorPageException;

public enum Status {
    open,
    validated,
    paid,
    delivered,
    canceled;

    public static Status getStatus(String status) throws ErrorPageException {
        if (status.equalsIgnoreCase(open.name())) {
            return Status.open;
        } else if (status.equalsIgnoreCase(validated.name())) {
            return Status.validated;
        } else if (status.equalsIgnoreCase(paid.name())) {
            return Status.paid;
        } else if (status.equalsIgnoreCase(delivered.name())) {
            return Status.delivered;
        } else if (status.equalsIgnoreCase(canceled.name())) {
            return Status.canceled;
        }

        throw new ErrorPageException();
    }

    public int getOrdinal() {
        return this.ordinal();
    }
}
