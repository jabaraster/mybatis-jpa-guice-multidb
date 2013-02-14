/**
 * 
 */
package jabara.sample.model;

import jabara.general.ArgUtil;
import jabara.jpa.PersistenceXmlPropertyNames;
import jabara.jpa_guice.MultiPersistenceUnitJpaModule;
import jabara.mybatis_guice.DataSourceMyBatisModule;
import jabara.mybatis_guice.MultiDataSourceMyBatisModule;
import jabara.sample.service.impl.IEmployeeMapper;
import jabara.sample.service.impl.IKeywordMapper;
import jabara.sample.service.impl.MainDao;
import jabara.sample.service.impl.MainTransactional;
import jabara.sample.service.impl.SubDao;
import jabara.sample.service.impl.SubTransactional;

import java.util.HashMap;
import java.util.Map;

import org.postgresql.Driver;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author jabaraster
 */
public class DI {

    private static Injector _injector = createInjector();

    /**
     * @param pType -
     * @param <T> -
     * @return -
     */
    public static <T> T get(final Class<T> pType) {
        ArgUtil.checkNull(pType, "pType"); //$NON-NLS-1$
        return _injector.getInstance(pType);
    }

    @SuppressWarnings({ "nls" })
    private static Injector createInjector() {
        final MultiPersistenceUnitJpaModule mainJpaModule = new MultiPersistenceUnitJpaModule( //
                "mainPersistenceUnit" //
                , MainDao.class //
                , MainTransactional.class);
        final MultiPersistenceUnitJpaModule subJpaModule = new MultiPersistenceUnitJpaModule( //
                "subPersistenceUnit" //
                , SubDao.class //
                , SubTransactional.class);
        final MultiDataSourceMyBatisModule mainMyBatisModule = new MultiDataSourceMyBatisModule("jdbc/mainDataSource" //
                , IKeywordMapper.class //
        );
        final MultiDataSourceMyBatisModule subMyBatisModule = new MultiDataSourceMyBatisModule("jdbc/subDataSource" //
                , IEmployeeMapper.class //
        );
        return Guice.createInjector( //
                mainJpaModule //
                , subJpaModule //
                , mainMyBatisModule //
                , subMyBatisModule //
                );
    }

    @SuppressWarnings({ "nls", "unused" })
    private static Map<String, String> createMainConnectionProperties() {
        final Map<String, String> map = new HashMap<String, String>();

        putDouble(map, PersistenceXmlPropertyNames.DRIVER, "JDBC.driver", Driver.class.getName());
        putDouble(map, PersistenceXmlPropertyNames.JDBC_URL, "JDBC.url", "jdbc:postgresql://localhost:5432/postgres");
        putDouble(map, PersistenceXmlPropertyNames.JDBC_USER, "JDBC.username", "postgres");
        putDouble(map, PersistenceXmlPropertyNames.JDBC_USER, "JDBC.password", "postgres");

        DataSourceMyBatisModule.putMyBatisProperty(map, "stage");

        return map;
    }

    private static void putDouble(final Map<String, String> pMap, final String pKey1, final String pKey2, final String pValue) {
        pMap.put(pKey1, pValue);
        pMap.put(pKey2, pValue);
    }
}
