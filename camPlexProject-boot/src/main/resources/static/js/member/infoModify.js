const checkObj = {
    "profileImg" : false,
    "memberNickname" : false
};

// 프로필 이미지 추가/변경/삭제
const profileImg = document.getElementById("profileImg"); // img 태그
const uploadProfileImg = document.getElementById("uploadProfileImg"); // input 태그
const deleteImage = document.getElementById("deleteProfileImg"); // x버튼

let initCheck;

let deleteCheck = -1;

let originalImage; // 초기 프로필 이미지 파일 경로 저장


if(uploadProfileImg != null){
	
    originalImage = profileImg.getAttribute("src"); 

    if(profileImg.getAttribute("src") == "/images/memberImg/gg_profile.png"){
        // 기본 이미지인 경우
        initCheck = false;
    }else{
        initCheck = true;
    }

    uploadProfileImg.addEventListener("change", e => {

        const maxSize = 1 * 1024 * 1024 * 5;


        const file = e.target.files[0];

        if(file == undefined){ 
			console.log("파일 선택이 취소됨");
			
			deleteCheck = -1; // 취소 == 파일 없음 == 초기상태

            profileImg.setAttribute("src", originalImage);

            return;
        }

        if( file.size > maxSize){
            alert("5MB 이하의 이미지를 선택해주세요.");
            uploadProfileImg.value = "";
            
            deleteCheck = -1; // 취소 == 파일 없음 == 초기상태

            profileImg.setAttribute("src", originalImage);
            
            return;
        }

        const reader = new FileReader();

        reader.readAsDataURL(file);

        reader.onload = e => {
			
            const url = e.target.result;

            profileImg.setAttribute("src", url);

            deleteCheck = 1;

        }
    });
    
    // 이미지 삭제 버튼 클릭 시
    deleteProfileImg.addEventListener('click', () => {
        uploadProfileImg.value = "";

        profileImg.setAttribute("src", "/images/memberImg/gg_profile.png");
        
        deleteCheck = 0;
		
    });

}

// 닉네임 유효성 검사
const memberNickname = document.getElementById("inputNickname");
const nickMessage = document.getElementById('nickMessage');

// 닉네임이 입력이 되었을 때
memberNickname.addEventListener("input", ()=>{

    // 닉네임 입력이 되지 않은 경우
    if(memberNickname.value.trim() == ''){
        nickMessage.innerText = "한글, 영어, 숫자 2~10글자";
        nickMessage.classList.remove("confirm", "error");
        checkObj.memberNickname = false;
        memberNickname.value = ""; 
        return;
    }

    // 정규표현식으로 유효성 검사
    const regEx = /^[가-힣\w\d]{2,10}$/;

    if(regEx.test(memberNickname.value)){// 유효

        fetch("/dupCheck/nickname?nickname="+memberNickname.value)
        .then(resp => resp.text()) // 응답 객체를 text로 파싱(변환)
        .then(count => {

            if(count == 0){ // 중복 아닌 경우
                nickMessage.innerText = "사용 가능한 닉네임 입니다";
                nickMessage.classList.add("confirm");
                nickMessage.classList.remove("error");
                checkObj.memberNickname = true;
                
            }else{ // 중복인 경우
                nickMessage.innerText = "이미 사용중인 닉네임 입니다";
                nickMessage.classList.add("error");
                nickMessage.classList.remove("confirm");
                checkObj.memberNickname = false;
            }
        })
        .catch(err => console.log(err));

        


    } else{ // 무효
        nickMessage.innerText = "닉네임 형식이 유효하지 않습니다";
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");
        checkObj.memberNickname = false;
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
    
    if(!initCheck && deleteCheck == -1)   flag = false;
    
    if(initCheck && deleteCheck == -1)   flag = false;

    if(flag){ // flag == true -> 제출하면 안되는 경우
        e.preventDefault(); // form 기본 이벤트 제거
        alert("이미지 변경 후 클릭하세요");
    }

	console.log("test")

    f/*or(let key in checkObj){

        if(!checkObj[key]){ // 각 key에 대한 value(true/false)를 얻어와
                            // false인 경우 == 유효하지 않다!

            switch(key){
            case "memberNickname": 
                alert("닉네임이 유효하지 않습니다"); break;

            }

            // 유효하지 않은 input 태그로 focus 이동
            // - key를 input의 id와 똑같이 설정했음!
            document.getElementById(key).focus();

            e.preventDefault(); // form 태그 기본 이벤트 제거
            return; // 함수 종료
        }
    }*/
});
