MERGE INTO GENRE(id, name) VALUES (default, 'Комедия'),
(default, 'Драма'),
(default, 'Мультфильм'),
(default, 'Триллер'),
(default, 'Документальный'),
(default, 'Боевик');
MERGE INTO MPA(id, name) VALUES (default, 'G'),
(default, 'PG'),
(default, 'PG-13'),
(default, 'R'),
(default, 'NC-17');

