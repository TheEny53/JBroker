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

import dhbwka.wwi.vertsys.javaee.jbroker.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jbroker.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.ejb.FinanceProdBean;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.ejb.FinanceProdCatBean;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProd;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProdCat;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.RiskCategory;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author AnikaSchmidt
 */
@WebServlet(urlPatterns = {"/app/finance/categorylist"})
public class FinanceProdCatListServlet extends HttpServlet {

    @EJB
    FinanceProdCatBean finProdCatBean;

    @EJB
    FinanceProdBean finProdBean;

    @EJB
    ValidationBean validationBean;

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
        request.setAttribute("categories", this.finProdCatBean.findAllSorted());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/finance/financeprodcatlist.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("categories_form");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Angeforderte Aktion ausführen        
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                this.createFinProdCategory(request, response);
                break;
            case "delete":
                this.deleteFinProdCategories(request, response);
                break;
        }
    }
    // </editor-fold>

    private void createFinProdCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String risk_category = request.getParameter("risk_category");
        FinanceProdCat cat = new FinanceProdCat();
        cat.setName(name);
        cat.setRisk_category(RiskCategory.valueOf(risk_category));
        List<String> errors = this.validationBean.validate(cat);

        if (errors.isEmpty()) {
            this.finProdCatBean.update(cat);
        }
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("categories_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    private void deleteFinProdCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] categoryIds = request.getParameterValues("category");
        if (categoryIds == null) {
            categoryIds = new String[0];
        }
        for (String categoryId : categoryIds) {
            FinanceProdCat cat;

            try {
                cat = this.finProdCatBean.findById(Long.parseLong(categoryId));
            } catch (NumberFormatException ex) {
                continue;
            }

            if (cat == null) {
                continue;
            }
            List<FinanceProd> products = cat.getFinance_products();

            if (products != null) {
                products.forEach((FinanceProd prod) -> {
                    prod.setCategory(null);
                    this.finProdBean.update(prod);
                });
            }
            this.finProdCatBean.delete(cat);
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }
}
