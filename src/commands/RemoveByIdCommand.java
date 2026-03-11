package commands;

import collection.CollectionManager;
import io.UserInputReader;

/**
 * Команда для удаления элемента по ID
 */
public class RemoveByIdCommand extends BaseCommand {
    public RemoveByIdCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "remove_by_id",
                "remove_by_id id - удалить элемент из коллекции по его id");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 1, "remove_by_id id");

        try {
            Integer id = parseId(args[0]);

            if (collectionManager.removeById(id)) {
                System.out.println("Элемент с ID " + id + " успешно удален");
            } else {
                System.out.println("Элемент с ID " + id + " не найден");
            }

        } catch (IllegalArgumentException e) {
            // Ошибка уже обработана в parseId
        }
    }
}