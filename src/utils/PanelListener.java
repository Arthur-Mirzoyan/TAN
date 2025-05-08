package utils;

import entities.user.User;

public interface PanelListener {
    void goTo(Panels panelName);

    void goTo(Panels panelName, User user);
}
