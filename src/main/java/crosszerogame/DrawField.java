package crosszerogame;

/**
 * Crosses - Zeros Game
 *  *
 *  * @author Ivannikov Ilya (voldores@mail.ru)
 *  * @version $Id$
 *  * @since 0.1
 */

public class DrawField {

    private static final String LN = System.lineSeparator();

    public void draw(int size, String[][] table) {
        StringBuilder sb = new StringBuilder();
        for (int vert = 0; vert <= size; vert++) {
            for (int hor = 0; hor <= size; hor++) {
                if (vert == 0) {
                    sb.append(hor);
                    sb.append(" ");
                } else {
                    if (hor == 0) {
                        sb.append(vert);
                    } else {
                        sb.append(" ");
                        if (table[hor - 1][vert - 1] == null) {
                            table[hor - 1][vert - 1] = " ";
                        }
                        sb.append(table[hor - 1][vert - 1]);
                    }
                }
            }
            sb.append(LN);
        }
        System.out.println(sb);
    }
}
