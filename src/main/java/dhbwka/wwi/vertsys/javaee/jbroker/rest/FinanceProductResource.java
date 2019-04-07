/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jbroker.rest;

import dhbwka.wwi.vertsys.javaee.jbroker.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.ejb.FinanceProdBean;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProd;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author D070512
 */
@Path("financeproducts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FinanceProductResource {
    @EJB
    private FinanceProdBean finprodbean;
    
    @EJB
    private UserBean userbean;
    
    @GET
    public List<FinanceProd> getAllProducts()   {
        return  this.finprodbean.findByUsername(this.userbean.getCurrentUser().getUsername());
    }
    
}
