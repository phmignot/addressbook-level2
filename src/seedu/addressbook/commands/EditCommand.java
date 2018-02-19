package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.UniquePersonList;

/**
 * Edit a person's information from the address book.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit a person's name information "
            + "identified by the index number in the last shown person listing. (just the name)\n"
            + "Parameters: NEW_NAME i/INDEX \n"
            + "Example: " + COMMAND_WORD 
            + " John Doe i/1";

    public static final String MESSAGE_DUPLICATE_PERSON = "You can't change a person into an already existing one in the address book";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Complete, new profile : %1$s";
    
    
    //The oldProfile is the actual profile of the person
    private Person oldProfile;
    //The newProfile is the future profile of the person after editing
    private Person newProfile;
    private Name newName;
    
    public EditCommand(String newName, int targetVisibleIndex) throws IllegalValueException {
        super(targetVisibleIndex);
        this.newName= new Name(newName);
    }
    

    @Override
    public CommandResult execute() {
        try {
            InitiateProfiles();
            addressBook.editPerson(oldProfile, newProfile);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, newProfile));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }
    }

    /**
     * Initiates the two profiles, oldProfile and newProfile of the person.
     *
     * @throws IndexOutOfBoundsException if incorrect index given to get the 
     * targeted person
     */
    
    private void InitiateProfiles() throws IndexOutOfBoundsException {
        generateOldProfile();
        generateNewProfile();
    }

    /**
     * Creates the OldProfile
     */
    private void generateOldProfile() {
        oldProfile = (Person) getTargetPerson();
    }
    
    /**
     * Creates the newProfile from OldProfile.
     */
    private void generateNewProfile() {
        newProfile = new Person(oldProfile);
        newProfile.setName(newName);
    }
}

