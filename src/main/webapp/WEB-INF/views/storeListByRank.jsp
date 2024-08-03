<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FOODing 가게리스트 - 찜별</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/storeList.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<c:import url="/top.jsp" />
<section class="content">
    <h1>가게 리스트</h1>
    <div class="sort-area">
        <a class="sort-element" id="sort_by_pick" href="#">찜 많은순</a>
        <p class="sort-element">|</p>
        <a class="sort-element" id="sort_by_score" href="#">별점 높은순</a>
    </div>
    <table class="store-table">
        <thead>
        <tr>
            <th>순위</th>
            <th>가게명</th>
            <th>음식점 종류</th>
            <th>주소</th>
            <th>영업시간</th>
            <th>주차장</th>
            <th id="sort-header">
                <c:choose>
                    <c:when test="${sortStandard == 'score'}">별점</c:when>
                    <c:otherwise>찜</c:otherwise>
                </c:choose>
            </th>
        </tr>
        </thead>
        <tbody id="store-list">
        <c:forEach var="store" items="${stores}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td><a href="${pageContext.request.contextPath}/storeDetail?sno=${store.sno}">${store.sname}</a></td>
                <td>
                    <c:choose>
                        <c:when test="${store.scate == '한식'}">
                            <img src="${pageContext.request.contextPath}/resources/store_images/korean_food_icon.png" alt="한식">
                        </c:when>
                        <c:when test="${store.scate == '일식'}">
                            <img src="${pageContext.request.contextPath}/resources/store_images/japanese_food_icon.png" alt="일식">
                        </c:when>
                        <c:when test="${store.scate == '중식'}">
                            <img src="${pageContext.request.contextPath}/resources/store_images/chinese_food_icon.png" alt="중식">
                        </c:when>
                        <c:when test="${store.scate == '양식'}">
                            <img src="${pageContext.request.contextPath}/resources/store_images/western_food_icon.png" alt="양식">
                        </c:when>
                        <c:when test="${store.scate == '세계요리'}">
                            <img src="${pageContext.request.contextPath}/resources/store_images/global_food_icon.png" alt="세계요리">
                        </c:when>
                        <c:when test="${store.scate == '빵/디저트'}">
                            <img src="${pageContext.request.contextPath}/resources/store_images/dessert_icon.png" alt="빵/디저트">
                        </c:when>
                        <c:when test="${store.scate == '차/커피'}">
                            <img src="${pageContext.request.contextPath}/resources/store_images/coffee_icon.png" alt="차/커피">
                        </c:when>
                        <c:when test="${store.scate == '술집'}">
                            <img src="${pageContext.request.contextPath}/resources/store_images/pub_icon.png" alt="술집">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/resources/store_images/global_food_icon.png" alt="기타음식">
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${store.saddr}</td>
                <td>${store.stime}</td>
                <td>
                    <c:choose>
                        <c:when test="${fn:contains(store.spark, '없음') or fn:contains(store.spark, '불가')}">
                            <img src="${pageContext.request.contextPath}/resources/store_images/non_parking2.png" alt="주차 불가">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/resources/store_images/parking.png" alt="주차 가능">
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="sort-value">
                    <c:choose>
                        <c:when test="${sortStandard == 'score'}"><fmt:formatNumber value="${store.scoreArg}" pattern="0.0"/>점</c:when>
                        <c:otherwise>${store.pickNum}</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
<c:import url="/bottom.jsp" />

<script>
    $(document).ready(function() {
        function loadStoreList(sortBy) {
            $.ajax({
                url: '${pageContext.request.contextPath}/storeListByRank',
                type: 'GET',
                data: { sortBy: sortBy },
                success: function(response) {
                    $('#store-list').html($(response).find('#store-list').html());
                    $('#sort-header').html($(response).find('#sort-header').html());
                },
                error: function(xhr, status, error) {
                    console.log('Error loading stores:', status, error);
                }
            });
        }

        $('#sort_by_pick').click(function(event) {
            event.preventDefault();
            loadStoreList('pick');
        });

        $('#sort_by_score').click(function(event) {
            event.preventDefault();
            loadStoreList('score');
        });
    });
</script>
</body>
</html>