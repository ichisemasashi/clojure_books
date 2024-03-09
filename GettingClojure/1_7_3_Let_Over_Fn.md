        
### Let Over Fn
          
While the ideas behind let aren’t terribly challenging—it binds names to values in a purely local and temporary way—`let` does have some hidden superpowers, which only become visible when you combine it with `fn`. To get a glimpse of these hidden powers, imagine our book discounts are customer-dependent.  Somewhere we have a map of the user’s name to the discount that user gets:

```clojure
(def user-discounts
  {"Nicholas" 0.10 "Jonathan" 0.07 "Felicia" 0.05})
```

We could certainly add some parameters to `compute-discount-amount` to deal with this:

```clojure
(defn compute-discount-amount [amount user-name user-discounts min-charge] 
  (let [discount-percent (user-discounts user-name)
        discount (* amount discount-percent)
        discounted-amount (- amount discount)]
    (if (> discounted-amount min-charge)
      discounted-amount
      min-charge)))
```

The trouble with this approach is that we now have to carry the "user names and discounts" table around every time we want to compute a price. If all that extra lifting turns out to be a problem, a better strategy might be to create a higher-level function, one that produces variants of the `compute-discount-amount` function tailored to a particular customer:


```clojure
(defn mk-discount-price-f [user-name user-discounts min-charge]
  (let [discount-percent (user-discounts user-name)] 
    (fn [amount]
      (let [discount (* amount discount-percent)
            discounted-amount (- amount discount)]
        (if (> discounted-amount min-charge)
          discounted-amount
          min-charge)))))
;; Get a price function for Felicia.
(def compute-felicia-price (mk-discount-price-f "Felicia" user-discounts 10.0))
;; ...and sometime later compute a price
(compute-felicia-price 20.0)
```

There are quite a few moving parts in `mk-discount-price-f`, but it’s just a combination of features we’ve already seen. The first thing `mk-discount-price-f` does, in that initial `let`, is look up the discount percentage for the user. With the percentage rate in hand, `mk-discount-price-f` then constructs an anonymous function with an fn whose body is nearly identical to `compute-discount-amount`.

The interesting thing about `mk-discount-price-f` is how `discount-percent` gets bound in the initial `let`, "outside" of the `fn` and then used "inside" the `fn`. That means that while `discount-percent` is only visible inside the body of the `let`, it can live on long after the call to `mk-discount-price-f` has completed, buried inside of the anonymous function.


This "compute it in a let, use it in an fn" is such a great way to build anonymous functions that are both efficient and clear. It’s efficient because you can use the outside `let` to compute everything you need to construct the anonymous function. And it’s clear because inside of the anonymous function you can use descriptive names for those precomputed values.


