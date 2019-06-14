<%--
  Created by IntelliJ IDEA.
  User: ruslan
  Date: 6/13/19
  Time: 4:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="link.html" %>
</head>
<body>
<div class="container">
    <%@include file="nav.html" %>
    <div class="raw">
        <div class="col-6 mx-auto">
            <h3 class="text-center">${empty meal.id ? "Save" : "Update"} meal</h3>
            <form action="meals" method="POST">
                <div class="form-group">
                    <label for="dateTime">Enter date</label>
                    <input id="dateTime" type="datetime-local" name="dateTime" value="${meal.dateTime}" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="description">Enter description</label>
                    <input id="description" type="text" name="description" value="${meal.description}" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="calories">Enter calories</label>
                    <input id="calories" type="text" name="calories" value="${meal.calories}" class="form-control" required>
                </div>
                <input type="hidden" name="id" value="${meal.id}">
                <input type="hidden" name="action" value="${empty meal.id ? "save": "update"}">
                <input type="submit" class="btn btn-block mt-2" value="Submit">
            </form>
        </div>
    </div>
</div>

</body>
</html>
