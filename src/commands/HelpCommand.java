package commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для вывода справки
 */
public class HelpCommand extends BaseCommand {
    private final CommandInvoker invoker;

    public HelpCommand(CommandInvoker invoker) {
        super(null, null, "help", "вывести справку по доступным командам");
        this.invoker = invoker;
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "help");

        System.out.println("*Доступные команды*");

        List<Command> commandList = new ArrayList<>(invoker.getCommands().values());

        for (Command command : commandList) {
            System.out.println(command);
        }
    }
}