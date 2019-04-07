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
        <c:choose>
            <c:when test="${edit}">
                Produkt bearbeiten
            </c:when>
            <c:otherwise>
                Produkt anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/prod_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/finance/productlist/"/>">Liste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="prod_owner">Eigentümer:</label>
                <div class="side-by-side">
                    <input type="text" name="prod_owner" value="${product_form.values["prod_owner"][0]}" readonly="readonly">
                </div>

                <label for="prod_category">Kategorie:</label>
                <div class="side-by-side">
                    <select name="prod_category">
                        <option value="">Keine Kategorie</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${product_form.values["prod_category"][0] == category.id.toString() ? 'selected' : ''}>
                                <c:out value="${category.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="prod_due_date">
                    Fällig am:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="prod_due_date" value="${product_form.values["prod_due_date"][0]}">
                </div>

                <label for="prod_status">
                    Status:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="prod_status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${product_form.values["prod_status"][0] == status ? 'selected' : ''}>
                                <c:out value="${status}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="prod_notes">
                    Notizen:
                    <span class=""></span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="prod_notes" value="${product_form.values["prod_notes"][0]}">
                </div>
                
                <label for="prod_name">
                    Klarname:
                    <span class=""></span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="prod_name" value="${product_form.values["prod_name"][0]}">
                </div>
                
                <label for="prod_isin">
                    ISIN:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="prod_isin" value="${product_form.values["prod_isin"][0]}">
                </div>
                
                <label for="prod_exchangename">
                    Handelsplatz:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="prod_exchangename" value="${product_form.values["prod_exchangename"][0]}">
                </div>
                
                <label for="prod_buycourse">
                    Kurs:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="prod_buycourse" value="${product_form.values["prod_buycourse"][0]}">
                </div>

                <label for="prod_amount">
                    Anzahl:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="prod_amount" value="${product_form.values["prod_amount"][0]}">
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty product_form.errors}">
                <ul class="errors">
                    <c:forEach items="${product_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>