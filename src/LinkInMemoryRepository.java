import java.util.HashMap;
import java.util.Map;

public class LinkInMemoryRepository implements LinkRepositoryInterface {         //класс для сохранения ссылок в памяти (создается словарь: ключ - короткая ссылка, значение - linkData)
    private final Map<String, LinkData> linkStorage = new HashMap<>();      //создание словаря
    @Override
    public void saveLink(String shortLink, LinkData linkData) {         //сохранение ссылки - добавление в словарь ключ-значение (короткая ссылка, объект класса
        linkStorage.put(shortLink, linkData);
    }

    @Override
    public LinkData getLink(String shortLink) {
        return linkStorage.get(shortLink);
    }

    @Override
    public void deleteLink(String shortLink) {
        linkStorage.remove(shortLink);
    }

    @Override
    public void deleteExpiredLinks() {
        linkStorage.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}