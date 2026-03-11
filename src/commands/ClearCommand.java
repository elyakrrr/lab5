package commands;

import collection.CollectionManager;

/**
 * Команда для очистки коллекции
 */
public class ClearCommand extends BaseCommand {
    public ClearCommand(CollectionManager collectionManager) {
        super(collectionManager, null, "clear", "очистить коллекцию");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "clear");

        int size = collectionManager.size();
        collectionManager.clear();
        System.out.println("Коллекция очищена. Удалено элементов: " + size);
    }
}