const insertBtn = document.querySelector("#insertBtn");

// 글쓰기 버튼 클릭 시
if(insertBtn != null) { // 로그인 여부에 따라 insertBtn에 있는가 없는가에 대한 예외 처리
	insertBtn.addEventListener("click", () => {
		// 해당 주소로 요청을 보내주세요
		// JS 객체중 location
		// location.href="주소"
		// 해당 주소로 요청(GET방식)
		location.href = `/board2/${location.pathname.split("/")[2]}/insert`;
		//  /board2/B/insert
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
                
				fetch("/board/{boardType}(boardType=B)")
                    .then(response => response.json())
                    .then(data => {
                        // 서버에서 받은 데이터로 화면을 갱신
                        updateUIWithData(data);
                    })
                    .catch(error => {
                        console.error('Error fetching data:', error);
                    });
            }
        });
    });

    // 화면을 받은 데이터로 갱신하는 함수
    function updateUIWithData(data) {
        
        console.log("최신순으로 정렬된 목록을 받아와서 화면을 갱신합니다.", data);

        // 예시: 결과를 result 엘리먼트에 표시
        var resultElement = document.getElementById('result');
        resultElement.innerHTML = '최신순으로 정렬된 결과를 표시합니다.';
    }
});