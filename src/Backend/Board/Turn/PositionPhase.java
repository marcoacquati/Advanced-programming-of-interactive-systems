package Backend.Board.Turn;

public class PositionPhase implements TurnState{
    private final Turn turn;

    public PositionPhase(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void observe() {
        System.out.println("Positioning has started");
    }

    @Override
    public void onEnterState() {
        System.out.println("Positioning is starting...");
    }
}

