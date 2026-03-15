package commands;

import collection.CollectionManager;
import model.Country;

import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Команда для вывода уникальных национальностей
 */
public class PrintUniqueNationalityCommand extends BaseCommand {
    public PrintUniqueNationalityCommand(CollectionManager collectionManager) {
        super(collectionManager, null, "print_unique_nationality",
                "вывести уникальные значения поля nationality");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 0, "print_unique_nationality");

        Set<Country> nationalities = collectionManager.getUniqueNationalities();

        if (nationalities.isEmpty()) {
            System.out.println("Нет элементов с национальностью");
            return;
        }

        System.out.println("*Уникальные национальности*");

        List<Country> sortedList = new ArrayList<>(nationalities);

        Collections.sort(sortedList);

        for (Country country : sortedList) {
            System.out.println(country);
        }
    }
}