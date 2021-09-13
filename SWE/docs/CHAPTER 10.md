# CHAPTER 10  ドキュメント

Written by Tom Manshreck

Edited by Riona MacNamara

多くのエンジニアがコードの作成、使用、保守に関して抱いている不満の中で、共通しているのは、質の高いドキュメントがないことです。"この方法の副作用は何ですか？" "ステップ3の後にエラーが出ました。" "この頭字語は何を意味しますか？" "このドキュメントは最新のものですか？" すべてのソフトウェアエンジニアは、そのキャリアを通じて、ドキュメントの質、量、あるいはまったくの欠如について不満を口にするものであり、Googleのソフトウェアエンジニアも同様である。

テクニカルライターやプロジェクトマネージャーの助けはあっても、ソフトウェアエンジニアは常にほとんどのドキュメントを自分で書く必要がある。そのため、エンジニアが効果的にドキュメントを書くためには、適切なツールとインセンティブが必要です。エンジニアが質の高いドキュメントを簡単に書けるようにするには、組織の規模に合わせたプロセスやツールを導入し、既存のワークフローと連携させることが重要です。

全体的に見ると、2010年代後半のエンジニアリング・ドキュメンテーションの状況は、1980年代後半のソフトウェア・テストの状況に似ています。ドキュメンテーションを改善するためにもっと努力する必要があることは誰もが認識していますが、その重要なメリットが組織的に認識されているわけではありません。しかし、少しずつではありますが、その状況は変わりつつあります。Googleでは、ドキュメントをコードのように扱い、従来のエンジニアリングのワークフローに組み込むことで、エンジニアが簡単なドキュメントを書いて維持することができるようになったときに、最も成功した取り組みがあります。

## ドキュメンテーションとは？

「ドキュメント」とは、エンジニアが仕事をする上で必要なあらゆる補足テキストのことを指します。(実際、Googleのエンジニアが書くドキュメントのほとんどは、コードコメントの形で提供されています）。この章では、さまざまな種類のエンジニアリングドキュメントについて説明します。

## ドキュメンテーションはなぜ必要なのか？

質の高いドキュメントは、エンジニアの組織にとって非常に大きなメリットがあります。コードやAPIがより理解しやすくなり、ミスが減ります。プロジェクトチームは、設計目標やチームの目的が明確に示されていると、より集中できます。マニュアルプロセスでは、手順が明確に示されていると従うのが容易になります。新しいメンバーをチームやコードベースに迎え入れる際にも、プロセスが明確に文書化されていれば、労力が大幅に軽減されます。

しかし、文書化のメリットは必ずしも下流にあるため、一般的に文書化した人がすぐに利益を得ることはできません。これから説明するように、プログラマーにすぐに利益をもたらすテストとは異なり、ドキュメント作成は一般的に前もって多くの努力を必要とし、作成者に明確な利益をもたらすのは後になってからです。しかし、テストへの投資と同様に、ドキュメンテーションへの投資も時間が経てば元が取れます。つまり、ドキュメントを書くのは一度だけかもしれないが(*1)、その後、何百回、何千回と読まれることになり、その初期費用は将来の読者すべてに償却されることになるのだ。ドキュメントは時間とともにスケールするだけでなく、組織の他の部分も同様にスケールするために重要です。それは、次のような質問に答えるのに役立ちます。

- なぜこのような設計判断をしたのか？
- なぜこのコードをこのように実装したのか？
- 2年後に自分のコードを見たときに、なぜこのコードをこのように実装したのか？

ドキュメントがこのようなメリットを伝えるものであるならば、なぜ一般的にエンジニアから「貧弱」と言われるのでしょうか？その理由の一つは、これまで述べてきたように、特に書き手にとってのメリットがすぐには得られないことです。しかし、それ以外にもいくつかの理由があります。

- エンジニアは、ライティングをプログラミングとは別のスキルと考えていることが多いです。(ここでは、そうではないことを説明します。また、そうであっても、必ずしもソフトウェアエンジニアリングとは別のスキルではありません。)
- エンジニアの中には、自分には文章を書く能力がないと思っている人もいます。しかし、実用的なドキュメントを作成するのに、英語力(*2)が必要なわけではありません。自分から少し離れて、相手の立場に立って物事を考えればいいのです。
- ドキュメントの作成は、ツールのサポートや開発者のワークフローへの統合が限られているために、より困難になることが多い。
- ドキュメンテーションは、既存のコードのメンテナンスを容易にするものではなく、余分な負担（メンテナンスするための他の何か）と見なされます。

すべてのエンジニアリングチームがテクニカルライターを必要としているわけではありません（仮にそうだとしても、その数は十分ではありません）。つまり、ほとんどのドキュメントはエンジニアが自分で書くことになります。ですから、エンジニアにテクニカルライターになることを強要するのではなく、エンジニアがドキュメントを書きやすくするにはどうしたらいいかを考えるべきなのです。ドキュメンテーションにどれだけの労力を割くかは、あなたの組織でもいつかは決めなければならないことです。
ドキュメンテーションは、いくつかの異なるグループに利益をもたらします。書き手にとっても、ドキュメンテーションは以下のようなメリットがあります。

- APIの策定に役立ちます。ドキュメントを書くことは、そのAPIが意味のあるものかどうかを見極める最も確実な方法の一つです。多くの場合、ドキュメントを書くこと自体が、エンジニアに、他の方法では疑われないような設計上の決定を再評価させるきっかけとなる。説明できない、定義できないということは、おそらく十分に設計されていないということです。
- メンテナンスのためのロードマップと歴史的な記録を提供します。コードのトリックは避けるべきですが、優れたコメントは、2年前に書いたコードを見つめながら何が問題なのかを考えているときに、大いに役立ちます。
- あなたのコードをよりプロフェッショナルに見せ、トラフィックを促進します。開発者は当然のことながら、ドキュメントが充実したAPIは設計の良いAPIだと考えるでしょう。必ずしもそうとは限りませんが、両者はしばしば高い相関関係にあります。製品のドキュメントが充実しているかどうかは、その製品がどの程度メンテナンスされているかを示すかなり良い指標となります。
- 他のユーザーからの質問が減ります。これはおそらく、ドキュメントを書いている人にとって、時間をかけて得られる最大のメリットです。何かを誰かに何度も説明しなければならない場合、そのプロセスを文書化することは通常意味のあることです。

ドキュメントを書く人にとってのこれらの利点と同様に、ドキュメントの利点の大部分は、当然ながら読者にもたらされます。GoogleのC++スタイルガイドでは、「読み手のために最適化する」という格言が記されています。この格言は、コードだけでなく、コード周りのコメントや、APIに付属するドキュメントセットにも当てはまります。テストと同じように、良いドキュメントを書くために費やした努力は、その寿命の間に何倍もの利益をもたらします。ドキュメントは時間とともに重要になり、組織の規模が大きくなるにつれて、特に重要なコードに多大な利益をもたらします。

## ドキュメントはコードと同じ

1つの主要なプログラミング言語を使用しているソフトウェアエンジニアでも、特定の問題を解決するために異なる言語を使用することはよくあります。シェルスクリプトやPythonを使ってコマンドラインタスクを実行したり、バックエンドのコードのほとんどをC++で書き、ミドルウェアのコードの一部をJavaで書いたり......。それぞれの言語はツールボックスの中の一つの道具です。

ドキュメントも同じで、特定のタスクを達成するために、異なる言語（通常は英語）で書かれたツールです。ドキュメンテーションを書くことは、コードを書くこととあまり変わりません。プログラミング言語のように、規則、特定の構文、スタイルの決定があり、多くの場合、コードと同じような目的を達成するために、一貫性を保ち、明確性を高め、（理解の）誤りを避けるために書かれます。技術文書において文法が重要なのは、ルールが必要だからではなく、音声を標準化し、読者を混乱させたり、気を散らさせたりしないためです。Googleが多くの言語で特定のコメントスタイルを要求しているのはこのためです。

コードと同様、ドキュメントにもオーナーが必要です。所有者のいないドキュメントは陳腐化し、メンテナンスも困難になります。また、オーナーシップを明確にすることで、バグトラッキングシステムやコードレビューツールなど、既存の開発者のワークフローでドキュメントを扱うことが容易になります。もちろん、所有者が異なるドキュメント同士が衝突することもあります。そのような場合には、正規のドキュメントを指定することが重要です。つまり、プライマリ・ソースを決定し、関連する他のドキュメントをそのプライマリ・ソースに統合します（または、重複するドキュメントを廃止します）。

Googleで普及している「go/links」（第3章参照）は、このプロセスを容易にしています。分かりやすいgo/linksを持つドキュメントは、しばしば正典となります。正準化されたドキュメントを促進するもう一つの方法は、ドキュメントをソースコントロールの下に直接置き、ソースコードそのものと並べることで、ドキュメントをコードと直接関連付けることです。

ドキュメントはコードと密接に結びついていることが多いので、可能な限りコードとして扱われるべきです。つまり、ドキュメントは以下のようにすべきです。

- 従うべき内部ポリシーやルールがある
- ソース管理されている
- ドキュメントの維持に責任を持つ明確なオーナーシップがある
- 変更時にはレビューを受ける（ドキュメントのコードと一緒に変更される)
- コードのバグを追跡するように、問題が追跡されること
- 定期的に評価される（何らかの形でテストされる）。
- 可能であれば、正確さや新鮮さなどの面で測定されること。(ツールはまだここに追いついていない）。

エンジニアがドキュメント作成をソフトウェア開発に必要な作業の1つと考えれば考えるほど、ドキュメント作成にかかる初期費用への抵抗が減り、長期的な利益を得ることができるようになります。また、ドキュメント作成の作業を容易にすることで、先行コストを削減することができます。

----

### ケーススタディ Google Wiki

Googleがもっと小さくてスリムだった頃、テクニカルライターはほとんどいませんでした。情報を共有する最も簡単な方法は、独自の社内Wiki（GooWiki）でした。最初は、これは合理的なアプローチに思えました。すべてのエンジニアが単一のドキュメントセットを共有し、必要に応じて更新することができたのです。

しかし、Googleの規模が大きくなるにつれ、wikiスタイルのアプローチの問題点が明らかになりました。文書の真の所有者がいないため、多くの文書が古くなってしまった(*3)。 新しい文書を追加するプロセスがないため、重複する文書や文書セットが出てきてしまった。GooWikiはフラットな名前空間を持っていたため、人々はドキュメントセットに階層を適用することが苦手だった。ある時点では、本番環境であるBorgのセットアップに関するドキュメントが7～10個（数え方による）ありましたが、そのうちのいくつかは維持されているように見えましたし、ほとんどは特定のチームに特定の権限や前提条件を与えたものでした。

GooWikiのもう一つの問題は、時間の経過とともに明らかになりました：ドキュメントを修正できる人々は、それらを使用する人々ではありませんでした。新しいユーザが悪いドキュメントを発見しても、そのドキュメントが間違っていることを確認できないか、エラーを報告する簡単な方法がありませんでした。彼らは何かが間違っていることを知っていたが（ドキュメントが機能しなかったので）、それを「修正」することができなかった。逆に言えば、ドキュメントを直すのに最も適した人たちは、ドキュメントが書かれた後にそれを参照する必要がないことが多かったのです。Googleが成長するにつれ、ドキュメントの質が非常に悪くなり、毎年行われる開発者アンケートでは、ドキュメントの質がGoogleの開発者の不満の第1位になっていました。

この状況を改善するには、重要なドキュメントを、コードの変更を追跡するために使われていたのと同じ種類のソースコントロールの下に移動させる必要があった。ドキュメントには所有者がいて、ソースツリー内の正規の位置が決められ、バグを特定して修正するためのプロセスが導入され、ドキュメントは劇的に改善されていった。さらに、ドキュメントの書き方、メンテナンスの仕方が、コードの書き方、メンテナンスの仕方と同じになってきた。ドキュメントのエラーは、バグトラッキングソフトウェアで報告することができる。ドキュメントの変更は、既存のコードレビュープロセスを使って行うことができました。最終的には、エンジニアが自分でドキュメントを修正したり、テクニカルライター（オーナーであることが多い）に変更を伝えたりするようになりました。

ドキュメントをソースコントロールに移行することは、当初、多くの議論を呼びました。多くのエンジニアは、情報の自由の砦であるGooWikiを廃止することで、ドキュメントに対するハードル（レビューの必要性、ドキュメントのオーナーの必要性など）が高くなり、品質が低下すると確信していました。しかし、そんなことはありませんでした。ドキュメントの質が向上したのです。

ドキュメントの共通フォーマット言語としてMarkdownが導入されたことで、HTMLやCSSの専門知識がなくても、エンジニアがドキュメントの編集方法を理解しやすくなったことも功を奏した。やがてGoogleは、コード内にドキュメントを埋め込むための独自のフレームワーク「g3doc」を導入しました。このフレームワークでは、エンジニアの開発環境の中にソースコードと一緒にドキュメントが存在するため、ドキュメントの質がさらに向上しました。このフレームワークにより、エンジニアの開発環境の中でソースコードとドキュメントが並存するようになり、エンジニアはコードとそれに関連するドキュメントを同じ変更で更新することができるようになりました（これは今でも採用率の向上を目指しています）。

大きな違いは、ドキュメントのメンテナンスがコードのメンテナンスと同じような感覚で行えるようになったことです。エンジニアはバグを報告し、チェンジリストでドキュメントに変更を加え、変更内容を専門家のレビューに回すなどの作業を行います。新しいワークフローを作るのではなく、既存の開発者のワークフローを活用できることが大きなメリットでした。

----

## 聴衆を知る

エンジニアがドキュメントを書くときに陥りがちな間違いのひとつに、自分のためだけに書いてしまうというものがあります。それは当然のことで、自分のために書くことに価値がないわけではありません。なぜなら、数年後にこのコードを見て、かつて自分が言っていたことを理解する必要があるかもしれないからです。数年後にこのコードを見て、かつての意味を理解する必要があるかもしれませんし、あなたの文書を読む人とほぼ同じスキルセットを持っているかもしれません。しかし、自分のためだけに書いていると、ある種の仮定をしてしまうことになります。あなたのドキュメントが非常に幅広い読者（エンジニアリング全員、外部の開発者）に読まれる可能性があることを考えると、たとえ数人の読者がいなくなったとしても、それは大きなコストになります。組織が大きくなると、ドキュメントのミスが目立つようになり、あなたの仮定が当てはまらなくなることがよくあります。

そうではなく、書き始める前に、（正式にも非公式にも）ドキュメントが満足させる必要のあるオーディエンス（複数可）を特定する必要があります。設計書は、意思決定者を説得する必要があるかもしれません。チュートリアルでは、コードベースを全く知らない人に非常に明確な指示を与える必要があります。APIでは、専門家であれ初心者であれ、そのAPIのすべてのユーザーに完全で正確なリファレンス情報を提供する必要があるかもしれません。常に主要な読者を特定し、その読者に向けて書くようにしましょう。

良いドキュメントは、洗練されていなくても、"完璧 "でなくても良い。エンジニアがドキュメントを書くときに犯す間違いの一つは、自分たちはもっと優れた文章を書く必要があると思い込んでいることだ。そのような基準では、ほとんどのソフトウェアエンジニアは文章を書きません。エンジニアとして必要なテストやその他のプロセスと同じように、文章を書くことについて考えてみてください。聴衆に向かって、聴衆が期待する声とスタイルで書くのです。読むことができれば、書くことができます。聴衆は、あなたがかつて立っていた場所に立っていることを忘れてはいけません。ですから、優れたライターである必要はありません。あなたのような人が、今のあなたと同じようにドメインに精通していればいいのです。(そして、地面に杭を打ちさえすれば、時間をかけてこのドキュメントを改善することができます)。

### オーディエンスの種類

これまで、読者に適したスキルレベルや知識を持った人に向けて文章を書くべきだと指摘してきました。しかし、あなたの読者はいったい誰なのでしょうか？おそらく、次のような基準に基づいて、複数の読者が存在するのではないでしょうか。

- 経験レベル（熟練したプログラマーや、その言語に精通していないジュニアエンジニアなど
- ドメイン知識（チームメンバー、またはAPIエンドポイントにしか詳しくない組織内の他のエンジニア）。
- 目的（特定のタスクを実行するためにAPIを必要とし、その情報を素早く見つける必要があるエンドユーザー、または誰にも維持される必要がないことを望む、特に毛深い実装の根幹を担当するソフトウェアの専門家）。

対象者によってライティングスタイルが異なる場合もありますが、ほとんどの場合、異なる対象者グループにできるだけ広く適用できる方法で書くことがコツです。多くの場合、複雑なトピックを、専門家と初心者の両方に説明する必要があります。ドメイン知識を持つ専門家向けに書くと、手抜きはできても、初心者を混乱させることになります。逆に、初心者にすべてを詳細に説明すると、専門家を悩ませることになるでしょう。

もちろん、このような文書を書くことはバランスのとれた行為であり、特効薬はありませんが、私たちが発見した一つのことは、文書を短くすることが有効だということです。複雑なテーマをよく知らない人にも説明できるように、説明的に書きますが、専門家を失望させたり、困らせたりしないようにしましょう。短い文書を書くためには、長い文書を書いて（すべての情報を書き出して）から、可能な限り重複した情報を削除するエディットパスを行う必要があることがよくあります。これは退屈に聞こえるかもしれませんが、この費用はドキュメントの読者全員に分散されることを覚えておいてください。かつてパスカルが言ったように、「もし時間があれば、もっと短い手紙を書いていただろう」。ドキュメントを短く、明確にすることで、専門家と初心者の両方を満足させることができます。

もうひとつの重要な違いは、ユーザーがどのようにしてドキュメントに出会うかということです。

- 求道者とは、自分が欲しいものを知っていて、自分が見ているものがその条件に合っているかどうかを知りたいと思っているエンジニアです。この層のための重要な教育的工夫は、一貫性です。このグループに向けてリファレンス・ドキュメントを書く場合、例えばコード・ファイルの中では、読者がリファレンスに素早く目を通し、探しているものが見つかるかどうかを確認できるように、コメントを同じようなフォーマットに沿って書きたいと思うでしょう。
- つまずきやすい人は、自分が何を求めているのか正確にはわからないかもしれません。漠然としたアイデアしか持っていないかもしれません。このようなお客様には、わかりやすさが重要です。見ているコードの目的を説明する概要や紹介文（ファイルの先頭など）を用意しましょう。また、あるドキュメントが対象者にとって適切でない場合を特定することも有効です。Googleのドキュメントの多くは、"TL;DR: If you are not interested in C++ compilers at Google, you can stop reading now. "のような「TL;DR文」で始まります。

最後に、顧客（例：APIのユーザー）と提供者（例：プロジェクトチームのメンバー）を区別することも重要です。可能な限り、一方を対象とした文書と他方を対象とした文書は分けて考えるべきです。実装の詳細は、チームメンバーがメンテナンスのために重要な情報であり、エンドユーザーはそのような情報を読む必要はありません。しばしば、エンジニアは自分が公開しているライブラリのリファレンスAPIの中で設計上の決定を示すことがあります。このような理由は、特定の文書（設計文書）か、せいぜいインターフェイスの後ろに隠されたコードの実装の詳細に記載するのが適切です。

## ドキュメントの種類

エンジニアは、仕事の一環として、設計書、コードコメント、ハウツー文書、プロジェクトページなど、さまざまな種類のドキュメントを書きます。これらはすべて「ドキュメント」として扱われます。しかし、それぞれの種類を知り、種類を混在させないことが重要です。ドキュメントは一般的に、単一の目的を持ち、それを貫くべきです。APIが1つのことをうまくやるべきであるように、1つのドキュメントで複数のことをやろうとするのは避けましょう。代わりに、それらの部分をより論理的に分割します。

ソフトウェアエンジニアが書く必要のあるドキュメントには、大きく分けていくつかの種類があります。

- コードコメントを含むリファレンスドキュメント
- 設計書
- チュートリアル
- コンセプト・ドキュメント
- ランディングページ

Googleの初期には、大量のリンク（その多くは壊れていたり、時代遅れだったりします）、システムがどのように機能するかについての概念的な情報、APIリファレンスなどが散りばめられた、一枚岩のようなwikiページを持つチームが一般的でした。そのようなドキュメントは、単一の目的を果たさないため、失敗します（また、誰も読まないほど長くなります。有名なwikiページでは、数十の画面をスクロールしていました）。もし、そのページに何かを追加することに意味がないのであれば、その目的のために別の文書を探すか、あるいは作成することをお勧めします。

### リファレンスドキュメント

リファレンスドキュメントは、エンジニアが書く必要のある最も一般的なものです。リファレンスドキュメントとは、コードベース内のコードの使用方法を文書化したものを意味します。コードコメントは、エンジニアが維持しなければならない最も一般的なリファレンスドキュメントの形態です。このコメントは2つの基本的な陣営に分けられます。APIコメントと実装コメントです。この2つのコメントの違いを覚えておいてください。APIコメントでは、実装の詳細や設計上の決定事項を議論する必要はなく、ユーザーが著者ほどAPIに精通していることを前提とすることはできません。一方、実装コメントは読み手のドメイン知識をより多く想定することができますが、想定しすぎることには注意が必要です。

ほとんどのリファレンスドキュメントは、コードとは別のドキュメントとして提供されている場合でも、コードベース自体のコメントから生成されています。(当然のことですが、リファレンスドキュメントはできる限りシングルソースであるべきです)。JavaやPythonなどの一部の言語では、このリファレンスドキュメントの生成を容易にするために、特定のコメントフレームワーク（Javadoc、PyDoc、GoDoc）が用意されています。C++のような他の言語では、標準的な「参照ドキュメント」の実装はありませんが、C++はAPIの表面（ヘッダーまたは.hファイル）と実装（.ccファイル）を分離しているので、ヘッダーファイルはC++ APIをドキュメント化するための自然な場所であることが多いです。

Googleはこのようなアプローチをとっています。C++のAPIは、そのリファレンスドキュメントがヘッダーファイル内に存在することが望ましいと考えています。その他のリファレンスドキュメントも、Java、Python、Goのソースコードに直接埋め込まれています。Googleのコード検索ブラウザ（第17章参照）は非常に堅牢なので、別個に生成されたリファレンスドキュメントを提供するメリットはほとんどありません。コード検索のユーザーは、コードを簡単に検索できるだけでなく、通常、そのコードのオリジナルの定義を一番上の結果として見つけることができます。また、コードの定義と一緒にドキュメントがあることで、ドキュメントの発見やメンテナンスが容易になります。

ドキュメントが充実したAPIには、コードコメントが欠かせないことは周知の事実です。しかし、「良い」コメントとは一体何なのでしょうか？この章の前半で、リファレンス・ドキュメントの2つの主要な読者を特定しました：求める人とつまずく人です。求める人は自分が何を求めているかを知っていますが、つまずく人は知りません。求める人にとっての重要なポイントは、一貫してコメントされたコードベースであり、APIを素早くスキャンして探しているものを見つけることができます。つまずきやすい人にとっては、APIの目的を明確に示すことが重要で、多くの場合、ファイルヘッダの先頭に書かれている。この後のサブセクションでは、いくつかのコードコメントについて説明します。以下のコードコメントのガイドラインはC++に適用されていますが、Googleでは他の言語についても同様のルールが設けられています。

#### ファイルコメント

Googleでは、ほぼすべてのコードファイルにファイルコメントを記述しなければなりません。(ユーティリティー関数を1つだけ含むヘッダーファイルなどは、この基準から外れる場合があります)。ファイルコメントは、次のような形式のヘッダーで始めなければなりません。

```C
// -----------------------------------------------------------------------------
// str_cat.h
// -----------------------------------------------------------------------------
//
// This header file contains functions for efficiently concatenating and appending
// strings: StrCat() and StrAppend(). Most of the work within these routines is
// actually handled through use of a special AlphaNum type, which was designed
// to be used as a parameter type that efficiently manages conversion to
// strings and avoids copies in the above operations.
...
```

一般的に、ファイルコメントは、読んでいるコードに何が含まれているかの概要から始めるべきです。また、コードの主な使用例や対象となる読者（先の例では、文字列を連結したいと考えている開発者）を特定する必要があります。最初の1、2段落で簡潔に説明できないAPIは、通常、よく考えられていないAPIの兆候である。そのような場合は、APIを別のコンポーネントに分割することを検討してください。

#### クラスのコメント

最近のプログラミング言語は、ほとんどがオブジェクト指向です。そのため、クラスコメントはコードベースで使用する API の「オブジェクト」を定義するために重要です。Google のすべてのパブリッククラス (および構造体) には、クラス/構造体、そのクラスの重要なメソッド、そしてクラスの目的を説明するクラスコメントを含める必要があります。一般的に、クラスコメントはそのオブジェクトの側面を強調したドキュメントで「名詞化」されるべきです。つまり、「Fooクラスはx, y, zを含み、Barを行うことができ、次のBazの側面を持っています」というように言います。
クラスのコメントは、一般的に次のような形式のコメントで始まるべきです。

```C
// -----------------------------------------------------------------------------
// AlphaNum
// -----------------------------------------------------------------------------
//
// The AlphaNum class acts as the main parameter type for StrCat() and
// StrAppend(), providing efficient conversion of numeric, boolean, and
// hexadecimal values (through the Hex type) into strings.
```

#### Function comments

All free functions, or public methods of a class, at Google must also contain a function comment describing what the function does. Function comments should stress the active nature of their use, beginning with an indicative verb describing what the function does and what is returned.
Function comments should generally begin with a comment of the following form:

```C
// StrCat()
//
// Merges the given strings or numbers, using no delimiter(s),
// returning the merged result as a string.
...
```

Note that starting a function comment with a declarative verb introduces consistency across a header file. A seeker can quickly scan an API and read just the verb to get an idea of whether the function is appropriate: “Merges, Deletes, Creates,” and so on.
Some documentation styles (and some documentation generators) require various forms of boilerplate on function comments, like “Returns:”, “Throws:”, and so forth, but at Google we haven’t found them to be necessary. It is often clearer to present such information in a single prose comment that’s not broken up into artificial section boundaries:

```C
// Creates a new record for a customer with the given name and address,
// and returns the record ID, or throws `DuplicateEntryError` if a
// record with that name already exists.
int AddCustomer(string name, string address);
```

Notice how the postcondition, parameters, return value, and exceptional cases are naturally documented together (in this case, in a single sentence), because they are not independent of one another. Adding explicit boilerplate sections would make the comment more verbose and repetitive, but no clearer (and arguably less clear).

### Design Docs

Most teams at Google require an approved design document before starting work on any major project. A software engineer typically writes the proposed design document using a specific design doc template approved by the team. Such documents are designed to be collaborative, so they are often shared in Google Docs, which has good collaboration tools. Some teams require such design documents to be discussed and debated at specific team meetings, where the finer points of the design can be discussed or critiqued by experts. In some respects, these design discussions act as a form of code review before any code is written.
Because the development of a design document is one of the first processes an engineer undertakes before deploying a new system, it is also a convenient place to ensure that various concerns are covered. The canonical design document templates at Google require engineers to consider aspects of their design such as security implications, internationalization, storage requirements and privacy concerns, and so on. In most cases, such parts of those design documents are reviewed by experts in those domains.
A good design document should cover the goals of the design, its implementation strategy, and propose key design decisions with an emphasis on their individual trade-offs. The best design documents suggest design goals and cover alternative designs, denoting their strong and weak points.
A good design document, once approved, also acts not only as a historical record, but as a measure of whether the project successfully achieved its goals. Most teams archive their design documents in an appropriate location within their team documents so that they can review them at a later time. It’s often useful to review a design document before a product is launched to ensure that the stated goals when the design document was written remain the stated goals at launch (and if they do not, either the document or the product can be adjusted accordingly).

### Tutorials

Every software engineer, when they join a new team, will want to get up to speed as quickly as possible. Having a tutorial that walks someone through the setup of a new project is invaluable; “Hello World” has established itself is one of the best ways to ensure that all team members start off on the right foot. This goes for documents as well as code. Most projects deserve a “Hello World” document that assumes nothing and gets the engineer to make something “real” happen.
Often, the best time to write a tutorial, if one does not yet exist, is when you first join a team. (It’s also the best time to find bugs in any existing tutorial you are following.) Get a notepad or other way to take notes, and write down everything you need to do along the way, assuming no domain knowledge or special setup constraints; after you’re done, you’ll likely know what mistakes you made during the process --- and why  --- and can then edit down your steps to get a more streamlined tutorial. Importantly, write everything you need to do along the way; try not to assume any particular setup, permissions, or domain knowledge. If you do need to assume some other setup, state that clearly in the beginning of the tutorial as a set of prerequisites.
Most tutorials require you to perform a number of steps, in order. In those cases, number those steps explicitly. If the focus of the tutorial is on the user (say, for external developer documentation), then number each action that a user needs to undertake. Don’t number actions that the system may take in response to such user actions. It is critical and important to number explicitly every step when doing this. Nothing is more annoying than an error on step 4 because you forget to tell someone to properly authorize their username, for example.

#### Example: A bad tutorial

1. Download the package from our server at http://example.com
2. Copy the shell script to your home directory
3. Execute the shell script
4. The foobar system will communicate with the authentication system
5. Once authenticated, foobar will bootstrap a new database named “baz”
6. Test “baz” by executing a SQL command on the command line
7. Type: CREATE DATABASE my_foobar_db;


In the preceding procedure, steps 4 and 5 happen on the server end. It’s unclear whether the user needs to do anything, but they don’t, so those side effects can be mentioned as part of step 3. As well, it’s unclear whether step 6 and step 7 are different. (They aren’t.) Combine all atomic user operations into single steps so that the user knows they need to do something at each step in the process. Also, if your tutorial has user-visible input or output, denote that on separate lines (often using the convention of a monospaced bold font).


#### Example: A bad tutorial made better

1. Download the package from our server at http://example.com: $ curl -I http://example.com
2. Copy the shell script to your home directory:
```
        $ cp foobar.sh ~
```
3. Execute the shell script in your home directory:
```
        $ cd ~; foobar.sh
```
 The foobar system will first communicate with the authentication system. Once authenticated, foobar will bootstrap a new database named “baz” and open an input shell.
4. Test “baz” by executing a SQL command on the command line:
```
        baz:$ CREATE DATABASE my_foobar_db;
```

Note how each step requires specific user intervention. If, instead, the tutorial had a focus on some other aspect (e.g., a document about the “life of a server”), number those steps from the perspective of that focus (what the server does).

### Conceptual Documentation

Some code requires deeper explanations or insights than can be obtained simply by reading the reference documentation. In those cases, we need conceptual documentation to provide overviews of the APIs or systems. Some examples of conceptual documentation might be a library overview for a popular API, a document describing the life cycle of data within a server, and so on. In almost all cases, a conceptual document is meant to augment, not replace, a reference documentation set. Often this leads to duplication of some information, but with a purpose: to promote clarity. In those cases, it is not necessary for a conceptual document to cover all edge cases (though a reference should cover those cases religiously). In this case, sacrificing some accuracy is acceptable for clarity. The main point of a conceptual document is to impart understanding.
“Concept” documents are the most difficult forms of documentation to write. As a result, they are often the most neglected type of document within a software engineer’s toolbox. One problem engineers face when writing conceptual documentation is that it often cannot be embedded directly within the source code because there isn’t a canonical location to place it. Some APIs have a relatively broad API surface area, in which case, a file comment might be an appropriate place for a “conceptual” explanation of the API. But often, an API works in conjunction with other APIs and/or modules. The only logical place to document such complex behavior is through a separate conceptual document. If comments are the unit tests of documentation, conceptual documents are the integration tests.
Even when an API is appropriately scoped, it often makes sense to provide a separate conceptual document. For example, Abseil’s StrFormat library covers a variety of concepts that accomplished users of the API should understand. In those cases, both internally and externally, we provide a format concepts document.
A concept document needs to be useful to a broad audience: both experts and novices alike. Moreover, it needs to emphasize clarity, so it often needs to sacrifice completeness (something best reserved for a reference) and (sometimes) strict accuracy. That’s not to say a conceptual document should intentionally be inaccurate; it just means that it should focus on common usage and leave rare usages or side effects for reference documentation.

### Landing Pages

Most engineers are members of a team, and most teams have a “team page” somewhere on their company’s intranet. Often, these sites are a bit of a mess: a typical landing page might contain some interesting links, sometimes several documents titled “read this first!”, and some information both for the team and for its customers. Such documents start out useful but rapidly turn into disasters; because they become so cumbersome to maintain, they will eventually get so obsolete that they will be fixed by only the brave or the desperate.
Luckily, such documents look intimidating, but are actually straightforward to fix: ensure that a landing page clearly identifies its purpose, and then include only links to other pages for more information. If something on a landing page is doing more than being a traffic cop, it is not doing its job. If you have a separate setup document, link to that from the landing page as a separate document. If you have too many links on the landing page (your page should not scroll multiple screens), consider breaking up the pages by taxonomy, under different sections.
Most poorly configured landing pages serve two different purposes: they are the “goto” page for someone who is a user of your product or API, or they are the home page for a team. Don’t have the page serve both masters --- it will become confusing. Create a separate “team page” as an internal page apart from the main landing page. What the team needs to know is often quite different than what a customer of your API needs to know.

## Documentation Reviews

At Google, all code needs to be reviewed, and our code review process is well understood and accepted. In general, documentation also needs review (though this is less universally accepted). If you want to “test” whether your documentation works, you should generally have someone else review it.
A technical document benefits from three different types of reviews, each emphasizing different aspects:

- A technical review, for accuracy. This review is usually done by a subject matter expert, often another member of your team. Often, this is part of a code review itself.
- An audience review, for clarity. This is usually someone unfamiliar with the domain. This might be someone new to your team or a customer of your API.
- A writing review, for consistency. This is often a technical writer or volunteer.

Of course, some of these lines are sometimes blurred, but if your document is high profile or might end up being externally published, you probably want to ensure that it receives more types of reviews. (We’ve used a similar review process for this book.) Any document tends to benefit from the aforementioned reviews, even if some of those reviews are ad hoc. That said, even getting one reviewer to review your text is preferable to having no one review it.
Importantly, if documentation is tied into the engineering workflow, it will often improve over time. Most documents at Google now implicitly go through an audience review because at some point, their audience will be using them, and hopefully letting you know when they aren’t working (via bugs or other forms of feedback).

----

### Case Study: The Developer Guide Library

As mentioned earlier, there were problems associated with having most (almost all) engineering documentation contained within a shared wiki: little ownership of important documentation, competing documentation, obsolete information, and difficulty in filing bugs or issues with documentation. But this problem was not seen in some documents: the Google C++ style guide was owned by a select group of senior engineers (style arbiters) who managed it. The document was kept in good shape because certain people cared about it. They implicitly owned that document. The document was also canonical: there was only one C++ style guide.
As previously mentioned, documentation that sits directly within source code is one way to promote the establishment of canonical documents; if the documentation sits alongside the source code, it should usually be the most applicable (hopefully). At Google, each API usually has a separate g3doc directory where such documents live (written as Markdown files and readable within our Code Search browser). Having the documentation exist alongside the source code not only establishes de facto ownership, it makes the documentation seem more wholly “part” of the code.
Some documentation sets, however, cannot exist very logically within source code. A “C++ developer guide” for Googlers, for example, has no obvious place to sit within the source code. There is no master “C++” directory where people will look for such information. In this case (and others that crossed API boundaries), it became useful to create standalone documentation sets in their own depot. Many of these culled together associated existing documents into a common set, with common navigation and look-and-feel. Such documents were noted as “Developer Guides” and, like the code in the codebase, were under source control in a specific documentation depot, with this depot organized by topic rather than API. Often, technical writers managed these developer guides, because they were better at explaining topics across API boundaries.
Over time, these developer guides became canonical. Users who wrote competing or supplementary documents became amenable to adding their documents to the canonical document set after it was established, and then deprecating their competing documents. Eventually, the C++ style guide became part of a larger “C++ Developer Guide.” As the documentation set became more comprehensive and more authoritative, its quality also improved. Engineers began logging bugs because they knew someone was maintaining these documents. Because the documents were locked down under source control, with proper owners, engineers also began sending changelists directly to the technical writers.
The introduction of go/ links (see Chapter 3) allowed most documents to, in effect, more easily establish themselves as canonical on any given topic. Our C++ Developer Guide became established at “go/cpp,” for example. With better internal search, go/ links, and the integration of multiple documents into a common documentation set, such canonical documentation sets became more authoritative and robust over time.

----

## Documentation Philosophy

Caveat: the following section is more of a treatise on technical writing best practices (and personal opinion) than of “how Google does it.” Consider it optional for software engineers to fully grasp, though understanding these concepts will likely allow you to more easily write technical information.

### WHO, WHAT, WHEN, WHERE, and WHY

Most technical documentation answers a “HOW” question. How does this work? How do I program to this API? How do I set up this server? As a result, there’s a tendency for software engineers to jump straight into the “HOW” on any given document and ignore the other questions associated with it: the WHO, WHAT, WHEN, WHERE, and WHY. It’s true that none of those are generally as important as the HOW --- a design document is an exception because an equivalent aspect is often the WHY --- but without a proper framing of technical documentation, documents end up confusing. Try to address the other questions in the first two paragraphs of any document:

- WHO was discussed previously: that’s the audience. But sometimes you also need to explicitly call out and address the audience in a document. Example: “This document is for new engineers on the Secret Wizard project.”
- WHAT identifies the purpose of this document: “This document is a tutorial designed to start a Frobber server in a test environment.” Sometimes, merely writing the WHAT helps you frame the document appropriately. If you start adding information that isn’t applicable to the WHAT, you might want to move that information into a separate document.
- WHEN identifies when this document was created, reviewed, or updated. Documents in source code have this date noted implicitly, and some other publishing schemes automate this as well. But, if not, make sure to note the date on which the document was written (or last revised) on the document itself.
- WHERE is often implicit as well, but decide where the document should live. Usually, the preference should be under some sort of version control, ideally with the source code it documents. But other formats work for different purposes as well. At Google, we often use Google Docs for easy collaboration, particularly on design issues. At some point, however, any shared document becomes less of a discussion and more of a stable historical record. At that point, move it to someplace more permanent, with clear ownership, version control, and responsibility.
- WHY sets up the purpose for the document. Summarize what you expect someone to take away from the document after reading it. A good rule of thumb is to establish the WHY in the introduction to a document. When you write the summary, verify whether you’ve met your original expectations (and revise accordingly).

 ### The Beginning, Middle, and End

 All documents --- indeed, all parts of documents --- have a beginning, middle, and end. Although it sounds amazingly silly, most documents should often have, at a minimum, those three sections. A document with only one section has only one thing to say, and very few documents have only one thing to say. Don’t be afraid to add sections to your document; they break up the flow into logical pieces and provide readers with a roadmap of what the document covers.
Even the simplest document usually has more than one thing to say. Our popular “C++ Tips of the Week” have traditionally been very short, focusing on one small piece of advice. However, even here, having sections helps. Traditionally, the first section denotes the problem, the middle section goes through the recommended solutions, and the conclusion summarizes the takeaways. Had the document consisted of only one section, some readers would doubtless have difficulty teasing out the important points.
Most engineers loathe redundancy, and with good reason. But in documentation, redundancy is often useful. An important point buried within a wall of text can be difficult to remember or tease out. On the other hand, placing that point at a more prominent location early can lose context provided later on. Usually, the solution is to introduce and summarize the point within an introductory paragraph, and then use the rest of the section to make your case in a more detailed fashion. In this case, redundancy helps the reader understand the importance of what is being stated.

### The Parameters of Good Documentation

There are usually three aspects of good documentation: completeness, accuracy, and clarity. You rarely get all three within the same document; as you try to make a document more “complete,” for example, clarity can begin to suffer. If you try to document every possible use case of an API, you might end up with an incomprehensible mess. For programming languages, being completely accurate in all cases (and documenting all possible side effects) can also affect clarity. For other documents, trying to be clear about a complicated topic can subtly affect the accuracy of the document; you might decide to ignore some rare side effects in a conceptual document, for example, because the point of the document is to familiarize someone with the usage of an API, not provide a dogmatic overview of all intended behavior.
In each case, a “good document” is defined as the document that is doing its intended job. As a result, you rarely want a document doing more than one job. For each document (and for each document type), decide on its focus and adjust the writing appropriately. Writing a conceptual document? You probably don’t need to cover every part of the API. Writing a reference? You probably want this complete, but perhaps must sacrifice some clarity. Writing a landing page? Focus on organization and keep discussion to a minimum. All of this adds up to quality, which, admittedly, is stubbornly difficult to accurately measure.
How can you quickly improve the quality of a document? Focus on the needs of the audience. Often, less is more. For example, one mistake engineers often make is adding design decisions or implementation details to an API document. Much like you should ideally separate the interface from an implementation within a well- designed API, you should avoid discussing design decisions in an API document. Users don’t need to know this information. Instead, put those decisions in a specialized document for that purpose (usually a design doc).

### Deprecating Documents

Just like old code can cause problems, so can old documents. Over time, documents become stale, obsolete, or (often) abandoned. Try as much as possible to avoid abandoned documents, but when a document no longer serves any purpose, either remove it or identify it as obsolete (and, if available, indicate where to go for new information). Even for unowned documents, someone adding a note that “This no longer works!” is more helpful than saying nothing and leaving something that seems authoritative but no longer works.
At Google, we often attach “freshness dates” to documentation. Such documents note the last time a document was reviewed, and metadata in the documentation set will send email reminders when the document hasn’t been touched in, for example, three months. Such freshness dates, as shown in the following example --- and tracking your documents as bugs --- can help make a documentation set easier to maintain over time, which is the main concern for a document:

```
<!--*
# Document freshness: For more information, see go/fresh-source.
freshness: { owner: `username` reviewed: '2019-02-27' }
*-->
```

Users who own such a document have an incentive to keep that freshness date current (and if the document is under source control, that requires a code review). As a result, it’s a low-cost means to ensure that a document is looked over from time to time. At Google, we found that including the owner of a document in this freshness date within the document itself with a byline of “Last reviewed by...” led to increased adoption as well.

## When Do You Need Technical Writers?

When Google was young and growing, there weren’t enough technical writers in software engineering. (That’s still the case.) Those projects deemed important tended to receive a technical writer, regardless of whether that team really needed one. The idea was that the writer could relieve the team of some of the burden of writing and maintaining documents and (theoretically) allow the important project to achieve greater velocity. This turned out to be a bad assumption.
We learned that most engineering teams can write documentation for themselves (their team) perfectly fine; it’s only when they are writing documents for another audience that they tend to need help because it’s difficult to write to another audience. The feedback loop within your team regarding documents is more immediate, the domain knowledge and assumptions are clearer, and the perceived needs are more obvious. Of course, a technical writer can often do a better job with grammar and organization, but supporting a single team isn’t the best use of a limited and specialized resource; it doesn’t scale. It introduced a perverse incentive: become an important project and your software engineers won’t need to write documents. Discouraging engineers from writing documents turns out to be the opposite of what you want to do.
Because they are a limited resource, technical writers should generally focus on tasks that software engineers don’t need to do as part of their normal duties. Usually, this involves writing documents that cross API boundaries. Project Foo might clearly know what documentation Project Foo needs, but it probably has a less clear idea what Project Bar needs. A technical writer is better able to stand in as a person unfamiliar with the domain. In fact, it’s one of their critical roles: to challenge the assumptions your team makes about the utility of your project. It’s one of the reasons why many, if not most, software engineering technical writers tend to focus on this specific type of API documentation.

## Conclusion

Google has made good strides in addressing documentation quality over the past decade, but to be frank, documentation at Google is not yet a first-class citizen. For comparison, engineers have gradually accepted that testing is necessary for any code change, no matter how small. As well, testing tooling is robust, varied and plugged into an engineering workflow at various points. Documentation is not ingrained at nearly the same level.
To be fair, there’s not necessarily the same need to address documentation as with testing. Tests can be made atomic (unit tests) and can follow prescribed form and function. Documents, for the most part, cannot. Tests can be automated, and schemes to automate documentation are often lacking. Documents are necessarily subjective; the quality of the document is measured not by the writer, but by the reader, and often quite asynchronously. That said, there is a recognition that documentation is important, and processes around document development are improving. In this author’s opinion, the quality of documentation at Google is better than in most software engineering shops.
To change the quality of engineering documentation, engineers --- and the entire engineering organization --- need to accept that they are both the problem and the solution. Rather than throw up their hands at the state of documentation, they need to realize that producing quality documentation is part of their job and saves them time and effort in the long run. For any piece of code that you expect to live more than a few months, the extra cycles you put in documenting that code will not only help others, it will help you maintain that code as well.


## TL;DRs

- Documentation is hugely important over time and scale.
- Documentation changes should leverage the existing developer workflow.
- Keep documents focused on one purpose.
- Write for your audience, not yourself.













-----

1 OK, you will need to maintain it and revise it occasionally.
2 English is still the primary language for most programmers, and most technical documentation for programmers relies on an understanding of English.
3 When we deprecated GooWiki, we found that around 90% of the documents had no views or updates in the previous few months.




