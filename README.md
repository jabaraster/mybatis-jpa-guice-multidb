このJavaプロジェクトは、複数のDBに接続剃る必要があるときに、MyBatisとJPAをGoogle Guiceと組み合わせて使うためのサンプルです。

## 前提・DataSourceの登録
DBの接続先毎に、JNDIに次の名前でDataSourceが登録されている必要があります。

* jdbc/mainDataSource
* jdbc/subDataSource

なおこのサンプルでは、JNDIへの登録はAppクラスで行なっています。  

## 前提・persistence.xmlの設定
persistence.xmlに２つのPersistenceUnitが定義されている必要があります。  
それぞれの接続先は、前出の２つのDataSourceになっています。  

* mainPersistenceUnit : 接続先はjdbc/mainDataSource
* subPersistenceUnit  : 接続先はjdbc/subDataSource


## JPAの場合

Google Guiceは、通常、```@Inject```アノテーションが付与された変数を検出したら、その変数の型に沿ったオブジェクトを自動でセットします。  
しかし複数の接続先がある状況では、オブジェクトが一意に定まらないため、```@Inject```に加えて識別子となるアノテーションを作る必要があります。  

サンプルコードを示します。  
次の点に注意しながらご覧下さい。

* コンストラクタに```@Inject```アノテーションが付与されている  
  これによりGoogle Guiceがオブジェクトを自動でセットする対象となります。  
* コンストラクタの引数に同じ型(```JpaDaoBase```)を取る  
* ```JpaDaoBase```型の変数の前に識別子となるアノテーション(```@MainDao```及び```@SubDao```)が付与されている  
  これによりGoogle Guiceは、適切な変数に適切なオブジェクトをセットすることが出来るようになります。  

```java
public class SomeServiceImpl implements ISomeService {

    private final JpaDaoBase      mainDao;
    private final JpaDaoBase      subDao;
    private final IKeywordMapper  keywordMapper;
    private final IEmployeeMapper employeeMapper;

    /**
     * @param pMainDao -
     * @param pSubDao -
     * @param pKeywordMapper -
     * @param pEmployeeMapper -
     */
    @Inject
    public SomeServiceImpl( //
            @MainDao final JpaDaoBase pMainDao //
            , @SubDao final JpaDaoBase pSubDao //
            , final IKeywordMapper pKeywordMapper //
            , final IEmployeeMapper pEmployeeMapper) {
        this.mainDao = pMainDao;
        this.subDao = pSubDao;
        this.keywordMapper = pKeywordMapper;
        this.employeeMapper = pEmployeeMapper;
    }

    /**
     * @see jabara.sample.service.ISomeService#run()
     */
    @Override
    @MainTransactional // main接続のトランザクション制御を行うことを指示します
    @SubTransactional  // sub接続のトランザクション制御を行うことを指示します
    public void run() {
        queryMainJpa();
        querySubJpa();
        queryMainMyBatis();
        querySubMyBatis();
    }
}
```

### 識別子となるアノテーションの作り方

サンプルコードを示します。  

```java
@BindingAnnotation
@Target({ FIELD, PARAMETER, METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MainDao {
    //
}
```

### Google Guiceに、適切なオブジェクトを示す方法

識別子となるアノテーションと、どのPersistenceUnitを使うかを紐付ける必要があります。  

サンプルコードを示します。

```java
final MultiPersistenceUnitJpaModule mainJpaModule = new MultiPersistenceUnitJpaModule( //
        "mainPersistenceUnit" //
        , MainDao.class //
        , MainTransactional.class);
```

上記コードの完全版は[DIクラス][]のソースをご覧下さい。

[DIクラス]: <https://github.com/jabaraster/mybatis-jpa-guice-multidb/blob/master/src/main/java/jabara/sample/model/DI.java>

## MyBatisの場合
（作成中・・・）
