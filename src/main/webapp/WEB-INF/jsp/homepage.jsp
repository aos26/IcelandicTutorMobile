<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                <span style="float: right" class="navbar-text">
                    Hello, ${msg}
                </span>
            </div>
        </div>
    </nav>
</div>

<div class="container-fluid">
    <div class="mainContainer">
        <div class="card-group">
            <div class="row my-1">
                <div class="col-sm-4 text-center">
                    <div class="card mx-3 categoryCard card1">
                        <div class="card-body cardMiddle">
                            <h3>Start learning Icelandic now by playing the game!</h3>
                            <input type="button" class="btn btn-primary option" onclick="location.href = '/newgame'" id="startGame" value="Select" />
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 text-center">
                    <div class="card mx-3 categoryCard card2">
                        <div class="card-body cardMiddle">
                            <h3>Not sure if you know any Icelandic words? Go to the dictionary for an overview</h3>
                            <input type="button" class="btn btn-primary option" onclick="location.href = '/dictionary'" id="dictionary" value="Select" />
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 text-center">
                    <div class="card mx-3 categoryCard card3">
                        <div class="card-body cardMiddle">
                            <h3>Want to see how you are improving? Check your score!</h3>
                            <input type="button" class="btn btn-primary option" onclick="location.href = '/scoreboard'" id="scoreBoard" value="Select" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>