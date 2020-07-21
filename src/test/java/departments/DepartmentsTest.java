package departments;

import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DepartmentsTest {
    @Test
    public void whenMissed() {
        Departments deps = new Departments();
        List<String> input = Collections.singletonList("k1/sk1");
        List<Departments.Org> expect = Arrays.asList(
                new Departments.Org(Collections.singletonList("k1")),
                new Departments.Org(Arrays.asList("k1", "sk1"))
        );
        List<Departments.Org> result = deps.convert(input);
        assertThat(result, is(expect));
    }

    @Test
    public void whenAsc() {
        Departments deps = new Departments();
        List<String> input = Arrays.asList("k1/sk1", "k2", "k1/sk2", "k1/sk1/ssk1");
        List<Departments.Org> expect = Arrays.asList(
                new Departments.Org(Collections.singletonList("k1")),
                new Departments.Org(Arrays.asList("k1", "sk1")),
                new Departments.Org(Arrays.asList("k1", "sk1", "ssk1")),
                new Departments.Org(Arrays.asList("k1", "sk2")),
                new Departments.Org(Collections.singletonList("k2"))
        );
        List<Departments.Org> result = deps.sortAsc(deps.convert(input));
        assertThat(result, is(expect));
    }

    @Test
    public void whenAsc1() {
        Departments deps = new Departments();
        List<String> input = Arrays.asList("k1/sk1", "k1/sk2", "k1/sk1/ssk1", "k1/sk1/ssk2", "k2", "k2/sk1/ssk1", "k2/sk1/ssk2");
        List<Departments.Org> expect = Arrays.asList(
                new Departments.Org(Collections.singletonList("k1")),
                new Departments.Org(Arrays.asList("k1", "sk1")),
                new Departments.Org(Arrays.asList("k1", "sk1", "ssk1")),
                new Departments.Org(Arrays.asList("k1", "sk1", "ssk2")),
                new Departments.Org(Arrays.asList("k1", "sk2")),
                new Departments.Org(Collections.singletonList("k2")),
                new Departments.Org(Arrays.asList("k2", "sk1")),
                new Departments.Org(Arrays.asList("k2", "sk1", "ssk1")),
                new Departments.Org(Arrays.asList("k2", "sk1", "ssk2"))
        );
        List<Departments.Org> result = deps.sortAsc(deps.convert(input));
        assertThat(result, is(expect));
    }

    @Test
    public void whenDesc() {
        Departments deps = new Departments();
        List<String> input = Arrays.asList("k1/sk1", "k2");
        List<Departments.Org> expect = Arrays.asList(
                new Departments.Org(Collections.singletonList("k2")),
                new Departments.Org(Collections.singletonList("k1")),
                new Departments.Org(Arrays.asList("k1", "sk1"))
        );
        List<Departments.Org> result = deps.sortDesc(deps.convert(input));
        assertThat(result, is(expect));
    }

    @Test
    public void whenDesc1() {
        Departments deps = new Departments();
        List<String> input = Arrays.asList("k1/sk1", "k1/sk2", "k1/sk1/ssk1", "k1/sk1/ssk2", "k2", "k2/sk1/ssk1", "k2/sk1/ssk2");
        List<Departments.Org> expect = Arrays.asList(
                new Departments.Org(Collections.singletonList("k2")),
                new Departments.Org(Arrays.asList("k2", "sk1")),
                new Departments.Org(Arrays.asList("k2", "sk1", "ssk2")),
                new Departments.Org(Arrays.asList("k2", "sk1", "ssk1")),
                new Departments.Org(Collections.singletonList("k1")),
                new Departments.Org(Arrays.asList("k1", "sk2")),
                new Departments.Org(Arrays.asList("k1", "sk1")),
                new Departments.Org(Arrays.asList("k1", "sk1", "ssk2")),
                new Departments.Org(Arrays.asList("k1", "sk1", "ssk1"))
        );
        List<Departments.Org> result = deps.sortDesc(deps.convert(input));
        assertThat(result, is(expect));
    }

}
