
### Class Methods and Fields

While Java classes mostly exist to define the behavior of their instances, the classes themselves are also objects with a class—`java.lang.Class`—and fields and methods of their own. The fields and methods provided by classes (static methods and fields in Java parlance) are independent of any particular instance of the class.

For example, the `File` class carries around a field called `separator` that contains the character that goes between the bits of your path for the operating system you’re working on. In a Clojure program you can get at static fields by writing `class/field`. Thus if you evaluated this:

```
File/separator
```

on a Linux box or a Mac, you would get a string containing a forward slash.  Evaluate the same expressions on Windows, and you will get a backslash.

You access static methods in much the same way as static fields: `class/method`.

For example, the `File` class has a static method that will create a temporary file for you. Here’s how you would use it from Clojure: 

```clojure
;; Create a temporary file in the standard temp directory, a file
;; with a name like authors_list8240847420696232491.txt
(def temp-authors-file (File/createTempFile "authors_list" ".txt"))
```

Remember, you call static methods on Java classes with `class/method`.



