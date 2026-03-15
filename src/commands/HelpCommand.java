package commands;

import java.util.Comparator;

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
        invoker.getCommands().values().stream()
                .sorted(Comparator.comparing(Command::getName))
                .forEach(System.out::println);
    }
}