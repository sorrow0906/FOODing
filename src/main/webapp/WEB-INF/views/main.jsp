<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix = "c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>FOODing 메인화면</title>
    <link rel = "stylesheet" href = "${pageContext.request.contextPath}/resources/css/style.css" type = "text/css">
</head>
<body>
<c:import url = "/top.jsp" />
<section>
    <div class = "section-div">
        <img src = "${pageContext.request.contextPath}/resources/images/chefudding.png">
    </div>
</section>
<c:import url = "/bottom.jsp" />
