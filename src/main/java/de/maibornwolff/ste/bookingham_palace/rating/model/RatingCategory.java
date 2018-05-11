package de.maibornwolff.ste.bookingham_palace.rating.model;

/**
 * RatingCategory enum
 */
public enum RatingCategory {
    TOP(5),
    GOOD(4),
    AVERAGE(3),
    POOR(2),
    HELL(1);

    private int points;

    RatingCategory(int points) {
        this.points = points;
    }

    public int getPoints(){
        return points;
    }
}
