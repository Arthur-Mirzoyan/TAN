package utils;

import entities.user.User;
import entities.user.components.UserData;
import network.Client;
import network.Server;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code PanelListener} interface defines navigation behavior between
 * panels in the application. It acts as a controller to switch UI screens
 * depending on user interaction or application state.
 */
public interface PanelListener {
    /**
     * Switches the active panel to the one specified by {@code panelName}.
     *
     * @param panelName the target panel to display
     */
    void goTo(Panels panelName);

    void goTo(Panels panelName, User user);

    /**
     * Switches to the game panel, initializing it with full multiplayer context.
     *
     * @param user            the current user
     * @param currentUserData the user's game-specific data
     * @param client          the connected network client for communication
     * @param users           the list of other users in the session
     */
    void goToGame(User user, UserData currentUserData, Client client, CopyOnWriteArrayList<UserData> users, Server server);
}
