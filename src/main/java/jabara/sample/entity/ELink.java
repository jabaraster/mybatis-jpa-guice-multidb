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
public class ELink extends EntityBase<ELink> {
    private static final long serialVersionUID    = -6836414276935841515L;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_REL  = 100;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_HREF = 1000;

    /**
     * 
     */
    @Column(nullable = true, length = MAX_CHAR_COUNT_REL * 3)
    protected String          rel;

    /**
     * 
     */
    @Column(nullable = false, length = MAX_CHAR_COUNT_HREF * 3)
    protected String          href;

    /**
     * @return the href
     */
    public String getHref() {
        return this.href;
    }

    /**
     * @return the rel
     */
    public String getRel() {
        return this.rel;
    }

    /**
     * @param pHref the href to set
     */
    public void setHref(final String pHref) {
        this.href = pHref;
    }

    /**
     * @param pRel the rel to set
     */
    public void setRel(final String pRel) {
        this.rel = pRel;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return "ELink [rel=" + this.rel + ", href=" + this.href + ", id=" + this.id + "]";
    }

}
