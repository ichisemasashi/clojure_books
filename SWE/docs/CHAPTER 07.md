# CHAPTER 7 エンジニアの生産性を測る

Written by Ciera Jaspan
Edited by Riona Macnamara

Googleはデータ重視の企業です。製品やデザインの決定のほとんどを、確固たるデータで裏付けています。適切な測定基準を用いてデータ駆動型の意思決定を行う文化には欠点もありますが、全体的に見ると、データに頼ることでほとんどの意思決定が主観的ではなく客観的になる傾向があり、これは良いことであることが多いのです。しかし、人間の側からデータを収集・分析することには、それなりの課題があります。具体的には、ソフトウェアエンジニアリングの分野では、エンジニアリングの生産性に特化した専門家チームを持つこと自体が、会社の規模が大きくなり、そこから得られる知見を活用する上で、非常に価値のある重要なことだとGoogleは考えています。

## なぜエンジニアの生産性を測定する必要があるのか？

例えば、オンライン検索エンジンを運営しているような繁盛しているビジネスを持っているとします。(例えば、あなたはオンライン検索エンジンを運営しています。) ビジネスの範囲を拡大したいと考えているとします。(企業向けアプリケーション市場、クラウド市場、モバイル市場への参入。) おそらく、ビジネスの範囲を広げるためには、エンジニアリング組織の規模も大きくする必要があるでしょう。しかし、組織の規模が直線的に大きくなると、通信費は二次関数的に大きくなります(*1)。ビジネスの規模を大きくするためには人を増やす必要がありますが、通信費のオーバーヘッドは人を増やしても直線的には拡大しません。その結果、エンジニアリング組織の規模に応じてビジネスの範囲をリニアに拡大することができなくなります。

スケーリングの問題を解決するには、一人ひとりの生産性を高めるという方法があります。組織内の個々のエンジニアの生産性を高めることができれば、コミュニケーションのオーバーヘッドを増やすことなく、ビジネスの範囲を拡大することができます。

Googleは新規事業への急速な成長を余儀なくされていますが、それはエンジニアの生産性を高める方法を学ぶことを意味します。そのためには、エンジニアの生産性を高める要因を理解し、エンジニアリングプロセスの非効率性を特定し、特定された問題を解決する必要がありました。そして、このサイクルを必要に応じて繰り返し、継続的に改善していきます。このようにして、需要の増加に合わせてエンジニアリング組織を拡張することができるのです。

しかし、この改善サイクルには人的資源も必要です。生産性の阻害要因を理解して修正するのに年間50人のエンジニアが必要であれば、エンジニア組織の生産性を年間10人分向上させても意味がありません。そのため、ソフトウェアエンジニアリングの生産性を向上させるだけでなく、それを効率的に行うことを目標としています。

Googleでは、エンジニアリングの生産性を理解するための研究チームを設立し、このようなトレードオフの問題に取り組んでいます。研究チームには、ソフトウェアエンジニアリングの研究者や一般のソフトウェアエンジニアのほか、認知心理学や行動経済学などさまざまな分野の社会科学者も参加しています。社会科学者が加わったことで、エンジニアが生み出すソフトウェアの成果物を研究するだけでなく、個人のモチベーションやインセンティブ構造、複雑なタスクを管理するための戦略など、ソフトウェア開発の人間的側面を理解することができます。このチームの目標は、エンジニアの生産性を測定し、向上させるために、データに基づいたアプローチをとることです。

本章では、私たちの研究チームがどのようにしてこの目標を達成したかを説明します。ソフトウェア開発には測定可能な部分がたくさんありますが，何を測定すればよいのでしょうか？プロジェクトが選択された後、リサーチチームがプロセスの問題点を特定するために、意味のある測定基準をどのように特定するかを説明します。最後に、Google社がこれらの測定基準を使って生産性の向上を追跡する方法を紹介します。

本章では、GoogleのC++およびJava言語チームが提起した具体的な例として、「読みやすさ」を取り上げます。Googleの歴史のほとんどにおいて、これらのチームはGoogleの可読性プロセスを管理してきました。(読みやすさについては、第3章を参照してください）読みやすさのプロセスは、自動フォーマッタ（第8章）や送信をブロックするリンタ（第9章）が一般的になる前の、Googleの初期に導入されました。このプロセスは、何百人ものエンジニアが他のエンジニアに可読性を付与するために可読性レビューを行う必要があるため、運営自体にコストがかかります。エンジニアの中には、もはや実用性のない古臭いハズレのプロセスと捉えている人もいて、ランチテーブルを囲んで議論するのが好きな話題だった。言語チームからの具体的な質問は、「可読性評価に費やす時間は価値があるのか」というものだった。

## トリアージ。測定する価値があるのか？

エンジニアの生産性をどのように測定するかを決める前に、測定する価値があるかどうかを知る必要があります。測定にはコストがかかります。プロセスを測定し、結果を分析し、それを社内に広めるためには人手が必要です。さらに、測定プロセス自体が負担となり、エンジニアリング組織のスピードを低下させる可能性もあります。私たちは賢く測定し、見積もる必要があります。推測はしたくありませんが、不必要に測定して時間と資源を無駄にするべきではありません。

Googleでは、生産性を測定する価値があるかどうかをチームで判断するために、一連の質問を用意しました。まず、測定したい内容を具体的な質問形式で説明してもらいます。この質問が具体的であればあるほど、プロセスから利益を得られる可能性が高くなります。読みやすさを追求するチームが私たちに求めたのは、「エンジニアが読みやすさを追求するために必要なコストは、会社にもたらされるかもしれない利益に見合うものなのか」というシンプルな問いでした。

そして、その質問に対して、次のような点を考慮してもらいます。

- どんな結果を期待しているのか、そしてその理由は？

 私たちは、自分が中立な調査員であるかのように装いたいかもしれませんが、そうではありません。私たちは、何が起こるべきかについて先入観を持っています。このことを最初に認識することで、これらのバイアスに対処し、結果を後付けで説明しないようにすることができます。
 この質問をリーダビリティチームに投げかけたところ、チームは「よくわからない」と答えました。ある時点では、コストがメリットに見合っていたと確信していましたが、オートフォーマターや静的解析ツールの登場により、完全に確信を持てる人はいなくなりました。今では、このプロセスはハズレの儀式のようなものだと考えられるようになっていた。技術者にとってはメリットがあるかもしれないが（実際にメリットがあるという調査データもあった）、コードの作成者やレビュアーの時間を割く価値があるかどうかは、はっきりしなかった。

- データがあなたの期待する結果をサポートする場合、どのような行動をとりますか？

 アクションが取られないのであれば、測定する意味がないので、このように尋ねます。この結果が得られなかった場合に起こる予定の変更がある場合、アクションは実際には「現状維持」になるかもしれないことに注意してください。
 この点について質問したところ、読みやすさ向上チームの答えは単純明快で、プロセスのコストを正当化できるほどのメリットがあれば、読みやすさに関する研究やFAQのデータにリンクし、期待を持たせるために宣伝するとのことでした。

- 否定的な結果が出た場合、適切な処置が取られるのでしょうか？

 この質問をするのは、多くの場合、否定的な結果が出ても意思決定を変えることはできないからです。否定的な結果が出ても、それを覆すような判断材料が他にあるかもしれません。そのような場合は、そもそも測定する価値がないかもしれません。意思決定者が結果を知りたいと思っていても、他の理由で方針を変えようとしないことがわかっているからです。
 しかし、読みやすさの場合は、チームからの強い行動宣言がありました。それは、もし私たちの分析で、コストが利益を上回るか、利益が無視できるほど小さいことがわかったら、チームはそのプロセスを中止すると約束したのです。プログラミング言語によってフォーマッタや静的解析の成熟度が異なるため、この評価は言語ごとに行うことになります。

- その結果に基づいて行動を起こすことを決定するのは誰か、そしてそれを実行するのはいつか？

 この質問は、測定を依頼した人が、行動を起こす権限を与えられている人であることを確認するために行います（または、その人に代わって直接行動を起こします）。最終的に、ソフトウェアプロセスを測定する目的は、人々がビジネス上の意思決定を行えるようにすることです。どのような形のデータが彼らを納得させるのかなど、その個人が誰なのかを理解することが重要です。最高の調査には、構造化されたインタビューからログの統計的分析まで、さまざまなアプローチが含まれますが、意思決定者が必要とするデータを提供するためには、限られた時間しかないかもしれません。そのような場合には、意思決定者に合わせて対応するのがベストです。彼らは、インタビューから得られるストーリーに共感して意思決定を行う傾向があるのか（※2）、アンケート結果やログデータを信頼しているのか（※3）。複雑な統計的分析に抵抗はないのか？意思決定者が結果の形式を原理的に信じないのであれば、やはりプロセスを測定する意味はありません。
 読みやすさの場合は、プログラミング言語ごとに明確な意思決定者がいました。JavaとC++の2つの言語チームが積極的に支援の手を差し伸べてくれ、他のチームはまずそれらの言語での結果を待っていたのです(*3)。幸福度や学習状況の把握についてはエンジニアの自己申告を信頼していましたが、ベロシティやコード品質についてはログデータに基づく「ハードな数字」を見たいというのが意思決定者の本音でした。つまり、これらの指標については、定性的な分析と定量的な分析の両方を盛り込む必要があったのです。この作業には明確な期限はありませんでしたが、社内会議があり、変更があった場合の発表のタイミングとしては有効でした。この期限のおかげで、私たちはこの作業を完成させるために数ヶ月を費やすことができました。

このような質問をしてみると、多くの場合、測定は単に価値のないものであることがわかります...そして、それでよいのです。ツールやプロセスが生産性に与える影響を測定しないことには、多くの正当な理由があります。私たちが見てきたいくつかの例をご紹介しましょう。

- 今すぐにプロセス/ツールを変更する余裕はありません

 時間的な制約や金銭的な制約があってできない場合もあります。例えば、より高速なビルドツールに切り替えさえすれば、毎週何時間もの時間を節約できると判断したとします。しかし、切り替えのためには、全員が切り替えを行う間、開発を一時中断しなければならず、大きな資金の締め切りが迫っているため、中断する余裕はありません。エンジニアリングのトレードオフは、単独で評価されるものではありません。このようなケースでは、より広範な文脈において、結果として行動を遅らせることが完全に正当化されることを認識することが重要です。

- 結果が出ても、すぐに他の要因で無効になってしまう。

 ここでの例としては、ある組織のソフトウェアプロセスを、計画された再編成の直前に測定することが挙げられる。あるいは、廃止されたシステムの技術的負債の量を測定する。
 意思決定者は強い意見を持っており、彼らの信念を変えるのに十分な量の、適切な種類の証拠を提供することはできないだろう。
 これは、自分のオーディエンスを知ることにつながります。Googleでも、過去の経験から、あるテーマに対して揺るぎない信念を持っている人を見かけることがあります。また、自己申告を信じないため、調査データを信用しないステークホルダーもいます。また、少数のインタビューに基づいた説得力のあるストーリーに最も惹かれるステークホルダーもいます。もちろん、ログ解析でしか動かせないステークホルダーもいます。いずれの場合も、私たちは複合的な手法を用いて真実を明らかにしようとしますが、ステークホルダーがその問題に適していない手法しか信じられないのであれば、その作業を行う意味はありません。

- その結果は、あなたがやろうとしていたことをサポートするための虚栄心に満ちた指標としてしか使われません。

 これは、Googleでソフトウェアプロセスを測定しないようにお伝えしている最も一般的な理由かもしれません。多くの場合、人々は複数の理由からある決定を計画しており、ソフトウェア開発プロセスの改善は複数のメリットのうちの1つに過ぎない。例えば、Googleのリリースツールチームが、リリースワークフローシステムの変更を計画した際に、測定を要求したことがありました。変更の性質上、現状よりも悪くならないことは明らかでしたが、それが小さな改善なのか大きな改善なのかがわからなかったのです。私たちはチームに問いかけました。「もし、軽微な改善にとどまることがわかったら、たとえ投資に見合わないように見えても、とにかくその機能を実装するためにリソースを費やしますか？その答えは「イエス」でした。この機能はたまたま生産性を向上させましたが、これは副次的な効果で、パフォーマンスが向上し、リリースツールチームのメンテナンスの負担が軽減されたのです。

- 利用可能な唯一の測定基準は、問題を測定するのに十分な精度ではなく、他の要因と混同される可能性がある

 場合によっては、必要なメトリクス（メトリクスの特定方法については次のセクションを参照）が単に入手できないことがあります。このような場合には、精度の低い他のメトリクス（たとえば、書かれたコードの行数）を使って測定したくなることがあります。しかし、これらのメトリクスから得られる結果は、解釈できないものになります。その指標がステークホルダーの既存の信念を裏付けるものであれば、その指標が正確な測定値ではないことを考慮せずに、計画を進めてしまうかもしれません。確信が持てない場合は、測定基準自体の不正確さから簡単に説明がつき、ステークホルダーは再び計画を進めてしまうかもしれません。

ソフトウェアプロセスの測定に成功するということは、仮説の正否を証明することではなく、ステークホルダーが意思決定をするために必要なデータを提供することです。そのステークホルダーがデータを使わないのであれば、そのプロジェクトは常に失敗です。ソフトウェアプロセスの測定は、その結果に基づいて具体的な意思決定がなされる場合にのみ行うべきです。可読性チームの場合は、明確な意思決定が必要でした。測定結果がプロセスの有益性を示していれば、その結果を公表する。そうでなければ、そのプロセスは廃止されます。最も重要なことは、読みやすさ向上チームにはこの決定を下す権限があったということです。

## ゴールとシグナルで意味のあるメトリクスを選択する

ソフトウェアプロセスを測定することを決めた後、どのような測定基準を使用するかを決定する必要があります。コードの行数(LOC)では不十分なのは明らかですが(*4)、実際にエンジニアリングの生産性を測定するにはどうすればよいのでしょうか。

Googleでは、メトリクス作成の指針として、GSM（Goals/Signals/Metrics）というフレームワークを用いています。

- ゴールとは、望ましい最終結果のことです。これは、高いレベルで理解したいことを表現したもので、具体的な測定方法については言及しない。
- シグナルとは、最終結果を達成したことを知る方法です。シグナルは測定したいものですが、それ自体は測定できないかもしれません。
- メトリクスはシグナルの代理です。シグナルは、実際に測定できるものです。理想的な測定値ではないかもしれませんが、それに近いと考えられるものです。

GSMフレームワークは、メトリクスを作成する際に、いくつかの望ましい特性を促します。まず、最初にゴールを作成し、次にシグナルを作成し、最後にメトリクスを作成することで、街灯効果を防ぐことができます。この言葉は、「街灯の下で鍵を探す」というフルフレーズから来ています。見えるところだけを見ていると、正しい場所を見ていない可能性があります。メトリクスの場合は、簡単にアクセスできて測定しやすいメトリクスを、そのメトリクスが自分のニーズに合っているかどうかに関わらず使用してしまうと、このような現象が起こります。GSMでは、簡単に手に入るものではなく、実際に目標達成に役立つ測定基準を考えるようにしています。

第二に、GSMは、実際に結果を測定する前に、原則的なアプローチを用いて適切なメトリクスのセットを考え出すよう促すことで、メトリクス・クリープとメトリクス・バイアスの両方を防ぐのに役立ちます。例えば、原則的なアプローチなしに測定基準を選択し、その結果がステークホルダーの期待に応えられなかった場合を考えてみましょう。その場合、ステークホルダーから、期待通りの結果が得られると思われる別の測定基準を使うよう提案されるリスクがあります。最初に原則的なアプローチに基づいて選択しなかったので、彼らが間違っていると言う理由はありません。その代わり、GSMでは、当初の目標を測定する能力に基づいて測定基準を選択することを推奨しています。ステークホルダーは、これらの測定基準が当初の目標にマッピングされていることを容易に理解し、これが成果を測定するための最良の測定基準のセットであることに事前に同意することができます。

最後に、GSMは、測定カバレッジがある場所とない場所を示してくれます。GSMのプロセスでは、すべての目標をリストアップし、それぞれにシグナルを作成します。例で見るように、すべてのシグナルが測定可能であるとは限りません。GSMでは、少なくとも、測定不可能なものを特定しています。これらの欠落したメトリクスを特定することで、新しいメトリクスを作成する価値があるのか、あるいはまったく測定する価値がないのかを評価することができます。

重要なことは、トレーサビリティを維持することです。それぞれのメトリクスについて、それが代理となるシグナルと、それが測定しようとしているゴールにまで遡ることができるようにしなければなりません。これにより、どのメトリクスを測定しているのか、また、なぜそれを測定しているのかを確実に把握することができます。

## ゴール（目標）

ゴールとは、望ましい性質を示す言葉で、測定基準を参照せずに書かれます。それだけでは測定できませんが、優れたゴールは、シグナルや測定基準に進む前に、誰もが同意できるものです。

これを実現するためには、まず最初に、測定すべき正しいゴールを特定する必要があります。チームは自分たちの仕事のゴールを知っているはずだからです。しかし、私たちの研究チームは、多くの場合、生産性に含まれるすべての可能なトレードオフを含めることを忘れており、それが誤った測定につながっていることを発見しました。

読みやすさの例では、チームが読みやすさのプロセスを早く簡単にすることに集中し、コード品質に関する目標を忘れていたとします。チームは、レビュープロセスにかかる時間や、エンジニアがプロセスにどれだけ満足しているかなどのトラッキング測定値を設定しました。チームメイトの一人が次のように提案します。

 私はあなたのレビュー速度を非常に速くすることができます：コードレビューを完全に削除すればいいのです。

これは明らかに極端な例ですが、チームは測定の際に核となるトレードオフを忘れてしまうことがよくあります。この問題を解決するために、私たちの研究チームは、生産性を5つのコアコンポーネントに分けました。この5つの構成要素は互いにトレードオフの関係にあり、チームはそれぞれの構成要素の目標を検討し、不用意に1つの構成要素を向上させて他の構成要素を低下させないようにしています。この5つの要素を覚えやすくするために、私たちは「QUANTS」というニーモニックを使っています。

- コードの品質

 作成されたコードの品質はどうか？テストケースはリグレッションを防ぐのに十分なものか？アーキテクチャは、リスクや変更を軽減するのに十分なものか？

- エンジニアの注意力

 エンジニアはどのくらいの頻度でフロー状態になっているか？通知によってどれくらい気を取られているか？ツールはエンジニアにコンテキストスイッチを促しているか？

- 知的複雑さ

 タスクを完了するために必要な認知的負荷はどのくらいか？解決しようとしている問題の本質的な複雑さとは？エンジニアは不必要な複雑さに対処する必要があるのか？

- テンポと速度

 エンジニアはどのくらいの速さでタスクをこなせるか？どのくらいの速さでリリースを出すことができますか？与えられた時間枠の中でどれだけのタスクを完了できるか？

- 満足度

 エンジニアは自分のツールにどれだけ満足しているのか？ツールはエンジニアのニーズをどれだけ満たしているか？エンジニアは自分の仕事や最終製品にどれだけ満足しているのか？エンジニアは燃え尽き症候群になっていないか？

読みやすさの例に戻ると、私たちの研究チームは読みやすさチームと協力して、読みやすさプロセスの生産性目標をいくつか特定しました。

- コードの品質

 読みやすさ向上プロセスの結果、エンジニアはより質の高いコードを書き、読みやすさ向上プロセスの結果、より一貫性のあるコードを書き、読みやすさ向上プロセスの結果、コードの健全性を保つ文化に貢献する。

- エンジニアからの注目

 読みやすさについての注意目標はありませんでした。これでOK! エンジニアの生産性に関するすべての質問が、5つの分野すべてにおいてトレードオフを伴うわけではない。

- 知的複雑さ

 エンジニアは、読みやすさを追求した結果、Google のコードベースや最善のコーディング方法について学び、読みやすさを追求した過程でメンタリングを受けます。

- テンポと速度

 読みやすさを追求した結果、エンジニアはより速く、より効率的に仕事をこなすことができるようになりました。

- 満足感

 エンジニアはリーダビリティ・プロセスの利点を理解し、プロセスへの参加に前向きな気持ちを持っています。

## Signals

A signal is the way in which we will know we’ve achieved our goal. Not all signals are measurable, but that’s acceptable at this stage. There is not a 1:1 relationship between signals and goals. Every goal should have at least one signal, but they might have more. Some goals might also share a signal. Table 7-1 shows some example signals for the goals of the readability process measurement.

Table 7-1. Signals and goals

| Goals | Signals |
|:----- |:------- |
|Engineers write higher-quality code as a result of the readability process. | Engineers who have been granted readability judge their code to be of higher quality than engineers who have not been granted readability. The readability process has a positive impact on code quality. |
|Engineers learn about the Google codebase and best coding practices as a result of the readability process.|Engineers report learning from the readability process.|
|Engineers receive mentoring during the readability process. |Engineers report positive interactions with experienced Google engineers who serve as reviewers during the readability process.|
|Engineers complete work tasks faster and more efficiently as a result of the readability process. |Engineers who have been granted readability judge themselves to be more productive than engineers who have not been granted readability. Changes written by engineers who have been granted readability are faster to review than changes written by engineers who have not been granted readability.|
|Engineers see the benefit of the readability process and have positive feelings about participating in it. |Engineers view the readability process as being worthwhile.|

## Metrics

Metrics are where we finally determine how we will measure the signal. Metrics are not the signal themselves; they are the measurable proxy of the signal. Because they are a proxy, they might not be a perfect measurement. For this reason, some signals might have multiple metrics as we try to triangulate on the underlying signal.

For example, to measure whether engineers’ code is reviewed faster after readability, we might use a combination of both survey data and logs data. Neither of these metrics really provide the underlying truth. (Human perceptions are fallible, and logs metrics might not be measuring the entire picture of the time an engineer spends reviewing a piece of code or can be confounded by factors unknown at the time, like the size or difficulty of a code change.) However, if these metrics show different results, it signals that possibly one of them is incorrect and we need to explore further. If they are the same, we have more confidence that we have reached some kind of truth.

Additionally, some signals might not have any associated metric because the signal might simply be unmeasurable at this time. Consider, for example, measuring code quality. Although academic literature has proposed many proxies for code quality, none of them have truly captured it. For readability, we had a decision of either using a poor proxy and possibly making a decision based on it, or simply acknowledging that this is a point that cannot currently be measured. Ultimately, we decided not to capture this as a quantitative measure, though we did ask engineers to self-rate their code quality.

Following the GSM framework is a great way to clarify the goals for why you are measuring your software process and how it will actually be measured. However, it’s still possible that the metrics selected are not telling the complete story because they are not capturing the desired signal. At Google, we use qualitative data to validate our metrics and ensure that they are capturing the intended signal.

## Using Data to Validate Metrics

As an example, we once created a metric for measuring each engineer’s median build latency; the goal was to capture the “typical experience” of engineers’ build latencies. We then ran an experience sampling study. In this style of study, engineers are interrupted in context of doing a task of interest to answer a few questions. After an engineer started a build, we automatically sent them a small survey about their experiences and expectations of build latency. However, in a few cases, the engineers responded that they had not started a build! It turned out that automated tools were starting up builds, but the engineers were not blocked on these results and so it didn’t “count” toward their “typical experience.” We then adjusted the metric to exclude such builds.(*5)

Quantitative metrics are useful because they give you power and scale. You can measure the experience of engineers across the entire company over a large period of time and have confidence in the results. However, they don’t provide any context or narrative. Quantitative metrics don’t explain why an engineer chose to use an antiquated tool to accomplish their task, or why they took an unusual workflow, or why they circumvented a standard process. Only qualitative studies can provide this information, and only qualitative studies can then provide insight on the next steps to improve a process.

Consider now the signals presented in Table 7-2. What metrics might you create to measure each of those? Some of these signals might be measurable by analyzing tool and code logs. Others are measurable only by directly asking engineers. Still others might not be perfectly measurable—how do we truly measure code quality, for example?

Ultimately, when evaluating the impact of readability on productivity, we ended up with a combination of metrics from three sources. First, we had a survey that was specifically about the readability process. This survey was given to people after they completed the process; this allowed us to get their immediate feedback about the process. This hopefully avoids recall bias,(*6) but it does introduce both recency bias(*7) and sampling bias.(*8) Second, we used a large-scale quarterly survey to track items that were not specifically about readability; instead, they were purely about metrics that we expected readability should affect. Finally, we used fine-grained logs metrics from our developer tools to determine how much time the logs claimed it took engineers to complete specific tasks.(*9) Table 7-2 presents the complete list of metrics with their corresponding signals and goals.

Table 7-2. Goals, signals, and metrics

|QUANTS|Goal|Signal|Metric|
|:-----|:---|:-----|:-----|
|Quality of the code|Engineers write higherquality code as a result of the readability process.|Engineers who have been granted readability judge their code to be of higher quality than engineers who have not been granted readability.|Quarterly Survey: Proportion of engineers who report being satisfied with the quality of their own code|
| | |The readability process has a positive impact on code quality.|Readability Survey: Proportion of engineers reporting that readability reviews have no impact or negative impact on code quality|
| | | |Readability Survey: Proportion of engineers reporting that participating in the readability process has improved code quality for their team|
| |Engineers write more consistent code as a result of the readability process. |Engineers are given consistent feedback and direction in code reviews by readability reviewers as a part of the readability process.|Readability Survey: Proportion of engineers reporting inconsistency in readability reviewers’ comments and readability criteria.|
| |Engineers contribute to a culture of code health as a result of the readability process.|Engineers who have been granted readability regularly comment on style and/or readability issues in code reviews.|Readability Survey: Proportion of engineers reporting that they regularly comment on style and/or readability issues in code reviews|
|Attention from engineers|n/a|n/a|n/a|
|Intellectual|Engineers learn about the Google codebase and best coding practices as a result of the readability process.|Engineers report learning from the readability process.|Readability Survey: Proportion of engineers reporting that they learned about four relevant topics|
| | | |Readability Survey: Proportion of engineers reporting that learning or gaining expertise was a strength of the readability process|
| |Engineers receive mentoring during the readability process.|Engineers report positive interactions with experienced Google engineers who serve as reviewers during the readability process.|Readability Survey: Proportion of engineers reporting that working with readability reviewers was a strength of the readability process|
|Tempo/velocity|Engineers are more productive as a result of the readability process.|Engineers who have been granted readability judge themselves to be more productive than engineers who have not been granted readability.|Quarterly Survey: Proportion of engineers reporting that they’re highly productive|
| | |Engineers report that completing the readability process positively affects their engineering velocity.|Readability Survey: Proportion of engineers reporting that not having readability reduces team engineering velocity|
| | |Changelists (CLs) written by engineers who have been granted readability are faster to review than CLs written by engineers who have not been granted readability.|Logs data: Median review time for CLs from authors with readability and without readability|
| | |CLs written by engineers who have been granted readability are easier to shepherd through code review than CLs written by engineers who have not been granted readability.|Logs data: Median shepherding time for CLs from authors with readability and without readability|
| | |CLs written by engineers who have been granted readability are faster to get through code review than CLs written by engineers who have not been granted readability.|Logs data: Median time to submit for CLs from authors with readability and without readability|
| | |The readability process does not have a negative impact on engineering velocity.|Readability Survey: Proportion of engineers reporting that the readability process negatively impacts their velocity|
| | | |Readability Survey: Proportion of engineers reporting that readability reviewers responded promptly|
| | | |Readability Survey: Proportion of engineers reporting that timeliness of reviews was a strength of the readability process|
|Satisfaction|Engineers see the benefit of the readability process and have positive feelings about participating in it.|Engineers view the readability process as being an overall positive experience.|Readability Survey: Proportion of engineers reporting that their experience with the readability process was positive overall|
| | |Engineers view the readability process as being worthwhile|Readability Survey: Proportion of engineers reporting that the readability process is worthwhile|
| | | |Readability Survey: Proportion of engineers reporting that the quality of readability reviews is a strength of the process|
| | | |Readability Survey: Proportion of engineers reporting that thoroughness is a strength of the process|
| | |Engineers do not view the readability process as frustrating.|Readability Survey: Proportion of engineers reporting that the readability process is uncertain, unclear, slow, or frustrating|
| | | |Quarterly Survey: Proportion of engineers reporting that they’re satisfied with their own engineering velocity|

## Taking Action and Tracking Results

Recall our original goal in this chapter: we want to take action and improve productivity. After performing research on a topic, the team at Google always prepares a list of recommendations for how we can continue to improve. We might suggest new features to a tool, improving latency of a tool, improving documentation, removing obsolete processes, or even changing the incentive structures for the engineers. Ideally, these recommendations are “tool driven”: it does no good to tell engineers to change their process or way of thinking if the tools do not support them in doing so. We instead always assume that engineers will make the appropriate trade-offs if they have the proper data available and the suitable tools at their disposal.

For readability, our study showed that it was overall worthwhile: engineers who had achieved readability were satisfied with the process and felt they learned from it. Our logs showed that they also had their code reviewed faster and submitted it faster, even accounting for no longer needing as many reviewers. Our study also showed places for improvement with the process: engineers identified pain points that would have made the process faster or more pleasant. The language teams took these recommendations and improved the tooling and process to make it faster and to be more transparent so that engineers would have a more pleasant experience.

## Conclusion

At Google, we’ve found that staffing a team of engineering productivity specialists has widespread benefits to software engineering; rather than relying on each team to chart its own course to increase productivity, a centralized team can focus on broadbased solutions to complex problems. Such “human-based” factors are notoriously difficult to measure, and it is important for experts to understand the data being analyzed given that many of the trade-offs involved in changing engineering processes are difficult to measure accurately and often have unintended consequences. Such a team must remain data driven and aim to eliminate subjective bias.

## TL;DRs

- Before measuring productivity, ask whether the result is actionable, regardless of whether the result is positive or negative. If you can’t do anything with the result, it is likely not worth measuring.
- Select meaningful metrics using the GSM framework. A good metric is a reasonable proxy to the signal you’re trying to measure, and it is traceable back to your original goals.
- Select metrics that cover all parts of productivity (QUANTS). By doing this, you ensure that you aren’t improving one aspect of productivity (like developer velocity) at the cost of another (like code quality).
- Qualitative metrics are metrics, too! Consider having a survey mechanism for tracking longitudinal metrics about engineers’ beliefs. Qualitative metrics should also align with the quantitative metrics; if they do not, it is likely the quantitative metrics that are incorrect.
- Aim to create recommendations that are built into the developer workflow and incentive structures. Even though it is sometimes necessary to recommend additional training or documentation, change is more likely to occur if it is built into the developer’s daily habits.


-----

1 Frederick P. Brooks, The Mythical Man-Month: Essays on Software Engineering (New York: Addison-Wesley,
1995).
2 It’s worth pointing out here that our industry currently disparages “anecdata,” and everyone has a goal of being “data driven.” Yet anecdotes continue to exist because they are powerful. An anecdote can provide context and narrative that raw numbers cannot; it can provide a deep explanation that resonates with others because it mirrors personal experience. Although our researchers do not make decisions on anecdotes, we do use and encourage techniques such as structured interviews and case studies to deeply understand phenomena and provide context to quantitative data.
3 Java and C++ have the greatest amount of tooling support. Both have mature formatters and static analysis tools that catch common mistakes. Both are also heavily funded internally. Even though other language teams, like Python, were interested in the results, clearly there was not going to be a benefit for Python to remove readability if we couldn’t even show the same benefit for Java or C++.
4 “From there it is only a small step to measuring ‘programmer productivity’ in terms of ‘number of lines of code produced per month.’ This is a very costly measuring unit because it encourages the writing of insipid code, but today I am less interested in how foolish a unit it is from even a pure business point of view. My point today is that, if we wish to count lines of code, we should not regard them as ‘lines produced’ but as ‘lines spent’: the current conventional wisdom is so foolish as to book that count on the wrong side of the ledger.” Edsger Dijkstra, on the cruelty of really teaching computing science, EWD Manuscript 1036.
5 It has routinely been our experience at Google that when the quantitative and qualitative metrics disagree, it was because the quantitative metrics were not capturing the expected result.
6 Recall bias is the bias from memory. People are more likely to recall events that are particularly interesting or frustrating.
7 Recency bias is another form of bias from memory in which people are biased toward their most recent experience. In this case, as they just successfully completed the process, they might be feeling particularly good about it.
8 Because we asked only those people who completed the process, we aren’t capturing the opinions of those who did not complete the process.
9 There is a temptation to use such metrics to evaluate individual engineers, or perhaps even to identify high and low performers. Doing so would be counterproductive, though. If productivity metrics are used for performance reviews, engineers will be quick to game the metrics, and they will no longer be useful for measuring and improving productivity across the organization. The only way to make these measurements work is to let go of the idea of measuring individuals and embrace measuring the aggregate effect.




