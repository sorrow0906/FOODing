<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset = "UTF-8">
        <title>FOODing 메인화면</title>
        <link rel = "stylesheet" href = "${pageContext.request.contextPath}/resources/css/style.css" type = "text/css">
    </head>
    <body>
        <header>
            <div class = "header-div">
                <a class = "header1" href = "main">FOOD</a>
                <a class = "header2" href = "main">ing</a>
                <a class = "header2" href = "main">
                    <img src = "${pageContext.request.contextPath}/resources/images/chefudding.png" width = "100px" height = "100px">
                </a>
            </div>
        </header>
        <nav>
            <div class = "nav-div">
                <a class = "nav" href="#">음식점 카테고리</a>
                <a class="nav" href="${pageContext.request.contextPath}/store">가게리스트</a>
                <a class = "nav" href = "#">모임</a>
                <a class = "nav" href = "#">찜</a>
                <a class = "nav" href = "#">검색</a>
            </div>
        </nav>
