const checkObj = {
    "inputNickname" : false
};

// 프로필 이미지 추가/변경/삭제
const profileImg = document.getElementById("profileImg"); // img 태그
const imageInput = document.getElementById("uploadProfileImg"); // input 태그
const deleteImage = document.getElementById("deleteProfileImg"); // x버튼


let initCheck; // 초기 프로필 이미지 상태를 저장하는 변수
                // false == 기본 이미지,  true == 이전 업로드 이미지

let deleteCheck = -1; 
// 프로필 이미지가 새로 업로드 되거나 삭제 되었음을 나타내는 변수
// -1 == 초기값 ,  0 == 프로필 삭제(x버튼),  1 == 새 이미지 업로드

let originalImage; // 초기 프로필 이미지 파일 경로 저장

if(imageInput != null){ // 화면에 imageInput이 있을 경우 ( if 굳이 안해도 되긴 함 ) 

    // 프로필 이미지가 출력되는 img태그의 src 속성을 저장
    originalImage = profileImg.getAttribute("src"); 


    // 회원 프로필 화면 진입 시 
    // 현재 회원의 프로필 이미지 상태를 확인
    if(profileImg.getAttribute("src") == "/images/memberImg/gg_profile.png"){
        // 기본 이미지인 경우
        initCheck = false;
    }else{
        initCheck = true;
    }


    // change 이벤트 : 값이 변했을 때
    // - input type="file", "checkbox", "radio" 에서 많이 사용
    // - text/number 형식 사용 가능
    //   -> 이 때 input값 입력 후 포커스를 잃었을 때 
    //      이전 값과 다르면 change 이벤트 발생

    imageInput.addEventListener("change", e => {

        // 2MB로 최대 크기 제한 
        const maxSize = 1 * 1024 * 1024 * 2; // 파일 최대 크기 지정(바이트 단위)

        console.log(e.target); // input
        console.log(e.target.value); // 업로드된 파일 경로
        console.log(e.target.files); // 업로드된 파일의 정보가 담긴 배열

        const file = e.target.files[0]; // 업로드한 파일의 정보가 담긴 객체


        // 파일을 한번 선택한 후 취소했을 때 ( file이 undefined가 된다 ) 
        if(file == undefined){ 
            console.log("파일 선택이 취소됨");
            deleteCheck = -1; // 취소 == 파일 없음 == 초기상태

            // 취소 시 기존 프로필 이미지로 변경 ( 기존 이미지에서 변경되는게 없게 하겠다는거죠 ) 
            profileImg.setAttribute("src", originalImage);

            return;
        }

        if( file.size > maxSize){ // 선택된 파일의 크기가 최대 크기를 초과한 경우
            alert("2MB 이하의 이미지를 선택해주세요.");
            imageInput.value = ""; 
            // input type="file" 태그에 대입할 수 있는 value는 "" (빈칸) 뿐이다!
            deleteCheck = -1; // 취소 == 파일 없음 == 초기상태

            // 기존 프로필 이미지로 변경
            profileImg.setAttribute("src", originalImage);

            return;
        }

	
        // JS에서 파일을 읽는 객체
        // - 파일을 읽고 클라이언트 컴퓨터에 파일을 저장할 수 있음 
        const reader = new FileReader();

        reader.readAsDataURL(file);
        // 매개변수에 작성된 파일을 읽어서 저장 후
        // 파일을 나타내는 URL을 result 속성으로 얻어올 수 있게 함.

        // 다 읽었을 때
        reader.onload = e => {
            //console.log(e.target);
            console.log(e.target.result); // 읽은 파일의 URL 
            /* 개발자도구에서 Application탭에서 Frames > top > images 안에서 확인가능 */

            const url = e.target.result;

            // 프로필이미지(img) 태그에 src 속성으로 추가
            profileImg.setAttribute("src", url);

            deleteCheck = 1;
        }
    });


    // x버튼 클릭 시
    deleteImage.addEventListener('click', () => {
        imageInput.value = ""; // input type="file"의 value 삭제

        profileImg.setAttribute("src", "/images/memberImg/gg_profile.png");
        // 프로필 이미지를 기본 이미지로 변경

        deleteCheck = 0;
    });

}

// 닉네임 유효성 검사
const inputNickname = document.getElementById("inputNickname");
const nickMessage = document.getElementById('nickMessage');

// 닉네임이 입력이 되었을 때
inputNickname.addEventListener("input", ()=>{

    // 닉네임 입력이 되지 않은 경우
    if(inputNickname.value.trim() == ''){
        nickMessage.innerText = "한글, 영어, 숫자 2~10글자";
        nickMessage.classList.remove("confirm", "error");
        checkObj.inputNickname = false;
        inputNickname.value = ""; 
        return;
    }

    // 정규표현식으로 유효성 검사
    const regEx = /^[가-힣\w\d]{2,10}$/;

    if(regEx.test(inputNickname.value)){// 유효

        fetch("/dupCheck/nickname?nickname="+inputNickname.value)
        .then(resp => resp.text()) // 응답 객체를 text로 파싱(변환)
        .then(count => {

            if(count == 0){ // 중복 아닌 경우
                nickMessage.innerText = "사용 가능한 닉네임 입니다";
                nickMessage.classList.add("confirm");
                nickMessage.classList.remove("error");
                checkObj.inputNickname = true;
                
            }else{ // 중복인 경우
                nickMessage.innerText = "이미 사용중인 닉네임 입니다";
                nickMessage.classList.add("error");
                nickMessage.classList.remove("confirm");
                checkObj.inputNickname = false;
            }
        })
        .catch(err => console.log(err));

        


    } else{ // 무효
        nickMessage.innerText = "닉네임 형식이 유효하지 않습니다";
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");
        checkObj.inputNickname = false;
    }

});


// 회원 가입 form태그가 제출 되었을 때
document.getElementById("updateMember").addEventListener("submit", e=>{

	let flag = true; // 제출하면 안되는 경우의 초기값 플래그 true로 지정

    // 이전 프로필 이미지가 없으면서, 새 이미지 업로드를 했다 -> 처음으로 이미지 추가
    if(!initCheck && deleteCheck == 1)  flag = false;

    // 이전 프로필 이미지가 있으면서, 새 이미지 업로드를 했다 -> 새 이미지로 변경
    if(initCheck && deleteCheck == 1)   flag = false;
    
    // 이전 프로필 이미지가 있으면서, 프로필 삭제 버튼을 눌렀다 -> 삭제
    if(initCheck && deleteCheck == 0)   flag = false;

    
    if(flag){ // flag == true -> 제출하면 안되는 경우
        e.preventDefault(); // form 기본 이벤트 제거
        alert("이미지 변경 후 클릭하세요");
    }

    for(let key in checkObj){
		
		console.log("test");
		
		console.log(checkObj);
		

        if(!checkObj[key]){ 

            switch(key){
            case "inputNickname" : 
                alert("닉네임이 유효하지 않습니다"); break;
                
            }

            document.getElementById(key).focus();

            e.preventDefault();
            return;
        }
    }
});
