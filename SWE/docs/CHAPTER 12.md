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

### Test State, Not Interactions

Another way that tests commonly depend on implementation details involves not which methods of the system the test calls, but how the results of those calls are verified. In general, there are two ways to verify that a system under test behaves as expected. With state testing, you observe the system itself to see what it looks like after invoking with it. With interaction testing, you instead check that the system took an expected sequence of actions on its collaborators in response to invoking it. Many tests will perform a combination of state and interaction validation.
Interaction tests tend to be more brittle than state tests for the same reason that it’s more brittle to test a private method than to test a public method: interaction tests check how a system arrived at its result, whereas usually you should care only what the result is. Example 12-4 illustrates a test that uses a test double (explained further in Chapter 13) to verify how a system interacts with a database.

Example 12-4. A brittle interaction test
```java
@Test
public void shouldWriteToDatabase() {
  accounts.createUser("foobar");
  verify(database).put("foobar");
}
```

The test verifies that a specific call was made against a database API, but there are a couple different ways it could go wrong:

- If a bug in the system under test causes the record to be deleted from the database shortly after it was written, the test will pass even though we would have wanted it to fail.
- If the system under test is refactored to call a slightly different API to write an equivalent record, the test will fail even though we would have wanted it to pass.

It’s much less brittle to directly test against the state of the system, as demonstrated in Example 12-5.

Example 12-5. Testing against state
```java
@Test
public void shouldCreateUsers() {
  accounts.createUser("foobar");
  assertThat(accounts.getUser("foobar")).isNotNull();
}
```

This test more accurately expresses what we care about: the state of the system under test after interacting with it.

The most common reason for problematic interaction tests is an over reliance on mocking frameworks. These frameworks make it easy to create test doubles that record and verify every call made against them, and to use those doubles in place of real objects in tests. This strategy leads directly to brittle interaction tests, and so we tend to prefer the use of real objects in favor of mocked objects, as long as the real objects are fast and deterministic.

 For a more extensive discussion of test doubles and mocking frameworks, when they should be used, and safer alternatives, see Chapter 13.

## Writing Clear Tests

Sooner or later, even if we’ve completely avoided brittleness, our tests will fail. Failure is a good thing --- test failures provide useful signals to engineers, and are one of the main ways that a unit test provides value.
Test failures happen for one of two reasons:(*3)

- The system under test has a problem or is incomplete. This result is exactly what tests are designed for: alerting you to bugs so that you can fix them.
- The test itself is flawed. In this case, nothing is wrong with the system under test, but the test was specified incorrectly. If this was an existing test rather than one that you just wrote, this means that the test is brittle. The previous section discussed how to avoid brittle tests, but it’s rarely possible to eliminate them entirely.

When a test fails, an engineer’s first job is to identify which of these cases the failure falls into and then to diagnose the actual problem. The speed at which the engineer can do so depends on the test’s clarity. A clear test is one whose purpose for existing and reason for failing is immediately clear to the engineer diagnosing a failure. Tests fail to achieve clarity when their reasons for failure aren’t obvious or when it’s difficult to figure out why they were originally written. Clear tests also bring other benefits, such as documenting the system under test and more easily serving as a basis for new tests.
Test clarity becomes significant over time. Tests will often outlast the engineers who wrote them, and the requirements and understanding of a system will shift subtly as it ages. It’s entirely possible that a failing test might have been written years ago by an engineer no longer on the team, leaving no way to figure out its purpose or how to fix it. This stands in contrast with unclear production code, whose purpose you can usually determine with enough effort by looking at what calls it and what breaks when it’s removed. With an unclear test, you might never understand its purpose, since removing the test will have no effect other than (potentially) introducing a subtle hole in test coverage.
In the worst case, these obscure tests just end up getting deleted when engineers can’t figure out how to fix them. Not only does removing such tests introduce a hole in test coverage, but it also indicates that the test has been providing zero value for perhaps the entire period it has existed (which could have been years).
For a test suite to scale and be useful over time, it’s important that each individual test in that suite be as clear as possible. This section explores techniques and ways of thinking about tests to achieve clarity.

### Make Your Tests Complete and Concise

Two high-level properties that help tests achieve clarity are completeness and conciseness. A test is complete when its body contains all of the information a reader needs in order to understand how it arrives at its result. A test is concise when it contains no other distracting or irrelevant information. Example 12-6 shows a test that is neither complete nor concise:

Example 12-6. An incomplete and cluttered test
```java
@Test
public void shouldPerformAddition() {
  Calculator calculator = new Calculator(new RoundingStrategy(),
      "unused", ENABLE_COSINE_FEATURE, 0.01, calculusEngine, false);
  int result = calculator.calculate(newTestCalculation());
  assertThat(result).isEqualTo(5); // Where did this number come from?
}
```

The test is passing a lot of irrelevant information into the constructor, and the actual important parts of the test are hidden inside of a helper method. The test can be made more complete by clarifying the inputs of the helper method, and more concise by using another helper to hide the irrelevant details of constructing the calculator, as illustrated in Example 12-7.



Example 12-7. A complete, concise test
```java
@Test
public void shouldPerformAddition() {
  Calculator calculator = newCalculator();
  int result = calculator.calculate(newCalculation(2, Operation.PLUS, 3));
  assertThat(result).isEqualTo(5);
}
```

Ideas we discuss later, especially around code sharing, will tie back to completeness and conciseness. In particular, it can often be worth violating the DRY (Don’t Repeat Yourself) principle if it leads to clearer tests. Remember: a test’s body should contain all of the information needed to understand it without containing any irrelevant or distracting information.

### Test Behaviors, Not Methods

The first instinct of many engineers is to try to match the structure of their tests to the structure of their code such that every production method has a corresponding test method. This pattern can be convenient at first, but over time it leads to problems: as the method being tested grows more complex, its test also grows in complexity and becomes more difficult to reason about. For example, consider the snippet of code in Example 12-8, which displays the results of a transaction.

Example 12-8. A transaction snippet
```java
public void displayTransactionResults(User user, Transaction transaction) {
  ui.showMessage("You bought a " + transaction.getItemName());
  if (user.getBalance() < LOW_BALANCE_THRESHOLD) {
    ui.showMessage("Warning: your balance is low!");
  }
}
```

It wouldn’t be uncommon to find a test covering both of the messages that might be shown by the method, as presented in Example 12-9.

Example 12-9. A method-driven test
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

With such tests, it’s likely that the test started out covering only the first method. Later, an engineer expanded the test when the second message was added (violating the idea of unchanging tests that we discussed earlier). This modification sets a bad precedent: as the method under test becomes more complex and implements more functionality, its unit test will become increasingly convoluted and grow more and more difficult to work with.
The problem is that framing tests around methods can naturally encourage unclear tests because a single method often does a few different things under the hood and might have several tricky edge and corner cases. There’s a better way: rather than writing a test for each method, write a test for each behavior.(*4) A behavior is any guarantee that a system makes about how it will respond to a series of inputs while in a particular state.(*5) Behaviors can often be expressed using the words “given,” “when,” and “then”: “Given that a bank account is empty, when attempting to withdraw money from it, then the transaction is rejected.” The mapping between methods and behaviors is many-to-many: most nontrivial methods implement multiple behaviors, and some behaviors rely on the interaction of multiple methods. The previous example can be rewritten using behavior-driven tests, as presented in Example 12-10.

Example 12-10. A behavior-driven test
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

The extra boilerplate required to split apart the single test is more than worth it, and the resulting tests are much clearer than the original test. Behavior-driven tests tend to be clearer than method-oriented tests for several reasons. First, they read more like natural language, allowing them to be naturally understood rather than requiring laborious mental parsing. Second, they more clearly express cause and effect because each test is more limited in scope. Finally, the fact that each test is short and descriptive makes it easier to see what functionality is already tested and encourages engineers to add new streamlined test methods instead of piling onto existing methods.

#### Structure tests to emphasize behaviors

Thinking about tests as being coupled to behaviors instead of methods significantly affects how they should be structured. Remember that every behavior has three parts: a “given” component that defines how the system is set up, a “when” component that defines the action to be taken on the system, and a “then” component that validates the result.(*6) Tests are clearest when this structure is explicit. Some frameworks like Cucumber and Spock directly bake in given/when/then. Other languages can use whitespace and optional comments to make the structure stand out, such as that shown in Example 12-11.

Example 12-11. A well-structured test
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

This level of description isn’t always necessary in trivial tests, and it’s usually sufficient to omit the comments and rely on whitespace to make the sections clear. However, explicit comments can make more sophisticated tests easier to understand. This pattern makes it possible to read tests at three levels of granularity:

1. A reader can start by looking at the test method name (discussed below) to get a rough description of the behavior being tested.
2. If that’s not enough, the reader can look at the given/when/then comments for a formal description of the behavior.
3. Finally, a reader can look at the actual code to see precisely how that behavior is expressed.

This pattern is most commonly violated by interspersing assertions among multiple calls to the system under test (i.e., combining the “when” and “then” blocks). Merging the “then” and “when” blocks in this way can make the test less clear because it makes it difficult to distinguish the action being performed from the expected result.
When a test does want to validate each step in a multistep process, it’s acceptable to define alternating sequences of when/then blocks. Long blocks can also be made more descriptive by splitting them up with the word “and.” Example 12-12 shows what a relatively complex, behavior-driven test might look like.

Example 12-12. Alternating when/then blocks within a test
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

When writing such tests, be careful to ensure that you’re not inadvertently testing multiple behaviors at the same time. Each test should cover only a single behavior, and the vast majority of unit tests require only one “when” and one “then” block.

#### Name tests after the behavior being tested

Method-oriented tests are usually named after the method being tested (e.g., a test for the updateBalance method is usually called testUpdateBalance). With more focused behavior-driven tests, we have a lot more flexibility and the chance to convey useful information in the test’s name. The test name is very important: it will often be the first or only token visible in failure reports, so it’s your best opportunity to communicate the problem when the test breaks. It’s also the most straightforward way to express the intent of the test.
A test’s name should summarize the behavior it is testing. A good name describes both the actions that are being taken on a system and the expected outcome. Test names will sometimes include additional information like the state of the system or its environment before taking action on it. Some languages and frameworks make this easier than others by allowing tests to be nested within one another and named using strings, such as in Example 12-13, which uses Jasmine.

Example 12-13. Some sample nested naming patterns
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

Other languages require us to encode all of this information in a method name, leading to method naming patterns like that shown in Example 12-14.

Example 12-14. Some sample method naming patterns
```
multiplyingTwoPositiveNumbersShouldReturnAPositiveNumber
multiply_postiveAndNegative_returnsNegative
divide_byZero_throwsException
```

Names like this are much more verbose than we’d normally want to write for methods in production code, but the use case is different: we never need to write code that calls these, and their names frequently need to be read by humans in reports. Hence, the extra verbosity is warranted.
Many different naming strategies are acceptable so long as they’re used consistently within a single test class. A good trick if you’re stuck is to try starting the test name with the word “should.” When taken with the name of the class being tested, this naming scheme allows the test name to be read as a sentence. For example, a test of a BankAccount class named shouldNotAllowWithdrawalsWhenBalanceIsEmpty can be read as “BankAccount should not allow withdrawals when balance is empty.” By reading the names of all the test methods in a suite, you should get a good sense of the behaviors implemented by the system under test. Such names also help ensure that the test stays focused on a single behavior: if you need to use the word “and” in a test name, there’s a good chance that you’re actually testing multiple behaviors and should be writing multiple tests!

## Don’t Put Logic in Tests

Clear tests are trivially correct upon inspection; that is, it is obvious that a test is doing the correct thing just from glancing at it. This is possible in test code because each test needs to handle only a particular set of inputs, whereas production code must be generalized to handle any input. For production code, we’re able to write tests that ensure complex logic is correct. But test code doesn’t have that luxury --- if you feel like you need to write a test to verify your test, something has gone wrong!
Complexity is most often introduced in the form of logic. Logic is defined via the imperative parts of programming languages such as operators, loops, and conditionals. When a piece of code contains logic, you need to do a bit of mental computation to determine its result instead of just reading it off of the screen. It doesn’t take much logic to make a test more difficult to reason about. For example, does the test in Example 12-15 look correct to you?

Example 12-15. Logic concealing a bug
```java
@Test
public void shouldNavigateToAlbumsPage() {
  String baseUrl = "http://photos.google.com/";
  Navigator nav = new Navigator(baseUrl);
  nav.goToAlbumPage();
  assertThat(nav.getCurrentUrl()).isEqualTo(baseUrl + "/albums");
}
```

There’s not much logic here: really just one string concatenation. But if we simplify the test by removing that one bit of logic, a bug immediately becomes clear, as demonstrated in Example 12-16.

Example 12-16. A test without logic reveals the bug
```java
@Test
public void shouldNavigateToPhotosPage() {
  Navigator nav = new Navigator("http://photos.google.com/");
  nav.goToPhotosPage();
  assertThat(nav.getCurrentUrl()))
      .isEqualTo("http://photos.google.com//albums"); // Oops!
}
```

When the whole string is written out, we can see right away that we’re expecting two slashes in the URL instead of just one. If the production code made a similar mistake, this test would fail to detect a bug. Duplicating the base URL was a small price to pay for making the test more descriptive and meaningful (see the discussion of DAMP versus DRY tests later in this chapter).
If humans are bad at spotting bugs from string concatenation, we’re even worse at spotting bugs that come from more sophisticated programming constructs like loops and conditionals. The lesson is clear: in test code, stick to straight-line code over clever logic, and consider tolerating some duplication when it makes the test more descriptive and meaningful. We’ll discuss ideas around duplication and code sharing later in this chapter.

### Write Clear Failure Messages

One last aspect of clarity has to do not with how a test is written, but with what an engineer sees when it fails. In an ideal world, an engineer could diagnose a problem just from reading its failure message in a log or report without ever having to look at the test itself. A good failure message contains much the same information as the test’s name: it should clearly express the desired outcome, the actual outcome, and any relevant parameters.
Here’s an example of a bad failure message:

```
Test failed: account is closed
```

Did the test fail because the account was closed, or was the account expected to be closed and the test failed because it wasn’t? A better failure message clearly distinguishes the expected from the actual state and gives more context about the result:

```
   Expected an account in state CLOSED, but got account:
        <{name: "my-account", state: "OPEN"}
```

Good libraries can help make it easier to write useful failure messages. Consider the assertions in Example 12-17 in a Java test, the first of which uses classical JUnit asserts, and the second of which uses Truth, an assertion library developed by Google:

Example 12-17. An assertion using the Truth library
```java
Set<String> colors = ImmutableSet.of("red", "green", "blue");
assertTrue(colors.contains("orange")); // JUnit
assertThat(colors).contains("orange"); // Truth
```

Because the first assertion only receives a Boolean value, it is only able to give a generic error message like “expected <true> but was <false>,” which isn’t very informative in a failing test output. Because the second assertion explicitly receives the subject of the assertion, it is able to give a much more useful error message: AssertionError: <[red, green, blue]> should have contained <orange>.”
Not all languages have such helpers available, but it should always be possible to manually specify the important information in the failure message. For example, test assertions in Go conventionally look like Example 12-18.

Example 12-18. A test assertion in Go
```go
result := Add(2, 3)
if result !=5 {
  t.Errorf("Add(2, 3) = %v, want %v", result, 5)
}
```

## Tests and Code Sharing: DAMP, Not DRY

One final aspect of writing clear tests and avoiding brittleness has to do with code sharing. Most software attempts to achieve a principle called DRY --- “Don’t Repeat Yourself.” DRY states that software is easier to maintain if every concept is canonically represented in one place and code duplication is kept to a minimum. This approach is especially valuable in making changes easier because an engineer needs to update only one piece of code rather than tracking down multiple references. The downside to such consolidation is that it can make code unclear, requiring readers to follow chains of references to understand what the code is doing.
In normal production code, that downside is usually a small price to pay for making code easier to change and work with. But this cost/benefit analysis plays out a little differently in the context of test code. Good tests are designed to be stable, and in fact you usually want them to break when the system being tested changes. So DRY doesn’t have quite as much benefit when it comes to test code. At the same time, the costs of complexity are greater for tests: production code has the benefit of a test suite to ensure that it keeps working as it becomes complex, whereas tests must stand by themselves, risking bugs if they aren’t self-evidently correct. As mentioned earlier, something has gone wrong if tests start becoming complex enough that it feels like they need their own tests to ensure that they’re working properly.
Instead of being completely DRY, test code should often strive to be DAMP --- that is, to promote “Descriptive And Meaningful Phrases.” A little bit of duplication is OK in tests so long as that duplication makes the test simpler and clearer. To illustrate, Example 12-19 presents some tests that are far too DRY.

Example 12-19. A test that is too DRY
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

The problems in this code should be apparent based on the previous discussion of clarity. For one, although the test bodies are very concise, they are not complete: important details are hidden away in helper methods that the reader can’t see without having to scroll to a completely different part of the file. Those helpers are also full of logic that makes them more difficult to verify at a glance (did you spot the bug?). The test becomes much clearer when it’s rewritten to use DAMP, as shown in Example 12-20.

Example 12-20. Tests should be DAMP
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

These tests have more duplication, and the test bodies are a bit longer, but the extra verbosity is worth it. Each individual test is far more meaningful and can be understood entirely without leaving the test body. A reader of these tests can feel confident that the tests do what they claim to do and aren’t hiding any bugs.
DAMP is not a replacement for DRY; it is complementary to it. Helper methods and test infrastructure can still help make tests clearer by making them more concise, factoring out repetitive steps whose details aren’t relevant to the particular behavior being tested. The important point is that such refactoring should be done with an eye toward making tests more descriptive and meaningful, and not solely in the name of reducing repetition. The rest of this section will explore common patterns for sharing code across tests.

### Shared Values

Many tests are structured by defining a set of shared values to be used by tests and then by defining the tests that cover various cases for how these values interact. Example 12-21 illustrates what such tests look like.

Example 12-21. Shared values with ambiguous names
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

This strategy can make tests very concise, but it causes problems as the test suite grows. For one, it can be difficult to understand why a particular value was chosen for a test. In Example 12-21, the test names fortunately clarify which scenarios are being tested, but you still need to scroll up to the definitions to confirm that `ACCOUNT_1` and `ACCOUNT_2` are appropriate for those scenarios. More descriptive constant names (e.g., `CLOSED_ACCOUNT` and `ACCOUNT_WITH_LOW_BALANCE`) help a bit, but they still make it more difficult to see the exact details of the value being tested, and the ease of reusing these values can encourage engineers to do so even when the name doesn’t exactly describe what the test needs.
Engineers are usually drawn to using shared constants because constructing individual values in each test can be verbose. A better way to accomplish this goal is to construct data using helper methods (see Example 12-22) that require the test author to specify only values they care about, and setting reasonable defaults(*7) for all other values. This construction is trivial to do in languages that support named parameters, but languages without named parameters can use constructs such as the Builder pattern to emulate them (often with the assistance of tools such as AutoValue):

Example 12-22. Shared values using helper methods
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

Using helper methods to construct these values allows each test to create the exact values it needs without having to worry about specifying irrelevant information or conflicting with other tests.


### Shared Setup

A related way that tests shared code is via setup/initialization logic. Many test frameworks allow engineers to define methods to execute before each test in a suite is run. Used appropriately, these methods can make tests clearer and more concise by obviating the repetition of tedious and irrelevant initialization logic. Used inappropriately, these methods can harm a test’s completeness by hiding important details in a separate initialization method.
The best use case for setup methods is to construct the object under tests and its collaborators. This is useful when the majority of tests don’t care about the specific arguments used to construct those objects and can let them stay in their default states. The same idea also applies to stubbing return values for test doubles, which is a concept that we explore in more detail in Chapter 13.
One risk in using setup methods is that they can lead to unclear tests if those tests begin to depend on the particular values used in setup. For example, the test in Example 12-23 seems incomplete because a reader of the test needs to go hunting to discover where the string “Donald Knuth” came from.

Example 12-23. Dependencies on values in setup methods
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

Tests like these that explicitly care about particular values should state those values directly, overriding the default defined in the setup method if need be. The resulting test contains slightly more repetition, as shown in Example 12-24, but the result is far more descriptive and meaningful.

Example 12-24. Overriding values in setup mMethods
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




