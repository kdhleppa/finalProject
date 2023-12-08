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

const conversation = document.querySelector('.col-md-12')

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
}

const msg = document.getElementById('msg');
const send = document.getElementById('send')
const noBtn = document.querySelectorAll('.noBtn')

for(var i = 0 ; i < noBtn.length ; i ++){
	
	noBtn[i].addEventListener("click", (e) => {
			
		msg.value = e.target.value;
		send.click();
		
	})
	
}



function sendMessage() {
    let message = $("#msg").val()
    showMessage("보낸 메시지: " + message);
	conversation.scrollTop = conversation.scrollHeight;
	msg.value ="";
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
