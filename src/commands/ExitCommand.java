package commands;

/**
 * Команда для завершения программы
 */
public class ExitCommand extends BaseCommand {
    private final CommandInvoker invoker;

    public ExitCommand(CommandInvoker invoker) {
        super(null, null, "exit", "завершить программу (без сохранения в файл)");
        this.invoker = invoker;
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "exit");

        System.out.println("Завершение программы...");
        invoker.exit();
    }
}