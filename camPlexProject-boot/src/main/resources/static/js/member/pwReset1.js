const checkObj = {
    "inputEmail" : false,
    "inputAuthkey" : false
};

// 이메일 유효성 검사
const inputEmail = document.getElementById("inputEmail");
const emailMessage = document.getElementById("emailMessage");

// 이메일이 입력될 때 마다
inputEmail.addEventListener("input", () => {

    // 입력된 이메일이 없을 경우
    if(inputEmail.value.trim().length == 0){
        inputEmail.value = ""; 

        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";

        // confirm, error 클래스 삭제해서 검정 글씨로 만들기
        emailMessage.classList.remove("confirm", "error");

        checkObj.inputEmail = false; // 빈칸 == 유효 X
        return;
    }


    // 정규 표현식을 이용해서 유효한 형식이지 판별
    // 1) 정규표현식 객체 생성
    const regEx = /^[A-Za-z\d\-\_]{4,}@[가-힣\w\-\_]+(\.\w+){1,3}$/;

    // 2) 입력 받은 이메일과 정규식 일치 여부 판별
    if(  regEx.test(inputEmail.value)  ){ // 유효한 경우

        /* fetch() API를 이용한 ajax(비동기 통신) : 이메일 중복*/
		//   url:   /dupCheck/email
		
		// GET 방식 
		fetch("/dupCheck/email?email=" + inputEmail.value)
		.then(res => res.text())
		.then(count => {
			
			//count : 중복되면 1, 중복 아니면 0
			if(count == 0) {
			 	emailMessage.innerText = "인증 가능한 이메일입니다.";
        		emailMessage.classList.add("confirm"); // .confirm 스타일 적용
        		emailMessage.classList.remove("error"); // .error 스타일 제거
        		checkObj.inputEmail = true;
			}
			
		})
		.catch(err => console.log(err));
		

    } else{ // 유효하지 않은 경우(무효인 경우)
        emailMessage.innerText = "이메일 형식이 유효하지 않습니다";
        emailMessage.classList.add("error"); // .error 스타일 적용
        emailMessage.classList.remove("confirm"); // .confirm 스타일 제거

        checkObj.inputEmail = false; // 유효 X
    }
});


// --------------------- 이메일 인증 ---------------------
// 인증번호 발송
const sendAuthKeyBtn = document.getElementById("sendAuthkey");
const authKeyMessage = document.getElementById("authKeyMessage");
let authTimer;
let authMin = 4;
let authSec = 59;

// 인증번호를 발송한 이메일 저장
let tempEmail;

sendAuthKeyBtn.addEventListener("click", function(){

    authMin = 4;
    authSec = 59;

    checkObj.authKey = false;

    if(checkObj.inputEmail){ // 중복이 아닌 이메일인 경우


        /* fetch() API 방식 ajax */
        fetch("/sendEmail/sendEmail?email="+inputEmail.value)
        .then(resp => resp.text())
        .then(result => {
            if(result > 0){
                console.log("인증 번호가 발송되었습니다.")
                tempEmail = inputEmail.value;
            }else{
                console.log("인증번호 발송 실패")
            }
        })
        .catch(err => {
            console.log("이메일 발송 중 에러 발생");
            console.log(err);
        });

        alert("인증번호가 발송 되었습니다.");

        
        authKeyMessage.innerText = "05:00";
        authKeyMessage.classList.remove("confirm");

        authTimer = window.setInterval(()=>{
													// 삼항연산자  :  조건 	  ?   	true : false
            authKeyMessage.innerText = "0" + authMin + ":" + (authSec < 10 ? "0" + authSec : authSec);
            
            // 남은 시간이 0분 0초인 경우
            if(authMin == 0 && authSec == 0){
                checkObj.authKey = false;
                clearInterval(authTimer);
                return;
            }

            // 0초인 경우
            if(authSec == 0){
                authSec = 60;
                authMin--;
            }


            authSec--; // 1초 감소

        }, 1000)

    } else {
		alert("유효한 이메일을 입력한 후 인증버튼을 눌러주세요.");
	}

});


// 인증 확인
const authKey = document.getElementById("inputAuthkey");
const checkAuthKeyBtn = document.getElementById("checkEmail");

checkAuthKeyBtn.addEventListener("click", function(){

    if(authMin > 0 || authSec > 0){ // 시간 제한이 지나지 않은 경우에만 인증번호 검사 진행
        /* fetch API */
        const obj = {"inputKey":authKey.value, "email":tempEmail}
        const query = new URLSearchParams(obj).toString()
        // inputKey=123456&email=user01
        

        fetch("/sendEmail/checkAuthKey?" + query)
        .then(resp => resp.text())
        .then(result => {
            if(result > 0){
                clearInterval(authTimer);
                authKeyMessage.innerText = "인증되었습니다.";
                authKeyMessage.classList.add("confirm");
                checkObj.inputAuthkey = true;

            } else{
                alert("인증번호가 일치하지 않습니다.")
                checkObj.inputAuthkey = false;
            }
        })
        .catch(err => console.log(err));


    } else{
        alert("인증 시간이 만료되었습니다. 다시 시도해주세요.")
    }

});


// 회원 가입 form태그가 제출 되었을 때
document.getElementById("pwReset1").addEventListener("submit", e => {

	for (let key in checkObj) {

		if (!checkObj[key]) {

			switch (key) {
				case "inputEmail":
					alert("이메일이 유효하지 않습니다"); break;

				case "inputAuthkey":
					alert("인증번호가 유효하지 않습니다"); break;
			}

			document.getElementById(key).focus();

			e.preventDefault();
			return;
		}
	}
});
