<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <a class="nav" href="${pageContext.request.contextPath}/storeList">가게리스트</a>
        <a class = "nav" href = "#">모임</a>
        <a class = "nav" href = "#">찜</a>
        <a class = "nav" href = "#">검색</a>
    </div>
</nav>
