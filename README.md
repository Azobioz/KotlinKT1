# KotlinKT1

Данный API был создан с целью закрыть КТ1 на 25 баллов по предмету "Кроссплатформенная разработка на языке Kotlin"

## Что нужно для работы с API
* Скачать Docker Desktop [здесь](https://www.docker.com/products/docker-desktop/)

## Как запустить API 

### На Windows
1. Запустите Docker Desktop
2. Перейдите в командную строку и пропишите
```

docker run -p 8088:8088 azobioz/kotlin-kt1:latest

```

3. Чтобы выключить работу API пропишите в командной строке:
```

docker ps -q --filter ancestor=azobioz/kotlin-kt1:latest | xargs -r docker stop

```

## Описание всех запросов

Каждый запрос начинается с http://127.0.0.1:8088{/запрос}

### Создание нового пользователя
```
 POST /users
```
Тело запроса:
```
{
     "id": Integer,
     "name": String,
     "age": Integer
}
```
Ответ:
```
{
     "id": Integer,
     "name": String,
     "age": Integer
}
```

### Получить всех пользователей
```
 GET /users
```

Ответ:
```
[
    {
        "id": Integer,
        "name": String,
        "age": Integer
    },
    {
        "id": Integer,
        "name": String,
        "age": Integer
    },
    {
    ...
    }

]
```

### Получить определенного пользователя по ID
```
 GET /users/{id}
```

Ответ:
```
{
    "id": Integer,
    "name": String,
    "age": Integer
}
```

### Получить пользователей, у которых возраст меньше или равно параметру ageMax
```
 GET /users?ageMax={value}
```

Ответ:
```
[
  {
      "id": Integer,
      "name": String,
      "age": Integer
  },
  {
  ...
  }
]
```

### Получить пользователей, у которых возраст больше или равно параметру ageMin
```
 GET /users?ageMin={value}
```

Ответ:
```
[
  {
      "id": Integer,
      "name": String,
      "age": Integer
  },
  {
  ...
  }
]
```

### Удалить пользователя
```
 GET /users/{id}
```

## Дополнительно
* API хранит данные в памяти, а не в БД, что значит при перезапуске API, данные введенные пользователем пропадут
* При запуске API создаются 2 пользователя:
```
[
    {
        "id": 1,
        "name": "Alice",
        "age": 25
    },
    {
        "id": 2,
        "name": "Bob",
        "age": 30
    }
]
```

