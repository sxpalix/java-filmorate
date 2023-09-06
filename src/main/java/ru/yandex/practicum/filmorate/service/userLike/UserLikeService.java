package ru.yandex.practicum.filmorate.service.userLike;

import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserLikeService {
    User putToFriends(int id, int friendsId) throws IncorrectValuesException;

    User unfriending(int id, int friendsId) throws IncorrectValuesException;

    List<User> listOfMutualFriends(int id, int friendsId) throws IncorrectValuesException;

    List<User> getFriendsList(int id) throws IncorrectValuesException;
}
