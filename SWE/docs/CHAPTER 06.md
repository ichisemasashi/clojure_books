# Leading at Scale

Written by Ben Collins-Sussman
Edited by Riona MacNamara

第5章では、「個人的な貢献者」から「チームの明確なリーダー」になるとはどういうことかをお話しました。1つのチームを率いることから、関連する一連のチームを率いることになるのは自然な流れです。本章では、エンジニアリング・リーダーシップの道を歩み続ける中で、どのようにすれば効果的なのかを説明します。

あなたの役割が進化しても、ベストプラクティスはすべて適用されます。あなたは今でも "サーバント・リーダー "であり、より大きなグループに仕えているだけなのです。しかし、あなたが解決する問題の範囲は、より大きく、より抽象的になっていきます。あなたは徐々に "ハイレベル "になることを余儀なくされます。つまり、技術的なことにはあまり興味が持てなくなり、"ディープ "ではなく "ブロード "になることが求められるのです。細かい部分が失われていくのを嘆き、それまでのエンジニアリングの専門知識が自分の仕事にあまり関係なくなってきていることを実感します。むしろ、技術的な直感力や、エンジニアに良い方向に向かわせる能力が、これまで以上に重要になってくるのです。

このようなプロセスにはしばしば落胆させられますが、ある日、自分が個人として貢献していたときよりも、リーダーとしての影響力がはるかに大きいことに気づくのです。満足感はありますが、ほろ苦い実感です。

さて、リーダーシップの基本を理解したとして、本当に優れたリーダーになるためには何が必要なのでしょうか？ここでは、私たちが「リーダーシップの3つの要素」と呼んでいるものを使って、そのことをお話しします。「常に決断する」「常に去る」「常にスケールする」ということです。

## 常に決断すること

チームの中のチームを管理するということは、より高いレベルでより多くの意思決定を行うことを意味します。あなたの仕事は、特定のエンジニアリングタスクを解決する方法よりも、ハイレベルな戦略が重要になります。このレベルでは、ほとんどの意思決定は、正しいトレードオフを見つけることです。

### 飛行機の例え

Lindsay Jonesは私たちの友人で、プロの劇場用サウンドデザイナー兼作曲家です。彼はアメリカ中を飛び回り、プロダクションからプロダクションへと移動する生活を送っているので、飛行機にまつわるクレイジーな（そして真実の）話をたくさんしてくれます。その中でも特にお気に入りの話を紹介します。

 午前6時、私たちは全員飛行機に乗り込み、出発の準備をしています。機長がPAシステムを使って、誰かが燃料タンクに1万ガロンの燃料を入れすぎたと説明した。私は長い間、飛行機に乗ってきましたが、そのようなことが可能だとは知りませんでした。車の燃料を1ガロンも入れすぎたら、靴の中がガスだらけになってしまうでしょう？
 1時間以上かかるトラックが燃料を飛行機から吸い上げてくれるのを待つか、重量を均等にするために20人が今すぐ飛行機から降りるか、だ。
 誰も動かない。
 さて、ファーストクラスの通路を挟んで向かい側にいる男性がいるのですが、彼は本当に怒り狂っています。彼はM*A*S*Hのフランク・バーンズを思い起こさせるような感じで、非常に憤慨していて、いたるところで口をとがらせ、誰が責任を負うのかを要求しています。まるでマルクス・ブラザーズの映画に出てくるマーガレット・デュモンのように、素晴らしい見せ場を作っている。
 そこで彼は財布を手に取り、大量の札束を取り出した！ そして、「このミーティングに遅れるわけにはいかない！」と言ったのです。「今、この飛行機から降りてきた人には40ドルを差し上げます！」と。
 案の定、人々は彼の話に乗った。彼は20人に40ドルを配り（ちなみに800ドルの現金です！）、みんな降りていきました。
 さて、準備が整い、滑走路に向かうと、再び機長がPAに戻ってきた。飛行機のコンピューターが動かなくなった。原因は誰にもわからない。今すぐゲートまで牽引してもらわなければならない。
 フランク・バーンズは絶句した。マジで脳卒中になるかと思ったよ。罵声と悲鳴を上げている。他のみんなはお互いに見ているだけだ。
 ゲートに戻ると、この男は別の便を要求している。彼らは9時30分発の便を予約しようとしましたが、それでは遅すぎます。彼は「9:30より前の便はないのか」と言いました。
 ゲートエージェントは、「8時台に別のフライトがあったのですが、今は全部満席です。彼らは今、ドアを閉めています」。
 すると彼は「満席？満席ってなんだ？あの飛行機には空席が一つもないのか！？
 ゲートエージェントは、「いいえ、あの飛行機は、20人の乗客がどこからともなく現れて、すべての席を取るまでは、大きく開いていました。彼らは私が今まで見た中で最も幸せな乗客で、ジェットブリッジを降りるまでずっと笑っていました」。
 9時30分発の飛行機はとても静かだった。

この話はもちろん、トレードオフについてです。本書では、エンジニアリングシステムにおけるさまざまな技術的なトレードオフに焦点を当てていますが、トレードオフは人間の行動にも当てはまることがわかりました。リーダーであるあなたは、チームが毎週何をすべきかを決定しなければなりません。トレードオフが明白な場合もあれば（「このプロジェクトに取り組めば、他のプロジェクトが遅れる...」）、先ほどの話のように、トレードオフが予想外の結果をもたらし、あなたを苦しめることになる場合もあります。

最高レベルのリーダーとしてのあなたの仕事は、1つのチームであれ、より大きな組織であれ、困難で曖昧な問題を解決するために人々を導くことです。曖昧な問題とは、明らかな解決策がなく、解決不可能な問題であることを意味します。いずれにしても、問題を調査し、ナビゲートし、（願わくば）コントロールできる状態にまで持っていく必要があります。コードを書くことが木を切ることに似ているとすれば、リーダーの仕事は「木を見て森を見る」ことであり、その森の中で実行可能な道を見つけ、エンジニアを重要な木に向かわせることです。このプロセスには、大きく分けて3つのステップがあります。まず、目隠しをすること、次にトレードオフを見極めること、そして解決策を決定し、繰り返し実行することです。

### 目隠しをする

ある問題に初めて取り組んだとき、すでに何年もその問題に取り組んでいるグループがあることに気づくことがあります。これらの人々は、あまりにも長い間その問題に浸ってきたため、「目隠し」をしているのです。彼らは、問題（あるいは解決策）について、気づかないうちに多くの仮定をしています。批判的に現状を検討する能力を失った彼らは、「これはいつものやり方だ」と言います。時には現状を正当化するために進化してきた奇妙な対処法や合理化を発見することもあるでしょう。ここでは、新鮮な目を持つあなたが大きなアドバンテージを持っています。このような目隠しを見て、疑問を持ち、新しい戦略を考えることができます。(もちろん、問題に精通していないことが優れたリーダーシップの条件ではありませんが、多くの場合、それが利点となります）。)

### 重要なトレードオフを特定する

定義上、重要で曖昧な問題には、魔法のような「銀の弾丸」のような解決策はありません。すべての状況で永遠に通用する答えはありません。あるのはその時のベストな答えだけで、それはほぼ間違いなく、ある方向へのトレードオフを伴うものです。トレードオフを指摘し、皆に説明し、そのバランスをどうとるかを決めるのがあなたの仕事です。

### 決断して、繰り返す

トレードオフとその仕組みを理解した後は、力が湧いてきます。この情報をもとに、その月に最適な判断を下すことができます。来月になれば、トレードオフを再評価し、バランスを調整する必要があるかもしれません。これが、「常に決断を」という言葉の意味です。

ここにはリスクがあります。トレードオフのバランスを継続的に調整するようにプロセスを設定しないと、チームは完璧なソリューションを探すという罠に陥る可能性があり、それは「分析麻痺」と呼ばれるものになるかもしれません。そのためには、チームがイテレーションに慣れ親しむ必要があります。そのためには、「今日はこんなことをやってみようと思います」と説明して、緊張感を和らげるのが一つの方法です。「この決定をしてみて、どうなるか見てみよう。来月になれば、この変更を元に戻すこともできるし、別の決定をすることもできます。そうすることで、人々は柔軟性を保ち、自分の選択から学ぶことができるのです。

----

### ケーススタディ ウェブ検索の「レイテンシー」への対応

複数のチームを管理していると、単一の製品から離れて、製品の「クラス」全体、あるいは製品を横断するより広い問題を所有するようになる傾向があります。Googleの場合は、最も古い製品であるウェブ検索がその良い例です。

何年もの間、何千人ものGoogleエンジニアが、検索結果をより良いものにするという一般的な問題に取り組んできました。つまり、検索結果ページの「品質」を向上させるのです。しかし、この品質の追求には副作用があり、製品の動作が徐々に遅くなることが判明した。かつて、Googleの検索結果は、それぞれが関連するウェブサイトを表す10個の青いリンクのページ以上のものではありませんでした。しかし、この10年間で、「品質」を向上させるために何千もの小さな変更を加えた結果、画像、動画、ウィキペディアの情報を記載したボックス、さらにはインタラクティブなUI要素など、検索結果はますます高度なものになってきました。これは、サーバーが情報を生成するために、より多くの仕事をしなければならないことを意味します。より多くのバイトがワイヤー上で送信され、クライアント（通常は携帯電話）はこれまで以上に複雑なHTMLやデータのレンダリングを求められます。この10年間でネットワークやコンピューターの速度は格段に向上しましたが、検索ページの速度はどんどん遅くなり、レイテンシーも大きくなっています。大したことではないと思われるかもしれませんが、製品のレイテンシーは、ユーザーのエンゲージメントや使用頻度に直接影響します（全体として）。レンダリング時間の増加は、10ms程度の小さなものでも問題となります。レイテンシーはゆっくりと上昇していきます。これは、特定のエンジニアリングチームのせいではなく、長い間の集団的な汚染の結果です。ある時点で、ウェブ検索の全体的なレイテンシーが大きくなり、その影響で、検索結果の「質」の向上によって得られたユーザーエンゲージメントの改善が打ち消されるようになる。

何人ものリーダーが長年にわたってこの問題に取り組んできたが、組織的に対処することはできなかった。皆が身につけていた目隠しは、レイテンシーに対処する唯一の方法は、2〜3年ごとにレイテンシーの「コードイエロー」(*1)を宣言し、皆がコードの最適化と製品のスピードアップに全力を尽くすことだと考えていた。この作戦は一時的には有効だが、1〜2ヵ月後には再びレイテンシーが上昇し始め、すぐに元のレベルに戻ってしまう。

では、何が変わったのでしょうか？ある時点で、私たちは一歩下がって目隠しをし、トレードオフを全面的に再評価しました。その結果、「品質」を追求することには、1つではなく、2つの異なるコストがかかることがわかりました。1つ目のコストはユーザーに対するもので、品質が高ければ高いほど、より多くのデータが送信されることになり、その分、遅延も大きくなります。2つ目のコストは、Googleにとってのコストです。品質が高ければ高いほど、データを生成するために多くの作業を行うことになり、サーバーのCPU時間が増えることになります。品質とキャパシティのトレードオフについては、これまでもリーダーシップを発揮して慎重に検討してきましたが、レイテンシーを計算の中で完全に市民権を得ているとは言えませんでした。古いジョークにあるように、「良いもの、速いもの、安いもの......2つ選べ」というわけだ。トレードオフを表現する簡単な方法は、図6-1に示されているように、Good（品質）、Fast（レイテンシー）、Cheap（容量）の間に緊張のトライアングルを描くことです。

![fig06-1](../img/Fig6-1.png)
Figure 6-1. ウェブ検索におけるトレードオフ：2つの選択

それはまさにここで起きていたことです。これらの特性のうち、どれか1つを改善するには、他の2つの特性のうち少なくとも1つを意図的に害することが簡単にできます。例えば、検索結果ページにより多くのデータを載せることで品質を向上させることができますが、そうすると容量とレイテンシーが損なわれます。レイテンシーとキャパシティの直接的なトレードオフを行うには、サービングクラスタのトラフィック負荷を変更することもできます。より多くのクエリをクラスタに送信すると、CPUの利用率が向上するという意味で容量が増加し、ハードウェアのコストに見合った効果が得られます。しかし、負荷が高いとコンピュータ内のリソースの競合が激しくなり、クエリの平均レイテンシーが悪化します。クラスタのトラフィックを意図的に減少させる（クールに運用する）と、全体的な処理能力は低下しますが、各クエリは高速化します。

ここで重要なのは、この洞察、つまりすべてのトレードオフに対する理解が深まったことで、バランスをとるための新しい方法を試すことができるようになったということです。遅延を避けられない偶発的な副作用として扱うのではなく、他の目標と同様に第一級の目標として扱うことができるようになったのです。これにより、新たな戦略が生まれました。例えば、データサイエンティストは、レイテンシーによってユーザーエンゲージメントがどれだけ低下するかを正確に測定することができました。これにより、品質に起因する短期的なユーザーエンゲージメントの向上と、レイテンシーに起因する長期的なユーザーエンゲージメントの低下を比較する指標を構築することができました。このアプローチにより、製品の変更について、よりデータに基づいた判断ができるようになりました。例えば、ある小さな変更が品質を向上させる一方でレイテンシーを悪化させた場合、その変更を開始する価値があるかどうかを定量的に判断することができます。私たちは、品質、レイテンシー、容量の変更がバランスよく行われているかどうかを常に判断し、毎月その判断を繰り返しています。

----

## Always Be Leaving

『Always Be Leaving』は、一見するとひどいアドバイスのように聞こえます。優秀なリーダーがなぜ去ろうとするのか？実はこれ、元Googleのエンジニアリングディレクター、バラット・メディラッタの有名な言葉なのです。彼が言いたかったのは、曖昧な問題を解決することだけがあなたの仕事ではなく、あなたがいなくても組織が自力で問題を解決するように仕向けることが大切だということです。それができれば、あなたは自由になって、新しい問題（あるいは新しい組織）に移ることができ、自給自足の成功の痕跡を残すことができるのです。

ここでのアンチパターンは、自分がSPOF（シングル・ポイント・オブ・フェイル）になってしまうことです。本書の冒頭で述べたように、Googlerにはバス・ファクターという言葉があります。これは、あなたのプロジェクトが完全に破滅するまでにバスに轢かれる必要がある人数のことです。

もちろん、ここでいう「バス」は単なる比喩です。人は病気になったり、チームや会社を変えたり、引っ越しをしたりします。リトマス試験として、あなたのチームが順調に進んでいる難しい問題について考えてみてください。ここで、リーダーであるあなたがいなくなったと想像してみてください。あなたのチームは進み続けますか？チームは成功し続けていますか？もっと簡単なテストをしてみましょう。最後に取った少なくとも1週間の休暇について考えてみてください。あなたは仕事のメールをチェックし続けましたか？(ほとんどのリーダーはチェックしています）その理由を自分に聞いてみてください。注意を払わないと物事がうまくいかないのか？もしそうなら、あなたは自分自身をSPOFにしてしまっている可能性が高いです。それを直さなければなりません。

### あなたの使命。「自走する」チームを作る

バラットの言葉に戻ると、成功するリーダーとは、困難な問題を自力で解決できる組織を構築することです。そのためには、強力なリーダー、健全なエンジニアリングプロセス、ポジティブで永続的な文化を持つ組織が必要です。これは難しいことです。しかし、チームを率いるということは、技術的な魔法使いであるよりも、むしろ人をまとめることであることが多いという事実に戻ります。繰り返しになりますが、このような自給自足のグループを構築するには、問題空間の分割、サブプロブレムの委譲、必要に応じた反復という3つの主要な部分があります。

### 問題空間の分割

難易度の高い問題は、たいてい難しいサブプロブレムで構成されています。もしあなたがチームを率いるなら、各サブプロブレムを担当するチームを作るのが当然の選択です。しかし、サブプロブレムは時間の経過とともに変化するものであり、硬直したチームの境界ではその事実に気づくことも適応することもできないというリスクがあります。できれば、サブチームの規模を変えたり、個人がサブチーム間を移動したり、サブチームに割り当てられた問題が時間の経過とともに変化したりするような、ゆるい組織構造を考えてみてください。これには、「厳しすぎる」と「漠然としすぎている」の間の微妙なバランスが必要です。一方で、サブチームには明確な問題意識、目的意識、着実な達成感を持ってもらいたいと思いますが、一方で、環境の変化に応じて方向性を変えたり、新しいことに挑戦したりする自由も必要です。

#### 例 Google検索の「待ち時間問題」の細分化

検索の待ち時間の問題に取り組む際、私たちはこの問題が少なくとも2つの一般的な空間に細分化できることに気づきました。遅延の症状に対処する作業と、遅延の原因に対処する別の作業です。コードベースのスピードを最適化するために多くのプロジェクトに人員を投入する必要があることは明らかでしたが、スピードだけに焦点を当てていても十分ではありません。検索結果の複雑さと「質」を高めるために、何千人ものエンジニアがいたのです。そのため、並行して、遅延を未然に防ぐという問題に取り組む人材も必要でした。私たちは、測定基準や遅延分析ツールにギャップを発見しました。指標、遅延解析ツール、開発者への教育やドキュメントにもギャップがありました。遅延の原因と症状を同時に解決するために、異なるチームを配置することで、長期的に遅延を組織的にコントロールすることができました。(また、これらのチームは具体的な解決策ではなく、問題を所有していることにも注目してください！。)

#### サブプロブレムをリーダーに任せる

マネジメントの本で「委任」について語るのは、基本的には決まり文句のようになっていますが、それには理由があります。効率や成果を求める人間の本能に反しているからです。その難しさゆえに、"If you want something done right, do it yourself. "という格言が生まれたのです。

とはいえ、自分のミッションが自走する組織を作ることであると同意するならば、教育の主なメカニズムは「委任」である。自己完結型のリーダーを作る必要がありますが、その育成には「委任」が最も効果的です。課題を与えて、失敗させて、また挑戦させる。シリコンバレーでは、"Failing Fast and Iterating "という言葉がよく知られています。この哲学は工学設計だけでなく、人間の学習にも当てはまります。

リーダーになると、やらなければならない重要な仕事が次々と舞い込んできます。これらのタスクのほとんどは、あなたにとってかなり簡単なことです。例えば、あなたが受信トレイを使って熱心に問題に対応しているときに、20分だけ確保して長年の懸案事項を解決しようと思ったとします。しかし、その作業を実行する前に、心を鬼にして自分を止めてください。問いかけてみてください。この仕事をできるのは、本当に自分だけだろうか？

確かに自分がやるのが一番効率的かもしれませんが、それではリーダーの育成ができていません。自立した組織を作ることができないのです。その仕事が本当に一刻を争うものでなければ、思い切って他の人に仕事を任せましょう。おそらく、あなたがその仕事ができると分かっていても、完成までにはかなりの時間がかかるでしょう。必要であれば、その人に仕事をコーチしてあげてください。あなたは、リーダーが成長する機会を作る必要があります。彼らが「レベルアップ」することを学び、あなたがクリティカルパスから外れるように、この仕事を自分で行う必要があります。

ここで重要なのは、リーダーのリーダーとしての自分の目的を意識することです。もし、自分が雑草の中に入り込んでいるとしたら、それは組織にとって不利益なことです。毎日出勤する際には、自分自身に別の重要な質問をしてみてください。チームの他の誰にもできないことを、私は何かできるだろうか？

良い答えはいくつもあります。例えば、組織の政治的な動きからチームを守ること、チームを励ますこと、全員がお互いによく接していることを確認し、謙虚さ、信頼、尊敬の文化を作ることなどが挙げられます。また、"マネジメントアップ "も重要です。自分たちのグループが何をしているかをマネジメントチェーンに理解させ、会社全体とのつながりを保つことです。しかし、この質問に対する最も一般的で重要な答えは、"木を見て森を見ることができる "というものです。言い換えれば、ハイレベルな戦略を定義できるということです。戦略には、技術的な方向性だけでなく、組織的な戦略も含まれている必要があります。曖昧な問題をどのように解決し、組織としてどのように管理していくかの青写真を描くのです。あなたは継続的に森の地図を作り、木を切る作業を他の人に任せるのです。

#### アジャストとイテレーション

今、あなたは自立したマシンを構築するところまで到達したと仮定しましょう。あなたはもうSPOFではありません。おめでとうございます。今、あなたは何をしていますか？

答えを出す前に、あなたは実際に自分自身を解放したことに注意してください --- あなたは今、"常に残る "自由を手にしています。それは、新しい隣接した問題に取り組む自由かもしれませんし、あるいは、自分が育てたリーダーのキャリアのためのスペースを確保して、全く新しい部門や問題領域に自分を移動させることもできるでしょう。これは、個人的な燃え尽きを避けるための素晴らしい方法です。

「これからどうするか」のシンプルな答えは、この機械に指示を出し、健全な状態を保つことです。しかし、危機的な状況でない限りは、穏やかなタッチで接するべきです。『チームをデバッグする』(*2)という本には、心を込めて調整するというたとえ話があります。

 退職して間もない機械の達人の話があります。彼が以前勤めていた会社では、誰にも解決できない問題を抱えていたので、問題解決の手助けをしてもらえないかと師匠を呼んだのです。師匠は機械を調べ、話を聞き、やがて使い古したチョークを取り出して、機械の側面に小さなXを描いた。彼は技術者に、その場所に修理が必要なワイヤーの緩みがあることを伝えた。技術者は機械を開けて、緩んだワイヤーを締め付けた。これで問題は解決した。師匠から1万ドルの請求書が届くと、怒ったCEOは、たった1つのチョークマークのために、この馬鹿げた高額な料金の内訳を要求する手紙を出した。その請求書には、マークをつけるためのチョーク代が1ドル、マークをつける場所を知るための費用が9,999ドルと書かれていた。
 私たちにとっては、これは「知恵」の話であり、一つの慎重な調整が巨大な効果をもたらすということです。私たちは、人を管理するときにこの手法を使っています。チームが大きな飛行船に乗って、ゆっくりと確実に一定の方向に向かって飛んでいるように想像するのです。私たちは、チームを飛行船に見立て、ゆっくりと確実に一定の方向に向かっていると考えます。週の終わりには、飛行船の正確な位置に小さなチョークマークを付け、小さくても重要な「タップ」をしてコースを調整します。

これこそが、優れたマネジメントの本質です。95%は観察と傾聴、5%は適切な場所で重要な調整を行うことです。リーダーや報告書に耳を傾ける。顧客と話をする。多くの場合（特にあなたのチームがエンジニアリング・インフラを構築している場合）、あなたの「顧客」は世界のエンドユーザーではなく、あなたの同僚であることを忘れてはならない。お客様の幸せのためには、報告者の幸せと同じくらい熱心に耳を傾ける必要があります。何がうまくいっていて、何がうまくいっていないのか。この自動運転の飛行船は、適切な方向に向かっているでしょうか？あなたの方向性は反復的であるべきですが、思慮深く、最小限の調整を行い、軌道修正をする必要があります。もし、マイクロマネジメントに逆戻りしたら、再びSPOFになってしまう危険性があります。"Always Be Leaving "は、マクロマネジメントへの呼びかけです。

#### チームのアイデンティティを確立するための注意点

よくある間違いは、あるチームが一般的な問題ではなく、特定の製品を担当することです。製品とは、問題に対する解決策です。解決策の寿命は短く、製品はより良い解決策に取って代わられることがあります。しかし、問題は、うまく選択すれば、永遠に残るものです。チームのアイデンティティを特定のソリューションに固定してしまうと（「私たちはGitリポジトリを管理するチームです」）、時間の経過とともに様々な問題が発生します。もし、エンジニアの大部分が新しいバージョンコントロールシステムに切り替えたいと思ったらどうしますか？そのチームは、たとえそれが組織にとって最善の道ではないとしても、自分たちの解決策を守り、変化に抵抗して「頑張る」ことになるでしょう。解決策がチームのアイデンティティと自己価値の一部になっているため、チームは目隠しに固執します。チームが問題を所有する代わりに（例えば、「我々は会社にバージョンコントロールを提供するチームである」）、時間をかけて様々なソリューションを試すことができるようになります。

## Always Be Scaling

A lot of leadership books talk about “scaling” in the context of learning to “maximize your impact” --- strategies to grow your team and influence. We’re not going to discuss those things here beyond what we’ve already mentioned. It’s probably obvious that building a self-driving organization with strong leaders is already a great recipe for growth and success.
Instead, we’re going to discuss team scaling from a defensive and personal point of view rather than an offensive one. As a leader, your most precious resource is your limited pool of time, attention, and energy. If you aggressively build out your teams’ responsibilities and power without learning to protect your personal sanity in the process, the scaling is doomed to fail. And so we’re going to talk about how to effectively scale yourself through this process.

### The Cycle of Success

When a team tackles a difficult problem, there’s a standard pattern that emerges, a particular cycle. It looks like this:

- Analysis
  - First, you receive the problem and start to wrestle with it. You identify the blinders, find all the trade-offs, and build consensus about how to manage them.
- Struggle
  - You start moving on the work, whether or not your team thinks it’s ready. You prepare for failures, retries, and iteration. At this point, your job is mostly about herding cats. Encourage your leaders and experts on the ground to form opinions and then listen carefully and devise an overall strategy, even if you have to “fake it” at first.(*3)
- Traction
  - Eventually your team begins to figure things out. You’re making smarter decisions, and real progress is made. Morale improves. You’re iterating on trade-offs, and the organization is beginning to drive itself around the problem. Nice job!
- Reward
  - Something unexpected happens. Your manager takes you aside and congratulates you on your success. You discover your reward isn’t just a pat on the back, but a whole new problem to tackle. That’s right: the reward for success is more work... and more responsibility! Often, it’s a problem that is similar or adjacent to the first one, but equally difficult.

So now you’re in a pickle. You’ve been given a new problem, but (usually) not more people. Somehow you need to solve both problems now, which likely means that the original problem still needs to be managed with half as many people in half the time. You need the other half of your people to tackle the new work! We refer to this final step as the compression stage: you’re taking everything you’ve been doing and compressing it down to half the size.
So really, the cycle of success is more of a spiral (see Figure 6-2). Over months and years, your organization is scaling by tackling new problems and then figuring out how to compress them so that it can take on new, parallel struggles. If you’re lucky, you’re allowed to hire more people as you go. More often than not, though, your hiring doesn’t keep pace with the scaling. Larry Page, one of Google’s founders, would probably refer to this spiral as “uncomfortably exciting.”

![fig06-2](../img/Fig6-2.png)

Figure 6-2. The spiral of success

The spiral of success is a conundrum --- it’s something that’s difficult to manage, and yet it’s the main paradigm for scaling a team of teams. The act of compressing a problem isn’t just about figuring out how to maximize your team’s efficiency, but also about learning to scale your own time and attention to match the new breadth of responsibility.

### Important Versus Urgent

Think back to a time when you weren’t yet a leader, but still a carefree individual contributor. If you used to be a programmer, your life was likely calmer and more panic- free. You had a list of work to do, and each day you’d methodically work down your list, writing code and debugging problems. Prioritizing, planning, and executing your work was straightforward.
As you moved into leadership, though, you might have noticed that your main mode of work became less predictable and more about firefighting. That is, your job became less proactive and more reactive. The higher up in leadership you go, the more escalations you receive. You are the “finally” clause in a long list of code blocks! All of your means of communication --- email, chat rooms, meetings --- begin to feel like a Denial-of-Service attack against your time and attention. In fact, if you’re not mindful, you end up spending 100% of your time in reactive mode. People are throwing balls at you, and you’re frantically jumping from one ball to the next, trying not to let any of them hit the ground.
A lot of books have discussed this problem. The management author Stephen Covey is famous for talking about the idea of distinguishing between things that are important versus things that are urgent. In fact, it was US President Dwight D. Eisenhower who popularized this idea in a famous 1954 quote:

 I have two kinds of problems, the urgent and the important. The urgent are not important, and the important are never urgent.

This tension is one of the biggest dangers to your effectiveness as a leader. If you let yourself slip into pure reactive mode (which happens almost automatically), you spend every moment of your life on urgent things, but almost none of those things are important in the big picture. Remember that your job as a leader is to do things that only you can do, like mapping a path through the forest. Building that meta- strategy is incredibly important, but almost never urgent. It’s always easier to respond to that next urgent email.
So how can you force yourself to work mostly on important things, rather than urgent things? Here are a few key techniques:

- Delegate
  - Many of the urgent things you see can be delegated back to other leaders in your organization. You might feel guilty if it’s a trivial task; or you might worry that handing off an issue is inefficient because it might take those other leaders longer to fix. But it’s good training for them, and it frees up your time to work on important things that only you can do.
- Schedule dedicated time
  - Regularly block out two hours or more to sit quietly and work only on important- but-not-urgent things --- things like team strategy, career paths for your leaders, or how you plan to collaborate with neighboring teams.
- Find a tracking system that works
  - There are dozens of systems for tracking and prioritizing work. Some are software based (e.g., specific “to-do” tools), some are pen-and-paper based (the “Bullet Journal” method), and some systems are agnostic to implementation. In this last category, David Allen’s book, Getting Things Done, is quite popular among engineering managers; it’s an abstract algorithm for working through tasks and maintaining a prized “inbox zero.” The point here is to try these different systems and determine what works for you. Some of them will click with you and some will not, but you definitely need to find something more effective than tiny Post- It notes decorating your computer screen.

### Learn to Drop Balls

There’s one more key technique for managing your time, and on the surface it sounds radical. For many, it contradicts years of engineering instinct. As an engineer, you pay attention to detail; you make lists, you check things off lists, you’re precise, and you finish what you start. That’s why it feels so good to close bugs in a bug tracker, or whittle your email down to inbox zero. But as a leader of leaders, your time and attention are under constant attack. No matter how much you try to avoid it, you end up dropping balls on the floor --- there are just too many of them being thrown at you. It’s overwhelming, and you probably feel guilty about this all the time.
So, at this point, let’s step back and take a frank look at the situation. If dropping some number of balls is inevitable, isn’t it better to drop certain balls deliberately rather than accidentally? At least then you have some semblance of control.
Here’s a great way to do that.
Marie Kondo is an organizational consultant and the author of the extremely popular book The Life-Changing Magic of Tidying Up. Her philosophy is about effectively decluttering all of the junk from your house, but it works for abstract clutter as well.
Think of your physical possessions as living in three piles. About 20% of your things are just useless --- things that you literally never touch anymore, and all very easy to throw away. About 60% of your things are somewhat interesting; they vary in importance to you, and you sometimes use them, sometimes not. And then about 20% of your possessions are exceedingly important: these are the things you use all the time, that have deep emotional meaning, or, in Ms. Kondo’s words, spark deep “joy” just holding them. The thesis of her book is that most people declutter their lives incorrectly: they spend time tossing the bottom 20% in the garbage, but the remaining 80% still feels too cluttered. She argues that the true work of decluttering is about identifying the top 20%, not the bottom 20%. If you can identify only the critical things, you should then toss out the other 80%. It sounds extreme, but it’s quite effective. It is greatly freeing to declutter so radically.
It turns out that you can also apply this philosophy to your inbox or task list --- the barrage of balls being thrown at you. Divide your pile of balls into three groups: the bottom 20% are probably neither urgent nor important and very easy to delete or ignore. There’s a middle 60%, which might contain some bits of urgency or importance, but it’s a mixed bag. At the top, there’s 20% of things that are absolutely, critically important.
And so now, as you work through your tasks, do not try to tackle the top 80% --- you’ll still end up overwhelmed and mostly working on urgent-but-not-important tasks. Instead, mindfully identify the balls that strictly fall in the top 20% --- critical things that only you can do --- and focus strictly on them. Give yourself explicit permission to drop the other 80%.
It might feel terrible to do so at first, but as you deliberately drop so many balls, you’ll discover two amazing things. First, even if you don’t delegate that middle 60% of tasks, your subleaders often notice and pick them up automatically. Second, if something in that middle bucket is truly critical, it ends up coming back to you anyway, eventually migrating up into the top 20%. You simply need to trust that things below your top-20% threshold will either be taken care of or evolve appropriately. Meanwhile, because you’re focusing only on the critically important things, you’re able to scale your time and attention to cover your group’s ever-growing responsibilities.

### Protecting Your Energy

We’ve talked about protecting your time and attention --- but your personal energy is the other piece of the equation. All of this scaling is simply exhausting. In an environment like this, how do you stay charged and optimistic?
Part of the answer is that over time, as you grow older, your overall stamina builds up. Early in your career, working eight hours a day in an office can feel like a shock; you come home tired and dazed. But just like training for a marathon, your brain and body build up larger reserves of stamina over time.
The other key part of the answer is that leaders gradually learn to manage their energy more intelligently. It’s something they learn to pay constant attention to. Typically, this means being aware of how much energy you have at any given moment, and making deliberate choices to “recharge” yourself at specific moments, in specific ways. Here are some great examples of mindful energy management:

- Take real vacations
  - A weekend is not a vacation. It takes at least three days to “forget” about your work; it takes at least a week to actually feel refreshed. But if you check your work email or chats, you ruin the recharge. A flood of worry comes back into your mind, and all of the benefit of psychological distancing dissipates. The vacation recharges only if you are truly disciplined about disconnecting.(*4) And, of course, this is possible only if you’ve built a self-driving organization.
- Make it trivial to disconnect
  - When you disconnect, leave your work laptop at the office. If you have work communications on your phone, remove them. For example, if your company uses G Suite (Gmail, Google Calendar, etc.), a great trick is to install these apps in a “work profile” on your phone. This causes a second set of work-badged apps to appear on your phone. For example, you’ll now have two Gmail apps: one for personal email, one for work email. On an Android phone, you can then press a single button to disable the entire work profile at once. All the work apps gray out, as if they were uninstalled, and you can’t “accidentally” check work messages until you re-enable the work profile.
- Take real weekends, too
  - A weekend isn’t as effective as a vacation, but it still has some rejuvenating power. Again, this recharge works only if you disconnect from work communications. Try truly signing out on Friday night, spend the weekend doing things you love, and then sign in again on Monday morning when you’re back in the office.
- Take breaks during the day
  - Your brain operates in natural 90-minute cycles.(*5) Use the opportunity to get up and walk around the office, or spend 10 minutes walking outside. Tiny breaks like this are only tiny recharges, but they can make a tremendous difference in your stress levels and how you feel over the next two hours of work.
- Give yourself permission to take a mental health day
  - Sometimes, for no reason, you just have a bad day. You might have slept well, eaten well, exercised --- and yet you are still in a terrible mood anyway. If you’re a leader, this is an awful thing. Your bad mood sets the tone for everyone around you, and it can lead to terrible decisions (emails you shouldn’t have sent, overly harsh judgements, etc.). If you find yourself in this situation, just turn around and go home, declaring a sick day. Better to get nothing done that day than to do active damage.

In the end, managing your energy is just as important as managing your time. If you learn to master these things, you’ll be ready to tackle the broader cycle of scaling responsibility and building a self-sufficient team.

## Conclusion

Successful leaders naturally take on more responsibility as they progress (and that’s a good and natural thing). Unless they effectively come up with techniques to properly make decisions quickly, delegate when needed, and manage their increased responsibility, they might end up feeling overwhelmed. Being an effective leader doesn’t mean that you need to make perfect decisions, do everything yourself, or work twice as hard. Instead, strive to always be deciding, always be leaving, and always be scaling.


## TL;DRs

- Always Be Deciding: Ambiguous problems have no magic answer; they’re all about finding the right trade-offs of the moment, and iterating.
- Always Be Leaving: Your job, as a leader, is to build an organization that automatically solves a class of ambiguous problems --- over time --- without you needing to be present.
- Always Be Scaling: Success generates more responsibility over time, and you must proactively manage the scaling of this work in order to protect your scarce resources of personal time, attention, and energy.














------

1 “Code yellow” is Google’s term for “emergency hackathon to fix a critical problem.” Affected teams are expected to suspend all work and focus 100% attention on the problem until the state of emergency is declared over.
2 Brian W. Fitzpatrick and Ben Collins-Sussman, Debugging Teams: Better Productivity through Collaboration (Boston: O’Reilly, 2016).
3 It’s easy for imposter syndrome to kick in at this point. One technique for fighting the feeling that you don’t know what you’re doing is to simply pretend that some expert out there knows exactly what to do, and that they’re simply on vacation and you’re temporarily subbing in for them. It’s a great way to remove the personal stakes and give yourself permission to fail and learn.
4 You need to plan ahead and build around the assumption that your work simply won’t get done during vacation. Working hard (or smart) just before and after your vacation mitigates this issue.
5 You can read more about BRAC at https://en.wikipedia.org/wiki/Basic_rest-activity_cycle.



