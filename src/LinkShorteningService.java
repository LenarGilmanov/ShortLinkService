import java.awt.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

class LinkShorteningService {
    private final LinkRepositoryInterface repository;

    public LinkShorteningService(LinkRepositoryInterface repository) {
        this.repository = repository;
    }

    //ГЕНЕРАЦИЯ UUID
    public UUID generateUUID() {
        UUID userUUID = UUID.randomUUID();
        return userUUID;
    }

    //СОЗДАНИЕ КОРОТКОЙ ССЫЛКИ
    public LinkResponse createShortLink(String stringUUID, String longLink, int maxClicks) {
        UUID userUUID = UUID.fromString(stringUUID);
        String shortLink = "clck.ru/" + UUID.randomUUID().toString().substring(0, 6);

        LinkData linkData = new LinkData(longLink, maxClicks, LocalDateTime.now().plusDays(1), userUUID);
        repository.saveLink(shortLink, linkData);
        return new LinkResponse(shortLink, userUUID);
    }

    //ПЕРЕХОД ПО КОРОТКОЙ ССЫЛКЕ
    public void navigateToShortLink(String shortLink) throws Exception {
        LinkData linkData = repository.getLink(shortLink);

        if (linkData == null) {
            throw new Exception("Ссылка некорректна или удалена");
        }

        if (linkData.isExpired()) {
            throw new Exception("Время жизни ссылки истекло");
        }

        if (linkData.isMaxClicksReached()) {
            throw new Exception("Достигнуто максимальное количество кликов");
        }

        linkData.incrementClickCount();
        Desktop.getDesktop().browse(new URI("https://ru.stackoverflow.com")); //переход по ссылке по условию
    }

    //УДАЛЕНИЕ КОРОТКОЙ ССЫЛКИ
    public boolean deleteLink(String shortLink, String stringUUID) {
        LinkData linkData = repository.getLink(shortLink);
        if (linkData != null && linkData.getUserUUID().toString().equals(stringUUID)) {
            repository.deleteLink(shortLink);
            return true;
        }

        return false;
    }

    //ИЗМЕНЕНИЕ ПАРАМЕТРОВ ССЫЛКИ
    public boolean changeLinkParamentrs(String shortLink, String stringUUID, int clicksQty, int expiryLifetimeMinutes) {
        LinkData linkData = repository.getLink(shortLink);
        if (linkData != null && linkData.getUserUUID().toString().equals(stringUUID)) {
            linkData.changeLimitClicks(clicksQty); //задаем максимальное количество кликов для данной короткой ссылки

            LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(expiryLifetimeMinutes);// Определяем время жизни по введенному аргументу clicksQty
            if (expiryTime.isBefore(linkData.getExpiryTime())) {
                linkData.setExpiryTime(expiryTime);
            }

            return true;
        }
        return false;
    }

    //УДАЛЕНИЕ ССЫЛОК С ИСТЕКШИМ СРОКОМ ДЕЙСТВИЯ
    public boolean deleteExpiredLinks() {
        repository.deleteExpiredLinks();
        return true;
    }

    //ПОЛУЧЕНИЕ ВРЕМЕНИ ЖИЗНИ ССЫЛКИ
    public LocalDateTime checkLinkLifetime(String shortLink, String stringUUID) {
        LinkData linkData = repository.getLink(shortLink);
        LocalDateTime linkLifetime = null;
        if (linkData != null && linkData.getUserUUID().toString().equals(stringUUID)) {
            linkLifetime = linkData.getExpiryTime();
        }
        return linkLifetime;
    }

    //ПОЛУЧЕНИЕ МАКСИМАЛЬНОГО КОЛИЧЕСТВА КЛИКОВ ССЫЛКИ
    public int checkLinkClicksMaxQty(String shortLink, String stringUUID) {
        LinkData linkData = repository.getLink(shortLink);
        int linkDataMaxClicks = 0;
        if (linkData != null && linkData.getUserUUID().toString().equals(stringUUID)) {
            linkDataMaxClicks = linkData.getMaxClicks();
        }

        return linkDataMaxClicks;
    }

    //ПОЛУЧЕНИЕ КОЛИЧЕСТВА ВЫПОЛНЕННЫХ КЛИКОВ ССЫЛКИ
    public int checkLinkClicksQty(String shortLink, String stringUUID) {
        LinkData linkData = repository.getLink(shortLink);
        int linkDataClicks = 0;
        if (linkData != null && linkData.getUserUUID().toString().equals(stringUUID)) {
            linkDataClicks = linkData.getClicksQty();
        }

        return linkDataClicks;
    }

}