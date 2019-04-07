<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Passwort ändern
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

   

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                     <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">
                
                    <%-- Eingabefelder --%>
                    <label for="signup_password1">
                        Altes Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="password">
                    </div>
                    
                    <label for="password1">
                        Neues Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="password1">
                    </div>
                    
                    <label for="password2">
                        Neues Passwort (wdh.):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="password2">
                    </div>
                    
                    <%-- Button zum Abschicken --%>
                    <button class="icon-pencil" type="submit">
                        Aktualisieren
                    </button>
                </div>
                    <c:if test="${!empty profil_form.errors}">
                        <ul class="errors">
                            <c:forEach items = "${profil_form.errors}" var="error">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>