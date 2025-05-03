package entities.user;

import entities.user.components.Inventory;

public class User {
    public Inventory inventory;

    private String username;
    private String password;
    private String currentTankId;
    private int gamesWon;
    private int gamesPlayed;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setCurrentTankId(String tankId) {
        this.currentTankId = tankId;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getUsername() {
        return username;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public static boolean login() {
        // TODO implement login functionality

        return true;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        User user = (User) obj;

        return username.equals(user.username) && password.equals(user.password);
    }
}
