function deleteCampWish(campNo) {
    if (confirm('이 캠핑장을 정말 삭제하시겠습니까?')) {
        fetch('/member/deleteCampWish/' + campNo, { 
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data && data.message === "삭제 성공") {
                // 성공적으로 삭제되었을 경우, 페이지에서 해당 행을 제거
                var tr = document.getElementById('tr' + campNo);
                if (tr) {
                    tr.remove();
                }
                alert(data.message); // 성공 메시지 표시
            } else {
                // 삭제 실패 메시지 표시
                alert(data ? data.message : '삭제에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('삭제 중 에러 발생:', error);
            alert('삭제 중 문제가 발생했습니다.');
        });
    }
}



function deleteItemWish(itemNo) {
	
    if (confirm('이 상품을 정말 삭제하시겠습니까?')) {
        fetch('/member/deleteItemWish/' + itemNo, { 
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data && data.message === "삭제 성공") {
                // 성공적으로 삭제되었을 경우, 페이지에서 해당 행을 제거
                var tr = document.getElementById('tr' + itemNo);
                if (tr) {
                    tr.remove();
                }
                
                alert(data.message); // 성공 메시지 표시
            } else {
                // 삭제 실패 메시지 표시
                alert(data ? data.message : '삭제에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('삭제 중 에러 발생:', error);
            alert('삭제 중 문제가 발생했습니다.');
        });
    }
}


function selectAll(groupClass) {
    var selectAllCheckbox = document.getElementById('selectAll' + groupClass.charAt(0).toUpperCase() + groupClass.slice(1));
    var checkboxes = document.querySelectorAll('.' + groupClass + '-item');
    
    checkboxes.forEach(function(checkbox) {
        checkbox.checked = selectAllCheckbox.checked;
    });
}

	
function deleteSelectedItems() {
    var selectedCamps = document.querySelectorAll('.campcb-item:checked');
    var selectedItems = document.querySelectorAll('.itemcb-item:checked');

    if (selectedCamps.length === 0 && selectedItems.length === 0) {
        alert('삭제할 항목을 선택해주세요.');
        return;
    }

    if (!confirm('정말 삭제하시겠습니까?')) {
        return;
    }

    var deleteRequests = [];

    // 캠핑장에 대한 삭제 요청
    selectedCamps.forEach(checkbox => {
        var campNo = checkbox.getAttribute('data-ccn');
        deleteRequests.push(fetch('/member/deleteCampWish/' + campNo, { method: 'DELETE' }).then(response => response.json()));
    });

    // 상품에 대한 삭제 요청
    selectedItems.forEach(checkbox => {
        var itemNo = checkbox.getAttribute('data-icn');
        deleteRequests.push(fetch('/member/deleteItemWish/' + itemNo, { method: 'DELETE' }).then(response => response.json()));
    });

    // 모든 삭제 요청 처리
    Promise.all(deleteRequests)
        .then(results => {
            var allSuccess = results.every(result => result.message === "삭제 성공");
            if (allSuccess) {
                selectedCamps.forEach(checkbox => checkbox.parentElement.parentElement.remove());
                selectedItems.forEach(checkbox => checkbox.parentElement.parentElement.remove());
                alert('모든 선택된 항목이 성공적으로 삭제되었습니다.');
            } else {
                alert('일부 항목 삭제에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('삭제 중 에러 발생:', error);
            alert('삭제 중 문제가 발생했습니다.');
        });
}

