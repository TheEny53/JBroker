/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */

package dhbwka.wwi.vertsys.javaee.jbroker.user.web;

import dhbwka.wwi.vertsys.javaee.jbroker.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jbroker.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jbroker.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.jbroker.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jbroker.common.web.WebUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author D070533
 */
@WebServlet(urlPatterns={"/app/changepw/"})
public class PasswordChangeServlet extends HttpServlet {
   
    @EJB
    private ValidationBean valbean;

    @EJB
    private UserBean userbean;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/admin/password.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String pw1 = request.getParameter("password1");
        String pw2 = request.getParameter("password2");
        String oldPw = request.getParameter("password");
        
        User user = userbean.getCurrentUser();
        List<String> errors = new ArrayList<>();
        if(!user.checkPassword(oldPw))
            errors.add("Passwort ist falsch.");
        if(!pw1.equals(pw2) && pw1 != null && pw2 != null)
            errors.add("Passwörter stimmen nicht überein.");
        if(errors.isEmpty())    {
            user.setPassword(pw1);
            userbean.update(user);
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        }
        else{
            FormValues fvalues = new FormValues();
            fvalues.setErrors(errors);
            request.getSession().setAttribute("profil_form", fvalues);
            response.sendRedirect(request.getRequestURI());
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
