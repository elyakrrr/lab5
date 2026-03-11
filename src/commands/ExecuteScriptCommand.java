package commands;

/**
 * Команда для выполнения скрипта
 */
public class ExecuteScriptCommand extends BaseCommand {
    private final CommandInvoker invoker;

    public ExecuteScriptCommand(CommandInvoker invoker) {
        super(null, null, "execute_script",
                "execute_script file_name - считать и исполнить скрипт из указанного файла");
        this.invoker = invoker;
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 1, "execute_script file_name");

        String fileName = args[0];
        invoker.executeScript(fileName);
    }
}