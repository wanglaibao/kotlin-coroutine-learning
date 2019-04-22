#   Quick Tip: Write Cleaner Code With Kotlin SAM Conversions

### What Is a SAM Conversion
```
    When we want to make use of a Java interface containing a single method in your Kotlin code,
    we don't have to manually create an anonymous class that implements it.
    Instead, we can use a lambda expression.
    Thanks to a process called SAM conversion,
    Kotlin can transparently convert any lambda expression
    whose signature matches that of the interface's single method into an instance of an anonymous class
    that implements the interface.


    For example, consider the following one-method Java interface

            public interface Adder {
                public void add(int a, int b);
            }

    we can use object expression to implement that interface in java, for example:


            // Creating instance of an anonymous class
            // using the object keyword
            val adder = object : Adder {
                override fun add(a: Int, b: Int): Int {
                    return a + b
                }
            }

    That's a lot of unnecessary code, which is also not very readable.
    By leveraging Kotlin's SAM conversion facility, however,
    we can write the following equivalent code instead:

        // Creating instance using a lambda
        val adder = Adder { a, b -> a + b }


    As you can see, we've now replaced the anonymous class with a short lambda expression,
    which is prefixed with the name of the interface.
    Note that the number of arguments the lambda expression takes
    is equal to the number of parameters in the signature of the interface's method.

```

### SAM Conversions in Function Calls

```
    While working with Java classes having methods that take SAM types as their arguments,
    we can further simplify the above syntax.
    For example, consider the following Java class, which contains a method that expects an object implementing the Adder interface:


            public class Calculator {
                private Adder adder;

                public void setAdder(Adder adder) {
                    this.adder = adder;
                }

                public void add(int a, int b) {
                    Log.d("CALCULATOR", "Sum is " + adder.add(a,b));
                }
            }

    In our Kotlin code, we can now directly pass a lambda expression to the setAdder() method,
    without prefixing it with the name of the Adder interface.

            val calculator = Calculator()
            calculator.setAdder({ a, b -> a+b })

    It is worth noting that while calling a method that takes a SAM type as its only argument,
    we3 are free to skip the parenthesis to make our code even more concise.

            calculator.setAdder { a, b -> a+b }
```

### SAM Conversions Without Lambdas

```
    SAM conversions work just fine with ordinary functions too.
    For example, consider the following function whose signature matches that of the Adder interface's method:

        fun myCustomAdd(a:Int , b:Int):Int {
              if (a+b < 100){
                return -1
              } else if (a+b < 200){
                return   0
              } else {
                return  a+b
              }
        }


    Kotlin allows we to directly pass the myCustomAdd() function as an argument
    to the setAdder() method of the Calculator class.
    Don't forget to reference the method using the :: operator. Here's how:

            calculator.setAdder (this::myCustomAdd)

```


### The it Variable

```
    Many times, SAM interfaces contain one-parameter methods.
    A one-parameter method, as its name suggests, has only one parameter in its signature.
    While working with such interfaces,
    Kotlin allows us to omit the parameter in our lambda expression's signature
    and use an implicit variable called it in the expression's body.
    To make things clearer, consider the following Java interface:

        public interface Doubler {
            public int doubleIt(int number);
        }

    While using the Doubler interface in our Kotlin code,
    we don't have to explicitly mention the number parameter in our lambda expression's signature.
    Instead, we can simply refer to it as it.

        // This lambda expression using the it variable
        val doubler1 = Doubler { 2*it }

        // is equivalent to this ordinary lambda expression
        val doubler2 = Doubler { number -> 2*number }



```


### SAM Interfaces in Kotlin

```
    As a Java developer, we might be inclined to create SAM interfaces in Kotlin.
    Doing so, however, is usually not a good idea.
    If we create a SAM interface in Kotlin,
    or create a Kotlin method that expects an object implementing a SAM interface as an argument,
    the SAM conversion facility will not be available to us—SAM conversion is a Java-interoperability feature and is limited to Java classes and interfaces only.

    Because Kotlin supports higher-order functions—functions that can take other functions as arguments
    —we'll never need to create SAM interfaces in it.
    For example, if the Calculator class is rewritten in Kotlin,
    its setAdder() method can be written such that it directly takes a function as its argument,
    instead of an object that implements the Adder interface.


        class Calculator {
            var adder:(a:Int, b:Int)->Int = {a,b -> 0}
                                            // Default implementation
                                            // Setter is available by default

            fun add(a:Int, b:Int) {
                Log.d("CALCULATOR", "Sum is " + adder(a,b))
            }
        }


    While using the above class,
    we can set adder to a function or a lambda expression using the = operator.
    The following code shows us how:

        val calculator = Calculator()
        calculator.adder = this::myCustomAdd
        // OR
        calculator.adder = {a,b -> a+b}
```









































































