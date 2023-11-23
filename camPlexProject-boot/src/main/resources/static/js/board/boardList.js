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


document.addEventListener("DOMContentLoaded", function () {
    // 라디오 버튼에 이벤트 리스너 추가
    var radioButtons = document.querySelectorAll('input[name="typeCheck"]');
    
    radioButtons.forEach(function (radioButton) {
        radioButton.addEventListener('change', function () {
            // 선택된 라디오 버튼의 값 확인
            var selectedValue = document.querySelector('input[name="typeCheck"]:checked').value;

            // 선택된 값에 따라 최신순 정렬 수행
            if (selectedValue === 'new') {
                // 여기에 최신순 정렬을 위한 로직을 추가하세요.
                // 예를 들어, Ajax 호출을 통해 서버에 최신순으로 정렬된 목록을 요청할 수 있습니다.
                // 그 후, 반환된 데이터로 화면을 갱신합니다.
                console.log("최신순 정렬 수행");
            }
        });
    });
});