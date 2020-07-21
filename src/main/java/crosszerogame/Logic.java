package crosszerogame;

import java.util.Random;

/**
 * Crosses - Zeros Game
 *  *
 *  * @author Ivannikov Ilya (voldores@mail.ru)
 *  * @version $Id$
 *  * @since 0.1
 */

public class Logic {

    public String computerMove(String[][] moveTable) {
        String compMove;
        Random r = new Random();
        int x = r.nextInt(moveTable.length);
        int y = r.nextInt(moveTable.length);
        while (!(moveTable[x][y].isBlank())) {
            x = r.nextInt(moveTable.length);
            y = r.nextInt(moveTable.length);
        }
        compMove = x + "," + y;
        return compMove;
    }

    /**
     *
     * @param moveTable table of moves
     * @param crossZero cross or zero
     * winAmount The quantity of crosses or zeros in line, need to win(depends on size of field)
     * @return
     */
    public boolean isWin(String[][] moveTable, String crossZero, int lastMoveX, int lastMoveY) {
        int winAmount =  winAmount(moveTable.length);
        boolean ret = false;
        int realAmountHor = 0;
        int realAmountVert = 0;
        int realAmountDiagLeft = 0;
        int realAmountDiagRight = 0;
        boolean gotShiftDiagLeft = true;
        boolean gotShiftDiagRight = true;
        int[] leftDiagBegin = getDiagBegin(moveTable, lastMoveX, lastMoveY, -1, -1);
        int leftDiagBeginX = leftDiagBegin[0];
        int leftDiagBeginY = leftDiagBegin[1];
        int[] rightDiagBegin = getDiagBegin(moveTable, lastMoveX, lastMoveY, -1, 1);
        int rightDiagBeginX = rightDiagBegin[0];
        int rightDiagBeginY = rightDiagBegin[1];

        for (int i = 0; i < moveTable.length; i++) {
            if (crossZero.equals(moveTable[i][lastMoveY])) { //check hor
                realAmountHor++;
            } else {
                realAmountHor = 0;
            }
            if (realAmountHor == winAmount) {
                ret = true;
                break;
            }

            if (crossZero.equals(moveTable[lastMoveX][i])) { // check vert
                realAmountVert++;
            } else {
                realAmountVert = 0;
            }
            if (realAmountVert == winAmount) {
                ret = true;
                break;
            }

            if (crossZero.equals(moveTable[leftDiagBeginX][leftDiagBeginY]) && gotShiftDiagLeft) { //check diag from left up to right down
                realAmountDiagLeft++;
            } else {
                realAmountDiagLeft = 0;
            }
            if (!(leftDiagBeginX == (moveTable.length - 1) || (leftDiagBeginY == (moveTable.length - 1)))) {
                leftDiagBeginX++;
                leftDiagBeginY++;
                gotShiftDiagLeft = true;
            } else {
                gotShiftDiagLeft = false;
            }
            if (realAmountDiagLeft == winAmount) {
                ret = true;
                break;
            }

            if (crossZero.equals(moveTable[rightDiagBeginX][rightDiagBeginY]) && gotShiftDiagRight) { //check diag from left down to right up
                realAmountDiagRight++;
            } else {
                realAmountDiagRight = 0;
            }
            if (!(rightDiagBeginX == (moveTable.length - 1) || (rightDiagBeginY == 0))) {
                rightDiagBeginX++;
                rightDiagBeginY--;
                gotShiftDiagRight = true;
            } else {
                gotShiftDiagRight = false;
            }
            if (realAmountDiagRight == winAmount) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * @return begining point of check win line
     */
    private int[] getDiagBegin(String[][] moveTable, int lastMoveX, int lastMoveY, int deltaX, int deltaY) {
        if (deltaY == 1) {
            while (!(lastMoveX == 0 || lastMoveY == (moveTable.length - 1))) { //go left down
                lastMoveX += deltaX;
                lastMoveY += deltaY;
            }
        } else {
            while (!(lastMoveX == 0 || lastMoveY == 0)) {  //go left up
                lastMoveX += deltaX;
                lastMoveY += deltaY;
            }
        }
        return new int[]{lastMoveX, lastMoveY};
    }

    /**
     * @param fieldSize
     * @return winAmount The quantity of crosses or zeros in line, need to win(depends on size of field)
     */
    private int winAmount(int fieldSize) {
        int ret;
        if (fieldSize < 5) {
            ret = fieldSize;
        } else {
            ret = 5;
        }
        return ret;
    }

    public boolean checkIfBizy(String[][] moveTable, int x, int y) {
       return (moveTable[x][y].isBlank()) ? false : true;
    }


    public String[] getCompMove(String[][] moveTable) {
        String[] compMove = computerMove(moveTable).split(",");
        int compMoveX = Integer.parseInt(compMove[0]);
        int compMoveY = Integer.parseInt(compMove[1]);

        while (checkIfBizy(moveTable, compMoveX, compMoveY)) {
            compMove = computerMove(moveTable).split(",");
            compMoveX = Integer.parseInt(compMove[0]);
            compMoveY = Integer.parseInt(compMove[1]);
        }
        return compMove;
    }


    public boolean fieldIsFull(String[][] moveTable) {
        boolean ret = true;
        for (int vert = 0; vert < moveTable.length; vert++) {
            for (int hor = 0; hor < moveTable.length; hor++) {
                if (moveTable[hor][vert].isBlank()) {
                    ret = false;
                }
            }
        }
        return ret;
    }
}

