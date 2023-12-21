const boardTypeCheck = document.getElementById("boardTypeCheck").value;
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



