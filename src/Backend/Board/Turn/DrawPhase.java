package Backend.Board.Turn;

public class DrawPhase implements TurnState{
    private final Turn turn;

    public DrawPhase(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void observe() {
        System.out.println("Draw phase");
    }

    @Override
    public void onEnterState() {
        System.out.println("Starting draw phase");
    }
}
