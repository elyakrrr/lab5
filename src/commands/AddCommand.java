package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Person;

/**
 * Команда для добавления нового элемента
 */
public class AddCommand extends BaseCommand {
    public AddCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "add",
                "добавить новый элемент в коллекцию");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "add");
        Person person = inputReader.readPerson();
        addPerson(person);
    }

    /**
     * Выполняет команду с готовым элементом (для скриптов)
     */
    public void executeWithElement(String[] args, Person person) {
        validateArgs(args, 0, "add");
        addPerson(person);
    }

    /**
     * Общая логика добавления человека
     */
    private void addPerson(Person person) {
        if (collectionManager.add(person)) {
            System.out.println("Элемент успешно добавлен. ID: " + person.getId());
        } else {
            System.out.println("Ошибка при добавлении элемента");
        }
    }
}