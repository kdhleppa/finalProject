/*// 전화번호 유효성 검사
const inputTel = document.getElementById("inputTel");
const telMessage = document.getElementById("telMessage");

// 전화번호가 입력 되었을 때
inputTel.addEventListener("input", ()=>{

    // 전화번호가 입력이 되지 않은 경우
    if(inputTel.value.trim() == ''){
        telMessage.innerText = "- 제외";
        telMessage.classList.remove("confirm", "error");
        checkObj.memberTel = false;
        inputTel.value = ""; 
        return;
    }

    // 정규표현식으로 유효성 검사
    const regEx = /^0(1[01679]|2|[3-6][1-5]|70)[1-9]\d{2,3}\d{4}$/;

    if(regEx.test(inputTel.value)){// 유효
        telMessage.innerText = "유효한 전화번호 형식입니다";
        telMessage.classList.add("confirm");
        telMessage.classList.remove("error");
        checkObj.inputTel = true;
        
    } else{ // 무효
        telMessage.innerText = "전화번호 형식이 유효하지 않습니다";
        telMessage.classList.add("error");
        telMessage.classList.remove("confirm");
        checkObj.inputTel = false;
    }


});*/


//휴대폰번호 인증번호 보내기 버튼 클릭 이벤트
/*$('#checkTel').click(function(){
 
	var to = $('input[name="inputTel"]').val();
	$.ajax({
		url : "/memberPhoneCheck",
		type : "POST",
		data : "to=" + to,
		dataType : "json",
		success : function(data) {
			const checkNum = data;
			alert('checkNum:'+ checkNum);
			
            //인증하기 버튼 클릭 이벤트
			$('#certifyCheck').click(function(){
				const userNum = $('input[id="inputAuthkey"]').val();		
				if(checkNum == userNum){
					alert('인증 성공하였습니다.');
				}else {
					alert('인증 실패하였습니다. 다시 입력해주세요.');
				}
			});
            
		},
		error : function() {
			alert("에러")
		}
	});
});*/