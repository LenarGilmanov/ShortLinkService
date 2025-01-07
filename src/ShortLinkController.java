import java.util.Scanner;

public class ShortLinkController {
    private final LinkShorteningService service;
    private final Scanner input = new Scanner(System.in);

    public ShortLinkController(LinkShorteningService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            System.out.println("Введите номер команды: \n" +
                    "1. Сгенерировать UUID пользователя\n" +
                    "2. Создать короткую ссылку \"clck.ru/...\"\n" +
                    "3. Перейти по короткой ссылке\n" +
                    "4. Удалить короткую ссылку\n" +
                    "5. Изменить параметры короткой ссылки\n" +
                    "7. Удалить ссылки с истекшим сроком действия\n" +
                    "8. Проверить время жизни ссылки\n" +
                    "9. Проверить максимальное количество кликов ссылки\n" +
                    "10. Проверить количество выполненных кликов ссылки\n" +
                    "11. Завершить работу программы"); //+

            String commandNumber = input.nextLine(); //+
            switch (commandNumber) {
                case "1":
                    generateUUID();
                    break;
                case "2":
                    createShortLink();
                    break;
                case "3":
                    navigateToShortLink();
                    break;
                case "4":
                    deleteLink();
                    break;
                case "5":
                    changeLinkParamentrs();
                    break;
                case "7":
                    deleteExpiredLinks();
                    break;
                case "8":
                    checkLinkLifetime();
                    break;
                case "9":
                    checkLinkClicksMaxQty();
                    break;
                case "10":
                    checkLinkClicksQty();
                    break;
                case "11":
                    System.out.println("Работа программы завершена");
                    return;
                default:
                    System.out.println("Введен некорректный номер команды");
            }
        }
    }

    //ГЕНЕРАЦИЯ UUID
    public void generateUUID() {
        System.out.println("Сгенерированный UUID: " + service.generateUUID());
    }

    //СОЗДАНИЕ КОРОТКОЙ ССЫЛКИ
    public void createShortLink() {
        System.out.println("Введите UUID: ");
        String stringUUID = input.nextLine();
        if (!ValidationUtils.isValidUUID(stringUUID)) {
            System.out.println("Введите корректный UUID");
            return;
        }

        System.out.println("Введите длинную URL-ссылку: ");
        String longLink = input.nextLine();
        if (!ValidationUtils.isValidLongLink(longLink)) {
            System.out.println("Введите корректную длинную ссылку");
            return;
        }

        System.out.println("Введите максимальное количество кликов для создаваемой короткой ссылки: ");
        int maxClicks = input.nextInt();
        if (!ValidationUtils.isValidClickLimit(maxClicks)) {
            System.out.println("Количество кликов должно быть > 0 и < " + Config.getMaxClickLimit());
            return;
        }

        LinkResponse response = service.createShortLink(stringUUID, longLink, maxClicks);

        System.out.println("Короткая ссылка создана: " + response.getShortLink());
        System.out.println("User UUID: " + response.getUserUUID());
    }

    //ПЕРЕХОД ПО КОРОТКОЙ ССЫЛКЕ
    private void navigateToShortLink() {
        System.out.println("Введите короткую ссылку для перехода по ней: ");
        String shortLink = input.nextLine();

        try {
            System.out.println("Переход по ссылке : " + shortLink);
            service.navigateToShortLink(shortLink);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //УДАЛЕНИЕ КОРОТКОЙ ССЫЛКИ
    private void deleteLink() {
        System.out.println("Введите UUID: ");
        String stringUUID = input.nextLine();
        if (!ValidationUtils.isValidUUID(stringUUID)) {
            System.out.println("Введите корректный UUID");
            return;
        }

        System.out.println("Введите короткую ссылку: ");
        String shortLink = input.nextLine();
        if (!ValidationUtils.isValidShortLink(shortLink)) {
            System.out.println("Введенная короткая ссылка некорректна");
            return;
        }

        if (service.deleteLink(shortLink, stringUUID)) {
            System.out.println("Ссылка удалена");
        } else  {
            System.out.println("Не удалось удалить ссылку");
        }
    }

    //ИЗМЕНЕНИЕ ПАРАМЕТРОВ ССЫЛКИ
    private void changeLinkParamentrs() {
        System.out.println("Введите UUID: ");
        String stringUUID = input.nextLine();

        if (!ValidationUtils.isValidUUID(stringUUID)) {
            System.out.println("Введите корректный UUID");
            return;
        }

        System.out.println("Введите короткую ссылку: ");
        String shortLink = input.nextLine();
        if (!ValidationUtils.isValidShortLink(shortLink)) {
            System.out.println("Введенная короткая ссылка некорректна");
            return;
        }

        System.out.println("Введите максимальное количество кликов: ");
        int clicksQty = input.nextInt();
        if (!ValidationUtils.isValidClickLimit(clicksQty)) {
            System.out.println("Количество кликов должно быть > 0 и < " + Config.getMaxClickLimit());
            return;
        }

        System.out.println("Введите время жизни ссылки в минутах: ");
        int expiryLifetimeMinutes = input.nextInt();
        if (!ValidationUtils.isValidLinkLifeTime(expiryLifetimeMinutes)) {
            System.out.println("Время жизни ссылки должно быть положительным");
            return;
        }

        if (service.changeLinkParamentrs(shortLink, stringUUID, clicksQty, expiryLifetimeMinutes)) {
            System.out.println("Параметры ссылки обновлены");
        } else {
            System.out.println("Не удалось обновить параметры ссылки");
        }
    }

    //УДАЛЕНИЕ ССЫЛОК С ИСТЕКШИМ СРОКОМ ДЕЙСТВИЯ
    private void deleteExpiredLinks() {
        if(service.deleteExpiredLinks())
            System.out.println("Ссылки с истекшим сроком действия удалены");;
    }

    //ПОЛУЧЕНИЕ ВРЕМЕНИ ЖИЗНИ ССЫЛКИ
    private void checkLinkLifetime() {
        System.out.println("Введите UUID: ");
        String stringUUID = input.nextLine();

        if (!ValidationUtils.isValidUUID(stringUUID)) {
            System.out.println("Введите корректный UUID");
            return;
        }

        System.out.println("Введите короткую ссылку: ");
        String shortLink = input.nextLine();
        if (!ValidationUtils.isValidShortLink(shortLink)) {
            System.out.println("Введенная короткая ссылка некорректна");
            return ;
        }

        System.out.println("Время жизни ссылки до " + service.checkLinkLifetime(shortLink, stringUUID));
    }

    //ПОЛУЧЕНИЕ МАКСИМАЛЬНОГО КОЛИЧЕСТВА КЛИКОВ ССЫЛКИ
    private void checkLinkClicksMaxQty() {
        System.out.println("Введите UUID: ");
        String stringUUID = input.nextLine();

        if (!ValidationUtils.isValidUUID(stringUUID)) {
            System.out.println("Введите корректный UUID");
            return;
        }

        System.out.println("Введите короткую ссылку: ");
        String shortLink = input.nextLine();
        if (!ValidationUtils.isValidShortLink(shortLink)) {
            System.out.println("Введенная короткая ссылка некорректна");
            return ;
        }

        System.out.println("Максимальное количество кликов данной ссылки: " + service.checkLinkClicksMaxQty(shortLink, stringUUID));
    }

    //ПОЛУЧЕНИЕ КОЛИЧЕСТВА ВЫПОЛНЕННЫХ КЛИКОВ ССЫЛКИ
    private void checkLinkClicksQty() {
        System.out.println("Введите UUID: ");
        String stringUUID = input.nextLine();

        if (!ValidationUtils.isValidUUID(stringUUID)) {
            System.out.println("Введите корректный UUID");
            return;
        }

        System.out.println("Введите короткую ссылку: ");
        String shortLink = input.nextLine();
        if (!ValidationUtils.isValidShortLink(shortLink)) {
            System.out.println("Введенная короткая ссылка некорректна");
            return ;
        }

        System.out.println("Количество выполненных кликов данной ссылки: " + service.checkLinkClicksQty(shortLink, stringUUID));
    }

}