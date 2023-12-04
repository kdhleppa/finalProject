// 모달 열기
function modalOpen() {
    document.querySelector('.modal_wrap').style.display = 'block';
    document.querySelector('.modal_background').style.display = 'block';
    
    var connectButton = document.getElementById("connect");
    connectButton.click();
}

// 모달 끄기
function modalClose() {
    document.querySelector('.modal_wrap').style.display = 'none';
    document.querySelector('.modal_background').style.display = 'none';
}


//버튼 클릭리스너 달기
document.querySelector('#modal_btn').addEventListener('click', modalOpen);
document.querySelector('.modal_close').addEventListener('click', modalClose);

const modalContainer = document.getElementById("modalContainer");
const modalBack = document.getElementById("modalBack");


(function(){
	document.addEventListener('keydown', function(e){
		const keyCode = e.keyCode;
		
		if(keyCode == 27){
			modalContainer.style.display = 'none';
			modalBack.style.display = 'none';
		}
	})
	
})();

function sendMessage() {
        // 입력된 내용을 가져오기
        var message = document.getElementById('msg').value;
        
        // 입력된 내용이 있을 경우에만 처리
        if (message.trim() !== '') {
            // 여기에 메시지 처리 또는 전송 로직을 추가
            console.log('보낸 메시지:', message);

            // 입력된 내용 초기화
            document.getElementById('msg').value = '';
            // 입력란의 placeholder를 초기값으로 되돌림
            document.getElementById('msg').placeholder = '내용을 입력하세요';
        } else {
            // 입력된 내용이 없을 경우에는 경고 메시지를 표시하거나 아무 작업을 하지 않습니다.
            alert('메시지를 입력하세요.');
        }
    }