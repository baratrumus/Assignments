package crosszerogame;

import org.junit.Test;
import static org.junit.Assert.*;

public class LogicTest {
    Logic logic = new Logic();

    @Test
    public void whenUserHaveDiagonalHeWins() {
        String[][] moveTable = new String[][]{{"", "", "X"}, {"", "X", ""}, {"X", "", ""}};
        Boolean result = logic.isWin(moveTable, "X", 1, 1);
        assertTrue(result);
    }

    @Test
    public void whenUserHaveHorizontalHeWins() {
        String[][] moveTable = new String[][]{{"X", "X", "X"}, {"", "", ""}, {"", "", ""}};
        Boolean result = logic.isWin(moveTable, "X", 0, 0);
        assertTrue(result);
    }

    @Test
    public void whenUserHaveVerticalHeWins() {
        String[][] moveTable = new String[][]{{"", "", "X"}, {"", "0", "X"}, {"0", "0", "X"}};
        Boolean result = logic.isWin(moveTable, "X", 2, 2);
        assertTrue(result);
    }

    @Test
    public void whenFieldIsBizyItsTrue() {
        String[][] moveTable = new String[][]{{"", "", "X"}, {"", "0", "X"}, {"0", "0", "X"}};
        Boolean result = logic.checkIfBizy(moveTable, 0, 2);
        assertTrue(result);
    }

    @Test
    public void whenfieldIsFullItsTrue() {
        String[][] moveTable = new String[][]{{"0", "0", "X"}, {"0", "0", "X"}, {"0", "0", "X"}};
        Boolean result = logic.fieldIsFull(moveTable);
        assertTrue(result);
    }
}