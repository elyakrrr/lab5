package commands;

import collection.CollectionManager;
import io.FileManager;
import exceptions.FileAccessException;

/**
 * Команда для сохранения коллекции в файл
 */
public class SaveCommand extends BaseCommand {
    private FileManager fileManager;

    public SaveCommand(CollectionManager collectionManager) {
        super(collectionManager, null, "save", "сохранить коллекцию в файл");
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "save");

        if (fileManager == null) {
            System.out.println("Ошибка: FileManager не инициализирован");
            return;
        }

        try {
            fileManager.saveCollection(collectionManager.getAll());
            System.out.println("Коллекция сохранена в файл: " + fileManager.getFileName());
        } catch (FileAccessException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }
}