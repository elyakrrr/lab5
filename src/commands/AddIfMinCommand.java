package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Person;

/**
 * Команда для добавления элемента, если он меньше минимального
 */
public class AddIfMinCommand extends BaseCommand {
    public AddIfMinCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "add_if_min",
                "add_if_min {element} - добавить элемент, если его значение меньше наименьшего");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "add_if_min");

        Person person = inputReader.readPerson();
        if (collectionManager.addIfMin(person)) {
            System.out.println("Элемент добавлен (он меньше минимального). ID: " + person.getId());
        } else {
            System.out.println("Элемент не добавлен (он не меньше минимального)");
        }
    }

    /**
     * Выполняет команду с готовым элементом (для скриптов)
     */
    public void executeWithElement(String[] args, Person person) {
        validateArgs(args, 0, "add_if_min");

        if (collectionManager.addIfMin(person)) {
            System.out.println(" [OK] Элемент добавлен как минимальный");
        } else {
            System.out.println(" [SKIP] Элемент не является минимальным");
        }
    }
}