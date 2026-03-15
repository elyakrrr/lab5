package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Person;

/**
 * Команда для удаления элементов, превышающих заданный.
 * Сравнение элементов происходит на основе их естественного порядка (compareTo).
 */
public class RemoveGreaterCommand extends BaseCommand {

    /**
     * Создает команду remove_greater
     *
     * @param collectionManager менеджер коллекции для управления элементами
     * @param inputReader       ридер для чтения данных пользователя
     */
    public RemoveGreaterCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "remove_greater",
                "remove_greater {element} - удалить элементы, превышающие заданный");
    }

    /**
     * Выполняет команду: читает человека из ввода и удаляет все большие элементы.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "remove_greater");
        Person person = inputReader.readPerson();
        processRemoveGreater(person);
    }

    /**
     * Выполняет команду с готовым объектом Person (для использования из скриптов).
     *
     * @param args   аргументы команды (не используются)
     * @param person готовый объект Person для сравнения
     */
    public void executeWithElement(String[] args, Person person) {
        validateArgs(args, 0, "remove_greater");
        processRemoveGreater(person);
    }

    /**
     * Общая логика удаления элементов, больших чем заданный.
     *
     * @param person объект Person для сравнения
     */
    private void processRemoveGreater(Person person) {
        int removed = collectionManager.removeGreater(person);
        System.out.println("Удалено элементов: " + removed);
    }
}