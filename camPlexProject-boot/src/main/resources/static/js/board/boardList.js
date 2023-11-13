const insertBtn = document.querySelector("#insertBtn");

// 글쓰기 버튼 클릭 시
if(insertBtn != null) { // 로그인 여부에 따라 insertBtn에 있는가 없는가에 대한 예외 처리
	insertBtn.addEventListener("click", () => {
		// 해당 주소로 요청을 보내주세요
		// JS 객체중 location
		// location.href="주소"
		// 해당 주소로 요청(GET방식)
		location.href = `/board2/${location.pathname.split("/")[2]}/insert`;
	});
}