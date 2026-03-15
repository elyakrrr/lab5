package commands;

import collection.CollectionManager;
import model.Person;
import java.util.List;

/**
 * Команда для вывода всех элементов коллекции
 */
public class ShowCommand extends BaseCommand {
    public ShowCommand(CollectionManager collectionManager) {
        super(collectionManager, null, "show",
                "вывести все элементы коллекции в строковом представлении");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "show");

        if (collectionManager.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        System.out.println("*Элементы коллекции*");
        List<Person> sortedList = collectionManager.getSorted();
        for (Person person : sortedList) {
            System.out.println(person);
        }
        System.out.println("Всего элементов: " + collectionManager.size());
    }
}