package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;

/**
 * Edit a person's information from the address book.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit a person's information "
            + "identified by the index number in the last shown person listing.\n"
            + "Parameters: i/INDEX NEW_NAME \n"
            + "Example: " + COMMAND_WORD 
            + "i/1 John Doe ";

    public static final String MESSAGE_DUPLICATE_PERSON = "You can't change a person into an already existing one in the address book";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";



    private Person newPerson;
    private Name NEW_NAME;
    
    public EditCommand(int targetVisibleIndex,String newName) throws IllegalValueException {
        super(targetVisibleIndex);
        this.NEW_NAME= new Name(newName);
    }
    

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson OldPerson = getTargetPerson();
            this.newPerson = (Person) OldPerson;
            this.newPerson.setName(NEW_NAME);
            addressBook.editPerson(OldPerson, newPerson);
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

