<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2>
<h2><c:choose>
        <c:when test="${param.action == 'create'}">
           <spring:message code="meal.create"/>
        </c:when>
        <c:otherwise>
            <spring:message code="meal.update"/>
        </c:otherwise>
    </c:choose></h2>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals/${param.action == 'create' ? 'create' : 'edit'}">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="mealform.datetime"/></dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="meal.calories"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="mealform.cancel"/></button>
    </form>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</body>
</html>
