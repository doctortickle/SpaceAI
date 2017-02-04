package spaceai;

/**
 *
 * @author Dylan Russell
 */
public enum Team {
    /**
     * Team A.
     */
    A,
    /**
     * Team B.
     */
    B,
    /**
     * Neutral objects.
     */
    NEUTRAL;

    /**
     * Determines the team that is the opponent of this team.
     *
     * @return the opponent of this team.
     *
     * 
     */
    public Team opponent() {
        switch (this) {
            case A:
                return B;
            case B:
                return A;
            default:
                return NEUTRAL;
        }
    }

    /**
     * Returns whether a ship or structure of this team is a player-controlled entity
     * (team A or team B).
     *
     * @return true a ship or structure of this team is player-controlled; false otherwise.
     *
     * 
     */
    public boolean isPlayer() {
        return this == A || this == B;
    }
}
   
