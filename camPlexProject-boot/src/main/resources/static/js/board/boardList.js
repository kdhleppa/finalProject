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
