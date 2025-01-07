import java.util.UUID;
import java.time.LocalDateTime;

public class LinkData {
    private final String longLink;       //исходная длинная ссылка
    private int maxClicks;      //максимальное кол-во кликов, которое задается пользователем
    private LocalDateTime expiryTime;       //время жизни ссылки
    private final UUID userUUID;
    private int counterClicks;

    public LinkData(String longLink, int maxClicks, LocalDateTime expiryTime, UUID userUUID) {
        this.longLink = longLink;
        this.maxClicks = maxClicks;
        this.expiryTime = expiryTime;
        this.userUUID = userUUID;
        this.counterClicks = 0;
    }

    public void setExpiryTime(LocalDateTime newExpiryTime) {
        this.expiryTime = newExpiryTime;
    }

    public int getMaxClicks() {
        return maxClicks;
    }

    public int getClicksQty() {
        return counterClicks;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public String getLongLink() {
        return longLink;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }

    public boolean isMaxClicksReached () {      //проверка превышения кол-ва кликов заданного макс.значения
        return counterClicks >= maxClicks;
    }

    public void incrementClickCount () {        //увеличение кол-ва кликов на 1
        counterClicks++;
    }

    public UUID getUserUUID() {         //возвращает UUID пользователя
        return userUUID;
    }

    public void changeLimitClicks(int newClicksQty) {       //изменение макс.кол-ва кликов в updateLinkParams()
        this.maxClicks = newClicksQty;
    }
}