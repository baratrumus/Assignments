package crosszerogame;

/**
 *
 *  * Crosses - Zeros Game
 *  *
 *  * @author Ivannikov Ilya (voldores@mail.ru)
 *  * @version $Id$
 *  * @since 0.1
 *
 *
 Создание игры крестики нолики.
 Вывод данных в консоль в псевдографики.
 Пользователь начинает игру. Вводи координаты точки.
 Предусмотреть, чтобы компьютер мог начать игру.
 Предусмотреть увеличение поля. По умолчанию используется поле 3 на 3.
 Предусмотреть усложнение логики игры. Выигрывает тот кто соберет 5 подряд
 Пользователь ходит Х, компьютер 0
 */

public class StartGame {
    private static final String LN = System.lineSeparator();
    public static final String ANSI_RED = "\u001B[31m";
    private Boolean exit = false;
    private Boolean switchMove = true;
    private String userBegins;
    private String gameType;
    private int fieldSize;
    private DrawField field;
    private Logic logic;
    private ConsoleInput input;
    private String[][] moveTable;

    public StartGame() {
        this.field = new DrawField();
        this.logic = new Logic();
        this.input =  new ConsoleInput();
    }

    public void init() {
        this.gameType = input.ask("Choose game type, comp vs comp press \"c\", user vs comp press \"u\":");
        if ("u".equals(gameType)) {
            this.userBegins = input.ask(
                    "Choose who begins, for user's begin press \"1\", for computer's begin press \"0\":");
        }
        this.fieldSize = Integer.parseInt(input.ask("If field size is more than 5, win always is 5 in line. "
                + "Input field size:"));
        this.moveTable = new String[fieldSize][fieldSize];
        field.draw(fieldSize, moveTable);
        do {
            if ("u".equals(gameType)) {
                if ("1".equals(userBegins)) {
                    makeUserMove();
                } else if (!logic.fieldIsFull(moveTable)) {
                    makeCompMove("0");
                    userBegins = "1";
                }
            } else  if ("c".equals(gameType)) {
                if (switchMove) {
                    makeCompMove("X");
                    switchMove = false;
                } else if (!logic.fieldIsFull(moveTable)) {
                    makeCompMove("0");
                    switchMove = true;
                }
            }
            field.draw(fieldSize, moveTable);

            if (logic.fieldIsFull(moveTable)) {
                this.exit = true;
                System.out.println(LN + "Field is full. Nobody wins");
            }
        } while (!this.exit);
    }


    private void makeCompMove(String sign) {
        String[] compMove = logic.getCompMove(moveTable);
        int compMoveX = Integer.parseInt(compMove[0]);
        int compMoveY = Integer.parseInt(compMove[1]);

        moveTable[compMoveX][compMoveY] = sign;
        System.out.println(String.format("Computer \"%s\" moves [%d,%d]" + LN, sign,
                compMoveX + 1, compMoveY + 1));
        if (logic.isWin(moveTable, sign, compMoveX, compMoveY)) {   //computer win check
            this.exit = true;
            System.out.println(String.format(ANSI_RED + "Computer \"%s\" wins" + ANSI_RED, sign));
        }
    }


    private void makeUserMove() {
        String inp = input.ask("Input coordinates of your move, "
                + "two comma-separated didgits (x - first, y - second), \"q\" to quit:");
        if ("q".equalsIgnoreCase(inp)) {
            this.exit = true;
        } else if (!inp.isBlank()) {
            String[] userMove = getUserMove(moveTable, inp);
            int userMoveX = Integer.parseInt(userMove[0]) - 1;
            int userMoveY = Integer.parseInt(userMove[1]) - 1;
            moveTable[userMoveX][userMoveY] = "X";
            if (logic.isWin(moveTable, "X", userMoveX, userMoveY)) {   //user win check
                this.exit = true;
                System.out.println(ANSI_RED + "User wins" + ANSI_RED);
            }
        }
        userBegins = "0";
    }

    /**
     * check if cell is bizy
     */
    private String[] getUserMove(String[][] moveTable, String inp) {
        String[] userMove = inp.split(",[ ]*");
        int userMoveX = Integer.parseInt(userMove[0]);
        int userMoveY = Integer.parseInt(userMove[1]);

        while (logic.checkIfBizy(moveTable, userMoveX - 1, userMoveY - 1)) {
            inp = input.ask("This cell is bizy, input another coordinates:");
            userMove = inp.split(",[ ]*");
            userMoveX = Integer.parseInt(userMove[0]);
            userMoveY = Integer.parseInt(userMove[1]);
        }
        return userMove;
    }

    public static void main(String[] args) {
        StartGame sg = new StartGame();
        sg.init();
    }

}
