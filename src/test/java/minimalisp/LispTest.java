package minimalisp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class LispTest extends Lisp {
	
	@Test public void testMakeAList(){
		List<String> letters = new ArrayList<String>();
		letters.add("A");
		letters.add("B");
		letters.add("C");
		letters.add("D");
		assertEquals(letters, list("A", "B", "C", "D"));
	}
	
	@Test public void testMakeAnArray(){
		assertArrayEquals(new String[] {"A", "B", "C", "D"}, array("A", "B", "C", "D"));
	}
	
	@Test public void testMakeAnArrayIntoAList(){
		assertEquals(list("A", "B", "C", "D"), list(array("A", "B", "C", "D")));
	}
	
	@Test public void testMakeAListIntoAnArray(){
		assertArrayEquals(array("A", "B", "C", "D"), array(list("A", "B", "C", "D")));
	}	
	
	@Test public void testMakeAMapFromAList(){
		Map<String, String> expected = new HashMap<String, String>();
		expected.put("greeting", "Hello");
		expected.put("name", "Joe");
		assertEquals(expected, map(list(
				"greeting", "Hello", 
				"name", "Joe")));
	}
	
	@Test public void testMakeAMapFromAnArray(){
		Map<String, String> expected = new HashMap<String, String>();
		expected.put("greeting", "Hey");
		expected.put("name", "Joe");
		assertEquals(expected, map(array(
				"greeting", "Hey", 
				"name", "Joe")));		
	}
	
	@Test public void testMakeAMapFromVarargs(){
		Map<String, String> expected = new HashMap<String, String>();
		expected.put("greeting", "Hey");
		expected.put("name", "Joe");
		assertEquals(expected, map("greeting", "Hey", "name", "Joe"));		
	}
	
	@Test public void testMakeAMapFromAKeysListAndAValuesList(){
		Map<String, String> expected = new HashMap<String, String>();
		expected.put("greeting", "Hey");
		expected.put("name", "Joe");
		assertEquals(expected, map(list("greeting", "name"), list("Hey", "Joe")));
	}
	
	@Test public void testInvert(){
		assertEquals(
			map(
			  1, "A", 
			  2, "B"), 
			invert(
			  map(
			    "A", 1, 
			    "B", 2)));
	}
	
	@Test public void testImmutableReverse(){
		List<String> letters = list("A", "B", "C", "D");
		assertEquals(list("D", "C", "B", "A"), reverse(letters));
		assertEquals(list("A", "B", "C", "D"), letters);
	}
	
	@Test public void testFirst(){
		assertEquals("A", first(list("A", "B", "C", "D")));
	}
	
	@Test public void testLast(){
		assertEquals("D", last(list("A", "B", "C", "D")));
	}
	
	@Test public void testRest(){
		assertEquals(list("B", "C", "D"), rest(list("A", "B", "C", "D")));
	}
	
	@Test public void testCompact(){
		assertEquals(list("A", "B", "C", "D"), compact(list("A", null, null, "B", null, "C", null, "D")));
	}
	
	@Test public void testFlatten(){
		assertEquals(list("A", "B", "C", "D"), flatten(list(list("A", "B"), list("C", "D"))));
	}
	
	@Test public void testZip(){
		assertEquals(list(list("A", "B"), list("C", "D")), zip(list("A", "C"), list("B", "D")));
	}
	
	@Test public void testDistinct(){
		assertEquals(list("A", "B", "C", "D"), distinct(list("A", "B", "A", "B", "C", "C", "A", "D")));
	}
	
	@Test public void testMap(){
		assertEquals(list("A", "B", "C", "D"), map(list("a", "b", "c", "d"), String::toUpperCase));
	}
		
	@Test public void testFizzBuzz(){
		assertEquals(
			list("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz", "16", "17", "Fizz", "19"), 
			map(range(1,20), i -> {
				if (i % 3 == 0 && i % 5 == 0) return "FizzBuzz";
				else if (i % 3 == 0) return "Fizz";
				else if (i % 5 == 0) return "Buzz";
				else return String.valueOf(i); }));
	}
	
	@Test public void testRange(){
		assertEquals(list(0,1,2,3,4,5,6,7,8,9), range(0,10));
	}
	
	@Test public void testReduce(){
		int reduced = reduce(list(1,2,3,4,5), (a, b) -> a+b);
		assertEquals(15, reduced);
		
		double reducedDoubles = reduce(list(1.0, 2.0, 3.0, 4.0, 5.0), (a, b) -> a + b);
		assertEquals(15.0, reducedDoubles, 0);		
	}
	
	@Test public void testSum(){
		assertEquals(15, sum(map(list(1,2,3,4,5), BigDecimal::valueOf)).intValue());
	}

	@Test public void testFilter(){
		assertEquals(list(2, 4, 6), filter(list(1,2,3,4,5,6,7), i -> i % 2 == 0));
	}
	
	@Test public void testDifference(){
		assertEquals(list(5, 6), difference(list(1,2,4,5,6), list(1,2,3,4)));
		assertEquals(list(3), difference(list(1,2,3,4), list(1,2,4,5,6)));
	}
	
	@Test public void testSort(){
		assertEquals(list(1,2,3,4,5,6,7), sort(list(1,4,2,5,3,7,6)));
	}
	
	@Test public void testSortBy_withComparitor(){
		assertEquals(
			list("The", "Fox", "the", "over", "lazy", "dogs", "Quick", "Brown", "Jumped"), 
			sortBy(list(
				"The", "Quick", "Brown", "Fox", "Jumped", "over", "the", "lazy", "dogs"), 
				(a, b) -> a.length() - b.length()));
	}
	
	@Test public void testSortBy_withFunction(){
		assertEquals(
				list("The", "Fox", "the", "over", "lazy", "dogs", "Quick", "Brown", "Jumped"), 
				sortBy(list(
					"The", "Quick", "Brown", "Fox", "Jumped", "over", "the", "lazy", "dogs"),
					String::length));
		
	}
}
