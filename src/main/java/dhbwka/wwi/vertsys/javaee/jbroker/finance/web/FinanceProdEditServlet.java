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

import dhbwka.wwi.vertsys.javaee.jbroker.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jbroker.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jbroker.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jbroker.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.ejb.FinanceProdBean;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.ejb.FinanceProdCatBean;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.FinanceProd;
import dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa.ProductStatus;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author D070512
 */
@WebServlet(urlPatterns = {"/app/finance/financeprodedit/*"})
public class FinanceProdEditServlet extends HttpServlet {

    private static final long serialVersionUID = 102831973239L;
    
    @EJB
    FinanceProdBean finprodbean;
    
    @EJB
    FinanceProdCatBean catbean;
    
    @EJB
    UserBean userbean;
    
    @EJB
    ValidationBean validationbean;

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
        
        HttpSession session = request.getSession();
        
        FinanceProd product = this.getRequestedProduct(request);
        
        request.setAttribute("edit", product.getId() != 0);
        
        if (session.getAttribute("product_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("product_form", this.createProductForm(product));
        }
        request.getRequestDispatcher("/WEB-INF/finance/finprodedit.jsp").forward(request, response);

        session.removeAttribute("product_form");
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
       String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveProduct(request, response);
                break;
            case "delete":
                this.deleteProduct(request, response);
                break;
        }
    }
    
    private void saveProduct(HttpServletRequest request, HttpServletResponse response) throws IOException{
         List<String> errors = new ArrayList<>();
         String category = request.getParameter("prod_category");
         String duedate = request.getParameter("prod_due_date");
         String status = request.getParameter("prod_status");
         String notes = request.getParameter("prod_notes");
         String name = request.getParameter("prod_name");
         String isin = request.getParameter("prod_isin");
         String exchange = request.getParameter("prod_exchangename");
         String amount = request.getParameter("prod_amount");
         String ordercourse = request.getParameter("prod_buycourse");
         
         FinanceProd prod = this.getRequestedProduct(request);
         
         if (category != null && !category.trim().isEmpty()) {
            try {
                prod.setCategory(this.catbean.findById(Long.parseLong(category)));
            } catch (NumberFormatException ex) {
            }
        }
         
        Date dueDate = WebUtils.parseDate(duedate);
         if (dueDate != null) {
            prod.setDue_date(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }
          try {
            prod.setStatus(ProductStatus.valueOf(status));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }
          
        prod.setNotes(notes);
        prod.setProduct_name(name);
        prod.setIsin(isin);
        prod.setExchange_name(exchange);
        prod.setOrder_course(Double.parseDouble(ordercourse));
        int iamount = Integer.parseInt(amount);
        if(amount!=null){
            prod.setAmount(iamount);
        }
        else{
            errors.add("Die Anzahl muss ganzzahlig sein.");
        }
          
        this.validationbean.validate(prod, errors);
        if(errors.isEmpty()){
            this.finprodbean.update(prod);
        }
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/finance/productlist/"));
        } else {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("product_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }
    
     private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException{
        

        // Datensatz löschen
        FinanceProd prod = this.getRequestedProduct(request);
        this.finprodbean.delete(prod);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/finance/productlist/"));
    
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    private FinanceProd getRequestedProduct(HttpServletRequest request){
        FinanceProd prod = new FinanceProd();
        prod.setOwner(this.userbean.getCurrentUser());
        prod.setDue_date(new Date(System.currentTimeMillis()));
        
        String taskId = request.getPathInfo();

        if (taskId == null) {
            taskId = "";
        }

        taskId = taskId.substring(1);

        if (taskId.endsWith("/")) {
            taskId = taskId.substring(0, taskId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            prod = this.finprodbean.findById(Long.parseLong(taskId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return prod;
    }
    private FormValues createProductForm(FinanceProd task) {
        Map<String, String[]> values = new HashMap<>();

        values.put("prod_owner", new String[]{
            task.getOwner().getUsername()
        });

        if (task.getCategory() != null) {
            values.put("prod_category", new String[]{
                "" + task.getCategory().getId()
            });
        }

        values.put("prod_due_date", new String[]{
            WebUtils.formatDate(task.getDueDate())
        });


        values.put("prod_status", new String[]{
            task.getStatus().toString()
        });

        values.put("prod_notes", new String[]{
            task.getNotes()
        });

        values.put("prod_name", new String[]{
            task.getProduct_name()
        });
        
        values.put("prod_isin", new String[]{
            task.getIsin()
        });
        
        values.put("prod_exchangename", new String[]{
            task.getExchange_name()
        });
        
        values.put("prod_amount", new String[]{
           Integer.toString(task.getAmount())
        });
        
        values.put("prod_buycourse", new String[]{
            Double.toString(task.getOrder_course())
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
}
