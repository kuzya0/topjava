<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 01.07.2018
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактирование</title>
</head>
<body>
<form  method="post" action="./update">
    <input type="hidden" name="id" value="${meal.id}"/>
    <br>
        <label>Дата</label>
        <input type="datetime-local" name="datetimeL" value="${meal.dateTime}" >
    </br>
    <br>
        <label>Калории</label>
        <input type="text" name="calories" value="${meal.calories}" >
    </br>
    <br>
        <label>Описание</label>
        <input type="text" name="description" value="${meal.description}"/>
    </br>
    <br>
        <input type="submit" value="Обновить"/>
    </br>
</form>
</body>
</html>
