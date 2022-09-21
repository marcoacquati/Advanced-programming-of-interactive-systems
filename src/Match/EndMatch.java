package Match;

public class EndMatch implements MatchState {

    private final Match match;

    public EndMatch(Match match) {
        this.match = match;
    }

    @Override
    public void observe() {
        System.out.println("Match ended");
    }

    @Override
    public void onEnterState() {
        System.out.println("match is ending");
    }
}
