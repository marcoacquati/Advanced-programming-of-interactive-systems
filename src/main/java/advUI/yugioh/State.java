package advUI.yugioh;

public enum State {
    Draw, Position, Attack, CheckPoints, End;
    private static final State[] states = State.values();
    private static State getState(int i){
        return State.states[i];
    }
}
