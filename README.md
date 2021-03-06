[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a742cf07f4a4454cae3753e7bcbde69f)](https://www.codacy.com/app/vitalynec/bugtracker?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=vitalynec/bugtracker&amp;utm_campaign=Badge_Grade)
## Тестовое задание «Bug tracking»

### Окружение

Реализовать REST API системы управления проектами и задачами.

Код выполненного задания должен быть структурирован, легко читаем и содержать необходимые
комментарии. Написанная Вами программа должна являться законченным программным
продуктом, т.е. должна легко устанавливаться, предусматривать обработку нестандартных
ситуаций, быть устойчивой к некорректным действиям пользователей.

Результат работы нужно предоставить в виде ссылки на открытый GiHub/Gitlab репозиторий с
исходным кодом приложения.

### Основные сущности
* Проект. Основные данные: наименование, текстовое описание, дата создания и последней модификации.
* Задача. Основные данные: ссылка на проект, наименование, текстовое описание,
приоритет (в виде целого положительного числа), дата создания и последней
модификации, статус (один из «НОВАЯ», «В РАБОТЕ», «ЗАКРЫТА»).

### Нефункциональные требования
* Необходимо использовать Java 8 и Spring Boot (версия >= 2.0).
* В качестве базы данных необходимо использовать MySQL (версия >= 5.7) или PostgreSQL (версия >= 9.4).
* Интерфейс приложения должен быть представлен в виде JSON REST API.

### Функциональные требования
* Возможность создавать/просматривать/редактировать/удалять информацию о проектах.
* Возможность создавать/просматривать/редактировать/удалять информацию о задачах.
* Для задач предусмотреть следующие способы модификации:
  * Если задача создана, её статус должен быть «НОВАЯ».
  * Если задача имеет статус «НОВАЯ», её статус может быть изменён на «В
РАБОТЕ» и «ЗАКРЫТА». Наименование и описание изменять допустимо.
  * Если задача имеет статус «В РАБОТЕ», её статус может быть изменён на
«НОВАЯ» и «ЗАКРЫТА». Наименование и описание изменять допустимо.
  * Если задача имеет статус «ЗАКРЫТА», модификация становится недопустимой.
* Возможность просматривать информацию о задачах конкретного проекта.
* При получении доступа к списку проектов и задач требуется использовать пагинацию.
* Для просмотра списка задач предусмотреть следующие способы фильтрации: по диапазону
дат, по статусам, по приоритетам.
* Для просмотра списка задач предусмотреть следующие способы сортировки: по дате,
приоритету.

### Дополнительные требования (будет плюсом, но не обязательно)
* Модульные тесты
* Dockerfile для сборки и запуска приложения одной командой

Отзыв
======================================
Выявлены следующие недочеты (исправлено):
* ~~логика сервиса размещена в контроллере, из-за чего код становится сложнее читать;~~
* ~~процедурный (не ООП) код в контроллере;~~
* нет инструкции, как поднимать приложение;
* ~~захардкоженные значения параметров в контроллере;~~
* ~~вложенный switch case, что негативно сказывается на читаемости кода;~~
* ~~часто - нет обработки ошибок в контроллере.~~

Рекомендуем в будущем обратить внимание на структуру оформления кода при использовании Spring, а также декомпозицию логики.
