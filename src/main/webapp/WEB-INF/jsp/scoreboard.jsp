<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--<spring:url value="/resources/css/scoreboard.css" var="scoreboardCss"/>-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/scoreboard.css"/>"/>


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
                <li class="game"><a onclick="location.href = '/newgame'">Category</a></li>
                <li class="active"><a onclick="location.href = '/scoreboard'">Scoreboard</a></li>
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
<div class="container-fluid">
    <div class="mainContainer">
        <div>
            <h2>Top scorers</h2>
        </div>
        <div class="table-container">
            <table class="w3-table">
                <tr>
                    <th>Place </th>
                    <th>Username</th>
                    <th>Total score</th>
                </tr>
                <tr>
                    <td>1. </td>
                    <td>${name1}</td>
                    <td>${score1}</td>
                </tr>
                <tr>
                    <td>2.</td>
                    <td>${name2}</td>
                    <td>${score2}</td>
                </tr>
                <tr>
                    <td>3.</td>
                    <td>${name3}</td>
                    <td>${score3}</td>
                </tr>
            </table>
        </div>
        <div>
            <h2>Your place and total score</h2>
        </div>
        <div class="table-container">
            <table class="w3-table">
                <tr>
                    <td>${place}.</td>
                    <td>${name}</td>
                    <td>${score}</td>
                </tr>
            </table>
        </div>
    </div>
</div>

</body>

</html>


