package collection;

import model.Person;
import model.Country;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Менеджер для управления коллекцией объектов Person
 */
public class CollectionManager {
    private final HashSet<Person> collection;
    private final LocalDateTime initializationDate;

    public CollectionManager() {
        this.collection = new HashSet<>();
        this.initializationDate = LocalDateTime.now();
    }

    public CollectionManager(HashSet<Person> initialCollection) {
        if (initialCollection != null) {
            this.collection = initialCollection;
        } else {
            this.collection = new HashSet<>();
        }
        this.initializationDate = LocalDateTime.now();
        updateMaxId();
    }

    /**
     * @return копия всех элементов коллекции
     */
    public HashSet<Person> getAll() {
        return new HashSet<>(collection);
    }

    /**
     * @return информация о коллекции
     */
    public String getInfo() {
        return "Тип коллекции: " + collection.getClass().getName() + "\n" +
                "Дата инициализации: " + initializationDate + "\n" +
                "Количество элементов: " + collection.size() + "\n" +
                "Тип элементов: " + Person.class.getName();
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
     * @return отсортированная коллекция (по имени)
     */
    public List<Person> getSorted() {
        List<Person> sortedList = new ArrayList<>(collection);
        Collections.sort(sortedList);
        return sortedList;
    }

    /**
     * Получает элемент по ID
     * @param id ID элемента
     * @return элемент или null, если не найден
     */
    public Person getById(Integer id) {
        if (id == null) return null;

        for (Person person : collection) {
            if (id.equals(person.getId())) {
                return person;
            }
        }
        return null;
    }

    /**
     * Проверяет наличие элемента с указанным ID
     * @param id ID для проверки
     * @return true если элемент существует
     */
    public boolean containsId(Integer id) {
        if (id == null) return false;

        for (Person person : collection) {
            if (id.equals(person.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return минимальный элемент (по имени)
     */
    public Person getMin() {
        if (collection.isEmpty()) return null;

        Person minPerson = null;
        for (Person person : collection) {
            if (minPerson == null || person.compareTo(minPerson) < 0) {
                minPerson = person;
            }
        }
        return minPerson;
    }

    /**
     * Добавляет новый элемент в коллекцию
     * @param person элемент для добавления
     * @return true если элемент добавлен
     */
    public boolean add(Person person) {
        if (person == null) return false;

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
        if (id == null || newPerson == null) return false;

        Person existing = getById(id);
        if (existing == null) return false;

        collection.remove(existing);
        newPerson.setId(id);
        return collection.add(newPerson);
    }

    /**
     * Удаляет элемент по ID
     * @param id ID элемента
     * @return true если элемент удален
     */
    public boolean removeById(Integer id) {
        if (id == null) return false;

        Iterator<Person> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (id.equals(person.getId())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Очищает коллекцию
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Добавляет элемент, если он меньше минимального (по имени)
     * @param person элемент для добавления
     * @return true если элемент добавлен
     */
    public boolean addIfMin(Person person) {
        if (person == null) return false;

        Person min = getMin();
        if (min == null || person.compareTo(min) < 0) {
            return add(person);
        }
        return false;
    }

    /**
     * Удаляет все элементы больше заданного (по имени)
     * @param person элемент для сравнения
     * @return количество удаленных элементов
     */
    public int removeGreater(Person person) {
        if (person == null) return 0;

        int before = collection.size();

        Iterator<Person> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.compareTo(person) > 0) {
                iterator.remove();
            }
        }

        return before - collection.size();
    }

    /**
     * Удаляет все элементы меньше заданного (по имени)
     * @param person элемент для сравнения
     * @return количество удаленных элементов
     */
    public int removeLower(Person person) {
        if (person == null) return 0;

        int before = collection.size();

        Iterator<Person> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.compareTo(person) < 0) {
                iterator.remove();
            }
        }

        return before - collection.size();
    }

    /**
     * Считает количество элементов с национальностью меньше заданной
     * @param nationality национальность для сравнения
     * @return количество элементов
     */
    public long countLessThanNationality(Country nationality) {
        if (nationality == null) return 0;

        int count = 0;
        for (Person person : collection) {
            Country personNationality = person.getNationality();
            if (personNationality != null && personNationality.ordinal() < nationality.ordinal()) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return множество уникальных национальностей
     */
    public Set<Country> getUniqueNationalities() {
        Set<Country> uniqueNationalities = new HashSet<>();

        for (Person person : collection) {
            Country nationality = person.getNationality();
            if (nationality != null) {
                uniqueNationalities.add(nationality);
            }
        }

        return uniqueNationalities;
    }

    /**
     * @return список дней рождения в порядке убывания
     */
    public List<Date> getBirthdaysDescending() {
        List<Date> birthdays = new ArrayList<>();

        for (Person person : collection) {
            Date birthday = person.getBirthday();
            if (birthday != null) {
                birthdays.add(birthday);
            }
        }

        Collections.sort(birthdays, Collections.reverseOrder());
        return birthdays;
    }

    /**
     * Генерирует новый уникальный ID
     * @return новый ID
     */
    private Integer generateNewId() {
        if (collection.isEmpty()) return 1;

        int maxId = 0;
        for (Person person : collection) {
            if (person.getId() > maxId) {
                maxId = person.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Обновляет максимальный ID в генераторе Person
     */
    private void updateMaxId() {
        int maxId = 0;
        for (Person person : collection) {
            if (person.getId() > maxId) {
                maxId = person.getId();
            }
        }
        Person.setNextId(maxId + 1);
    }

    @Override
    public String toString() {
        return "CollectionManager{size=" + collection.size() + "}";
    }
}