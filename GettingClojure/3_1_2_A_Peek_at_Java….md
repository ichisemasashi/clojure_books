
### A Peek at Java …

Java is all about objects. A Java object is little bundle of data and code that typically represents a thing, possibly something in the real world like a book but possibly something more abstract like a stack or a queue. The data in objects is organized as fields, named values attached to the object. So an object that represents a book might have fields called `title` and `author`.

Every Java object is associated with a class. It’s an object’s class that determines exactly which fields and which methods the object carries. Classes typically also specify a constructor, a special bit of code that gets called when a new object is created. It’s the constructor that initializes the fields on an object and otherwise gets it ready to go out into the world. For example, our books might be represented by a `Book` class, complete with three fields and a constructor:
    
interop/book1/Book.java
```java
public class Book {
    // The fields. Every Book instance has its own title, author, and chapter.
    public String title;
    public String author;
    public int numberChapters;
    // Constructor method.
    public Book(String t, String a, int nChaps) {
        title = t;
        author = a;
        numberChapters = nChaps;
    } 
}
```

Most Java code comes packaged in methods, which are named, functionlike bits of program defined inside of classes. Thus an object representing a book might have methods with names like `publish` and `payRoyalties`:

interop/book2/Book.java
```java
public class Book {
    //Note the fields are public.
    public String title;
    public String author;
    public int numberChapters;
    public Book(String t, String a, int nChaps) {
        title = t;
        author = a;
        numberChapters = nChaps;
    }
    public void publish() {
        // Do something to publish the book.
    }
    public void payRoyalties() {
        // Do something to pay royalties.
    }
}
```

It’s generally considered bad programming practice to expose the data fields of a Java object directly to the outside world by declaring them public as we’ve done so far in our `Book` class. A more typical Java class will declare its fields private—that is, accessible only by code defined within the class—and will include methods with names like `getTitle` and `getAuthor` to expose the data hidden inside:

interop/book3/Book.java
```java
public class Book {
    // Make the fields private, accessible only inside this class.
    private String title;
    private String author;
    private int numberChapters;
    public Book(String t, String a, int nChaps) {
        title = t;
        author = a;
        numberChapters = nChaps;
    }
    // Add getter methods to make fields available to the outside world.
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getNumberChapters() {
        return numberChapters;
    }
}
```

Java organizes classes into an inheritance tree, where every class except one has a parent, or superclass, which supplies default methods and fields. The lone parentless exception is `java.lang.Object`, which sits alone at the top of the hierarchy, the ultimate ancestor of all Java classes.

Thus we might have a class called `Publication`:

interop/book4/Publication.java
```java
// By default, Publication is a subclass of java.lang.Object.
public class Publication {
    private String title;
    private String author;
    public Publication(String t, String a) {
        title = t;
        author = a;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
}
```

And make `Book` a subclass of `Publication`:

interop/book4/Book.java
```java
// Book is now a subclass of Publication, which handles the author and title.
public class Book extends Publication {
    private int numberChapters;
    public Book(String t, String a, int nChaps) {
        super(t, a);
        numberChapters = nChaps;
    }
    public int getNumberChapters() {
        return numberChapters;
    }
}
```

The idea is that the superclass represents a more generalized version of the concept while the subclass represents something more specific.

Finally, most classes in Java are organized into packages, which are roughly analogous to Clojure’s namespaces. We might, for example, put our `Book` class in the `com.russolsen.blottsbooks` package:

interop/book5/Book.java
```java
// Book is now in the com.russolsen.blottsbooks package.
package com.russolsen.blottsbooks;
public class Book extends Publication {
    // Guts of the class omitted.
}
```


Once we encase a class in a package, the package name becomes part of the class’s "fully qualified classname", so that the fully qualified name of `Book` is now `com.russolsen.blottsbooks.Book`. In an effort to prevent name collisions, most— but not all—Java package names are constructed using a sort of reverse domain name scheme. Hence the `com.russolsen` part of the `com.russolsen.blottsbooks`.


