public class Application {
    public static void main(String[] args) {
        LinkShorteningService linkShorteningService = new LinkShorteningService(new LinkInMemoryRepository());
        ShortLinkController controller = new ShortLinkController(linkShorteningService);

        controller.start(); //запуск программы - вызов метода start() класса ShortLinkController
    }

}