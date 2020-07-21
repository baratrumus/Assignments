package links;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringJoiner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.*;


public class LinksTest {


    @Test
    public void whenGroupStringsItsOk() {
        Links links = new Links();
        try (PrintWriter out = new PrintWriter(new FileOutputStream("source.csv"))) {
            out.println("200;400;tyty");
            out.println("100;400;uuio");
            out.println("100;frr;fgbfg");
            out.println("300;bbb;ffewxxx");
            out.println("300;700;ll");
            out.println("546;700;mjkj");
            out.println("555;900;kkk");
            out.println("555;2200;2rt");
            out.println("111;2233;278");
        } catch (Exception e) {
            e.printStackTrace();
        }
        links.load("source.csv");
        links.makeGroups();
        links.groups.sort(new Comparator<ArrayList<Links.TripleString>>() {
            public int compare(ArrayList<Links.TripleString> ar1, ArrayList<Links.TripleString> ar2) {
                return  ar2.size() - ar1.size();
            }
        });
        assertThat(links.groupCount(), is(3));
    }

}
