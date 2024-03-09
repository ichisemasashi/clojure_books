
### Testing Namespaces and Projects

So far we have run our tests one at a time, but this gets old fast. Happily, `clojure.test` provides the `run-tests` function, which makes it easy to run all of the tests in a namespace:

test/inventory/dev/run_tests.clj
```clojure
;; Three ways to run the tests in a namespace.
(test/run-tests)
(test/run-tests *ns*)
(test/run-tests 'inventory.core-test)
```

Call `run-tests` without any arguments, and it will run all the tests in the current namespace. Pass it either a namespace value or a namespace name (as a symbol), and it will run all the tests in that namespace. 

Better still, Leiningen provides a task to run all the tests in all the namespaces in your project from the command line:

```bash
$ lein test

Ran 1 tests containing 1 assertions.
1 failures, 0 errors.
{:test 1, :pass 1, :fail 0, :error 0, :type :summary}
```

It even supplies a nice summary of the test results.


