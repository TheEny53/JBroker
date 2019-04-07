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

import dhbwka.wwi.vertsys.javaee.jbroker.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.jbroker.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.jbroker.dashboard.ejb.DashboardSection;
import dhbwka.wwi.vertsys.javaee.jbroker.dashboard.ejb.DashboardTile;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProdCat;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.ProductStatus;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author D070512
 */
@Stateless(name = "prods")
public class DashboardContent implements DashboardContentProvider{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private FinanceProdBean finprodbean;
    
    @EJB
    private FinanceProdCatBean catbean;

    @Override
    public void createDashboardContent(List<DashboardSection> sections) {
        DashboardSection section = this.createSection(null);
        sections.add(section);
        List<FinanceProdCat> categories = this.catbean.findAllSorted();

        for (FinanceProdCat category : categories) {
            section = this.createSection(category);
            sections.add(section);
        }
    }
    
    private DashboardSection createSection(FinanceProdCat category) {
        // Neue Rubrik im Dashboard erzeugen
        DashboardSection section = new DashboardSection();
        String cssClass = "";

        if (category != null) {
            section.setLabel(category.getName());
        } else {
            section.setLabel("Alle Kategorien");
            cssClass = "overview";
        }

        // Eine Kachel für alle Aufgaben in dieser Rubrik erzeugen
        DashboardTile tile = this.createTile(category, null, "Alle", cssClass + " status-all", "calendar");
        section.getTiles().add(tile);

        // Ja Aufgabenstatus eine weitere Kachel erzeugen
        for (ProductStatus status : ProductStatus.values()) {
            String cssClass1 = cssClass + " status-" + status.toString().toLowerCase();
            String icon = "";

            switch (status) {
                case ORDER_PLACED:
                    icon = "doc-text";
                    break;
                case ORDER_FULFILLED:
                    icon = "rocket";
                    break;
                case SELLORDER_PLACED:
                    icon = "ok";
                    break;
                case SELLORDER_FULFILLED:
                    icon = "cancel";
                    break;
            }

            tile = this.createTile(category, status, status.getLabel(), cssClass1, icon);
            section.getTiles().add(tile);
        }

        // Erzeugte Dashboard-Rubrik mit den Kacheln zurückliefern
        return section;
    }
    private DashboardTile createTile(FinanceProdCat category, ProductStatus status, String label, String cssClass, String icon) {
        int amount = finprodbean.search(null, category,null,null,null, status).size();
        String href = "/app/finance/productlist";

        if (category != null) {
            href = WebUtils.addQueryParameter(href, "search_category", "" + category.getId());
        }

        if (status != null) {
            href = WebUtils.addQueryParameter(href, "search_status", status.toString());
        }

        DashboardTile tile = new DashboardTile();
        tile.setLabel(label);
        tile.setCssClass(cssClass);
        tile.setHref(href);
        tile.setIcon(icon);
        tile.setAmount(amount);
        tile.setShowDecimals(false);
        return tile;
    }
}
