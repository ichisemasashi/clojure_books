(ns learn-macro.answers.ch13
  "第13章演習の解答例。")

(defmacro when-let-lite-bad
  "【悪い例】test を二度埋め込む。"
  [[sym test] & body]
  `(when ~test
     (let [~sym ~test]
       ~@body)))

(def answers
  {:q1 {:bad `when-let-lite-bad
        :how-to-test "atom を swap! する test 式を渡し、inc 回数が 2 になることを is で確認"}
   :q2 {:a "ラベルを quote 済みリテラルや展開時定数に限定するなら、マクロ引数をシンボル／文字列リテラルに制限し ~'label のように埋め込む"
        :b "計測後に付けるなら、マクロは result マップだけ返し、呼び出し側で (assoc … :label …) する関数境界が自然"}
   :q3 {:note "emit-cond-> に対句を渡して手書きの if/-> 形と = で照合。奇数個なら ex-info"}})

(defn demo-bad-double-eval
  "悪い when-let-lite が二重評価することを示す。"
  []
  (let [n (atom 0)]
    (when-let-lite-bad [_x (do (swap! n inc) 1)]
      :ok)
    @n))
