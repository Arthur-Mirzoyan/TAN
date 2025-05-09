package utils;

import entities.user.User;
import entities.user.components.UserData;
import network.Client;

import java.util.concurrent.CopyOnWriteArrayList;

public interface PanelListener {
    void goTo(Panels panelName);

    void goTo(Panels panelName, User user);

    void goToGame(User user, UserData currentUserData, Client client, CopyOnWriteArrayList<UserData> users);
}
