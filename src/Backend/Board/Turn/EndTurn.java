package Backend.Board.Turn;

public class EndTurn implements TurnState{
    private final Turn turn;

    public EndTurn(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void observe() {
        System.out.println("Backend.Board.Turn has ended");
    }

    @Override
    public void onEnterState() {
        System.out.println("Backend.Board.Turn is ending...");
    }
}
