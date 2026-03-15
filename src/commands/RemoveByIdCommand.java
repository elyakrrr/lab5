package commands;

import collection.CollectionManager;
import io.UserInputReader;

/**
 * Команда для удаления элемента по ID
 */
public class RemoveByIdCommand extends BaseCommand {

    /**
     * Конструктор команды remove_by_id
     *
     * @param collectionManager менеджер коллекции для управления элементами
     * @param inputReader       ридер для чтения данных пользователя
     */
    public RemoveByIdCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "remove_by_id",
                "remove_by_id id - удалить элемент из коллекции по его id");
    }

    /**
     * Выполняет команду удаления элемента по ID.
     * Парсит переданный ID, при ошибке парсинга завершает выполнение.
     * При успешном удалении выводит подтверждение, иначе сообщение об отсутствии элемента.
     *
     * @param args аргументы команды (первый аргумент - id)
     */
    @Override
    public void execute(String[] args) {
        validateArgs(args, 1, "remove_by_id id");

        Integer id;
        try {
            id = parseId(args[0]);
        } catch (IllegalArgumentException e) {
            return;
        }

        if (collectionManager.removeById(id)) {
            System.out.println("Элемент с ID " + id + " успешно удален");
        } else {
            System.out.println("Элемент с ID " + id + " не найден");
        }
    }
}