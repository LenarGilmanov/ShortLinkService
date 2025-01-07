public class Config {           //класс с конфигурационными параметрами для приложения
    private static final int MAX_CLICK_LIMIT = 25;       //максимальное кол-во кликов

    public static int getMaxClickLimit() {
        return MAX_CLICK_LIMIT;
    }
}