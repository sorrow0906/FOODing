<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<div class = "all-review-div">
    <form:form method="post" action="${pageContext.request.contextPath}/review" modelAttribute="review" id="review">
        <input type="hidden" name="sno" id="sno" value="${sno}" />
        <div class="form-group">
            <div class="star-rating">
                <input type="radio" name="rstar" class="star" id="star5" value="5"><label for="star5"></label>
                <input type="radio" name="rstar" class="star" id="star4" value="4"><label for="star4"></label>
                <input type="radio" name="rstar" class="star" id="star3" value="3"><label for="star3"></label>
                <input type="radio" name="rstar" class="star" id="star2" value="2"><label for="star2"></label>
                <input type="radio" name="rstar" class="star" id="star1" value="1"><label for="star1"></label>
            </div>
        </div>
        <div class="form-group">
            <form:textarea path="rcomm" id="rcomm" class="custom-textarea" placeholder="리뷰 내용을 입력하세요."></form:textarea>
        </div>
        <div class="form group">
            <p>태그를 선택하세요</p>
            <div class="tag-buttons">
                <c:forEach var="tag" items="${tags}">
                    <button type="button" class="tag-button" onclick="toggleTag(${tag.tno}, this)">${tag.ttag}</button>
                </c:forEach>
            </div>
        </div>
        <div class="form-group">
            <button type="submit" class="submit-button">리뷰 작성</button>
        </div>
        <input type="hidden" name="tnos" id="tnos" />

    </form:form>


    <h2>리뷰 목록</h2>

    <c:choose>
        <c:when test="${not empty reviews}">
            <c:forEach var="review" items="${reviews}">
                <div class="review-container">
                    <div class="review-item review-item-left">${review.dateToString}</div>
                    <div class="review-item review-item-left" style="top: 35px;"><strong>${review.member.mnick}</strong></div>
                    <div class="review-item review-item-left" style="top: 60px;">
                        <span class="star-rating">
                            <c:forEach begin="${review.rstar + 1}" end="5" var="i">☆</c:forEach>
                            <c:forEach begin="1" end="${review.rstar}" var="i">★</c:forEach>
                        </span>
                    </div>
                    <div class="review-item-content">
                        <div class="review-item review-content">${review.rcomm}</div>
                        <div class="review-actions-right">
                            <c:if test="${loggedInMember != null && review.member.mno == loggedInMember.mno}">
                                <%--<form method="post" action="${pageContext.request.contextPath}/review/edit" style="display: inline;">
                                    <input type="hidden" name="rno" value="${review.rno}" />
                                    <button type="submit">수정</button>
                                </form>--%>
                                <button type="button" onclick="openEditWindow(${review.rno})">수정</button>
                                <form method="post" action="${pageContext.request.contextPath}/review/delete" style="display: inline;">
                                    <input type="hidden" name="rno" value="${review.rno}" />
                                    <button type="submit">삭제</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                    <div class="review-tags">
                            <c:forEach var="tag" items="${review.tags}">
                                <span class="tag-label">${tag.ttag}</span>
                            </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p class="no-reviews-message">작성된 리뷰가 없습니다.</p>
        </c:otherwise>
    </c:choose>
</div>


