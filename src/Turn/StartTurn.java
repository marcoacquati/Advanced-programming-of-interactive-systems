package Turn;

public class StartTurn implements TurnState{
    private final Turn turn;

    public StartTurn(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void observe() {
        System.out.println("Turn has started");
    }

    @Override
    public void onEnterState() {
        System.out.println("Turn is starting...");
    }
}
