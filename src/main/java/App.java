import jabara.sample.model.DI;
import jabara.sample.service.ISomeService;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.eclipse.jetty.plus.jndi.Resource;
import org.postgresql.ds.PGPoolingDataSource;

/**
 * @author jabaraster
 */
public class App {
    /**
     * @param pArgs -
     * @throws NamingException
     */
    @SuppressWarnings("unused")
    public static void main(final String[] pArgs) throws NamingException {
        new Resource("jdbc/mainDataSource", createMainDataSource()); //$NON-NLS-1$
        new Resource("jdbc/subDataSource", createSubDataSource()); //$NON-NLS-1$
        DI.get(ISomeService.class).run();
    }

    @SuppressWarnings("nls")
    private static DataSource createMainDataSource() {
        final PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setDatabaseName("rakeup");
        ds.setUser("postgres");
        ds.setPassword("postgres");
        return ds;
    }

    @SuppressWarnings("nls")
    private static DataSource createSubDataSource() {
        final PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setDatabaseName("postgres");
        ds.setUser("postgres");
        ds.setPassword("postgres");
        return ds;
    }
}
