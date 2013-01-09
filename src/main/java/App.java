import jabara.sample.model.DI;
import jabara.sample.service.ISomeService;

/**
 * @author jabaraster
 */
public class App {
    /**
     * @param pArgs -
     */
    public static void main(final String[] pArgs) {
        DI.get(ISomeService.class).run();
    }
}
