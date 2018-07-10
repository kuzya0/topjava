<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 09.07.2018
  Time: 22:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактирование</title>
</head>
<body>
<form  method="post" action="./add">
    <input type="hidden" name="id" value="-1"/>
    <br>
    <label>Дата</label>
    <input type="datetime-local" name="datetimeL" >
    </br>
    <br>
    <label>Калории</label>
    <input type="text" name="calories" >
    </br>
    <br>
    <label>Описание</label>
    <input type="text" name="description"/>
    </br>
    <br>
    <input type="submit" value="Добавить"/>
    </br>
</form>
</body>
</html>
