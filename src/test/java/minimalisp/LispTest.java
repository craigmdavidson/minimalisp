package minimalisp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class LispTest extends Lisp {

  @Test public void testMakeAList() {
    List<String> letters = new ArrayList<String>();
    letters.add("A");
    letters.add("B");
    letters.add("C");
    letters.add("D");
    assertEquals(letters, list("A", "B", "C", "D"));
  }

  @Test public void testMakeAnArray() {
    assertArrayEquals(new String[] { "A", "B", "C", "D" }, array("A", "B", "C", "D"));
  }

  @Test public void testMakeAnArrayIntoAList() {
    assertEquals(list("A", "B", "C", "D"), list(array("A", "B", "C", "D")));
  }

  @Test public void testMakeAListIntoAnArray() {
    assertArrayEquals(array("A", "B", "C", "D"), array(list("A", "B", "C", "D")));
  }
  
  @Test public void testMakeASet(){
    Set<String> letters = new HashSet<String>();
    letters.add("A");
    letters.add("B");
    letters.add("C");
    letters.add("D");
    assertEquals(letters , set("A", "B", "C", "D"));
  }

  @Test public void testMakeAMapFromAList() {
    Map<String, String> expected = new HashMap<String, String>();
    expected.put("greeting", "Hello");
    expected.put("name", "Joe");
    assertEquals(expected, map(list("greeting", "Hello", "name", "Joe")));
  }

  @Test public void testMakeAMapFromAnArray() {
    Map<String, String> expected = new HashMap<String, String>();
    expected.put("greeting", "Hey");
    expected.put("name", "Joe");
    assertEquals(expected, map(array("greeting", "Hey", "name", "Joe")));
  }

  @Test public void testMakeAMapFromVarargs() {
    Map<String, String> expected = new HashMap<String, String>();
    expected.put("greeting", "Hey");
    expected.put("name", "Joe");
    assertEquals(expected, map("greeting", "Hey", "name", "Joe"));
  }

  @Test public void testMakeATypedMapFromVarargs() {
    Map<String, Integer> intMap = map("a", 1, "b", 2, "c", 3);
    int i = intMap.get("a");
    assertEquals(1, i);
  }

  @Test public void testMakeAMapFromAKeysListAndAValuesList() {
    Map<String, String> expected = new HashMap<String, String>();
    expected.put("greeting", "Hey");
    expected.put("name", "Joe");
    assertEquals(expected, map(list("greeting", "name"), list("Hey", "Joe")));
  }

  @Test public void testMergeMaps(){
    assertEquals(
        map("A", 1, "B", 2, "C", 3, "D", 4),
        merge(map("A", 1, "B", 2), map("C", 3, "D", 4)));
    
    assertEquals(
        map("A", 1, "B", 2, "C", 10, "D", 11),
        merge(map("A", 1, "B", 2, "C", 3), map("C", 10, "D", 11)));

    assertEquals(
        map("C", 3, "D", 11, "A", 1, "B", 2),
        merge(map("C", 10, "D", 11), map("A", 1, "B", 2, "C", 3)));
  }
  
  @Test public void testInvert() {
    assertEquals(map(1, "A", 2, "B"), invert(map("A", 1, "B", 2)));
  }
  
  @Test public void testGet(){
    assertEquals("1", get(map("A", "1", "B", "2", "C", "3"), "A", "4"));
    assertEquals("2", get(map("A", "1", "B", "2", "C", "3"), "B", "4"));    
    assertEquals("4", get(map("A", "1", "B", "2", "C", "3"), "D", "4"));    
  }

  @Test public void testImmutableReverse() {
    List<String> letters = list("A", "B", "C", "D");
    assertEquals(list("D", "C", "B", "A"), reverse(letters));
    assertEquals(list("A", "B", "C", "D"), letters);
  }

  @Test public void testFirst() {
    assertEquals("A", first(list("A", "B", "C", "D")));
    assertNull(first(list()));
    assertNull(first(null));
  }

  @Test public void testLast() {
    assertEquals("D", last(list("A", "B", "C", "D")));
    assertNull(last(list()));
    assertNull(last(null));
  }
  
  @Test public void testHead() {
    assertNull(head(null, 3));
    assertEquals(list(1, 2), head(list(1, 2), 3));
    assertEquals(list(1, 2, 3), head(list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3));
  }
  
  @Test public void testTail() {
    assertEquals(list(8, 9, 10), tail(list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3));
    assertEquals(list(1, 2), tail(list(1, 2), 3));
    assertNull(tail(null, 3));
  }

  @Test public void testRest() {
    assertEquals(list("B", "C", "D"), rest(list("A", "B", "C", "D")));
    assertEquals(list(), rest(list()));
    assertNull(rest(null));
  }

  @Test public void testCompact() {
    assertEquals(list("A", "B", "C", "D"), compact(list("A", null, null, "B", null, "C", null, "D")));
  }

  @Test public void testFlatten() {
    assertEquals(list("A", "B", "C", "D"), flatten(list(list("A", "B"), list("C", "D"))));
  }

  @Test public void testZip() {
    assertEquals(
        list(
            list("A", "B"), 
            list("C", "D")), 
        zip(
            list("A", "C"), 
            list("B", "D")));
  }
  
  @Test public void testExcept() {
    assertEquals(map("B", 2, "C", 3, "D", 4), except(map("A", 1, "B", 2, "C", 3, "D", 4), "A"));
    assertEquals(map("A", 1, "B", 2, "C", 3, "D", 4), except(map("A", 1, "B", 2, "C", 3, "D", 4), "E"));    
    assertEquals(map("B", 2, "C", 3), except(map("A", 1, "B", 2, "C", 3, "D", 4), "A", "D", "E"));
  }
  
  @Test public void testZip_uneven() {
    assertEquals(
        list(
            list("A", "B"), 
            list("C", "D"), 
            list("E", null)), 
        zip(
            list("A", "C", "E"), 
            list("B", "D")));
  }

  @Test public void testDistinct() {
    assertEquals(list("A", "B", "C", "D"), distinct(list("A", "B", "A", "B", "C", "C", "A", "D")));
  }

  @Test public void testMap() {
    assertEquals(list("A", "B", "C", "D"), map(String::toUpperCase, list("a", "b", "c", "d")));
  }

  @Test public void testFizzBuzz() {
    assertEquals(
        list("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz", "16", "17", "Fizz", "19"), 
        map(
          i -> {
                 if (i % 3 == 0 && i % 5 == 0) return "FizzBuzz";
                 else if (i % 3 == 0) return "Fizz";
                 else if (i % 5 == 0) return "Buzz";
                 else return String.valueOf(i);}, 
          range(1, 20)));
  }

  @Test public void testRange() {
    assertEquals(list(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), range(0, 10));
  }

  @Test public void testReduce() {
    int reduced = reduce((a, b) -> a + b, list(1, 2, 3, 4, 5));
    assertEquals(15, reduced);

    double reducedDoubles = reduce((a, b) -> a + b, list(1.0, 2.0, 3.0, 4.0, 5.0));
    assertEquals(15.0, reducedDoubles, 0);
  }

  @Test public void testSum() {
    assertEquals(15, sum(map(BigDecimal::valueOf, list(1, 2, 3, 4, 5))).intValue());
  }

  @Test public void testFilter() {
    assertEquals(list(2, 4, 6), filter(i -> i % 2 == 0, list(1, 2, 3, 4, 5, 6, 7)));
  }

  @Test public void testDifference() {
    assertEquals(list(5, 6), difference(list(1, 2, 4, 5, 6), list(1, 2, 3, 4)));
    assertEquals(list(3), difference(list(1, 2, 3, 4), list(1, 2, 4, 5, 6)));
  }

  @Test public void testSort() {
    assertEquals(list(1, 2, 3, 4, 5, 6, 7), sort(list(1, 4, 2, 5, 3, 7, 6)));
  }

  @Test public void testSortBy_withComparitor() {
    assertEquals(list("The", "Fox", "the", "over", "lazy", "dogs", "Quick", "Brown", "Jumped"),
        sortBy((a, b) -> a.length() - b.length(), list("The", "Quick", "Brown", "Fox", "Jumped", "over", "the", "lazy", "dogs")));
  }

  @Test public void testSortBy_withFunction() {
    assertEquals(
        list("The", "Fox", "the", "over", "lazy", "dogs", "Quick", "Brown", "Jumped"),
        sortBy(String::length, list("The", "Quick", "Brown", "Fox", "Jumped", "over", "the", "lazy", "dogs")));
  }
  
  @Test public void testGroupBy() {
    assertEquals(
      map(
          3, list("The", "Fox", "the"), 
          4, list("over", "lazy", "dogs"), 
          5, list("Quick", "Brown"), 
          6, list("Jumped")),
      groupBy(
          String::length, 
          list("The", "Quick", "Brown", "Fox", "Jumped", "over", "the", "lazy", "dogs"))
    );
  }
  
  @Test public void testInGroupsOf() {
    assertEquals(
        list(list(1,2,3), list(4,5,6), list(7,8,9), list(10)),
        inGroupsOf(3, range(1, 11)));
  }
}
