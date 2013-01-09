/**
 * 
 */
package jabara.sample.entity;

import javax.persistence.Entity;

/**
 * @author jabaraster
 */
@Entity
public class ESource extends ELabelableEntityBase<ESource> {
    private static final long serialVersionUID = 4372231683564540448L;

    /**
     * 
     */
    public ESource() {
        // 処理なし
    }

    /**
     * @param pLabel ラベル文字列.
     */
    public ESource(final String pLabel) {
        super(pLabel);
    }
}
