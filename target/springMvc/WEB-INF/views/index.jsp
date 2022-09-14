<%--
  Created by IntelliJ IDEA.
  User: 02guz
  Date: 14.09.2022
  Time: 1:46
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Contact Manager Home</title>
</head>
    <body>
        <div align="center">
            <h1>Contact List</h1>
            <h3><a href=" <spring:url value="/new" />" >New Contact</a></h3>
            <table border="1">
                <th>No</th>
                <th>Name</th>
                <th>Email</th>
                <th>Address</th>
                <th>Phone</th>
                <th>Action</th>

                <c:forEach var="contact" items="${contactList}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${contact.name}</td>
                    <td>${contact.email}</td>
                    <td>${contact.address}</td>
                    <td>${contact.telephone}</td>
                    <td>

                        <a href=" <spring:url value="/edit?id=${contact.id}" /> "> Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href=" <spring:url value="/delete?id=${contact.id}" /> ">Delete</a>
                    </td>

                </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
