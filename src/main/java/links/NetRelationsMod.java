package links;

import java.util.*;


/**
 * @author Ilya Ivannikov (voldores@mail.ru)
 * @version $Id$
 * @since 0.1
 *
 * Адаптация алгоритма Петра Арсентьева под тесты.  Не завершено, сам алгоритм не соотв. ТЗ
 * */

public class NetRelationsMod {

    public static Map<String, Set<String>> analyze(List<List<String>> data) {
        Map<String, Set<String>> list = new HashMap<>(); //сформированные группы
        Map<String, String> backward = new HashMap<>(); //временный лист для формирования группы
        int names = 0;
        for (List<String> group : data) {    //строка из файла
            String name = null;
            for (String el : group) {       //элементы строки
                if (backward.containsKey(el)) {
                    name = backward.get(el);
                    break;
                }
            }
            if (name != null) {
                list.get(name).addAll(group);
                for (String gr : group) {
                    backward.put(gr, name);
                }
            } else {
                name = String.valueOf(names++);
                list.put(name, new HashSet<>());
                list.get(name).addAll(group);
            }
            for (String gr : group) {
                backward.put(gr, name);
            }
        }
        return list;
    }

    public static void main(String[] args) {

        System.out.println(analyze(
                Arrays.asList(
                        Arrays.asList("1", "2", "3"),
                        Arrays.asList("3", "4", "5"),
                        Arrays.asList("5", "6", "7")
                )
        ));

        System.out.println(analyze(
                Arrays.asList(
                        Arrays.asList("1", "2", "3"),
                        Arrays.asList("3", "4", "5"),
                        Arrays.asList("8", "6", "7")
                )
        ));

        System.out.println(analyze(
                Arrays.asList(
                        Arrays.asList("1", "2", "3"),   //группа 1
                        Arrays.asList("3", "4", "5"),   //группа 1
                        Arrays.asList("8", "6", "7"),   //группа 2
                        Arrays.asList("ва", "er", "3"), //группа 1
                        Arrays.asList("fg", "ds", "re"), //группа 3
                        Arrays.asList("8", "6", "uu"),   //группа 4
                        Arrays.asList("jj", "6", "7"),   //группа 4
                        Arrays.asList("ап", "ds", "7"),   //группа 3
                        Arrays.asList("jj", "6", "uu")    //группа 4
                )
        ));
    }
}
