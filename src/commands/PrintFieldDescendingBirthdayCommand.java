package commands;

import collection.CollectionManager;

import java.util.Date;
import java.util.List;

/**
 * Команда для вывода дней рождения в порядке убывания
 */
public class PrintFieldDescendingBirthdayCommand extends BaseCommand {
    public PrintFieldDescendingBirthdayCommand(CollectionManager collectionManager) {
        super(collectionManager, null, "print_field_descending_birthday",
                "вывести значения поля birthday всех элементов в порядке убывания");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "print_field_descending_birthday");

        List<Date> birthdays = collectionManager.getBirthdaysDescending();

        if (birthdays.isEmpty()) {
            System.out.println("Нет элементов с датой рождения");
            return;
        }

        System.out.println("*Дни рождения (в порядке убывания)*");
        for (Date birthday : birthdays) {
            System.out.println(birthday);
        }
    }
}