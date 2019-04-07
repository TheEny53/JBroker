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
 * @author AnikaSchmidt
 */
public enum RiskCategory {
    RK_1, RK_2, RK_3, RK_4, RK_5, RK_N;

    public String getRiskCategoryDecription() {
        switch (this) {
            case RK_1:
                return "Spareinlagen, andere Bankeinlagen wie Tages- und Festgelder, Pfandbriefe, Geldmarktfonds in Euro";
            case RK_2:
                return "Rentenfonds in Euro, geldmarktnahe Fonds, Euro-Anleihen mit guter Bonität, offene Immobilienfonds";
            case RK_3:
                return "Europäische Aktienfonds, Mischfonds, internationale Rentenfonds, Standardwerte-Aktien , Auslandsanleihen in Euro, Währungsanleihen guter Bonität";
            case RK_4:
                return "Außereuropäische Aktienfonds, europäische Nebenwerte-Aktien, Zertifikate, Währungsanleihen mittlerer Bonität";
            case RK_5:
                return "Optionen, Futures, andere Derivate, High-Yield-Anleihen, internationale Nebenwerte, spekulative Anleihen und Aktien";
            case RK_N:
                return "Risikokategorie nicht bekannt";
            default:
                return this.toString();
        }
    }
}
