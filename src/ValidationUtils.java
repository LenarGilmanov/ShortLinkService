public class ValidationUtils {
    //ПРОВЕРКА КОРРЕКТНОСТИ UUID
    public static boolean isValidUUID(String stringUUID) {
        if (stringUUID.length() != 36) { //проверка длины UUID
            return false;
        }

        if (stringUUID == null || stringUUID.trim().isEmpty()) { //проверка переданной строки на пустоту (пробелы)
            return false;
        }

        if (stringUUID.contains(":") || stringUUID.contains(" ") || stringUUID.contains("\n") || stringUUID.contains("\r")) { //проверка недопустимых символов
            return false;
        }

        return !stringUUID.matches("\\d+"); //проверка строки на содержание только цирф

    }

    //ПРОВЕРКА КОРРЕКТНОСТИ ИСХОДНОЙ ДЛИННОЙ ССЫЛКИ
    public static boolean isValidLongLink(String longLink) {
        if (longLink.length() < 10 || longLink.length() > 2000) {
            return false;
        }

        if (longLink.trim().isEmpty()) { //проверка переданной строки на пустоту
            return false;
        }

        if (!longLink.matches("^https://.*")) { //проверка на начало строки с https://
            return false;
        }

        if (longLink.matches("\\d+")) { //проверка строки на содержание только цирф
            return false;
        }

        return true;
    }

    //ПРОВЕРКА КОРРЕКТНОСТИ КОЛИЧЕСТВА КЛИКОВ
    public static boolean isValidClickLimit(int clicks) {
        return clicks > 0 && clicks <= Config.getMaxClickLimit();
    }

    //ПРОВЕРКА КОРРЕКТНОСТИ ВРЕМЕНИ ЖИЗНИ ССЫЛКИ
    public static boolean isValidLinkLifeTime(int expiryLifetimeMinutes) {
        return expiryLifetimeMinutes > 1;
    }

    //ПРОВЕРКА КОРРЕКТНОСТИ КОРОТКОЙ ССЫЛКИ
    public static boolean isValidShortLink(String shortLink) {
        if (shortLink.length() != 14) { //проверка длины короткой ссылки (согласно условию click.ru/ + 6 символов = 14 символов)
            return false;
        }

        if (shortLink.trim().isEmpty()) { //проверка переданной короткой ссылки на пустоту
            return false;
        }

        return shortLink.startsWith("clck.ru/"); //проверка начала короткой ссылки на clck.ru/
    }

}