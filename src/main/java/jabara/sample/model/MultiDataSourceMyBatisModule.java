package jabara.sample.model;

import jabara.general.ArgUtil;

import javax.sql.DataSource;

import com.google.inject.PrivateModule;

/**
 * @author jabaraster
 */
public class MultiDataSourceMyBatisModule extends PrivateModule {

    private final DataSourceMyBatisModule dataSourceMyBatisModule;

    /**
     * @param pDataSourceJndiName JNDIから{@link DataSource}をルックアップするときの名前.
     * @param pMapperClasses MyBatisのマッパクラスのリスト.
     */
    public MultiDataSourceMyBatisModule(final String pDataSourceJndiName, final Class<?>... pMapperClasses) {
        ArgUtil.checkNullOrEmpty(pDataSourceJndiName, "pDataSourceJndiName"); //$NON-NLS-1$
        ArgUtil.checkNull(pMapperClasses, "pMapperClasses"); //$NON-NLS-1$
        this.dataSourceMyBatisModule = new DataSourceMyBatisModule(pDataSourceJndiName, pMapperClasses);
    }

    /**
     * @see com.google.inject.PrivateModule#configure()
     */
    @Override
    protected void configure() {
        install(this.dataSourceMyBatisModule);
        for (final Class<?> mapperClass : this.dataSourceMyBatisModule.getMapperClasses()) {
            this.expose(mapperClass);
        }
    }

}