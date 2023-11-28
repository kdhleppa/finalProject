// 프로필 이미지 추가/변경/삭제
const profileImg = document.getElementById("profileImg"); // img 태그
const uploadProfileImg = document.getElementById("uploadProfileImg"); // input 태그


if(uploadProfileImg != null){

    uploadProfileImg.addEventListener("change", e => {

        const maxSize = 1 * 1024 * 1024 * 5;


        const file = e.target.files[0];

        if(file == undefined){ 
			console.log("파일 선택이 취소됨");
			
            return;
        }

        if( file.size > maxSize){
            alert("5MB 이하의 이미지를 선택해주세요.");
            uploadProfileImg.value = ""; 
            return;
        }

        const reader = new FileReader();

        reader.readAsDataURL(file);

        reader.onload = e => {
			

            const url = e.target.result;

            profileImg.setAttribute("src", url);

        }
    });
    
    // 이미지 삭제 버튼 클릭 시
    deleteProfileImg.addEventListener('click', () => {
        uploadProfileImg.value = "";

        profileImg.setAttribute("src", "/images/memberImg/gg_profile.png");
		
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
                
            }else{ // 중복인 경우
                nickMessage.innerText = "이미 사용중인 닉네임 입니다";
                nickMessage.classList.add("error");
                nickMessage.classList.remove("confirm");
            }
        })
        .catch(err => console.log(err));

        


    } else{ // 무효
        nickMessage.innerText = "닉네임 형식이 유효하지 않습니다";
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");
    }

});