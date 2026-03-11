package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Person;

/**
 * Команда для удаления элементов больше заданного
 */
public class RemoveGreaterCommand extends BaseCommand {
    public RemoveGreaterCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "remove_greater",
                "remove_greater {element} - удалить элементы, превышающие заданный");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "remove_greater");

        Person person = inputReader.readPerson();
        int removed = collectionManager.removeGreater(person);
        System.out.println("Удалено элементов: " + removed);
    }

    /**
     * Выполняет команду с готовым элементом (для скриптов)
     */
    public void executeWithElement(String[] args, Person person) {
        validateArgs(args, 0, "remove_greater");

        int removed = collectionManager.removeGreater(person);
        System.out.println(" [OK] Удалено элементов: " + removed);
    }
}