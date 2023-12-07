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

var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#msg").html("");
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/public', function (message) {
            showMessage("받은 메시지: " + message.body); //서버에 메시지 전달 후 리턴받는 메시지
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    let message = $("#msg").val()
    showMessage("보낸 메시지: " + message);

    stompClient.send("/app/sendMessage", {}, JSON.stringify(message)); //서버에 보낼 메시지
}

function showMessage(message) {
    $("#communicate").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});


 function scrollToBottom() {
      var colmd12 = document.getElementById('colmd12');
      colmd12.scrollTop = colmd12.scrollHeight;
    }

    // 테이블 내용을 동적으로 추가하는 함수 (예시)
    function addTableRow() {
      var table = document.getElementById('myTable');
      var newRow = table.insertRow(-1);
      var cell = newRow.insertCell(0);
      cell.textContent = 'New Row';

      // 스크롤을 항상 맨 아래로 이동
      scrollToBottom();
    }

    // 페이지 로드 후 초기에 실행
    window.addEventListener('load', scrollToBottom);

    // 테이블 내용을 동적으로 추가하는 예시 (행 추가 버튼을 클릭할 때마다 호출)
    var addButton = document.createElement('button');
    addButton.textContent = 'Add Row';
    addButton.addEventListener('click', addTableRow);

    document.body.appendChild(addButton);

