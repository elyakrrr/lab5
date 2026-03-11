package commands;

/**
 * Интерфейс для всех команд
 */
public interface Command {
    /**
     * Выполняет команду
     * @param args аргументы команды
     */
    void execute(String[] args);

    /**
     * @return описание команды
     */
    String getDescription();

    /**
     * @return имя команды
     */
    String getName();
}