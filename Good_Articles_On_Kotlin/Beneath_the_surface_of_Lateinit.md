#                       Beneath the surface of Lateinit

```
    The lateinit keyword stands for late initialization.
    Lateinit comes very handy when a non-null initializer cannot be supplied in the constructor,
    but the developer is certain that the variable will not be null when accessing it,
    thus avoiding null checks when referencing it later.

    Warning: lateinit is not to be confused with the by lazy keyword,
    which basically defers initialization to where the defined property is first used,
    not its declaration
```


### a handful of use cases where lateinit keyword are extremely helpful
* Android: variables that get initialized in lifecycle methods

* Using Dagger for DI: injected class variables are initialized outside and independently from the constructor

* Setup for unit tests: test environment variables are initialized in a @Before-annotated method

* Spring Boot annotations (eg. @Autowired)



### Prerequisites for Using the lateinit keyword
* has to be a var property, val not allowed
* can be either a property inside the body of a class, or a top-level property (Since Kotlin 1.2)
* can only be of non-null type
* primitive types disallowed

### What happens in the background

```
    Although lateinit might look like magic, it is nothing of that sort.
    A quick peek into the decompiled bytecode reveals what is happening under the hood.
    For example, the following line in Kotlin:

            lateinit var book: Book

    translates to:

            @NotNull
            private Book book;

            @NotNull
            public final Book getBook() {
              Book var10000 = this.book;
              if(this.book == null) {
                Intrinsics.throwUninitializedPropertyAccessException("book");
              }
              return var10000;
            }

            public final void setBook(@NotNull Book var1) {
              Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
              this.book = var1;
            }


    This means a backing field is generated, with a proper getter and setter.
    Important: this will make the backing field exposed in Java code via get() and set() methods.
    To disable this, annotate the variable with @field:JvmSynthetic
    ---- this will apply the synthetic modifier via bytecode, preventing it to be accessible in Java code
```


### Debugging

```
    Trying to access a lateinit var before it is initialized throws a special exception stating the property itself,
    and the fact that it wasn't initialized.
    For example, if trying to access a lateinit var book: Book
    before it was initialized, the following RuntimeException is presented:
                kotlin.UninitializedPropertyAccessException: lateinit property book has not been initialized
```


### For the overprotective
```
    Since Kotlin 1.2, it is possible to check if a lateinit property has been initialized before using by calling .isInitialized on the reference to that property.
    So if there's uncertainty involved, the following lines can help.


                if (::book.isInitialized) {
                  // do stuff
                }
```



### Conclusion

```
    Lateinit is a neat trick, that is very helpful (even unavoidable) in certain situations - an elegant and handy solution in the Kotlin developers' repertoire. However, one has to be careful with it - it is not a substitute for using a nullable var to suppress access warnings.
```



























































