<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/storeDetail.css">
<c:import url="/top.jsp" />
<section class="content">
    <div class="store_info_title" itemprop="name">${store.sname}</div>
    <div id="naviLayer"></div>
    <div class="store_info3 pc_only">
        <div class="store_info3_title">매장소개</div>
        <div class="text">${store.seg}</div>
    </div>
    <div class="store_info">
        <div class="info_text">
            <div class="location location_home"></div>
            <table>
                <tbody>
                <tr>
                    <th><h3>영업시간</h3></th>
                    <td>
                        <div class="inline-div"><div class="inline-div">주말</div><div class="inline-div"><label>${store.stime}</label></div></div>
                        <div class="inline-div"><div class="inline-div">주중</div><div class="inline-div"><label>${store.stime}</label></div></div>
                        <div class="inline-div"><span class="label close">CLOSE</span></div>
                    </td>
                </tr>
                <tr>
                    <th><h3>주차</h3></th>
                    <td>${store.spark}</td>
                </tr>
                <tr>
                    <th><h3>주소</h3></th>
                    <td itemprop="address">
                        <div>${store.saddr}</div>
                    </td>
                </tr>
                <tr class="pc_only">
                    <th><h3>전화번호</h3></th>
                    <td itemprop="telephone">
                        <div>${store.stel}</div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="info_map">
            <div class="location location_address"></div>
            <figure>
                <div class="map" id="map" style="height: 90%; background-color: grey;">
                    <!-- 나중에 카카오맵 API를 여기에 추가할 수 있습니다. -->
                </div>
                <a href="#" class="btn">길찾기</a>
            </figure>
        </div>
    </div>
</section>

<c:import url="/bottom.jsp" />
