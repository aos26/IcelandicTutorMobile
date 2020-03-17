<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--<spring:url value="/resources/css/game.css" var="gameCss"/>-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/homepage.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/game.css"/>"/>


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
                <a class="navbar-brand" onclick="location.href = '/homepage'">IcelandicTutor</a>
            </div>
            <ul class="nav navbar-nav">
                <li class="active"><a onclick="location.href = '/newgame'">Category</a></li>
                <li class="scoreboard"><a onclick="location.href = '/scoreboard'">Scoreboard</a></li>
                <li class="dictionary"><a onclick="location.href = '/dictionary'">Dictionary</a></li>
            </ul>
            <div>
                <input type="button" class="btn btn-primary logoutbtn" onclick="location.href = '/logout'" value="Logout" />
                <span style="float: right" class="navbar-text">
                    Hello, ${msg}
                </span>
            </div>
        </div>
    </nav>
</div>

<div class="mainContainer">
        <div class="scoreHeading" style="float: right">
            <h2>Correct answers: ${currScore}</h2>
        </div>
        <sf:form method="POST" action="/game/${cat_id}/${lvl_id}">
            <div class="questionHeading">
                <c:choose>
                    <c:when test="${lvl_id == 1}">
                      
                        <h2>Question number ${questionNR + 1}</h2>
                        <h1>${question}</h1>
                    </c:when>
                    <c:when test="${lvl_id == 2}">

                        <h2>Question number ${questionNR + 1}</h2>
                        <h1>${question}</h1>
                    </c:when>
                    <c:when test="${lvl_id == 3}">
                        <h2>Question number ${questionNR + 1}</h2>

                        <img src="${questionImg}">
                    </c:when>
                </c:choose>
            </div>
            <div class="message">
                    ${svarMsg}
            </div>
            <div class="answerContainer">
                <c:choose>
                    <c:when test="${order == 0}">
                        <div>
                            <input type="submit" class="btn btn-primary option" onclick="location.href = '/game/${cat_id}/${lvl_id}'" value="${answer}" name="answer" />

                            <input type="submit" class="btn btn-primary option" onclick="location.href = '/game/${cat_id}/${lvl_id}'" value="${wrongAnswer1}" name="wrongAnswer1" />

                            <input type="submit" class="btn btn-primary option" onclick="location.href = '/game/${cat_id}/${lvl_id}'" value="${wrongAnswer2}" name="wrongAnswer2"/>
                        </div>
                    </c:when>
                    <c:when test="${order == 1}">
                        <div>
                            <input type="submit" class="btn btn-primary option" onclick="location.href = '/game/${cat_id}/${lvl_id}'" value="${wrongAnswer1}" name="wrongAnswer1" />

                            <input type="submit" class="btn btn-primary option" onclick="location.href = '/game/${cat_id}/${lvl_id}'" value="${answer}" name="answer" />

                            <input type="submit" class="btn btn-primary option" onclick="location.href = '/game/${cat_id}/${lvl_id}'" value="${wrongAnswer2}" name="wrongAnswer2"/>
                        </div>
                    </c:when>
                    <c:when test="${order == 2}">
                        <div>
                            <input type="submit" class="btn btn-primary option" onclick="location.href = '/game/${cat_id}/${lvl_id}'" value="${wrongAnswer1}" name="wrongAnswer1" />

                            <input type="submit" class="btn btn-primary option" onclick="location.href = '/game/${cat_id}/${lvl_id}'" value="${wrongAnswer2}" name="wrongAnswer2"/>

                            <input type="submit" class="btn btn-primary option" onclick="location.href = '/game/${cat_id}/${lvl_id}'" value="${answer}" name="answer" />
                        </div>
                    </c:when>
                </c:choose>


            </div>
        </sf:form>

    </div>

</body>

</html>