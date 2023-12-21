const checkObj = {
    "inputPw" : false,
    "inputPwRe" : false
};

// ----------------------------------
// 비밀번호 재설정 페이지 JS

// 비밀번호/비밀번호 확인 유효성 검사
const inputPw = document.getElementById("inputPw");
const inputPwRe = document.getElementById("inputPwRe");
const pwMessage = document.getElementById("pwMessage");

// 비밀번호 입력 시 유효성 검사
inputPw.addEventListener("input", () => {

    // 비밀번호가 입력되지 않은 경우
    if(inputPw.value.trim().length == 0){
        inputPw.value = ""; // 띄어쓰지 못넣게 하기

        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자";
        pwMessage.classList.remove("confirm", "error"); // 검정 글씨

        checkObj.inputPw = false; // 빈칸 == 유효 X
        return;
    }


    // 정규 표현식을 이용한 비밀번호 유효성 검사

    // 영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이
    const regEx = /^[a-zA-Z0-9\!\@\#\-\_]{6,20}$/;

    // 입력한 비밀번호가 유효한 경우
    if(regEx.test(inputPw.value)){
        checkObj.inputPw = true; 
        
        // 비밀번호가 유효하게 작성된 상태에서
        // 비밀번호 확인이 입력되지 않았을 때
        if(inputPwRe.value.trim().length == 0){

            pwMessage.innerText = "사용 가능한 비밀번호입니다";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");
        
        }else{
            // 비밀번호가 유효하게 작성된 상태에서
            // 비밀번호 확인이 입력되어 있을 때

            // 비밀번호 == 비밀번호 확인  (같을 경우)
            if(inputPw.value == inputPwRe.value){
                pwMessage.innerText = "비밀번호가 일치합니다";
                pwMessage.classList.add("confirm");
                pwMessage.classList.remove("error");
                checkObj.inputPwRe = true;
                
            } else{ // 다를 경우
                pwMessage.innerText = "비밀번호가 일치하지 않습니다";
                pwMessage.classList.add("error");
                pwMessage.classList.remove("confirm");
                checkObj.inputPwRe = false;
            }
        }

        
    } else{ // 유효하지 않은 경우
        
        pwMessage.innerText = "비밀번호 형식이 유효하지 않습니다";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        checkObj.inputPw = false; 
    }
});


// 비밀번호 확인 유효성 검사
inputPwRe.addEventListener('input', ()=>{

    if(checkObj.inputPw){ // 비밀번호가 유효하게 작성된 경우에

        // 비밀번호 == 비밀번호 확인  (같을 경우)
        if(inputPw.value == inputPwRe.value){
            pwMessage.innerText = "비밀번호가 일치합니다";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");
            checkObj.inputPwRe = true;
            
        } else{ // 다를 경우
            pwMessage.innerText = "비밀번호가 일치하지 않습니다";
            pwMessage.classList.add("error");
            pwMessage.classList.remove("confirm");
            checkObj.inputPwRe = false;
        }

    } else { // 비밀번호가 유효하지 않은 경우
        checkObj.inputPwRe = false;
    }
});






