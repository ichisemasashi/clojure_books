# CHAPTER 13  Test Doubles

Written by Andrew Trenk and Dillon Bly

Edited by Tom Manshreck

ユニットテストは、開発者の生産性を維持し、コードの欠陥を減らすための重要なツールです。単純なコードであれば簡単に書くことができますが、コードが複雑になるとテストを書くのが難しくなります。

例えば、外部のサーバーにリクエストを送信し、そのレスポンスをデータベースに格納する関数のテストを書こうとしたとします。一握りのテストを書くことは、多少の努力で可能かもしれません。しかし、このように何百、何千ものテストを書かなければならない場合、テストスイートの実行には何時間もかかるでしょう。また、ネットワークのランダムな障害やテスト同士のデータの上書きなどの問題によって、テストが不安定になる可能性もあります。

このような場合に便利なのがテストダブルです。テストダブルとは、映画でスタントダブルが俳優の代役を務めるように、テストで実際の実装の代わりとなるオブジェクトや関数のことです。テストダブルの使用はしばしばモッキングと呼ばれますが、この章ではその言葉を避けます。なぜなら、この言葉はテストダブルのより具体的な側面を指すのにも使われるからです。

テストダブルの最も分かりやすいタイプは、インメモリデータベースのように、実際の実装と同様の動作をするオブジェクトの単純な実装です。その他のタイプのテストダブルでは、システムの特定の詳細を検証することができます。例えば、稀なエラー条件を簡単に引き起こすことができたり、実際にはその関数の実装を実行せずに重い関数を確実に呼び出すことができたりします。

前の 2 つの章では、小規模なテストの概念を紹介し、 テストスイートの大部分のテストを小規模なテストで構成すべき理由を説明しました。しかし、実稼働中のコードは、複数のプロセスやマシンにまたがって通信を行うため、小規模なテストの制約に当てはまらないことがよくあります。テストダブルスは実際の実装よりもはるかに軽量であるため、 実行速度が速く、不安定さのない小さなテストをたくさん書くことができます。

## テストダブルスがソフトウェア開発に与える影響

テストダブルスを使用すると、ソフトウェア開発にいくつかの複雑な問題が発生し、いくつかのトレードオフが必要になります。ここで紹介した概念は、この章でさらに詳しく説明します。

- テスト可能性
  - テストダブルを使用するためには、コードベースがテスト可能に設計されている必要があります。つまり、テストで実際の実装とテストダブルを入れ替えることが可能でなければなりません。例えば、データベースを呼び出すコードは、実際のデータベースの代わりにテストダブルを使用できるような柔軟性が必要です。コードベースがテストを考慮して設計されておらず、後になってテストが必要だと判断した場合、テストダブルの使用をサポートするためにコードをリファクタリングするには大きなコミットメントが必要になります。
- 適用性
  - テストダブルスを適切に使用することで、エンジニアリングの速度を強力に向上させることができますが、不適切な使い方をすると、テストが脆弱で複雑になり、効果も低くなります。大規模なコードベースでテストダブルスが不適切に使用された場合、これらの欠点は拡大し、エンジニアの生産性が大きく損なわれる可能性があります。多くの場合、テスト替え玉は適切ではなく、エンジニアは代わりに実際の実装を使用することを好むべきです。
- 忠実度
  - 忠実度とは、テストダブルの動作が、置き換えようとしている実際の実装の動作にどれだけ似ているかということです。例えば、データベースに追加されたデータを無視して常に空の結果を返すデータベースのテストを書こうとしたとしましょう。しかし、完全な忠実性は実現できないかもしれません。テストダブルをテストに使用するためには、実際の実装よりもはるかにシンプルでなければならないことがよくあります。多くの場合、完全な忠実性がなくてもテストダブルを使用することは適切です。テストダブルを使ったユニットテストは、実際の実装を使ったより大規模なテストで補う必要があります。

## Googleにおけるテストダブルス

Google では、テストの二重化がコードベースにもたらす生産性やソフトウェア品質への恩恵と、不適切に使用した場合の悪影響について、数多くの例を見てきました。Googleで行っているプラクティスは、これらの経験に基づいて時間をかけて進化してきました。歴史的には、テストダブルを効果的に使用するためのガイドラインはほとんどありませんでしたが、多くのチームのコードベースに共通のパターンやアンチパターンが発生するのを見て、ベストプラクティスが進化していきました。

私たちが苦労して学んだ教訓のひとつは、テストダブルスを簡単に作成できるモッキング・フレームワークを使いすぎることの危険性です（モッキング・フレームワークについては、この章の後半で詳しく説明します）。モッキング・フレームワークは、Googleで初めて使用されたとき、あらゆる釘に対応するハンマーのように思えました。そのようなテストの代償に気付き始めたのは、数年後、数え切れないほどのテストを行った後でした。これらのテストは簡単に書けるにもかかわらず、バグを発見することはほとんどなく、維持するために絶え間ない努力が必要であることに、私たちは大きな苦しみを感じていました。現在、Googleでは振り子が反対方向に振れ始めており、多くのエンジニアがモッキング・フレームワークを避け、より現実的なテストを書くようになっています。

本章で取り上げたプラクティスは、Googleでは一般的に合意されているものですが、実際の適用はチームによって大きく異なります。このばらつきは、エンジニアがこれらのプラクティスについて一貫した知識を持っていないこと、既存のコードベースがこれらのプラクティスに準拠していない惰性的なものであること、またはチームが長期的な影響を考えずに短期的に最も簡単なことを行うことに起因する。

### 基本的な概念

テストダブルスの効果的な使い方を説明する前に、テストダブルスに関連する基本的な概念を説明します。これらは、この章の後半で説明するベスト・プラクティスの基礎となります。

#### テストダブルの例

クレジットカードでの支払いを処理する必要のあるeコマースサイトを想像してみてください。その中核となるのが、例 13-1 に示すようなコードです。

例 13-1. クレジットカードのサービス
```java
class PaymentProcessor {
  private CreditCardService creditCardService;
  ...
  boolean makePayment(CreditCard creditCard, Money amount) {
    if (creditCard.isExpired()) { return false; }
    boolean success =
        creditCardService.chargeCreditCard(creditCard, amount);
    return success;
  }
}
```

実際のクレジットカードサービスをテストに使うのは不可能ですが (テストを実行することによる取引手数料を想像してみてください!)、 その代わりにテストダブルを使って実際のシステムの動作をシミュレートすることができます。例 13-2 のコードは、非常にシンプルなテストダブルを示しています。

例 13-2. 些細なテストダブル
```java
class TestDoubleCreditCardService implements CreditCardService {
  @Override
  public boolean chargeCreditCard(CreditCard creditCard, Money amount) {
    return true;
  }
}
```

このテストダブルはあまり役に立たないように見えますが、 これをテストに使用することで、makePayment() メソッドのロジックの一部をテストすることができます。たとえば例 13-3 では、クレジットカードの有効期限が切れたときにこのメソッドが適切に動作するかどうかを検証することができます。なぜなら、このテストが実行するコードパスはクレジットカードサービスの動作に依存していないからです。

例 13-3. テストダブルの使用
```java
@Test public void cardIsExpired_returnFalse() {
  boolean success = paymentProcessor.makePayment(EXPIRED_CARD, AMOUNT);
  assertThat(success).isFalse();
}
```

この章の次のセクションでは、今回よりも複雑な状況でのテストダブルスの活用方法について説明します。

#### 継ぎ目

コードのユニットテストを書くことができるように書かれたコードは、テスト可能であると言われています。シーム(継ぎ目)とは、テストダブルの使用を可能にすることでコードをテスト可能にする方法である。

ディペンデンシー・インジェクションは、シームを導入するための一般的な手法です。つまり、クラスが依存性注入を使用すると、 そのクラスが使用する必要のあるクラス (つまりクラスの依存関係) が直接インスタンス化されずにクラスに渡されるので、 テストの中でこれらの依存関係を置き換えることが可能になります。

例 13-4 は、依存性の注入の例です。コンストラクタは、CreditCardService のインスタンスを生成するのではなく、 パラメータとしてインスタンスを受け取ります。

例 13-4. ディペンデンシー・インジェクション
```java
class PaymentProcessor {
  private CreditCardService creditCardService;

  PaymentProcessor(CreditCardService creditCardService) {
    this.creditCardService = creditCardService;
  }
  ...
}
```

このコンストラクタを呼び出したコードは、適切な Credit CardService インスタンスを生成する責任があります。本番環境のコードでは、外部サーバーと通信するCreditCardServiceの実装を渡すことができますが、テストでは例13-5のようにテスト用のダブルを渡すことができます。

例 13-5. テスト用のダブルを渡す
```java
PaymentProcessor paymentProcessor =
    new PaymentProcessor(new TestDoubleCreditCardService());
```

コンストラクタを手動で指定する際の煩雑な作業を軽減するために、自動依存性注入フレームワークを使用して、オブジェクトグラフを自動的に構築することができる。Googleでは、GuiceやDaggerといった自動依存性注入フレームワークが、Javaコードによく使われています。

また、PythonやJavaScriptなどの動的型付け言語では、個々の関数やオブジェクトのメソッドを動的に置き換えることができる。これらの言語では、依存性注入の重要性は低い。なぜなら、この機能により、テストに適さない依存性の関数やメソッドをオーバーライドするだけで、実際の依存性の実装をテストで使用することができるからである。

テスト可能なコードを書くには、先行投資が必要です。テスト可能性を考慮するのが遅くなればなるほど、コードベースに適用するのが難しくなるため、コードベースのライフタイムの早い段階でのテストが特に重要です。テストを考慮せずに書かれたコードは、通常、適切なテストを追加する前にリファクタリングやリライトを行う必要があります。

#### モッキングフレームワーク

モッキングフレームワークとは、テスト内でのテストダブルの作成を容易にするためのソフトウェアライブラリです。モックフレームワークを使用すると、テストダブルが必要になるたびに新しいクラスを定義する必要がなくなるため、定型的な作業を減らすことができます。

例 13-6 では、Java 用のモッキング・フレームワークである Mockito を使用しています。Mockitoは`CreditCardService`用のテストダブルを作成し、特定の値を返すように指示しています。

例 13-6. モッキングフレームワーク
```java
class PaymentProcessorTest {
  ...
  PaymentProcessor paymentProcessor;

  // たった1行のコードでCreditCardServiceのテストダブルを作成します。
  @Mock CreditCardService mockCreditCardService;
  @Before public void setUp() {
    // テストダブルでテスト対象のシステムに渡す。
    paymentProcessor = new PaymentProcessor(mockCreditCardService);
  }
  @Test public void chargeCreditCardFails_returnFalse() {
    // Give some behavior to the test double: it will return false
    // anytime the chargeCreditCard() method is called. The usage of
    // “any()” for the method’s arguments tells the test double to
    // return false regardless of which arguments are passed.
    when(mockCreditCardService.chargeCreditCard(any(), any())
      .thenReturn(false);
    boolean success = paymentProcessor.makePayment(CREDIT_CARD, AMOUNT);
    assertThat(success).isFalse();
  }
}
```

モッキング・フレームワークは、ほとんどの主要なプログラミング言語に対応しています。Googleでは、JavaにはMockito、C++にはGoogletestのgooglemockコンポーネント、Pythonにはunittest.mockを使用しています。

モッキング・フレームワークは、テスト・ダブルスの使用を容易にしてくれますが、使いすぎるとコードベースのメンテナンスが難しくなるという重大な注意点があります。この章の後の方で、これらの問題のいくつかを取り上げます。

## テストダブルスの使い方

テストダブルスを使用するための主なテクニックは 3 つあります。このセクションでは、これらのテクニックがどのようなもので、どのように違うのかを簡単にご紹介します。この章の後のセクションでは、これらのテクニックを効果的に活用する方法について詳しく説明します。

これらのテクニックの違いを認識しているエンジニアは、テストダブルを使用する必要に迫られたときに、どのテクニックを使用するのが適切かを知ることができます。

### フェイク

フェイクとは、あるAPIの軽量な実装のことです。実際の実装に似た動作をしますが、実運用には適していません。例えば、インメモリデータベースなどです。例 13-7 は、フェイクの例を示しています。

例 13-7. 単純な偽物
```java
// フェイクの作成は迅速かつ簡単です。
AuthorizationService fakeAuthorizationService =
    new FakeAuthorizationService();
AccessManager accessManager = new AccessManager(fakeAuthorizationService):

// 未知のユーザーIDではアクセスできないはずです。
assertFalse(accessManager.userHasAccess(USER_ID));

// ユーザーIDは、認証サービスに追加された後にアクセスできる必要があります。
fakeAuthorizationService.addAuthorizedUser(new User(USER_ID));
assertThat(accessManager.userHasAccess(USER_ID)).isTrue();
```

しかし、テストで使用する必要のあるオブジェクトのフェイクが存在しない場合もありますし、フェイクを作成することは、現在および将来の実際の実装と同様の動作を保証する必要があるため、困難を伴います。

### スタッビング

スタビングとは、それ自体では何の動作もしない関数に動作を与えることです。つまり、どのような値を返すかを関数に正確に指定します（つまり、戻り値をスタブ化します）。
例13-8はスタブの例です。Mockitoモッキングフレームワークの`when(...).thenReturn(...)`メソッドコールは、`lookupUser()`メソッドの振る舞いを指定しています。

例 13-8. スタブ
```java
// モッキングフレームワークで作成されたテストダブルを渡します。
AccessManager accessManager = new AccessManager(mockAuthorizationService):

// nullが返された場合、そのユーザーIDはアクセスできないはずです。
when(mockAuthorizationService.lookupUser(USER_ID)).thenReturn(null);
assertThat(accessManager.userHasAccess(USER_ID)).isFalse();

// 非nullの値が返された場合に、アクセス権を持つべきユーザーIDです。
when(mockAuthorizationService.lookupUser(USER_ID)).thenReturn(USER);
assertThat(accessManager.userHasAccess(USER_ID)).isTrue();
```

スタッビングは一般的にモッキングフレームワークを使って行われ、戻り値をハードコードする新しいクラスを手動で作成する際に必要となる定型文を減らすことができます。

スタッビングは迅速かつシンプルな手法ですが、この章の後で説明するように、制限があります。

### インタラクションテスト

インタラクションテストとは、関数の実装を実際に呼び出すことなく、その関数がどのように呼び出されるかを検証する方法です。関数が正しい方法で呼び出されていないと、テストは失敗します。例えば、関数がまったく呼び出されていない、何度も呼び出されている、間違った引数で呼び出されている、などです。

例13-9はインタラクションテストの一例です。Mockitoモッキングフレームワークの`verify(...)`メソッドを使って、`lookupUser()`が期待通りに呼ばれているかどうかを検証しています。

例 13-9. インタラクションテスト
```java
// モッキングフレームワークで作成されたテストダブルを渡します。
AccessManager accessManager = new AccessManager(mockAuthorizationService);
accessManager.userHasAccess(USER_ID);

// accessManager.userHasAccess(USER_ID)がmockAuthorizationService.lookupUser(USER_ID)を
// 呼び出さなかった場合、テストは失敗します。
verify(mockAuthorizationService).lookupUser(USER_ID);
```

スタビングと同様に、インタラクション・テストは一般的にモッキング・フレームワークによって行われます。これにより、関数が呼び出された頻度や、どの引数が渡されたかを追跡するコードを含む新しいクラスを手動で作成する場合に比べて、定型的な作業を減らすことができます。

インタラクション・テストはモッキングと呼ばれることもあります。本章ではこの用語を避けていますが、これはモッキング・フレームワークと混同される可能性があるからです。モッキング・フレームワークはインタラクション・テストだけでなくスタブにも使用できます。

この章で後述するように、インタラクション・テストは特定の状況では有用ですが、使いすぎるとテストがもろくなってしまうので、可能な限り避けたほうがよいでしょう。

## 実際の実装

テストダブルは非常に貴重なテストツールですが、私たちがテストで最初に選ぶのは、テスト対象のシステムの依存関係の実際の実装、つまり本番コードで使用されているのと同じ実装を使用することです。テストは、本番環境で実行されるのと同じようにコードを実行することで、より高い忠実度を得ることができますが、実際の実装を使用することでこれを実現することができます。

Googleでは、モッキングフレームワークを使いすぎると、実際の実装と同期しない反復的なコードでテストが汚染され、リファクタリングが困難になる傾向が見られたため、時間をかけて実際の実装を優先するようになりました。このトピックについては、この章の後半で詳しく説明します。

テストにおいて実際の実装を優先することは、古典的なテストとして知られています。また、テストのスタイルには、本物の実装ではなく、モッキング・フレームワークを使用することを優先するモキスト・テストというものがあります。ソフトウェア業界にはモックテストを実践している人がいますが（最初にモックフレームワークを開発した人もいます）、Googleでは、このスタイルのテストを拡張するのは難しいと考えています。テスト対象のシステムを設計する際、エンジニアは厳格なガイドラインに従う必要がありますが、Googleのほとんどのエンジニアのデフォルトの行動は、古典的なテストスタイルに適した方法でコードを書くことでした。

### 分離よりも現実性を優先する

依存関係に実際の実装を使用することで、実際の実装のコードがすべてテストで実行されることになり、テスト対象のシステムがより現実的になります。一方、テストダブ ルを利用したテストでは、テスト対象のシステムをその依存関係から分離し、テスト対象のシステムの依存関係にあるコードをテストで実行しないようにします。

現実的なテストは、テスト対象のシステムが正しく動作していることをより確信させてくれるからです。ユニットテストがテストダブりに依存しすぎている場合、エンジニアが同じレベルの信頼性を得るためには、統合テストを実行したり、その機能が期待通りに動作していることを手動で検証したりする必要があります。このような余分な作業を行うと、開発速度が低下するだけでなく、ユニットテストの実行に比べて時間がかかりすぎるために、エンジニアがこれらの作業を完全に省略してしまうと、バグがすり抜けてしまう可能性があります。

クラスのすべての依存関係をテストダブルに置き換えることで、テスト対象のシステムを、作者がたまたまクラスに直接入れた実装に恣意的に隔離し、たまたま別のクラスにある実装を除外することができます。しかし、良いテストは実装に依存しないものでなければなりません。つまり、実装がどのように構成されているかではなく、テストされるAPIの観点から書かれるべきなのです。

実際の実装を使用すると、実際の実装にバグがあった場合にテストが失敗することがあります。これは良いことです。なぜなら、テストが失敗するということは、あなたのコードが本番で正しく動作しないことを意味するからです。実際の実装にバグがあると、その実装を使用している他のテストも失敗するため、テストの失敗が連鎖的に発生することがあります。しかし、継続的インテグレーション(CI)システムなどの優れた開発ツールを使えば、失敗の原因となった変更を簡単に突き止めることができます。

-----

#### ケーススタディ @DoNotMock

Googleでは、モッキング・フレームワークに過度に依存したテストを十分に見てきたため、Javaに`@DoNotMock`アノテーションを作成することになり、ErrorProne静的解析ツールの一部として利用できるようになりました。このアノテーションは、APIの所有者が「この型は、より良い代替手段が存在するため、モックされるべきではない」と宣言する方法です。

モッキングフレームワークを使って、例13-10のように `@DoNotMock` とアノテーションされたクラスやインターフェイスのインスタンスを作成しようとすると、実際の実装や偽物など、より適切なテスト戦略を使うように指示するエラーが表示されます。このアノテーションは、そのまま使用できるほど単純な値のオブジェクトや、 よくできた偽物が用意されている API によく使われます。

例 13-10. DoNotMock` アノテーション
```java
@DoNotMock("Use SimpleQuery.create() instead of mocking.")
public abstract class Query {
  public abstract String getQueryValue();
}
```

なぜAPIオーナーが気にするのか？一言で言えば、APIの所有者が時間をかけて実装を変更する能力が著しく制限されるからです。後述するように、スタブやインタラクション・テストにモッキング・フレームワークを使用すると、APIが提供する動作が重複してしまいます。

APIの所有者がそのAPIを変更しようとすると、Googleのコードベース全体で何千回、何万回もモックされていることに気づくかもしれません。これらのテストダブルは、モックされている型のAPIコントラクトに違反する動作を示す可能性が非常に高い。例えば、絶対にNULLを返してはいけないメソッドに対してNULLを返すなど。テストが本物の実装または偽物を使用していた場合、APIの所有者は、まず何千もの欠陥のあるテストを修正することなく、その実装に変更を加えることができる。

-----

### 実際の実装を使用するタイミングを決定する方法

実際の実装は、高速で決定性があり、依存関係が単純である場合に好まれます。例えば、値のオブジェクトには実数実装を使用する必要があります。例えば、金額、日付、地理的な住所、リストやマップなどのコレクションクラスなどです。

しかし、より複雑なコードでは、実際の実装を使用することができない場合もあります。実際の実装とテストダブルのどちらを使うかは、トレードオフの関係にあるため、正確な答えはないかもしれませんが、以下のような点を考慮する必要があります。

#### 実行時間

ユニットテストの最も重要な品質のひとつは、高速であることです。コードが機能しているかどうかについて迅速なフィードバックを得られるように、開発中に継続的にテストを実行できるようにしたいものです (また、CI システムで実行したときにも迅速に終了するようにしたいものです)。その結果、実際の実装が遅い場合には、テストダブルが非常に有効になります。

ユニットテストの遅さとは？実際の実装が、個々のテストケースの実行時間に1ミリ秒を加えたとしても、それを遅いと分類する人はほとんどいないでしょう。しかし、10ミリ秒、100ミリ秒、1秒......と増えていったらどうでしょう？

正確な答えはありません。エンジニアが生産性の低下を感じているかどうかや、実際の実装を使用しているテストの数にもよります（テストケースが5つの場合は1つのテストケースにつき1秒の追加が妥当かもしれませんが、500つの場合はそうではありません）。境界線上の状況では、実際の実装が遅すぎて使えなくなるまでは、実際の実装を使用した方がシンプルであることが多いですが、その時点でテストを更新して代わりにテストダブルを使用することができます。

また、テストを並列化することで、実行時間を短縮することができます。Googleのテストインフラでは、テストスイート内のテストを分割して複数のサーバーで実行することが容易になっています。これにより、CPU時間のコストは増加しますが、開発者の時間を大幅に節約することができます。この点については第18章で詳しく説明します。

もうひとつのトレードオフについて説明します。実際の実装を使用すると、 テストが実際の実装とその依存関係のすべてを構築する必要があるため、 ビルド時間が長くなります。Bazel のような拡張性の高いビルドシステムを使用すると、変更されていないビルド成果物がキャッシュされるので便利です。

#### 決定論

テスト対象のシステムのあるバージョンについて、テストを実行すると常に同じ結果になる場合、つまり、テストが常に合格または常に不合格である場合、テストは決定論的である。対照的に、テスト対象のシステムが変更されていなくても、テストの結果が変わる可能性がある場合、テストは非決定論的です。

テストの非決定性はフレーキー性につながります。つまり、テスト対象のシステムに変更がなくても、テストが時々失敗することがあります。第11章で述べたように、開発者がテストの結果を信用せず、失敗を無視するようになると、テストスイートの健全性が損なわれます。実際の実装を使用してもフレーキーがめったに起こらないのであれば、技術者への混乱が少ないため、対応を必要としないかもしれません。しかし、フレーキーが頻繁に発生する場合は、実際の実装をテストダブルに置き換えるべきかもしれません。そうすることでテストの忠実度が向上するからです。

実際の実装は、テストダブルスに比べてはるかに複雑であるため、非決定性になる可能性が高くなります。たとえば、マルチスレッドを利用した実際の実装では、スレッドの実行順序によってテスト対象のシステムの出力が異なるため、テストが失敗することがあります。

不確定性の一般的な原因は、密閉されていないコードです。つまり、テストの制御対象外である外部サービスに依存しているコードです。例えば、HTTP サーバーから Web ページのコンテンツを読み込もうとするテストは、サーバーが過負荷状態になったり、Web ページのコンテンツが変更されたりすると失敗する可能性があります。代わりに、テストダブルを使用して、テストが外部のサーバーに依存しないようにする必要があります。テストダブルの使用が困難な場合は、サーバーのハーメチックインスタンスを使用するという方法もあります。ハーメチックインスタンスは、そのライフサイクルがテストによって制御されます。ハーメチック・インスタンスについては、次の章で詳しく説明します。

非決定論のもう一つの例は、システムクロックに依存するコードです。テスト対象のシステムの出力は、現在の時刻によって異なる可能性があります。システムクロックに頼る代わりに、特定の時間をハードコードしたテストダブルを使用することができます。

#### 依存関係の構築

実際の実装を使用する際には、その依存関係のすべてを構築する必要があります。たとえば、あるオブジェクトは、そのオブジェクトが依存しているすべてのオブジェクト、その依存しているオブジェクトが依存しているすべてのオブジェクトなど、依存関係のツリー全体を構築する必要があります。テストダブルは依存関係を持たないことが多いので、テストダブルの構築は実際の実装の構築に比べてはるかにシンプルになります。

極端な例として、次のコードスニペットのオブジェクトをテストで作成しようとした場合を考えてみましょう。個々のオブジェクトをどのように構築するかを決定するには時間がかかります。また、これらのオブジェクトのコンストラクタのシグネチャが変更された場合、テストを更新する必要があるため、常にメンテナンスが必要になります。

```
 Foo foo = new Foo(new A(new B(new C()), new D()), new E(), ..., new Z());
```

代わりにテストダブルを使いたくなるのは、テストダブルの構築が簡単だからです。たとえば、Mockito モッキング・フレームワークを使用した場合、テストダブルを作成するには次のような手順が必要です。

```
@Mock Foo mockFoo;
```

このテストダブルの作成ははるかに簡単ですが、このセクションの前半で説明したように、実際の実装を使用することには大きな利点があります。また、このようにテストダブルを使いすぎると、後述するように大きなマイナス面もあります。そのため、実際の実装とテストダブルのどちらを使用するかを検討する際には、トレードオフを行う必要があります。

テストでオブジェクトを手動で構築するのではなく、 ファクトリーメソッドや自動化された依存性注入など、 本番コードで使用するのと同じオブジェクト構築コードを使用するのが理想的なソリューションです。テストのユースケースをサポートするためには、オブジェクト構築コードは、プロダクションで使用される実装をハードコーディングするのではなく、テストダブルを使用できるような柔軟性が必要です。

## フェイク

テストの中で本物の実装を使うことができない場合、最良の選択肢はしばしば偽物を代わりに使うことです。偽物は本物の実装と同じように動作するので、他のテスト手法よりも好まれます。テスト対象のシステムは、本物の実装と偽物のどちらとやりとりしているのか分からないはずです。例 13-11 は偽物のファイルシステムを示しています。

Example 13-11. A fake file system
```java
// This fake implements the FileSystem interface. This interface is also
// used by the real implementation.
public class FakeFileSystem implements FileSystem {
  // Stores a map of file name to file contents. The files are stored in
  // memory instead of on disk since tests shouldn’t need to do disk I/O.
  private Map<String, String> files = new HashMap<>();
  @Override
  public void writeFile(String fileName, String contents) {
    // Add the file name and contents to the map.
    files.add(fileName, contents);
  }
  @Override
  public String readFile(String fileName) {
    String contents = files.get(fileName);
    // The real implementation will throw this exception if the
    // file isn’t found, so the fake must throw it too.
    if (contents == null) { throw new FileNotFoundException(fileName); }
    return contents;
  }
}
```

### Why Are Fakes Important?

Fakes can be a powerful tool for testing: they execute quickly and allow you to effectively test your code without the drawbacks of using real implementations.
A single fake has the power to radically improve the testing experience of an API. If you scale that to a large number of fakes for all sorts of APIs, fakes can provide an enormous boost to engineering velocity across a software organization.
At the other end of the spectrum, in a software organization where fakes are rare, velocity will be slower because engineers can end up struggling with using real implementations that lead to slow and flaky tests. Or engineers might resort to other test double techniques such as stubbing or interaction testing, which, as we’ll examine later in this chapter, can result in tests that are unclear, brittle, and less effective.

### When Should Fakes Be Written?

A fake requires more effort and more domain experience to create because it needs to behave similarly to the real implementation. A fake also requires maintenance: whenever the behavior of the real implementation changes, the fake must also be updated to match this behavior. Because of this, the team that owns the real implementation should write and maintain a fake.
If a team is considering writing a fake, a trade-off needs to be made on whether the productivity improvements that will result from the use of the fake outweigh the costs of writing and maintaining it. If there are only a handful of users, it might not be worth their time, whereas if there are hundreds of users, it can result in an obvious productivity improvement.
To reduce the number of fakes that need to be maintained, a fake should typically be created only at the root of the code that isn’t feasible for use in tests. For example, if a database can’t be used in tests, a fake should exist for the database API itself rather than for each class that calls the database API.
Maintaining a fake can be burdensome if its implementation needs to be duplicated across programming languages, such as for a service that has client libraries that allow the service to be invoked from different languages. One solution for this case is to create a single fake service implementation and have tests configure the client libraries to send requests to this fake service. This approach is more heavyweight compared to having the fake written entirely in memory because it requires the test to communicate across processes. However, it can be a reasonable trade-off to make, as long as the tests can still execute quickly.

### The Fidelity of Fakes

Perhaps the most important concept surrounding the creation of fakes is fidelity; in other words, how closely the behavior of a fake matches the behavior of the real implementation. If the behavior of a fake doesn’t match the behavior of the real implementation, a test using that fake is not useful --- a test might pass when the fake is used, but this same code path might not work properly in the real implementation.
Perfect fidelity is not always feasible. After all, the fake was necessary because the real implementation wasn’t suitable in one way or another. For example, a fake database would usually not have fidelity to a real database in terms of hard drive storage because the fake would store everything in memory.
Primarily, however, a fake should maintain fidelity to the API contracts of the real implementation. For any given input to an API, a fake should return the same output and perform the same state changes of its corresponding real implementation. For example, for a real implementation of database.save(itemId), if an item is successfully saved when its ID does not yet exist but an error is produced when the ID already exists, the fake must conform to this same behavior.
One way to think about this is that the fake must have perfect fidelity to the real implementation, but only from the perspective of the test. For example, a fake for a hashing API doesn’t need to guarantee that the hash value for a given input is exactly the same as the hash value that is generated by the real implementation --- tests likely don’t care about the specific hash value, only that the hash value is unique for a given input. If the contract of the hashing API doesn’t make guarantees of what specific hash values will be returned, the fake is still conforming to the contract even if it doesn’t have perfect fidelity to the real implementation.
Other examples where perfect fidelity typically might not be useful for fakes include latency and resource consumption. However, a fake cannot be used if you need to explicitly test for these constraints (e.g., a performance test that verifies the latency of a function call), so you would need to resort to other mechanisms, such as by using a real implementation instead of a fake.
A fake might not need to have 100% of the functionality of its corresponding real implementation, especially if such behavior is not needed by most tests (e.g., error handling code for rare edge cases). It is best to have the fake fail fast in this case; for example, raise an error if an unsupported code path is executed. This failure communicates to the engineer that the fake is not appropriate in this situation.

### Fakes Should Be Tested

A fake must have its own tests to ensure that it conforms to the API of its corresponding real implementation. A fake without tests might initially provide realistic behavior, but without tests, this behavior can diverge over time as the real implementation evolves.
One approach to writing tests for fakes involves writing tests against the API’s public interface and running those tests against both the real implementation and the fake (these are known as contract tests). The tests that run against the real implementation will likely be slower, but their downside is minimized because they need to be run only by the owners of the fake.

### What to Do If a Fake Is Not Available

If a fake is not available, first ask the owners of the API to create one. The owners might not be familiar with the concept of fakes, or they might not realize the benefit they provide to users of an API.
If the owners of an API are unwilling or unable to create a fake, you might be able to write your own. One way to do this is to wrap all calls to the API in a single class and then create a fake version of the class that doesn’t talk to the API. Doing this can also be much simpler than creating a fake for the entire API because often you’ll need to use only a subset of the API’s behavior anyway. At Google, some teams have even contributed their fake to the owners of the API, which has allowed other teams to benefit from the fake.
Finally, you could decide to settle on using a real implementation (and deal with the trade-offs of real implementations that are mentioned earlier in this chapter), or resort to other test double techniques (and deal with the trade-offs that we will mention later in this chapter).
In some cases, you can think of a fake as an optimization: if tests are too slow using a real implementation, you can create a fake to make them run faster. But if the speedup from a fake doesn’t outweigh the work it would take to create and maintain the fake, it would be better to stick with using the real implementation.

## Stubbing

As discussed earlier in this chapter, stubbing is a way for a test to hardcode behavior for a function that otherwise has no behavior on its own. It is often a quick and easy way to replace a real implementation in a test. For example, the code in Example 13-12 uses stubbing to simulate the response from a credit card server.

Example 13-12. Using stubbing to simulate responses
```java
@Test public void getTransactionCount() {
  transactionCounter = new TransactionCounter(mockCreditCardServer);
  // Use stubbing to return three transactions.
  when(mockCreditCardServer.getTransactions()).thenReturn(
      newList(TRANSACTION_1, TRANSACTION_2, TRANSACTION_3));
  assertThat(transactionCounter.getTransactionCount()).isEqualTo(3);
}
```

### The Dangers of Overusing Stubbing

Because stubbing is so easy to apply in tests, it can be tempting to use this technique anytime it’s not trivial to use a real implementation. However, overuse of stubbing can result in major losses in productivity for engineers who need to maintain these tests.

#### Tests become unclear

Stubbing involves writing extra code to define the behavior of the functions being stubbed. Having this extra code detracts from the intent of the test, and this code can be difficult to understand if you’re not familiar with the implementation of the system under test.
A key sign that stubbing isn’t appropriate for a test is if you find yourself mentally stepping through the system under test in order to understand why certain functions in the test are stubbed.

#### Tests become brittle

Stubbing leaks implementation details of your code into your test. When implementation details in your production code change, you’ll need to update your tests to reflect these changes. Ideally, a good test should need to change only if user-facing behavior of an API changes; it should remain unaffected by changes to the API’s implementation.

#### Tests become less effective

With stubbing, there is no way to ensure the function being stubbed behaves like the real implementation, such as in a statement like that shown in the following snippet that hardcodes part of the contract of the `add()` method (“If 1 and 2 are passed in, 3 will be returned”):

```
when(stubCalculator.add(1, 2)).thenReturn(3);
```

Stubbing is a poor choice if the system under test depends on the real implementation’s contract because you will be forced to duplicate the details of the contract, and there is no way to guarantee that the contract is correct (i.e., that the stubbed function has fidelity to the real implementation).

Additionally, with stubbing there is no way to store state, which can make it difficult to test certain aspects of your code. For example, if you call `database.save(item)` on either a real implementation or a fake, you might be able to retrieve the item by calling `database.get(item.id())` given that both of these calls are accessing internal state, but with stubbing, there is no way to do this.

#### An example of overusing stubbing

Example 13-13 illustrates a test that overuses stubbing.

Example 13-13. Overuse of stubbing
```java
@Test public void creditCardIsCharged() {
  // Pass in test doubles that were created by a mocking framework.
  paymentProcessor =
      new PaymentProcessor(mockCreditCardServer, mockTransactionProcessor);
  // Set up stubbing for these test doubles.
  when(mockCreditCardServer.isServerAvailable()).thenReturn(true);
  when(mockTransactionProcessor.beginTransaction()).thenReturn(transaction);
  when(mockCreditCardServer.initTransaction(transaction)).thenReturn(true);
  when(mockCreditCardServer.pay(transaction, creditCard, 500))
      .thenReturn(false);
  when(mockTransactionProcessor.endTransaction()).thenReturn(true);
  // Call the system under test.
  paymentProcessor.processPayment(creditCard, Money.dollars(500));
  // There is no way to tell if the pay() method actually carried out the
  // transaction, so the only thing the test can do is verify that the
  // pay() method was called.
  verify(mockCreditCardServer).pay(transaction, creditCard, 500);
}
```

Example 13-14 rewrites the same test but avoids using stubbing. Notice how the test is shorter and that implementation details (such as how the transaction processor is used) are not exposed in the test. No special setup is needed because the credit card server knows how to behave.

Example 13-14. Refactoring a test to avoid stubbing
```java
@Test public void creditCardIsCharged() {
  paymentProcessor =
      new PaymentProcessor(creditCardServer, transactionProcessor);
  // Call the system under test.
  paymentProcessor.processPayment(creditCard, Money.dollars(500));
  // Query the credit card server state to see if the payment went through.
  assertThat(creditCardServer.getMostRecentCharge(creditCard))
      .isEqualTo(500);
}
```

We obviously don’t want such a test to talk to an external credit card server, so a fake credit card server would be more suitable. If a fake isn’t available, another option is to use a real implementation that talks to a hermetic credit card server, although this will increase the execution time of the tests. (We explore hermetic servers in the next chapter.)

### When Is Stubbing Appropriate?

Rather than a catch-all replacement for a real implementation, stubbing is appropriate when you need a function to return a specific value to get the system under test into a certain state, such as Example 13-12 that requires the system under test to return a non-empty list of transactions. Because a function’s behavior is defined inline in the test, stubbing can simulate a wide variety of return values or errors that might not be possible to trigger from a real implementation or a fake.
To ensure its purpose is clear, each stubbed function should have a direct relationship with the test’s assertions. As a result, a test typically should stub out a small number of functions because stubbing out many functions can lead to tests that are less clear. A test that requires many functions to be stubbed can be a sign that stubbing is being overused, or that the system under test is too complex and should be refactored.
Note that even when stubbing is appropriate, real implementations or fakes are still preferred because they don’t expose implementation details and they give you more guarantees about the correctness of the code compared to stubbing. But stubbing can be a reasonable technique to use, as long as its usage is constrained so that tests don’t become overly complex.

## Interaction Testing

As discussed earlier in this chapter, interaction testing is a way to validate how a function is called without actually calling the implementation of the function.
Mocking frameworks make it easy to perform interaction testing. However, to keep tests useful, readable, and resilient to change, it’s important to perform interaction testing only when necessary.

### Prefer State Testing Over Interaction Testing

In contrast to interaction testing, it is preferred to test code through state testing.
With state testing, you call the system under test and validate that either the correct value was returned or that some other state in the system under test was properly changed. Example 13-15 presents an example of state testing.

Example 13-15. State testing
```java
@Test public void sortNumbers() {
  NumberSorter numberSorter = new NumberSorter(quicksort, bubbleSort);
  // Call the system under test.
  List sortedList = numberSorter.sortNumbers(newList(3, 1, 2));
  // Validate that the returned list is sorted. It doesn’t matter which
  // sorting algorithm is used, as long as the right result was returned.
  assertThat(sortedList).isEqualTo(newList(1, 2, 3));
}
```

Example 13-16 illustrates a similar test scenario but instead uses interaction testing. Note how it’s impossible for this test to determine that the numbers are actually sorted, because the test doubles don’t know how to sort the numbers --- all it can tell you is that the system under test tried to sort the numbers.

Example 13-16. Interaction testing
```java
@Test public void sortNumbers_quicksortIsUsed() {
  // Pass in test doubles that were created by a mocking framework.
  NumberSorter numberSorter =
      new NumberSorter(mockQuicksort, mockBubbleSort);
  // Call the system under test.
  numberSorter.sortNumbers(newList(3, 1, 2));

  // Validate that numberSorter.sortNumbers() used quicksort. The test
  // will fail if mockQuicksort.sort() is never called (e.g., if
  // mockBubbleSort is used) or if it’s called with the wrong arguments.
  verify(mockQuicksort).sort(newList(3, 1, 2));
}
```

At Google, we’ve found that emphasizing state testing is more scalable; it reduces test brittleness, making it easier to change and maintain code over time.
The primary issue with interaction testing is that it can’t tell you that the system under test is working properly; it can only validate that certain functions are called as expected. It requires you to make an assumption about the behavior of the code; for example, “If database.save(item) is called, we assume the item will be saved to the database.” State testing is preferred because it actually validates this assumption (such as by saving an item to a database and then querying the database to validate that the item exists).
Another downside of interaction testing is that it utilizes implementation details of the system under test --- to validate that a function was called, you are exposing to the test that the system under test calls this function. Similar to stubbing, this extra code makes tests brittle because it leaks implementation details of your production code into tests. Some people at Google jokingly refer to tests that overuse interaction testing as change-detector tests because they fail in response to any change to the production code, even if the behavior of the system under test remains unchanged.


### When Is Interaction Testing Appropriate?

There are some cases for which interaction testing is warranted:

- You cannot perform state testing because you are unable to use a real implementation or a fake (e.g., if the real implementation is too slow and no fake exists). As a fallback, you can perform interaction testing to validate that certain functions are called. Although not ideal, this does provide some basic level of confidence that the system under test is working as expected.
- Differences in the number or order of calls to a function would cause undesired behavior. Interaction testing is useful because it could be difficult to validate this behavior with state testing. For example, if you expect a caching feature to reduce the number of calls to a database, you can verify that the database object is not accessed more times than expected. Using Mockito, the code might look similar to this:

```
        verify(databaseReader, atMostOnce()).selectRecords();
```

Interaction testing is not a complete replacement for state testing. If you are not able to perform state testing in a unit test, strongly consider supplementing your test suite with larger-scoped tests that do perform state testing. For instance, if you have a unit test that validates usage of a database through interaction testing, consider adding an integration test that can perform state testing against a real database. Larger-scope testing is an important strategy for risk mitigation, and we discuss it in the next chapter.

### Best Practices for Interaction Testing

When performing interaction testing, following these practices can reduce some of the impact of the aforementioned downsides.

#### Prefer to perform interaction testing only for state-changing functions

When a system under test calls a function on a dependency, that call falls into one of two categories:

- State-changing
  - Functions that have side effects on the world outside the system under test. Examples: `sendEmail()`, `saveRecord()`, `logAccess()`.
- Non-state-changing
  - Functions that don’t have side effects; they return information about the world outside the system under test and don’t modify anything. Examples: `getUser()`, `findResults()`, `readFile()`.

In general, you should perform interaction testing only for functions that are state- changing. Performing interaction testing for non-state-changing functions is usually redundant given that the system under test will use the return value of the function to do other work that you can assert. The interaction itself is not an important detail for correctness, because it has no side effects.
Performing interaction testing for non-state-changing functions makes your test brittle because you’ll need to update the test anytime the pattern of interactions changes. It also makes the test less readable given that the additional assertions make it more difficult to determine which assertions are important for ensuring correctness of the code. By contrast, state-changing interactions represent something useful that your code is doing to change state somewhere else.
Example 13-17 demonstrates interaction testing on both state-changing and non- state-changing functions.

Example 13-17. State-changing and non-state-changing interactions
```java
@Test public void grantUserPermission() {
  UserAuthorizer userAuthorizer =
      new UserAuthorizer(mockUserService, mockPermissionDatabase);
  when(mockPermissionService.getPermission(FAKE_USER)).thenReturn(EMPTY);

  // Call the system under test.
  userAuthorizer.grantPermission(USER_ACCESS);

  // addPermission() is state-changing, so it is reasonable to perform
  // interaction testing to validate that it was called.
  verify(mockPermissionDatabase).addPermission(FAKE_USER, USER_ACCESS);

  // getPermission() is non-state-changing, so this line of code isn’t
  // needed. One clue that interaction testing may not be needed:
  // getPermission() was already stubbed earlier in this test.
  verify(mockPermissionDatabase).getPermission(FAKE_USER);
}
```

#### Avoid overspecification

In Chapter 12, we discuss why it is useful to test behaviors rather than methods. This means that a test method should focus on verifying one behavior of a method or class rather than trying to verify multiple behaviors in a single test.
When performing interaction testing, we should aim to apply the same principle by avoiding overspecifying which functions and arguments are validated. This leads to tests that are clearer and more concise. It also leads to tests that are resilient to changes made to behaviors that are outside the scope of each test, so fewer tests will fail if a change is made to a way a function is called.
Example 13-18 illustrates interaction testing with overspecification. The intention of the test is to validate that the user’s name is included in the greeting prompt, but the test will fail if unrelated behavior is changed.

Example 13-18. Overspecified interaction tests
```java
@Test public void displayGreeting_renderUserName() {
  when(mockUserService.getUserName()).thenReturn("Fake User");
  userGreeter.displayGreeting(); // Call the system under test.

  // The test will fail if any of the arguments to setText() are changed.
  verify(userPrompt).setText("Fake User", "Good morning!", "Version 2.1");

  // The test will fail if setIcon() is not called, even though this
  // behavior is incidental to the test since it is not related to
  // validating the user name.
  verify(userPrompt).setIcon(IMAGE_SUNSHINE);
}
```

Example 13-19 illustrates interaction testing with more care in specifying relevant arguments and functions. The behaviors being tested are split into separate tests, and each test validates the minimum amount necessary for ensuring the behavior it is testing is correct.

Example 13-19. Well-specified interaction tests
```java
@Test public void displayGreeting_renderUserName() {
  when(mockUserService.getUserName()).thenReturn("Fake User");
  userGreeter.displayGreeting(); // Call the system under test.
  verify(userPrompter).setText(eq("Fake User"), any(), any());
}
@Test public void displayGreeting_timeIsMorning_useMorningSettings() {
  setTimeOfDay(TIME_MORNING);
  userGreeter.displayGreeting(); // Call the system under test.
  verify(userPrompt).setText(any(), eq("Good morning!"), any());
  verify(userPrompt).setIcon(IMAGE_SUNSHINE);
}
```

## Conclusion

We’ve learned that test doubles are crucial to engineering velocity because they can help comprehensively test your code and ensure that your tests run fast. On the other hand, misusing them can be a major drain on productivity because they can lead to tests that are unclear, brittle, and less effective. This is why it’s important for engineers to understand the best practices for how to effectively apply test doubles.
There is often no exact answer regarding whether to use a real implementation or a test double, or which test double technique to use. An engineer might need to make some trade-offs when deciding the proper approach for their use case.
Although test doubles are great for working around dependencies that are difficult to use in tests, if you want to maximize confidence in your code, at some point you still want to exercise these dependencies in tests. The next chapter will cover larger-scope testing, for which these dependencies are used regardless of their suitability for unit tests; for example, even if they are slow or nondeterministic.

## TL;DRs

- A real implementation should be preferred over a test double.
- A fake is often the ideal solution if a real implementation can’t be used in a test.
- Overuse of stubbing leads to tests that are unclear and brittle.
- Interaction testing should be avoided when possible: it leads to tests that are brittle because it exposes implementation details of the system under test.












