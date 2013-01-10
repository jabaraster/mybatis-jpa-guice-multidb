/**
 * 
 */
package jabara.sample.service.impl;

import jabara.jpa.JpaDaoBase;
import jabara.sample.entity.EEmployee;
import jabara.sample.entity.EKeyword;
import jabara.sample.service.ISomeService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * @author jabaraster
 */
public class SomeServiceImpl implements ISomeService {

    private final JpaDaoBase      mainDao;
    private final JpaDaoBase      subDao;
    private final IEmployeeMapper employeeMapper;

    /**
     * @param pMainDao -
     * @param pSubDao -
     * @param pEmployeeMapper -
     */
    @Inject
    public SomeServiceImpl( //
            @MainDao final JpaDaoBase pMainDao //
            , @SubDao final JpaDaoBase pSubDao //
            , final IEmployeeMapper pEmployeeMapper) {
        this.mainDao = pMainDao;
        this.subDao = pSubDao;
        this.employeeMapper = pEmployeeMapper;
    }

    /**
     * @see jabara.sample.service.ISomeService#run()
     */
    @Override
    @MainTransactional
    @SubTransactional
    public void run() {
        queryMain();
        querySub();
        queryMyBatis();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return super.toString();
    }

    private void queryMain() {
        final EntityManager em = this.mainDao.getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<EKeyword> query = builder.createQuery(EKeyword.class);
        query.from(EKeyword.class);

        for (final EKeyword keyword : em.createQuery(query).getResultList()) {
            System.out.println(keyword);
        }
    }

    private void queryMyBatis() {
        System.out.println(this.employeeMapper.getAll());
    }

    private void querySub() {
        final EntityManager em = this.subDao.getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<EEmployee> query = builder.createQuery(EEmployee.class);
        query.from(EEmployee.class);

        for (final EEmployee emp : em.createQuery(query).getResultList()) {
            System.out.println(emp);
        }
    }
}
