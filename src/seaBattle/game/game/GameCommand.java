package seaBattle.game.game;

public enum GameCommand {
    HOST("host"),
    CONNECT("connect");
    private final String name;
    GameCommand(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
