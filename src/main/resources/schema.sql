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
    EMAIL    CHARACTER VARYING(50) not null
        unique,
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