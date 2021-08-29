# CHAPTER 8 スタイルガイド・ルール

Written by Shaindel Schwartz
Edited by Tom Manshreck

ソースファイルの保存場所、コードのフォーマット、ネーミングやパターン、例外やスレッドに関するルールなど、ほとんどのエンジニアはコードベースを管理するルールを持っています。ほとんどのソフトウェアエンジニアは、自分たちの作業方法を管理する一連のポリシーの範囲内で仕事をしています。Googleでは、コードベースを管理するために、ルールを定義したスタイルガイドのセットを管理しています。

ルールは法律です。単なる提案や推奨ではなく、厳格で義務的な法律なのです。そのため、普遍的な強制力があり、必要に応じて承認された場合を除き、ルールを無視することはできません。ルールとは対照的に、ガイダンスは推奨事項やベスト・プラクティスを提供します。これらの断片は、従うべきであり、従うことを強く推奨しますが、ルールとは異なり、通常は多少の変動の余地があります。

私たちは、自分たちが定義したルール、つまり守らなければならないコードを書くための「やるべきこと」と「やってはいけないこと」を、プログラミングスタイルガイドにまとめ、それを規範として扱っています。「スタイル」というと、書式に限定したコレクションという意味で、少し語弊があるかもしれません。スタイルガイドはそれ以上のもので、私たちのコードを管理するための完全な規約なのです。しかし、スタイルガイドが厳密に規定されているわけではありません。スタイルガイドのルールでは、「合理的な範囲内で、できるだけ記述的な名前を使用する」というルールなど、判断が求められることがあります。むしろ、スタイルガイドは、エンジニアが責任を負うべきルールの決定的な情報源となっています。

Googleで使用しているプログラミング言語ごとにスタイルガイドを作成しています（※1）。 どのスタイルガイドも、持続可能性を意識したコード開発を目指しているという点では共通しています。一方で、その範囲や長さ、内容には大きな違いがあります。プログラミング言語には、異なる強み、異なる機能、異なる優先順位があり、進化し続けるGoogleのコードリポジトリに採用されるまでの歴史的な経緯も異なります。そのため、各言語のガイドラインを独自に調整する方がはるかに現実的です。スタイルガイドの中には、Dart、R、Shellのガイドに見られるように、ネーミングやフォーマットなど、いくつかの包括的な原則に焦点を当てた簡潔なものもあります。一方、C++、Python、Javaなどのように、特定の言語の特徴を詳細に説明し、より長い文書にしているスタイルガイドもあります。スタイルガイドの中には、Google以外の言語の典型的な使用方法を重視しているものもあります。私たちのGoスタイルガイドは非常に短く、外部で認識されている規約に記載されているプラクティスを遵守するという概要の指示に、いくつかのルールを追加しただけです。また、外部の規範とは根本的に異なるルールを盛り込んだものもあります。私たちのC++のルールでは、Googleコード以外で広く使われている言語機能である例外の使用を禁止しています。

Google独自のスタイルガイドであっても様々な違いがあるため、スタイルガイドがカバーすべき内容を正確に説明することは困難です。Googleのスタイルガイド開発の指針となる決定は、Googleのコードベースを持続可能なものにする必要性に由来しています。他の組織のコードベースでは、持続可能性に関する要件が本質的に異なるため、別の調整されたルールが必要になります。本章では、Google の C++、Python、Java のスタイルガイドを例に、ルールとガイダンスの開発を導く原則とプロセスについて説明します。

## なぜルールがあるのか？

では、なぜ私たちはルールを持つのでしょうか？ルールを設ける目的は、「良い」行動を促し、「悪い」行動を抑制することにあります。「良い」と「悪い」の解釈は組織によって異なり、組織が何を重視しているかによって異なります。このような呼称は普遍的な好みではなく、善と悪は主観的であり、ニーズに合わせて調整される。ある組織では、「良い」は、小さなメモリーフットプリントをサポートする使用パターンを促進したり、ランタイムの最適化の可能性を優先したりするかもしれません。また、ある組織では、新しい言語機能を利用することが「良い」ことになるかもしれません。また、一貫性を最も重視する組織では、既存のパターンと矛盾するものは「悪い」とされます。私たちはまず、組織が何に価値を置いているかを認識し、それに応じて行動を奨励したり抑制したりするために、ルールやガイダンスを使用する必要があります。

組織が成長するにつれ、確立されたルールとガイドラインがコーディングの共通語彙を形成していきます。共通のボキャブラリーがあれば、エンジニアはコードがどのように表現されているかではなく、コードが何を言わなければならないかに集中することができます。この語彙を形成することで、エンジニアは無意識のうちに「良い」ことをデフォルトで行うようになります。このようにルールは、一般的な開発パターンを望ましい方向に導くための大きな力となります。

## ルールの作成

ルールを定義する際に重要なのは、"どのようなルールにすべきか？"ということではありません。問われるべきは、"どのような目標を達成しようとしているのか "ということです。ルールが果たすべきゴールに焦点を当て、そのゴールをサポートするルールを特定することで、有用なルールを抽出することが容易になります。スタイルガイドがコーディングプラクティスの法則として機能しているGoogleでは、"What goes into the style guide?" ではなく、"Why does something go into the style guide?" と問いかけます。コードを書くことを規制する一連のルールを持つことで、私たちの組織は何を得るのでしょうか？

### 行動指針

物事を整理してみましょう。Googleのエンジニアリング組織は、3万人以上のエンジニアで構成されています。このエンジニア集団は、スキルや経歴に大きなばらつきがあります。また、20億行以上のコードベースに対して、毎日約6万件のコードを提出しています。このコードベースは今後数十年にわたって存在する可能性があります。私たちは、他の多くの組織が必要とするものとは異なる価値観に基づいて最適化を行っていますが、これらの懸念はある程度、普遍的なものであり、スケールと時間の両方に強いエンジニアリング環境を維持する必要があります。

この文脈では、私たちのルールの目的は、開発環境の複雑さを管理し、コードベースを管理可能な状態に保ちつつ、エンジニアが生産的に作業できるようにすることです。ここではトレードオフの関係にあります。この目標を達成するための大きなルール群は、選択肢を制限することになります。柔軟性は失われ、一部の人々を怒らせることもあるかもしれませんが、権威ある標準によってもたらされる一貫性と対立の減少という利益の方が大きいのです。

このような観点から、私たちは、ルールの開発を導くいくつかの包括的な原則を認識していますが、これらの原則は以下のとおりです。

- 自重する
- 読者にとって最適であること
- 一貫性があること
- エラーになりやすく、驚くような構造を避ける
- 必要に応じて実用性を考慮する

#### ルールは自重するもの

すべてをスタイルガイドに記載すべきではありません。新しいルールが設定されるたびに、組織内のすべてのエンジニアに学習と適応を求めることには、ゼロではないコストがかかります。ルールが多すぎると(*2)、エンジニアがコードを書くときにすべての関連ルールを覚えておくことが難しくなるだけでなく、新しいエンジニアが自分のやり方を覚えるのも難しくなります。また、ルールが多すぎると、ルールセットを維持するのが難しくなり、コストもかかります。

そのため、自明であると思われるルールはあえて掲載していません。Googleのスタイルガイドは、弁護士的に解釈されることを意図したものではありません。明示的に禁止されていないからといって、それが合法であるとは限りません。例えば、C++のスタイルガイドには、gotoの使用を禁止する規則はありません。C++プログラマーはすでにgotoを避ける傾向にあるため、明示的に禁止するルールを盛り込むと、不必要なオーバーヘッドが生じます。たった1人か2人のエンジニアが何かを間違えている場合、新しいルールを作ることで全員の精神的負担を増やすことは、スケールメリットがありません。

#### 読み手のための最適化

私たちのルールのもう一つの原則は、コードの作者ではなく、コードを読む人のために最適化することです。時間の経過とともに、コードは書かれた時よりもはるかに頻繁に読まれるようになります。読むのが難しいコードよりも、タイプするのが面倒なコードの方がいいのです。Pythonスタイルガイドでは、条件式について説明していますが、条件式はif文よりも短く、コード作成者にとって便利であると認識しています。しかし、条件式は、より冗長なif文よりも読者に理解されにくい傾向があるため、その使用を制限しています。書くのが簡単」よりも「読むのが簡単」を重視しているのです。ここではトレードオフの関係にあります。エンジニアが変数や型のために長くて説明的な名前を何度も入力しなければならない場合、前もってコストがかかります。しかし、将来の読者に読みやすさを提供するために、このコストを支払うことを選択しました。

この優先順位付けの一環として、私たちはエンジニアが意図した動作の証拠をコードの中に明示的に残すことも要求しています。読者がコードを読むときに、そのコードが何をしているのかを明確に理解できるようにするためです。例えば、Java、JavaScript、C++のスタイルガイドでは、メソッドがスーパークラスのメソッドをオーバーライドする場合、overrideアノテーションまたはキーワードの使用を義務付けています。明示的な設計上の証拠がなければ、読者はこの意図を理解することができますが、コードを読む際には各読者の側でもう少し調べる必要があります。

意図された動作の証拠は、それが意外なものである場合にさらに重要になります。C++では、コードの断片を読んだだけでは、ポインタの所有権を追跡することが難しい場合があります。ポインタが関数に渡された場合、その関数の動作に精通していなければ、何を期待していいのかわかりません。呼び出し側はまだポインタを所有しているのでしょうか？関数が所有権を持ったのか？関数が戻った後もポインタを使い続けられるのか，それとも削除されてしまったのか．この問題を回避するため、C++スタイルガイドでは、所有権の移転が意図される場合、std::unique_ptrの使用を推奨しています。unique_ptrは、ポインタの所有権を管理する構造体で、ポインタのコピーが1つしか存在しないことを保証します。関数が引数として unique_ptr を受け取り、そのポインタの所有権を取得しようとする場合、呼び出し側は明示的に移動セマンティクスを呼び出す必要があります。

```
// Foo*を受け取り、渡されたポインタの所有権を持つことも持たないこともできる関数。
void TakeFoo(Foo* arg);

// 関数を呼び出しても、その関数が戻ってきた後の所有権に関して、読者には何も伝えられません。
Foo* my_foo(NewFoo());
TakeFoo(my_foo);
```

次のように比較してみてください。

```
// std::unique_ptr<Foo>を受け取る関数です。
void TakeFoo(std::unique_ptr<Foo> arg);

// 関数を呼び出した場合は、所有権が放棄されたことを明示的に示し、
// 関数が戻った後はunique_ptrを使用できません。
std::unique_ptr<Foo> my_foo(FooFactory());
TakeFoo(std::move(my_foo));
```

このスタイルガイドのルールがあれば、すべての呼び出しサイトに、所有権移転の明確な証拠が適用されるたびに含まれることが保証されます。このシグナルがあれば、コードの読者はすべての関数呼び出しの動作を理解する必要はありません。私たちは、APIの相互作用を推論するのに十分な情報をAPIで提供しています。このように、呼び出し先での動作を明確に文書化することで、コードスニペットの可読性と理解性を確保しています。私たちはローカルな推論を目指しており、関数の実装を含む他のコードを探して参照しなくても、呼び出しサイトで何が起こっているかを明確に理解することを目標としています。

コメントを対象としたほとんどのスタイルガイドのルールも、読者にとってのその場での証拠というこの目標をサポートするように設計されています。ドキュメントコメント（ファイル、クラス、または関数の前に付けられるブロックコメント）は、後に続くコードの設計や意図を説明します。実装コメント（コード自体に散りばめられたコメント）は、明らかでない選択を正当化したり強調したり、やっかいな部分を説明したり、コードの重要な部分を強調したりします。当社では、この2種類のコメントについてスタイルガイドのルールを設けており、他のエンジニアがコードを読む際に求めているであろう説明を提供することを求めています。

#### 一貫性を保つ

コードベース内の一貫性に関する私たちの考え方は、Googleのオフィスで適用されている哲学に似ています。大規模で分散したエンジニア集団の中では、チームは頻繁にオフィス間で分割され、グーグル社員もしばしば他のサイトに移動します。それぞれのオフィスは独自の個性を持ち、地域性やスタイルを尊重していますが、仕事を進める上で必要なものは、意図的に同じものを維持しています。訪問するグーグラーのバッジは現地のすべてのバッジリーダーで使用でき、グーグルのデバイスは常にWiFiを利用でき、会議室のビデオ会議のセットアップは同じインターフェースで行われます。Googlerは、これらすべての設定方法を学ぶために時間を費やす必要はなく、どこにいても同じことができることを知っています。オフィス間を移動しても、仕事を進めるのは簡単です。

私たちはソースコードにもそれを求めています。一貫性があるからこそ、どんなエンジニアでもコードベースの知らない部分に飛び込んでも、すぐに仕事を始めることができるのです。ローカルプロジェクトには独特の個性がありますが、ツールは同じで、テクニックも同じで、ライブラリも同じで、すべてがうまくいくのです。

#### 一貫性のメリット

バッジリーダーやビデオ会議のインターフェースをカスタマイズできないのは、オフィスにとっては制約に感じるかもしれませんが、一貫性を保つことで得られるメリットは、失うクリエイティブな自由度をはるかに上回るものです。コードも同じで、一貫性を保つことで制約を感じることもあるかもしれませんが、その分、より多くのエンジニアがより少ない労力でより多くの仕事をこなすことができるのです(*3)

- コードベースのスタイルや規範が内部的に一貫していると、コードを書くエンジニアもそれを読む人も、表現方法よりも何をするのかに集中することができます。同じインターフェースで問題を解決し、一貫した方法でコードをフォーマットすることで、専門家がコードを一目見て、何が重要なのか、何をしているのかを理解することが容易になるのです。また、コードのモジュール化や重複の発見も容易になります。このような理由から、私たちは一貫した命名規則、共通パターンの一貫した使用、一貫したフォーマットと構造に多くの注意を払っています。また、一見すると小さな問題でも、物事が一つの方法でしか行われないことを保証するためだけに決定を下すルールも多くあります。たとえば、インデントに使うスペースの数や、行の長さの制限などです(*5)。ここで重要なのは、答えそのものではなく、答えがひとつであるという一貫性です。
- 一貫性がスケーリングを可能にします。一貫性のあるコードは、コードを理解し、編集し、生成することができるツールの構築を容易にしてくれます。一貫性に依存するツールの利点は、全員のコードが少しずつ異なっている場合には適用できません。あるツールが、不足しているインポートを追加したり、使用されていないインクルードを削除したりすることで、ソースファイルを更新し続けることができたとしても、プロジェクトごとにインポートリストのソート戦略が異なっている場合には、そのツールはどこでも動作することはできないかもしれません。全員が同じコンポーネントを使用し、全員のコードが構造や構成に関する同じルールに従っていれば、どこでも動作するツールに投資することができ、メンテナンス作業の多くを自動化することができます。もし各チームが、それぞれの環境に合わせて同じツールの特注バージョンに投資する必要があるとしたら、その利点は失われてしまいます。
- 一貫性は、組織の人間的な部分を拡大する際にも役立ちます。組織が大きくなると、コードベースに携わるエンジニアの数も増えます。全員が作業するコードを可能な限り一貫性のあるものにしておくことで、プロジェクト間の機動性を高め、エンジニアがチームを変更する際の立ち上げ時間を最小限に抑え、人員数のニーズが変動しても組織が柔軟に対応できるようにすることができます。組織が成長するということは、SRE、ライブラリエンジニア、コード管理者など、他の役割の人々がコードに関わるということでもあります。Googleでは、これらの役割が複数のプロジェクトにまたがっていることが多く、あるチームのプロジェクトに精通していないエンジニアが、そのプロジェクトのコードを担当することもあります。コードベース全体で一貫したエクスペリエンスを提供することで、これを効率的に行うことができます。
- 一貫性は、時間への耐性も確保します。時間が経過すると、エンジニアがプロジェクトを離れ、新しい人が加わり、オーナーシップが変化し、プロジェクトが合併したり分裂したりします。一貫性のあるコードベースを目指すことで、このような移行を低コストで行うことができます。また、コードとそれに携わるエンジニアの両方に、ほぼ制約のない流動性をもたらし、長期的なメンテナンスに必要なプロセスを簡素化することができます。

----

### スケールメリット

数年前、私たちのC++スタイルガイドは、古いコードの一貫性を失わせるようなスタイルガイドのルールはほとんど変更しないと約束しました。"場合によっては、特定のスタイルルールを変更する正当な理由があるかもしれませんが、一貫性を保つために、現状のままにしています。"

コードベースが小さく、古くて埃をかぶったコーナーが少なかった頃は、それが理にかなっていました。

コードベースが大きく、古くなってくると、それは優先すべきことではなくなりました。これは（少なくともC++スタイルガイドの策定者にとっては）意識的な変更でした。このビットを打ち込むことで、C++のコードベースが完全に一貫したものになることは二度とないし、それを目指しているわけでもないことを明確に表明したのです。

現在のベストプラクティスに合わせてルールを更新するだけでなく、これまでに書かれたすべてのものにそのルールを適用しなければならないというのは、単純に負担が大きすぎます。大規模変更ツールとプロセスにより、ほぼすべてのコードを、ほぼすべての新しいパターンや構文に従うように更新し、古いコードのほとんどが最新の承認されたスタイルを示すようにしています（第22章参照）。しかし、このような仕組みは完璧ではありません。コードベースがこれほど大きくなると、古いコードのすべてが新しいベストプラクティスに適合するとは限りません。完全な一貫性を求めることは、価値に対してコストがかかりすぎるという点に達しています。

----

**基準を設けること**
一貫性を主張するとき、私たちは内部的な一貫性に注目しがちです。時には、グローバルな規約が採用される前にローカルな規約が生まれることがあり、すべてを合わせることは合理的ではありません。そのような場合には、一貫性の階層化を提唱します。「一貫性を保つ」というのはローカルから始まります。あるファイルの中の規範が、あるチームの規範よりも先にあり、それが大きなプロジェクトの規範よりも先にあり、それがコードベース全体の規範よりも先にあります。実際、スタイルガイドには、ローカルな慣習に従うことを明示したルールが数多く含まれており(*6)、科学的な技術的選択よりも、このローカルな一貫性を重視しています。

しかし、組織が内部規約を作り、それを守るだけでは必ずしも十分ではありません。時には、外部のコミュニティで採用されている基準を考慮に入れる必要があります。

----

### スペースの数え方

GoogleのPythonスタイルガイドでは、当初、すべてのPythonコードに2スペースのインデントを義務付けていました。外部のPythonコミュニティで使用されている標準的なPythonスタイルガイドは4スペースのインデントを使用しています。私たちの初期のPython開発のほとんどは、実際のPythonアプリケーションではなく、C++プロジェクトを直接サポートするためでした。そのため、すでにそのようにフォーマットされていたC++のコードとの一貫性を保つために、2スペースのインデントを使うことにしました。時間が経つにつれ、この理由は実際には通用しないことがわかりました。Pythonのコードを書くエンジニアは、C++のコードを読んだり書いたりするよりも、他のPythonのコードを読んだり書いたりする方がはるかに多いのです。私たちは、エンジニアが何かを調べたり、外部のコードスニペットを参照する必要があるたびに、余分な労力を費やしていました。また、コードの一部をオープンソースにエクスポートしようとするたびに、社内のコードと外部の世界との違いを調整するのに時間がかかり、大変な苦労をしました。

Starlark（Googleで開発されたPythonベースのビルド記述言語）に独自のスタイルガイドを作成することになったとき、外部との整合性をとるために4スペースのインデントを使用することにしました(*7)。

----

慣例がすでに存在している場合は、通常、組織が外部との整合性をとることは良いアイデアです。小規模で自己完結的な短期間の取り組みの場合は、プロジェクトの限られた範囲の外で起こることよりも、内部の一貫性の方が重要なので、違いはないでしょう。しかし、時間の経過やスケールアップの可能性が出てくると、自分のコードが外部のプロジェクトに影響を与えたり、外の世界に出て行ってしまう可能性が高くなります。長期的に見れば、広く受け入れられている標準に従うことが利益につながるでしょう。

#### エラーになりやすい構文や意外な構文を避ける

私たちのスタイルガイドでは、私たちが使用している言語の中で、意外性のある、変わった、またはトリッキーな構成要素の使用を制限しています。複雑な機能には、一見しただけではわからない微妙な落とし穴があることがよくあります。複雑な機能を十分に理解せずに使用すると、誤用やバグの発生が容易になります。また、あるプロジェクトのエンジニアがよく理解している機能であっても、将来のプロジェクトメンバーやメンテナが同じように理解できるとは限りません。

このような理由から、Pythonのスタイルガイドでは、リフレクションのような強力な機能の使用を避けるように定めています。Pythonの反射関数hasattr()とgetattr()は、文字列を使ってオブジェクトの属性にアクセスすることができます。

```
if hasattr(my_object, 'foo'):
some_var = getattr(my_object, 'foo')
```

さて、この例では、すべてが順調に見えるかもしれません。しかし、考えてみてください。

```
some_file.py:
    A_CONSTANT = [
    'foo',
    'bar',
    'baz',
    ]
other_file.py:
    values = []
    for field in some_file.A_CONSTANT:
    values.append(getattr(my_object, field))
```

コードを検索するとき、foo、bar、bazというフィールドがここでアクセスされていることをどうやって知ることができますか？読み手には明確な証拠が残りません。どの文字列がオブジェクトの属性にアクセスするために使われているのか、簡単に見ることができないので、簡単に検証することができません。これらの値を `A_CONSTANT` から読み取る代わりに、リモート・プロシージャ・コール (RPC) のリクエスト・メッセージやデータ・ストアから読み取ったとしたらどうでしょう？このような難読化されたコードは、メッセージの検証を誤るだけで、重大なセキュリティ上の欠陥を引き起こす可能性があり、非常に気付きにくいものです。また、そのようなコードをテスト・検証することも困難です。

Pythonの動的な性質はそのような動作を可能にし、非常に限られた状況では、`hasattr()`や`getattr()`を使うことは有効です。しかし、ほとんどの場合、これらは難読化の原因となり、バグを引き起こすだけです。

このような高度な言語機能は、その活用方法を知っている専門家にとっては完璧に問題を解決できるかもしれませんが、パワー系の機能は理解するのが難しく、あまり普及していないのが現状です。エキスパートだけでなく、すべてのエンジニアがコードベースで操作できるようにする必要があります。これは、初心者のソフトウェアエンジニアをサポートするだけでなく、SREにとってもより良い環境となります。SREが本番の障害をデバッグしている場合、彼らはどんな疑わしいコードにも飛びつくでしょうし、たとえ自分が精通していない言語で書かれたコードであっても同様です。私たちは、理解しやすくメンテナンスしやすい、シンプルでわかりやすいコードに価値を置いています。

#### 実用性に譲歩する

ラルフ・ウォルドー・エマーソンの言葉です。"愚かな一貫性は、小さな心の迷信である。" 一貫性のあるシンプルなコードベースを追求するためには、他のすべてを盲目的に無視することはできません。スタイルガイドに記載されているルールの中には、例外を認めざるを得ないケースがあることは承知していますが、それは構いません。必要であれば、ルールに抵触する可能性のある最適化や実用性のための譲歩を認めます。

パフォーマンスは重要です。時には、一貫性や読みやすさを犠牲にしてでも、パフォーマンスの最適化に対応することは理にかなっています。たとえば、C++スタイルガイドでは例外の使用を禁止していますが、コンパイラの最適化を引き起こす例外関連の言語指定子であるnoexceptの使用を許可するルールが含まれています。

相互運用性も重要です。グーグル以外の特定の部品で動作するように設計されたコードは、そのターゲットに合わせて調整することで、より良い結果が得られるかもしれません。例えば、C++スタイルガイドには、一般的なキャメルケース命名ガイドラインの例外として、標準ライブラリの機能を模倣したエンティティに標準ライブラリのsnake_caseスタイルを使用することが認められています(*8)。また、C++スタイルガイドでは、プラットフォーム機能との互換性のために多重継承を必要とするWindowsプログラミングの例外も認められていますが、これは他のすべてのC++コードでは明示的に禁止されています。また、JavaとJavaScriptのスタイルガイドでは、プロジェクトの所有者以外のコンポーネントと頻繁にインターフェースをとったり、コンポーネントに依存したりする生成コードは、スタイルガイドのルールの対象外であることが明示されています(*9) 一貫性が重要であり、適応が重要です。

## スタイルガイド

では、言語スタイルガイドには何が必要なのでしょうか？スタイルガイドのルールには、大きく分けて3つのカテゴリーがあります。

- 危険を回避するためのルール
- ベストプラクティスを実施するためのルール
- 一貫性を保つためのルール

#### 危険を回避するために

スタイルガイドには、何よりもまず、技術的な理由で行わなければならない、あるいは行ってはならない言語機能に関するルールが記載されています。静的メンバーや変数の使い方、ラムダ式の使い方、例外処理のルール、スレッド化、アクセス制御、クラス継承などの構築に関するルールなどです。また、どのような言語機能を使用し、どのような構造を避けるべきかを説明しています。また、どのような目的で使用されるのか、標準的な語彙の種類についても言及しています。言語機能の中には、直感的に理解できなかったり、適切に適用するのが容易ではなかったりする微妙な使用パターンがあり、それが微妙なバグの原因になることがあります。このガイドでは、それぞれの判断について、長所と短所を考慮した上で、どのような結論に至ったかを説明することを目指しています。これらの決定のほとんどは、時間への耐性の必要性、維持可能な言語使用のサポートと奨励に基づいています。

#### ベストプラクティスの徹底

スタイルガイドには、ソースコードを書く上でのベストプラクティスを守るためのルールも含まれています。これらのルールは、コードベースの健全性と保守性を維持するのに役立ちます。例えば、コード作成者はどこにどのようにコメントを記述しなければならないかを規定しています(*10)。コメントに関する規則は、コメントの一般的な慣習を網羅しており、コード内の文書を記述しなければならない特定のケース（switch文のフォールスルー、空の例外キャッチブロック、テンプレートのメタプログラミングなど、意図が必ずしも明らかではないケース）にまで拡大しています。また、ソースファイルの構造を詳細に規定し、期待されるコンテンツの構成を概説するルールもあります。また、パッケージ、クラス、関数、変数などのネーミングに関するルールもあります。これらのルールはすべて、エンジニアがより健全で持続可能なコードをサポートするための実践を導くことを目的としています。
スタイルガイドで定められているベストプラクティスの中には、ソースコードをより読みやすくするためのものがあります。多くのフォーマットルールがこのカテゴリーに属します。スタイルガイドでは、読みやすさを向上させるために、垂直および水平方向の空白をいつ、どのように使用するかを規定しています。また、行の長さの制限や波括弧の配置についても規定しています。いくつかの言語では、自動フォーマットツール（Goの場合はgofmt、Dartの場合はdartfmt）に委ねることで、フォーマットの要件をカバーしています。フォーマット要件の詳細なリストを項目別に作成しても、適用しなければならないツールの名前を挙げても、目的は同じです。つまり、読みやすさを向上させるために設計された一貫したフォーマットルールを持ち、すべてのコードに適用するのです。
私たちのスタイルガイドには、新しい言語機能や、まだよく理解されていない言語機能に対する制限も含まれています。その目的は、その機能が持つ潜在的な落とし穴に安全柵をあらかじめ設置しておき、皆が学習プロセスを経ることにあります。同時に、みんなが走り出す前に、使用を制限することで、発展していく使用パターンを観察し、観察した例からベストプラクティスを抽出するチャンスを得ることができます。このような新機能の場合、最初はどのように指導すればよいのかわからないことがあります。導入が進むにつれ、新機能を様々な方法で使用したいと考えているエンジニアは、その例をスタイルガイドのオーナーに相談し、当初の制限事項でカバーされていない追加のユースケースを許可してほしいと依頼します。寄せられた許可申請を見て、私たちはその機能がどのように使われているかを把握し、最終的には良い事例と悪い事例を一般化できるだけの例を集めます。このような情報が得られれば、制限付きの裁定に立ち返り、より広い範囲で使用できるように修正することができます。

----

### Case Study: Introducing std::unique_ptr

When C++11 introduced `std::unique_ptr`, a smart pointer type that expresses exclusive ownership of a dynamically allocated object and deletes the object when the `unique_ptr` goes out of scope, our style guide initially disallowed usage. The behavior of the `unique_ptr` was unfamiliar to most engineers, and the related move semantics that the language introduced were very new and, to most engineers, very confusing. Preventing the introduction of `std::unique_ptr` in the codebase seemed the safer choice. We updated our tooling to catch references to the disallowed type and kept our existing guidance recommending other types of existing smart pointers.
Time passed. Engineers had a chance to adjust to the implications of move semantics and we became increasingly convinced that using `std::unique_ptr` was directly in line with the goals of our style guidance. The information regarding object ownership that a `std::unique_ptr` facilitates at a function call site makes it far easier for a reader to understand that code. The added complexity of introducing this new type, and the novel move semantics that come with it, was still a strong concern, but the significant improvement in the long-term overall state of the codebase made the adoption of `std::unique_ptr` a worthwhile trade-off.

----

#### Building in consistency

Our style guides also contain rules that cover a lot of the smaller stuff. For these rules, we make and document a decision primarily to make and document a decision. Many rules in this category don’t have significant technical impact. Things like naming conventions, indentation spacing, import ordering: there is usually no clear, measurable, technical benefit for one form over another, which might be why the technical community tends to keep debating them.(*11) By choosing one, we’ve dropped out of the endless debate cycle and can just move on. Our engineers no longer spend time discussing two spaces versus four. The important bit for this category of rules is not what we’ve chosen for a given rule so much as the fact that we have chosen.

#### And for everything else...

With all that, there’s a lot that’s not in our style guides. We try to focus on the things that have the greatest impact on the health of our codebase. There are absolutely best practices left unspecified by these documents, including many fundamental pieces of good engineering advice: don’t be clever, don’t fork the codebase, don’t reinvent the wheel, and so on. Documents like our style guides can’t serve to take a complete novice all the way to a master-level understanding of software engineering --- there are some things we assume, and this is intentional.

## Changing the Rules

Our style guides aren’t static. As with most things, given the passage of time, the landscape within which a style guide decision was made and the factors that guided a given ruling are likely to change. Sometimes, conditions change enough to warrant reevaluation. If a new language version is released, we might want to update our rules to allow or exclude new features and idioms. If a rule is causing engineers to invest effort to circumvent it, we might need to reexamine the benefits the rule was supposed to provide. If the tools that we use to enforce a rule become overly complex and burdensome to maintain, the rule itself might have decayed and need to be revisited. Noticing when a rule is ready for another look is an important part of the process that keeps our rule set relevant and up to date.
The decisions behind rules captured in our style guides are backed by evidence. When adding a rule, we spend time discussing and analyzing the relevant pros and cons as well as the potential consequences, trying to verify that a given change is appropriate for the scale at which Google operates. Most entries in Google’s style guides include these considerations, laying out the pros and cons that were weighed during the process and giving the reasoning for the final ruling. Ideally, we prioritize this detailed reasoning and include it with every rule.
Documenting the reasoning behind a given decision gives us the advantage of being able to recognize when things need to change. Given the passage of time and changing conditions, a good decision made previously might not be the best current one. With influencing factors clearly noted, we are able to identify when changes related to one or more of these factors warrant reevaluating the rule.

----

### Case Study: CamelCase Naming

At Google, when we defined our initial style guidance for Python code, we chose to use CamelCase naming style instead of snake_case naming style for method names. Although the public Python style guide (PEP 8) and most of the Python community used snake_case naming, most of Google’s Python usage at the time was for C++ developers using Python as a scripting layer on top of a C++ codebase. Many of the defined Python types were wrappers for corresponding C++ types, and because Google’s C++ naming conventions follow CamelCase style, the cross-language consistency was seen as key.
Later, we reached a point at which we were building and supporting independent Python applications. The engineers most frequently using Python were Python engineers developing Python projects, not C++ engineers pulling together a quick script. We were causing a degree of awkwardness and readability problems for our Python engineers, requiring them to maintain one standard for our internal code but constantly adjust for another standard every time they referenced external code. We were also making it more difficult for new hires who came in with Python experience to adapt to our codebase norms.
As our Python projects grew, our code more frequently interacted with external Python projects. We were incorporating third-party Python libraries for some of our projects, leading to a mix within our codebase of our own CamelCase format with the externally preferred snake_case style. As we started to open source some of our Python projects, maintaining them in an external world where our conventions were nonconformist added both complexity on our part and wariness from a community that found our style surprising and somewhat weird.
Presented with these arguments, after discussing both the costs (losing consistency with other Google code, reeducation for Googlers used to our Python style) and benefits (gaining consistency with most other Python code, allowing what was already leaking in with third-party libraries), the style arbiters for the Python style guide decided to change the rule. With the restriction that it be applied as a file-wide choice, an exemption for existing code, and the latitude for projects to decide what is best for them, the Google Python style guide was updated to permit snake_case naming.

----

### The Process

Recognizing that things will need to change, given the long lifetime and ability to scale that we are aiming for, we created a process for updating our rules. The process for changing our style guide is solution based. Proposals for style guide updates are framed with this view, identifying an existing problem and presenting the proposed change as a way to fix it. “Problems,” in this process, are not hypothetical examples of things that could go wrong; problems are proven with patterns found in existing Google code. Given a demonstrated problem, because we have the detailed reasoning behind the existing style guide decision, we can reevaluate, checking whether a different conclusion now makes more sense.
The community of engineers writing code governed by the style guide are often best positioned to notice when a rule might need to be changed. Indeed, here at Google, most changes to our style guides begin with community discussion. Any engineer can ask questions or propose a change, usually by starting with the language-specific mailing lists dedicated to style guide discussions.
Proposals for style guide changes might come fully-formed, with specific, updated wording suggested, or might start as vague questions about the applicability of a given rule. Incoming ideas are discussed by the community, receiving feedback from other language users. Some proposals are rejected by community consensus, gauged to be unnecessary, too ambiguous, or not beneficial. Others receive positive feedback, gauged to have merit either as-is or with some suggested refinement. These proposals, the ones that make it through community review, are subject to final decision- making approval.

### The Style Arbiters

At Google, for each language’s style guide, final decisions and approvals are made by the style guide’s owners --- our style arbiters. For each programming language, a group of long-time language experts are the owners of the style guide and the designated decision makers. The style arbiters for a given language are often senior members of the language’s library team and other long-time Googlers with relevant language experience.
The actual decision making for any style guide change is a discussion of the engineering trade-offs for the proposed modification. The arbiters make decisions within the context of the agreed-upon goals for which the style guide optimizes. Changes are not made according to personal preference; they’re trade-off judgments. In fact, the C++ style arbiter group currently consists of four members. This might seem strange: having an odd number of committee members would prevent tied votes in case of a split decision. However, because of the nature of the decision making approach, where nothing is “because I think it should be this way” and everything is an evaluation of trade-off, decisions are made by consensus rather than by voting. The four-member group is happily functional as-is.

### Exceptions

Yes, our rules are law, but yes, some rules warrant exceptions. Our rules are typically designed for the greater, general case. Sometimes, specific situations would benefit from an exemption to a particular rule. When such a scenario arises, the style arbiters are consulted to determine whether there is a valid case for granting a waiver to a particular rule.
Waivers are not granted lightly. In C++ code, if a macro API is introduced, the style guide mandates that it be named using a project-specific prefix. Because of the way C++ handles macros, treating them as members of the global namespace, all macros that are exported from header files must have globally unique names to prevent collisions. The style guide rule regarding macro naming does allow for arbiter-granted exemptions for some utility macros that are genuinely global. However, when the reason behind a waiver request asking to exclude a project-specific prefix comes down to preferences due to macro name length or project consistency, the waiver is rejected. The integrity of the codebase outweighs the consistency of the project here.
Exceptions are allowed for cases in which it is gauged to be more beneficial to permit the rule-breaking than to avoid it. The C++ style guide disallows implicit type conversions, including single-argument constructors. However, for types that are designed to transparently wrap other types, where the underlying data is still accurately and precisely represented, it’s perfectly reasonable to allow implicit conversion. In such cases, waivers to the no-implicit-conversion rule are granted. Having such a clear case for valid exemptions might indicate that the rule in question needs to be clarified or amended. However, for this specific rule, enough waiver requests are received that appear to fit the valid case for exemption but in fact do not --- either because the specific type in question is not actually a transparent wrapper type or because the type is a wrapper but is not actually needed --- that keeping the rule in place as-is is still worthwhile.

## Guidance

In addition to rules, we curate programming guidance in various forms, ranging from long, in-depth discussion of complex topics to short, pointed advice on best practices that we endorse.
Guidance represents the collected wisdom of our engineering experience, documenting the best practices that we’ve extracted from the lessons learned along the way. Guidance tends to focus on things that we’ve observed people frequently getting wrong or new things that are unfamiliar and therefore subject to confusion. If the rules are the “musts,” our guidance is the “shoulds.”
One example of a pool of guidance that we cultivate is a set of primers for some of the predominant languages that we use. While our style guides are prescriptive, ruling on which language features are allowed and which are disallowed, the primers are descriptive, explaining the features that the guides endorse. They are quite broad in their coverage, touching on nearly every topic that an engineer new to the language’s use at Google would need to reference. They do not delve into every detail of a given topic, but they provide explanations and recommended use. When an engineer needs to figure out how to apply a feature that they want to use, the primers aim to serve as the go-to guiding reference.
A few years ago, we began publishing a series of C++ tips that offered a mix of general language advice and Google-specific tips. We cover hard things --- object lifetime, copy and move semantics, argument-dependent lookup; new things --- C++ 11 features as they were adopted in the codebase, preadopted C++17 types like string_view, optional, and variant; and things that needed a gentle nudge of correction --- reminders not to use using directives, warnings to remember to look out for implicit bool conversions. The tips grow out of actual problems encountered, addressing real programming issues that are not covered by the style guides. Their advice, unlike the rules in the style guide, are not true canon; they are still in the category of advice rather than rule. However, given the way they grow from observed patterns rather than abstract ideals, their broad and direct applicability set them apart from most other advice as a sort of “canon of the common.” Tips are narrowly focused and relatively short, each one no more than a few minutes’ read. This “Tip of the Week” series has been extremely successful internally, with frequent citations during code reviews and technical discussions.(*12)
Software engineers come in to a new project or codebase with knowledge of the programming language they are going to be using, but lacking the knowledge of how the programming language is used within Google. To bridge this gap, we maintain a series of “<Language>@Google 101” courses for each of the primary programming languages in use. These full-day courses focus on what makes development with that language different in our codebase. They cover the most frequently used libraries and idioms, in-house preferences, and custom tool usage. For a C++ engineer who has just become a Google C++ engineer, the course fills in the missing pieces that make them not just a good engineer, but a good Google codebase engineer.
In addition to teaching courses that aim to get someone completely unfamiliar with our setup up and running quickly, we also cultivate ready references for engineers deep in the codebase to find the information that could help them on the go. These references vary in form and span the languages that we use. Some of the useful references that we maintain internally include the following:

- Language-specific advice for the areas that are generally more difficult to get correct (such as concurrency and hashing).
- Detailed breakdowns of new features that are introduced with a language update and advice on how to use them within the codebase.
- Listings of key abstractions and data structures provided by our libraries. This keeps us from reinventing structures that already exist and provides a response to, “I need a thing, but I don’t know what it’s called in our libraries.”

## Applying the Rules

Rules, by their nature, lend greater value when they are enforceable. Rules can be enforced socially, through teaching and training, or technically, with tooling. We have various formal training courses at Google that cover many of the best practices that our rules require. We also invest resources in keeping our documentation up to date to ensure that reference material remains accurate and current. A key part of our overall training approach when it comes to awareness and understanding of our rules is the role that code reviews play. The readability process that we run here at Google  --- where engineers new to Google’s development environment for a given language are mentored through code reviews --- is, to a great extent, about cultivating the habits and patterns required by our style guides (see details on the readability process in
Chapter 3). The process is an important piece of how we ensure that these practices are learned and applied across project boundaries.
Although some level of training is always necessary --- engineers must, after all, learn the rules so that they can write code that follows them --- when it comes to checking for compliance, rather than exclusively depending on engineer-based verification, we strongly prefer to automate enforcement with tooling.
Automated rule enforcement ensures that rules are not dropped or forgotten as time passes or as an organization scales up. New people join; they might not yet know all the rules. Rules change over time; even with good communication, not everyone will remember the current state of everything. Projects grow and add new features; rules that had previously not been relevant are suddenly applicable. An engineer checking for rule compliance depends on either memory or documentation, both of which can fail. As long as our tooling stays up to date, in sync with our rule changes, we know that our rules are being applied by all our engineers for all our projects.
Another advantage to automated enforcement is minimization of the variance in how a rule is interpreted and applied. When we write a script or use a tool to check for compliance, we validate all inputs against a single, unchanging definition of the rule. We aren’t leaving interpretation up to each individual engineer. Human engineers view everything with a perspective colored by their biases. Unconscious or not, potentially subtle, and even possibly harmless, biases still change the way people view things. Leaving enforcement up to engineers is likely to see inconsistent interpretation and application of the rules, potentially with inconsistent expectations of accountability. The more that we delegate to the tools, the fewer entry points we leave for human biases to enter.
Tooling also makes enforcement scalable. As an organization grows, a single team of experts can write tools that the rest of the company can use. If the company doubles in size, the effort to enforce all rules across the entire organization doesn’t double, it costs about the same as it did before.
Even with the advantages we get by incorporating tooling, it might not be possible to automate enforcement for all rules. Some technical rules explicitly call for human judgment. In the C++ style guide, for example: “Avoid complicated template metaprogramming.” “Use auto to avoid type names that are noisy, obvious, or unimportant --- cases where the type doesn’t aid in clarity for the reader.” “Composition is often more appropriate than inheritance.” In the Java style guide: “There’s no single correct recipe for how to [order the members and initializers of your class]; different classes may order their contents in different ways.” “It is very rarely correct to do nothing in response to a caught exception.” “It is extremely rare to override Object.finalize.” For all of these rules, judgment is required and tooling can’t (yet!) take that place.
Other rules are social rather than technical, and it is often unwise to solve social problems with a technical solution. For many of the rules that fall under this category, the details tend to be a bit less well defined and tooling would become complex and expensive. It’s often better to leave enforcement of those rules to humans. For example, when it comes to the size of a given code change (i.e., the number of files affected and lines modified) we recommend that engineers favor smaller changes. Small changes are easier for engineers to review, so reviews tend to be faster and more thorough. They’re also less likely to introduce bugs because it’s easier to reason about the potential impact and effects of a smaller change. The definition of small, however, is somewhat nebulous. A change that propagates the identical one-line update across hundreds of files might actually be easy to review. By contrast, a smaller, 20-line change might introduce complex logic with side effects that are difficult to evaluate. We recognize that there are many different measurements of size, some of which may be subjective --- particularly when taking the complexity of a change into account. This is why we do not have any tooling to autoreject a proposed change that exceeds an arbitrary line limit. Reviewers can (and do) push back if they judge a change to be too large. For this and similar rules, enforcement is up to the discretion of the engineers authoring and reviewing the code. When it comes to technical rules, however, whenever it is feasible, we favor technical enforcement.

### Error Checkers

Many rules covering language usage can be enforced with static analysis tools. In fact, an informal survey of the C++ style guide by some of our C++ librarians in mid-2018 estimated that roughly 90% of its rules could be automatically verified. Error- checking tools take a set of rules or patterns and verify that a given code sample fully complies. Automated verification removes the burden of remembering all applicable rules from the code author. If an engineer only needs to look for violation warnings ---  many of which come with suggested fixes --- surfaced during code review by an analyzer that has been tightly integrated into the development workflow, we minimize the effort that it takes to comply with the rules. When we began using tools to flag deprecated functions based on source tagging, surfacing both the warning and the suggested fix in-place, the problem of having new usages of deprecated APIs disappeared almost overnight. Keeping the cost of compliance down makes it more likely for engineers to happily follow through.
We use tools like clang-tidy (for C++) and Error Prone (for Java) to automate the process of enforcing rules. See Chapter 20 for an in-depth discussion of our approach.
The tools we use are designed and tailored to support the rules that we define. Most tools in support of rules are absolutes; everybody must comply with the rules, so everybody uses the tools that check them. Sometimes, when tools support best practices where there’s a bit more flexibility in conforming to the conventions, there are opt-out mechanisms to allow projects to adjust for their needs.

### Code Formatters

At Google, we generally use automated style checkers and formatters to enforce consistent formatting within our code. The question of line lengths has stopped being interesting.(*13) Engineers just run the style checkers and keep moving forward. When formatting is done the same way every time, it becomes a non-issue during code review, eliminating the review cycles that are otherwise spent finding, flagging, and fixing minor style nits.
In managing the largest codebase ever, we’ve had the opportunity to observe the results of formatting done by humans versus formatting done by automated tooling. The robots are better on average than the humans by a significant amount. There are some places where domain expertise matters --- formatting a matrix, for example, is something a human can usually do better than a general-purpose formatter. Failing that, formatting code with an automated style checker rarely goes wrong.
We enforce use of these formatters with presubmit checks: before code can be submitted, a service checks whether running the formatter on the code produces any diffs. If it does, the submit is rejected with instructions on how to run the formatter to fix the code. Most code at Google is subject to such a presubmit check. For our code, we use clang-format for C++; an in-house wrapper around yapf for Python; gofmt for Go; dartfmt for Dart; and buildifier for our `BUILD` files.

----

### Case Study: gofmt

Sameer Ajmani

Google released the Go programming language as open source on November 10, 2009. Since then, Go has grown as a language for developing services, tools, cloud infrastructure, and open source software.(*14)
We knew that we needed a standard format for Go code from day one. We also knew that it would be nearly impossible to retrofit a standard format after the open source release. So the initial Go release included gofmt, the standard formatting tool for Go.

#### Motivations

Code reviews are a software engineering best practice, yet too much time was spent in review arguing over formatting. Although a standard format wouldn’t be everyone’s favorite, it would be good enough to eliminate this wasted time.(*15)
By standardizing the format, we laid the foundation for tools that could automatically update Go code without creating spurious diffs: machine-edited code would be indistinguishable from human-edited code.(*16)
For example, in the months leading up to Go 1.0 in 2012, the Go team used a tool called gofix to automatically update pre-1.0 Go code to the stable version of the language and libraries. Thanks to gofmt, the diffs gofix produced included only the important bits: changes to uses of the language and APIs. This allowed programmers to more easily review the changes and learn from the changes the tool made.

#### Impact

Go programmers expect that all Go code is formatted with gofmt. gofmt has no configuration knobs, and its behavior rarely changes. All major editors and IDEs use gofmt or emulate its behavior, so nearly all Go code in existence is formatted identically. At first, Go users complained about the enforced standard; now, users often cite gofmt as one of the many reasons they like Go. Even when reading unfamiliar Go code, the format is familiar.
Thousands of open source packages read and write Go code.(*17) Because all editors and IDEs agree on the Go format, Go tools are portable and easily integrated into new developer environments and workflows via the command line.

#### Retrofitting

In 2012, we decided to automatically format all `BUILD` files at Google using a new standard formatter: `buildifier`. `BUILD` files contain the rules for building Google’s software with Blaze, Google’s build system. A standard `BUILD` format would enable us to create tools that automatically edit `BUILD` files without disrupting their format, just as Go tools do with Go files.
It took six weeks for one engineer to get the reformatting of Google’s 200,000 BUILD files accepted by the various code owners, during which more than a thousand new `BUILD` files were added each week. Google’s nascent infrastructure for making large- scale changes greatly accelerated this effort. (See Chapter 22.)

## Conclusion

For any organization, but especially for an organization as large as Google’s engineering force, rules help us to manage complexity and build a maintainable codebase. A shared set of rules frames the engineering processes so that they can scale up and keep growing, keeping both the codebase and the organization sustainable for the long term.


## TL;DRs

- Rules and guidance should aim to support resilience to time and scaling.
- Know the data so that rules can be adjusted.
- Not everything should be a rule.
- Consistency is key.
- Automate enforcement when possible.







-----

1 Many of our style guides have external versions, which you can find at https://google.github.io/styleguide. We cite numerous examples from these guides within this chapter.
2 Tooling matters here. The measure for “too many” is not the raw number of rules in play, but how many an engineer needs to remember. For example, in the bad-old-days pre-clang-format, we needed to remember a ton of formatting rules. Those rules haven’t gone away, but with our current tooling, the cost of adherence has fallen dramatically. We’ve reached a point at which somebody could add an arbitrary number of formatting rules and nobody would care, because the tool just does it for you.
3 Credit to H. Wright for the real-world comparison, made at the point of having visited around 15 different Google offices.
4 “Chunking” is a cognitive process that groups pieces of information together into meaningful “chunks” rather than keeping note of them individually. Expert chess players, for example, think about configurations of pieces rather than the positions of the individuals.
5 See 4.2 Block indentation: +2 spaces, Spaces vs. Tabs, 4.4 Column limit:100 and Line Length.
6 Use of const, for example.
7 Style formatting for BUILD files implemented with Starlark is applied by buildifier. See https://github.com/ bazelbuild/buildtools.
8 See Exceptions to Naming Rules. As an example, our open sourced Abseil libraries use snake_case naming for types intended to be replacements for standard types. See the types defined in https://github.com/abseil/abseil- cpp/blob/master/absl/utility/utility.h. These are C++11 implementation of C++14 standard types and therefore use the standard’s favored snake_case style instead of Google’s preferred CamelCase form.
9 See Generated code: mostly exempt.
10 Seehttps://google.github.io/styleguide/cppguide.html#Comments,http://google.github.io/styleguide/pyguide#38- comments-and-docstrings, and https://google.github.io/styleguide/javaguide.html#s7-javadoc, where multiple languages define general comment rules.
11 Such discussions are really just bikeshedding, an illustration of Parkinson’s law of triviality.
12 https://abseil.io/tips has a selection of some of our most popular tips.
15 Robert Griesemer’s 2015 talk, “The Cultural Evolution of gofmt,” provides details on the motivation, design, and impact of gofmt on Go and other languages.
16 Russ Cox explained in 2009 that gofmt was about automated changes: “So we have all the hard parts of a program manipulation tool just sitting waiting to be used. Agreeing to accept ‘gofmt style’ is the piece that makes it doable in a finite amount of code.”
17 The Go AST and format packages each have thousands of importers.


