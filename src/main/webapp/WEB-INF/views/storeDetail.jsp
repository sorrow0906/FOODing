<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FOODing 가게 디테일</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/storeDetail.css">
</head>
<body>
<!-- 상단 내비게이션 바 -->
<c:import url="/top.jsp" />

<section>
    <div class="store-div">
        <img id="store-img" src="${pageContext.request.contextPath}/resources/images/korean_food.jpg">
        <div class="store-head">
            <p id="store-title">${store.sname}</p>
            <p>${store.scate}</p>
            <p id="store-explain">${store.seg}</p>
        </div>
        <div class="store-info-map">
            <div class="store-info">
                <h2 class="menu-title">메뉴</h2>
                <table class="menu-table">
                    <thead>
                    <tr>
                        <td>메뉴이름</td>
                        <td>가격</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="menu" items="${menus}">
                        <tr>
                            <td>${menu.mnname}</td>
                            <td>${menu.mnprice}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <h2>영업시간</h2>
                <p>${store.stime}</p>
                <h2>주차</h2>
                <p>${store.spark}</p>
                <h2>전화번호</h2>
                <p>${store.stel}</p>
            </div>
            <div id="map-container">
                <p id="store-address">${store.saddr}</p>
                <div id="map"></div>
            </div>
        </div>
    </div>
</section>

<!-- 하단 내비게이션 바 -->
<c:import url="/bottom.jsp" />
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=573fb4487497eb28636a2f91b5ca8f70&libraries=services"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };

        // 지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();

        // 주소로 좌표를 검색합니다
        geocoder.addressSearch("${store.saddr}", function(result, status) {

            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {

                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
                });
                infowindow.open(map, marker);

                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
            }
        });
    });
</script>
</body>
</html>
