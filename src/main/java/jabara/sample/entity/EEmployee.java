/**
 * 
 */
package jabara.sample.entity;

import jabara.jpa.entity.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author jabaraster
 */
@Entity
public class EEmployee extends EntityBase<EEmployee> {
    private static final long serialVersionUID      = 9178581578167955933L;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_NUMBER = 20;
    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_NAME   = 100;

    /**
     * 
     */
    @Column(nullable = true, length = MAX_CHAR_COUNT_NUMBER * 3)
    protected String          number;

    /**
     * 
     */
    @Column(nullable = true, length = MAX_CHAR_COUNT_NAME * 3)
    protected String          name;

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * @param pName the name to set
     */
    public void setName(final String pName) {
        this.name = pName;
    }

    /**
     * @param pNumber the number to set
     */
    public void setNumber(final String pNumber) {
        this.number = pNumber;
    }

}
