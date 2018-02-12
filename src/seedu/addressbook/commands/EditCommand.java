package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
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

    public static final String MESSAGE_DUPLICATE_PERSON = "You can't change a person into an already existing one in the address book";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";



    private final Person NewPerson;
    private final ReadOnlyPerson OldPerson;

    public EditCommand(int targetVisibleIndex,String newName) throws IllegalValueException {
        super(targetVisibleIndex);
        this.OldPerson = getTargetPerson();
        this.NewPerson = (Person) OldPerson;
        this.NewPerson.setName(new Name(newName));
    }
    

    @Override
    public CommandResult execute() {
        try {
            addressBook.editPerson(OldPerson,NewPerson);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, OldPerson));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }
    }
}

