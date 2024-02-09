package seaBattle.game.game;

public enum GameMode {
    SINGLE_MODE(1),
    MULTIPLAYER_MODE(2);
    private final int value;
    GameMode(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
