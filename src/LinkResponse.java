import java.util.UUID;

public class LinkResponse {
    private final String shortLink;
    private final UUID userUUID;

    public LinkResponse(String shortLink, UUID userUUID) {
        this.shortLink = shortLink;
        this.userUUID = userUUID;
    }


    public String getShortLink() {
        return shortLink;
    }

    public UUID getUserUUID() {
        return userUUID;
    }
}