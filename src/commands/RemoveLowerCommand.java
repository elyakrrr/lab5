package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Person;

/**
 * Команда для удаления элементов меньше заданного
 */
public class RemoveLowerCommand extends BaseCommand {
    public RemoveLowerCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "remove_lower",
                "remove_lower {element} - удалить элементы, меньшие чем заданный");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "remove_lower");

        Person person = inputReader.readPerson();
        int removed = collectionManager.removeLower(person);
        System.out.println("Удалено элементов: " + removed);
    }

    /**
     * Выполняет команду с готовым элементом (для скриптов)
     */
    public void executeWithElement(String[] args, Person person) {
        validateArgs(args, 0, "remove_lower");

        int removed = collectionManager.removeLower(person);
        System.out.println(" [OK] Удалено элементов: " + removed);
    }
}