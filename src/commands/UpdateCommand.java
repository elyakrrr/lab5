package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Person;

import java.util.Optional;

/**
 * Команда для обновления элемента
 */
public class UpdateCommand extends BaseCommand {
    public UpdateCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "update",
                "update id {element} - обновить значение элемента по id");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 1, "update id");

        try {
            Integer id = parseId(args[0]);

            Optional<Person> existing = collectionManager.getById(id);
            if (existing.isEmpty()) {
                System.out.println("Элемент с ID " + id + " не найден");
                return;
            }

            System.out.println("Обновление элемента с ID " + id);
            System.out.println("Текущие данные: " + existing.get());

            Person newPerson = inputReader.readPerson();
            if (collectionManager.update(id, newPerson)) {
                System.out.println("Элемент с ID " + id + " успешно обновлен");
            } else {
                System.out.println("Ошибка при обновлении элемента");
            }

        } catch (IllegalArgumentException e) {
            // Ошибка уже обработана в parseId
        }
    }

    /**
     * Выполняет команду с готовым элементом (для скриптов)
     */
    public void executeWithElement(String[] args, Person person) {
        validateArgs(args, 1, "update id");

        try {
            Integer id = parseId(args[0]);

            if (collectionManager.update(id, person)) {
                System.out.println(" [OK] Элемент " + id + " обновлен");
            } else {
                System.out.println(" [ERROR] Элемент " + id + " не найден");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(" [ERROR] Неверный ID");
        }
    }
}