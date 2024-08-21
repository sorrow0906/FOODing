<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix = "c"%>
<%@ include file="/WEB-INF/views/includes/cacheControl.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>FOODing 모임 게시판</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board.css">

</head>
<body>
<c:import url = "/top.jsp" />
<section class="board-section">
    <div class="board-container">

        <h1>${board.bname}</h1>
        <c:forEach var="boardWrite" items="${boardWrite}" varStatus="status">
            <div class="btnwrite">
                <button><a href="${pageContext.request.contextPath}/write?bno=${boardWrite.bno}">글쓰기</a></button>
            </div>
        </c:forEach>
        <c:choose>
        <c:when test="${writes==null}">
            <p>게시물이 존재하지 않습니다.</p>
        </c:when>
        <c:otherwise>
        <table>
            <thead>
                <td>번호</td>
                <td>글제목</td>
                <td>글쓴이</td>
                <td>작성날짜</td>
            </thead>
            <tbody>

            <c:forEach var="write" items="${writes}">
                    <tr>
                        <td>${write.wno}</td>
                        <td><a href="#">${write.wtitle}</a></td>
                        <td>${write.member.mnick}</td>
                        <td>${write.dateToString}</td>
                    </tr>
            </c:forEach>
            </c:otherwise>
            </c:choose>
            </tbody>
        </table>
        <div class="btnupdate"><button>수정</button></div>

        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <span>${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="?page=${i}&size=${size}&gno=${board.group.gno}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <input type="text">
        <span class="btnsearch"><button>검색</button></span>
    </div>
</section>
<c:import url = "/bottom.jsp" />