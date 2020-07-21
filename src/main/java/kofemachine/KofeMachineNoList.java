package kofemachine;

import java.util.Arrays;

public class KofeMachineNoList {
    private final int value;
    private final int price;

    KofeMachineNoList(int value, int price) {
        this.value = value;
        this.price = price;
    }

    public int[] changes1() {
        int rest = this.value - this.price;
        int[] rt = new int[2 * rest / 10 + 2];
        int[] monets = new int[]{10, 5, 2, 1};
        int i = 0;
        for (int mon: monets) {
            while (rest >= mon) {
                rest -= mon;
                rt[i] = mon;
                i++;
            }
        }
        return Arrays.copyOf(rt, i);
    }

    public static void main(String[] args) {
        KofeMachineNoList km = new KofeMachineNoList(999, 22);
        System.out.println(Arrays.toString(km.changes1()));
    }
}
