package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Person;

/**
 * Команда для обновления элемента
 */
public class UpdateCommand extends BaseCommand {

    /**
     * Конструктор команды update
     * @param collectionManager менеджер коллекции
     * @param inputReader ридер для ввода данных
     */
    public UpdateCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "update",
                "update id {element} - обновить значение элемента по id");
    }

    /**
     * Выполняет команду: читает новый элемент из ввода и обновляет существующий по ID.
     * @param args аргументы команды (первый аргумент - id)
     */
    @Override
    public void execute(String[] args) {
        validateArgs(args, 1, "update id");

        Integer id;
        try {
            id = parseId(args[0]);
        } catch (IllegalArgumentException e) {
            return;
        }

        processUpdate(id, null);
    }

    /**
     * Выполняет команду с готовым объектом Person (для использования из скриптов).
     * @param args аргументы команды (первый аргумент - id)
     * @param person готовый объект Person для обновления
     */
    public void executeWithElement(String[] args, Person person) {
        validateArgs(args, 1, "update id");

        Integer id;
        try {
            id = parseId(args[0]);
        } catch (IllegalArgumentException e) {
            return;
        }

        processUpdate(id, person);
    }

    /**
     * Общая логика обновления элемента.
     * @param id ID элемента для обновления
     * @param person объект Person для обновления (null если нужно прочитать из ввода)
     */
    private void processUpdate(Integer id, Person person) {
        Person existing = collectionManager.getById(id);

        if (existing == null) {
            System.out.println("Элемент с ID " + id + " не найден");
            return;
        }

        if (person == null) {
            System.out.println("Обновление элемента с ID " + id);
            System.out.println("Текущие данные: " + existing);
            person = inputReader.readPerson();
        }

        if (collectionManager.update(id, person)) {
            System.out.println("Элемент с ID " + id + " успешно обновлен");
        } else {
            System.out.println("Ошибка при обновлении элемента");
        }
    }
}