Сервис для сокращения ссылок

После запуска программы нужно ввести в консоль номер команды:
1. Сгенерировать UUID пользователя
2. Создать короткую ссылку "clck.ru/..."
3. Перейти по короткой ссылке
4. Удалить короткую ссылку
5. Изменить параметры короткой ссылки
7. Удалить ссылки с истекшим сроком действия
8. Проверить время жизни ссылки
9. Проверить максимальное количество кликов ссылки
10. Проверить количество выполненных кликов ссылки
11. Завершить работу программы

После ввода номера команды вызываются соответствующие методы в классе ShortLinkController. Данные методы обращаются к методам класса LinkShorteningService и ValidationUtils.

Информация по ссылкам сохраняется в оперативной памяти, сохранение реализовано в классах LinkData и LinkInMemoryRepository.

В классе Config задано максимальное количество кликов (проверка применяется при создании ссылки (команда 2) и изменении ее параметров (команда 5)).

Переход по ссылке, заданной в условии: "https://ru.stackoverflow.com".

При создании ссылки ей по умолчанию задается время жизни 1 сутки. Время жизни можно менять командой 5.

В классе ValidationUtils заданы проверки, которые вызываются в классе ShortLinkController при вводе значений в консоль (UUID, длинная ссылка, количество кликов, время жизни ссылки, короткая ссылка).

