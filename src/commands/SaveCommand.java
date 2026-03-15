package commands;

import collection.CollectionManager;
import io.FileManager;
import exceptions.FileAccessException;

/**
 * Команда для сохранения коллекции в файл
 */
public class SaveCommand extends BaseCommand {
    private final FileManager fileManager;

    /**
     * Конструктор команды save
     * @param collectionManager менеджер коллекции
     * @param fileManager менеджер файлов для сохранения
     */
    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        super(collectionManager, null, "save", "сохранить коллекцию в файл");
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "save");

        try {
            fileManager.saveCollection(collectionManager.getAll());
            System.out.println("Коллекция сохранена в файл: " + fileManager.getFileName());
        } catch (FileAccessException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }
}