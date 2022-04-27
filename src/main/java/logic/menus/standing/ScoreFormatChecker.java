package logic.menus.standing;

public class ScoreFormatChecker {
    public static boolean isInValidRange(Double score) {
        boolean isEqualOrGreaterThanZero = score >= 0;
        boolean isEqualOrSmallerThanTwenty = score <= 20;
        return isEqualOrGreaterThanZero && isEqualOrSmallerThanTwenty;
    }
}
