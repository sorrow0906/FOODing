<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/pick.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<c:import url="/top.jsp" />
<h2>찜 폴더 관리</h2>
<div class="pick-container">
    <div class="pickList-container">
        <h4>전체 찜 목록</h4>
            <table class="pick-table">
                <c:forEach var="pick" items="${pickList}">
                <tr>
                    <td>
                        <input type="checkbox" name="selectedStore" value="${pick.store.sno}" />
                        <button class="storeName">${pick.store.sname}</button>
                    </td>
                </tr>
                </c:forEach>
            </table>
        <div class="removeButton-container">
            <button type="submit" id="removePick" class="removePick">삭제</button>
        </div>
    </div>
    <div class="addToFolder-container">
        <button type="button" class="add-button" onclick="addPickToFolder()"><img src="${pageContext.request.contextPath}/resources/images/addToFolder.png"></button>
    </div>
    <div class="pickFolders-container">
        <h4>찜 폴더 관리</h4>
        <div class="addFolder-container">
            <form method="post" action="${pageContext.request.contextPath}/createFolder">
                <input type="hidden" name="pfname" value="새 폴더" />
                <button type="submit" class="createFolder">+새 폴더</button>
            </form>
        </div>
        <form id="deleteFolderForm" method="post" action="${pageContext.request.contextPath}/deleteFolder">
            <table class="folder-table">
                <c:forEach var="pfolder" items="${pfolderList}">
                    <tr>
                        <td>
                            <div class="folder-items">
                                <div class="folder-items folder-items-left">
                                    <input type="checkbox" name="selectedFolders" value="${pfolder.pfno}" class="folder-checkbox"/>
                                    <button type="button" class="pfolderName" id="pfname_${pfolder.pfno}" onclick="loadFolderContent('${pfolder.pfno}')">${pfolder.pfname}</button>
                                    <input type="text" class="edit-input" id="edit_${pfolder.pfno}" value="${pfolder.pfname}" style="display: none" />
                                    <button type="button" class="save-pfname" id="save_${pfolder.pfno}" style="display: none" onclick="savePfname('${pfolder.pfno}')">저장</button>
                                    <button type="button" class="cancel-pfname" id="cancel_${pfolder.pfno}" style="display: none" onclick="cancelEdit('${pfolder.pfno}')">취소</button>
                                </div>
                                <div class="folder-items folder-items-right">
                                    <button type="button" class="edit-button" onclick="editPfname('${pfolder.pfno}')">
                                        <img src="${pageContext.request.contextPath}/resources/images/edit_icon.png">
                                    </button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr id="folder-content-${pfolder.pfno}" style="display:none;">
                        <%--<h4>${pfolder.pfname}</h4>--%>
                        <td>
                            <input type="checkbox" name="selectedStores" value="${pfolder.pfno}" class="folder-checkbox"/>
                            <%--<a>${pick.store.sname}</a>--%>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div class="removeButton-container">
                <button type="submit" id="deleteFolder" class="deleteFolder">삭제</button>
            </div>
        </form>
    </div>
</div>
<script>
    document.getElementById("deleteFolder").addEventListener("click", function() {
        var checkboxes = document.querySelectorAll(".folder-checkbox:checked");
        if (checkboxes.length === 0) {
            alert("삭제할 폴더를 선택하세요.");
        } else {
            checkboxes.forEach(function(checkbox) {
            });
            document.getElementById("deleteFolderForm").submit();
        }
    });

    document.getElementById("removePick").addEventListener("click", function() {
        var selectedStores = document.querySelectorAll('.pickList-container input[name="selectedStore"]:checked');

        if (selectedStores.length === 0) {
            alert("삭제할 가게를 선택하세요.");
            return;
        }

        var snos = [];
        selectedStores.forEach(function(store) {
            snos.push(store.value);
        });

        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/removePick',
            data: {
                snos: snos.join(',')
            },
            success: function(response) {
                if (response === "success") {
                    alert('선택된 가게들이 삭제되었습니다.');
                    location.reload();
                } else {
                    alert('삭제 중 오류가 발생했습니다.');
                }
            },
            error: function() {
                alert('삭제 중 오류가 발생했습니다.');
            }
        });
    });

    function editPfname(pfno) {
        document.getElementById('pfname_' + pfno).style.display = 'none';
        document.getElementById('edit_' + pfno).style.display = 'inline';
        document.getElementById('edit_' + pfno).focus();
        document.getElementById('save_' + pfno).style.display = 'inline';
        document.getElementById('cancel_' + pfno).style.display = 'inline';
    }

    function savePfname(pfno) {
        var newName = document.getElementById('edit_' + pfno).value;
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/updateFolderName',
            data: {
                pfno: pfno,
                pfname: newName
            },
            success: function(response) {
                if (response === 'success') {
                    document.getElementById('pfname_' + pfno).innerText = newName;
                    document.getElementById('pfname_' + pfno).style.display = 'inline';
                    document.getElementById('edit_' + pfno).style.display = 'none';
                    document.getElementById('save_' + pfno).style.display = 'none';
                    document.getElementById('cancel_' + pfno).style.display = 'none';
                } else {
                    alert('폴더 이름 변경 중 오류가 발생했습니다.');
                }
            }
        });
    }

    function cancelEdit(pfno) {
        document.getElementById('edit_' + pfno).style.display = 'none';
        document.getElementById('save_' + pfno).style.display = 'none';
        document.getElementById('cancel_' + pfno).style.display = 'none';
        document.getElementById('pfname_' + pfno).style.display = 'inline';
    }

    function loadFolderContent(pfno) {
        var contentRow = document.getElementById('folder-content-' + pfno);
        if (contentRow.style.display === 'table-row') {
            contentRow.style.display = 'none';
            return;
        }

        $.ajax({
            type: 'GET',
            url: '${pageContext.request.contextPath}/getFolderContent',
            data: { pfno: pfno },
            success: function(response) {
                if (response.length === 0) {
                    contentRow.querySelector('td').innerHTML = "폴더가 비어있습니다.";
                } else {
                    var contentHtml = '<ul>';
                    response.forEach(function(pick) {
                        contentHtml += '<li><a href="#">' + pick.store.sname + '</a></li>';
                    });
                    contentHtml += '</ul>';
                    contentRow.querySelector('td').innerHTML = contentHtml;
                }
                contentRow.style.display = 'table-row';
            },
            error: function() {
                alert('폴더 내용을 불러오는 중 오류가 발생했습니다.');
            }
        });
    }

    function addPickToFolder() {
        var selectedStores = document.querySelectorAll('.pickList-container input[name="selectedStore"]:checked');
        var selectedFolders = document.querySelectorAll('.pickFolders-container input[name="selectedFolders"]:checked');

        if (selectedStores.length === 0) {
            alert('추가할 가게를 선택하세요.');
            return;
        }
        if (selectedFolders.length === 0) {
            alert('추가할 찜 폴더를 선택하세요.');
            return;
        }

        var pfnos = [];
        selectedFolders.forEach(function(folder) {
            pfnos.push(folder.value);
        });

        var snos = [];
        selectedStores.forEach(function(store) {
            snos.push(store.value);
        });

        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/addPickToFolder',
            data: {
                pfnos: pfnos.join(','),
                snos: snos.join(',')
            },
            success: function(response) {
                if (response === "success") {
                    alert('선택한 폴더에 찜이 추가되었습니다.');
                    location.reload();
                } else if (response === "error") {
                    alert('한 번에 하나의 폴더에만 추가할 수 있습니다. 폴더를 하나만 선택해주세요.')
                }
            },
            error: function() {
                alert('가게를 폴더에 추가하는 중 오류가 발생했습니다.');
            }
        });
    }

</script>
<c:import url="/bottom.jsp" />