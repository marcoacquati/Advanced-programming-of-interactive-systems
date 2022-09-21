package Backend.Board.Match;

public class StartMatch implements MatchState {

    private final Match match;

    public StartMatch(Match match) {
        this.match = match;
    }

    @Override
    public void observe() {
        System.out.println("Backend.Board.Match has started");
    }

    @Override
    public void onEnterState() {
        System.out.println("Backend.Board.Match is starting...");
    }
}
