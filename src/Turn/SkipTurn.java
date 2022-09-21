package Turn;

public class SkipTurn implements TurnState{
    private final Turn turn;

    public SkipTurn(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void observe() {
        System.out.println("Turn has been skipped");
    }

    @Override
    public void onEnterState() {
        System.out.println("Skipping turn...");
    }
}
