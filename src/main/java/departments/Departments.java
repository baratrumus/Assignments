package departments;

import java.util.*;

/*
В организации было решено ввести справочник подразделений. Коды подразделений представлены в виде массива строк вида:
“K1\SK1” “K1\SK2” “K1\SK1\SSK1” “K1\SK1\SSK2” “K2” “K2\SK1\SSK1” “K2\SK1\SSK2”
, где каждая строка имеет следующую структуру: каждая строка включает в себя код данного подразделения,
а также все коды подразделений, которые включают в свою структуру данное подразделение
(к примеру департамент K1 включает в себя службу SK1, включающую в себя отдел SSK1). Подразделения в одной строке
разделены знаком “\”. Возможны случаи, когда в массиве отсутствуют строки с кодом верхнеуровнего подразделения
(в показанном выше массиве есть строки с подразделением K1, но данный код подразделения не представлен
 отдельной строкой “K1”, аналогичный случай с кодом K2\SK1), в таком случае необходимо добавить строку с кодом
  данного подразделения и учитывать ее при сортировке.
Реализовать возможность сортировки массива кодов подразделений по возрастанию и убыванию, при которых
сохранялась бы иерархическая структура (показано далее в примерах сортировки), т.к. отсортированный массив
используется для отображения категорий пользователю. Отсортированный тестовый массив должен иметь вид:
Сортировка по возрастанию:
“K1”
“K1\SK1”
“K1\SK1\SSK1”
“K1\SK1\SSK2”
“K1\SK2”
“K2”
“K2\SK1”
“K2\SK1\SSK1”
“K2\SK1\SSK2”
 */

public class Departments {

    public static final class Org implements Comparable<Org> {
        private final List<String> deps;

        public Org(List<String> deps) {
            this.deps = deps;
        }

        @Override
        public int compareTo(Org o) {
            int result = 0;
            int size = (this.deps.size() > o.deps.size()) ? o.deps.size() : this.deps.size(); //берем меньший размер
            for (int i = 0; i < size; i++) {
                 result = this.deps.get(i).compareTo(o.deps.get(i));
                 if (result != 0) {
                     break;
                 }
            }
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Org org = (Org) o;
            return Objects.equals(this.deps, org.deps);
        }

        @Override
        public int hashCode() {
            return Objects.hash(deps);
        }

        @Override
        public String toString() {
            return deps.toString();
        }
    }

    public List<Org> convert(List<String> depsList) {
        List<Departments.Org> result = new ArrayList<>();
        Departments.Org tmpOrg;
        String[] strArr;
        List<String> strTemp = new ArrayList<>();
        for (String str : depsList) {
            strArr = str.split("/");
            strTemp.clear();
            for (int i = 0; i < strArr.length; i++) {
                strTemp.add(strArr[i]);
                tmpOrg = new Departments.Org(List.copyOf(strTemp));
                if (!checkIfOrgAlreadyExist(result, tmpOrg)) {
                    result.add(tmpOrg);
                }
            }
        }
        return result;
    }

    /**
     *
     * @param result список объектов Org
     * @param o объект Org
     * @return true если объект уже в списке
     */
    private  boolean checkIfOrgAlreadyExist(List<Departments.Org> result, Org o) {
        boolean res = false;
        for (Org org : result) {
            if (o.equals(org)) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Сортировка по возрастанию через Comparable CompareTo
     */
    public List<Org> sortAsc(List<Org> orgs) {
        Collections.sort(orgs);
        return orgs;
    }

    /**
     * Сортировка по убыванию через comparator
     */
    public List<Org> sortDesc(List<Org> orgs) {
        orgs.sort(
                new Comparator<Org>() {
                    @Override
                    public int compare(Org o1, Org o2) {
                        int result = 0;
                        int size = (o1.deps.size() > o2.deps.size()) ? o2.deps.size() : o1.deps.size();
                        for (int i = 0; i < size; i++) {
                            result = o1.deps.get(i).compareTo(o2.deps.get(i));
                            if (result != 0) {
                                break;
                            }
                        }
                        return result * -1;

                    }
                }
         );
        return orgs;
    }
}
