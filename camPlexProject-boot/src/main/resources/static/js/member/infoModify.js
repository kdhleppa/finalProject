const checkObj = {
	"inputNickname": false
};

// 프로필 이미지 추가/변경/삭제
const profileImg = document.getElementById("profileImg"); // img 태그
const imageInput = document.getElementById("uploadProfileImg"); // input 태그
const deleteImage = document.getElementById("deleteProfileImg"); // x버튼

const afterImg = document.getElementById("afterImg"); // 기존 이미지 저장

let initCheck; // 초기 프로필 이미지 상태를 저장하는 변수

let deleteCheck = -1;
// 프로필 이미지가 새로 업로드 되거나 삭제 되었음을 나타내는 변수

let originalImage; // 초기 프로필 이미지 파일 경로 저장

if (imageInput != null) {

	originalImage = profileImg.getAttribute("src");

	if (profileImg.getAttribute("src") == "/images/memberImg/gg_profile.png") {
		initCheck = false;
	} else {
		initCheck = true;
	}

	imageInput.addEventListener("change", e => {

		const maxSize = 1 * 1024 * 1024 * 5;

		const file = e.target.files[0];

		if (file == undefined) {
			deleteCheck = -1;

			profileImg.setAttribute("src", originalImage);

			imageInput.setAttribute("value", originalImage);

			return;
		}

		if (file.size > maxSize) {
			alert("5MB 이하의 이미지를 선택해주세요.");
			imageInput.value = "";
			deleteCheck = -1;

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


	deleteImage.addEventListener('click', () => {
		imageInput.value = "";
		afterImg.value = "";

		profileImg.setAttribute("src", "/images/memberImg/gg_profile.png");

		deleteCheck = 0;
	});

}

// 닉네임 유효성 검사
const inputNickname = document.getElementById("inputNickname");
const nickMessage = document.getElementById('nickMessage');

// 기존 닉네임 담을 변수
const existingNickname = document.getElementById("inputNickname").value;

// 닉네임이 입력이 되었을 때
inputNickname.addEventListener("input", () => {

	// 닉네임 입력이 되지 않은 경우
	if (inputNickname.value.trim() == '') {
		nickMessage.innerText = "한글, 영어, 숫자 2~10글자";
		nickMessage.classList.remove("confirm", "error");
		checkObj.inputNickname = false;
		inputNickname.value = "";
		return;
	}

	// 정규표현식으로 유효성 검사
	const regEx = /^[가-힣\w\d]{2,10}$/;

	if (regEx.test(inputNickname.value)) {// 유효

		fetch("/dupCheck/nickname?nickname=" + inputNickname.value)
			.then(resp => resp.text()) // 응답 객체를 text로 파싱(변환)
			.then(count => {

				if (count == 0) { // 중복 아닌 경우
					nickMessage.innerText = "사용 가능한 닉네임 입니다";
					nickMessage.classList.add("confirm");
					nickMessage.classList.remove("error");
					checkObj.inputNickname = true;

				} else { // 중복인 경우

					if (existingNickname == inputNickname.value) {
						// 기존 닉네임과 같은 닉네임인 경우 == 사용가능
						nickMessage.innerText = "기존 닉네임 사용하기";
						nickMessage.classList.add("confirm");
						nickMessage.classList.remove("error");
						checkObj.inputNickname = true;
					} else {
						// 기존 닉네임 외 다른 사용자의 닉네임인 경우 == 사용불가
						nickMessage.innerText = "이미 사용중인 닉네임 입니다";
						nickMessage.classList.add("error");
						nickMessage.classList.remove("confirm");
						checkObj.inputNickname = false;
					}
				}
			})
			.catch(err => console.log(err));




	} else { // 무효
		nickMessage.innerText = "닉네임 형식이 유효하지 않습니다";
		nickMessage.classList.add("error");
		nickMessage.classList.remove("confirm");
		checkObj.inputNickname = false;
	}

});



// form태그가 제출 되었을 때
document.getElementById("updateMember").addEventListener("submit", e => {

	let flag = true;

	if (!initCheck && deleteCheck == 1) flag = false;

	if (initCheck && deleteCheck == 1) flag = false;

	if (initCheck && deleteCheck == 0) flag = false;

	if (initCheck && deleteCheck == -1) flag = false;


	if (flag) {
		e.preventDefault();
		alert("이미지 변경 후 클릭하세요");
	}

	for (let key in checkObj) {

		if (!checkObj[key]) {

			switch (key) {
				case "inputNickname":
					alert("닉네임이 유효하지 않습니다"); break;
			}

			document.getElementById(key).focus();

			e.preventDefault();
			return;
		}
	}
});
