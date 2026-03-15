package commands;

import collection.CollectionManager;
import io.FileManager;
import io.UserInputReader;
import io.ScriptReader;
import model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Класс для вызова и выполнения команд (Invoker)
 */
public class CommandInvoker {
    private final Map<String, Command> commands;
    private final CollectionManager collectionManager;
    private final UserInputReader inputReader;
    private final FileManager fileManager;
    private final Set<String> executingScripts;
    private boolean isRunning;

    /**
     * Конструктор с FileManager
     */
    public CommandInvoker(CollectionManager collectionManager, UserInputReader inputReader, FileManager fileManager) {
        this.commands = new HashMap<>();
        this.collectionManager = collectionManager;
        this.inputReader = inputReader;
        this.fileManager = fileManager;
        this.executingScripts = new HashSet<>();
        this.isRunning = true;
        initializeCommands();
    }

    /**
     * Инициализирует все команды
     */
    private void initializeCommands() {
        registerCommand(new HelpCommand(this));
        registerCommand(new InfoCommand(collectionManager));
        registerCommand(new ShowCommand(collectionManager));
        registerCommand(new AddCommand(collectionManager, inputReader));
        registerCommand(new UpdateCommand(collectionManager, inputReader));
        registerCommand(new RemoveByIdCommand(collectionManager, inputReader));
        registerCommand(new ClearCommand(collectionManager));
        registerCommand(new SaveCommand(collectionManager, fileManager));
        registerCommand(new ExecuteScriptCommand(this));
        registerCommand(new ExitCommand(this));
        registerCommand(new AddIfMinCommand(collectionManager, inputReader));
        registerCommand(new RemoveGreaterCommand(collectionManager, inputReader));
        registerCommand(new RemoveLowerCommand(collectionManager, inputReader));
        registerCommand(new CountLessThanNationalityCommand(collectionManager, inputReader));
        registerCommand(new PrintUniqueNationalityCommand(collectionManager));
        registerCommand(new PrintFieldDescendingBirthdayCommand(collectionManager));
    }

    /**
     * Регистрирует команду
     */
    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    /**
     * Выполняет команду
     */
    public void executeCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("Неизвестная команда. Введите 'help' для справки.");
            return;
        }

        try {
            command.execute(args);
        } catch (IllegalArgumentException e) {
            // Ошибка уже обработана в команде
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении команды: " + e.getMessage());
        }
    }

    /**
     * Выполняет скрипт из файла
     */
    public void executeScript(String fileName) {
        File file = new File(fileName);

        if (executingScripts.contains(file.getAbsolutePath())) {
            System.out.println("Ошибка: обнаружена рекурсия в скрипте " + fileName);
            return;
        }

        if (!file.exists()) {
            System.out.println("Ошибка: файл " + fileName + " не существует");
            return;
        }

        if (!file.canRead()) {
            System.out.println("Ошибка: нет прав на чтение файла " + fileName);
            return;
        }

        executingScripts.add(file.getAbsolutePath());

        try (ScriptReader scriptReader = new ScriptReader(file)) {
            System.out.println("Выполнение скрипта: " + fileName);

            String line;
            int lineNumber = 0;

            while ((line = scriptReader.readLine()) != null) {
                lineNumber++;

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                System.out.print("[" + fileName + ":" + lineNumber + "] " + line);

                String[] parts = line.trim().split("\\s+");
                String commandName = parts[0];
                String[] cmdArgs = Arrays.copyOfRange(parts, 1, parts.length);

                if (isCommandWithElement(commandName)) {
                    try {
                        Person person = scriptReader.readPersonFromScript();
                        handleCommandWithElement(commandName, cmdArgs, person);
                    } catch (IOException e) {
                        System.out.println(" Ошибка чтения элемента из скрипта: " + e.getMessage());
                        break;
                    }
                } else {
                    executeCommand(line);
                }
            }

            System.out.println("Скрипт " + fileName + " выполнен");

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден - " + fileName);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении скрипта: " + e.getMessage());
        } finally {
            executingScripts.remove(file.getAbsolutePath());
        }
    }

    /**
     * Проверяет, требует ли команда ввода элемента
     */
    private boolean isCommandWithElement(String commandName) {
        return commandName.equals("add") ||
                commandName.equals("update") ||
                commandName.equals("add_if_min") ||
                commandName.equals("remove_greater") ||
                commandName.equals("remove_lower");
    }

    /**
     * Обрабатывает команду с элементом из скрипта
     */
    private void handleCommandWithElement(String commandName, String[] args, Person person) {
        Command command = commands.get(commandName);

        if (command instanceof AddCommand) {
            ((AddCommand) command).executeWithElement(args, person);
        } else if (command instanceof UpdateCommand) {
            ((UpdateCommand) command).executeWithElement(args, person);
        } else if (command instanceof AddIfMinCommand) {
            ((AddIfMinCommand) command).executeWithElement(args, person);
        } else if (command instanceof RemoveGreaterCommand) {
            ((RemoveGreaterCommand) command).executeWithElement(args, person);
        } else if (command instanceof RemoveLowerCommand) {
            ((RemoveLowerCommand) command).executeWithElement(args, person);
        } else {
            System.out.println(" [ERROR] Команда " + commandName + " не поддерживает элемент из скрипта");
        }
    }

    /**
     * @return карта всех команд
     */
    public Map<String, Command> getCommands() {
        return Collections.unmodifiableMap(commands);
    }

    /**
     * Завершает работу программы
     */
    public void exit() {
        this.isRunning = false;
    }

    /**
     * @return true если программа должна продолжать работу
     */
    public boolean isRunning() {
        return isRunning;
    }
}