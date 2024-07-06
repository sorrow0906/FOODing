<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/top.jsp" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/storeList.css">
<section class = "content">
<table class="store-table">
 <thead>
 <tr>
  <th>가게명</th>
  <th>음식점 종류</th>
  <th>주소</th>
  <th>영업시간</th>
  <th>주차장</th>
 </tr>
 </thead>
 <tbody>
 <c:forEach var="store" items="${stores}">
  <tr>
   <td>${store.sname}</td>
   <td>${store.scate}</td>
   <td>${store.saddr}</td>
   <td>${store.stime}</td>
   <td>${store.spark}</td>
  </tr>
 </c:forEach>
 </tbody>
</table>
</section>
<c:import url="/bottom.jsp" />