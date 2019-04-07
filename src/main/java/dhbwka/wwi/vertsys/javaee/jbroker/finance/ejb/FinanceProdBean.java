/*
 * Copyright Â© 2019 Anika Schmidt, Enzo Hilzinger, Marvin GÃ¶ckel
 * 
 * E-Mail: enzo.hilzinger@sap.com
 * Webseite: https://www.sap.com/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jbroker.finance.ejb;

import dhbwka.wwi.vertsys.javaee.jbroker.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProd;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProdCat;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.ProductStatus;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Anika Schmidt
 */
@Stateless
@RolesAllowed("app-user")
public class FinanceProdBean extends EntityBean<FinanceProd, Long> {

    public FinanceProdBean() {
        super(FinanceProd.class);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     * Find Finance Products by Username
     *
     * @param username Username
     * @return All Finance Products for Given User
     */
    public List<FinanceProd> findByUsername(String username) {
        return em.createQuery("SELECT t FROM FinanceProd t WHERE t.owner.username = :username")
                .setParameter("username", username)
                .getResultList();
    }

    /**
     * Find Finance Products by multiple search criteria
     *
     * @param search String contained in description (optional)
     * @param category Category of searhed product (optional)
     * @param isin ISIN of searched product (optional)
     * @param exchangeName Name of trade exchange (optional)
     * @param amount Amount of purchased products (optional)
     * @return List with retrieved Finance Products
     */
    public List<FinanceProd> search(String search, FinanceProdCat category, String isin, String exchangeName, String amount, ProductStatus status) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();

        //Select p from products
        CriteriaQuery<FinanceProd> query = cb.createQuery(FinanceProd.class);
        Root<FinanceProd> from = query.from(FinanceProd.class);
        query.select(from);

        query.orderBy(cb.asc(from.get("due_date")));
        
        //Where p.notes LIKE :search
        Predicate p = cb.conjunction();
        if (search != null && !search.trim().isEmpty()) {
            p = cb.and(p, cb.like(from.get("notes"), "%" + search + "%"));
            query.where(p);
        }
        
        //WHERE p.category = :category
        if (category != null) {
            p = cb.and(p, cb.equal(from.get("category"), category));
            query.where(p);
        }

        //WHERE p.isin = :isin
        if (isin != null) {
            p = cb.and(p, cb.equal(from.get("isin"), isin));
            query.where(p);
        }

        //WHERE p.exchangeName = :exchangeName
        if (exchangeName != null) {
            p = cb.and(p, cb.equal(from.get("exchange_name"), exchangeName));
            query.where(p);
        }
        
         if (status != null) {
            p = cb.and(p, cb.equal(from.get("status"), status));
            query.where(p);
        }

       


        return em.createQuery(query).getResultList();
    }
}
