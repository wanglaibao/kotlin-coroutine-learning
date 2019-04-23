#                                   Standard.kt 源码解析

### let{}
```
    /**
     * Calls the specified function [block] with `this` value as its argument and returns its result.
     */
    @kotlin.internal.InlineOnly
    public inline fun <T, R> T.let(block: (T) -> R): R {
        return block(this)
    }


    let() is a scoping function:
          use it whenever we want to define a variable for a specific scope of our code but not beyond.
          It’s extremely useful to keep our code nicely self-contained
          so that we don’t have variables “leaking out”:being accessible past the point where they should be

          eg:
          DbConnection.getConnection().let { connection ->
          }
          // connection is no longer visible here


    let() can also be used as an alternative to testing against null:

        val map : Map<String, Config> = ...
        val config = map[key]
        // config is a "Config?"
        config?.let {
            // This whole block will not be executed if "config" is null.
            // Additionally, "it" has now been cast to a "Config" (no question mark)
        }

```

### apply{}

```
    /**
     * Calls the specified function [block] with `this` value as its receiver and returns `this` value.
     */
    @kotlin.internal.InlineOnly
    public inline fun <T> T.apply(block: T.() -> Unit): T {
        //block.invoke()
        block()
        return this
    }

    apply() defines an extension function on all types.
    When we invoke it, it calls the closure[lambda expression] passed in parameter
    and then returns the receiver object that closure[lambda expression] ran on.
    Sounds complicated? It’s actually very simple and extremely useful

        eg:
            File(dir).apply { mkdirs() }

    This snippet turns a String into a File object,
    calls mkdirs() on it and then returns the file.
    The equivalent Java code is a bit verbose

            File makeDir(String path) {
              File result = new File(path);
              result.mkdirs();
              return result;
            }

    apply() turns this kind of ubiquitous code into a one liner

```

### with{}

```

    /**
     * Calls the specified function [block] with the given [receiver] as its receiver and returns its result.
     */
    @kotlin.internal.InlineOnly
    public inline fun <T, R> with(receiver: T, block: T.() -> R): R {
        return receiver.block()
    }


    with() is convenient when we finding have to call multiple different methods on the same object.
    Instead of repeating the variable containing this object on each line,
    we can instead “factor it out” with a with call:

        eg:

            val w = Window()
            with(w) {
              setWidth(100)
              setHeight(200)
              setBackground(RED)
            }
```

### run{}

```
    /**
     * Calls the specified function [block] with `this` value as its receiver and returns its result.
     */
    @kotlin.internal.InlineOnly
    public inline fun <T, R> T.run(block: T.() -> R): R {
        return block()
    }


    run() is another interesting one liner from the standard library.
    Its definition is so simple that it looks almost useless but it’s actually a combination of with() and let(),
    they can be easily combined to create more powerful expressions.


```

### use{}


































































































