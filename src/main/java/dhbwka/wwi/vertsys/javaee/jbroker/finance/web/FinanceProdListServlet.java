/*
 * Copyright Â© 2019 Anika Schmidt, Enzo Hilzinger, Marvin GÃ¶ckel
 * 
 * E-Mail: enzo.hilzinger@sap.com
 * Webseite: https://www.sap.com/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jbroker.finance.web;

import dhbwka.wwi.vertsys.javaee.jbroker.finance.ejb.FinanceProdBean;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.ejb.FinanceProdCatBean;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProd;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProdCat;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.ProductStatus;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author D070512
 */
@WebServlet(urlPatterns = {"/app/finance/productlist/"})
public class FinanceProdListServlet extends HttpServlet {

    @EJB
    private FinanceProdCatBean catbean;

    @EJB
    private FinanceProdBean prodbean;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("categories", this.catbean.findAllSorted());
        request.setAttribute("statuses", ProductStatus.values());

        String searchText = request.getParameter("search_text");
        String searchCategory = request.getParameter("search_category");
        String searchStatus = request.getParameter("search_status");
        String isin = request.getParameter("search_isin");
        String exchange_name = request.getParameter("search_exchangename");
        String amount = request.getParameter("search_amount");
        
        

        
        FinanceProdCat category = null;
        ProductStatus status = null;
        
        if(searchCategory != null)  {
            try{
                category = this.catbean.findById(Long.parseLong(searchCategory));
            }
            catch(NumberFormatException e){
                category = null;
            }
        }
        if (searchStatus != null) {
            try {
                status = ProductStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }
        
        List<FinanceProd> prods = this.prodbean.search(searchText, category, isin, exchange_name, amount, status);
        request.setAttribute("prods", prods);
        request.getRequestDispatcher("/WEB-INF/finance/financeprodlist.jsp").forward(request, response);
    }

    
    // </editor-fold>

}
