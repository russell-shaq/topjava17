<%--
  Created by IntelliJ IDEA.
  User: ruslan
  Date: 6/12/19
  Time: 4:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="link.html" %>
    <style type="text/css">

    </style>
</head>
<body>
<div class="container">
    <%@ include file="nav.html" %>
    <div class="raw">
        <div class="col-8 mx-auto">
            <h3 class="text-center">Meals</h3>
            <a href="meals?action=save" class="btn btn-outline-danger btn-block mb-3 mt-3">Add new meal</a>
            <table class="table table-hover table-striped table-bordered">
                <thead>
                <tr class="bg-danger text-white text-center">
                    <th class="align-middle py-3" scope="col">Date</th>
                    <th class="align-middle" scope="col">Description</th>
                    <th class="align-middle" scope="col">Calories</th>
                    <th class="align-middle" scope="col" colspan="2">Action</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo" scope="page" />
                    <tr class="text-center ${meal.excess ? 'text-danger' : 'text-success'}">
                            <%--                    <td>${fn:replace(meal.dateTime, 'T', ' ')}</td>--%>
                        <td class="align-middle">
                            <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="date"/>
                            <fmt:formatDate value="${date}" pattern="yyyy.MM.dd HH:mm:"/>
                        </td>

<%--                        <td class="align-middle">${meal.date} ${meal.time}</td>--%>
<%--                        <td class="align-middle">${meal.date} ${meal.time}</td>--%>
                        <td class="align-middle">${meal.description}</td>
                        <td class="align-middle">${meal.calories}</td>
                            <%--<td><a class="text-danger" href="meals?action=update&&id=${meal.id}"><i class="fas fa-trash"></i> Update</a></td>--%>
                            <%--<td><a class=text-danger" href="meals?action=delete&&id=${meal.id}">Delete</a></td>--%>
                        <td ><a class="btn btn-outline-danger btn-sm" href="meals?action=update&&id=${meal.id}"><i
                                class="far fa-trash-alt"></i> Update</a></td>
                        <td><a class="btn btn-outline-danger btn-sm" href="meals?action=delete&&id=${meal.id}"><i
                                class="far fa-trash-alt"></i> Delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


</div>
</body>
</html>
