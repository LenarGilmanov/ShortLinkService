public interface LinkRepositoryInterface {
    void saveLink(String shortLink, LinkData linkData);
    void deleteLink(String shortLink);
    void deleteExpiredLinks();
    LinkData getLink(String shortLink);
}