const cqmodal_btn = document.getElementById("cqmodal_btn");

cqmodal_btn.addEventListener('click', e => {
	
	if(loginMemberNo != null){
		cqmodalOpen();
		e.preventDefault();
		return;
    } else {
		alert("로그인 후 이용해주세요.");
        return;
	}
	
});

// 모달 열기
function cqmodalOpen() {
    document.querySelector('.cqmodal_wrap').style.display = 'block';
    document.querySelector('.cqmodal_background').style.display = 'block';
    
}

// 모달 끄기
function cqmodalClose() {
    document.querySelector('.cqmodal_wrap').style.display = 'none';
    document.querySelector('.cqmodal_background').style.display = 'none';
}

//버튼 클릭리스너 달기
document.querySelector('.cqmodal_close').addEventListener('click', cqmodalClose);

const cqmodalContainer = document.getElementById("cqmodalContainer");
const cqmodalBack = document.getElementById("cqmodalBack");


(function(){
	document.addEventListener('keydown', function(e){
		const keyCode = e.keyCode;
		
		if(keyCode == 27){
			cqmodalContainer.style.display = 'none';
			cqmodalBack.style.display = 'none';
		}
	})
	
})();

const ceoQnaFrm = document.getElementById("ceoQnaFrm");
const ceoQnaTitle = document.getElementById("ceoQnaTitle");
const ceoQnaContent = document.getElementById("ceoQnaContent");

	ceoQnaFrm.addEventListener('submit', e => {
	
	if(ceoQnaTitle.value.trim().length == 0){
		alert("제목을 입력해주세요")
        ceoQnaTitle.value = "";
        ceoQnaTitle.focus();
        e.preventDefault();
        return;
	}
	
	if(ceoQnaContent.value.trim().length == 0){
		alert("내용을 입력해주세요")
        ceoQnaContent.value = "";
        ceoQnaContent.focus();
        e.preventDefault();
        return;
	}
	
	alert("문의가 접수되었습니다.")
	document.querySelector("[name='insertCeoQna']").value
	 = Array.from(insertCeoQna);
	e.preventDefault();
	cqmodalClose();
	return;
});

