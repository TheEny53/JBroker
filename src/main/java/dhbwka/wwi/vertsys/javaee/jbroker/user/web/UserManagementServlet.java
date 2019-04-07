/*
 * Copyright Â© 2019 Anika Schmidt, Enzo Hilzinger, Marvin GÃ¶ckel
 * 
 * E-Mail: enzo.hilzinger@sap.com
 * Webseite: https://www.sap.com/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jbroker.user.web;

import dhbwka.wwi.vertsys.javaee.jbroker.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jbroker.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.jbroker.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jbroker.common.web.WebUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(urlPatterns = {"/app/account/"})
public class UserManagementServlet extends HttpServlet {

    @EJB
    UserBean userBean;

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
        request.setAttribute("firstname", userBean.getCurrentUser().getFirstname());
        request.setAttribute("lastname", userBean.getCurrentUser().getLastname());
        request.setAttribute("title", userBean.getCurrentUser().getTitle());
          request.getRequestDispatcher("/WEB-INF/admin/usermanagement.jsp").forward(request, response);
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
        List<String> errors = new ArrayList<>();
        User user = this.userBean.getCurrentUser();
        String fname = request.getParameter("firstname");
        String lname = request.getParameter("lastname");
        String title = request.getParameter("title");
        if(fname == null){
            errors.add("Vorname darf nicht leer sein");
        }
        if(lname == null){
            errors.add("Nachname darf nicht leer sein");
        }
        if(errors.isEmpty()){
            user.setLastname(lname);
            user.setFirstname(fname);
            user.setTitle(title);
            this.userBean.update(user);
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        }
        else {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("beverage_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }
}
