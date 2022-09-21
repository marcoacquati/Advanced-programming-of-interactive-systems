package Match;

public class CheckLifePoints implements MatchState {

    private final Match match;

    public CheckLifePoints(Match match) {
        this.match = match;
    }

    @Override
    public void observe() {
        System.out.println("Life points checked");
    }

    @Override
    public void onEnterState() {
        System.out.println("Checking life points");
    }
}
