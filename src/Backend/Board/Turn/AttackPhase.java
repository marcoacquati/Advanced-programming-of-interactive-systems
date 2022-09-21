package Backend.Board.Turn;

public class AttackPhase implements TurnState{
    private final Turn turn;

    public AttackPhase(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void observe() {
        System.out.println("Attack has started");
    }

    @Override
    public void onEnterState() {
        System.out.println("Attack is starting...");
    }
}
