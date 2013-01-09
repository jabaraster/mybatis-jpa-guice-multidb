/**
 * 
 */
package jabara.sample.model;

import jabara.general.ArgUtil;
import jabara.general.IProducer;
import jabara.jpa.PersistenceXmlPropertyNames;
import jabara.jpa_guice.util.MultiPersistenceUnitJpaModule;
import jabara.sample.service.impl.IEmployeeMapper;
import jabara.sample.service.impl.MainDao;
import jabara.sample.service.impl.MainTransactional;
import jabara.sample.service.impl.SubDao;
import jabara.sample.service.impl.SubTransactional;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.postgresql.Driver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

/**
 * @author jabaraster
 */
public class DI {

    private static final Map<String, String> _mainConnectionProperties = createMainConnectionProperties();
    private static final Map<String, String> _subConnectionProperties  = createSubConnectionProperties();

    private static Injector                  _injector                 = createInjector();

    /**
     * @param pType -
     * @return -
     */
    public static <T> T get(final Class<T> pType) {
        ArgUtil.checkNull(pType, "pType"); //$NON-NLS-1$
        return _injector.getInstance(pType);
    }

    @SuppressWarnings({ "synthetic-access", "nls" })
    private static Injector createInjector() {
        return Guice.createInjector( //
                new MultiPersistenceUnitJpaModule("mainPersistenceUnit", new IProducer<Map<String, String>>() {
                    @Override
                    public Map<String, String> produce() {
                        return _mainConnectionProperties;
                    }
                }, MainDao.class, MainTransactional.class) //
                , new MultiPersistenceUnitJpaModule("subPersistenceUnit", new IProducer<Map<String, String>>() {
                    @Override
                    public Map<String, String> produce() {
                        return _subConnectionProperties;
                    }
                }, SubDao.class, SubTransactional.class) // //$NON-NLS-1$
                , new ExMyBatisModule() //
                );
    }

    @SuppressWarnings("nls")
    private static Map<String, String> createMainConnectionProperties() {
        final Map<String, String> map = new HashMap<String, String>();

        putDouble(map, PersistenceXmlPropertyNames.DRIVER, "JDBC.driver", Driver.class.getName());
        putDouble(map, PersistenceXmlPropertyNames.JDBC_URL, "JDBC.url", "jdbc:postgresql://localhost:5432/postgres");
        putDouble(map, PersistenceXmlPropertyNames.JDBC_USER, "JDBC.username", "postgres");
        putDouble(map, PersistenceXmlPropertyNames.JDBC_USER, "JDBC.password", "postgres");

        putMyBatisProperty(map);

        return map;
    }

    @SuppressWarnings("nls")
    private static Map<String, String> createSubConnectionProperties() {
        final Map<String, String> map = new HashMap<String, String>();

        putDouble(map, PersistenceXmlPropertyNames.DRIVER, "JDBC.driver", Driver.class.getName());
        putDouble(map, PersistenceXmlPropertyNames.JDBC_URL, "JDBC.url", "jdbc:postgresql://localhost:5432/rakeup");
        putDouble(map, PersistenceXmlPropertyNames.JDBC_USER, "JDBC.username", "postgres");
        putDouble(map, PersistenceXmlPropertyNames.JDBC_USER, "JDBC.password", "postgres");

        putMyBatisProperty(map);

        return map;
    }

    private static void putDouble(final Map<String, String> pMap, final String pKey1, final String pKey2, final String pValue) {
        pMap.put(pKey1, pValue);
        pMap.put(pKey2, pValue);
    }

    private static void putMyBatisProperty(final Map<String, String> pMap) {
        pMap.put("mybatis.environment.id", "test"); //$NON-NLS-1$ //$NON-NLS-2$
        pMap.put("JDBC.autoCommit", Boolean.toString(false)); //$NON-NLS-1$
    }

    private static class ExMyBatisModule extends MyBatisModule {

        @SuppressWarnings("synthetic-access")
        @Override
        protected void initialize() {
            bindDataSourceProviderType(PooledDataSourceProvider.class);
            bindTransactionFactoryType(JdbcTransactionFactory.class);
            addMapperClass(IEmployeeMapper.class);

            Names.bindProperties(binder(), _mainConnectionProperties);

            // TODO DataSourceをコンテナから指定する方法.
            // TODO またそれを、PersistenceUnitを環境に応じて切り替える方法.
        }
    }
}
