import collection.CollectionManager;
import commands.CommandInvoker;
import commands.SaveCommand;
import io.FileManager;
import io.UserInputReader;
import exceptions.FileAccessException;
import exceptions.InvalidDataException;

import java.util.Scanner;

/**
 * Главный класс приложения
 * Отвечает за инициализацию и основной цикл программы
 */
public class App {
    private static final String ENV_VAR_NAME = "PERSON_DATA";

    private CollectionManager collectionManager;
    private FileManager fileManager;
    private UserInputReader inputReader;
    private CommandInvoker invoker;
    private boolean isInitialized;

    /**
     * Конструктор приложения
     */
    public App() {
        this.isInitialized = false;
    }

    /**
     * Инициализирует все компоненты приложения
     */
    public void initialize() {
        System.out.println("Инициализация приложения...");

        try {
            // 1. Инициализация FileManager
            System.out.print("Загрузка файлового менеджера... ");
            fileManager = new FileManager(ENV_VAR_NAME);
            System.out.println("OK");
            System.out.println("   Файл данных: " + fileManager.getFileName());

            // 2. Инициализация CollectionManager
            System.out.print("Загрузка менеджера коллекции... ");
            collectionManager = new CollectionManager();
            System.out.println("OK");

            // 3. Загрузка данных из файла
            System.out.print("Загрузка данных из файла... ");
            loadData();
            System.out.println("OK");
            System.out.println("   Загружено элементов: " + collectionManager.size());

            // 4. Инициализация UserInputReader
            System.out.print("Инициализация ввода... ");
            inputReader = new UserInputReader();
            System.out.println("OK");

            // 5. Инициализация CommandInvoker
            System.out.print("Инициализация команд... ");
            invoker = new CommandInvoker(collectionManager, inputReader);

            // Передаем FileManager в SaveCommand
            SaveCommand saveCommand = (SaveCommand) invoker.getCommands().get("save");
            if (saveCommand != null) {
                saveCommand.setFileManager(fileManager);
            }

            System.out.println("OK");

            isInitialized = true;
            System.out.println("Приложение успешно инициализировано!\n");

        } catch (FileAccessException e) {
            System.err.println("ОШИБКА: " + e.getMessage());
            System.err.println("Приложение не может быть запущено.");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка при инициализации: " + e.getMessage());
            System.err.println("Приложение будет закрыто.");
            System.exit(1);
        }
    }

    /**
     * Загружает данные из файла в коллекцию
     */
    private void loadData() {
        if (fileManager == null) return;

        try {
            // Проверяем существование файла
            if (!fileManager.fileExists()) {
                System.out.println("\n   Файл не существует. Будет создана пустая коллекция.");
                return;
            }

            // Проверяем права на чтение
            if (!fileManager.canRead()) {
                System.out.println("\n   Предупреждение: нет прав на чтение файла. Будет создана пустая коллекция.");
                return;
            }

            // Загружаем коллекцию
            var loadedCollection = fileManager.loadCollection();

            // Добавляем загруженные элементы в менеджер коллекции
            for (var person : loadedCollection) {
                collectionManager.add(person);
            }

        } catch (FileAccessException e) {
            System.out.println("\n   Предупреждение: " + e.getMessage());
            System.out.println("   Будет создана пустая коллекция.");
        } catch (InvalidDataException e) {
            System.out.println("\n   Предупреждение: ошибка в данных файла: " + e.getMessage());
            System.out.println("   Будут загружены только корректные элементы.");
        }
    }

    /**
     * Запускает основной цикл приложения
     */
    public void run() {
        if (!isInitialized) {
            System.err.println("Ошибка: приложение не инициализировано");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // Выводим приветствие
        printWelcomeMessage();

        // Основной цикл
        while (invoker.isRunning()) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                invoker.executeCommand(input);
            }
        }

        scanner.close();
    }

    /**
     * Выводит приветственное сообщение
     */
    private void printWelcomeMessage() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║     Управление коллекцией объектов Person  ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("Введите 'help' для списка команд");
        System.out.println("Введите 'exit' для выхода\n");
    }

    /**
     * Завершает приложение
     */
    public void shutdown() {
        System.out.println("\nЗавершение работы приложения...");

        // Здесь можно добавить дополнительные действия при завершении
        // Например, автосохранение, если потребуется

        System.out.println("Приложение завершено.");
    }
}