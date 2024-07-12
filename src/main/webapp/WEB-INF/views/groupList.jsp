<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FOODing 모임관리</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/groupList.css">
</head>
<body>
<c:import url="/top.jsp"/>
<section>
    <div class="group-box">
        <div class="group-list-area">
            <h1>모임 리스트</h1>
            <table class="group-table">
                <thead>
                <tr>
                    <th>번호</th>
                    <th>모임명</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="group" items="${groups}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td><a href="#">${group.gname}</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <form:form name="group-createForm" action="${pageContext.request.contextPath}/groupList" modelAttribute="group" method="post" onsubmit="return validateForm()">
            <div class="group-create-area">
                <h1>모임 생성</h1>
                <table class="group-form">
                    <tr>
                        <td><form:label path="gname">모임명</form:label></td>
                        <td><form:input path="gname" /></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><input type="submit" value="생성" /></td>
                    </tr>
                </table>
            </div>
        </form:form>
    </div>
</section>
<c:import url="/bottom.jsp"/>