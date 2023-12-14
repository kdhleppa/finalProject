const checkObj = {
	"inputName": false,
	"inputTel": false,
	"inputAuthkey": false
};

// 이름 유효성 검사
const inputName = document.getElementById("inputName");
const nameMessage = document.getElementById('nameMessage');

// 이름 입력이 되었을 때
inputName.addEventListener("input", () => {

	// 이름 입력이 되지 않은 경우
	if (inputName.value.trim() == '') {
		nameMessage.innerText = "한글, 영어 2~10글자";
		nameMessage.classList.remove("confirm", "error");
		checkObj.inputName = false;
		inputName.value = "";
		return;
	}

	// 정규표현식으로 유효성 검사
	const regEx = /^[가-힣\w]{2,10}$/;

	if (regEx.test(inputName.value)) {// 유효

		nameMessage.innerText = "유효한 이름입니다.";
		nameMessage.classList.add("confirm");
		nameMessage.classList.remove("error");
		checkObj.inputName = true;

	} else { // 무효
		nameMessage.innerText = "이름 형식이 유효하지 않습니다.";
		nameMessage.classList.add("error");
		nameMessage.classList.remove("confirm");
		checkObj.inputName = false;
	}

});

// 전화번호 유효성 검사
const inputTel = document.getElementById("inputTel");
const telMessage = document.getElementById("telMessage");

// 전화번호가 입력 되었을 때
inputTel.addEventListener("input", () => {

	// 전화번호가 입력이 되지 않은 경우
	if (inputTel.value.trim() == '') {
		telMessage.innerText = "- 제외한 전화번호를 입력해주세요.";
		telMessage.classList.remove("confirm", "error");
		checkObj.inputTel = false;
		inputTel.value = "";
		return;
	}

	// 정규표현식으로 유효성 검사
	const regEx = /^0(1[01679]|2|[3-6][1-5]|70)[1-9]\d{2,3}\d{4}$/;

	if (regEx.test(inputTel.value)) {// 유효

		fetch("/dupCheck/phone?phone=" + inputTel.value)
		.then(resp => resp.text())
		.then(count => {

			if (count > 0) {
				telMessage.innerText = "등록된 전화번호입니다.";
				telMessage.classList.add("confirm");
				telMessage.classList.remove("error");
				checkObj.inputTel = true;
			} else {
				telMessage.innerText = "등록된 정보가 없습니다.";
				telMessage.classList.add("error");
				telMessage.classList.remove("confirm");
				checkObj.inputTel = false;
			}

		})
		.catch(err => console.log(err));

	} else { // 무효
		telMessage.innerText = "전화번호 형식이 유효하지 않습니다.";
		telMessage.classList.add("error");
		telMessage.classList.remove("confirm");
		checkObj.inputTel = false;
	}

});

// 전화번호 인증
const checkTel = document.getElementById("sendAuthkey");
const authkeyMessage = document.getElementById("authkeyMessage");
let tempTel;

checkTel.addEventListener("click", () => {
	
	authMin = 4;
    authSec = 59;
	
	if(checkObj.inputTel) {
		
		alert("인증번호가 발송되었습니다.\n휴대폰에서 확인 해 주세요.");
		
		fetch("/dupCheck/sendAuthKey?phone=" + inputTel.value)
		.then(resp => resp.text())
		.then(count => {

			if (count > 0) {
				telMessage.innerText = "인증번호가 발송되었습니다.";
				tempTel = inputTel.value;
			} else {
				alert("인증번호 발송에 실패했습니다.");
			}

		})
		.catch(err => console.log(err));
		
		authkeyMessage.innerText = "05:00";
        authkeyMessage.classList.remove("confirm");

        authTimer = window.setInterval(()=>{
													// 삼항연산자  :  조건 	  ?   	true : false
            authkeyMessage.innerText = "0" + authMin + ":" + (authSec < 10 ? "0" + authSec : authSec);
            
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
		
	}
	
})

// 전화번호 인증 인증번호 확인
const authKey = document.getElementById("inputAuthkey");
const checkAuthKeyBtn = document.getElementById("checkNumber");

checkAuthKeyBtn.addEventListener("click", function(){

    if(authMin > 0 || authSec > 0){ // 시간 제한이 지나지 않은 경우에만 인증번호 검사 진행
        /* fetch API */
        const obj = {"inputKey":authKey.value, "phone":tempTel}
        const query = new URLSearchParams(obj).toString()
        // inputKey=123456&email=user01

        fetch("/sendAuthKey/checkTelAuthkey?" + query)
        .then(resp => resp.text())
        .then(result => {
            if(result > 0){
                clearInterval(authTimer);
                authkeyMessage.innerText = "인증되었습니다.";
                authkeyMessage.classList.add("confirm");
                checkObj.inputAuthkey = true;

            } else{
                alert("인증번호가 일치하지 않습니다.")
                checkObj.inputAuthkey = false;
            }
        })
        .catch(err => log(err));


    } else{
        alert("인증 시간이 만료되었습니다. 다시 시도해주세요.")
    }

});


document.getElementById("searchId1").addEventListener("submit", e => {

	for (let key in checkObj) {

		if (!checkObj[key]) {

			switch (key) {
				case "inputName":
					alert("이름이 유효하지 않았습니다"); break;

				case "inputTel":
					alert("전화번호가 유효하지 않습니다"); break;
					
				case "inputAuthkey":
					alert("인증번호가 유효하지 않습니다"); break;
			}

			document.getElementById(key).focus();

			e.preventDefault();
			return;
		}
	}
});