## Доступен следующий функционал:
### api/projects

Интерфейс для работы с сущностями типа "Проект" вида
```json
{
  "id":1,
  "name":"Project",
  "description":"Decsription",
  "dateOfCreation":"2019-04-16T17:59:34",
  "dateOfLastModification":"2019-04-19T10:03:22"
}
```

Доступны следующие методы:
* GET

Доступна пагинация по параметру ```/?page=```, по умолчанию ```page=0```
* GET ../{id}
* POST
* DELETE ../{id}, ../{id}/edit
* PUT ../{id}, ../{id}/edit
* PATCH ../{id}, ../{id}/edit
* GET ../{id}/tasks
  
### api/tasks

Интерфейс для работы с сущностями типа "Задача" вида
```json 
{
"id":1,
"project":{
  "id":1,
  "name":"Project",
  "description":"Decsription",
  "dateOfCreation":"2019-04-16T17:59:34",
  "dateOfLastModification":"2019-04-19T10:03:22"
  },
"name":"Task",
"description":"Decsription",
"priority":0,
"dateOfCreation":"2019-04-16T17:59:38",
"dateOfLastModification":"2019-04-18T18:19:36",
"status":"NEW"
}
```

Доступны следующие методы:
* GET

Доступна сортировка или фильтрация результатов

Для сортировки необходимо использовать следующие параметры
* ```/?sort=priority``` для сортировки по приоритетам
* ```/?sort=date```для сортировки по дате создания

Для фильтрации необходимо использовать следующие параметры
* ```/?filter=priority&ptype=0``` для фильтрации по приоритетам
* ```/?filter=status&stype=in_progress``` для фильтрации по статусам
* ```/?filter=date&datef=01012019&datet=31122019``` для фильтрации по дате создания (параметры ```datef``` и ```datet``` не являются обязательными)

Доступна пагинация по параметру ```/?page=```, по умолчанию ```page=0```

* GET ../{id}
* POST
* DELETE ../{id}, ../{id}/edit
* PUT ../{id}, ../{id}/edit
