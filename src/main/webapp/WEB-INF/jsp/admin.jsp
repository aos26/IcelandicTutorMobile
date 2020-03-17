<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--<spring:url value="/resources/css/homepage.css" var="homepageCss"/>-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/homepage.css"/>"/>


    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<header>

</header>

<body>
<div class="header">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand active" onclick="location.href = '/homepage'">IcelandicTutor</a>
            </div>
            <ul class="nav navbar-nav">
                <li class="newgame"><a onclick="location.href = '/newgame'">Category</a></li>
                <li class="scoreboard"><a onclick="location.href = '/scoreboard'">Scoreboard</a></li>
                <li class="dictionary"><a onclick="location.href = '/dictionary'">Dictionary</a></li>
            </ul>
            <div>
                <input type="button" class="btn btn-primary logoutbtn" onclick="location.href = '/logout'" value="Logout" />
            </div>
        </div>
    </nav>
</div>

<div class="container-fluid">
    <div class="mainContainer">
       <sf:form name="adminForm" method="POST" modelAttribute="addQuestion" action="/admin">
           <table>
               <div class="form-group">
                   <label path="questionWord">Question</label>
                   <sf:input type="text" path="questionWord" name="questionWord" class="form-control" placeholder="Enter word" />
               </div>
               <div class="form-group">
                   <label path="answer">Answer</label>
                   <sf:input type="text" path="answer" name="answer" class="form-control" placeholder="Enter answer"/>
               </div>
               <div class="form-group">
                   <label path="wrongAnswer1">Wrong Answer 1</label>
                   <sf:input type="text" path="wrongAnswer1" name="wrongAnswer1" class="form-control" placeholder="Enter wrong answer"/>
               </div>
               <div class="form-group">
                   <label path="wrongAnswer2">Wrong Answer 2</label>
                   <sf:input type="text" path="wrongAnswer2" name="wrongAnswer2" class="form-control" placeholder="Enter wrong answer"/>
               </div>
               <div class="form-group">
                   <label path="cat_id">Category number</label>
                   <form:select path="cat_id" items="${categoryList}" class="form-control"/>
               </div>
               <div class="form-group">
                   <label path="lvl_id">Level number</label>
                   <form:select path="lvl_id" items="${levelList}" class="form-control"/>
               </div>
               <div class="form-group">
                   <label path="question_image">Image URL (optional)</label>
                   <sf:input type="text" path="question_image" name="question_image" class="form-control" placeholder="Enter image URL"/>
               </div>
               <input type="submit" class="btn btn-primary login" value="Submit" />
           </table>
       </sf:form>
    </div>
</div>

</body>

</html>