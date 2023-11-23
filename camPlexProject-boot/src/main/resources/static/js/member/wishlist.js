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





