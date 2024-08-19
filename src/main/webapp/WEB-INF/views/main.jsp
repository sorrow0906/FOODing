<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix = "c"%>
<%@ include file="/WEB-INF/views/includes/cacheControl.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>FOODing 메인화면</title>
    <link rel = "stylesheet" href = "${pageContext.request.contextPath}/resources/css/main_style_section.css" type = "text/css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/slider.js"></script>
</head>
<body>
<c:import url = "/top.jsp" />
<c:if test="${not empty error}">
<script>
    alert('${error}');
</script>
</c:if>
<section>
    <div class = "show-area1">
        <div class="slider-div">
        <button class = "preBtn"></button>
        <ul class = "mainSliderList">
            <li class = "slider mainSlider1 active">
                <a href = "#">
                    <div class = "sliderImg">
                        <img src = "${pageContext.request.contextPath}/resources/images/chefudding.png">
                    </div>
                </a>
            </li>
            <li class = "slider mainSlider2">
                <a href = "#">
                    <div class = "sliderImg">
                        <img src = "${pageContext.request.contextPath}/resources/images/chefudding.png">
                    </div>
                </a>
            </li>
            <li class = "slider mainSlider3">
                <a href = "#">
                    <div class = "sliderImg">
                        <img src = "${pageContext.request.contextPath}/resources/images/chefudding.png">
                    </div>
                </a>
            </li>
        </ul>
        <button class = "nextBtn"></button>
        </div>
    </div>
    <div class="show-area2">
    <div class="category-area">
        <button id="category-title">카테고리별</button>
        <table>
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/storeList?scate=한식">
                    <img src="${pageContext.request.contextPath}/resources/store_images/korean_food_icon.png">
                    <button href="${pageContext.request.contextPath}/storeList?scate=한식">한식</button>
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/storeList?scate=일식">
                    <img src="${pageContext.request.contextPath}/resources/store_images/japanese_food_icon.png">
                    <button href="${pageContext.request.contextPath}/storeList?scate=일식">일식</button>
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/storeList?scate=중식">
                    <img src="${pageContext.request.contextPath}/resources/store_images/chinese_food_icon.png">
                    <button href="${pageContext.request.contextPath}/storeList?scate=중식">중식</button>
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/storeList?scate=양식">
                    <img src="${pageContext.request.contextPath}/resources/store_images/western_food_icon.png">
                    <button href="${pageContext.request.contextPath}/storeList?scate=한식">양식</button>
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/storeList?scate=세계요리">
                    <img src="${pageContext.request.contextPath}/resources/store_images/global_food_icon.png">
                    <button>세계요리</button>
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/storeList?scate=빵/디저트">
                    <img src="${pageContext.request.contextPath}/resources/store_images/dessert_icon.png">
                    <button>빵/디저트</button>
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/storeList?scate=차/커피">
                    <img src="${pageContext.request.contextPath}/resources/store_images/coffee_icon.png">
                    <button>차/커피</button>
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/storeList?scate=술집">
                    <img src="${pageContext.request.contextPath}/resources/store_images/pub_icon2.png">
                    <button>술집</button>
                    </a>
                </td>
            </tr>
        </table>
    </div>
        <div class="groupshow-area">
            <h1 id="grouparea-title">내가 참여한 모임</h1>
            <c:forEach var="memberGroup" items="${myMemberGroups}" varStatus="status">
                <p style="color: #dddddd">--------------------------------------------------------</p>
            <div class="each-group-area">
                <img class="group-img" src="${pageContext.request.contextPath}/resources/images/group-thumbnail1.png"/>
                <table class="group-table">
                    <tbody>
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${memberGroup.jauth == 1}">
                                    ★
                                </c:when>
                                <c:otherwise>
                                    ☆
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="#">${memberGroup.group.gname}</a></td>
                        <td>
                            <c:forEach var="entry" items="${leaderList}">
                                <c:if test="${entry.key == memberGroup.group.gno}">
                                    ${entry.value}
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" align="center">
                            <c:forEach var="entry" items="${allMemberList}">
                                <c:if test="${entry.key == memberGroup.group.gno}">
                                    ${entry.value}
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            </c:forEach>
        </div>
    </div>
</section>
<c:import url = "/bottom.jsp" />