<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register Form</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/register.css"/>"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
</head>
<body>
<h3>Welcome to</h3>
<h1>IcelandicTutor</h1>
<sf:form name="registerForm" modelAttribute="createUser" method="POST" action="/register">
    <div style="color: blue" align="center">${msg}</div>
    <div class="form-group">
        <label for="name">Name</label>
        <sf:input type="text" path="name" name="name" class="form-control" id="name" />
    </div>
    <div class="form-group">
        <label for="userName">Username</label>
        <sf:input path="userName" name="userName" class="form-control" id="userName" />
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <sf:input type="password" path="password" name="password" class="form-control" id="password" />
    </div>
    <div class="form-group">
        <label for="email">Email</label>
        <sf:input type="email" path="email" name="email" class="form-control" id="email" />
    </div>
    <sf:button id="register" name="register" class="btn btn-primary register">Register</sf:button>
    <a href="/login" class="btn btn-primary login">Already a member? Log in!</a>
    <div style="color: red" align="center">${error}</div>
</sf:form>
</body>
</html>