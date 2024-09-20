# Library

## Оглавление

- [Описание](#описание)
- [Начало работы](#начало-работы)
- [Технологии и инструменты](#технологии-и-инструменты)
- [Логика работы](#Логика-работы)
- [Требования](#требования)
- [Установка](#установка)
- [Использование](#использование)
- [Тестирование](#тестирование)

## Описание

Проект представляет собой систему управления библиотекой книг с возможностью поиска, редактирования и удаления книг, а также добавления комментариев.

> Это приложение позволяет пользователям добавлять, редактировать и удалять информацию об авторах книг.

## Начало работы

В этом разделе будет информация о том, как начать работать с проектом.

## Технологии и инструменты
- **Java**: Основной язык программирования для backend-части.
- **Spring Boot**: Фреймворк для быстрой разработки микросервисов.
- **Spring Security**: Обеспечивает безопасность доступа к API.
- **JWT (JSON Web Token)**: Используется для аутентификации и авторизации пользователей.
- **PostgreSQL**: База данных для хранения информации о книгах, авторах и комментариях.
- **Redis**: Используется для хранения хешированных паролей.
- **Maven**: Система сборки проекта.


## Логика работы
- **Пользовательский интерфейс**: Клиентское приложение взаимодействует с REST API через HTTP запросы.
- **API**: RESTful API, который позволяет выполнять различные операции с книгами, такие как создание, чтение, обновление и удаление (CRUD).
- **Безопасность**: Spring Security и JWT используются для защиты API.
- **База данных**: PostgreSQL служит хранилищем для данных о книгах, авторах и комментариях.


## Требования

- Java 11 или выше
- Maven
- Spring Boot

### Установка

1. Клонируй репозиторий:
   ```bash
   git clone https://github.com/ValentinShilov/Library.git
   ```

2. Перейди в директорию проекта:
   ```bash
   cd Library
   ```

3. Собери проект и установи зависимости:
   ```bash
   mvn install
   ```

## Использование

Вот как запускать и использовать приложение:

1. **Запусти приложение:**
   ```bash
   mvn spring-boot:run
   ```

2. **Настрой подключение к базе данных:**  
   Открой файл `src/main/resources/application.yaml` и измени настройки подключения к вашей базе данных:

   ```yaml
    url: ${JDBC_DATABASE_URL:jdbc:postgresql://localhost:5432/ваша_бд}
    username: ${JDBC_DATABASE_USERNAME:ваш_username}
    password: ${JDBC_DATABASE_PASSWORD:<<ваш_пароль>>}
   ```

3. **Используй файл `database.sql` для инициализации данных:**  
   В этом файле находятся команды для вставки начальных данных в твою базу данных:

   ```sql
   INSERT INTO authors (name, description) VALUES ('Автор 1', 'Описание автора 1');
   INSERT INTO authors (name, description) VALUES ('Автор 2', 'Описание автора 2');
   INSERT INTO authors (name, description) VALUES ('Автор 3', 'Описание автора 3');

   INSERT INTO books (name, author_id) VALUES ('Книга 1', 1);
   INSERT INTO books (name, author_id) VALUES ('Книга 2', 1);
   INSERT INTO books (name, author_id) VALUES ('Книга 3', 2);
   INSERT INTO books (name, author_id) VALUES ('Книга 4', 3);
   INSERT INTO books (name, author_id) VALUES ('Книга 5', 3);
   
   INSERT INTO users (username, email) VALUES ('user1', 'user1@example.com');
   INSERT INTO users (username, email) VALUES ('user2', 'user2@example.com');
   INSERT INTO users (username, email) VALUES ('user3', 'user3@example.com');
   
   INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий 1 к книге 1', 1, 1);
   INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий 2 к книге 1', 2, 1);
   INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий к книге 2', 3, 2);
   INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий 1 к книге 3', 1, 3);
   INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий 1 к книге 4', 2, 4);
   ```


4. **Открой браузер и перейди по адресу:
   ```
   http://localhost:8080
   ```

## Тестирование

Как запускать тесты для проекта:
```bash
mvn test
```