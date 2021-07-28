# 知識の共有

Written by Nina Chen and Mark Barolak
Edited by Riona MacNamara

あなたの組織は、インターネット上のランダムな人よりも、あなたの問題領域をよく理解しており、あなたの組織は自分自身の質問のほとんどに答えることができるはずです。そのためには、質問の答えを知っている専門家と、その知識を配布する仕組みが必要です。これらのメカニズムは、（質問をする、知っていることを書き留める）という極めて単純なものから、チュートリアルやクラスのようなはるかに構造的なものまで様々です。しかし、最も重要なことは、組織が学習する文化を持つことであり、そのためには、人々が知識の欠如を認めることができる心理的な安全性を作り出す必要がある。

## 学習への挑戦

組織全体で専門知識を共有することは、簡単なことではありません。学習するという強い文化がなければ、課題が出てきます。Googleは、特に会社の規模が大きくなるにつれて、このような課題を数多く経験してきました。

* 心理的安全性の欠如
  * 罰を受けることを恐れて、人前でリスクを取ったり、ミスをしたりすることができない環境。これはしばしば、恐怖の文化や透明性を避ける傾向として現れます。
* 情報の島
  * 組織の中で、相互に連絡を取らず、共有のリソースを使用しない異なる部分で発生する知識の断片化。このような環境では、それぞれのグループが独自のやり方を確立しています(*1)。 このような環境では、次のようなことがよく起こります。
  * 情報の断片化
    * 情報の断片化：それぞれの島が全体を把握できていない。
  * 情報の重複
    * それぞれの島が自分のやり方を再構築する。
  * 情報の偏り
    * それぞれの島には同じことをする独自の方法があり、それらは衝突するかもしれませんし、しないかもしれません。
* シングルポイントオブフェイル(SPOF)
  * 重要な情報が一人の人間からしか得られない場合に発生するボトルネックのこと。これは、第2章で詳しく説明するバスファクターと関連しています。
  * SPOFは善意で発生することもあります。"私に任せてください "という習慣に陥りがちです。しかし、このアプローチは、短期的な効率性（「私がやったほうが早い」）を最適化しますが、長期的なスケーラビリティが損なわれます（チームは、やらなければならないことがあっても、そのやり方を学ぶことができません）。また、このような考え方は、All-or-nothingの専門知識につながりがちです。
* All-or-nothing 専門知識
  * 何でも知っている人と初心者に分かれ、中間がほとんどない集団のこと。この問題は、専門家が常にすべてを自分で行い、メンタリングやドキュメンテーションを通じて新しい専門家を育成する時間を取らない場合に、さらに深刻化します。このシナリオでは、すでに専門知識を持っている人に知識と責任が蓄積されていき、新しいチームメンバーや初心者は自力で何とかしなければならず、成長が遅れることになります。
* オウム返し
  * 理解せずに真似をすること。これは一般的に、目的を理解せずにパターンやコードを無意識にコピーすることを特徴とし、しばしばそのコードが未知の理由で必要であると仮定する。
* Haunted graveyards
  * 何か問題が起こるのではないかと恐れて、人々が触ったり変更したりするのを避ける場所、しばしばコードの中にあります。前述のパロディとは異なり、Haunted graveyardsは、人々が恐怖や迷信のために行動を避けるという特徴があります。

本章の残りの部分では、Googleのエンジニアリング組織がこれらの課題に対処するために成功した戦略を紹介します。

## 哲学

ソフトウェアエンジニアリングとは、多バージョンのプログラムを多人数で開発することと定義できます(*2)。コードは重要なアウトプットですが、製品を作る上ではごく一部に過ぎません。重要なのは、コードは何もないところから自然に出てくるものではなく、また専門知識もないということです。専門家もかつては初心者でした。組織の成功は、人材の成長と投資にかかっているのです。

専門家からの一対一のアドバイスは、常に貴重なものです。チームのメンバーによって専門分野が異なるため、どのような質問をするのが最適かは異なります。しかし、専門家が休暇を取ったり、チームを変えたりすると、チームは孤立してしまいます。また、一人の人間が一対多の個人的なヘルプを提供することはできるかもしれませんが、これはスケールしませんし、少数の "多 "に限定されます。

一方、文書化された知識は、チームだけではなく、組織全体へのスケールアップにも有効です。チームウィキのような仕組みを使えば、多くの著者が自分の専門知識をより大きなグループと共有することができます。しかし、文書化された知識は一対一の会話よりも拡張性が高いとはいえ、その拡張性にはいくつかのトレードオフがある。一般化されていて個々の学習者の状況には適用できないかもしれないし、長期間にわたって情報を適切に更新し続けるためのメンテナンスコストもかかる。

部族的な知識は、個々のチームメンバーが知っていることと、文書化されていることの間のギャップに存在します。人間の専門家は、文書化されていないことを知っています。その知識を文書化して維持すれば、その専門家と直接1対1で接することができる人だけでなく、文書を見つけて見ることができる人なら誰でも利用できるようになります。

では、すべてが常に完璧ですぐに文書化される魔法の世界では、人に相談する必要はなくなるのでしょうか？そうではありません。文書化された知識にはスケールメリットがありますが、対象となる人間の助けも同様です。人間の専門家は、その膨大な知識を統合することができます。その人のユースケースにどのような情報が当てはまるのかを評価し、そのドキュメントが今も有効かどうかを判断し、どこにあるのかを知ることができるのです。また、彼らが答えを見つける場所を知らなくても、知っている人を知っているかもしれません。

部族間の知識と文書化された知識はお互いに補完し合います。文書化された完璧なエキスパートチームであっても、互いにコミュニケーションをとり、他のチームと調整し、時間の経過とともに戦略を変えていく必要があります。すべてのタイプの学習に適した知識共有の方法はなく、組織によって最適な組み合わせは異なります。組織の知識は時間とともに進化するため、組織にとって最適な知識共有の方法は、組織の成長とともに変化する可能性があります。トレーニングを行い、学習と成長に焦点を当て、独自のエキスパート集団を構築してください。

## Setting the Stage: 心理的安全性

学習環境を整えるためには、心理的安全性が重要です。

学ぶためには、まず自分が理解できないことがあることを認めなければなりません。私たちは、そのような正直さを罰するのではなく、歓迎すべきです。(Googleはこの点をよく理解していますが、エンジニアは理解していないことを認めたがらないことがあります。)

学ぶということは、何かに挑戦し、失敗しても大丈夫だと思えることが非常に重要です。健全な環境では、人々は安心して質問したり、間違ったことをしたり、新しいことを学んだりすることができます。実際、心理的安全性は効果的なチームにとって最も重要な要素であることが、グーグルの調査で明らかになっています。

## メンターシップ

グーグルでは、「ヌーグラー」（新しいグーグル人）と呼ばれるエンジニアが入社してきた時点で、その調子を整えようとしています。心理的な安全性を構築するための重要な方法の1つは、ヌーグラーにメンターを割り当てることです。メンターは、チームメンバー、マネージャー、技術リーダーではなく、質問に答えたり、ヌーグラーが立ち上がるのを支援することを明確に責務としています。メンターがいることで、新人は助けを求めやすくなり、同僚の時間を奪うことを心配しなくてもよくなります。

メンターとは、グーグルに1年以上勤務しているボランティアのことで、グーグルのインフラの使い方やグーグルの文化を理解するためのアドバイスをしてくれます。重要なのは、メンティーが他に誰に相談していいかわからないときに、メンターがセーフティネットとして相談に乗ってくれることです。このメンターはメンティーと同じチームではないので、メンティーは厄介な状況でも安心して助けを求めることができます。

メンターシップは学習を公式化し、促進するものですが、学習自体は継続的なプロセスでもあります。組織に加わった新入社員であれ、新しい技術を学んでいる経験豊富なエンジニアであれ、同僚が互いに学び合う機会は常にあります。健全なチームであれば、チームメイトは質問に答えるだけでなく、知らないことを教えてもらい、お互いに学び合うことができます。

## 大人数での心理的安全性

近くにいるチームメイトに助けを求めるのは、ほとんど見知らぬ人ばかりの大集団に近づくよりも簡単です。しかし、これまで見てきたように、1対1の解決策では拡張性がありません。グループでの解決策は拡張性がありますが、怖さもあります。初心者にとって、質問を作って大勢の人に尋ねることは、その質問が何年も保存されるかもしれないと思うと、怖くてできません。大人数のグループでは、心理的な安全性の必要性が高まります。初心者が自信を持って質問でき、新進気鋭の専門家が既存の専門家に自分の答えを攻撃される心配をせずに、新参者を助ける力を得られるような安全な環境を作り、維持するために、グループのメンバー全員が役割を担っている。

このような安全で快適な環境を実現するための最も重要な方法は、グループの相互作用が敵対的ではなく協力的であることである。表3-1は、グループ相互作用の推奨パターン（およびそれに対応するアンチパターン）のいくつかの例を示している。

表3-1. グループの相互作用パターン
| 推奨パターン(協力) | アンチパターン(敵対的) |
|:---|:---|
| 基本的な疑問やミスを適切な方向に導く | 基本的な質問や間違いが取り上げられ、質問者が叱られる |
| 説明は、質問者の学習に役立つことを意図して行われます。 | 自分の知識を誇示するために説明をする | 
| 対応が親切、忍耐強い、親切 | 対応は慇懃無礼で鼻持ちならない、建設的でないものです。 |
| インタラクションは、解決策を見出すための共有された議論です。 | インタラクションは、"勝者 "と "敗者 "がいる議論である。 |

このようなアンチパターンは、意図せずに発生することがあります。誰かが親切にしようとしているのに、誤って慇懃無礼な態度をとってしまうことがあるのです。ここでは、Recurse Centerの社会的ルールが役に立ちます。

* 驚いたふりをしない（「えっ、スタックが何か知らないなんて信じられない！」）。
  * 驚きを装うことは、心理的安全性の障害となり、グループのメンバーは知識の欠如を認めることを恐れます。
* "well-actually "を使わない
  * 正確さよりも大げさになりがちな衒学的な修正。
* バックシートドライビングの禁止
  * 既存の議論を中断して、会話にコミットすることなく意見を述べること。
* 微妙な「-イズム」（「おばあちゃんにもできるくらい簡単だよ！」）の禁止
  * 歓迎されていない、軽蔑されている、安全でないと個人が感じるような小さな偏見（人種差別、年齢差別、同性愛嫌悪）の表現。

## Growing Your Knowledge

知識の共有は、まず自分自身から始まります。常に学ぶべきことがあることを認識することが重要です。以下のガイドラインは、あなたが自分自身の知識を増やすためのものです。

## 質問する

この章から得られるものが一つだけあるとすれば、それは「常に学び、常に質問をする」ということです。

私たちは、Nooglerたちに、立ち上げには約6ヶ月かかると言っています。これは、Googleの大規模で複雑なインフラに対応するために必要な期間ですが、同時に、学習は継続的かつ反復的なプロセスであるという考えを強化するためでもあります。初心者が陥りがちなミスのひとつに、行き詰まったときに助けを求めないことがあります。一人で解決しようとしたり、自分の質問が "単純すぎる "と恐れたりすることがあるかもしれません。「誰かに助けを求める前に、もっと頑張らないと」と思うかもしれません。しかし、これではいけません。同僚が最高の情報源であることが多いので、この貴重なリソースを活用してください。

どんな状況でも、突然、何をすべきか正確にわかるようになる魔法のような日はありませんし、学ぶべきことは常にあります。Googleで何年も働いているエンジニアでも、自分が何をしているのか分からない分野があるものですが、それは構いません。恐れずに "それが何なのかわかりませんが、説明してもらえますか？"と言ってください。知らないことを恐れるのではなく、チャンスだと思ってください(*3)。

新入社員であろうと、シニアリーダーであろうと、常に学ぶべきことがある環境に身を置くべきである。そうでなければ、停滞してしまいます（新しい環境を探すべきです）。

特に、リーダーとしての役割を担う人は、この行動の模範となることが重要です。「年長者」を「何でも知っている」と誤解してはいけません。実際には、知れば知るほど、知らないことが多くなるものです。質問(*4)や知識の不足を率直に伝えることで、他の人も同じことをしてもいいんだという意識を持つことができます。

また、質問を受ける側も、根気よく親切に答えてあげることで、安心して相談できる環境が整います。質問をするのをためらう気持ちを抑えて、積極的に質問をし、些細な質問にも答えられるようにしておくことが大切なのです。エンジニアは部族的な知識を自分で理解することができるかもしれませんが、彼らは孤立して仕事をするためにいるわけではありません。的確なサポートがあれば、エンジニアの生産性が向上し、チーム全体の生産性も向上します。

## Understand Context

学習とは、単に新しいものを理解することだけではなく、既存のものの設計や実装の背後にある意思決定を理解することも含まれます。例えば、あなたのチームが何年も前から存在する重要なインフラのレガシーコードベースを引き継いだとします。オリジナルの作者はとっくに亡くなっていて、コードは理解しにくいものです。既存のコードを学ぶのに時間をかけるよりも、ゼロから書き直したいと思うかもしれません。しかし、「わからない」と思ってそこで考えを終わらせるのではなく、もっと深く掘り下げてみましょう：どんな質問をすればいいのか？

「チェスターソンの柵」の原則を考えてみましょう。何かを削除したり変更したりする前に、まずなぜそれがあるのかを理解するのです。

> 物事を変形させるのではなく、改革するという問題には、一つの単純明快な原則があります。それは、おそらくパラドックスと呼ばれるであろう原則です。このような場合には、ある種の制度や法律が存在しています。単純化のために、道路を横切って建てられたフェンスやゲートのことを考えてみましょう。現代的なタイプの改革者は、そのフェンスに近づいて、「こんなものは使い道がない、なくしてしまおう」と言います。これに対して、より知的なタイプの改革者は、こう答えるのがよいでしょう。「もし、あなたがそれの用途を見出せないなら、私は確かにあなたにそれを片付けさせません。どこかに行って考えなさい。そして、戻ってきて、使い道があると言ってくれたら、壊してもいいかもしれません」。

これは、コードが明確でなかったり、既存のデザインパターンが間違っていたりすることがないという意味ではありませんが、エンジニアは、特に慣れないコードや言語、パラダイムに対しては、正当な理由がある場合よりもはるかに早く「これはダメだ！」と言ってしまう傾向があります。Googleもこの傾向があります。特に、普通ではないと思われる決定については、文脈を探り、理解します。コードの文脈と目的を理解した後、その変更がまだ意味のあるものかどうかを考えます。意味がある場合はそのまま変更し、意味がない場合はその理由を文書化して後の読者に伝えます。

Googleのスタイルガイドの多くは、恣意的なルールのリストを暗記するのではなく、読者がスタイルガイドラインの背景にある理由を理解できるよう、文脈を明示しています。さらに言えば、あるガイドラインの背後にある理論的根拠を理解することで、著者は、そのガイドラインを適用すべきでない場合や、ガイドラインの更新が必要な場合に、十分な情報に基づいた判断を下すことができます。第8章をご覧ください。

## Scaling Your Questions: コミュニティに聞く

一対一でサポートを受けることは、帯域は広くても規模は限られています。また、学習者としては、すべての詳細を覚えておくことは難しいでしょう。一対一のディスカッションで何かを学んだら、それを書き留めておいてください。

将来の自分のためにも、1対1のディスカッションで何かを学んだら、それを書き留めておきましょう。その人たちのためにも、メモしたものを共有しましょう。

受け取った答えを共有することも有益ですが、個人ではなくコミュニティに助けを求めることも有益です。このセクションでは、コミュニティ・ベースド・ラーニングのさまざまな形態を紹介します。グループチャット、メーリングリスト、質疑応答システムなど、それぞれのアプローチには異なるトレードオフがあり、お互いに補完し合っています。しかし、これらのアプローチは、知識を求める人が、仲間や専門家のより広いコミュニティから助けを得ることを可能にし、また、そのコミュニティの現在および将来のメンバーが回答を広く利用できるようにするものである。

### グループチャット

質問があるときに、適切な人に助けを求めるのは難しいことがあります。誰が答えを知っているのかわからない、聞きたい人が忙しいなどの理由からです。そんなとき、グループチャットはとても便利です。一度に多くの人に質問をすることができ、手の空いている人とすぐに会話をすることができます。おまけに、グループチャットの他のメンバーは、その質問と回答から学ぶことができますし、多くの形式のグループチャットは自動的にアーカイブされ、後で検索することができます。

グループチャットは、トピックまたはチームのいずれかに専念する傾向があります。トピック中心のグループチャットは、通常、誰もが質問をするために立ち寄ることができるように開かれています。専門家が集まる傾向にあり、かなりの規模になることもあるので、質問にはすぐに答えられるのが普通です。一方、チーム指向のチャットは小規模で、メンバーを限定する傾向があります。その結果、トピック中心のチャットほどのリーチはありませんが、その小ささは新規参入者にとって安心感があります。

グループチャットは素早い質問には適していますが、あまり構造化されていないため、自分が積極的に参加していない会話から意味のある情報を引き出すことが難しくなります。グループの外で情報を共有したり、後で参照できるようにしたりする必要がある場合は、すぐに文書を書いたり、メーリングリストにメールを送ったりするべきです。

### メーリングリスト

Google のほとんどのトピックには、topic-users@ や topic-discuss@ といった Google Groups のメイ リングリストがあり、社内の誰もが参加したりメールを送ったりすることができます。公開されているメーリングリストで質問をすることは、グループチャットで質問をするのとよく似ています。質問が多くの人に届き、その質問に答えられる可能性があり、メーリングリストをフォローしている人はその答えから学ぶことができます。しかし、グループチャットとは異なり、公開されたメーリングリストはより多くの人と共有することができます。Googleでは、メーリングリストはインデックス化されており、Googleのイントラネット検索エンジンであるMomaで検索することができます。

メーリングリストで質問したことに対する答えが見つかると、仕事に取り掛かりたくなることがあります。しかし、それはやめましょう。将来、誰かが同じ情報を必要とするかどうかはわかりませんから、答えをメーリングリストに投稿するのが最善の方法です。

メーリングリストには、トレードオフがないわけではありません。多くの背景を必要とする複雑な質問には適していますが、グループチャットが得意とする迅速な前後関係のやり取りには不便です。特定の問題に関するスレッドは、通常、そのスレッドがアクティブである間が最も有効です。電子メールのアーカイブは不変であり、古いスレッドで見つけた答えが現在の状況に関連しているかどうかを判断するのは難しいことです。また、誰かが自分のワークフローで抱えている問題は、自分には当てはまらないかもしれないので、正式なドキュメントのような他のメディアよりもS/N比が低くなることがあります。

---

### Googleでのメール

Google の文化は、悪名高いように、メール中心でメールを多用しています。Google のエンジニアは毎日何百通ものメールを受け取りますが、そのアクションの度合いは様々です。自動登録したグループから送られてくる大量の通知に対応するために、メールフィルターの設定だけで何日も費やしてしまうNooglerもいますし、あきらめて流れに乗らない人もいます。一部のグループでは、大規模なメーリングリストをデフォルトですべてのディスカッションに参加させ、特に興味を持ちそうな人に情報を絞り込もうとしません。

Googleはデフォルトでメールベースのワークフローを採用しています。これは、必ずしもメールが他のコミュニケーション手段よりも優れているからというわけではありません（しばしばそうではありません）。このことを念頭に置いて、どのようなコミュニケーション手段を奨励したり、投資したりするかを検討してみてください。

---

### YAQS: 質問・回答プラットフォーム

YAQS ("Yet Another Question System")は、Stack OverflowのようなウェブサイトのGoogle内部版で、Googlerが既存のコードやワークインプログレスのコードにリンクしたり、機密情報を話し合ったりすることを容易にします。

YAQSはStack Overflowと同様に、メーリングリストの利点を多く共有し、さらに改良を加えています。有用であるとマークされた回答はユーザーインターフェースで促進され、ユーザーは質問と回答を編集することができるため、コードや事実が変化しても正確で有用な回答を維持することができます。その結果、いくつかのメーリングリストはYAQSに取って代わられましたが、他のメーリングリストは問題解決に焦点を当てていない、より一般的なディスカッションリストに進化しました。

## Scaling Your Knowledge: 教えるべきことは常にある

教えることは専門家に限ったことではありませんし、専門知識は初心者か専門家かの二元的な状態ではありません。専門知識とは、自分が何を知っているかという多次元的なベクトルであり、誰もがさまざまな分野でさまざまなレベルの専門知識を持っています。これが、組織の成功に多様性が欠かせない理由の一つです。人によって異なる視点や専門知識がもたらされるからです（第4章参照）。グーグルのエンジニアは、オフィスアワー、技術講演、授業、ドキュメントの作成、コードのレビューなど、さまざまな方法で人に教えています。

### オフィスアワー

時には、人間と話をすることが本当に重要なことがあります。そのような場合には、オフィスアワーが有効な手段となります。オフィスアワーとは、定期的に（通常は毎週）開催されるイベントで、1人または複数の人が特定のトピックについての質問に答えるために自分の席を用意します。緊急の質問がある場合、次のセッションまで回答を待つのは苦痛ですし、オフィスアワーを開催する場合は、時間がかかり、定期的に宣伝する必要があります。しかし、オフィスアワーは、専門家と直接話をすることができます。例えば、新しいサービスの設計を始めたばかりで、何を質問すればいいのかわからないような曖昧な問題の場合や、ドキュメントがないような特殊な問題の場合には、特に有効です。

### 技術講演と授業

Googleでは、社内外(*5)で技術講演や授業を行う文化が根付いています。engEDU (Engineering Education) チームは、グーグルのエンジニアから世界中の学生まで、多くの人々にコンピュータサイエンスの教育を提供することに注力しています。このプログラムは大きな成功を収めており、何千人ものグーグル社員が、技術的なトピック（例：「最新のCPUにおけるベクタライゼーションの理解」）から、気軽に楽しめるトピック（例：「初心者向けのスウィングダンス」）まで、さまざまなトピックを教えています(*6)。

技術講演では、講演者が聴衆に向かって直接プレゼンテーションを行うのが一般的です。一方、クラスは、講義の要素もありますが、クラス内での演習が中心となるため、参加者の積極的な参加が必要となります。そのため、インストラクター主導のクラスは、テックトークに比べて作成や維持にかかる費用が大きく、最も重要で難しいテーマに限られます。しかし、クラスを作成した後は、多くのインストラクターが同じ教材を使ってクラスを教えることができるため、比較的簡単に規模を拡大することができます。私たちは、次のような状況下で、クラスが最も効果的に機能することを発見しました。

- このテーマは複雑なので、誤解が生じることが多い。クラスの作成には多大な労力を要するため、特定のニーズに対応する場合にのみ開発すべきである。
- トピックが比較的安定している。クラスの教材を更新するのは大変なので、テーマが急速に進化している場合は、他の方法で知識を共有した方が効果的です。
- 教師が質問に答えたり、個人的なサポートをしたりすることで、そのトピックにメリットがある。生徒が指示された助けを借りずに簡単に学べる場合は、ドキュメントや録音のようなセルフサービス型の媒体の方が効率的です。Googleの入門講座にも自習用のものが多数あります。
- そのクラスを定期的に提供するだけの需要があります。そうでなければ、潜在的な学習者は、授業を待たずに他の方法で必要な情報を手に入れてしまうだろう。グーグルでは、地理的に離れた小規模なオフィスでは、特にこの点が問題となる。

### ドキュメント

ドキュメンテーションとは、読者が何かを学ぶのを助けることを主な目的とした、書かれた知識のことです。文書化された知識のすべてが必ずしも文書化されているわけではありませんが、文書化された知識は記録として有用な場合があります。例えば、メーリングリストのスレッドで問題の答えを見つけることは可能ですが、スレッドでの最初の質問の主な目的は、答えを探すことであり、他の人のために議論を記録することは二の次でした。

このセクションでは、タイプミスの修正のような小さなことから、部族間の知識の文書化のような大きな取り組みまで、正式な文書に貢献したり作成したりする機会を見つけることに焦点を当てます。

> ドキュメントについてのより包括的な議論は、第10章を参照してください。

### ドキュメントの更新

最初に何かを学んだときは、既存のドキュメントやトレーニング教材を改善するための最良のタイミングです。新しいプロセスやシステムを吸収して理解した頃には、何が難しかったのか、「はじめに」のドキュメントに欠けていた簡単なステップは何だったのか、忘れてしまっているかもしれません。この段階で、もし資料に間違いや抜けがあれば、それを修正しましょう。また、ドキュメントの所有者が組織内の別の場所であっても、自分でドキュメントを更新するようにしましょう(*7)。

グーグルでは、エンジニアはドキュメントの所有者が誰であるかに関わらず、ドキュメントを更新する権限を与えられていると感じており、タイプミスを修正する程度の小さな修正であっても、しばしば更新している。このようなコミュニティの維持管理は、g3doc(*8)の導入により、グーグルのエンジニアが自分の提案をレビューしてくれるドキュメントの所有者を見つけるのが非常に簡単になったことで顕著になりました。また、コードと同じように、変更履歴を監査可能な形で残すことができます。

### Creating documentation

As your proficiency grows, write your own documentation and update existing docs. For example, if you set up a new development flow, document the steps. You can then make it easier for others to follow in your path by pointing them to your document. Even better, make it easier for people to find the document themselves. Any sufficiently undiscoverable or unsearchable documentation might as well not exist. This is another area in which g3doc shines because the documentation is predictably located right next to the source code, as opposed to off in an (unfindable) document or webpage somewhere.
Finally, make sure there’s a mechanism for feedback. If there’s no easy and direct way for readers to indicate that documentation is outdated or inaccurate, they are likely not to bother telling anyone, and the next newcomer will come across the same problem. People are more willing to contribute changes if they feel that someone will actually notice and consider their suggestions. At Google, you can file a documentation bug directly from the document itself.
In addition, Googlers can easily leave comments on g3doc pages. Other Googlers can see and respond to these comments and, because leaving a comment automatically files a bug for the documentation owner, the reader doesn’t need to figure out who to contact.
Promoting documentation
Traditionally, encouraging engineers to document their work can be difficult. Writing documentation takes time and effort that could be spent on coding, and the benefits that result from that work are not immediate and are mostly reaped by others. Asymmetrical trade-offs like these are good for the organization as a whole given that many people can benefit from the time investment of a few, but without good incentives, it can be challenging to encourage such behavior. We discuss some of these structural incentives in the section “Incentives and recognition” on page 57.
However, a document author can often directly benefit from writing documentation. Suppose that team members always ask you for help debugging certain kinds of production failures. Documenting your procedures requires an upfront investment of time, but after that work is done, you can save time in the future by pointing team members to the documentation and providing hands-on help only when needed.
Writing documentation also helps your team and organization scale. First, the information in the documentation becomes canonicalized as a reference: team members can refer to the shared document and even update it themselves. Second, the canonicalization may spread outside the team. Perhaps some parts of the documentation are not unique to the team’s configuration and become useful for other teams looking to resolve similar problems.

### Code

At a meta level, code is knowledge, so the very act of writing code can be considered a form of knowledge transcription. Although knowledge sharing might not be a direct intent of production code, it is often an emergent side effect, which can be facilitated by code readability and clarity.
Code documentation is one way to share knowledge; clear documentation not only benefits consumers of the library, but also future maintainers. Similarly, implementation comments transmit knowledge across time: you’re writing these comments expressly for the sake of future readers (including Future You!). In terms of trade- offs, code comments are subject to the same downsides as general documentation: they need to be actively maintained or they can quickly become out of date, as anyone who has ever read a comment that directly contradicts the code can attest.
Code reviews (see Chapter 9) are often a learning opportunity for both author(s) and reviewer(s). For example, a reviewer’s suggestion might introduce the author to a new testing pattern, or a reviewer might learn of a new library by seeing the author use it in their code. Google standardizes mentoring through code review with the readability process, as detailed in the case study at the end of this chapter.

## Scaling Your Organization’s Knowledge

Ensuring that expertise is appropriately shared across the organization becomes more difficult as the organization grows. Some things, like culture, are important at every stage of growth, whereas others, like establishing canonical sources of information, might be more beneficial for more mature organizations.

### Cultivating a Knowledge-Sharing Culture

Organizational culture is the squishy human thing that many companies treat as an afterthought. But at Google, we believe that focusing on the culture and environment first(*9) results in better outcomes than focusing on only the output—such as the code— of that environment.
Making major organizational shifts is difficult, and countless books have been written on the topic. We don’t pretend to have all the answers, but we can share specific steps Google has taken to create a culture that promotes learning.
See the book Work Rules!(*10) for a more in-depth examination of Google’s culture.

#### Respect

The bad behavior of just a few individuals can make an entire team or community unwelcoming. In such an environment, novices learn to take their questions elsewhere, and potential new experts stop trying and don’t have room to grow. In the worst cases, the group reduces to its most toxic members. It can be difficult to recover from this state.
Knowledge sharing can and should be done with kindness and respect. In tech, tolerance—or worse, reverence—of the “brilliant jerk” is both pervasive and harmful, but being an expert and being kind are not mutually exclusive. The Leadership section of Google’s software engineering job ladder outlines this clearly:

  Although a measure of technical leadership is expected at higher levels, not all leadership is directed at technical problems. Leaders improve the quality of the people around them, improve the team’s psychological safety, create a culture of teamwork and collaboration, defuse tensions within the team, set an example of Google’s culture and values, and make Google a more vibrant and exciting place to work. Jerks are not good leaders.

This expectation is modeled by senior leadership: Urs Hölzle (Senior Vice President of Technical Infrastructure) and Ben Treynor Sloss (Vice President, Founder of Google SRE) wrote a regularly cited internal document (“No Jerks”) about why Googlers should care about respectful behavior at work and what to do about it.

#### Incentives and recognition

Good culture must be actively nurtured, and encouraging a culture of knowledge sharing requires a commitment to recognizing and rewarding it at a systemic level. It’s a common mistake for organizations to pay lip service to a set of values while actively rewarding behavior that does not enforce those values. People react to incentives over platitudes, and so it’s important to put your money where your mouth is by putting in place a system of compensation and awards.
Google uses a variety of recognition mechanisms, from company-wide standards such as performance review and promotion criteria to peer-to-peer awards between Googlers.
Our software engineering ladder, which we use to calibrate rewards like compensation and promotion across the company, encourages engineers to share knowledge by noting these expectations explicitly. At more senior levels, the ladder explicitly calls out the importance of wider influence, and this expectation increases as seniority increases. At the highest levels, examples of leadership include the following:

- Growing future leaders by serving as mentors to junior staff, helping them develop both technically and in their Google role
- Sustaining and developing the software community at Google via code and design reviews, engineering education and development, and expert guidance to others in the field

> See Chapters 5 and 6 for more on leadership.

Job ladder expectations are a top-down way to direct a culture, but culture is also formed from the bottom up. At Google, the peer bonus program is one way we embrace the bottom-up culture. Peer bonuses are a monetary award and formal recognition that any Googler can bestow on any other Googler for above-and-beyond work.(*11) For example, when Ravi sends a peer bonus to Julia for being a top contributor to a mailing list—regularly answering questions that benefit many readers—he is publicly recognizing her knowledge-sharing work and its impact beyond her team. Because peer bonuses are employee driven, not management driven, they can have an important and powerful grassroots effect.
Similar to peer bonuses are kudos: public acknowledgement of contributions (typically smaller in impact or effort than those meriting a peer bonus) that boost the visibility of peer-to-peer contributions.
When a Googler gives another Googler a peer bonus or kudos, they can choose to copy additional groups or individuals on the award email, boosting recognition of the peer’s work. It’s also common for the recipient’s manager to forward the award email to the team to celebrate one another’s achievements.
A system in which people can formally and easily recognize their peers is a powerful tool for encouraging peers to keep doing the awesome things they do. It’s not the bonus that matters: it’s the peer acknowledgement.

### Establishing Canonical Sources of Information

Canonical sources of information are centralized, company-wide corpuses of information that provide a way to standardize and propagate expert knowledge. They work best for information that is relevant to all engineers within the organization, which is otherwise prone to information islands. For example, a guide to setting up a basic developer workflow should be made canonical, whereas a guide for running a local Frobber instance is more relevant just to the engineers working on Frobber.
Establishing canonical sources of information requires higher investment than maintaining more localized information such as team documentation, but it also has broader benefits. Providing centralized references for the entire organization makes broadly required information easier and more predictable to find and counters problems with information fragmentation that can arise when multiple teams grappling with similar problems produce their own—often conflicting—guides.
Because canonical information is highly visible and intended to provide a shared understanding at the organizational level, it’s important that the content is actively maintained and vetted by subject matter experts. The more complex a topic, the more critical it is that canonical content has explicit owners. Well-meaning readers might see that something is out of date but lack the expertise to make the significant structural changes needed to fix it, even if tooling makes it easy to suggest updates.
Creating and maintaining centralized, canonical sources of information is expensive and time consuming, and not all content needs to be shared at an organizational level. When considering how much effort to invest in this resource, consider your audience. Who benefits from this information? You? Your team? Your product area? All engineers?

#### Developer guides

Google has a broad and deep set of official guidance for engineers, including style guides, official software engineering best practices,(*12) guides for code review(*13) and testing,(*14) and Tips of the Week (TotW).(*15)
The corpus of information is so large that it’s impractical to expect engineers to read it all end to end, much less be able to absorb so much information at once. Instead, a human expert already familiar with a guideline can send a link to a fellow engineer, who then can read the reference and learn more. The expert saves time by not needing to personally explain a company-wide practice, and the learner now knows that there is a canonical source of trustworthy information that they can access whenever necessary. Such a process scales knowledge because it enables human experts to recognize and solve a specific information need by leveraging common, scalable resources.

#### go/ links

go/ links (sometimes referred to as goto/ links) are Google’s internal URL shortener.(*16)
Most Google-internal references have at least one internal go/ link. For example, “go/ spanner” provides information about Spanner, “go/python” is Google’s Python developer guide. The content can live in any repository (g3doc, Google Drive, Google Sites, etc.), but having a go/ link that points to it provides a predictable, memorable way to access it. This yields some nice benefits:

- go/ links are so short that it’s easy to share them in conversation (“You should check out go/frobber!”). This is much easier than having to go find a link and then send a message to all interested parties. Having a low-friction way to share references makes it more likely that that knowledge will be shared in the first place.
- go/ links provide a permalink to the content, even if the underlying URL changes. When an owner moves content to a different repository (for example, moving content from a Google doc to g3doc), they can simply update the go/ link’s target URL. The go/ link itself remains unchanged.

go/ links are so ingrained into Google culture that a virtuous cycle has emerged: a Googler looking for information about Frobber will likely first check go/frobber. If the go/ link doesn’t point to the Frobber Developer Guide (as expected), the Googler will generally configure the link themselves. As a result, Googlers can usually guess the correct go/ link on the first try.

## Codelabs

Google codelabs are guided, hands-on tutorials that teach engineers new concepts or processes by combining explanations, working best-practice example code, and code exercises.(*17) A canonical collection of codelabs for technologies broadly used across Google is available at go/codelab. These codelabs go through several rounds of formal review and testing before publication. Codelabs are an interesting halfway point between static documentation and instructor-led classes, and they share the best and worst features of each. Their hands-on nature makes them more engaging than traditional documentation, but engineers can still access them on demand and complete them on their own; but they are expensive to maintain and are not tailored to the learner’s specific needs.

#### Static analysis

Static analysis tools are a powerful way to share best practices that can be checked programmatically. Every programming language has its own particular static analysis tools, but they have the same general purpose: to alert code authors and reviewers to ways in which code can be improved to follow style and best practices. Some tools go one step further and offer to automatically apply those improvements to the code.

> See Chapter 20 for details on static analysis tools and how they’re used at Google.

Setting up static analysis tools requires an upfront investment, but as soon as they are in place, they scale efficiently. When a check for a best practice is added to a tool, every engineer using that tool becomes aware of that best practice. This also frees up engineers to teach other things: the time and effort that would have gone into manually teaching the (now automated) best practice can instead be used to teach something else. Static analysis tools augment engineers’ knowledge. They enable an organization to apply more best practices and apply them more consistently than would otherwise be possible.

### Staying in the Loop
Some information is critical to do one’s job, such as knowing how to do a typical development workflow. Other information, such as updates on popular productivity tools, is less critical but still useful. For this type of knowledge, the formality of the information sharing medium depends on the importance of the information being delivered. For example, users expect official documentation to be kept up to date, but typically have no such expectation for newsletter content, which therefore requires less maintenance and upkeep from the owner.

#### Newsletters

Google has a number of company-wide newsletters that are sent to all engineers, including EngNews (engineering news), Ownd (Privacy/Security news), and Google’s Greatest Hits (report of the most interesting outages of the quarter). These are a good way to communicate information that is of interest to engineers but isn’t mission critical. For this type of update, we’ve found that newsletters get better engagement when they are sent less frequently and contain more useful, interesting content. Otherwise, newsletters can be perceived as spam.
Even though most Google newsletters are sent via email, some are more creative in their distribution. Testing on the Toilet (testing tips) and Learning on the Loo (productivity tips) are single-page newsletters posted inside toilet stalls. This unique delivery medium helps the Testing on the Toilet and Learning on the Loo stand out from other newsletters, and all issues are archived online.

> See Chapter 11 for a history of how Testing on the Toilet came to be.

#### Communities

Googlers like to form cross-organizational communities around various topics to share knowledge. These open channels make it easier to learn from others outside your immediate circle and avoid information islands and duplication. Google Groups are especially popular: Google has thousands of internal groups with varying levels of formality. Some are dedicated to troubleshooting; others, like the Code Health group, are more for discussion and guidance. Internal Google+ is also popular among Googlers as a source of informal information because people will post interesting technical breakdowns or details about projects they are working on.

## Readability: Standardized Mentorship Through Code Review

At Google, “readability” refers to more than just code readability; it is a standardized, Google-wide mentorship process for disseminating programming language best practices. Readability covers a wide breadth of expertise, including but not limited to language idioms, code structure, API design, appropriate use of common libraries, documentation, and test coverage.
Readability started as a one-person effort. In Google’s early days, Craig Silverstein (employee ID #3) would sit down in person with every new hire and do a line-by-line “readability review” of their first major code commit. It was a nitpicky review that covered everything from ways the code could be improved to whitespace conventions. This gave Google’s codebase a uniform appearance but, more important, it taught best practices, highlighted what shared infrastructure was available, and showed new hires what it’s like to write code at Google.
Inevitably, Google’s hiring rate grew beyond what one person could keep up with. So many engineers found the process valuable that they volunteered their own time to scale the program. Today, around 20% of Google engineers are participating in the readability process at any given time, as either reviewers or code authors.

### What Is the Readability Process?

Code review is mandatory at Google. Every changelist (CL)(*18) requires readability approval, which indicates that someone who has readability certification for that language has approved the CL. Certified authors implicitly provide readability approval of their own CLs; otherwise, one or more qualified reviewers must explicitly give readability approval for the CL. This requirement was added after Google grew to a point where it was no longer possible to enforce that every engineer received code reviews that taught best practices to the desired rigor.

> See Chapter 9 for an overview of the Google code review process and what Approval means in this context.

Within Google, having readability certification is commonly referred to as “having readability” for a language. Engineers with readability have demonstrated that they consistently write clear, idiomatic, and maintainable code that exemplifies Google’s best practices and coding style for a given language. They do this by submitting CLs through the readability process, during which a centralized group of readability reviewers review the CLs and give feedback on how much it demonstrates the various areas of mastery. As authors internalize the readability guidelines, they receive fewer and fewer comments on their CLs until they eventually graduate from the process and formally receive readability. Readability brings increased responsibility: engineers with readability are trusted to continue to apply their knowledge to their own code and to act as reviewers for other engineers’ code.
Around 1 to 2% of Google engineers are readability reviewers. All reviewers are volunteers, and anyone with readability is welcome to self-nominate to become a readability reviewer. Readability reviewers are held to the highest standards because they are expected not just to have deep language expertise, but also an aptitude for teaching through code review. They are expected to treat readability as first and foremost a mentoring and cooperative process, not a gatekeeping or adversarial one. Readability reviewers and CL authors alike are encouraged to have discussions during the review process. Reviewers provide relevant citations for their comments so that authors can learn about the rationales that went into the style guidelines (“Chesterson’s fence”). If the rationale for any given guideline is unclear, authors should ask for clarification (“ask questions”). Readability is deliberately a human-driven process that aims to scale knowledge in a standardized yet personalized way. As a complementary blend of written and tribal knowledge, readability combines the advantages of written documentation, which can be accessed with citable references, with the advantages of expert human reviewers, who know which guidelines to cite. Canonical guidelines and language recommendations are comprehensively documented—which is good!—but the corpus of information is so large(*19) that it can be overwhelming, especially to newcomers.

### Why Have This Process?

Code is read far more than it is written, and this effect is magnified at Google’s scale and in our (very large) monorepo.(*20) Any engineer can look at and learn from the wealth of knowledge that is the code of other teams, and powerful tools like Kythe make it easy to find references throughout the entire codebase (see Chapter 17). An important feature of documented best practices (see Chapter 8) is that they provide consistent standards for all Google code to follow. Readability is both an enforcement and propagation mechanism for these standards.
One of the primary advantages of the readability program is that it exposes engineers to more than just their own team’s tribal knowledge. To earn readability in a given language, engineers must send CLs through a centralized set of readability reviewers who review code across the entire company. Centralizing the process makes a significant trade-off: the program is limited to scaling linearly rather than sublinearly with organization growth, but it makes it easier to enforce consistency, avoid islands, and avoid (often unintentional) drifting from established norms.
The value of codebase-wide consistency cannot be overstated: even with tens of thousands of engineers writing code over decades, it ensures that code in a given language will look similar across the corpus. This enables readers to focus on what the code does rather than being distracted by why it looks different than code that they’re used to. Large-scale change authors (see Chapter 22) can more easily make changes across the entire monorepo, crossing the boundaries of thousands of teams. People can change teams and be confident that the way that the new team uses a given language is not drastically different than their previous team.
These benefits come with some costs: readability is a heavyweight process compared to other mediums like documentation and classes because it is mandatory and enforced by Google tooling (see Chapter 19). These costs are nontrivial and include the following:

- Increased friction for teams that do not have any team members with readability, because they need to find reviewers from outside their team to give readability approval on CLs.
- Potential for additional rounds of code review for authors who need readability review.
- Scaling disadvantages of being a human-driven process. Limited to scaling linearly to organization growth because it depends on human reviewers doing specialized code reviews.

The question, then, is whether the benefits outweigh the costs. There’s also the factor of time: the full effect of the benefits versus the costs are not on the same timescale. The program makes a deliberate trade-off of increased short-term code-review latency and upfront costs for the long-term payoffs of higher-quality code, repository-wide code consistency, and increased engineer expertise. The longer timescale of the benefits comes with the expectation that code is written with a potential lifetime of years, if not decades.(*21)
As with most—or perhaps all—engineering processes, there’s always room for improvement. Some of the costs can be mitigated with tooling. A number of readability comments address issues that could be detected statically and commented on automatically by static analysis tooling. As we continue to invest in static analysis, readability reviewers can increasingly focus on higher-order areas, like whether a particular block of code is understandable by outside readers who are not intimately familiar with the codebase instead of automatable detections like whether a line has trailing whitespace.
But aspirations aren’t enough. Readability is a controversial program: some engineers complain that it’s an unnecessary bureaucratic hurdle and a poor use of engineer time. Are readability’s trade-offs worthwhile? For the answer, we turned to our trusty Engineering Productivity Research (EPR) team.
The EPR team performed in-depth studies of readability, including but not limited to whether people were hindered by the process, learned anything, or changed their behavior after graduating. These studies showed that readability has a net positive impact on engineering velocity. CLs by authors with readability take statistically significantly less time to review and submit than CLs by authors who do not have readability.(*22) Self-reported engineer satisfaction with their code quality—lacking more objective measures for code quality—is higher among engineers who have readability versus those who do not. A significant majority of engineers who complete the program report satisfaction with the process and find it worthwhile. They report learning from reviewers and changing their own behavior to avoid readability issues when writing and reviewing code.

> For an in-depth look at this study and Google’s internal engineering productivity research, see Chapter 7.

Google has a very strong culture of code review, and readability is a natural extension of that culture. Readability grew from the passion of a single engineer to a formal program of human experts mentoring all Google engineers. It evolved and changed with Google’s growth, and it will continue to evolve as Google’s needs change.

## Conclusion

Knowledge is in some ways the most important (though intangible) capital of a software engineering organization, and sharing of that knowledge is crucial for making an organization resilient and redundant in the face of change. A culture that promotes open and honest knowledge sharing distributes that knowledge efficiently across the organization and allows that organization to scale over time. In most cases, investments into easier knowledge sharing reap manyfold dividends over the life of a company.
 
## TL;DRs

- Psychological safety is the foundation for fostering a knowledge-sharing environment.
- Start small: ask questions and write things down.
- Make it easy for people to get the help they need from both human experts and documented references.
- At a systemic level, encourage and reward those who take time to teach and broaden their expertise beyond just themselves, their team, or their organization.
- There is no silver bullet: empowering a knowledge-sharing culture requires a combination of multiple strategies, and the exact mix that works best for your organization will likely change over time.



---

1 In other words, rather than developing a single global maximum, we have a bunch of local maxima.
2 David Lorge Parnas, Software Engineering: Multi-person Development of Multi-version Programs (Heidelberg: Springer-Verlag Berlin, 2011).
3 Impostor syndrome is not uncommon among high achievers, and Googlers are no exception—in fact, a majority of this book’s authors have impostor syndrome. We acknowledge that fear of failure can be difficult for those with impostor syndrome and can reinforce an inclination to avoid branching out.
4 See “How to ask good questions.”
5 https://talksat.withgoogle.com and https://www.youtube.com/GoogleTechTalks, to name a few.
7 See “The Boy Scout Rule” and Kevlin Henney, 97 Things Every Programmer Should Know (Boston: O’Reilly, 2010).
8 g3doc stands for “google3 documentation.” google3 is the name of the current incarnation of Google’s monolithic source repository.
9 Laszlo Bock, Work Rules!: Insights from Inside Google That Will Transform How You Live and Lead (New York:
Twelve Books, 2015). 10 Ibid.
11 Peer bonuses include a cash award and a certificate as well as being a permanent part of a Googler’s award record in an internal tool called gThanks.
12 Such as books about software engineering at Google.
13 See Chapter 9.
14 See Chapter 11.
15 Available for multiple languages. Externally available for C++ at https://abseil.io/tips.
16 go/ links are unrelated to the Go language.
17 External codelabs are available at https://codelabs.developers.google.com.
18 A changelist is a list of files that make up a change in a version control system. A changelist is synonymous with a changeset.
19 As of 2019, just the Google C++ style guide is 40 pages long. The secondary material making up the complete corpus of best practices is many times longer.
20 For why Google uses a monorepo, see https://cacm.acm.org/magazines/2016/7/204032-why-google-stores- billions-of-lines-of-code-in-a-single-repository/fulltext. Note also that not all of Google’s code lives within the monorepo; readability as described here applies only to the monorepo because it is a notion of within- repository consistency.
21 For this reason, code that is known to have a short time span is exempt from readability requirements. Examples include the experimental/ directory (explicitly designated for experimental code and cannot push to production) and the Area 120 program, a workshop for Google’s experimental products.
22 This includes controlling for a variety of factors, including tenure at Google and the fact that CLs for authors who do not have readability typically need additional rounds of review compared to authors who already have readability.


