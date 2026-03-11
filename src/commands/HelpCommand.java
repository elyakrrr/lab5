package commands;

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
        System.out.println("\n=== Доступные команды ===");
        invoker.getCommands().values().stream()
                .sorted((c1, c2) -> c1.getName().compareTo(c2.getName()))
                .forEach(System.out::println);
        System.out.println();
    }
}