package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Person;

/**
 * Команда для удаления элементов, меньших чем заданный.
 * Сравнение элементов происходит на основе их естественного порядка (compareTo).
 */
public class RemoveLowerCommand extends BaseCommand {

    /**
     * Создает команду remove_lower
     *
     * @param collectionManager менеджер коллекции для управления элементами
     * @param inputReader       ридер для чтения данных пользователя
     */
    public RemoveLowerCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "remove_lower",
                "remove_lower {element} - удалить элементы, меньшие чем заданный");
    }

    /**
     * Выполняет команду: читает человека из ввода и удаляет все меньшие элементы.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "remove_lower");
        Person person = inputReader.readPerson();
        processRemoveLower(person);
    }

    /**
     * Выполняет команду с готовым объектом Person (для использования из скриптов).
     *
     * @param args   аргументы команды (не используются)
     * @param person готовый объект Person для сравнения
     */
    public void executeWithElement(String[] args, Person person) {
        validateArgs(args, 0, "remove_lower");
        processRemoveLower(person);
    }

    /**
     * Общая логика удаления элементов, меньших чем заданный.
     *
     * @param person объект Person для сравнения
     */
    private void processRemoveLower(Person person) {
        int removed = collectionManager.removeLower(person);
        System.out.println("Удалено элементов: " + removed);
    }
}