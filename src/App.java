import collection.CollectionManager;
import commands.CommandInvoker;
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
    private static final String EMPTY_COLLECTION_MSG = "Будет создана пустая коллекция.";

    private CollectionManager collectionManager;
    private FileManager fileManager;
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
        try {
            fileManager = new FileManager(ENV_VAR_NAME);
            collectionManager = new CollectionManager();
            loadData();

            UserInputReader inputReader = new UserInputReader();
            invoker = new CommandInvoker(collectionManager, inputReader, fileManager);

            isInitialized = true;

        } catch (FileAccessException e) {
            handleFatalError(e.getMessage());
        } catch (Exception e) {
            handleFatalError("Неожиданная ошибка при инициализации: " + e.getMessage());
        }
    }

    /**
     * Загружает данные из файла в коллекцию
     */
    private void loadData() {
        if (fileManager == null) return;

        try {
            if (!fileManager.fileExists()) {
                printInfo("Файл не существует. " + EMPTY_COLLECTION_MSG);
                return;
            }

            if (!fileManager.canRead()) {
                printInfo("Нет прав на чтение файла. " + EMPTY_COLLECTION_MSG);
                return;
            }

            var loadedCollection = fileManager.loadCollection();
            loadedCollection.forEach(collectionManager::add);

        } catch (FileAccessException e) {
            printError("Ошибка доступа к файлу: " + e.getMessage());
            printInfo(EMPTY_COLLECTION_MSG);
        } catch (InvalidDataException e) {
            printWarning("Ошибка в данных файла: " + e.getMessage());
            printInfo("Будут загружены только корректные элементы.");
        }
    }

    /**
     * Запускает основной цикл приложения
     */
    public void run() {
        if (!isInitialized) {
            handleFatalError("Приложение не инициализировано");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            printWelcomeMessage();

            while (invoker.isRunning()) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (!input.isEmpty()) {
                    invoker.executeCommand(input);
                }
            }
        }
    }

    /**
     * Выводит приветственное сообщение
     */
    private void printWelcomeMessage() {
        System.out.println("*Управление коллекцией объектов Person*");
        System.out.println("Введите 'help' для списка команд");
        System.out.println("Введите 'exit' для выхода");
    }

    /**
     * Завершает приложение
     */
    public void shutdown() {
        printInfo("Завершение работы приложения...");
        printInfo("Приложение завершено.");
    }

    /**
     * Обработка фатальных ошибок с завершением программы
     */
    private void handleFatalError(String message) {
        System.err.println("ОШИБКА: " + message);
        System.err.println("Приложение не может быть запущено.");
        System.exit(1);
    }

    /**
     * Вывод информационных сообщений с единым форматированием
     */
    private void printInfo(String message) {
        System.out.println("\n   " + message);
    }

    /**
     * Вывод предупреждений с единым форматированием
     */
    private void printWarning(String message) {
        System.out.println("\n   ПРЕДУПРЕЖДЕНИЕ: " + message);
    }

    /**
     * Вывод ошибок с единым форматированием
     */
    private void printError(String message) {
        System.err.println("\n   ОШИБКА: " + message);
    }
}