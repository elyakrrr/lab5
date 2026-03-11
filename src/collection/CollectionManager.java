package collection;

import model.Person;
import model.Country;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для управления коллекцией объектов Person
 */
public class CollectionManager {
    private final HashSet<Person> collection;
    private final LocalDateTime initializationDate;

    public CollectionManager() {
        this.collection = new HashSet<>();
        this.initializationDate = LocalDateTime.now();
    }

    public CollectionManager(HashSet<Person> initialCollection) {
        this.collection = initialCollection != null ? initialCollection : new HashSet<>();
        this.initializationDate = LocalDateTime.now();
        updateMaxId();
    }

    /**
     * @return информация о коллекции
     */
    public String getInfo() {
        return String.format(
                "Тип коллекции: %s\n" +
                        "Дата инициализации: %s\n" +
                        "Количество элементов: %d\n" +
                        "Тип элементов: %s",
                collection.getClass().getName(),
                initializationDate,
                collection.size(),
                Person.class.getName()
        );
    }

    /**
     * @return все элементы коллекции
     */
    public HashSet<Person> getAll() {
        return new HashSet<>(collection);
    }

    /**
     * @return отсортированная коллекция
     */
    public List<Person> getSorted() {
        return collection.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Добавляет новый элемент в коллекцию
     * @param person элемент для добавления
     * @return true если элемент добавлен
     */
    public boolean add(Person person) {
        if (person == null) {
            return false;
        }

        if (containsId(person.getId())) {
            person.setId(generateNewId());
        }

        return collection.add(person);
    }

    /**
     * Обновляет элемент по ID
     * @param id ID элемента
     * @param newPerson новые данные
     * @return true если элемент обновлен
     */
    public boolean update(Integer id, Person newPerson) {
        if (id == null || newPerson == null) {
            return false;
        }

        // Ищем существующий элемент
        Optional<Person> existing = collection.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst();

        if (existing.isPresent()) {
            collection.remove(existing.get());
            newPerson.setId(id);
            return collection.add(newPerson);
        }

        return false; // Элемент не найден
    }

    /**
     * Удаляет элемент по ID
     * @param id ID элемента
     * @return true если элемент удален
     */
    public boolean removeById(Integer id) {
        if (id == null) return false;

        return collection.removeIf(p -> id.equals(p.getId()));
    }

    /**
     * Очищает коллекцию
     */
    public void clear() {
        collection.clear();
    }

    /**
     * @return размер коллекции
     */
    public int size() {
        return collection.size();
    }

    /**
     * Получает элемент по ID
     * @param id ID элемента
     * @return Optional с элементом или empty
     */
    public Optional<Person> getById(Integer id) {
        if (id == null) return Optional.empty();

        return collection.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst();
    }

    /**
     * Проверяет наличие элемента с указанным ID
     */
    public boolean containsId(Integer id) {
        if (id == null) return false;

        return collection.stream()
                .anyMatch(p -> id.equals(p.getId()));
    }

    /**
     * Добавляет элемент, если он меньше минимального
     */
    public boolean addIfMin(Person person) {
        if (person == null) return false;

        Optional<Person> minPerson = collection.stream().min(Person::compareTo);

        if (minPerson.isEmpty() || person.compareTo(minPerson.get()) < 0) {
            return add(person);
        }
        return false;
    }

    /**
     * Удаляет все элементы больше заданного
     */
    public int removeGreater(Person person) {
        if (person == null) return 0;

        int initialSize = collection.size();
        collection.removeIf(p -> p.compareTo(person) > 0);
        return initialSize - collection.size();
    }

    /**
     * Удаляет все элементы меньше заданного
     */
    public int removeLower(Person person) {
        if (person == null) return 0;

        int initialSize = collection.size();
        collection.removeIf(p -> p.compareTo(person) < 0);
        return initialSize - collection.size();
    }

    /**
     * Считает количество элементов с национальностью меньше заданной
     */
    public long countLessThanNationality(Country nationality) {
        if (nationality == null) return 0;

        return collection.stream()
                .map(Person::getNationality)
                .filter(Objects::nonNull)
                .filter(n -> n.compareTo(nationality) < 0)
                .count();
    }

    /**
     * @return множество уникальных национальностей
     */
    public Set<Country> getUniqueNationalities() {
        return collection.stream()
                .map(Person::getNationality)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * @return список дней рождения в порядке убывания
     */
    public List<Date> getBirthdaysDescending() {
        return collection.stream()
                .map(Person::getBirthday)
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    /**
     * Генерирует новый уникальный ID
     */
    private Integer generateNewId() {
        return collection.stream()
                .mapToInt(Person::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Обновляет максимальный ID в Person
     */
    private void updateMaxId() {
        collection.stream()
                .mapToInt(Person::getId)
                .max()
                .ifPresent(Person::setNextId);
    }
}