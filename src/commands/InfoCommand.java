package commands;

import collection.CollectionManager;

/**
 * Команда для вывода информации о коллекции
 */
public class InfoCommand extends BaseCommand {
    public InfoCommand(CollectionManager collectionManager) {
        super(collectionManager, null, "info",
                "вывести информацию о коллекции (тип, дата инициализации, количество элементов)");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "info");
        System.out.println(collectionManager.getInfo());
    }
}