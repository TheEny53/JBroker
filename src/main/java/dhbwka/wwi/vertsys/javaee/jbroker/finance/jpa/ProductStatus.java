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

/**
 *
 * @author Anika Schmidt
 */
public enum ProductStatus {
    ORDER_PLACED, ORDER_FULFILLED, SELLORDER_PLACED, SELLORDER_FULFILLED;

    public String getLabel() {
        switch (this) {
            case ORDER_PLACED:
                return "Order ist platziert.";
            case ORDER_FULFILLED:
                return "Order wurde ausgeführt.";
            case SELLORDER_PLACED:
                return "Verkaufsorder ist platziert.";
            case SELLORDER_FULFILLED:
                return "Verkaufsorder wurde ausgeführt.";
            default:
                return this.toString();
        }
    }

}
