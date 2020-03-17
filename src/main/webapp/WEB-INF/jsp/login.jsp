<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Spring Login Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/login.css"/>"/>

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<h3>Welcome to</h3>
<h1>IcelandicTutor</h1>
<sf:form method="POST" modelAttribute="users" action="/login">
    <div style="color: blue" align="center">${msg}</div>
    <div class="form-group">
        <label for="exampleInputUsername">Username</label>
        <input type="text" name="userName" class="form-control" id="exampleInputUsername" placeholder="Enter username">
    </div>
    <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
    </div>
    <input type="submit" class="btn btn-primary login" value="Submit" />
    <a href="/register" class="btn btn-primary register">Not a member? Sign-up</a>
    <div style="color: red" align="center">${error}</div>
</sf:form>
</body>
</html>