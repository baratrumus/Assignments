package lift;

/**
 * Предлагаем вам решить тестовое задание — написать программу «симулятор лифта».
 * Программа запускается из командной строки, в качестве параметров задается:
 *     кол-во этажей в подъезде — N (от 5 до 20);
 *     высота одного этажа;
 *     скорость лифта при движении в метрах в секунду (ускорением пренебрегаем, считаем,
 *     что когда лифт едет — он сразу едет с определенной скоростью);
 *     время между открытием и закрытием дверей.
 * После запуска программа должна постоянно ожидать ввода от пользователя и выводить действия
 * лифта в реальном времени. События, которые нужно выводить:
 *     лифт проезжает некоторый этаж;
 *     лифт открыл двери;
 *     лифт закрыл двери.
 * Возможный ввод пользователя:
 *     вызов лифта на этаж из подъезда;
 *     нажать на кнопку этажа внутри лифта.
 * Считаем, что пользователь не может помешать лифту закрыть двери.
 */
public class Lift {
    private final int stages;
    private final float stageHeight;
    private final float liftSpeed;
    private final int openCloseTime;
    private int liftOnStage;
    private boolean youInLift;
    private boolean doorsClosed;


    public Lift(int stages, float stageHeight, float liftSpeed, int openCloseTime) {
        this.stages = stages;
        this.stageHeight = stageHeight;
        this.liftSpeed = liftSpeed;
        this.openCloseTime = openCloseTime;
        this.liftOnStage = 1;
        this.youInLift = false;
        this.doorsClosed = true;
    }

    public void setLiftOnStage(int liftOnStage) {
        this.liftOnStage = liftOnStage;
    }

    public void setDoorsClosed(boolean doorsClosed) {
        this.doorsClosed = doorsClosed;
    }

    public float getStageHeight() {
        return stageHeight;
    }

    public float getLiftSpeed() {
        return liftSpeed;
    }

    public int getOpenCloseTime() {
        return openCloseTime;
    }

    public void userAction(Lift lift) {
        ConsoleInput input = new ConsoleInput();
        String inp = "";
        System.out.println("To call lift to your stage print c+stage (example c4)");
        System.out.println("To move in lift to stage print m+stage (example m1)");
        System.out.println("To exit program print q");

        while (!"q".equals(inp)) {
            inp = input.ask("Your input:");
            if (inp.contains("c") && !youInLift) {
                int yourStage = Character.getNumericValue(inp.charAt(1));
                Thread callToStage = new Thread(new MoveLiftToStage(lift, yourStage, liftOnStage, youInLift, doorsClosed));
                callToStage.start();
            } else if (inp.contains("m")) {
                youInLift = true;
                System.out.println(String.format("You are in lift on the %s stage", liftOnStage));
                int toStage = Character.getNumericValue(inp.charAt(1));
                Thread moveToStage = new Thread(new MoveLiftToStage(lift, toStage, liftOnStage, youInLift, doorsClosed));
                moveToStage.start();
            } else if (!inp.contains("q")) {
                System.out.println("Wrong input, make correct input.");
            }
        }
    }


    public static void main(String[] args) {
        int stages;
        float stageHeight;
        float liftSpeed;
        int openCloseTime;
        if (args.length != 0) {
            stages = Integer.parseInt(args[0]);
            stageHeight = Float.parseFloat(args[1]);
            liftSpeed = Float.parseFloat(args[2]);
            openCloseTime = Integer.parseInt(args[3]);
        } else {
            stages = 6;
            stageHeight = 3F;
            liftSpeed = 1.5F;
            openCloseTime = 3;
        }
        Lift lift = new Lift(stages, stageHeight, liftSpeed, openCloseTime);
        lift.userAction(lift);
    }
}
