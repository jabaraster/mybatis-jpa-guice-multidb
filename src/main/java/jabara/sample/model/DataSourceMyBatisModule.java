package jabara.sample.model;

import jabara.general.ArgUtil;
import jabara.general.ExceptionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;

import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * @author jabaraster
 */
public class DataSourceMyBatisModule extends MyBatisModule {

    private final String         dataSourceJndiName;
    private final List<Class<?>> mapperClasses;

    /**
     * @param pDataSourceJndiName JNDIから{@link DataSource}をルックアップするときの名前.
     * @param pMapperClasses MyBatisのマッパクラスのリスト.
     */
    public DataSourceMyBatisModule(final String pDataSourceJndiName, final Class<?>... pMapperClasses) {
        ArgUtil.checkNullOrEmpty(pDataSourceJndiName, "pDataSourceJndiName"); //$NON-NLS-1$
        ArgUtil.checkNull(pMapperClasses, "pMapperClasses"); //$NON-NLS-1$
        this.dataSourceJndiName = pDataSourceJndiName;
        this.mapperClasses = Arrays.asList(pMapperClasses);
    }

    /**
     * @param pDataSourceJndiName JNDIから{@link DataSource}をルックアップするときの名前.
     * @param pMapperClasses MyBatisのマッパクラスのリスト.
     */
    public DataSourceMyBatisModule(final String pDataSourceJndiName, final Collection<Class<?>> pMapperClasses) {
        ArgUtil.checkNullOrEmpty(pDataSourceJndiName, "pDataSourceJndiName"); //$NON-NLS-1$
        ArgUtil.checkNull(pMapperClasses, "pMapperClasses"); //$NON-NLS-1$
        this.mapperClasses = new ArrayList<Class<?>>(pMapperClasses);
        this.dataSourceJndiName = pDataSourceJndiName;
    }

    /**
     * @return the dataSourceJndiName
     */
    public String getDataSourceJndiName() {
        return this.dataSourceJndiName;
    }

    /**
     * @return the mapperClasses
     */
    public List<Class<?>> getMapperClasses() {
        return new ArrayList<Class<?>>(this.mapperClasses);
    }

    /**
     * @see org.mybatis.guice.AbstractMyBatisModule#initialize()
     */
    @Override
    protected void initialize() {
        final DataSource ds = lookupDataSource();

        try {
            System.out.println(ds.getConnection());
            System.out.println(ds.getConnection());
        } catch (final SQLException e) {
            jabara.Debug.write(e);
        }

        bindDataSourceProvider(new Provider<DataSource>() {
            @Override
            public DataSource get() {
                return ds;
            }
        });
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClasses(this.mapperClasses);

        final Map<String, String> map = putMyBatisProperty(new HashMap<String, String>());
        Names.bindProperties(binder(), map);
    }

    private DataSource lookupDataSource() {
        try {
            return InitialContext.doLookup(this.dataSourceJndiName);
        } catch (final NamingException e) {
            throw ExceptionUtil.rethrow(e);
        }
    }

    /**
     * @param pMap
     * @return 引数のpMapそのもの.
     */
    static Map<String, String> putMyBatisProperty(final Map<String, String> pMap) {
        pMap.put("mybatis.environment.id", "test"); //$NON-NLS-1$ //$NON-NLS-2$
        pMap.put("JDBC.autoCommit", Boolean.toString(false)); //$NON-NLS-1$
        return pMap;
    }
}