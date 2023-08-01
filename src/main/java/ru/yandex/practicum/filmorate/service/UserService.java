package ru.yandex.practicum.filmorate.service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserService {

    void putToFriends(int id, int friendsId) throws IncorrectValuesException;

    void unfriending(int id, int friendsId) throws IncorrectValuesException;

    List<User> listOfMutualFriends(int id, int friendsId) throws IncorrectValuesException;

    List<User> getFriendsList(int id) throws IncorrectValuesException;

    List<User> getUsers();

    User postUser(User user) throws ValidationException;

    User putUser(User user) throws ValidationException, IncorrectValuesException;

    User getUserById(int id) throws IncorrectValuesException;
}
