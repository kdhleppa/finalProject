 document.addEventListener('DOMContentLoaded', () => {
	if(loginMemberNo == null || memberType != 'C' ){
        alert("CEO만 이용가능합니다")
        window.history.back();
    }
})

const ceoQnaAnswerFrm = document.getElementById("ceoQnaAnswerFrm");
const ceoQnaAnswer = document.getElementById("ceoQnaAnswer");

	ceoQnaAnswerFrm.addEventListener('submit', e => {
	
	if(ceoQnaAnswer.value.trim().length == 0){
		alert("답변을 입력해주세요")
        ceoQnaAnswer.value = "";
        ceoQnaAnswer.focus();
        e.preventDefault();
        return;
	}
	
	alert("답변이 완료되었습니다.")
	document.querySelector("[name='insertAnswer']").value
	 = Array.from(insertAnswer);
	e.preventDefault();
	return;
});


const divListContainer = document.getElementById('divListContainer')
const formContainer = document.getElementById('formContainer')
const divList = document.getElementsByClassName("divList");

document.getElementById('loadMoreBtn').addEventListener('click', () => {
    
    console.log(divListContainer.offsetHeight)
    const temp = divList[0].offsetHeight;

    formContainer.style.height = divListContainer.offsetHeight + temp + 80+ "px";
    divListContainer.style.height = divListContainer.offsetHeight + temp + 80 +"px";

});