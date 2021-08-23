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

## シグナル

シグナルとは、目標を達成したことを知るための方法です。すべてのシグナルが測定可能なわけではありませんが、この段階ではそれは許容されます。シグナルとゴールの間には、1対1の関係はありません。すべてのゴールは少なくとも1つのシグナルを持つべきですが、それ以上のシグナルを持つ場合もあります。また、いくつかのゴールはシグナルを共有するかもしれません。表7-1は、読みやすさのプロセス測定の目標に対するシグナルの例を示している。

Table 7-1. Signals and goals

| Goals | Signals |
|:----- |:------- |
|エンジニアは、読みやすさを追求した結果、より質の高いコードを書くようになります。| リーダビリティを付与されたエンジニアは、付与されていないエンジニアよりも自分のコードが高品質であると判断する。読みやすさ向上プロセスは、コードの品質に良い影響を与える。|
|エンジニアは、可読性プロセスの結果として、Google のコードベースとベスト コーディング プラクティスについて学んでいる。|
|エンジニアは、読みやすさ向上のためのメンタリングを受けています。|エンジニアは、可読性評価の過程でレビュアーを務める経験豊富なGoogleのエンジニアと積極的に交流したと報告している。|
|エンジニアは、読みやすさ向上の結果、より速く、より効率的に仕事をこなすことができるようになった。|可読性を付与されたエンジニアは、可読性を付与されていないエンジニアよりも生産性が高いと判断している。読みやすさを付与されたエンジニアが書いた変更は、付与されていないエンジニアが書いた変更よりもレビューが早い。|
|エンジニアは可読性プロセスの利点を理解しており、このプロセスに参加することに前向きな気持ちを持っている。|エンジニアは、読みやすさ向上のプロセスに価値があると考えている。|

## メトリクス

メトリクスとは、シグナルをどのように測定するかを最終的に決定する場所です。メトリクスはシグナルそのものではなく、シグナルの測定可能な代理です。代理人であるがゆえに、完璧な測定ではないかもしれません。このような理由から、シグナルによっては複数の測定基準を設けて、根本的なシグナルの三角測量を行うことがあります。

例えば、エンジニアのコードが読みやすいかどうかを測定するには、調査データとログデータの両方を組み合わせて使用することがあります。しかし、これらの指標はどちらも真実を示すものではありません。(人間の認識には誤りがあり、ログの測定基準は、エンジニアがコードの一部をレビューするのに費やす時間の全体像を測定していないかもしれませんし、コード変更の規模や難易度など、その時点ではわからない要因に惑わされる可能性もあります) しかし、これらの測定基準が異なる結果を示す場合、おそらくどちらかが間違っていることを示しており、さらに調査する必要があります。同じ結果であれば、ある種の真実に到達したという確信が得られます。

また、シグナルの中には、現時点では測定不可能なものもあるため、関連する指標がない場合もあります。例えば、コード品質の測定を考えてみましょう。学術論文ではコード品質の指標が数多く提案されていますが、コード品質を真に捉えたものはありません。可読性については、劣悪なプロキシを使用し、それに基づいて判断を下すか、あるいは、現在は測定できないポイントであることを認めるか、どちらかを選択しなければなりませんでした。最終的には、エンジニアにコードの品質を自己評価してもらいましたが、これを定量的な指標として捉えることはしませんでした。

GSMのフレームワークに従うことは、なぜソフトウェアプロセスを測定するのか、そして実際にどのように測定するのかという目標を明確にするための素晴らしい方法です。しかし、選択した測定基準が、望ましい信号を捉えていないために、完全なストーリーを語っていない可能性もあります。Googleでは、定性データを用いて測定基準を検証し、意図したシグナルを捉えているかどうかを確認しています。

## データを使った測定基準の検証

例えば、各エンジニアのビルドレイテンシーの中央値を測定する指標を作成したことがあります。その目的は、エンジニアのビルドレイテンシーの「典型的な経験」を把握することでした。そこで、エクスペリエンスサンプリング調査を実施しました。この調査では、エンジニアが興味のあるタスクを実行しているときに、いくつかの質問に答えるために中断します。エンジニアがビルドを開始した後、ビルドの待ち時間に関する経験と期待についての小さなアンケートを自動的に送りました。しかし、いくつかのケースでは、エンジニアはビルドを開始していないと回答しました。その結果、自動化されたツールがビルドを開始していたが、エンジニアはその結果をブロックされていなかったため、彼らの "典型的な経験 "にはカウントされていなかったのだ。そこで、そのようなビルドを除外するように指標を調整しました(*5)。

定量的なメトリクスが有用なのは、パワーとスケールを与えてくれるからです。会社全体のエンジニアの経験を長期間にわたって測定し、その結果に自信を持つことができます。しかし、定量的な指標は、文脈や物語を提供しません。定量的な指標では、なぜエンジニアが自分のタスクを達成するために時代遅れのツールを使うことを選んだのか、なぜ普通ではないワークフローをとったのか、なぜ標準的なプロセスを回避したのかを説明できません。このような情報を提供できるのは定性調査だけであり、定性調査はプロセスを改善するための次のステップへのインサイトを提供することができます。

ここで、表7-2のシグナルを考えてみましょう。それぞれのシグナルを測定するために、どのようなメトリクスを作成できますか？これらのシグナルの中には、ツールやコードのログを分析することで測定可能なものもあります。また、エンジニアに直接聞いてみないと測定できないものもあります。また、完全には測定できないものもあります。例えば、コードの品質を本当に測定できるのでしょうか？

最終的に、読みやすさが生産性に与える影響を評価するために、私たちは3つのソースから得られた指標を組み合わせました。まず、読みやすさのプロセスについてのアンケートを実施しました。このアンケートは、プロセスが完了した後に行われました。これにより、プロセスに関するフィードバックをすぐに得ることができました。これにより、想起バイアス(*6)を回避することができましたが、想起バイアス(*7)とサンプリングバイアス(*8)が発生しました。最後に、開発者ツールの詳細なログメトリクスを用いて、エンジニアが特定のタスクを完了するのにどれだけの時間がかかったかをログで確認しました(*9)。 表7-2は、メトリクスの全リストと、それに対応するシグナルおよびゴールを示しています。

Table 7-2. Goals, signals, and metrics

|QUANTS|Goal|Signal|Metric|
|:-----|:---|:-----|:-----|
|コードの品質|読みやすさを追求した結果、エンジニアはより質の高いコードを書くことができるようになりました。|可読性を付与されたエンジニアは、可読性を付与されていないエンジニアに比べて、自分のコードの品質が高いと判断する。|四半期ごとの調査。自分が書いたコードの品質に満足していると答えたエンジニアの割合|
| | |読みやすさを追求したプロセスは、コードの品質に良い影響を与えます。|リーダビリティ調査。読みやすさに関するレビューがコード品質に影響しない、または悪影響を与えると回答したエンジニアの割合|
| | | |Readability調査。Readabilityプロセスに参加したことで、チームのコード品質が向上したと回答したエンジニアの割合|
| |読みやすさを追求した結果、エンジニアはより一貫性のあるコードを書くことができるようになりました。|エンジニアは、コードレビューにおいて、可読性プロセスの一環として、可読性レビュアーから一貫したフィードバックと指示を受けます。|読みやすさに関する調査。可読性審査員のコメントと可読性基準に矛盾があると回答したエンジニアの割合。|
| |エンジニアは、読みやすさを追求した結果、コードの健全性を保つ文化に貢献しています。|可読性を認められたエンジニアは、コードレビューでスタイルや可読性の問題について定期的にコメントしています。|読みやすさに関する調査。コードレビューにおいて、スタイルや読みやすさの問題について定期的にコメントしていると回答したエンジニアの割合|
|エンジニアからの注目|n/a|n/a|n/a|
|知的|エンジニアは、読みやすさを追求した結果、Googleのコードベースやベストなコーディング手法について学ぶことができます。|エンジニアは読みやすさのプロセスから学んだことを報告します。|読みやすさ調査。関連する4つのトピックについて学んだと回答したエンジニアの割合|
| | | |Readability調査。読みやすさを追求した結果、専門知識の習得や獲得ができたと回答したエンジニアの割合|
| |読み上げの際には、エンジニアがメンタリングを受けます。|エンジニアからは、読みやすさを追求する過程でレビュアーを務める経験豊富なグーグルのエンジニアとの交流が良かったとの報告を受けています。|読みやすさに関する調査 可読性評価者との共同作業が可読性評価プロセスの長所であると回答したエンジニアの割合|
|テンポ/速度|読みやすさを追求した結果、エンジニアの生産性が向上しました。|可読性を獲得したエンジニアは、可読性を獲得していないエンジニアよりも生産性が高いと判断している。|四半期ごとの調査。生産性が高いと回答したエンジニアの割合|
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




