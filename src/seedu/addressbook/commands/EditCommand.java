package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Edit a person's information from the address book.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit a person's information "
            + "identified by the index number in the last shown person listing.\n"
            + "Parameters: [i]i/INDEX NEW_NAME \n"
            + "Example: " + COMMAND_WORD 
            + "i/1 John Doe ";

    public EditCommand(int targetVisibleIndex,String name) throws IllegalValueException {
        super(targetVisibleIndex);
    }
}

