public enum Direction {
    NORTH(3),
    SOUTH(0),
    EAST(2),
    WEST(1);

    private final int frameLineNumber;

    Direction(int frameLineNumber) {
        this.frameLineNumber = frameLineNumber;
    }

    public int getFrameLineNumber() {
        return frameLineNumber;
    }
}