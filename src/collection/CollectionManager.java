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
     * @return размер коллекции
     */
    public int size() {
        return collection.size();
    }

    /**
     * @return true если коллекция пуста
     */
    public boolean isEmpty() {
        return collection.isEmpty();
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
     * @return минимальный элемент коллекции
     */
    public Optional<Person> getMin() {
        return collection.stream()
                .min(Person::compareTo);
    }

    /**
     * @return максимальный элемент коллекции
     */
    public Optional<Person> getMax() {
        return collection.stream()
                .max(Person::compareTo);
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
     * Добавляет элемент, если его день рождения раньше самого раннего в коллекции
     */
    public boolean addIfMin(Person person) {
        if (person == null || person.getBirthday() == null) return false;

        Optional<Person> oldest = collection.stream()
                .filter(p -> p.getBirthday() != null)
                .min(Comparator.comparing(Person::getBirthday));

        if (oldest.isEmpty() || person.getBirthday().before(oldest.get().getBirthday())) {
            return add(person);
        }
        return false;
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
        Optional<Person> existing = getById(id);
        if (existing.isPresent()) {
            collection.remove(existing.get());
            newPerson.setId(id);
            return collection.add(newPerson);
        }
        return false;
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
     * Удаляет все элементы с ростом больше заданного
     */
    public int removeGreater(Person person) {
        if (person == null) return 0;
        int initialSize = collection.size();
        collection.removeIf(p -> p.getHeight() > person.getHeight());
        return initialSize - collection.size();
    }

    /**
     * Удаляет все элементы с ростом меньше заданного
     */
    public int removeLower(Person person) {
        if (person == null) return 0;
        int initialSize = collection.size();
        collection.removeIf(p -> p.getHeight() < person.getHeight());
        return initialSize - collection.size();
    }

    /**
     * Очищает коллекцию
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Считает количество элементов с национальностью, имеющей меньший порядковый номер в enum
     */
    public long countLessThanNationality(Country nationality) {
        if (nationality == null) return 0;
        return collection.stream()
                .map(Person::getNationality)
                .filter(Objects::nonNull)
                .filter(n -> n.ordinal() < nationality.ordinal())
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

    /**
     * Валидирует всю коллекцию
     * @return список ошибок
     */
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        for (Person person : collection) {
            if (person == null) {
                errors.add("Обнаружен null элемент");
                continue;
            }
            try {
                if (person.getId() == null || person.getId() <= 0) {
                    errors.add("Элемент с некорректным ID: " + person);
                }
                long count = collection.stream()
                        .filter(p -> p.getId().equals(person.getId()))
                        .count();
                if (count > 1) {
                    errors.add("Обнаружены дубликаты ID: " + person.getId());
                }
            } catch (Exception e) {
                errors.add("Ошибка валидации элемента: " + e.getMessage());
            }
        }
        return errors;
    }

    @Override
    public String toString() {
        return String.format("CollectionManager{size=%d, type=HashSet<Person>}", collection.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionManager that = (CollectionManager) o;
        return Objects.equals(collection, that.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection);
    }
}