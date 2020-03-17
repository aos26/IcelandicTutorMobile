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

<div class="container-fluid">
    <div class="mainContainer">
        <div class="card-group">
            <h2>Select a category</h2>
            <div class="row my-1">
                <div class="col-sm-4 text-center">
                    <div class="card mx-3 categoryCard card1">
                        <div class="card-body cardMiddle">

                            <h1>Animals</h1>
                            <input type="button" class="btn btn-primary option"v onclick="location.href = '/category/1'" id="animals" value="Select" />
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 text-center">
                    <div class="card mx-3 categoryCard card2">
                        <div class="card-body cardMiddle">
                            <h1>Clothing</h1>
                            <input type="button" class="btn btn-primary option" onclick="location.href = '/category/2'" id="clothing" value="Select" />
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 text-center">
                    <div class="card mx-3 categoryCard card3">
                        <div class="card-body cardMiddle">

                            <h1>Numbers and dates</h1>
                            <input type="button" class="btn btn-primary option" id="activity" value="Select" />
                            <h3>Coming soon...</h3>

                        </div>
                    </div>
                </div>
            </div>
            <div class="row my-1" style="margin-top: 20px;">
                <div class="col-sm-4  text-center">
                    <div class="card mx-3 categoryCard card4">
                        <div class="card-body cardMiddle">
                            <h1>Adjectives</h1>
                            <input type="button" class="btn btn-primary option" id="cat1" value="Select" />
                            <h3>Coming soon...</h3>

                        </div>
                    </div>
                </div>
                <div class="col-sm-4 text-center">
                    <div class="card mx-3 categoryCard card5">
                        <div class="card-body cardMiddle">
                            <h1>Sports</h1>
                            <input type="button" class="btn btn-primary option" id="cat2" value="Select" />
                            <h3>Coming soon...</h3>

                        </div>
                    </div>
                </div>
                <div class="col-sm-4 text-center">
                    <div class="card mx-3 categoryCard card6">
                        <div class="card-body cardMiddle">

                            <h1>School</h1>
                            <input type="button" class="btn btn-primary option" id="cat3" value="Select" />
                            <h3>Coming soon...</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>