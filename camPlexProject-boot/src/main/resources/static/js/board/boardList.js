const insertBtn = document.querySelector("#insertBtn");

// 글쓰기 버튼 클릭 시
if(insertBtn != null) { // 로그인 여부에 따라 insertBtn에 있는가 없는가에 대한 예외 처리
	insertBtn.addEventListener("click", () => {
		
		if(memberType != 'M' && location.pathname.split("/")[2] == 'N') {
			alert("공지사항은 관리자만 작성 가능합니다.")
			return;
		}
		
		location.href = `/board2/${location.pathname.split("/")[2]}/insert`;

	});
}

document.addEventListener('DOMContentLoaded', function () {
    const currentSortType = /*[[${param.typeCheck}]]*/ ''; // 이전에 선택된 정렬 유형
    const currentBoardType = /*[[${boardType}]]*/ ''; // 현재 게시판 유형
    const queryParams = new URLSearchParams(window.location.search);

    // 이전에 선택된 정렬 유형이 있으면 해당 라디오 버튼을 체크
    if (currentSortType) {
        document.querySelector('input[name="typeCheck"][value="' + currentSortType + '"]').checked = true;
    }

    // 라디오 버튼 변경 이벤트 리스너 추가
    document.querySelectorAll('input[name="typeCheck"]').forEach(function (radio) {
        radio.addEventListener('change', function () {
            const newSortType = this.value;

            // 현재 게시판 타입과 선택된 정렬 유형을 이용하여 새로운 URL을 생성
            const newUrl = '/board/' + currentBoardType + '?cp=1&typeCheck=' + newSortType;
            window.location.href = newUrl;
        });
    });
});
