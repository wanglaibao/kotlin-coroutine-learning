#               Standard.kt 源码解析

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

```

### apply{}

### with{}

### run{}

### use{}


































































































