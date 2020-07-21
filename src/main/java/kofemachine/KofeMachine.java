package kofemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Надо реализовать метод выдачи сдачи для автомата.
 * int[] changes(int value, int price)
 * value = купюра. 50 100 и тд.
 * price = цена кофе
 * в автомате монеты наминалом 1 2 5 10
 * Пример. Мы засунули 50 рублей выбрали кофе за 35. Сдачу автомат должен дать 15 рублей
 * Алгоритм должен вернуть наименьшее количество монет.
 * Метод вернет массив {10, 5} = 15 рублей
 */
public class KofeMachine {
    private final int value;
    private final int price;

    KofeMachine(int value, int price) {
        this.value = value;
        this.price = price;
    }

    public Integer[] changes() {
        List<Integer> rt = new ArrayList<Integer>();
        int[] monets = new int[]{10, 5, 2, 1};
        int rest = this.value - this.price;
        for (int mon: monets) {
            while (rest >= mon) {
                rest -= mon;
                rt.add(mon);
            }
        }
        return rt.toArray(new Integer[rt.size()]);
    }

    public static void main(String[] args) {
        KofeMachine km = new KofeMachine(50, 33);
        System.out.println(Arrays.toString(km.changes()));
    }
}

