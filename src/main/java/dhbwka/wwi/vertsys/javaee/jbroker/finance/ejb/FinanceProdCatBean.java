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
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProdCat;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author AnikaSchmidt
 */
@Stateless
@RolesAllowed("app-user")
public class FinanceProdCatBean extends EntityBean<FinanceProdCat, Long> {

    public FinanceProdCatBean() {
        super(FinanceProdCat.class);
    }

    /**
     * Find all categories, sorted alphabetically
     *
     * @return List with all categories
     */
    public List<FinanceProdCat> findAllSorted() {
        return this.em.createQuery("SELECT p FROM FinanceProdCat p ORDER BY p.name").getResultList();
    }
}
