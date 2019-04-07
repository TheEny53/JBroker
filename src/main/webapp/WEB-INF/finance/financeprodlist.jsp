<%--
 * Copyright Â© 2019 Anika Schmidt, Enzo Hilzinger, Marvin GÃ¶ckel
 * 
 * E-Mail: enzo.hilzinger@sap.com
 * Webseite: https://www.sap.com/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Übersicht aller Finanzprodukte
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/finance/financeprodedit/new"/>">Finanzprodukt erstellen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/finance/categorylist"/>">Kategorien bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select name="search_category">
                <option value="">Alle Kategorien</option>

                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}" ${param.search_category == category.id ? 'selected' : ''}>
                        <c:out value="${category.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_status">
                <option value="">Alle Stati</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status}"/>
                    </option>
                </c:forEach>
            </select>

            <input type="text" name="search_isin" value="${param.search_isin}" placeholder="ISIN"/>
            <input type="text" name="search_exchangename" value="${param.search_exchangename}" placeholder="Handelsplatz"/>
            <input type="text" name="search_amount" value="${param.search_amount}" placeholder="Anzahl"/>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty prods}">
                <p>
                    Es sind keine Produkte für obige Suchoptionen verfügbar.
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.jbroker.common.web.WebUtils"/>

                <table>
                    <thead>
                        <tr>
                            <th>Kategorie</th>
                            <th>ISIN</th>
                            <th>Kurzbeschreibung</th>
                            <th>Handelsplatz</th>
                            <th>Anzahl</th>
                            <th>Fälligkeitsdatum</th>
                            <th>Buy-Kurs</th>
                        </tr>
                    </thead>
                    <c:forEach items="${prods}" var="prods">
                        <tr>
                             <td>
                                <c:out value="${prods.category.name}"/>
                            </td>
                            <td>
                                <a href="<c:url value="/app/finance/financeprodedit/${prods.id}/"/>">
                                    <c:out value="${prods.isin}"/>
                                </a>
                            </td>       
                            <td>
                                <c:out value="${prods.product_name}"/>
                            </td>

                            <td>
                                <c:out value="${prods.exchange_name}"/>
                            </td>
                            <td>
                                <c:out value="${prods.amount}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(prods.dueDate)}"/>
                            </td>
                            <td>
                                <c:out value="${prods.order_course}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>
