/**
 * 
 */
package jabara.sample.entity;

import javax.persistence.Entity;

/**
 * @author jabaraster
 */
@Entity
public class EKeyword extends ELabelableEntityBase<EKeyword> {
    private static final long serialVersionUID = 4333759403400457958L;

    /**
     * 
     */
    public EKeyword() {
        // 処理なし
    }

    /**
     * @param pLabel ラベル文字列.
     */
    public EKeyword(final String pLabel) {
        super(pLabel);
    }
}
