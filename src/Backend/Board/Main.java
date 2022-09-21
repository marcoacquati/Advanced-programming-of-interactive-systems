package Backend.Board;

import Backend.Board.Match.Match;

public class Main {
    public static void main(String[] args){
        Match match = new Match();

        match.observe();
        match.timePasses();
        match.observe();
        match.timePasses();
        match.observe();
        match.timePasses();
        match.observe();
        match.timePasses();
        match.observe();
        match.timePasses();
    }
}
