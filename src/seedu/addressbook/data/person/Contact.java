package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

public class Contact {

    public final String value;
    protected boolean isPrivate;

    public Contact(String information, boolean isPrivate) throws IllegalValueException {
        String trimmedInformation = information.trim();
        this.value = trimmedInformation;
        this.isPrivate = isPrivate;
        //throw new UnsupportedOperationException("This method is to be implemented by child classes");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Contact // instanceof handles nulls
                && this.value.equals(((Contact) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
