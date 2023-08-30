DROP TABLE IF EXISTS GENRE CASCADE;
DROP TABLE IF EXISTS MPA CASCADE;
DROP TABLE IF EXISTS FILM CASCADE;
DROP TABLE IF EXISTS FILM_GENRES CASCADE;
DROP TABLE IF EXISTS USERS CASCADE;
DROP TABLE IF EXISTS FILM_LIKES CASCADE;
DROP TABLE IF EXISTS FRIENDSHIP CASCADE;
DROP TABLE IF EXISTS DIRECTOR CASCADE;
DROP TABLE IF EXISTS FILM_DIRECTOR CASCADE;

create table IF NOT EXISTS GENRE
(
    ID   INTEGER auto_increment,
    NAME CHARACTER VARYING(50) not null,
    constraint GENRE_PK
        primary key (ID)
);

create table IF NOT EXISTS MPA
(
    ID   INTEGER auto_increment,
    NAME CHARACTER VARYING(50) not null,
    constraint RATING_PK
        primary key (ID)
);

create table IF NOT EXISTS FILM
(
    ID           INTEGER auto_increment,
    NAME         CHARACTER VARYING(50)  not null,
    DESCRIPTION  CHARACTER VARYING(200) not null,
    RELEASE_DATE DATE                   not null,
    DURATION     INTEGER                not null,
    MPA_ID       INTEGER                not null,
    RATING       DOUBLE PRECISION       not null,
    constraint FILM_PK
        primary key (ID),
    constraint "MPA_fk"
        foreign key (MPA_ID) references MPA
);

create table IF NOT EXISTS FILM_GENRES
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint "GENRE_FILM_fk"
        foreign key (FILM_ID) references FILM,
    constraint "GENRE__fk"
        foreign key (GENRE_ID) references GENRE
);

create table IF NOT EXISTS USERS
(
    ID       INTEGER auto_increment,
    NAME     CHARACTER VARYING(50) not null,
    EMAIL    CHARACTER VARYING(50) not null,
    BIRTHDAY DATE                  not null,
    LOGIN    CHARACTER VARYING(50) not null,
    constraint USER_PK
        primary key (ID)
);

create table IF NOT EXISTS FILM_LIKES
(
    FILM_ID INTEGER not null,
    USER_ID INTEGER not null,
    constraint "FILM_LIKES__fk"
        foreign key (FILM_ID) references FILM,
    constraint "USER_LIKES_fk"
        foreign key (USER_ID) references USERS
);

create table IF NOT EXISTS FRIENDSHIP
(
    USER_ID   INT not null,
    FRIEND_ID INT not null,
    constraint "FRIENDS_pk"
        primary key (USER_ID, FRIEND_ID),
    constraint "FRIENDS_USERS_USER_ID_fk"
        foreign key (USER_ID) references USERS
            on delete cascade,
    constraint "FRIENDS_USERS_USER_ID_fk2"
        foreign key (FRIEND_ID) references USERS
            on delete cascade
);

create table IF NOT EXISTS DIRECTOR
(
    ID   INTEGER auto_increment,
    NAME CHARACTER VARYING(50) not null,
    constraint DIRECTOR_PK
        primary key (ID)
);


create table IF NOT EXISTS FILM_DIRECTOR
(
    FILM_ID     INTEGER not null,
    DIRECTOR_ID INTEGER not null,
    constraint "DIRECTOR_FILM_fk"
        foreign key (FILM_ID) references FILM,
    constraint "DIRECTOR_Film_fk"
        foreign key (DIRECTOR_ID) references DIRECTOR
);

create unique index IF not exists EMAIL_UINDEX on USERS(email);
create unique index IF not exists LOGIN_UINDEX on USERS(login);


