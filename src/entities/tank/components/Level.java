package entities.tank.components;

public enum Level {
    PRIVATE,
    CORPORAL,
    CAPTAIN,
    MAJOR,
    COLONEL;

    public static Level parseLevel(String level) {
        level = level.toUpperCase();

        return switch (level) {
            case "CORPORAL" -> CORPORAL;
            case "CAPTAIN" -> CAPTAIN;
            case "MAJOR" -> MAJOR;
            case "COLONEL" -> COLONEL;
            default -> PRIVATE;
        };
    }
}