package seaBattle.game.game;

public enum ShutResult {
    SHUT_MISSED("Miss"),
    SHUT_INJURED("Hit"),
    SHUT_KILLED("Hit:Sink");
    private final String result;
    ShutResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
