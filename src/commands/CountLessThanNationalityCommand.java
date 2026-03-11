package commands;

import collection.CollectionManager;
import io.UserInputReader;
import model.Country;
import utils.Validator;

/**
 * Команда для подсчета элементов с национальностью меньше заданной
 */
public class CountLessThanNationalityCommand extends BaseCommand {
    public CountLessThanNationalityCommand(CollectionManager collectionManager, UserInputReader inputReader) {
        super(collectionManager, inputReader, "count_less_than_nationality",
                "count_less_than_nationality nationality - вывести количество элементов, значение поля nationality которых меньше заданного");
    }

    @Override
    public void execute(String[] args) {
        validateArgs(args, 1, "count_less_than_nationality nationality");

        try {
            Country nationality = Validator.validateCountry(args[0], false);
            long count = collectionManager.countLessThanNationality(nationality);
            System.out.println("Количество элементов с национальностью меньше " + nationality + ": " + count);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}