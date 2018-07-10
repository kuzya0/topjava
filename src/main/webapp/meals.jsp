<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 01.07.2018
  Time: 5:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>HEllo Meal!</title>
</head>
<body>
<table>
<c:forEach items="${meals}" var="meals">
    <tr bgcolor="${meals.exceed == true ? "red" : "green"}">
        <td>${meals.description}</td>
        <td>${meals.time}</td>
        <td>${meals.date}</td>
        <td>${meals.calories}</td>
        <td><form action="./meals?action=del&id=${meals.id}" style="margin:0px;" method="post">
            <button type="submit" value="Удалить">Удалить</button>
        </form></td>
        <td><form action="./meals?action=upd&id=${meals.id}" style="margin:0px;" method="post">
            <button type="submit" value="submit">Редактировать</button>
        </form></td>
    </tr>
</c:forEach>
</table>
    <br><form action="./meals?action=add" style="margin:0px;" method="post">
    <button type="submit" value="submit">Добавить</button></form></br>
</body>
</html>
