package minimalisp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 
 * Makes functional style programming easier in Java through easier list processing.
 * 
 * <p>Either have your class extend minimalisp.Lisp (if it's lisp processing it, statically import it's methods or use it directly (e.g. Lisp.list(1,2,3,4)).</p. 
 * 
 * <pre>{@code
 *  
 *     // FizzBuzz as:
 *     assertEquals(
 *        list("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz", "16", "17", "Fizz", "19"), 
 *          map(range(1,20), i -> {
 *            if (i % 3 == 0 && i % 5 == 0) return "FizzBuzz";
 *            else if (i % 3 == 0) return "Fizz";
 *            else if (i % 5 == 0) return "Buzz";
 *            else return String.valueOf(i); }));
 * 
 *     // easy list creation
 *     List<String> letters = new ArrayList<String>();
 *     letters.add("A");
 *     letters.add("B");
 *     letters.add("C");
 *     letters.add("D");
 *     // becomes...
 *     assertEquals(letters, list("A", "B", "C", "D"));
 *     
 *     // easy array creation
 *     assertArrayEquals(new String[] {"A", "B", "C", "D"}, array("A", "B", "C", "D"));
 *     
 *     // easy map creation
 *     Map<String, String> expected = new HashMap<String, String>();
 *     expected.put("greeting", "Hey");
 *     expected.put("name", "Joe");
 *     // becomes...      
 *     assertEquals(expected, map("greeting", "Hey", "name", "Joe"));     
 * 
 *     // Transform lists to arrays and back again
 *     assertEquals(list("A", "B", "C", "D"), list(array("A", "B", "C", "D")));
 *     assertArrayEquals(array("A", "B", "C", "D"), array(list("A", "B", "C", "D")));
 *     assertEquals(map(1, "A", 2, "B"), invert(map("A", 1, "B", 2)));     
 *     
 *     // immutable reversing
 *     List<String> letters = list("A", "B", "C", "D");
 *     assertEquals(list("D", "C", "B", "A"), reverse(letters));
 *     assertEquals(list("A", "B", "C", "D"), letters);
 *
 *     // quick first and last items
 *     assertEquals("A", first(list("A", "B", "C", "D")));
 *     assertEquals("D", last(list("A", "B", "C", "D")));
 *     
 *     // compact away null values
 *     assertEquals(list("A", "B", "C", "D"), compact(list("A", null, null, "B", null, "C", null, "D")));
 *
 *     // flatten deep lists
 *     assertEquals(list("A", "B", "C", "D"), flatten(list(list("A", "B"), list("C", "D"))));
 *     
 *     // quick mapping and filtering 
 *     assertEquals(list("A", "B", "C", "D"), map(list("a", "b", "c", "d"), String::toUpperCase));     
 *     assertEquals(list(2, 4, 6), filter(list(1,2,3,4,5,6,7), i -> i % 2 == 0));
 *     
 *     // find unique values
 *     assertEquals(list("A", "B", "C", "D"), distinct(list("A", "B", "A", "B", "C", "C", "A", "D")));
 *     
 *     // sort by results of applied functions 
 *     assertEquals(
 *      list("The", "Fox", "the", "over", "lazy", "dogs", "Quick", "Brown", "Jumped"), 
 *      sortBy(
 *            list("The", "Quick", "Brown", "Fox", "Jumped", "over", "the", "lazy", "dogs"),
 *        String::length));    
 *     
 * </pre>
 * 
 * @author Craig Davidson
 *
 */
public class Lisp {
  /**
   * Returns a List containing the items in the provided VarArgs
   */
  @SafeVarargs public static <T> List<T> list(T... items) {
    return Arrays.asList(items);
  }

  /**
   * Returns a List containing items in the provided Collection.
   */
  public static <T> List<T> list(Collection<T> items) {
    return items.stream().collect(Collectors.toList());
  }

  /**
   * Returns an array containing items in the provided VarArgs
   */
  @SafeVarargs public static <T> T[] array(T... items) {
    return items;
  }

  /**
   * Returns an array containing items in the provided List
   */
  public static <T> T[] array(List<T> items) {
    return items.toArray(array(items.get(0)));
  }

  /**
   * Returns a shallow copy of the provided List.
   */
  public static <T> List<T> copy(List<T> list) {
    return new ArrayList<>(list);
  }

  /**
   * Returns a List of integers starting (inclusive) of the first number, and
   * finishing (exlusive of the last).
   */
  public static List<Integer> range(int startInclusive, int endExclusive) {
    return IntStream.range(startInclusive, endExclusive).boxed().collect(Collectors.toList());
  }

  /**
   * Returns a Map of constructed from the supplied key List and value List.
   */
  public static <K, V> Map<K, V> map(List<K> keys, List<V> values) {
    Map<K, V> map = new LinkedHashMap<K, V>();
    for (int i = 0; i < keys.size(); i++)
      map.put(keys.get(i), values.get(i));
    return map;
  }

  /**
   * Returns a Map from the supplied list of objects (key, value, key, value,
   * ...)
   * 
   */
  @SuppressWarnings("unchecked") public static <T, K, V> Map<K, V> map(List<T> objects) {
    Map<K, V> map = new LinkedHashMap<K, V>();
    for (int i = 0; i < objects.size(); i = i + 2)
      map.put((K) objects.get(i), (V) objects.get(i + 1));
    return map;
  }

  /**
   * Returns a Map from the supplied array of objects (key, value, key, value,
   * ...)
   */
  @SafeVarargs public static <T, K, V> Map<K, V> map(T... objects) {
    Map<K, V> map = new LinkedHashMap<K, V>();
    for (int i = 0; i < objects.length; i = i + 2)
      map.put((K) objects[i], (V) objects[i + 1]);
    return map;
  }

  /**
   * Returns a Set containing items in the supplied List.
   */
  public static <T> Set<T> hashSet(List<T> objects) {
    return new HashSet<T>(objects);
  }

  /**
   * Returns a Set containing items from the passed VarArgs
   */
  @SafeVarargs public static <T> Set<T> set(T... objects) {
    return hashSet(list(objects));
  }

  /**
   * Returns the first item in the list.
   */
  public static <T> T first(List<T> list) {
    return list == null || list.isEmpty() ? null : list.get(0);
  }
  
  /** 
   * Returns a list of the first n items in the list.
   */
  public static <T> List<T> head(List<T> list, int n) {
    if (list == null) return null;
    if (list.size() < n) return copy(list);
    return list.subList(0, n); 
  }

  /**
   * Returns the last item in the list.
   */
  public static <T> T last(List<T> list) {
    return list == null || list.isEmpty() ? null : list.get(list.size() - 1);
  }
  
  /**
   * Returns a list of the last n items in the list
   */
  public static <T> List<T> tail(List<T> list, int n) {
    if (list == null) return null;
    if (list.size() < n) return copy(list);    
    return list.subList((list.size() - n), list.size());
  }

  /**
   * Returns a new List of the results of applying the passed function to the
   * items in the list.
   */
  public static <T, R> List<R> map(List<T> items, Function<T, R> func) {
    return items.stream().map(func).collect(Collectors.toList());
  }

  /**
   * Returns a Reduction of the results of applying the passed accumulator to
   * the items in the list.
   */
  public static <T> T reduce(List<T> items, BinaryOperator<T> accumulator) {
    return items.stream().reduce(accumulator).get();
  }

  /**
   * Returns the distinct items in the list.
   */
  public static <T> List<T> distinct(List<T> list) {
    return list.stream().distinct().collect(Collectors.toList());
  }

  /**
   * Returns a new list of the results of applying the predicate to the items in
   * the list.
   */
  public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
    return list.stream().filter(predicate).collect(Collectors.toList());
  }

  /**
   * Returns the sum of all the BigDecimal items in the list, ignoring nulls.
   */
  public static BigDecimal sum(List<BigDecimal> items) {
    return compact(items).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  /**
   * Returns a new list the items in the list sorted by natural order.
   */
  public static <T> List<T> sort(List<T> list) {
    return list.stream().sorted().collect(Collectors.toList());
  }

  /**
   * Returns a new list the items in the list sorted by the natural order of the
   * results of applying the passed function to the items in the list.
   */
  public static <T, R extends Comparable<R>> List<T> sortBy(List<T> list, Function<T, R> func) {
    return sortBy(list, new Comparator<T>() {
      public int compare(T o1, T o2) {
        Comparator<R> c = Comparator.<R>naturalOrder();
        return c.compare(func.apply(o1), func.apply(o2));
      }
    });
  }
  
  /**
   * Returns a new Map where the keys are the evaluated result from the block and the values
   * are arrays of elements in the collection that correspond to the key.
   */
  public static <T, G> Map<G, List<T>> groupBy(List<T> list, Function<T, G> func) {
    return list.stream().collect(Collectors.groupingBy(func));
  }
  

  /**
   * Returns a new list of items in the list sorted by the supplied comparator.
   */
  public static <T> List<T> sortBy(List<T> list, Comparator<? super T> comparitor) {
    List<T> copy = copy(list);
    Collections.sort(copy, comparitor);
    return copy;
  }

  /**
   * Returns a new list of the items in reversed order.
   */
  public static <T> List<T> reverse(List<T> list) {
    List<T> copy = copy(list);
    Collections.reverse(copy);
    return copy;
  }

  /**
   * Returns a new list containing the rest of the items in the list after the
   * first
   */
  public static <T> List<T> rest(List<T> list) {
    if (list == null) return null;
    if (list.isEmpty()) return list.subList(0, list.size());
    return list.subList(1, list.size());
  }

  /**
   * Returns a new list containing all non-null items in the list.
   */
  public static <T> List<T> compact(List<T> list) {
    List<T> copy = copy(list);
    copy.removeAll(Collections.singleton(null));
    return copy;
  }

  /**
   * Returns a new one dimensional list containing all items in the supplied
   * lists.
   */
  public static <T> List<T> flatten(List<List<T>> list) {
    List<T> results = copyAndClear(first(list));
    for (List<T> items : list)
      results.addAll(items);
    return results;
  }

  private static <T> List<T> clear(List<T> list) {
    list.clear();
    return list;
  }

  private static <T> List<T> copyAndClear(List<T> list) {
    return clear(copy(list));
  }

  /**
   * Returns a new Map with the contents of the second provided map merged over the provided map  
   */
  public static <K, V> Map<K, V> merge(Map<K, V> a, Map<K, V> b) {
    Map<K, V> merged = new LinkedHashMap<K, V>(a);
    merged.putAll(b);
    return merged;
  }
  
  /**
   * Returns a new combined list of merged elements of each list.
   */
  public static <T> List<List<T>> zip(List<T> a, List<T> b) {
    List<List<T>> result = new ArrayList<List<T>>();
    for (int i = 0; i < a.size(); i++)
      result.add(list(a.get(i), (b.size() > i) ? b.get(i) : null));
    return result;
  }

  /**
   * Returns a new list of the items in the first list without the items in the
   * second set.
   */
  public static <T> List<T> difference(List<T> list, List<T> list2) {
    Set<T> principal = hashSet(list);
    principal.removeAll(hashSet(list2));
    return list(principal);
  }

  /**
   * Returns a new Map with the original maps values as keys, and keys as
   * values.
   */
  public static <V, K> Map<V, K> invert(Map<K, V> map) {
    return map(list(map.values()), list(map.keySet()));
  }
}
