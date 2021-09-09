# CHAPTER 9 Code Review


Written by Tom Manshreck and Caitlin Sadowski

Edited by Lisa Carey

コードレビューとは、コードベースにコードを導入する前に、そのコードを作者以外の人間がレビューするプロセスである。これは簡単な定義ですが、コードレビューのプロセスの実装は、ソフトウェア業界全体で大きく異なります。ある組織では、コードベース全体の中から選ばれた「ゲートキーパー」たちが変更をレビューします。また、コードレビューのプロセスを小さなチームに委ね、チームごとに異なるレベルのコードレビューを要求することもあります。Googleでは、基本的にすべての変更がコミットされる前にレビューされ、すべてのエンジニアがレビューの開始と変更の確認に責任を持っています。

コードレビューは一般的に、プロセスとそれをサポートするツールの組み合わせが必要です。Googleでは、Critiqueというカスタムコードレビューツールを使ってプロセスをサポートしています(*1)。この章では、特定のツールではなく、Googleで実践されているコードレビューのプロセスに焦点を当てています。これは、これらの基礎がツールよりも古いものであることと、これらの洞察のほとんどが、コードレビューに使用するどのようなツールにも適用できるものであるためです。


  Critiqueについては、第19章を参照してください。

コードレビューの利点の中には、コードベースに入る前にコードのバグを検出できるなど、よく知られており(*2)、(測定が不正確ではあるが)ある程度明白なものがあります。しかし、それ以外のメリットは、より微妙なものです。Googleのコードレビュープロセスは非常にユビキタスで広範囲にわたっているため、心理的なものも含め、このような微妙な効果の多くに気付きました。

## コードレビューの流れ

コードレビューは、ソフトウェア開発のさまざまな段階で行われます。Googleでは、コードレビューは変更をコードベースにコミットする前に行われます。この段階はプリコミットレビューとも呼ばれます。コードレビューの主な目的は、他のエンジニアに変更を承諾してもらうことであり、私たちはその変更を「looks good to me」（LGTM）というタグで表します。私たちはこのLGTMを、変更をコミットするために必要なパーミッションの「ビット」として使用します（後述の他のビットと組み合わせて使用します）。
Googleの典型的なコードレビューは、以下のような手順で行われます。

1. ユーザーが自分のワークスペースのコードベースに変更を書き込みます。この作者は、変更のスナップショット（パッチとそれに対応する説明文）を作成し、コードレビューツールにアップロードします。この変更により、コードベースに対する diff が作成され、どのコードが変更されたかを評価するために使用されます。
2. 作者は、この最初のパッチを使って、自動レビューコメントを適用したり、セルフレビューを行ったりすることができます。作者は、変更点の diff に満足したら、その変更点を 1 人または複数のレビュアーに郵送します。このプロセスでは、それらのレビュアーに通知し、スナップショットの閲覧とコメントを求めます。
3. レビューアは、コードレビューツールで変更箇所を開き、diffにコメントを投稿します。コメントの中には、明示的な解決を求めるものがあります。単に情報を提供するものもあります。
4. 作者は、フィードバックに基づいて変更を修正し、新しいスナップショットをアップロードして、レビュアーに返信します。ステップ3と4は複数回繰り返すことができます。
5. レビューアが変更の最新の状態に満足した後、変更に同意し、「looks good to me」(LGTM)とマークして承認します。デフォルトでは1つのLGTMのみが要求されますが、コンベンションではすべてのレビュアーが変更に同意することを要求する場合もあります。
6. 変更が LGTM とマークされた後、すべてのコメントを解決し、変更が承認されていれば、作者はその変更をコードベースにコミットすることができます。承認については、次のセクションで説明します。

このプロセスについては、本章の後半で詳しく説明します。

-----

### コードには責任がある

コード自体が負債であることを忘れない（受け入れる）ことが重要です。必要な責任かもしれませんが、コードはそれだけでは、どこかの誰かのメンテナンス作業に過ぎません。飛行機が運ぶ燃料のように、コードには重みがありますが、もちろん飛行機が飛ぶためには必要なものです。

新しい機能が必要になることはよくありますが、そもそもコードを開発する前に、新しい機能が必要であるかどうかを確認するための注意が必要です。重複したコードは無駄な作業であるだけでなく、実際にはコードを持たない場合よりも時間的なコストがかかることがあります。一つのコードパターンで簡単にできる変更でも、コードベースに重複があるとより多くの労力を必要とします。全く新しいコードを書くことは嫌われるので、ある人はこう言います。"もしゼロから書いているなら、それは間違っている!"

これは、ライブラリやユーティリティのコードに特に当てはまります。おそらく、あなたがユーティリティを書いていても、Googleと同じ規模のコードベースのどこかで、他の誰かが同じようなことをしているでしょう。そのため、第17章で説明したようなツールは、そのようなユーティリティコードを見つけたり、重複したコードの導入を防いだりするために非常に重要です。理想的には、このような調査が事前に行われ、新しいコードを書く前に、新しいもののデザインが適切なグループに伝えられていることです。

もちろん、新しいプロジェクトが発生したり、新しい技術が導入されたり、新しいコンポーネントが必要になったりすることはあります。とはいえ、コードレビューは以前の設計上の決定を蒸し返したり、議論したりする場ではありません。設計の決定には時間がかかることが多く、設計案の配布、APIレビューなどでの設計に関する議論、そしておそらくプロトタイプの開発が必要になります。全く新しいコードのコードレビューが突然行われるべきではないのと同様に、コードレビューのプロセス自体も、過去の決定事項を再検討する機会と見なすべきではありません。

-----

## Googleにおけるコードレビューの仕組み

典型的なコードレビュープロセスがどのように機能するかを大まかに指摘してきましたが、重要なのは細部にあります。このセクションでは、Googleでのコードレビューの仕組みと、これらのプラクティスがどのようにして時間をかけて適切にスケーリングすることを可能にしているかについて、詳細に説明します。
Googleでは、どのような変更でも「承認」を必要とするレビューの3つの側面があります。

- 他のエンジニアが、コードが適切であり、作者が主張していることを実行しているかどうか、正しさと理解度をチェックすること。これはチームメンバーであることが多いのですが、必ずしもそうである必要はありません。このことは、LGTMの許可ビットに反映されています。許可ビットは、ピア・レビュアーがコードを「良さそうだ」と同意した後にセットされます。
- コードがコードベースの特定の部分に適している(特定のディレクトリにチェックインできる)という、コード所有者の1人からの承認。作者がそのような所有者である場合、この承認は暗黙の了解となることがあります。Googleのコードベースは、特定のディレクトリの所有者が階層化されたツリー構造になっています。(第16章参照)。所有者は、特定のディレクトリのゲートキーパーの役割を果たします。エンジニアが提案した変更は、他のエンジニアによってLGTMされるかもしれませんが、問題のディレクトリのオーナーは、コードベースの自分の部分への追加を承認しなければなりません。そのようなオーナーは、コードベースの特定の領域に精通していると思われる技術リーダーや他のエンジニアかもしれません。オーナーシップの権限をどれだけ広く、あるいは狭く設定するかは、各チームに任されています。
- 言語の「読みやすさ」(*3)を持つ人が、その言語のスタイルやベストプラクティスに準拠していることを承認し、私たちが期待する方法でコードが書かれているかどうかをチェックします。この承認も、著者にそのような読みやすさがあれば、暗黙の了解となるかもしれません。これらのエンジニアは、そのプログラミング言語のリーダビリティを付与された全社のエンジニアの中から引き抜かれます。

しかし、ほとんどのレビューでは、一人の人間がこの3つの役割を担っているため、プロセスが非常に速くなっています。重要なのは、著者が後者の2つの役割を担うこともできるということです。自分のコードベースにコードをチェックするためには、他のエンジニアがLGTMを必要とするだけですが、その言語での読みやすさをすでに持っていることが条件となります（オーナーがそうであることが多い）。

これらの要件により、コードレビュープロセスは非常に柔軟になります。あるプロジェクトのオーナーであり、そのコードの言語の可読性を持っているテックリードは、他のエンジニアからのLGTMだけでコード変更を提出することができます。そのような権限を持たないインターンは、言語可読性を持つオーナーから承認を得れば、同じコードベースに同じ変更を提出することができます。前述の3つの許可「ビット」は、どのような組み合わせも可能です。作者は、すべてのレビュアーにLGTMを求めるように変更を明示的にタグ付けすることで、別々の人に複数のLGTMを要求することもできます。

実際には、複数の承認を必要とするコードレビューのほとんどは、ピアエンジニアからLGTMを得て、次に適切なコードオーナー/可読性レビュアー（複数）に承認を求めるという2段階のプロセスを経るのが普通です。これにより、2つの役割がコードレビューの異なる側面に焦点を当てることができ、レビュー時間を短縮することができます。プライマリーレビュアーは、コードの正しさとコード変更の一般的な妥当性に注目することができ、コードオーナーは、コードの各行の詳細に注目しなくても、この変更がコードベースの自分の部分に適切であるかどうかに注目することができます。承認者が求めているものは、言い換えればピアレビューとは異なるものであることが多いのです。結局のところ、誰かが自分のプロジェクトやディレクトリにコードをチェックインしようとしているのです。彼らは次のような質問に関心があります。"このコードは維持するのが簡単か難しいか？" "このコードは私の技術的負債を増やすことになるのか？" "チーム内でメンテナンスするための専門知識はあるのか？"

これら3つのタイプのレビューを1人のレビュアーが担当できるのであれば、そのタイプのレビュアーがすべてのコードレビューを担当すればよいのではないでしょうか。その答えは「規模」です。3つの役割を分けることで、コードレビューのプロセスに柔軟性を持たせることができます。ユーティリティーライブラリ内の新しい関数を仲間と一緒に作っている場合、チーム内の誰かにコードの正しさと理解度をレビューしてもらいます。数回のラウンド（おそらく数日間）の後、あなたのコードがピア・レビュアーを満足させ、LGTMを得ることができます。あとは、そのライブラリのオーナー（オーナーは適切な読みやすさを持っていることが多い）に変更を承認してもらうだけです。

----

### 所有権

Hyrum Wright

専用のリポジトリで少人数のチームで作業する場合、チーム全体にリポジトリ内のすべてのものへのアクセスを許可するのが一般的です。結局のところ、他のエンジニアを知っているし、ドメインが狭いので各人が専門家になれるし、人数が少ないので潜在的なエラーの影響を抑えることができるからです。

チームの規模が大きくなると、このアプローチは拡張性に欠けるようになります。その結果、リポジトリが乱雑に分割されるか、誰がどのような知識と責任を持っているかをリポジトリの異なる部分に記録するための異なるアプローチが必要になります。Googleでは、この一連の知識と責任をオーナーシップと呼び、それを行使する人をオーナーと呼んでいます。この概念は、ソースコードの集合体を所有することとは異なり、コードベースのある部分について会社の利益のために行動するスチュワードシップの感覚を意味しています。(確かに、もう一度やり直すとしたら、「スチュワード」という言葉の方がいいかもしれません)。

特別な名前のOWNERSファイルには、ディレクトリとその子の所有権を持つ人のユーザー名がリストアップされています。これらのファイルは、他のOWNERSファイルや外部のアクセスコントロールリストへの参照を含むこともありますが、最終的には個人のリストに解決されます。各サブディレクトリには、それぞれ別のOWNERSファイルが含まれており、その関係は階層的に加算されていきます。OWNERSファイルは、チームが必要とする数のエントリーを持つことができますが、責任の所在を明確にするために、比較的小さくて集中的なリストを持つことを推奨します。

Google のコードを所有すると、自分の権限内のコードを承認する権利が与えられますが、この権利には、所有しているコードを理解したり、理解している人を見つける方法を知っておくなどの責任が伴います。新しいメンバーにオーナーシップを与える基準はチームによって異なりますが、一般的には、オーナーシップを入社式の儀式として使わないようにし、退社するメンバーにはできるだけ早くオーナーシップを放棄するように勧めています。

この分散したオーナーシップ構造により、本書で説明した他の多くの実践が可能になります。例えば、ルートOWNERSファイルに登録されている人たちは、大規模な変更を行う際に、ローカルチームに迷惑をかけることなく、グローバルな承認者として機能することができます（第22章参照）。同様に、OWNERSファイルは一種のドキュメントのような役割を果たし、人々やツールがディレクトリツリーを歩くだけで、特定のコードの責任者を簡単に見つけることができます。また、新しいプロジェクトが作成されても、新しいオーナーシップ権限を登録する中央機関はありません：新しいOWNERSファイルで十分です。

このオーナーシップの仕組みはシンプルでありながら強力で、過去20年間に渡ってうまく拡張されてきました。これは、Googleが、何万人ものエンジニアが1つのリポジトリで何十億行ものコードを効率的に処理できるようにするための方法の1つです。

----

## コードレビューのメリット

業界全体では、コードレビューは普遍的な慣行とは程遠いものの、それ自体は議論の対象にはなっていません。多くの企業やオープンソースプロジェクトでは、何らかの形でコードレビューが行われており、ほとんどの企業では、コードベースに新しいコードを導入する際の健全性チェックとして、このプロセスを重要視しています。ソフトウェアエンジニアは、個人的にはコードレビューがすべてのケースに適用されるとは思っていなくても、コードレビューのより明白なメリットを理解しています。しかし、グーグルでは、このプロセスが他の多くの企業よりも徹底しており、広く普及している。

多くのソフトウェア企業がそうであるように、Googleの文化は、エンジニアに仕事の進め方の自由度を与えることを基本としています。新しい技術に迅速に対応しなければならないダイナミックな企業にとって、厳格なプロセスはうまく機能しない傾向があり、官僚的なルールはクリエイティブなプロフェッショナルとうまく機能しない傾向があるという認識があります。しかし、コードレビューは、グーグルのすべてのソフトウェアエンジニアが参加しなければならない数少ない包括的なプロセスの1つであり、義務付けられている。グーグルでは、どんなに小さなコードベースへの変更でも、ほぼすべてのコードレビューを要求している(*4)。この義務化は、コードベースへの新しいコードの導入を遅らせ、コード変更の生産開始までの時間に影響を与えることから、エンジニアリングの速度にコストと影響を与えます。(これらはいずれも、厳格なコードレビュープロセスに対するソフトウェアエンジニアの一般的な不満です。) では、なぜこのようなプロセスを必要とするのでしょうか。また、このプロセスが長期的に有益であると考える理由は何でしょうか。

適切に設計されたコードレビュープロセスと、コードレビューに真剣に取り組む文化は、以下のようなメリットをもたらします。

- コードの正しさをチェック
- コードの変更が他のエンジニアに理解できるかどうかの確認
- コードベース全体の一貫性を確保する
- チームのオーナーシップを心理的に促進する
- 知識の共有が可能になる
- コードレビュー自体の履歴が残る
  
これらの利点の多くは、ソフトウェア組織にとって長期的に重要であり、作者だけでなくレビュアーにとっても有益なものが多い。以下のセクションでは、それぞれの項目についてより具体的に説明します。

### Code Correctness

An obvious benefit of code review is that it allows a reviewer to check the “correctness” of the code change. Having another set of eyes look over a change helps ensure that the change does what was intended. Reviewers typically look for whether a change has proper testing, is properly designed, and functions correctly and efficiently. In many cases, checking code correctness is checking whether the particular change can introduce bugs into the codebase.
Many reports point to the efficacy of code review in the prevention of future bugs in software. A study at IBM found that discovering defects earlier in a process, unsurprisingly, led to less time required to fix them later on.(*5) The investment in the time for code review saved time otherwise spent in testing, debugging, and performing regressions, provided that the code review process itself was streamlined to keep it lightweight. This latter point is important; code review processes that are heavyweight, or that don’t scale properly, become unsustainable.(*6) We will get into some best practices for keeping the process lightweight later in this chapter.
To prevent the evaluation of correctness from becoming more subjective than objective, authors are generally given deference to their particular approach, whether it be in the design or the function of the introduced change. A reviewer shouldn’t propose alternatives because of personal opinion. Reviewers can propose alternatives, but only if they improve comprehension (by being less complex, for example) or functionality (by being more efficient, for example). In general, engineers are encouraged to approve changes that improve the codebase rather than wait for consensus on a more “perfect” solution. This focus tends to speed up code reviews.
As tooling becomes stronger, many correctness checks are performed automatically through techniques such as static analysis and automated testing (though tooling might never completely obviate the value for human-based inspection of code --- see Chapter 20 for more information). Though this tooling has its limits, it has definitely lessoned the need to rely on human-based code reviews for checking code correctness.
That said, checking for defects during the initial code review process is still an integral part of a general “shift left” strategy, aiming to discover and resolve issues at the earliest possible time so that they don’t require escalated costs and resources farther down in the development cycle. A code review is neither a panacea nor the only check for such correctness, but it is an element of a defense-in-depth against such problems in software. As a result, code review does not need to be “perfect” to achieve results.
Surprisingly enough, checking for code correctness is not the primary benefit Google accrues from the process of code review. Checking for code correctness generally ensures that a change works, but more importance is attached to ensuring that a code change is understandable and makes sense over time and as the codebase itself scales. To evaluate those aspects, we need to look at factors other than whether the code is simply logically “correct” or understood.


### Comprehension of Code

A code review typically is the first opportunity for someone other than the author to inspect a change. This perspective allows a reviewer to do something that even the best engineer cannot do: provide feedback unbiased by an author’s perspective. A code review is often the first test of whether a given change is understandable to a broader audience. This perspective is vitally important because code will be read many more times than it is written, and understanding and comprehension are critically important.
It is often useful to find a reviewer who has a different perspective from the author, especially a reviewer who might need, as part of their job, to maintain or use the code being proposed within the change. Unlike the deference reviewers should give authors regarding design decisions, it’s often useful to treat questions on code comprehension using the maxim “the customer is always right.” In some respect, any questions you get now will be multiplied many-fold over time, so view each question on code comprehension as valid. This doesn’t mean that you need to change your approach or your logic in response to the criticism, but it does mean that you might need to explain it more clearly.
Together, the code correctness and code comprehension checks are the main criteria for an LGTM from another engineer, which is one of the approval bits needed for an approved code review. When an engineer marks a code review as LGTM, they are saying that the code does what it says and that it is understandable. Google, however, also requires that the code be sustainably maintained, so we have additional approvals needed for code in certain cases.

### Code Consistency

At scale, code that you write will be depended on, and eventually maintained, by someone else. Many others will need to read your code and understand what you did. Others (including automated tools) might need to refactor your code long after you’ve moved to another project. Code, therefore, needs to conform to some standards of consistency so that it can be understood and maintained. Code should also avoid being overly complex; simpler code is easier for others to understand and maintain as well. Reviewers can assess how well this code lives up to the standards of the codebase itself during code review. A code review, therefore, should act to ensure code health.
It is for maintainability that the LGTM state of a code review (indicating code correctness and comprehension) is separated from that of readability approval. Readability approvals can be granted only by individuals who have successfully gone through the process of code readability training in a particular programming language. For example, Java code requires approval from an engineer who has “Java readability.”
A readability approver is tasked with reviewing code to ensure that it follows agreed- on best practices for that particular programming language, is consistent with the codebase for that language within Google’s code repository, and avoids being overly complex. Code that is consistent and simple is easier to understand and easier for tools to update when it comes time for refactoring, making it more resilient. If a particular pattern is always done in one fashion in the codebase, it’s easier to write a tool to refactor it.
Additionally, code might be written only once, but it will be read dozens, hundreds, or even thousands of times. Having code that is consistent across the codebase improves comprehension for all of engineering, and this consistency even affects the process of code review itself. Consistency sometimes clashes with functionality; a readability reviewer may prefer a less complex change that may not be functionally “better” but is easier to understand.
With a more consistent codebase, it is easier for engineers to step in and review code on someone else’s projects. Engineers might occasionally need to look outside the team for help in a code review. Being able to reach out and ask experts to review the code, knowing they can expect the code itself to be consistent, allows those engineers to focus more properly on code correctness and comprehension.

### Psychological and Cultural Benefits

Code review also has important cultural benefits: it reinforces to software engineers that code is not “theirs” but in fact part of a collective enterprise. Such psychological benefits can be subtle but are still important. Without code review, most engineers would naturally gravitate toward personal style and their own approach to software design. The code review process forces an author to not only let others have input, but to compromise for the sake of the greater good.
It is human nature to be proud of one’s craft and to be reluctant to open up one’s code to criticism by others. It is also natural to be somewhat reticent to welcome critical feedback about code that one writes. The code review process provides a mechanism to mitigate what might otherwise be an emotionally charged interaction. Code review, when it works best, provides not only a challenge to an engineer’s assumptions, but also does so in a prescribed, neutral manner, acting to temper any criticism which might otherwise be directed to the author if provided in an unsolicited manner. After all, the process requires critical review (we in fact call our code review tool “Critique”), so you can’t fault a reviewer for doing their job and being critical. The code review process itself, therefore, can act as the “bad cop,” whereas the reviewer can still be seen as the “good cop.”
Of course, not all, or even most, engineers need such psychological devices. But buffering such criticism through the process of code review often provides a much gentler introduction for most engineers to the expectations of the team. Many engineers joining Google, or a new team, are intimidated by code review. It is easy to think that any form of critical review reflects negatively on a person’s job performance. But over time, almost all engineers come to expect to be challenged when sending a code review and come to value the advice and questions offered through this process (though, admittedly, this sometimes takes a while).
Another psychological benefit of code review is validation. Even the most capable engineers can suffer from imposter syndrome and be too self-critical. A process like code review acts as validation and recognition for one’s work. Often, the process involves an exchange of ideas and knowledge sharing (covered in the next section), which benefits both the reviewer and the reviewee. As an engineer grows in their domain knowledge, it’s sometimes difficult for them to get positive feedback on how they improve. The process of code review can provide that mechanism.
The process of initiating a code review also forces all authors to take a little extra care with their changes. Many software engineers are not perfectionists; most will admit that code that “gets the job done” is better than code that is perfect but that takes too long to develop. Without code review, it’s natural that many of us would cut corners, even with the full intention of correcting such defects later. “Sure, I don’t have all of the unit tests done, but I can do that later.” A code review forces an engineer to resolve those issues before sending the change. Collecting the components of a change for code review psychologically forces an engineer to make sure that all of their ducks are in a row. The little moment of reflection that comes before sending off your change is the perfect time to read through your change and make sure you’re not missing anything.

### Knowledge Sharing

One of the most important, but underrated, benefits of code review is in knowledge sharing. Most authors pick reviewers who are experts, or at least knowledgeable, in the area under review. The review process allows reviewers to impart domain knowledge to the author, allowing the reviewer(s) to offer suggestions, new techniques, or advisory information to the author. (Reviewers can even mark some comments “FYI,” requiring no action; they are simply added as an aid to the author.) Authors who become particularly proficient in an area of the codebase will often become owners as well, who then in turn will be able to act as reviewers for other engineers.
Part of the code review process of feedback and confirmation involves asking questions on why the change is done in a particular way. This exchange of information facilitates knowledge sharing. In fact, many code reviews involve an exchange of information both ways: the authors as well as the reviewers can learn new techniques and patterns from code review. At Google, reviewers may even directly share suggested edits with an author within the code review tool itself.
An engineer may not read every email sent to them, but they tend to respond to every code review sent. This knowledge sharing can occur across time zones and projects as well, using Google’s scale to disseminate information quickly to engineers in all corners of the codebase. Code review is a perfect time for knowledge transfer: it is timely and actionable. (Many engineers at Google “meet” other engineers first through their code reviews!)
Given the amount of time Google engineers spend in code review, the knowledge accrued is quite significant. A Google engineer’s primary task is still programming, of course, but a large chunk of their time is still spent in code review. The code review process provides one of the primary ways that software engineers interact with one another and exchange information about coding techniques. Often, new patterns are advertised within the context of code review, sometimes through refactorings such as large-scale changes.
Moreover, because each change becomes part of the codebase, code review acts as a historical record. Any engineer can inspect the Google codebase and determine when some particular pattern was introduced and bring up the actual code review in question. Often, that archeology provides insights to many more engineers than the original author and reviewer(s).

## Code Review Best Practices

Code review can, admittedly, introduce friction and delay to an organization. Most of these issues are not problems with code review per se, but with their chosen implementation of code review. Keeping the code review process running smoothly at Google is no different, and it requires a number of best practices to ensure that code review is worth the effort put into the process. Most of those practices emphasize keeping the process nimble and quick so that code review can scale properly.

### Be Polite and Professional

As pointed out in the Culture section of this book, Google heavily fosters a culture of trust and respect. This filters down into our perspective on code review. A software engineer needs an LGTM from only one other engineer to satisfy our requirement on code comprehension, for example. Many engineers make comments and LGTM a change with the understanding that the change can be submitted after those changes are made, without any additional rounds of review. That said, code reviews can introduce anxiety and stress to even the most capable engineers. It is critically important to keep all feedback and criticism firmly in the professional realm.
In general, reviewers should defer to authors on particular approaches and only point out alternatives if the author’s approach is deficient. If an author can demonstrate that several approaches are equally valid, the reviewer should accept the preference of the author. Even in those cases, if defects are found in an approach, consider the review a learning opportunity (for both sides!). All comments should remain strictly professional. Reviewers should be careful about jumping to conclusions based on a code author’s particular approach. It’s better to ask questions on why something was done the way it was before assuming that approach is wrong.
Reviewers should be prompt with their feedback. At Google, we expect feedback from a code review within 24 (working) hours. If a reviewer is unable to complete a review in that time, it’s good practice (and expected) to respond that they’ve at least seen the change and will get to the review as soon as possible. Reviewers should avoid responding to the code review in piecemeal fashion. Few things annoy an author more than getting feedback from a review, addressing it, and then continuing to get unrelated further feedback in the review process.
As much as we expect professionalism on the part of the reviewer, we expect professionalism on the part of the author as well. Remember that you are not your code, and that this change you propose is not “yours” but the team’s. After you check that piece of code into the codebase, it is no longer yours in any case. Be receptive to questions on your approach, and be prepared to explain why you did things in certain ways. Remember that part of the responsibility of an author is to make sure this code is understandable and maintainable for the future.
It’s important to treat each reviewer comment within a code review as a TODO item; a particular comment might not need to be accepted without question, but it should at least be addressed. If you disagree with a reviewer’s comment, let them know, and let them know why and don’t mark a comment as resolved until each side has had a chance to offer alternatives. One common way to keep such debates civil if an author doesn’t agree with a reviewer is to offer an alternative and ask the reviewer to PTAL (please take another look). Remember that code review is a learning opportunity for both the reviewer and the author. That insight often helps to mitigate any chances for disagreement.
By the same token, if you are an owner of code and responding to a code review within your codebase, be amenable to changes from an outside author. As long as the change is an improvement to the codebase, you should still give deference to the author that the change indicates something that could and should be improved.

### Write Small Changes

Probably the most important practice to keep the code review process nimble is to keep changes small. A code review should ideally be easy to digest and focus on a single issue, both for the reviewer and the author. Google’s code review process discourages massive changes consisting of fully formed projects, and reviewers can rightfully reject such changes as being too large for a single review. Smaller changes also prevent engineers from wasting time waiting for reviews on larger changes, reducing downtime. These small changes have benefits further down in the software development process as well. It is far easier to determine the source of a bug within a change if that particular change is small enough to narrow it down.
That said, it’s important to acknowledge that a code review process that relies on small changes is sometimes difficult to reconcile with the introduction of major new features. A set of small, incremental code changes can be easier to digest individually, but more difficult to comprehend within a larger scheme. Some engineers at Google admittedly are not fans of the preference given to small changes. Techniques exist for managing such code changes (development on integration branches, management of changes using a diff base different than HEAD), but those techniques inevitably involve more overhead. Consider the optimization for small changes just that: an optimization, and allow your process to accommodate the occasional larger change.
“Small” changes should generally be limited to about 200 lines of code. A small change should be easy on a reviewer and, almost as important, not be so cumbersome that additional changes are delayed waiting for an extensive review. Most changes at Google are expected to be reviewed within about a day.(*7) (This doesn’t necessarily mean that the review is over within a day, but that initial feedback is provided within a day.) About 35% of the changes at Google are to a single file.(*8) Being easy on a reviewer allows for quicker changes to the codebase and benefits the author as well. The author wants a quick review; waiting on an extensive review for a week or so would likely impact follow-on changes. A small initial review also can prevent much more expensive wasted effort on an incorrect approach further down the line.
Because code reviews are typically small, it’s common for almost all code reviews at Google to be reviewed by one and only one person. Were that not the case --- if a team were expected to weigh in on all changes to a common codebase --- there is no way the process itself would scale. By keeping the code reviews small, we enable this optimization. It’s not uncommon for multiple people to comment on any given change ---  most code reviews are sent to a team member, but also CC’d to appropriate teams ---  but the primary reviewer is still the one whose LGTM is desired, and only one LGTM is necessary for any given change. Any other comments, though important, are still optional.
Keeping changes small also allows the “approval” reviewers to more quickly approve any given changes. They can quickly inspect whether the primary code reviewer did due diligence and focus purely on whether this change augments the codebase while maintaining code health over time.

### Write Good Change Descriptions

A change description should indicate its type of change on the first line, as a summary. The first line is prime real estate and is used to provide summaries within the code review tool itself, to act as the subject line in any associated emails, and to become the visible line Google engineers see in a history summary within Code Search (see Chapter 17), so that first line is important.
Although the first line should be a summary of the entire change, the description should still go into detail on what is being changed and why. A description of “Bug fix” is not helpful to a reviewer or a future code archeologist. If several related modifications were made in the change, enumerate them within a list (while still keeping it on message and small). The description is the historical record for this change, and tools such as Code Search allow you to find who wrote what line in any particular change in the codebase. Drilling down into the original change is often useful when trying to fix a bug.

Descriptions aren’t the only opportunity for adding documentation to a change. When writing a public API, you generally don’t want to leak implementation details, but by all means do so within the actual implementation, where you should comment liberally. If a reviewer does not understand why you did something, even if it is correct, it is a good indicator that such code needs better structure or better comments (or both). If, during the code review process, a new decision is reached, update the change description, or add appropriate comments within the implementation. A code review is not just something that you do in the present time; it is something you do to record what you did for posterity.

### Keep Reviewers to a Minimum

Most code reviews at Google are reviewed by precisely one reviewer.(*9) Because the code review process allows the bits on code correctness, owner acceptance, and language readability to be handled by one individual, the code review process scales quite well across an organization the size of Google.
There is a tendency within the industry, and within individuals, to try to get additional input (and unanimous consent) from a cross-section of engineers. After all, each additional reviewer can add their own particular insight to the code review in question. But we’ve found that this leads to diminishing returns; the most important LGTM is the first one, and subsequent ones don’t add as much as you might think to the equation. The cost of additional reviewers quickly outweighs their value.
The code review process is optimized around the trust we place in our engineers to do the right thing. In certain cases, it can be useful to get a particular change reviewed by multiple people, but even in those cases, those reviewers should focus on different aspects of the same change.

### Automate Where Possible

Code review is a human process, and that human input is important, but if there are components of the code process that can be automated, try to do so. Opportunities to automate mechanical human tasks should be explored; investments in proper tooling reap dividends. At Google, our code review tooling allows authors to automatically submit and automatically sync changes to the source control system upon approval (usually used for fairly simple changes).
One of the most important technological improvements regarding automation over the past few years is automatic static analysis of a given code change (see Chapter 20). Rather than require authors to run tests, linters, or formatters, the current Google code review tooling provides most of that utility automatically through what is known as presubmits. A presubmit process is run when a change is initially sent to a reviewer. Before that change is sent, the presubmit process can detect a variety of problems with the existing change, reject the current change (and prevent sending an awkward email to a reviewer), and ask the original author to fix the change first. Such automation not only helps out with the code review process itself, it also allows the reviewers to focus on more important concerns than formatting.


## Types of Code Reviews

All code reviews are not alike! Different types of code review require different levels of focus on the various aspects of the review process. Code changes at Google generally fall into one of the following buckets (though there is sometimes overlap):

- Greenfield reviews and new feature development
- Behavioral changes, improvements, and optimizations
- Bug fixes and rollbacks
- Refactorings and large-scale changes

### Greenfield Code Reviews

The least common type of code review is that of entirely new code, a so-called greenfield review. A greenfield review is the most important time to evaluate whether the code will stand the test of time: that it will be easier to maintain as time and scale change the underlying assumptions of the code. Of course, the introduction of entirely new code should not come as a surprise. As mentioned earlier in this chapter, code is a liability, so the introduction of entirely new code should generally solve a real problem rather than simply provide yet another alternative. At Google, we generally require new code and/or projects to undergo an extensive design review, apart from a code review. A code review is not the time to debate design decisions already made in the past (and by the same token, a code review is not the time to introduce the design of a proposed API).
To ensure that code is sustainable, a greenfield review should ensure that an API matches an agreed design (which may require reviewing a design document) and is tested fully, with all API endpoints having some form of unit test, and that those tests fail when the code’s assumptions change. (See Chapter 11). The code should also have proper owners (one of the first reviews in a new project is often of a single OWNERS file for the new directory), be sufficiently commented, and provide supplemental documentation, if needed. A greenfield review might also necessitate the introduction of a project into the continuous integration system. (See Chapter 23).

### Behavioral Changes, Improvements, and Optimizations

Most changes at Google generally fall into the broad category of modifications to existing code within the codebase. These additions may include modifications to API endpoints, improvements to existing implementations, or optimizations for other factors such as performance. Such changes are the bread and butter of most software engineers.
In each of these cases, the guidelines that apply to a greenfield review also apply: is this change necessary, and does this change improve the codebase? Some of the best modifications to a codebase are actually deletions! Getting rid of dead or obsolete code is one of the best ways to improve the overall code health of a codebase.
Any behavioral modifications should necessarily include revisions to appropriate tests for any new API behavior. Augmentations to the implementation should be tested in a Continuous Integration (CI) system to ensure that those modifications don’t break any underlying assumptions of the existing tests. As well, optimizations should of course ensure that they don’t affect those tests and might need to include performance benchmarks for the reviewers to consult. Some optimizations might also require benchmark tests.

### Bug Fixes and Rollbacks

Inevitably, you will need to submit a change for a bug fix to your codebase. When doing so, avoid the temptation to address other issues. Not only does this risk increasing the size of the code review, it also makes it more difficult to perform regression testing or for others to roll back your change. A bug fix should focus solely on fixing the indicated bug and (usually) updating associated tests to catch the error that occurred in the first place.
Addressing the bug with a revised test is often necessary. The bug surfaced because existing tests were either inadequate, or the code had certain assumptions that were not met. As a reviewer of a bug fix, it is important to ask for updates to unit tests if applicable.
Sometimes, a code change in a codebase as large as Google’s causes some dependency to fail that was either not detected properly by tests or that unearths an untested part of the codebase. In those cases, Google allows such changes to be “rolled back,” usually by the affected downstream customers. A rollback consists of a change that essentially undoes the previous change. Such rollbacks can be created in seconds because they just revert the previous change to a known state, but they still require a code review.
It also becomes critically important that any change that could cause a potential rollback (and that includes all changes!) be as small and atomic as possible so that a rollback, if needed, does not cause further breakages on other dependencies that can be difficult to untangle. At Google, we’ve seen developers start to depend on new code very quickly after it is submitted, and rollbacks sometimes break these developers as a result. Small changes help to mitigate these concerns, both because of their atomicity, and because reviews of small changes tend to be done quickly.

### Refactorings and Large-Scale Changes

Many changes at Google are automatically generated: the author of the change isn’t a person, but a machine. We discuss more about the large-scale change (LSC) process in Chapter 22, but even machine-generated changes require review. In cases where the change is considered low risk, it is reviewed by designated reviewers who have approval privileges for our entire codebase. But for cases in which the change might be risky or otherwise requires local domain expertise, individual engineers might be asked to review automatically generated changes as part of their normal workflow.
At first look, a review for an automatically generated change should be handled the same as any other code review: the reviewer should check for correctness and applicability of the change. However, we encourage reviewers to limit comments in the associated change and only flag concerns that are specific to their code, not the underlying tool or LSC generating the changes. While the specific change might be machine generated, the overall process generating these changes has already been reviewed, and individual teams cannot hold a veto over the process, or it would not be possible to scale such changes across the organization. If there is a concern about the underlying tool or process, reviewers can escalate out of band to an LSC oversight group for more information.
We also encourage reviewers of automatic changes to avoid expanding their scope. When reviewing a new feature or a change written by a teammate, it is often reasonable to ask the author to address related concerns within the same change, so long as the request still follows the earlier advice to keep the change small. This does not apply to automatically generated changes because the human running the tool might have hundreds of changes in flight, and even a small percentage of changes with review comments or unrelated questions limits the scale at which the human can effectively operate the tool.

## Conclusion

Code review is one of the most important and critical processes at Google. Code review acts as the glue connecting engineers with one another, and the code review process is the primary developer workflow upon which almost all other processes must hang, from testing to static analysis to CI. A code review process must scale appropriately, and for that reason, best practices, including small changes and rapid feedback and iteration, are important to maintain developer satisfaction and appropriate production velocity.

## TL;DRs

- Code review has many benefits, including ensuring code correctness, comprehension, and consistency across a codebase.
- Always check your assumptions through someone else; optimize for the reader.
- Provide the opportunity for critical feedback while remaining professional.
- Code review is important for knowledge sharing throughout an organization.
- Automation is critical for scaling the process.
- The code review itself provides a historical record.








-----

1 We also use Gerrit to review Git code, primarily for our open source projects. However, Critique is the primary tool of a typical software engineer at Google.
2 Steve McConnell, Code Complete (Redmond: Microsoft Press, 2004).
3 At Google, “readability” does not refer simply to comprehension, but to the set of styles and best practices that allow code to be maintainable to other engineers. See Chapter 3.
4 Some changes to documentation and configurations might not require a code review, but it is often still preferable to obtain such a review.
5 “Advances in Software Inspection,” IEEE Transactions on Software Engineering, SE-12(7): 744–751, July 1986. Granted, this study took place before robust tooling and automated testing had become so important in the software development process, but the results still seem relevant in the modern software age.
6 Rigby, Peter C. and Christian Bird. 2013. “Convergent software peer review practices.” ESEC/FSE 2013: Proceedings of the 2013 9th Joint Meeting on Foundations of Software Engineering, August 2013: 202-212. https:// dl.acm.org/doi/10.1145/2491411.2491444.
7 Caitlin Sadowski, Emma Söderberg, Luke Church, Michal Sipko, and Alberto Bacchelli, “Modern code review: a case study at Google.”
8 Ibid.
9 Ibid.


