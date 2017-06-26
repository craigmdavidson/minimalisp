[![Build Status](https://travis-ci.org/craigmdavidson/minimalisp.svg?branch=master)](https://travis-ci.org/craigmdavidson/minimalisp) [![Code Climate](https://codeclimate.com/github/craigmdavidson/minimalisp/badges/gpa.svg)](https://codeclimate.com/github/craigmdavidson/minimalisp) [![codecov](https://codecov.io/gh/craigmdavidson/minimalisp/branch/master/graph/badge.svg)](https://codecov.io/gh/craigmdavidson/minimalisp) 

# Minimalisp

Minimalisp is a drop in library for standard Java projects that:
 - makes Java list processing as easy, succinct and enjoyable as programming in Clojure or Ruby;
 - uses standard Java collections;
 - embraces immutability and helps you program in a more functional style; 
 - involves less boilerplate than using Java 1.8 streams directly;
 - you can use with your existing code today.

### Quick Example

Instead of [this](https://github.com/EnterpriseQualityCoding/FizzBuzzEnterpriseEdition), you can write FizzBuzz like this:

```java
public List<String> fizzBuzz(int start, int finish) {
  return map( 
           i -> {
             if (i % 15 == 0) return "FizzBuzz";
             else if (i % 3 == 0) return "Fizz";
             else if (i % 5 == 0) return "Buzz";
             else return String.valueOf(i); },
           range(start, finish));
}

List<String> fizzBuzz = fizzBuzz(1, 21);   // => ["1", "2", "Fizz", "4", "Buzz", 
                                           //     "Fizz", "7", "8", "Fizz", "Buzz", 
                                           //     "11", "Fizz", "13", "14", "FizzBuzz", 
                                           //     "16", "17", "Fizz", "19", "Buzz"]
```

To use Minimalisp in your objects, either:
* make your class extend minimalisp.Lisp; 
* statically import minimalisp.Lisp methods;
* or use  minimalisp.Lisp static methods directly.

## FEATURES

### Easily make Lists and Arrays...
```java
static import minimalisp.Lisp.*;

// instead of this....
List<String> letters = new ArrayList<String>();
letters.add("A");
letters.add("B");
letters.add("C");
letters.add("D");

// write this...
List<String> letters = list("A", "B", "C", "D");
// or..
String[] letters = array("A", "B", "C", "D");
```

### ... and Maps
```java
// From lists of keys and values,
Map<String, Integer> a = map(list("A", "B", "C"), list(1, 2, 3)); // => { "A" : 1, "B" : 2, "C" : 3 }

// .. from a single list of varargs
Map<String, Integer> b = map("A", 1, "B", 2, "C", 3);             // => { "A" : 1, "B" : 2, "C" : 3 }

// .. or from a single list.
Map<String, Integer> c = map(list("A", 1, "B", 2, "C", 3));      // => { "A" : 1, "B" : 2, "C" : 3 }

```

### List Processing 
```java
// immutable reverse
List<String> reversed = reverse(list("A", "B", "C", "D")); // => ["A", "B", "C", "D"]

String first = first(list("A", "B", "C", "D"));            // => "A"
String last = last(list("A", "B", "C", "D"));              // => "B"
String rest = rest(list("A", "B", "C", "D"));              // => ["B", "C", "D"]

List<String> compacted = compact(list(null, "A", null, "B", null, "C")); //=> ["A", "B", "C"]
List<String> flattened = flatten(list(list("A", "B"), list("C", "D")));  //=> ["A", "B", "C", "D"]
```


### Easy Mapping, Reducing and Filtering
```java

List<String> letters = list("a", "b", "c", "d");
// instead of this...
List<String> upcased = letters.
                         stream().
                         map(String::toUpperCase).
                         collect(Collections.toList);
// just write this
List<String> upcased = map(String::toUpperCase, letters); 

int reduced = reduce((a, b) -> a+b, list(1,2,3,4,5));                       // 15

List<String> distincts = distinct(list("A", "B", "A", "B", "C", "A", "D")); // => ["A", "B", "C", "D"]

List<Integer> numbers = filter(i -> i % 2 == 0, list(1, 2, 3, 4, 5, 6, 7)); // => [2, 4, 6]
```

### Easy simple and no-so-simple Sorting
```java

List<String> words = list("The", "Quick", "Brown", "Fox", "Jumped", "over", "the", "lazy", "dogs");

List<String> sortedNaturally = sort(words);      
// => ["Brown", "Fox", "Jumped", "Quick", "The", "dogs", "lazy", "over", "the"];

List<String> sortedByLength = sortBy(String::length, words); 
// => ["The", "Fox", "the", "over", "lazy", "dogs", "Quick", "Brown", "Jumped"];
```






