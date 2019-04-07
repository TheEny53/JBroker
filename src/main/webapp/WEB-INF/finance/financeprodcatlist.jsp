<%-- 
    Document   : financeprodcatlist
    Created on : Apr 4, 2019, 2:57:33 PM
    Author     : AnikaSchmidt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<template:base>
    <jsp:attribute name="title">
        Finanzproduktkategorien bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/category_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/finance/productlist/"/>">Finanzproduktliste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <%-- CSRF-Token --%>
            <input type="hidden" name="csrf_token" value="${csrf_token}">

            <%-- Feld zum Anlegen einer neuen Kategorie --%>
            <div class="column margin">
                <label for="j_username">Neue Produktkategorie:</label>
                <label>Name der Produktkategorie</label>
                <input type="text" name="name" value="${categories_form.values["name"][0]}">
                <label>Risikokategorie</label>
                <div class="side-by-side" value="${signup_form.values["risk_category"][0]}">
                    <select name="risk_category" onchange="">
                        <option value="RK_N">RK_N</option>
                        <option value="RK_1">RK_1</option>
                        <option value="RK_2">RK_2</option>
                        <option value="RK_3">RK_3</option>
                        <option value="RK_4">RK_4</option>
                        <option value="RK_5">RK_5</option>
                    </select>
                </div>
                <button type="submit" name="action" value="create" class="icon-pencil">
                    Kategorie erstellen
                </button>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty categories_form.errors}">
                <ul class="errors margin">
                    <c:forEach items="${categories_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
            <br>
            <%-- Vorhandene Kategorien --%>
            <c:choose>
                <c:when test="${empty categories}">
                    <p>
                        Bitte Produkkategorie definieren.
                    </p>
                </c:when>
                <c:otherwise>
                    <div>
                        <div class="margin">
                            <c:forEach items="${categories}" var="category">
                                <input type="checkbox" name="category" id="${'category-'.concat(category.id)}" value="${category.id}" />
                                <label for="${'category-'.concat(category.id)}">
                                    <c:out value="Name: ${category.name}"/>
                                    <c:out value="Risikokategorie: ${category.risk_category}"/>
                                </label>
                                <br />
                            </c:forEach>
                        </div>

                        <button type="submit" name="action" value="delete" class="icon-trash">
                            Markierte löschen
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </jsp:attribute>
</template:base>