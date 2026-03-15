package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Person;

/**
 * Команда для добавления элемента, если он меньше минимального элемента в коллекции.
 * Сравнение элементов происходит на основе их естественного порядка (compareTo).
 */
public class AddIfMinCommand extends BaseCommand {

    /**
     * Создает команду add_if_min
     *
     * @param collectionManager менеджер коллекции для управления элементами
     * @param inputReader       ридер для чтения данных пользователя
     */
    public AddIfMinCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "add_if_min",
                "add_if_min {element} - добавить элемент, если его значение меньше наименьшего");
    }

    /**
     * Выполняет команду: читает человека из ввода и добавляет, если он минимальный.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "add_if_min");
        Person person = inputReader.readPerson();
        processAddIfMin(person);
    }

    /**
     * Выполняет команду с готовым объектом Person (для использования из скриптов).
     *
     * @param args   аргументы команды (не используются)
     * @param person готовый объект Person для добавления
     */
    public void executeWithElement(String[] args, Person person) {
        validateArgs(args, 0, "add_if_min");
        processAddIfMin(person);
    }

    /**
     * Общая логика добавления элемента с проверкой на минимальность.
     *
     * @param person объект Person для добавления
     */
    private void processAddIfMin(Person person) {
        if (collectionManager.addIfMin(person)) {
            System.out.println("Элемент добавлен (он меньше минимального). ID: " + person.getId());
        } else {
            System.out.println("Элемент не добавлен (он не меньше минимального)");
        }
    }
}