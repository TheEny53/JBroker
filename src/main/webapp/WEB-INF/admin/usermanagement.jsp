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
        Nutzerdaten anpassen
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
                     <div class="side-by-side" value="${title}">
                        <select name="title" onchange="">
                            <option value=" ">Keine Angabe</option>
                            <option value="Dr.">Dr.</option>
                            <option value="Dr. med.">Dr. med.</option>
                            <option value="Dr. rer. nat.">Dr. rer. nat.</option>
                            <option value="Prof.">Prof.</option>
                            <option value="Prof. Dr.">Prof. Dr.</option>
                            <option value="Prof. Dr. Mult.">Prof. Dr. Mult.</option>
                        </select>
                    </div>
                    <label for="firstname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <input type="text" name="firstname" value="${firstname}">

                   <label for="lastname">
                        Nachname
                        <span class="required">*</span>
                    </label>
                    <input type="text" name="lastname" value="${lastname}">

                    <%-- Button zum Abschicken --%>
                    <button class="icon-pencil" type="submit">
                        Aktualisieren
                    </button>
                </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>