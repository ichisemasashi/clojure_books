# CHAPTER 12  Unit Testing


Written by Erik Kuefler

Edited by Tom Manshreck

前章では、Google がテストを分類する際の主な軸として、サイズとスコープの 2 つを紹介しました。要約すると、サイズとはテストが消費するリソースやテストに許可されていることを指し、 スコープとはテストがどの程度のコードを検証するかを指します。Googleはテストのサイズについて明確な定義をしていますが、スコープについては少し曖昧な傾向があります。私たちは、単一のクラスやメソッドなど、比較的狭い範囲のテストをユニットテストと呼んでいます。ユニットテストは通常、サイズが小さいのですが、必ずしもそうではありません。

テストの最大の目的は、バグを防ぐことに加えて、エンジニアの生産性を向上させることにあります。範囲の広いテストに比べて、ユニットテストには生産性を向上させるのに適した多くの特性があります。

- Googleのテストサイズの定義によれば、テストサイズは小さい傾向にあります。小規模なテストは高速で決定性があるため、開発者はワークフローの一部として頻繁にテストを実行し、すぐにフィードバックを得ることができます。
- また、テスト対象のコードと同時に記述することが容易であるため、エンジニアは大規模なシステムを設定したり理解したりすることなく、作業中のコードにテストを集中させることができる。
- また、素早く簡単に書けるため、高いレベルのテストカバレッジを実現しています。テストカバレッジが高いと、エンジニアは何も壊していないという確信を持って変更を加えることができます。
- それぞれのテストは概念的にシンプルで、システムの特定の部分に焦点を当てているため、失敗しても何が問題なのか簡単に理解できる傾向がある。
- また、テスト対象となるシステムの一部をどのように使用するか、そのシステムがどのように動作するように意図されているかをエンジニアに示す、ドキュメントや例としての役割も果たします。

Googleでは、経験則として、ユニットテストが80％、より広い範囲のテストが20％程度になるようにエンジニアに勧めています。このアドバイスと、ユニットテストの書きやすさ、実行速度の速さが相まって、エンジニアは大量のユニットテストを実行することになります。平均的な勤務時間中に、数千ものユニットテストを（直接または間接的に）実行することは決して珍しいことではありません。

エンジニアの生活の大部分を占めるユニットテストだからこそ、Googleはテストのメンテナンス性に力を入れています。保守性の高いテストとは、「ただ動く」テストのことです。テストを書いた後、エンジニアはテストが失敗するまでテストのことを考える必要はなく、失敗した場合には明確な原因のある本当のバグを示します。この章の大部分は、保守性という考え方と、それを実現するためのテクニックについて説明します。


## メンテナンス性の重要性

こんなシナリオを想像してみてください。メアリーは製品に簡単な新機能を追加したいと考えており、おそらく数十行のコードですぐに実装することができます。しかし、その変更をチェックしようとすると、自動テストシステムからエラーが画面いっぱいに表示されます。彼女はその日のうちに、これらのエラーをひとつひとつ確認していきます。それぞれのケースでは、変更によって実際のバグは発生していませんでしたが、コードの内部構造に関するテストの前提条件が崩れていたため、それらのテストを更新する必要がありました。そもそもテストが何をしようとしていたのかがわからず、修正のために追加したハックによって、そのテストが今後さらにわかりにくくなってしまうこともよくあります。結局、短時間で終わるはずの作業が、何時間も何日もかかってしまい、メアリーの生産性と士気は低下してしまうのだ。

ここでは、テストが意図された効果とは逆に、生産性を向上させるどころか、生産性を低下させ、テスト対象のコードの品質を有意に向上させることができませんでした。このようなシナリオはあまりにも一般的であり、Googleのエンジニアは毎日のようにこの問題に取り組んでいます。魔法の弾はありませんが、Googleの多くのエンジニアは、これらの問題を軽減するためのパターンやプラクティスの開発に取り組んでおり、会社の他のメンバーもそれに従うことを推奨しています。

メアリーが遭遇した問題は、彼女の責任ではなく、彼女が回避できたものでもありません。悪いテストは、将来のエンジニアの足を引っ張らないように、チェックインする前に修正しなければなりません。彼女が直面した問題は、大きく分けて2つあります。1つ目は、彼女が担当していたテストが脆かったこと。実際にはバグをもたらさない無害で無関係な変更に反応して壊れてしまったのです。2つ目は、テストが不明確なことです。テストが失敗した後、何が悪いのか、どうやって修正すればいいのか、そもそもテストは何をするべきなのかを判断するのが難しいのです。

## 脆いテストの防止

先ほど定義したように、脆いテストとは、実際にはバグが発生していない本番コードへの無関係な変更にもかかわらず、失敗してしまうテストのことである(*1)。 このようなテストは、エンジニアが仕事として診断し、修正しなければならない。数人のエンジニアしかいない小規模なコードベースでは、変更のたびに数個のテストを調整することは大きな問題ではないかもしれません。しかし、チームが定期的に脆弱なテストを書いている場合、テストのメンテナンスにかかる時間は必然的に大きくなります。これは、増え続けるテスト・スイートの中で、増え続ける不具合を調べなければならないからです。変更のたびにエンジニアが手作業で調整しなければならないようなテストセットを「自動化されたテストスイート」と呼ぶのは、少し大げさです。

脆弱なテストは、どのような規模のコードベースでも問題となりますが、Googleの規模では特に顕著になります。一人のエンジニアが1日の仕事の中で数千のテストを実行することもあれば、1回の大規模な変更（第22章参照）が数十万のテストを引き起こすこともあります。このような規模では、わずかな割合のテストに影響を与える偽装故障が発生すると、膨大なエンジニアリング時間が無駄になってしまいます。Google のチームによって、テストスイートの脆弱性の程度は大きく異なりますが、変更に対するテストの堅牢性を高めるためのいくつかの手法やパターンがあります。

### 変わらないテストを目指して

脆弱なテストを避けるためのパターンについて説明する前に、 ある質問に答える必要があります。古いテストの更新に費やす時間は、 より価値のある仕事に費やすことのできない時間です。したがって、理想的なテストとは不変のものです。つまり、テストを書いた後は、 テスト対象のシステムの要件が変わらない限り変更する必要がないということです。

では、実際にはどうなのでしょうか？エンジニアがプロダクションコードに加える変更の種類と、その変更にテストがどのように対応すべきかについて考える必要があります。基本的に、変更には4つの種類があります。

- 純粋なリファクタリング
  - エンジニアがシステムのインターフェイスを変更せずにシステムの内部をリファクタリングした場合、 パフォーマンスやわかりやすさなどの理由から、システムのテストを変更する必要はありません。この場合のテストの役割は、リファクタリングによってシステムの動作が変更されていないことを確認することです。リファクタリング中にテストを変更しなければならないということは、その変更がシステムの動作に影響を与えており、純粋なリファクタリングではないか、あるいはテストが適切な抽象度で書かれていないかのいずれかである。Google はこのようなリファクタリングを行う際に大規模な変更 (第 22 章で説明) に依存しているため、このケースは私たちにとって特に重要です。
- 新機能
  - エンジニアが既存のシステムに新しい機能や動作を追加する場合、 システムの既存の動作は影響を受けないようにする必要があります。エンジニアは新しい動作をカバーするために新しいテストを書く必要がありますが、 既存のテストを変更する必要はありません。リファクタリングと同様に、新しい機能を追加する際に既存のテストを変更すると、その機能の意図しない結果や不適切なテストを示唆することになります。
- バグの修正
  - バグ修正は新機能の追加とよく似ています。バグが存在するということは、 最初のテストスイートにケースが欠けていることを示唆しており、 バグ修正ではその欠けているテストケースを含めるべきです。繰り返しになりますが、バグの修正には既存のテストの更新は必要ありません。
- 動作の変更
  - システムの既存の挙動を変更する場合は、 既存のテストを更新しなければならないことが予想されます。このような変更は、他の3つのタイプに比べてかなりコストがかかる傾向があることに注意してください。システムのユーザは、そのシステムの現在の動作に依存している可能性が高く、その動作を変更する場合には、混乱や破損を避けるためにユーザとの調整が必要になります。このケースでのテストの変更は、システムの明示的な契約を破っていることを示していますが、前のケースでの変更は、意図しない契約を破っていることを示しています。低レベルのライブラリでは、ユーザーを混乱させないように、動作を変更する必要性を避けるために多大な努力を払うことがよくあります。


重要なのは、テストを書いた後、システムのリファクタリングやバグの修正、新機能の追加などの際に、そのテストを再び触る必要はないということです。この理解が、システムを大規模に扱うことを可能にしています。システムを拡張する際には、 これまでにシステムに対して書かれたすべてのテストに手をつけるのではなく、 変更内容に関連する少数の新しいテストを書くだけで済みます。システムの動作に大きな変更があった場合にのみ、テストを変更する必要があります。そのような場合、システムのすべてのユーザーを更新するコストに比べて、テストを更新するコストは小さくなる傾向にあります。

### パブリックAPIを使ったテスト

目的を理解したところで、テスト対象のシステムの要件が変わらない限りテストを変更する必要がないようにするための方法を考えてみましょう。これを確実にするための最も重要な方法は、 テスト対象のシステムをそのユーザーと同じ方法で呼び出すようにテストを書くことです。テストがシステムのユーザーと同じように動作するのであれば、 定義上、テストを壊すような変更はユーザーも壊すことになります。さらに、このようなテストはユーザーにとっても有用な例や資料となります。
例 12-1 では、トランザクションを検証してデータベースに保存します。

Example 12-1. トランザクションAPI
```java
public void processTransaction(Transaction transaction) {
 if (isValid(transaction)) {
    saveToDatabase(transaction);
  }
}
private boolean isValid(Transaction t) {
  return t.getAmount() < t.getSender().getBalance();
}
private void saveToDatabase(Transaction t) {
  String s = t.getSender() + "," + t.getRecipient() + "," + t.getAmount();
  database.put(t.getId(), s);
}
public void setAccountBalance(String accountName, int balance) {
  // Write the balance to the database directly
}
public void getAccountBalance(String accountName) {
  // Read transactions from the database to determine the account balance
}
```

このコードをテストするには、例12-2のように、"private "可視性修飾子を削除して、実装ロジックを直接テストするのがよいでしょう。

Example 12-2. トランザクションAPIの実装に関する素朴なテスト
```java
@Test
public void emptyAccountShouldNotBeValid() {
  assertThat(processor.isValid(newTransaction().setSender(EMPTY_ACCOUNT)))
      .isFalse();
}
@Test
public void shouldSaveSerializedData() {
  processor.saveToDatabase(newTransaction()
      .setId(123)
      .setSender("me")
      .setRecipient("you")
      .setAmount(100));
  assertThat(database.get(123)).isEqualTo("me,you,100");
}
```

このテストは、実際のユーザーとは大きく異なる方法でトランザクションプロセッサと対話します。システムの内部状態を覗き見し、システムのAPIの一部として公開されていないメソッドを呼び出します。その結果、このテストは脆く、テスト対象のシステムのほとんどすべてのリファクタリング（メソッド名の変更、ヘルパークラスへのファクタリング、シリアル化フォーマットの変更など）は、たとえそのクラスの実際のユーザーには見えない変更であっても、テストが壊れる原因となります。

その代わり、例12-3のようにクラスのパブリックAPIに対してのみテストを行うことで、同じテストカバレッジを実現することができます(*2)。

Example 12-3. 公開APIのテスト
```java
@Test
public void shouldTransferFunds() {
  processor.setAccountBalance("me", 150);
  processor.setAccountBalance("you", 20);

  processor.processTransaction(newTransaction()
      .setSender("me")
      .setRecipient("you")
      .setAmount(100));
  assertThat(processor.getAccountBalance("me")).isEqualTo(50);
  assertThat(processor.getAccountBalance("you")).isEqualTo(120);
}
@Test
public void shouldNotPerformInvalidTransactions() {
  processor.setAccountBalance("me", 50);
  processor.setAccountBalance("you", 20);

  processor.processTransaction(newTransaction()
      .setSender("me")
      .setRecipient("you")
      .setAmount(100));
  assertThat(processor.getAccountBalance("me")).isEqualTo(50);
  assertThat(processor.getAccountBalance("you")).isEqualTo(20);
}
```

パブリック API のみを使用したテストは、定義上、ユーザーと同じ方法でテスト対象のシステムにアクセスすることになります。このようなテストは、明示的な契約を結んでいるため、より現実的で脆くありません。このようなテストが壊れた場合、そのシステムの既存のユーザーも壊れることになります。このようなコントラクトのみをテストするということは、テストの面倒な変更を気にすることなく、システムの内部リファクタリングを自由に行うことができるということです。

何をもって「公開API」とするかは必ずしも明確ではありません。また、この問題はユニットテストにおける「ユニット」とは何かということにも関わってきます。ユニットとは、個々の関数のように小さなものから、複数の関連するパッケージやモジュールのセットのように大きなものまであります。この文脈で「パブリックAPI」と言った場合、実際にはそのユニットがコードを所有するチーム以外の第三者に公開するAPIのことを指しています。これは、いくつかのプログラミング言語で提供されている可視性の概念とは必ずしも一致しません。例えば、Javaのクラスは、同じユニット内の他のパッケージからアクセスできるように「パブリック」と定義されているかもしれませんが、ユニット外の他のパーティーが使用することは意図されていません。また、Pythonのように可視性の概念が組み込まれていない言語もあります（プライベートメソッド名の前にアンダースコアを付けるなどの慣例に頼っている場合が多い）。Bazelのようなビルドシステムでは、プログラミング言語で公開が宣言されているAPIに依存することができる人をさらに制限することができます。

ユニットの適切なスコープを定義し、したがって何を公開APIとみなすかは、科学というよりも芸術ですが、以下のような経験則があります。

- あるメソッドやクラスが、他の 1 つまたは 2 つのクラスをサポートするためだけに存在している場合 (つまり「ヘルパークラス」)、それはおそらく独立したユニットと考えるべきではなく、その機能は直接ではなくそれらのクラスを通してテストされるべきです。
- パッケージやクラスが、その所有者と相談せずに誰でもアクセスできるように設計されている場合、それはほぼ確実に、直接テストすべきユニットを構成します。
- パッケージやクラスを所有している人だけがアクセスできるが、さまざまな状況で役立つ一般的な機能を提供するように設計されている場合（つまり「サポートライブラリ」）、それもユニットとみなして直接テストすべきです。サポートライブラリのコードは、サポートライブラリ自身のテストとユーザのテストの両方でカバーされるため、通常、テストには冗長性が生じます。しかし、このような冗長性は貴重なものです。冗長性がないと、ライブラリのユーザー（およびそのテスト）が削除された場合に、テストのカバレッジにギャップが生じてしまいます。

Googleでは、エンジニアが、実装の詳細に対してテストするよりも、公開されているAPIを使ってテストする方が良いと説得しなければならないことがあります。なぜなら、自分が書いたばかりのコードに注目してテストを書く方が、そのコードがシステム全体にどのような影響を与えるかを理解するよりもずっと簡単だからです。しかし、私たちはこのような方法を奨励することに価値があると考えています。なぜなら、前もって余分な努力をすることで、メンテナンスの負担を何倍にも減らすことができるからです。公開されているAPIに対してテストを行うことで、脆さを完全に防ぐことはできませんが、システムに意味のある変更があった場合にのみテストが失敗するようにすることは、最も重要なことです。

### インタラクションではなくステートをテストする

テストが実装の詳細に依存するもう一つの方法は、 テストがシステムのどのメソッドを呼び出すかではなく、 その呼び出しの結果をどのように検証するかということです。一般的に、テスト対象のシステムが期待通りの動作をするかどうかを検証するには、2つの方法があります。ステートテストでは、システムを起動した後に、システム自体を観察して確認します。一方、インタラクションテストでは、システムを起動した際に、システムが協力者に対して期待通りのアクションを起こすかどうかを確認します。多くのテストでは、状態の検証とインタラクションの検証を組み合わせて行います。

インタラクションテストはステートテストよりも脆い傾向があります。 これは、パブリックなメソッドをテストするよりもプライベートなメソッドをテストするほうが 脆いのと同じ理由です。例 12-4 は、テストダブル (第 13 章で詳しく説明します) を使用してシステムがデータベースとどのようにやり取りするのかを検証するテストです。

Example 12-4. 脆性相互作用のテスト
```java
@Test
public void shouldWriteToDatabase() {
  accounts.createUser("foobar");
  verify(database).put("foobar");
}
```

このテストでは、データベースのAPIに対して特定の呼び出しが行われたことを検証しますが、いくつかの異なる方法で失敗する可能性があります。

- テスト対象のシステムのバグにより、レコードが書き込まれた直後にデータベースから削除されてしまった場合、テストは失敗したいと思っていても合格してしまいます。
- テスト対象のシステムがリファクタリングされて、同等のレコードを書くためにわずかに異なるAPIを呼び出すようになった場合、テストは合格したいと思っていても失敗してしまいます。

例 12-5 で示したように、システムの状態を直接テストする方がはるかにもろいです。

Example 12-5. Testing against state
```java
@Test
public void shouldCreateUsers() {
  accounts.createUser("foobar");
  assertThat(accounts.getUser("foobar")).isNotNull();
}
```

このテストでは、私たちが気にかけていること、つまりテスト対象のシステムとインタラクトした後の状態をより正確に表現しています。

インタラクションテストに問題がある最も一般的な理由は、モッキングフレームワークに頼りすぎていることです。これらのフレームワークを使うと、テスト用の替え玉を簡単に作ることができます。この替え玉は、実際のオブジェクトの代わりにテストで使用することができます。この方法ではインタラクションテストがもろくなってしまうので、高速で決定論的な実オブジェクトであれば、モックされたオブジェクトよりも実オブジェクトの使用を好む傾向があります。

 テストの替え玉やモッキング・フレームワーク、それらを使うべき場合、より安全な代替手段などについては、第13章を参照してください。

## 明確なテストを書く

遅かれ早かれ、たとえ脆さを完全に回避したとしても、テストは失敗します。テストの失敗は良いことです。テストの失敗はエンジニアにとって有益なシグナルであり、ユニットテストの価値を高める主な方法のひとつです。

テストの失敗は、次の2つの理由で起こります(*3)。

- テスト対象のシステムに問題があったり、不完全だったりする。この結果は、まさにテストの目的である「バグを警告し、それを修正できるようにする」ためのものです。
- テスト自体に問題がある。この場合、テスト対象のシステムには何の問題もありませんが、テストの仕様が間違っていたということになります。もしこのテストが自分で書いたものではなく、既存のテストだった場合、そのテストは脆いということになります。前のセクションでは、脆いテストを回避する方法を説明しましたが、完全に排除できることはほとんどありません。

テストが失敗した場合、エンジニアの最初の仕事は、その失敗がこれらのケースのどれに該当するかを特定し、実際の問題を診断することです。そのスピードは、テストのわかりやすさにかかっているといっても過言ではありません。明確なテストとは、障害を診断するエンジニアにとって、テストが存在する目的や障害が発生する理由がすぐにわかるものです。テストが明確でないのは、失敗の理由が明らかでない場合や、最初に書かれた理由を理解するのが難しい場合である。明確なテストは、テスト対象のシステムを文書化し、新しいテストの基礎となりやすいなどの利点もあります。

テストが明確であることは、時間の経過とともに重要になります。テストは多くの場合、それを書いたエンジニアよりも長持ちします。また、システムの要件や理解は、古くなるにつれて微妙に変化します。不合格になったテストが、何年も前にもうチームにいないエンジニアによって書かれたものである可能性は十分にあり、その目的や修正方法を把握する方法はありません。これは、不明瞭なプロダクションコードとは対照的です。プロダクションコードの目的は、そのコードを呼び出しているものや、そのコードを削除したときに何が壊れるかを見れば、十分な努力をすれば大抵は分かります。不明瞭なテストでは、テストを削除しても、テストカバレッジに微妙な穴が生じる（可能性がある）以外には何の効果もないので、その目的を理解することはできないかもしれません。

最悪の場合、これらの不明瞭なテストは、エンジニアが修正方法を見つけられずに削除されてしまうこともあります。このようなテストを削除すると、テストカバレッジに穴が開いてしまうだけでなく、そのテストが存在していた期間（何年にもなるかもしれません）、そのテストがゼロの価値しか提供していなかったことになります。

テストスイートの規模を拡大し、長期間にわたって有用であるためには、 そのスイート内の個々のテストが可能な限り明確であることが重要です。このセクションでは、明快なテストを実現するためのテクニックや考え方を紹介します。

### テストの完全性と簡潔性

テストをわかりやすくするための高レベルの特性として、 完全性と簡潔性があります。テストの本文に、どのようにしてその結果にたどり着くのかを理解するために必要な情報がすべて含まれている場合、そのテストは完全です。テストが簡潔であるとは、 邪魔な情報や無関係な情報が含まれていないことです。例 12-6 は、完全でも簡潔でもないテストを示しています。

例12-6. 不完全で雑然としたテスト
```java
@Test
public void shouldPerformAddition() {
  Calculator calculator = new Calculator(new RoundingStrategy(),
      "unused", ENABLE_COSINE_FEATURE, 0.01, calculusEngine, false);
  int result = calculator.calculate(newTestCalculation());
  assertThat(result).isEqualTo(5); // Where did this number come from?
}
```

このテストでは、コンストラクタに多くの無関係な情報を渡しており、 テストの実際の重要な部分はヘルパーメソッドの中に隠されています。例 12-7 に示すように、ヘルパーメソッドの入力を明確にすることでテストをより完全なものにし、 別のヘルパーを使って電卓を作る際の無関係な詳細を隠すことでより簡潔なものにすることができます。



例 12-7. 完全で簡潔なテスト
```java
@Test
public void shouldPerformAddition() {
  Calculator calculator = newCalculator();
  int result = calculator.calculate(newCalculation(2, Operation.PLUS, 3));
  assertThat(result).isEqualTo(5);
}
```

後述するアイデア、特にコード共有に関するアイデアは、完全性と簡潔性に結びつきます。特に、DRY (Don't Repeat Yourself) の原則に反しても、 テストがより明確になるのであれば価値があるでしょう。覚えておいてほしいのは、 テストの本文にはそれを理解するのに必要なすべての情報が含まれていなければならず、 無関係な情報や気を散らすような情報は含まれていてはいけないということです。

### メソッドではなく、ビヘイビアをテストする

多くのエンジニアの最初の直感は、テストの構造をコードの構造に合わせようとすることです。つまり、すべてのプロダクションメソッドに対応するテストメソッドがあるということです。このパターンは最初は便利ですが、時間が経つにつれて問題が出てきます。テストするメソッドが複雑になると、そのテストも複雑になり、推論が難しくなります。例えば、例 12-8 のコードでは、トランザクションの結果を表示しています。

例 12-8. トランザクションスニペット
```java
public void displayTransactionResults(User user, Transaction transaction) {
  ui.showMessage("You bought a " + transaction.getItemName());
  if (user.getBalance() < LOW_BALANCE_THRESHOLD) {
    ui.showMessage("Warning: your balance is low!");
  }
}
```

例12-9のように、そのメソッドで表示される可能性のあるメッセージの両方をカバーするテストを見つけることは珍しいことではありません。

例 12-9. メソッド駆動型のテスト
```java
@Test
public void testDisplayTransactionResults() {
  transactionProcessor.displayTransactionResults(
      newUserWithBalance(
          LOW_BALANCE_THRESHOLD.plus(dollars(2))),
      new Transaction("Some Item", dollars(3)));
  assertThat(ui.getText()).contains("You bought a Some Item");
  assertThat(ui.getText()).contains("your balance is low");
}
```

このようなテストでは、最初は1つ目の方法だけでテストを行っていたと考えられます。このようなテストの場合、最初は1つ目のメソッドだけを対象としたテストだったのが、2つ目のメッセージが追加されたときにエンジニアがテストを拡張してしまったのでしょう。この修正は、テスト対象のメソッドがより複雑になり、より多くの機能を実装するようになると、そのユニットテストはますます複雑になり、作業が困難になるという悪い前例を作ってしまいます。

問題は、メソッドを中心にテストを構成すると、当然ながら不明確なテストになってしまうことです。なぜなら、ひとつのメソッドは、しばしばボンネットの中でいくつかの異なることを行い、いくつかの厄介なエッジケースやコーナーケースがあるからです。メソッドごとにテストを書くのではなく、ビヘイビアごとにテストを書くのです。(*4) ビヘイビアとは、システムが特定の状態にあるときに、一連の入力に対してどのように反応するかを保証するものです。(*5) ビヘイビアは、しばしば「given」「when」「then」という言葉を使って表現されます。"銀行口座が空であるときに、そこからお金を引き出そうとすると、その取引は拒否される。" メソッドとビヘイビアの間のマッピングは多対多です。自明ではないほとんどのメソッドは複数のビヘイビアを実装し、一部のビヘイビアは複数のメソッドの相互作用に依存します。先ほどの例は、例12-10のように振る舞い駆動型のテストを用いて書き換えることができます。

例 12-10. 動作駆動型のテスト
```java
@Test
public void displayTransactionResults_showsItemName() {
  transactionProcessor.displayTransactionResults(
      new User(), new Transaction("Some Item"));
  assertThat(ui.getText()).contains("You bought a Some Item");
}
@Test
public void displayTransactionResults_showsLowBalanceWarning() {
  transactionProcessor.displayTransactionResults(
      newUserWithBalance(
          LOW_BALANCE_THRESHOLD.plus(dollars(2))),
      new Transaction("Some Item", dollars(3)));
  assertThat(ui.getText()).contains("your balance is low");
}
```

一つのテストを分割するために必要な余分な定型文は、その価値以上のものであり、結果として得られるテストは元のテストよりもはるかに明確です。振る舞い駆動型のテストは、メソッド指向型のテストよりも明確になる傾向がありますが、それにはいくつかの理由があります。第一に、テストは自然言語のように読めるので、手間のかかる精神的な解析を必要とせず、自然に理解することができます。次に、各テストの範囲が限定されているため、原因と結果がより明確に表現されます。最後に、それぞれのテストが短くて説明的であるため、どの機能がすでにテストされているかがわかりやすく、エンジニアが既存のテスト手法を重ねるのではなく、新しい合理的なテスト手法を追加することを促す。

#### ビヘイビアを重視したテストの構成

テストがメソッドではなくビヘイビアと結びついていると考えると、 テストの構造が大きく変わります。すべてのビヘイビアには3つの部分があることを覚えておいてください。すなわち、システムがどのように設定されているかを定義する「given」コンポーネント、システムに対して行うべきアクションを定義する「when」コンポーネント、そして結果を検証する「then」コンポーネントです(*6)。CucumberやSpockのようなフレームワークでは、given/when/thenを直接組み込むことができます。その他の言語では、例12-11のように、空白やオプションのコメントを使って構造を目立たせることができます。

例 12-11. 構造化されたテスト
```java
@Test
public void transferFundsShouldMoveMoneyBetweenAccounts() {
  // Given two accounts with initial balances of $150 and $20
  Account account1 = newAccountWithBalance(usd(150));
  Account account2 = newAccountWithBalance(usd(20));

  // When transferring $100 from the first to the second account
  bank.transferFunds(account1, account2, usd(100));

  // Then the new account balances should reflect the transfer
  assertThat(account1.getBalance()).isEqualTo(usd(50));
  assertThat(account2.getBalance()).isEqualTo(usd(120));
}
```

些細なテストではこのレベルの記述は必ずしも必要ではなく、 通常はコメントを省略して空白に頼ってセクションを明確にすることで十分です。しかし、コメントを明示することで、より高度なテストを理解しやすくすることができます。このパターンでは、3 つのレベルの粒度でテストを読むことができます。

1. テストメソッド名 (後述) を見て、テストされている動作の大まかな内容を知ることから始めます。
2. それだけでは不十分な場合は、 given/when/then コメントを見て動作の正式な説明を確認します。
3. 最後に、実際のコードを見て、その動作がどのように表現されているかを正確に確認することができます。

このパターンは、テスト対象のシステムへの複数の呼び出しの間にアサーションを挟む（つまり、「when」と「then」のブロックを組み合わせる）ことで最もよく破られます。このように「then」と「when」のブロックを組み合わせると、実行されるアクションと期待される結果を区別することが難しくなるため、テストが明確でなくなります。

テストが多段階プロセスの各ステップを検証したい場合は、when/then ブロックを交互に並べて定義することができます。長いブロックは、"and "という単語で分割することで、より説明的にすることができます。例 12-12 は、比較的複雑な動作駆動型のテストがどのようなものかを示しています。

例 12-12. テスト内でのwhen/thenブロックの交互配置
```java
@Test
public void shouldTimeOutConnections() {
  // Given two users
  User user1 = newUser();
  User user2 = newUser();

  // And an empty connection pool with a 10-minute timeout
  Pool pool = newPool(Duration.minutes(10));

  // When connecting both users to the pool
  pool.connect(user1);
  pool.connect(user2);

  // Then the pool should have two connections
  assertThat(pool.getConnections()).hasSize(2);

  // When waiting for 20 minutes
  clock.advance(Duration.minutes(20));

  // Then the pool should have no connections
  assertThat(pool.getConnections()).isEmpty();

  // And each user should be disconnected
  assertThat(user1.isConnected()).isFalse();
  assertThat(user2.isConnected()).isFalse();
}
```

このようなテストを書く際には、不用意に複数の動作を同時にテストしてしまわないように注意しましょう。各テストは単一の動作のみを対象とすべきであり、ユニットテストの大部分は1つの「when」と1つの「then」ブロックのみを必要とします。

#### テストの名前は、テストされる動作の後に付けます。

メソッド指向のテストは通常、テスト対象のメソッドにちなんだ名前をつけます (たとえば updateBalance メソッドのテストは通常 testUpdateBalance と呼ばれます)。より焦点を絞った振る舞い駆動型のテストでは、より多くの柔軟性を持ち、 テストの名前で有用な情報を伝えることができます。テスト名は非常に重要です。障害報告の中で最初あるいは唯一のトークンとなることが多いので、 テストが壊れたときに問題点を伝える絶好の機会となります。また、テストの意図を表現する最もわかりやすい方法でもあります。

テストの名前は、テストしている動作を要約したものでなければなりません。良い名前は、システム上で行われている動作と期待される結果の両方を表しています。テストの名前には、システムにアクションを起こす前に、システムの状態や環境などの追加情報を含めることもあります。言語やフレームワークによっては、Jasmineを使った例12-13のように、テストを互いに入れ子にして文字列で名前をつけることができるので、他よりも簡単にテストを行うことができます。

例12-13. ネストしたネーミングパターンの例
```
describe("multiplication", function() {
  describe("with a positive number", function() {
    var positiveNumber = 10;
    it("is positive with another positive number", function() {
      expect(positiveNumber * 10).toBeGreaterThan(0);
    });
    it("is negative with a negative number", function() {
      expect(positiveNumber * -10).toBeLessThan(0);
    });
  });
  describe("with a negative number", function() {
    var negativeNumber = 10;
    it("is negative with a positive number", function() {
      expect(negativeNumber * 10).toBeLessThan(0);
    });
    it("is positive with another negative number", function() {
      expect(negativeNumber * -10).toBeGreaterThan(0);
    });
  });
});
```

他の言語では、これらの情報をすべてメソッド名にエンコードする必要があるため、例12-14のようなメソッドの命名パターンになります。

例12-14. メソッドの命名パターンの例
```
multiplyingTwoPositiveNumbersShouldReturnAPositiveNumber
multiply_postiveAndNegative_returnsNegative
divide_byZero_throwsException
```

このような名前は、プロダクションコードのメソッドに通常書くべきものよりもはるかに冗長ですが、ユースケースは異なります。これらを呼び出すコードを書く必要はなく、その名前はレポートで人間が読む必要があることが多いのです。そのため、余分な冗長性が必要になります。

ひとつのテストクラスの中で一貫して使用されている限り、 さまざまな名前の付け方が許容されます。困ったときには、テストの名前を "should" という単語で始めてみるのがいいでしょう。この命名法は、テストするクラスの名前と一緒に使うと、テスト名を文章として読むことができます。たとえば BankAccount クラスのテストである shouldNotAllowWithdrawalsWhenBalanceIsEmpty は、"BankAccount should not allow withdrawalals when balance is empty" と読むことができます。スイート内のすべてのテストメソッドの名前を読むことで、テスト対象のシステムで実装されている動作を把握することができます。テストの名前に "and" という単語を使う必要がある場合は、 実際には複数の動作をテストしている可能性が高いので、 複数のテストを書くべきでしょう。

## テストに論理を入れるな

明確なテストとは、一見しただけで正しいことがわかるものです。 つまり、テストを見ただけで正しいことをしていることがわかるのです。これが可能なのは、テストコードでは各テストが特定の入力セットのみを処理する必要があるのに対し、プロダクションコードではあらゆる入力を処理するように一般化する必要があるからです。プロダクションコードでは、複雑なロジックが正しいかどうかを確認するテストを書くことができます。しかし、テストコードにはそのような余裕はありません。もし、テストを検証するためにテストを書く必要があると感じたら、何かが間違っているのです。

複雑さは論理の形で導入されることがほとんどです。論理は、演算子やループ、条件分岐など、プログラミング言語の命令的な部分で定義されます。コードにロジックが含まれていると、その結果をただ画面から読み取るのではなく、少し頭を使って計算しなければなりません。テストの推論を難しくするためには、それほど多くのロジックは必要ありません。例えば、例 12-15 のテストは正しく見えるでしょうか？

Example 12-15. バグを隠すロジック
```java
@Test
public void shouldNavigateToAlbumsPage() {
  String baseUrl = "http://photos.google.com/";
  Navigator nav = new Navigator(baseUrl);
  nav.goToAlbumPage();
  assertThat(nav.getCurrentUrl()).isEqualTo(baseUrl + "/albums");
}
```

ここには大したロジックはありません。実際には1つの文字列を連結しただけです。しかし、このテストを単純化してロジックを1つ取り除くと、例12-16のようにバグがすぐに明らかになります。

例 12-16. ロジックのないテストでバグを発見
```java
@Test
public void shouldNavigateToPhotosPage() {
  Navigator nav = new Navigator("http://photos.google.com/");
  nav.goToPhotosPage();
  assertThat(nav.getCurrentUrl()))
      .isEqualTo("http://photos.google.com//albums"); // Oops!
}
```

文字列全体を書き出してみると、URLにスラッシュが1つではなく2つあると思っていることがすぐにわかります。本番のコードが同様のミスを犯した場合、このテストはバグを検出できません。ベースとなるURLを複製することは、テストをより説明的で意味のあるものにするための小さな代償でした（この章の後の方で、DAMPテストとDRYテストの議論を参照してください）。

人間が文字列の連結によるバグを発見するのが苦手なのであれば、ループや条件分岐のような高度なプログラミング構造によるバグを発見するのはもっと苦手です。テストコードでは、巧妙なロジックよりも直線的なコードにこだわり、テストをより説明的で意味のあるものにするためには、多少の重複を許容することを検討してみてください。重複やコードの共有については、この章の後半で説明します。

### 明確な失敗メッセージを書く

明瞭さの最後の側面は、テストの書き方ではなく、テストが失敗したときにエンジニアが見るものに関係します。理想的な世界では、エンジニアはテストそのものを見なくても、 ログやレポートに書かれた失敗メッセージを読むだけで問題を診断することができます。優れた失敗メッセージには、テストの名前とほぼ同じ情報が含まれています。つまり、望ましい結果、実際の結果、関連するパラメータを明確に表現する必要があります。
以下は、悪い失敗メッセージの例です。

```
Test failed: account is closed
```

アカウントが閉鎖されたためにテストが失敗したのか、それともアカウントが閉鎖されると予想されていて、そうではなかったためにテストが失敗したのか？より良い失敗メッセージは、期待される状態と実際の状態を明確に区別し、結果についてより多くのコンテキストを与えます。

```
   Expected an account in state CLOSED, but got account:
        <{name: "my-account", state: "OPEN"}
```

優れたライブラリを使えば、有用な失敗メッセージを簡単に書くことができます。例12-17のアサーションをJavaのテストで考えてみましょう。1つ目のアサーションは従来のJUnitのアサーションを使用し、2つ目のアサーションはGoogleが開発したアサーションライブラリTruthを使用しています。

例 12-17. Truthライブラリを使用したアサーション
```java
Set<String> colors = ImmutableSet.of("red", "green", "blue");
assertTrue(colors.contains("orange")); // JUnit
assertThat(colors).contains("orange"); // Truth
```

最初のアサーションはブール値を受け取るだけなので、 「期待された <true> が <false> になった」 といった一般的なエラーメッセージしか表示することができません。2 番目のアサーションでは、アサーションのサブジェクトを明示的に受け取るため、より有用なエラーメッセージを表示することができます。AssertionError: <[red, green, blue]> は <orange> を含むべきでした。"

すべての言語でこのようなヘルパーが利用できるわけではありませんが、失敗メッセージの重要な情報を手動で指定することは常に可能であるべきです。例えば、Goのテストアサーションは慣習的に例12-18のようになります。

例 12-18. Goでのテストアサーション
```go
result := Add(2, 3)
if result !=5 {
  t.Errorf("Add(2, 3) = %v, want %v", result, 5)
}
```

## テストとコードの共有。DRYではなくDAMP

明確なテストを書き、もろさを回避するための最後の側面は、コードの共有に関係しています。ほとんどのソフトウェアは DRY (Don't Repeat Yourself) と呼ばれる原則を達成しようとしています。DRYとは、すべての概念が1つの場所に統一され、コードの重複が最小限に抑えられていれば、ソフトウェアの保守が容易になるというものです。このアプローチは、特に変更を容易にするという点で価値があります。エンジニアは、複数の参照を追跡するのではなく、1つのコードだけを更新する必要があるからです。しかし、このような統合は、コードを不明確にし、コードが何をしているのかを理解するために読者が参照の連鎖を辿らなければならなくなるというデメリットがあります。

通常のプロダクションコードでは、コードの変更や作業を容易にするためには、このようなデメリットは小さな代償となります。しかし、この費用対効果の分析は、テストコードの文脈では少し違ってきます。優れたテストは安定しているように設計されており、実際にはテスト対象のシステムが変更されたときにテストが壊れることを望んでいます。そのため、テストコードに関しては、DRYはそれほどメリットがありません。一方、テストは単独で行わなければならず、自明の正しさでなければバグのリスクがあります。先に述べたように、テストが複雑になり、適切に動作しているかどうかを確認するために独自のテストが必要だと感じられるようになったら、何かが間違っている。

完全な DRY である代わりに、テストコードはしばしば DAMP（Descriptive And Meaningful Phrases）であるように努めるべきです。多少の重複があっても、それがテストをよりシンプルで明確なものにするのであれば問題ありません。例12-19では、DRYすぎるテストを紹介しています。

例 12-19. DRY に過ぎるテスト
```java
@Test
public void shouldAllowMultipleUsers() {
  List<User> users = createUsers(false, false);
  Forum forum = createForumAndRegisterUsers(users);
  validateForumAndUsers(forum, users);
}

@Test
public void shouldNotAllowBannedUsers() {
  List<User> users = createUsers(true);
  Forum forum = createForumAndRegisterUsers(users);
  validateForumAndUsers(forum, users);
}

// Lots more tests...

private static List<User> createUsers(boolean... banned) {
  List<User> users = new ArrayList<>();
  for (boolean isBanned : banned) {
    users.add(newUser()
        .setState(isBanned ? State.BANNED : State.NORMAL)
        .build());
  }
  return users;
}

private static Forum createForumAndRegisterUsers(List<User> users) {
  Forum forum = new Forum();
  for (User user : users) {
    try {
      forum.register(user);
    } catch(BannedUserException ignored) {}
  }
  return forum;
}

private static void validateForumAndUsers(Forum forum, List<User> users) {
  assertThat(forum.isReachable()).isTrue();
  for (User user : users) {
    assertThat(forum.hasRegisteredUser(user))
        .isEqualTo(user.getState() == State.BANNED);
  }
}
```

このコードの問題点は、先ほどのわかりやすさの話からも明らかでしょう。重要な詳細はヘルパーメソッドに隠されていて、読者はファイルのまったく別の部分にスクロールしないと見ることができません。また、これらのヘルパーには、一見しただけでは検証が困難なロジックがたくさん含まれています（バグを発見しましたか？例12-20のように、DAMPを使うように書き換えると、テストの内容がより明確になります。

例 12-20. テストはDAMPであるべき
```java
@Test
public void shouldAllowMultipleUsers() {
  User user1 = newUser().setState(State.NORMAL).build();
  User user2 = newUser().setState(State.NORMAL).build();

  Forum forum = new Forum();
  forum.register(user1);
  forum.register(user2);

  assertThat(forum.hasRegisteredUser(user1)).isTrue();
  assertThat(forum.hasRegisteredUser(user2)).isTrue();
}

@Test
public void shouldNotRegisterBannedUsers() {
  User user = newUser().setState(State.BANNED).build();

  Forum forum = new Forum();
  try {
    forum.register(user);
  } catch(BannedUserException ignored) {}

  assertThat(forum.hasRegisteredUser(user)).isFalse();
}
```

これらのテストは重複が多く、テスト本体も少し長くなりますが、余分な冗長性には価値があります。個々のテストははるかに意味のあるものであり、 テスト本体から離れることなく完全に理解することができます。これらのテストを読む人は、テストが主張することを実行しており、バグを隠していないことを確信できます。

DAMPはDRYの代わりではなく、DRYを補完するものです。ヘルパーメソッドやテストインフラストラクチャは、テストをより簡潔にし、テスト対象の特定の動作に関係のない反復的なステップを省くことで、テストをより明確にすることができます。重要なのは、このようなリファクタリングはテストをより説明的で意味のあるものにすることを念頭に置いて行うべきであり、 単に繰り返しを減らすという名目で行うべきではないということです。このセクションの残りの部分では、テスト間でコードを共有するための一般的なパターンについて説明します。

### 共有値

多くのテストでは、テストで使用する共通の価値観を定義し、 その価値観がどのように作用するかについての様々なケースをカバーするテストを定義することで構成されています。例 12-21 は、そのようなテストの例です。

例 12-21. 曖昧な名前の共有値
```java
private static final Account ACCOUNT_1 = Account.newBuilder()
 .setState(AccountState.OPEN).setBalance(50).build();

private static final Account ACCOUNT_2 = Account.newBuilder()
 .setState(AccountState.CLOSED).setBalance(0).build();

private static final Item ITEM = Item.newBuilder()
 .setName("Cheeseburger").setPrice(100).build();

// Hundreds of lines of other tests...

@Test
public void canBuyItem_returnsFalseForClosedAccounts() {
  assertThat(store.canBuyItem(ITEM, ACCOUNT_1)).isFalse();
}

@Test
public void canBuyItem_returnsFalseWhenBalanceInsufficient() {
  assertThat(store.canBuyItem(ITEM, ACCOUNT_2)).isFalse();
}
```

この方法はテストを非常に簡潔にすることができますが、 テストスイートが大きくなると問題が発生します。ひとつは、あるテストで特定の値が選ばれた理由を理解するのが難しいということです。例12-21では、テスト名を見ればどのシナリオがテストされているのかがわかりますが、それでも定義までスクロールして `ACCOUNT_1` や `ACCOUNT_2` がそのシナリオに適しているかどうかを確認する必要があります。より説明的な定数名（例えば、`CLOSED_ACCOUNT`や`ACCOUNT_WITH_LOW_BALANCE`）は少し助けになりますが、テストされる値の正確な詳細を確認することはまだ難しく、これらの値を再利用することが容易であるため、名前がテストに必要なものを正確に説明していなくても、エンジニアはそうすることができます。

エンジニアは、テストごとに個別の値を作成すると冗長になってしまうため、共有定数を使用したいと考えるのが普通です。この目的を達成するためのより良い方法は、ヘルパーメソッドを使ってデータを構築することです（例12-22参照）。ヘルパーメソッドでは、テスト作成者が気になる値だけを指定し、それ以外の値には妥当なデフォルト(*7)を設定する必要があります。名前付きパラメータをサポートしている言語では、このような構造は些細なことですが、名前付きパラメータを持たない言語では、Builderパターンなどの構造を使ってそれを模倣することができます（多くの場合、AutoValueなどのツールの助けを借ります）。

例12-22. ヘルパーメソッドによる値の共有
```
# A helper method wraps a constructor by defining arbitrary defaults for 
# each of its parameters.
def newContact(
  firstName="Grace", lastName="Hopper", phoneNumber="555-123-4567"):
  return Contact(firstName, lastName, phoneNumber)

# Tests call the helper, specifying values for only the parameters that they 
# care about.
def test_fullNameShouldCombineFirstAndLastNames(self):
  def contact = newContact(firstName="Ada", lastName="Lovelace")
  self.assertEqual(contact.fullName(), "Ada Lovelace")

// Languages like Java that don’t support named parameters can emulate them
// by returning a mutable "builder" object that represents the value under
// construction.
private static Contact.Builder newContact() {
  return Contact.newBuilder()
   .setFirstName("Grace")
   .setLastName("Hopper")
   .setPhoneNumber("555-123-4567");
}

// Tests then call methods on the builder to overwrite only the parameters
// that they care about, then call build() to get a real value out of the
// builder.
@Test
public void fullNameShouldCombineFirstAndLastNames() {
  Contact contact = newContact()
      .setFirstName("Ada")
      .setLastName("Lovelace")
      .build();
  assertThat(contact.getFullName()).isEqualTo("Ada Lovelace");
}
```

ヘルパーメソッドを使用してこれらの値を作成することで、各テストは必要な値を正確に作成することができ、無関係な情報を指定したり他のテストと衝突したりする心配はありません。


### 共有セットアップ

コードを共有してテストする方法として、セットアップや初期化のロジックがあります。多くのテストフレームワークでは、エンジニアがスイート内の各テストを実行する前に実行するメソッドを定義することができます。これらのメソッドを適切に使用すれば、退屈で無関係な初期化ロジックの繰り返しを避けることができ、テストをより明確かつ簡潔なものにすることができます。逆に不適切な使い方をすると、重要な内容を別の初期化メソッドに隠してしまうことで、テストの完成度を下げてしまうことになります。

セットアップメソッドの最も良い使用例は、 テスト対象のオブジェクトとその共同作業者を構築することです。これは、テストの大半がオブジェクトを構築する際の引数を気にせず、 デフォルトの状態のままにしておく場合に便利です。同じ考え方は、テストのダブルスの戻り値をスタブ化する場合にも当てはまります。 これについては第 13 章で詳しく説明します。

セットアップメソッドを使用する際のリスクとして、 セットアップで使用する特定の値にテストが依存するようになると、 不明瞭なテストになってしまうことがあります。たとえば、例 12-23 のテストは、 "Donald Knuth" という文字列がどこから来たのかを知るために、 テストを読む人が探し回る必要があるため、不完全なものに見えます。

例 12-23. setupメソッドの値への依存性
```java
private NameService nameService;
private UserStore userStore;

@Before
public void setUp() {
  nameService = new NameService();
  nameService.set("user1", "Donald Knuth");
  userStore = new UserStore(nameService);
}

// [... hundreds of lines of tests ...]

@Test
public void shouldReturnNameFromService() {
  UserDetails user = userStore.get("user1");
  assertThat(user.getName()).isEqualTo("Donald Knuth");
}
```

このように特定の値を明示的に指定するテストでは、 その値を直接指定し、必要に応じて setup メソッドで定義したデフォルト値を上書きします。結果として、例 12-24 に示すようにテストの繰り返しが若干多くなりますが、 結果ははるかに説明的で意味のあるものとなります。

例 12-24. mMethodsのセットアップで値を上書きする
```java
private NameService nameService;
private UserStore userStore;

@Before
public void setUp() {
  nameService = new NameService();
  nameService.set("user1", "Donald Knuth");
  userStore = new UserStore(nameService);
}

@Test
public void shouldReturnNameFromService() {
  nameService.set("user1", "Margaret Hamilton");
  UserDetails user = userStore.get("user1");
  assertThat(user.getName()).isEqualTo("Margaret Hamilton");
}
```

### Shared Helpers and Validation

The last common way that code is shared across tests is via “helper methods” called from the body of the test methods. We already discussed how helper methods can be a useful way for concisely constructing test values --- this usage is warranted, but other types of helper methods can be dangerous.
One common type of helper is a method that performs a common set of assertions against a system under test. The extreme example is a validate method called at the end of every test method, which performs a set of fixed checks against the system under test. Such a validation strategy can be a bad habit to get into because tests using this approach are less behavior driven. With such tests, it is much more difficult to determine the intent of any particular test and to infer what exact case the author had in mind when writing it. When bugs are introduced, this strategy can also make them more difficult to localize because they will frequently cause a large number of tests to start failing.
More focused validation methods can still be useful, however. The best validation helper methods assert a single conceptual fact about their inputs, in contrast to general-purpose validation methods that cover a range of conditions. Such methods can be particularly helpful when the condition that they are validating is conceptually simple but requires looping or conditional logic to implement that would reduce clarity were it included in the body of a test method. For example, the helper method in Example 12-25 might be useful in a test covering several different cases around account access.

Example 12-25. A conceptually simple test
```java
private void assertUserHasAccessToAccount(User user, Account account) {
  for (long userId : account.getUsersWithAccess()) {
    if (user.getId() == userId) {
      return;
    }
  }
  fail(user.getName() + " cannot access " + account.getName());
}
```

### Defining Test Infrastructure

The techniques we’ve discussed so far cover sharing code across methods in a single test class or suite. Sometimes, it can also be valuable to share code across multiple test suites. We refer to this sort of code as test infrastructure. Though it is usually more valuable in integration or end-to-end tests, carefully designed test infrastructure can make unit tests much easier to write in some circumstances.
Custom test infrastructure must be approached more carefully than the code sharing that happens within a single test suite. In many ways, test infrastructure code is more similar to production code than it is to other test code given that it can have many callers that depend on it and can be difficult to change without introducing breakages. Most engineers aren’t expected to make changes to the common test infrastructure while testing their own features. Test infrastructure needs to be treated as its own separate product, and accordingly, test infrastructure must always have its own tests.
Of course, most of the test infrastructure that most engineers use comes in the form of well-known third-party libraries like JUnit. A huge number of such libraries are available, and standardizing on them within an organization should happen as early and universally as possible. For example, Google many years ago mandated Mockito as the only mocking framework that should be used in new Java tests and banned new tests from using other mocking frameworks. This edict produced some grumbling at the time from people comfortable with other frameworks, but today, it’s universally seen as a good move that made our tests easier to understand and work with.

## Conclusion

Unit tests are one of the most powerful tools that we as software engineers have to make sure that our systems keep working over time in the face of unanticipated changes. But with great power comes great responsibility, and careless use of unit testing can result in a system that requires much more effort to maintain and takes much more effort to change without actually improving our confidence in said system.
Unit tests at Google are far from perfect, but we’ve found tests that follow the practices outlined in this chapter to be orders of magnitude more valuable than those that don’t. We hope they’ll help you to improve the quality of your own tests!

## TL;DRs

- Strive for unchanging tests.
- Test via public APIs.
- Test state, not interactions.
- Make your tests complete and concise.
- Test behaviors, not methods.
- Structure tests to emphasize behaviors.
- Name tests after the behavior being tested.
- Don’t put logic in tests.
- Write clear failure messages.
- Follow DAMP over DRY when sharing code for tests.






-----

1 Note that this is slightly different from a flaky test, which fails nondeterministically without any change to production code.
2 This is sometimes called the "Use the front door first principle.”
3 These are also the same two reasons that a test can be “flaky.” Either the system under test has a nondeterministic fault, or the test is flawed such that it sometimes fails when it should pass.
4 Seehttps://testing.googleblog.com/2014/04/testing-on-toilet-test-behaviors-not.htmlandhttps://dannorth.net/ introducing-bdd.
5 Furthermore, a feature (in the product sense of the word) can be expressed as a collection of behaviors.
6 These components are sometimes referred to as “arrange,” “act,” and “assert.”
7 In many cases, it can even be useful to slightly randomize the default values returned for fields that aren’t explicitly set. This helps to ensure that two different instances won’t accidentally compare as equal, and makes it more difficult for engineers to hardcode dependencies on the defaults.




