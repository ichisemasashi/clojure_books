
### マップの問題点

Clojureのマップの素晴らしいところは、`{:key "value"}`という素敵な構造で、非常に柔軟性があるということです。マップを使って、どんなキーでも、どんな値でも関連付けることができます。この "何でも扱える "柔軟性が、Clojureプログラムでマップをとても便利でどこにでもあるものにしている。

しかし、マップのこの柔軟性はタダではありません。柔軟性のコストの一部は、実行時のCPU秒数で測ることができる。マップは素晴らしくスピーディーですが、"何でも扱える柔軟性 "には実行時のペナルティが伴います。マップは任意のキーを扱う必要があるため、これらのキーだけを扱うように設計されたデータ構造よりも少し遅くなる。たいていの場合、このスピード・ペナルティは問題にはならないが、例えば膨大な量のデータを処理しようとする場合など、時には大きな問題になる。

マップの2つ目の欠点は、コードの一貫性と文書化の面でコストがかかることだ。どんなマップにも何でも入れることができるため、このようなマップの意図を見分ける唯一の方法はない：

record/examples.clj
```clojure
(let [watson-1 (get-watson-1)
      watson-2 (get-watson-2)]
      ;; Do something with our watsons...
      )
```

架空の人物か、ジョパディ！で優勝したスーパーコンピューターかを見るためだ：

```clojure
;; A fictional character.
(defn get-watson-1 []
  {:name "John Watson"
   :appears-in "Sign of the Four"
   :author "Doyle"})
  ;; A Jeopardy playing computer.
(defn get-watson-2 [] {:cpu "Power7" :no-cpus 2880 :storage-gb 4000})
```

