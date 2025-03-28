
### スローとキャッチ

この章では、`if` や `cond` や `case` を使ってコードの実行の流れをコントロールする方法について説明してきましたが、時には実行の流れが勝手にコントロールされてしまうこともあります。もちろん例外のことだ。例外は、何かがとてもとても間違っていることを世界に伝える、プログラムの方法です：
  
``` 
user=> (/ 0 0) 
ArithmeticException Divide by zero clojure.lang.Numbers.divide
(Numbers.java:158)
```

デフォルトでは、例外が発生するとプログラムは即座に終了します。それが嫌な場合は、`try`で容疑者の式をラップすることで、例外処理コードを含めることができる：

```clojure
(try
  (publish-book book)
  (catch ArithmeticException e (println "Math problem."))
  (catch StackOverflowError e (println "Unable to publish..")))
```

このとき、 `try` の後、最初の `catch` の前にあるコードは通常通り実行され、例外が発生しなければ `try`には何の効果もありません。

しかし、コードが例外をトリガーした場合、 `try` は一種の `case` 式として動作し、例外の型を `catch` 節のいずれかにマッチさせようとします。この例では、例外の型が `ArithmeticException` であれば、最初の `catch` で処理され、プログラムは保存される。例外の型が `ArithmeticException` ではなく `StackOverflowError` であれば、2番目の `catch` 節で処理され、プログラムは別の日の計算を行うことができる。スローされた例外が `catch` 節のどれにも当てはまらない場合、その例外はあなたのプログラムを大混乱に陥れ続ける。


例外はゼロで割るような間抜けなことをしたときに発生しますが、手動で例外を投げることもできます。それには2つのものが必要である： `throw` 式と `throw` 式で使用する例外値である。例外値を取得する最も簡単な方法は、組み込みの `ex-info` 関数を使うことである：

```clojure
(defn publish-book [book]
  (when (not (:title book))
    (throw
      (ex-info "A book needs a title!" {:book book})))
  ;; Lots of publishing stuff...
)
```

関数 `ex-info` は問題を説明する文字列と、その他の関連情報を含むマップ (空の場合もある) を受け取る。さらに、 `ex-info` によって生成された例外をキャッチしたい場合は、 `clojure.lang.ExceptionInfo` 型の例外を探す必要があります。

