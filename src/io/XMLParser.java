package io;

import exceptions.InvalidDataException;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Класс для парсинга XML в объекты Person и обратно
 */
public class XMLParser {
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Парсит XML строку в коллекцию Person
     * @param xmlContent XML содержимое
     * @return HashSet объектов Person
     * @throws InvalidDataException при ошибках парсинга
     */
    public HashSet<Person> parseToCollection(String xmlContent) throws InvalidDataException {
        HashSet<Person> collection = new HashSet<>();

        if (xmlContent == null || xmlContent.trim().isEmpty()) {
            return collection;
        }

        try {
            // Удаляем XML заголовок если есть
            String content = xmlContent.replaceFirst("<\\?xml.*\\?>", "").trim();

            // Находим все элементы person
            int maxId = 0;
            String[] personBlocks = extractPersonBlocks(content);

            for (String personBlock : personBlocks) {
                if (personBlock.trim().isEmpty()) continue;

                Person person = parsePerson(personBlock);
                collection.add(person);

                // Отслеживаем максимальный ID для генератора
                if (person.getId() > maxId) {
                    maxId = person.getId();
                }
            }

            // Устанавливаем следующий ID
            if (maxId > 0) {
                Person.setNextId(maxId);
            }

        } catch (Exception e) {
            throw new InvalidDataException("Ошибка парсинга XML: " + e.getMessage(), e);
        }

        return collection;
    }

    /**
     * Извлекает блоки person из XML
     */
    private String[] extractPersonBlocks(String xml) {
        // Упрощенный парсинг - ищем между <person> и </person>
        String[] blocks = xml.split("</person>");
        for (int i = 0; i < blocks.length; i++) {
            int startIndex = blocks[i].lastIndexOf("<person>");
            if (startIndex >= 0) {
                blocks[i] = blocks[i].substring(startIndex + 8).trim();
            }
        }
        return blocks;
    }

    /**
     * Парсит один блок person
     */
    private Person parsePerson(String personBlock) throws InvalidDataException {
        Person person = new Person();

        try {
            // Парсим ID
            person.setId(parseInt(extractTag(personBlock, "id")));

            // Парсим имя
            person.setName(extractTag(personBlock, "name"));

            // Парсим координаты
            String coordsBlock = extractTag(personBlock, "coordinates");
            if (!coordsBlock.isEmpty()) {
                Coordinates coords = new Coordinates();
                coords.setX(parseInt(extractTag(coordsBlock, "x")));
                coords.setY(parseInt(extractTag(coordsBlock, "y")));
                person.setCoordinates(coords);
            }

            // Парсим дату создания
            String dateStr = extractTag(personBlock, "creationDate");
            if (!dateStr.isEmpty()) {
                person.setCreationDate(LocalDate.parse(dateStr));
            }

            // Парсим рост
            String heightStr = extractTag(personBlock, "height");
            if (!heightStr.isEmpty()) {
                person.setHeight(Float.parseFloat(heightStr));
            }

            // Парсим дату рождения
            String birthdayStr = extractTag(personBlock, "birthday");
            if (!birthdayStr.isEmpty() && !"null".equals(birthdayStr)) {
                person.setBirthday(parseDate(birthdayStr));
            }

            // Парсим цвет волос
            String hairColorStr = extractTag(personBlock, "hairColor");
            if (!hairColorStr.isEmpty() && !"null".equals(hairColorStr)) {
                person.setHairColor(Color.valueOf(hairColorStr));
            }

            // Парсим национальность
            String nationalityStr = extractTag(personBlock, "nationality");
            if (!nationalityStr.isEmpty() && !"null".equals(nationalityStr)) {
                person.setNationality(Country.valueOf(nationalityStr));
            }

            // Парсим локацию
            String locationBlock = extractTag(personBlock, "location");
            if (!locationBlock.isEmpty()) {
                Location location = new Location();
                String xStr = extractTag(locationBlock, "x");
                if (!xStr.isEmpty()) {
                    location.setX(Float.parseFloat(xStr));
                }
                String yStr = extractTag(locationBlock, "y");
                if (!yStr.isEmpty()) {
                    location.setY(Long.parseLong(yStr));
                }
                String nameStr = extractTag(locationBlock, "name");
                if (!nameStr.isEmpty() && !"null".equals(nameStr)) {
                    location.setName(nameStr);
                }
                person.setLocation(location);
            }

        } catch (IllegalArgumentException | DateTimeParseException | ParseException e) {
            throw new InvalidDataException("Ошибка парсинга данных: " + e.getMessage(), e);
        }

        return person;
    }

    /**
     * Преобразует XML в строку
     */
    public String parseToXML(HashSet<Person> collection) {
        StringBuilder xml = new StringBuilder();
        xml.append(XML_HEADER);
        xml.append("<persons>\n");

        for (Person person : collection) {
            xml.append("  <person>\n");
            xml.append("    <id>").append(person.getId()).append("</id>\n");
            xml.append("    <name>").append(escapeXml(person.getName())).append("</name>\n");

            // Coordinates
            xml.append("    <coordinates>\n");
            xml.append("      <x>").append(person.getCoordinates().getX()).append("</x>\n");
            xml.append("      <y>").append(person.getCoordinates().getY()).append("</y>\n");
            xml.append("    </coordinates>\n");

            xml.append("    <creationDate>").append(person.getCreationDate()).append("</creationDate>\n");
            xml.append("    <height>").append(person.getHeight()).append("</height>\n");

            // Birthday (может быть null)
            if (person.getBirthday() != null) {
                xml.append("    <birthday>").append(formatDate(person.getBirthday())).append("</birthday>\n");
            } else {
                xml.append("    <birthday>null</birthday>\n");
            }

            // HairColor (может быть null)
            if (person.getHairColor() != null) {
                xml.append("    <hairColor>").append(person.getHairColor()).append("</hairColor>\n");
            } else {
                xml.append("    <hairColor>null</hairColor>\n");
            }

            // Nationality (может быть null)
            if (person.getNationality() != null) {
                xml.append("    <nationality>").append(person.getNationality()).append("</nationality>\n");
            } else {
                xml.append("    <nationality>null</nationality>\n");
            }

            // Location (может быть null)
            if (person.getLocation() != null) {
                xml.append("    <location>\n");
                xml.append("      <x>").append(person.getLocation().getX()).append("</x>\n");
                xml.append("      <y>").append(person.getLocation().getY()).append("</y>\n");
                String name = person.getLocation().getName();
                xml.append("      <name>").append(name != null ? escapeXml(name) : "null").append("</name>\n");
                xml.append("    </location>\n");
            } else {
                xml.append("    <location>null</location>\n");
            }

            xml.append("  </person>\n");
        }

        xml.append("</persons>");
        return xml.toString();
    }

    // Вспомогательные методы

    private String extractTag(String xml, String tag) {
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";

        int start = xml.indexOf(openTag);
        if (start == -1) return "";

        int contentStart = start + openTag.length();
        int end = xml.indexOf(closeTag, contentStart);
        if (end == -1) return "";

        return xml.substring(contentStart, end).trim();
    }

    private Integer parseInt(String str) {
        return str.isEmpty() ? null : Integer.parseInt(str);
    }

    private Date parseDate(String str) throws ParseException {
        return str.isEmpty() ? null : DATE_FORMAT.parse(str);
    }

    private String formatDate(Date date) {
        return date != null ? DATE_FORMAT.format(date) : "null";
    }

    private String escapeXml(String text) {
        if (text == null) return "null";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}