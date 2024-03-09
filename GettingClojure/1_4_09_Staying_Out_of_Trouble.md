
### Staying Out of Trouble
  
Depending on your programming background, the truthy/falsy approach to logic will seem either completely mundane or like an outrage to all that is right and moral. If you lean toward the outrage school of thought, please keep in mind that the "false and nil are false and everything else is true" rule is not a minor aberration that only pops up when some programmer isn’t careful.  There is a lot of code in Clojure that takes advantage of this behavior. For an example of this, look no further than `and`. While `(and true true)` does indeed evaluate to `true` and `(and false false)` is comfortably `false`, the `and` function can and will return some decidedly un-Boolean values:

```clojure
(and true 1984)       ; Evaluates to 1984, which is truthy.
(and 2001 "Emma")     ; Evaluates to "Emma", again truthy.
(and 2001 nil "Emma") ; Evaluates to nil, which is falsy.
``` 
    
The point is that the behavior of `and` only makes sense if you look at it from the truthy/falsy point of view. Given this, you should avoid testing for true or `false` explicitly. For example, the condition part of this `if`:

```clojure
(if (= (some-predicate? some-argument) true)
  (some-other-function))
```

isn’t just extra wordy; it’s wrong. The author of `some-predicate?`—like the author of `and`—may have decided to return something other than `true` to indicate truthyness. If it does, then this `if` will miss it. 

There’s also a stylist pothole that you should avoid: As you build functions that contain `if` and `cond` and do a bit of nesting, you’re going to find yourself needing to close off a lot of open parentheses as you get to the end. The convention is to close off the parentheses on the last line of your expression, the way we did with the three parentheses at the end of `shipping-charge`:


```clojure
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0
    :else (* 0.1 order-amount))) ; Close, close, close.
```

What you should not do is grant each closing parenthesis its own line, like this:

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0
    :else (* 0.1 order-amount) ; No.
  ) ; No.
) ; No!
```

As you get used to Clojure’s parentheses-based syntax, all those closing parentheses will begin to fade into the psychological background. Don’t draw attention to them while you wait for that to happen.


