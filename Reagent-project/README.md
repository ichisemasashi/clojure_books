# Reagent: ClojureScriptのためのミニマルなReact

## Reagentの紹介

[Reagent](https://github.com/reagent-project/reagent)は、[ClojureScript](https://github.com/clojure/clojurescript)と[React](https://reactjs.org/)の間の最小限のインターフェースを提供します。これにより、プレーンなClojureScriptの関数とデータだけを使って、効率的なReactコンポーネントを定義することができ、[Hiccup](https://github.com/weavejester/hiccup)のような構文を使ってUIを記述することができます。

Reagentの目標は、いくつかの基本的なコンセプトだけで任意の複雑なUIを定義できるようにすること、そしてパフォーマンスについてほとんど考える必要がないほど、デフォルトで十分に高速であることです。

非常に基本的なReagentコンポーネントは次のようなものである。

**Example**
---
I am a component!

I have **bold** <span style="color: red; ">and red</span> text.
---

```Clojure
(defn simple-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])
```

他のコンポーネントを構成要素として、新しいコンポーネントを作ることができます。このように

**Example**
---
I include simple-component.

I am a component!

I have **bold** <span style="color: red; ">and red</span> text.
---

```Clojure
(defn simple-parent []
  [:div
   [:p "I include simple-component."]
   [simple-component]])
```


