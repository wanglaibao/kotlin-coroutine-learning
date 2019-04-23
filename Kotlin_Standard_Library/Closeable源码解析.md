### use()

```
    public inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
        var exception: Throwable? = null
        try {
            return block(this)
        } catch (e: Throwable) {
            exception = e
            throw e
        } finally {
            when {
                apiVersionIsAtLeast(1, 1, 0) -> this.closeFinally(exception)
                this == null -> {}
                exception == null -> close()
                else ->
                    try {
                        close()
                    } catch (closeException: Throwable) {
                        // cause.addSuppressed(closeException) // ignored here
                    }
            }
        }
    }



    use() function of the standard library gives us the equivalent of Java’s try-with-resources and of C#’s using statement.

    This function applies to all objects of type Closeable and it automatically closes its receiver on exit.

    Note that as opposed to Java and C#, Kotlin’s use() is a regular library function and not directly baked in the language with a special syntax.

    This is made possible by Kotlin’s extension functions and closure syntax used coinjointly.

        eg:

        // Java 1.7 and above
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            prop.load(fis);
        }
        // fis automatically closed


        // Kotlin
        val prop = Properties()
        FileInputStream("config.properties").use {
            prop.load(it)
        }
        // FileInputStream automatically closed


    Because Kotlin’s version is just a regular function, it’s actually much more composable than Java’s.

    For example, we can return this prop object after loading it.

        // Kotlin
        fun readProperties() = Properties().apply {
            FileInputStream("config.properties").use { fis ->
                load(fis)
            }
        }


    The apply() call tells us that the type of this expression
    is that of the object apply() is invoked on, which is Properties.
    Inside this block, this is now of type Properties,
    which allows us to call load() on it directly.
    In between, we create a FileInputStream that we use to populate this property object.
    And once we call use() on it,
    that FileInputStream will be automatically closed before this function returns,
    saving us from the ugly try/catch/finally combo that Java requires.



```































































































