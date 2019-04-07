/*
 * Copyright Â© 2019 Anika Schmidt, Enzo Hilzinger, Marvin GÃ¶ckel
 * 
 * E-Mail: enzo.hilzinger@sap.com
 * Webseite: https://www.sap.com/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author D070512
 */
@Entity
public class FinanceProdCat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RiskCategory risk_category = RiskCategory.RK_N;

    @Column(length = 20)
    @NotNull(message = "Name darf nicht leer sein.")
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<FinanceProd> finance_products = new ArrayList<>();

    public RiskCategory getRisk_category() {
        return risk_category;
    }

    public void setRisk_category(RiskCategory risk_category) {
        this.risk_category = risk_category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<FinanceProd> getFinance_products() {
        return finance_products;
    }

    public void setFinance_products(List<FinanceProd> finance_products) {
        this.finance_products = finance_products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinanceProdCat)) {
            return false;
        }
        FinanceProdCat other = (FinanceProdCat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProdCat[ id=" + id + " ]";
    }

    public FinanceProdCat() {
    }

    public FinanceProdCat(String name, RiskCategory category) {
        this.name = name;
        this.risk_category = category;
    }
}
