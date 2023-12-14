const qmodal_btn = document.getElementById("qmodal_btn");

qmodal_btn.addEventListener('click', e => {
	
	if(loginMemberNo != null){ 
		qmodalOpen();
		e.preventDefault();
		return;
    } else {
		alert("로그인 후 이용해주세요.");
        return;
	}
	
});

// 모달 열기
function qmodalOpen() {
    document.querySelector('.qmodal_wrap').style.display = 'block';
    document.querySelector('.qmodal_background').style.display = 'block';
    
}

// 모달 끄기
function qmodalClose() {
    document.querySelector('.qmodal_wrap').style.display = 'none';
    document.querySelector('.qmodal_background').style.display = 'none';
}


//버튼 클릭리스너 달기
document.querySelector('.qmodal_close').addEventListener('click', qmodalClose);

const qmodalContainer = document.getElementById("qmodalContainer");
const qmodalBack = document.getElementById("qmodalBack");


(function(){
	document.addEventListener('keydown', function(e){
		const keyCode = e.keyCode;
		
		if(keyCode == 27){
			qmodalContainer.style.display = 'none';
			qmodalBack.style.display = 'none';
		}
	})
	
})();

const qnaFrm = document.getElementById("qnaFrm");
const QNATitle = document.getElementById("QNATitle");
const QNAContent = document.getElementById("QNAContent");

	qnaFrm.addEventListener('submit', e => {
	
	if(QNATitle.value.trim().length == 0){
		alert("제목을 입력해주세요")
        QNATitle.value = "";
        QNATitle.focus();
        e.preventDefault();
        return;
	}
	
	if(QNAContent.value.trim().length == 0){
		alert("내용을 입력해주세요")
        QNAContent.value = "";
        QNAContent.focus();
        e.preventDefault();
        return;
	}
	
	alert("문의가 접수되었습니다.")
	document.querySelector("[name='insertQNA']").value
	 = Array.from(insertQNA);
	e.preventDefault();
	qmodalClose();
	return;
});

