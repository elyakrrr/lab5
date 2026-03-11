package commands;

import collection.CollectionManager;
import io.UserInputReader;

/**
 * Абстрактный базовый класс для всех команд
 */
public abstract class BaseCommand implements Command {
    protected final CollectionManager collectionManager;
    protected final UserInputReader inputReader;
    protected final String name;
    protected final String description;

    public BaseCommand(CollectionManager collectionManager, UserInputReader inputReader,
                       String name, String description) {
        this.collectionManager = collectionManager;
        this.inputReader = inputReader;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Проверяет количество аргументов
     */
    protected void validateArgs(String[] args, int expectedCount, String usage) {
        if (args.length != expectedCount) {
            System.out.println("Ошибка: неверное количество аргументов");
            System.out.println("Использование: " + usage);
            throw new IllegalArgumentException();
        }
    }

    /**
     * Парсит ID из аргументов
     */
    protected Integer parseId(String idStr) {
        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть целым числом");
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s", name, description);
    }
}
