(ns learn-macro.answers.ch14
  "第14章演習の解答例（判断のたたき台）。")

(def answers
  {:q1 {:verdict :maybe-macro
        :why "ルート表の構文糖衣はあり得るが、データ＋関数コンパイル（第11章）で足りることが多い。感染に注意"}
   :q2 {:verdict :prefer-function-or-data
        :why "設定マップ生成に評価制御が不要なら def とデータで足りる"}
   :q3 {:verdict :ok-thin-macro
        :why "式を一度だけ評価する計測は評価制御が本質（labeled-time 型）。薄い糖衣に留めよ"}
   :q4 {:verdict :maybe-macro-or-codegen
        :why "繰り返しの定義生成はマクロ／codegen 候補だが、仕様がデータで表せるならテーブル駆動も可"}
   :q5 {:verdict :avoid
        :why "core の if/when で足りる独自糖衣は学習コストと感染だけ増やす"}})
