package lift;

public class MoveLiftToStage  implements Runnable {
    private Lift lift;
    private int moveToStage;
    private long oneStageTime;
    private int liftOnStage;
    private boolean doorsClosed;
    private boolean youInLift;


    public MoveLiftToStage(Lift lift, int stage, int liftOnStage, boolean youInLift, boolean doorsClosed) {
        this.lift = lift;
        this.moveToStage = stage;
        this.oneStageTime = (long) (1000 * lift.getStageHeight() / lift.getLiftSpeed());
        this.liftOnStage = liftOnStage;
        this.youInLift = youInLift;
        this.doorsClosed = doorsClosed;
    }

    @Override
    public void run() {
        System.out.println();
        System.out.println(String.format("Lift is on the %s stage", liftOnStage));
        if (youInLift) {
            doorOperation();
        }
        synchronized (lift) {
            do {
                try {
                    if (liftOnStage != moveToStage) {
                        liftOnStage = (liftOnStage < moveToStage) ? liftOnStage + 1 : liftOnStage - 1;
                        lift.setLiftOnStage(liftOnStage);
                        Thread.currentThread().sleep(oneStageTime);
                        System.out.println(String.format("Lift is on the %s stage", liftOnStage));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            } while (liftOnStage != moveToStage);
        }
        doorOperation();
    }


    private void doorOperation() {
        synchronized (lift) {
            try {
                if (doorsClosed) {
                    System.out.printf("Doors are opening...");
                    Thread.currentThread().sleep(lift.getOpenCloseTime() * 1000);
                    System.out.println(" Doors opened");
                    doorsClosed = false;
                    lift.setDoorsClosed(doorsClosed);

                } else {
                    System.out.printf("Doors are closing...");
                    Thread.currentThread().sleep(lift.getOpenCloseTime() * 1000);
                    System.out.println(" Doors closed");
                    doorsClosed = true;
                    lift.setDoorsClosed(doorsClosed);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
