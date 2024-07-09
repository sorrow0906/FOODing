<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel = "stylesheet" href = "${pageContext.request.contextPath}/resources/css/main_style_header.css" type = "text/css">
<link href = "https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link rel = "stylesheet" href = "${pageContext.request.contextPath}/resources/css/main_style_nav.css" type = "text/css">
<link rel = "stylesheet" href = "${pageContext.request.contextPath}/resources/css/main_style_footer.css" type = "text/css">
<script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src = "${pageContext.request.contextPath}/resources/js/nav_hover.js"></script>

<header>
    <div class = "header-div">
        <div class = "hello1"></div>
        <a class = "header1" href = "main">FOOD</a>
        <a class = "header2" href = "main">ing</a>
        <a class = "header2" href = "main">
            <img src = "${pageContext.request.contextPath}/resources/images/chefudding.png" width = "100px" height = "100px">
        </a>
        <div class = "hello2">
            <table border = "0" align = "center">
                <tr>
                    <td align = "center">
                        [닉네임]님, 안녕하세욧!
                    </td>
                </tr>
                <tr>
                    <td align = "center">
                        <span>
                            <a class = "bell" href = "#">
                                <img src = "${pageContext.request.contextPath}/resources/images/bell.png" width = "30px" height = "30px">
                            </a>
                        </span>
                        <span>|</span>
                        <span>
                            마이페이지
                        </span>
                        <span>|</span>
                        <span>
                            로그아웃
                        </span>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</header>
<nav>
    <div class = "nav-div">
        <a class = "nav" href = "#">음식점 카테고리</a>
        <a class = "nav" href="${pageContext.request.contextPath}/storeList">가게리스트</a>
        <a class = "nav" href = "#">모임</a>
        <a class = "nav" href = "#">찜</a>
        <form class="d-flex">
            <div class = "search-form">
                <input class="form-control me-2" type="search" placeholder="가게를 검색하세욧" aria-label="Search">
                <a class = "btn btn-link" href = "#" role = "button">
                    <img src = "${pageContext.request.contextPath}/resources/images/search.png" alt="Search">
                </a>
            </div>
        </form>
        <ul class = "snb">
            <div class = "submenu">
                <li><a href = "#">한식</a></li>
                <li><a href = "#">일식</a></li>
                <li><a href = "#">중식</a></li>
                <li><a href = "#">양식</a></li>
                <li><a href = "#">세계요리</a></li>
                <li><a href = "#">빵/디저트</a></li>
                <li><a href = "#">차/커피</a></li>
                <li><a href = "#">술집</a></li>
            </div>
            <div class="submenu">
                <li><a href="#">위치별</a></li>
                <li><a href="#">순위별</a></li>
                <li><a href="#">태그별</a></li>
            </div>
            <div class = "submenu">
                <li><a href = "#">모임 기능1</a></li>
                <li><a href = "#">모임 기능2</a></li>
                <li><a href = "#">모임 기능3</a></li>
            </div>
            <div class = "submenu">
                <li><a href = "#">찜 기능1</a></li>
                <li><a href = "#">찜 기능2</a></li>
                <li><a href = "#">찜 기능3</a></li>
            </div>
        </ul>
    </div>
</nav>
