# java-filmorate
Template repository for Filmorate project.

## Filmorate project ER-diagram
![DbDiogram](database.png)

## Приложение умеет

- Создавать пользователей, добавлять фильмы и хранить их в Базе Данных
- Добавлять других пользователей в друзья
- Оценивать фильмы
- Получать списки пользователей и фильмов
- Получать списки общих друзей
- Получать данные отдельных пользователей и фильмов

## Примеры запросов к БД

- Запрос на создание записи фильма в БД
```sql
INSERT INTO FILM(name, description, release_date, duration, mpa_id, rating) VALUES (?, ?, ?, ?, ?, ?)
 ```
- Запрос на создание записи пользователя в БД
```sql
INSERT INTO Users(name, email, birthday, login) VALUES (?, ?, ?, ?)
 ```
- Запрос на добавление пользователя в друзья по его ID
```sql
INSERT INTO FRIENDSHIP(user_id, friend_id) VALUES(?, ?)
 ```
- Запрос на обновление пользователя по его идентификатору
```sql
UPDATE USERS SET NAME=?, EMAIL=?, LOGIN=?, BIRTHDAY=? WHERE ID=?
 ```

