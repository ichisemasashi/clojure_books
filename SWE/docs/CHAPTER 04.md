# 公平性のためのエンジニアリング

Written by Demma Rodriguez
Edited by Riona MacNamara


これまでの章では、その時々の問題を解決するためにコードを作成するプログラミングと、数十年、あるいは一生をかけて取り組まなければならないダイナミックで曖昧な問題に対して、コード、ツール、ポリシー、プロセスなどを幅広く適用するソフトウェアエンジニアリングとの対比について説明してきました。本章では、広範なユーザーを対象とした製品を設計する際に、エンジニアが果たすべき独自の責任について説明します。さらに、多様性を受け入れることで、組織がどのようにして誰にでも役立つシステムを設計し、ユーザーに対する被害を永続させないようにするかを評価します。

ソフトウェアエンジニアリングの分野は新しいものですが、その分野が社会的弱者や多様な社会に与える影響を理解することは、まだまだ新しいと言えます。私たちは、すべての答えを知っているからこの章を書いたわけではありません。そうではないのです。実際、すべてのユーザーに力を与え、尊重するような製品を作る方法を理解することは、Googleがまだ学んでいるところです。私たちは、最も弱い立場にあるユーザーを守るために多くの失敗をしてきました。だからこそ、より公平な製品への道は、自らの失敗を評価し、成長を促すことから始まると考え、この章を書いています。

また、世界に影響を与える開発決定を下す人々と、その決定を受け入れて生きていかなければならない人々との間の力関係がますます不均衡になってきているため、この章を書いています。これまでに学んだことを次世代のソフトウェアエンジニアと共有し、振り返ることは重要です。また、次世代のエンジニアに影響を与え、現在の私たちよりも優れた人材を育成することは、さらに重要なことです。

この本を手に取るだけでも、あなたは優れたエンジニアになりたいと思っているはずです。問題を解決したいと思っている。到達するのが最も困難な人々を含む、より多くの人々にポジティブな結果をもたらす製品を作りたいと思っているはずです。そのためには、自分が作ったツールがどのように活用され、人類の軌道を変えていくのか、できれば良い方向に持っていきたいと考えているはずです。

## バイアスはデフォルト

エンジニアが、国籍、民族、人種、性別、年齢、社会経済的地位、能力、信念体系などが異なるユーザーに焦点を当てていない場合、どんなに優秀なスタッフでも、不注意でユーザーの期待を裏切ってしまうことがあります。このような失敗は意図的ではないことが多い。すべての人は特定の偏見を持っており、社会科学者たちは過去数十年の間に、ほとんどの人が無意識の偏見を持ち、既存の固定観念を強要し、広めていることを認識している。無意識の偏見は陰湿で、意図的な排除行為よりも軽減するのが難しい場合があります。正しいことをしたいと思っていても、自分の偏見に気づかないことがあります。同様に、組織もそのような偏見が存在することを認識し、従業員、製品開発、ユーザーへの働きかけの中で、その偏見に対処しなければなりません。

Googleは、過去数年間に発売した製品において、偏見のために、ユーザーを公平に代表することができなかったことがあります。多くのユーザーは、グーグルのエンジニアの多くが男性で、白人やアジア人が多く、グーグル製品を使用するすべてのコミュニティを代表していないことが、このようなケースに対するグーグルの認識不足の原因だと考えています。このようなユーザーが当社の従業員に含まれていない(*1)ということは、当社製品の使用が社会的弱者にどのような影響を与えるかを理解するのに必要な多様性がないことを意味しています。

---

### ケーススタディ 人種の取り込みに失敗したGoogle

2015年、ソフトウェアエンジニアのジャッキー・アルシネは、Googleフォトの画像認識アルゴリズムが、黒人の友人を "ゴリラ "と分類していることを指摘しました(*2)。グーグルはこうしたミスへの対応が遅く、不完全だった。

このような重大な失敗の原因は何か。いくつか理由がある。

- 画像認識アルゴリズムは、「適切な」（多くの場合「完全な」という意味）データセットが提供されることに依存している。Googleの画像認識アルゴリズムに投入された写真データは、明らかに不完全なものでした。つまり、そのデータは人口を代表するものではなかったのだ。
- グーグル社（およびハイテク産業全般）には、黒人の代表者があまりいなかった（現在もいない）(*3)ため、このようなアルゴリズムの設計やデータセットの収集において、主観的な判断に影響を与えている。組織自体の無意識のバイアスにより、より代表的な製品がテーブルの上に残されてしまったのだろう。
- グーグルの画像認識のターゲット市場には、このような代表性の低いグループが十分に含まれていなかった。Googleのテストではこれらのミスを発見できず、結果的にユーザーがミスを犯してしまい、Googleに恥をかかせ、ユーザーにも損害を与えてしまいました。

2018年になっても、Googleは根本的な問題に適切に対処していませんでした(*4)。

-----

この例では、製品の設計と実行が不適切で、すべての人種を適切に考慮することができず、その結果、ユーザーを失望させ、Googleに悪評をもたらしました。オートコンプリートは、攻撃的または人種差別的な結果を返す可能性があります。Googleの広告システムは、人種差別的または攻撃的な広告を表示するように操作される可能性があります。YouTubeでは、ヘイトスピーチが技術的に禁止されているにもかかわらず、キャッチできない場合があります。

これらのケースでは、テクノロジー自体にはあまり責任はありません。例えば、オートコンプリートは、ユーザーをターゲットにしたり、差別したりするために設計されたものではありません。しかし、ヘイトスピーチとされる差別的な言葉を除外するほどの弾力性のある設計でもありませんでした。その結果、アルゴリズムはユーザーに害を与える結果を返してしまいました。また、Google自体の害も明らかなはずです。ユーザーの信頼と会社へのエンゲージメントの低下です。例えば、黒人、ラテン系、ユダヤ系の応募者は、プラットフォームとしての Google、あるいは包括的な環境そのものへの信頼を失い、その結果、採用における代表性を向上させるという Google の目標が損なわれる可能性があります。

なぜこのようなことが起こるのでしょうか？結局のところ、グーグルは非の打ちどころのない学歴や職歴を持つ技術者を採用しています。最高のコードを書き、自分の仕事をテストする優れたプログラマーです。"Build for everyone "はGoogleのブランドステートメントですが、実際のところ、私たちがそうだと主張できるようになるまでには、まだ長い道のりがあるのです。これらの問題に対処する一つの方法は、ソフトウェアエンジニアリングの組織自体が、製品を作る対象となる人々と同じように見えるようにすることです。

## ダイバーシティの必要性を理解する

グーグルでは、優れたエンジニアであるためには、製品の設計や実装に多様な視点を取り入れることも重要であると考えています。また、他のエンジニアの採用や面接を担当するグーグル社員は、より代表的な人材の育成に貢献しなければならないと考えています。例えば、自分の会社のポジションのために他のエンジニアを面接する場合、採用時に偏った結果がどのように起こるかを知ることが重要です。被害を予測し、それを防ぐ方法を理解するには、大きな前提条件があります。すべての人のための製品を作ることができるようになるには、まず代表的な人々を理解する必要があります。エンジニアの教育訓練の幅を広げることを奨励する必要があります。

そのためには、コンピューターサイエンスの学位や実務経験があれば、優れたエンジニアになるためのスキルをすべて持っているという考え方を改めることが第一です。コンピュータサイエンスの学位は、しばしば必要な基礎となります。しかし、学位だけでは（たとえ実務経験があったとしても）エンジニアにはなれません。また、コンピューターサイエンスの学位を持っている人だけが製品をデザインしたり作ったりできるという考えを改めることも重要です。今日、[ほとんどのプログラマーはコンピュータサイエンスの学位を持っています](https://www.bls.gov/ooh/computer-and-information-technology/computer-programmers.htm)。彼らはコードを構築し、変化の理論を確立し、問題解決のための方法論を適用することに成功しています。しかし、前述の例が示すように、このアプローチは包括的で公平なエンジニアリングには不十分です。

エンジニアは、自分が影響を与えようとしている完全なエコシステムの枠組みの中で、すべての仕事に集中することから始めるべきです。最低限、ユーザーの人口統計を理解する必要があります。エンジニアは、自分とは異なる人々、特に製品を使って危害を加えようとする可能性のある人々に焦点を当てるべきです。考慮すべき最も困難なユーザーは、テクノロジーにアクセスするプロセスや環境によって権利を奪われている人々です。この問題に対処するためには、エンジニアリングチームは、既存および将来のユーザーを代表する必要があります。エンジニアチームに多様な人材がいない場合は、個々のエンジニアがすべてのユーザーのために構築する方法を学ぶ必要があります。

## 多文化共生のための能力開発

優れたエンジニアの特徴の一つは、製品が人間の様々なグループにどのようなメリットとデメリットをもたらすかを理解する能力を持っていることです。エンジニアには技術的な適性が求められますが、それだけでなく、何かを作るべき時とそうでない時を見極める力も必要です。識別力には、不利な結果をもたらす機能や製品を識別し、拒否する能力を身につけることも含まれます。高性能なエンジニアになるためには、膨大な量の個人主義が必要とされるため、これは高くて難しい目標です。しかし、成功するためには、自分たちのコミュニティだけでなく、次の10億人のユーザーや、製品によって権利を奪われたり、取り残されたりする可能性のある既存のユーザーにまで目を向けなければなりません。

将来的には、何十億人もの人々が日常的に使用するツールを構築することになるかもしれません。人命の価値について人々が考える方法に影響を与えるツール、人間の活動を監視するツール、子供や恋人の画像などのセンシティブなデータをキャプチャして持続させるツールなどです。エンジニアであるあなたは、自分が思っている以上に、文字通り社会を変える力を持っているかもしれません。卓越したエンジニアになるためには、害を与えずに力を発揮するための生来の責任を理解することが重要です。そのためにはまず、社会的・教育的要因による自分の「偏り」を認識することが大切です。認識した後は、忘れがちなユースケースや、自分が作った製品によって利益を得たり損害を被ったりするユーザーのことを考えられるようになります。

この業界は、人工知能（AI）や機械学習の新しいユースケースを構築しながら、かつてないほどのスピードで前進を続けています。競争力を維持するために、私たちは規模を拡大し、才能豊かなエンジニアリングとテクノロジーの人材を効率的に育成しています。しかし、今日、テクノロジーの未来をデザインする能力を持つ人と持たない人がいるという事実を、一歩立ち止まって考える必要があります。私たちが構築するソフトウェアシステムによって、全国民が繁栄を共有し、テクノロジーを平等に利用できる可能性がなくなるのかどうかを理解する必要があります。

歴史的に見ても、市場を支配して収益を上げるための戦略的目標を達成することと、その目標に向けての勢いが鈍る可能性のある目標を達成することのどちらかを選択しなければならない企業は、スピードと株主価値を選択してきました。この傾向は、多くの企業が個人のパフォーマンスとエクセレンスを重視しているにもかかわらず、すべての分野で製品の公平性に関する説明責任を効果的に果たせていないという事実によって、さらに悪化しています。代表性のないユーザーに焦点を当てることは、公平性を促進する明確な機会となります。テクノロジー分野で競争力を維持するためには、グローバルな公平性を確保するための技術を身につける必要があります。

今日、私たちは、企業が道を歩いている人をスキャンし、撮影し、識別する技術を設計することを心配しています。私たちは、プライバシーや、政府が現在および将来的にこれらの情報をどのように使用するかについて心配しています。しかし、ほとんどの技術者は、顔認識における人種的差異の影響を理解したり、AIを適用することで有害で不正確な結果をもたらすことを理解したりするために必要な、社会的弱者の視点を持ち合わせていません。

現在、AIを使った顔認識ソフトウェアは、有色人種や少数民族の人々に不利な状況が続いています。私たちの研究は包括的なものではなく、さまざまな肌色の人を十分に含んでいません。トレーニングデータとソフトウェアを作成する人の両方が、ごく一部の人々を代表しているだけでは、出力の妥当性は期待できません。このような場合には、開発を延期してでも、より完全で正確なデータと、より包括的な製品を手に入れる努力をすべきです。

しかし、人間がデータサイエンスを評価することは困難です。表現力があったとしても、トレーニングセットに偏りがあり、無効な結果が出ることがあります。2016年に行われた調査では、1億1700万人以上のアメリカ人成人が法執行機関の顔認識データベースに登録されていることが判明しました(*5)。黒人コミュニティに対する不均衡な取り締まりや、逮捕の結果の格差などから、顔認識にこのようなデータベースを利用すると、人種的に偏ったエラーレートが発生する可能性があります。ソフトウェアはどんどん開発・導入されていますが、独自のテストは行われていません。この重大な誤りを正すためには、スピードを落として、入力にできるだけ偏りがないようにする誠実さが必要です。Googleは現在、データセットが本質的に偏っていないことを確認するために、AIの文脈の中で統計トレーニングを提供しています。

したがって、業界での経験の焦点を、より包括的な、多文化、人種、ジェンダー学の教育にシフトすることは、あなたの責任だけでなく、雇用者の責任でもあります。テクノロジー企業は、従業員が継続的に専門的な開発を受け、その開発が包括的で学際的なものであることを保証しなければなりません。求められているのは、一人の個人が自分の責任で他の文化や他の人口統計について学ぶことではありません。変革のためには、私たち一人ひとりが、個人として、あるいはチームのリーダーとして、ソフトウェア開発やリーダーシップのスキルだけでなく、人類全体の多様な経験を理解する能力を身につけるための継続的な専門能力開発に投資することが必要なのです。

## ダイバーシティを実現するために

テクノロジー業界で見られる組織的な差別について、私たち全員が責任を負っていることを喜んで受け入れれば、組織的な公平性と公正さを達成することができます。私たちは、システムの失敗に対して責任があります。個人の説明責任を先送りしたり、抽象化したりすることは効果的ではなく、役割によっては無責任になることもあります。また、特定の会社やチーム内の力学を、不公平の原因となっているより大きな社会的問題に完全に帰するのも無責任です。ダイバーシティ推進派と反対派の間でよく使われるセリフに、こんなものがあります。「私たちは（制度的な差別問題を）解決するために努力していますが、説明責任を果たすことは困難です。何百年にもわたる歴史的差別にどう立ち向かうのか」。このような質問は、より哲学的、学術的な話になり、労働条件や成果を改善するための集中的な取り組みから遠ざかってしまいます。多文化対応能力を高めるためには、社会における不平等のシステムが、特にテクノロジー分野の職場にどのような影響を与えているかをより包括的に理解する必要があります。

もしあなたが、「社会的に受け入れられていないグループの人々をより多く雇用する」ことに取り組んでいるエンジニアのマネージャーであれば、世界における差別の歴史的な影響を考慮することは、学術的には有効な手段です。しかし、学術的な話を超えて、公平性と公正性を推進するための定量的で実行可能なステップに焦点を当てることが重要です。例えば、ソフトウェアエンジニアを採用するマネージャーは、候補者のバランスを取ることに責任があります。候補者のレビューのプールには、女性やその他の代表性の低いグループがいますか？採用後、どのような成長の機会を提供したか、またその機会の配分は公平か。テクノロジーリーダーやソフトウェアエンジニアリングマネージャーは、チーム内の公平性を高めるための手段を持っています。重要なのは、システム全体に大きな問題があるにもかかわらず、私たち全員がシステムの一部であることを認識することです。私たちが解決すべき問題なのです。

## 単刀直入なアプローチを拒否する

テクノロジー分野における不公平感を解消するために、単一の哲学や方法論を提示するソリューションを永続させることはできません。私たちの問題は複雑で多因子的なものです。したがって、職場での表現を前進させるための単一のアプローチは、たとえそれが私たちが尊敬する人や組織的な力を持つ人によって推進されていたとしても、断ち切らなければなりません。

テクノロジー業界では、職場における代表性の欠如は、採用パイプラインを整備するだけで解決できるという考え方が根強く残っています。確かにそれは基本的なステップですが、それは私たちが解決しなければならない緊急の問題ではありません。私たちは、昇進や定着における体系的な不公平さを認識すると同時に、より代表的な採用や、人種、性別、社会経済的・移民的地位などの線引きによる教育格差に焦点を当てる必要があります。

テクノロジー業界では、社会的に受け入れられていないグループに属する多くの人々が、日々、機会や昇進のために追い越されています。Googleの黒人従業員の離職率は [他のすべてのグループの離職率を上回っています。](https://diversity.google/annual-report/#!#_this-years-data)で、代表権目標の進捗を妨げています。変化を促し、代表性を高めるには、意欲的なエンジニアやその他のテクノロジー専門家が活躍できるようなエコシステムを構築できているかどうかを評価する必要があります。

問題を解決する方法を決定するには、問題領域全体を理解することが重要です。このことは、重要なデータの移行から代表的な人材の採用まで、あらゆることに当てはまります。例えば、女性の採用を増やしたいと考えているエンジニアのマネージャーは、パイプラインの構築だけに注力するのではなく、他の面にも目を向けてください。採用、定着、昇進のエコシステムの他の側面に注目し、それが女性にとってどのように包括的であるか、あるいはそうでないかを考えてみましょう。採用担当者が、男性だけでなく女性の優秀な候補者を見分ける能力を発揮しているかどうかを考えてみましょう。多様性のあるエンジニアリングチームを管理している場合は、心理的安全性に焦点を当て、新しいチームメンバーが歓迎されていると感じられるように、チーム内の多文化能力を高めるための投資を行います。

今日の一般的な方法論は、まず大多数のユースケースを想定して構築し、エッジケースに対応する改良や機能は後回しにするというものです。しかし、このアプローチには欠陥があります。テクノロジーへのアクセスがすでに有利なユーザーに先手を打たれてしまい、不公平感が増してしまうのです。すべてのユーザーグループへの配慮を、デザインがほぼ完成した段階で行うことは、優秀なエンジニアであることのハードルを下げることになります。そうではなく、最初からインクルーシブデザインを組み込み、開発の基準を高めて、テクノロジーへのアクセスに苦労している人々が喜んで利用できるツールを作ることで、すべてのユーザーの体験を高めることができるのです。

自分に最も似ていないユーザーのためにデザインすることは、賢明であるだけでなく、ベストプラクティスでもあります。ユーザーに不利益を与えないような製品を開発するためには、分野を問わず、すべての技術者が考慮すべき現実的かつ迅速な次のステップがあります。それは、より包括的なユーザー体験の調査から始まります。この研究は、多言語、多文化、複数の国、社会経済階級、能力、年齢層にまたがるユーザーグループを対象に行うべきである。最も困難な、あるいは代表性の低いユースケースに最初に焦点を当てる。

## 確立されたプロセスへの挑戦

より公平なシステムの構築に挑戦することは、より包括的な製品仕様を設計することに留まりません。公平なシステムを構築するということは、時に、無効な結果をもたらす確立されたプロセスに挑戦することを意味します。

最近の事例で、公平性への影響が評価されたものがあります。Google 社では、複数のエンジニアリングチームがグローバルな採用情報システムの構築に取り組んでいました。このシステムは、社外からの採用と社内での移動の両方に対応しています。エンジニアとプロダクトマネージャーは、コアユーザーであるリクルーターの要望をよく聞いていました。採用担当者は、採用担当者と応募者の無駄な時間を最小限にすることを重視しており、彼らのために規模と効率を重視したユースケースを開発チームに提示しました。効率化のために、採用担当者はエンジニアリングチームに、社内異動者が仕事に興味を示したら、すぐにパフォーマンス評価（特に低い評価）をハイライト表示して、採用担当者とリクルーターに伝える機能を入れてほしいと依頼した。

評価プロセスを迅速化し、求職者の時間を節約することは、一見すると素晴らしい目標です。しかし、衡平性の問題はどこにあるのでしょうか。次のような公平性の問題が提起されました。

- 開発アセスメントは、パフォーマンスを予測する尺度となるか？
- 管理職候補者に提示される業績評価は、個人のバイアスがかかっていないか？
- パフォーマンス評価のスコアは、組織全体で標準化されているか？

これらの質問に対する答えが「ノー」であれば、パフォーマンス評価を提示しても、不公平な、つまり無効な結果をもたらす可能性がある。

ある優秀なエンジニアが、過去のパフォーマンスが将来のパフォーマンスを予測するものであるかどうかを疑問視したため、審査チームは徹底的に検証することにしました。最終的には、業績の悪い評価を受けた候補者は、新しいチームを見つければ、その悪い評価を克服できる可能性が高いと判断されました。実際、一度も悪い評価を受けたことのない候補者と同じように、満足または模範的なパフォーマンス評価を受ける可能性が高かったのです。要するに、パフォーマンス評価は、評価された時点で、その人が与えられた役割をどのように果たしているかを示すものに過ぎない。評価は、特定の期間のパフォーマンスを測定する重要な方法ではありますが、将来のパフォーマンスを予測するものではなく、将来の役割に対する準備状況を測ったり、社内の候補者を別のチームに参加させる資格を得るために使用すべきではありません。(しかし、従業員が現在のチームで適切に配置されているか、不適切に配置されているかを評価するために使用することはできます。したがって、社内候補者を今後どのようにサポートしていくべきかを評価する機会となります）。)

この分析にはかなりの時間を要しましたが、その結果、より公平な社内移動プロセスを実現することができました。

## 価値と結果の比較

Googleには、採用への投資において優れた実績があります。前述の例が示すように、Google は公平性と包括性を向上させるために、プロセスを継続的に評価しています。さらに言えば、グーグルのコアバリューは、敬意と、多様で包括的な労働力への揺るぎないコミットメントに基づいています。しかし、年々、世界中のユーザーを反映した代表的な人材を採用するという目標を達成できていません。インクルージョンの取り組みを支援し、優れた採用と昇進を促進するためのポリシーやプログラムが導入されているにもかかわらず、公平な成果を上げるための苦労が続いています。失敗の原因は、企業の価値観や意図、投資にあるのではなく、それらのポリシーを実行レベルで適用することにあります。

古い習慣はなかなか抜けません。今日、あなたがデザインするのに慣れているユーザー、つまり、あなたがフィードバックを得るのに慣れているユーザーは、あなたが到達すべきすべてのユーザーを代表するものではないかもしれません。女性の体に合わないウェアラブル製品や、肌の色が濃い人には合わないビデオ会議ソフトなど、さまざまな製品でこのような現象が頻繁に見られます。

では、どうすればいいのでしょうか？

1. 鏡をよく見てください。Googleでは、"Build For Everyone "というブランドスローガンを掲げています。代表的な人材もいなければ、コミュニティからのフィードバックを最初に集約するエンゲージメントモデルもないのに、どうやってみんなのために作ることができるでしょうか？それはできません。実際、Googleは、人種差別的、反ユダヤ的、同性愛嫌悪的なコンテンツから最も弱い立場にあるユーザーを守ることができていないことを公にしたことがあります。
2. すべての人のために作るのではありません。みんなと一緒に作る。私たちはまだすべての人のために構築しているわけではありません。その作業は何もないところではできませんし、テクノロジーがまだ人口全体を代表していないときには確かにできません。とはいえ、私たちは荷物をまとめて家に帰ることはできません。では、どうすればみんなのために作ることができるのでしょうか？それは、ユーザーと一緒に作ることです。私たちは、全人類のユーザーを巻き込み、最も弱い立場にあるコミュニティをデザインの中心に据えることを意識する必要があります。彼らを後回しにしてはいけません。
3. 製品の使用に最も困難を伴うユーザーを想定して設計する。困難な状況にあるユーザーのために設計することで、すべての人にとってより良い製品となります。これを別の方法で考えると、「短期的な速度のために資本を交換してはいけない」ということになります。
4. 衡平性を仮定するのではなく、システム全体で衡平性を測定しましょう。意思決定者にもバイアスがかかり、不公平の原因について十分な教育を受けていない可能性があることを認識してください。また、公平性の問題の範囲を特定したり、測定したりするための専門知識がないかもしれません。このようなトレードオフを見極めるのは難しく、元に戻すのは不可能です。ダイバーシティ、エクイティ、インクルージョンの専門家である個人やチームと提携する。
5. 変化は可能である。監視、偽情報、オンラインハラスメントなど、今日私たちがテクノロジーを使って直面している問題は、本当に圧倒的なものです。過去の失敗したアプローチや、すでに持っているスキルだけでは、これらを解決することはできません。私たちは変わる必要があるのです。

## 好奇心を持って前に進む

エクイティへの道は長く、複雑です。しかし、単にツールやサービスを作るだけではなく、自分たちが開発した製品が人類にどのような影響を与えるかについて理解を深めることは可能であり、またそうすべきであると考えています。教育を受けること、チームやマネージャーに影響を与えること、そしてより包括的なユーザー調査を行うことは、前進するためのすべての方法です。変化は居心地が悪く、ハイパフォーマンスへの道は痛みを伴うこともありますが、コラボレーションと創造性によってそれは可能です。
最後に、未来の優れたエンジニアとして、私たちはまず、偏見や差別の影響を最も受けているユーザーに焦点を当てるべきです。継続的な改善に焦点を当て、失敗を自分のものにすることで、進歩を加速させることができるのです。エンジニアになるためには、継続的なプロセスが必要です。その目的は、恵まれない人々の権利をさらに奪うことなく、人類を前進させる変化を起こすことです。未来の優れたエンジニアとして、私たちは将来のシステムの失敗を防ぐことができると信じています。

## 結論

ソフトウェアの開発、そしてソフトウェア組織の開発は、チームで行うものです。ソフトウェア組織の規模が大きくなると、ユーザーベースに対応し、適切に設計しなければなりません。ユーザーベースとは、今日のコンピュータの相互接続された世界では、地域や世界中のすべての人々を指します。ソフトウェアを設計する開発チームと、彼らが生み出す製品の両方が、このような多様で包括的なユーザーの価値観を反映するよう、さらなる努力が必要です。これらのグループのエンジニアは、組織自体を強化するだけでなく、世界全体にとって真に有用なソフトウェアを設計・実装するために必要な独自の視点を提供してくれるのです。

## TL;DRs

- 偏りはデフォルトである。
- 包括的なユーザーベースのために適切に設計するには、多様性が必要です。
- 包括性は、社会的に受け入れられていないグループの採用パイプラインを改善するだけでなく、すべての人にとって真に協力的な職場環境を提供するためにも重要です。
- 製品の速度は、すべてのユーザーにとって本当に役立つ製品を提供することと比較して評価しなければなりません。一部のユーザーに悪影響を及ぼす可能性のある製品をリリースするよりも、スピードを落とした方が良いのです。








-----


1 Googleの2019年ダイバーシティレポート。
2 @jackyalcine. 2015. "Google Photos, Y'all Fucked up. 僕の友達はゴリラじゃないんだ。" Twitter, June 29, 2015.
https://twitter.com/jackyalcine/status/615329515909156865.
3 2018年から2019年にかけて、テック全体の多様性の欠如を指摘するレポートが多く見られました。有名なところでは、National Center for Women & Information Technology（女性と情報技術のためのナショナルセンター）、Diversity in Tech（テックにおける多様性）などがある。
4 Tom Simonite, "When It Comes to Gorillas, Google Photos Remains Blind," Wired, January 11, 2018.



