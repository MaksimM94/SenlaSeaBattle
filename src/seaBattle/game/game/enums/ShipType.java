package seaBattle.game.game.enums;

public enum ShipType {
    SIX_CELL(6),
    FIVE_CELL(5),
    FOUR_CELL(4),
    THREE_CELL(3),
    TWO_CELL(2),
    ONE_CELL(1);
    private final int value;
    ShipType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
