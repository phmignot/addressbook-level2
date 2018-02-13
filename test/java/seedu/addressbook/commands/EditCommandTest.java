package seedu.addressbook.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.UniqueTagList;
import seedu.addressbook.ui.TextUi;
import seedu.addressbook.util.TestUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EditCommandTest {

    private AddressBook emptyAddressBook;
    private AddressBook addressBook;

    private List<ReadOnlyPerson> emptyDisplayList;
    private List<ReadOnlyPerson> listWithEveryone;
    private List<ReadOnlyPerson> listWithSurnameDoe;

    @Before
    public void setUp() throws Exception {
        Person johnDoe = new Person(new Name("John Doe"), new Phone("61234567", false),
                new Email("john@doe.com", false), new Address("395C Ben Road", false),
                new UniqueTagList());
        Person janeDoe = new Person(new Name("Jane Doe"), new Phone("91234567", false),
                new Email("jane@doe.com", false), new Address("33G Ohm Road", false),
                new UniqueTagList());
        Person samDoe = new Person(new Name("Sam Doe"), new Phone("63345566", false),
                new Email("sam@doe.com", false), new Address("55G Abc Road", false),
                new UniqueTagList());
        Person davidGrant = new Person(new Name("David Grant"), new Phone("61121122", false),
                new Email("david@grant.com", false), new Address("44H Define Road", false),
                new UniqueTagList());
        Person johnDoeTwo = new Person(new Name("John DoeTwo"), new Phone("061234567", false),
                new Email("john@doetwo.com", false), new Address("395C Ben Road two", false),
                new UniqueTagList());


        emptyAddressBook = TestUtil.createAddressBook();
        addressBook = TestUtil.createAddressBook(johnDoe, janeDoe, davidGrant, samDoe, johnDoeTwo);

        emptyDisplayList = TestUtil.createList();

        listWithEveryone = TestUtil.createList(johnDoe, janeDoe, davidGrant, samDoe, johnDoeTwo);
        listWithSurnameDoe = TestUtil.createList(johnDoe, janeDoe, samDoe);
    }
    
    
    @Test
    public void execute_noPersonDisplayed_returnsInvalidIndexMessage() throws IllegalValueException{
        assertEditFailsDueToInvalidIndex(Name.EXAMPLE,1, addressBook, emptyDisplayList);
    }


    @Test
    public void execute_invalidIndex_returnsInvalidIndexMessage() throws IllegalValueException{
        assertEditFailsDueToInvalidIndex(Name.EXAMPLE,0, addressBook, listWithEveryone);
        assertEditFailsDueToInvalidIndex(Name.EXAMPLE,-1, addressBook, listWithEveryone);
        assertEditFailsDueToInvalidIndex(Name.EXAMPLE,listWithEveryone.size() + 1, addressBook,
                listWithEveryone);
    }

    @Test
    public void execute_validName_validIndex_personIsEdited() throws IllegalValueException {
        assertEditSuccessful("hello",1, addressBook, listWithSurnameDoe);
        assertEditSuccessful("howw",listWithSurnameDoe.size(), addressBook, listWithSurnameDoe);

        int middleIndex = (listWithSurnameDoe.size() / 2) + 1;
        assertEditSuccessful("fantastic",middleIndex, addressBook, listWithSurnameDoe);
    }

    /**
     * Creates a new edit command.
     *
     * @param targetVisibleIndex of the person that we want to delete
     */
    private EditCommand createEditCommand(String editName, int targetVisibleIndex, AddressBook addressBook,
                                              List<ReadOnlyPerson> displayList) throws IllegalValueException {

        EditCommand command = new EditCommand(editName,targetVisibleIndex);
        command.setData(addressBook, displayList);

        return command;
    }

    /**
     * Executes the command, and checks that the execution was what we had expected.
     */
    private void assertCommandBehaviour(EditCommand editCommand, String expectedMessage,
                                        AddressBook expectedAddressBook, AddressBook actualAddressBook) {

        CommandResult result = editCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook.getAllPersons(), actualAddressBook.getAllPersons());
    }


    
    /**
     * Asserts that the index is not valid for the given display list.
     */
    private void assertEditFailsDueToInvalidIndex(String editName, int invalidVisibleIndex, AddressBook addressBook,
                                                  List<ReadOnlyPerson> displayList) throws IllegalValueException{

        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        EditCommand command = createEditCommand(editName,invalidVisibleIndex, addressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, addressBook, addressBook);
    }



    /**
     * Asserts that the person at the specified index can be successfully edited.
     *
     *
     * @throws UniquePersonList.DuplicatePersonException if the new profile of the selected person is matching with 
     * another person in the address book.
     */
    private void assertEditSuccessful(String editName, int targetVisibleIndex, AddressBook addressBook,
                                      List<ReadOnlyPerson> displayList) throws IllegalValueException {

        Person targetPerson = (Person) displayList.get(targetVisibleIndex - TextUi.DISPLAYED_INDEX_OFFSET);
        Person newProfile = new Person(targetPerson);
        newProfile.setName(new Name(editName)); 
        AddressBook expectedAddressBook = TestUtil.clone(addressBook);
        expectedAddressBook.editPerson(targetPerson, newProfile);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, newProfile);

        AddressBook actualAddressBook = TestUtil.clone(addressBook);

        EditCommand command = createEditCommand(editName, targetVisibleIndex, actualAddressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, expectedAddressBook, actualAddressBook);
    }
}
