package wrrv.quizquest;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        // Compare players based on their scores
        return player2.getPlayerScore() - player1.getPlayerScore();
        // To sort in ascending order, swap player1 and player2.
    }
}
