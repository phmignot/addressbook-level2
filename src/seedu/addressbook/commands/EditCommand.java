package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;

/**
 * Edit a person's information from the address book.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit a person's name information "
            + "identified by the index number in the last shown person listing.\n"
            + "Parameters: NEW_NAME i/INDEX \n"
            + "Example: " + COMMAND_WORD 
            + "John Doe i/1";

    public static final String MESSAGE_DUPLICATE_PERSON = "You can't change a person into an already existing one in the address book";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    
    private Person newProfile;
    private Person oldProfile;
    private Name NEW_NAME;
    
    public EditCommand(int targetVisibleIndex,String newName) throws IllegalValueException {
        super(targetVisibleIndex);
        this.NEW_NAME= new Name(newName);
    }
    

    @Override
    public CommandResult execute() {
        try {
            oldProfile = (Person) getTargetPerson();
            newProfile = new Person(oldProfile);
            newProfile.setName(NEW_NAME);
            addressBook.editPerson(oldProfile, newProfile);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, newProfile));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }
    }
}

