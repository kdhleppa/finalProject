const checkObj = {
	"inputEmail": false,
	"inputPw": false,
	"inputPwRe": false,
	"inputName": false,
	"inputNickname": false,
	"inputTel": false,
	"inputAuthkey": false
};

// 이메일 유효성 검사
const inputEmail = document.getElementById("inputEmail");
const emailMessage = document.getElementById("emailMessage");

// 이메일이 입력될 때 마다
inputEmail.addEventListener("input", () => {

	// 입력된 이메일이 없을 경우
	if (inputEmail.value.trim().length == 0) {
		inputEmail.value = "";

		emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";

		// confirm, error 클래스 삭제해서 검정 글씨로 만들기
		emailMessage.classList.remove("confirm", "error");

		checkObj.memberEmail = false; // 빈칸 == 유효 X
		return;
	}


	// 정규 표현식을 이용해서 유효한 형식이지 판별
	// 1) 정규표현식 객체 생성
	const regEx = /^[A-Za-z\d\-\_]{4,}@[가-힣\w\-\_]+(\.\w+){1,3}$/;

	// 2) 입력 받은 이메일과 정규식 일치 여부 판별
	if (regEx.test(inputEmail.value)) { // 유효한 경우

		/* fetch() API를 이용한 ajax(비동기 통신) : 이메일 중복*/
		//   url:   /dupCheck/email

		// GET 방식 
		fetch("/dupCheck/email?email=" + inputEmail.value)
			.then(res => res.text())
			.then(count => {

				//count : 중복되면 1, 중복 아니면 0
				if (count == 0) {
					emailMessage.innerText = "사용 가능한 이메일입니다.";
					emailMessage.classList.add("confirm"); // .confirm 스타일 적용
					emailMessage.classList.remove("error"); // .error 스타일 제거
					checkObj.inputEmail = true;
				} else {
					emailMessage.innerText = "이미 사용중인 이메일입니다.";
					emailMessage.classList.add("error"); // .error 스타일 적용
					emailMessage.classList.remove("confirm"); // .confirm 스타일 제거
					checkObj.inputEmail = false;
				}

			})
			.catch(err => console.log(err));


	} else { // 유효하지 않은 경우(무효인 경우)
		emailMessage.innerText = "이메일 형식이 유효하지 않습니다";
		emailMessage.classList.add("error"); // .error 스타일 적용
		emailMessage.classList.remove("confirm"); // .confirm 스타일 제거

		checkObj.inputEmail = false; // 유효 X
	}
});



// 비밀번호/비밀번호 확인 유효성 검사
const inputPw = document.getElementById("inputPw");
const inputPwRe = document.getElementById("inputPwRe");
const pwMessage = document.getElementById("pwMessage");

// 비밀번호 입력 시 유효성 검사
inputPw.addEventListener("input", () => {

	// 비밀번호가 입력되지 않은 경우
	if (inputPw.value.trim().length == 0) {
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
	if (regEx.test(inputPw.value)) {
		checkObj.inputPw = true;

		// 비밀번호가 유효하게 작성된 상태에서
		// 비밀번호 확인이 입력되지 않았을 때
		if (inputPwRe.value.trim().length == 0) {

			pwMessage.innerText = "사용 가능한 비밀번호입니다";
			pwMessage.classList.add("confirm");
			pwMessage.classList.remove("error");

		} else {
			// 비밀번호가 유효하게 작성된 상태에서
			// 비밀번호 확인이 입력되어 있을 때

			// 비밀번호 == 비밀번호 확인  (같을 경우)
			if (inputPw.value == inputPwRe.value) {
				pwMessage.innerText = "비밀번호가 일치합니다";
				pwMessage.classList.add("confirm");
				pwMessage.classList.remove("error");
				checkObj.inputPwRe = true;

			} else { // 다를 경우
				pwMessage.innerText = "비밀번호가 일치하지 않습니다";
				pwMessage.classList.add("error");
				pwMessage.classList.remove("confirm");
				checkObj.inputPwRe = false;
			}
		}


	} else { // 유효하지 않은 경우

		pwMessage.innerText = "비밀번호 형식이 유효하지 않습니다";
		pwMessage.classList.add("error");
		pwMessage.classList.remove("confirm");
		checkObj.inputPw = false;
	}
});


// 비밀번호 확인 유효성 검사
inputPwRe.addEventListener('input', () => {

	if (checkObj.inputPw) { // 비밀번호가 유효하게 작성된 경우에

		// 비밀번호 == 비밀번호 확인  (같을 경우)
		if (inputPw.value == inputPwRe.value) {
			pwMessage.innerText = "비밀번호가 일치합니다";
			pwMessage.classList.add("confirm");
			pwMessage.classList.remove("error");
			checkObj.inputPwRe = true;

		} else { // 다를 경우
			pwMessage.innerText = "비밀번호가 일치하지 않습니다";
			pwMessage.classList.add("error");
			pwMessage.classList.remove("confirm");
			checkObj.inputPwRe = false;
		}

	} else { // 비밀번호가 유효하지 않은 경우
		checkObj.inputPwRe = false;
	}
});


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

		nameMessage.innerText = "사용 가능한 이름 입니다";
		nameMessage.classList.add("confirm");
		nameMessage.classList.remove("error");
		checkObj.inputName = true;

	} else { // 무효
		nameMessage.innerText = "이름 형식이 유효하지 않습니다";
		nameMessage.classList.add("error");
		nameMessage.classList.remove("confirm");
		checkObj.inputName = false;
	}

});

// 닉네임 유효성 검사
const inputNickname = document.getElementById("inputNickname");
const nickMessage = document.getElementById('nickMessage');

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
					nickMessage.innerText = "이미 사용중인 닉네임 입니다";
					nickMessage.classList.add("error");
					nickMessage.classList.remove("confirm");
					checkObj.inputNickname = false;
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


// 전화번호 유효성 검사
const memberTel = document.getElementById("inputTel");
const telMessage = document.getElementById("telMessage");

// 전화번호가 입력 되었을 때
memberTel.addEventListener("input", () => {

	// 전화번호가 입력이 되지 않은 경우
	if (memberTel.value.trim() == '') {
		telMessage.innerText = "- 제외한 전화번호를 입력해주세요.";
		telMessage.classList.remove("confirm", "error");
		checkObj.memberTel = false;
		memberTel.value = "";
		return;
	}

	// 정규표현식으로 유효성 검사
	const regEx = /^0(1[01679]|2|[3-6][1-5]|70)[1-9]\d{2,3}\d{4}$/;

	if (regEx.test(memberTel.value)) {// 유효

		fetch("/dupCheck/phone?phone=" + memberTel.value)
		.then(resp => resp.text())
		.then(count => {

			if (count == 0) {
				telMessage.innerText = "사용 가능한 전화번호입니다";
				telMessage.classList.add("confirm");
				telMessage.classList.remove("error");
				checkObj.memberTel = true;
			} else {
				telMessage.innerText = "이미 사용중인 전화번호입니다";
				telMessage.classList.add("error");
				telMessage.classList.remove("confirm");
				checkObj.memberTel = false;
			}

		})
		.catch(err => console.log(err));

	} else { // 무효
		telMessage.innerText = "전화번호 형식이 유효하지 않습니다";
		telMessage.classList.add("error");
		telMessage.classList.remove("confirm");
		checkObj.memberTel = false;
	}

});

// 전화번호 인증



// 인증번호 확인



// 프로필 이미지 추가/변경/삭제
const profileImg = document.getElementById("profileImg"); // img 태그
const uploadProfileImg = document.getElementById("uploadProfileImg"); // input 태그


let initCheck;


if (uploadProfileImg != null) {

	uploadProfileImg.addEventListener("change", e => {

		const maxSize = 1 * 1024 * 1024 * 5;


		const file = e.target.files[0];

		if (file == undefined) {
			console.log("파일 선택이 취소됨");

			return;
		}

		if (file.size > maxSize) {
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


// 회원 가입 form태그가 제출 되었을 때
document.getElementById("signUpFrm").addEventListener("submit", e => {

	for (let key in checkObj) {

		if (!checkObj[key]) {

			switch (key) {
				case "inputEmail":
					alert("이메일이 유효하지 않습니다"); break;

				case "inputPw":
					alert("비밀번호가 유효하지 않습니다"); break;

				case "inputPwRe":
					alert("비밀번호가 확인되지 않았습니다"); break;

				case "inputName":
					alert("이름이 유효하지 않았습니다"); break;

				case "inputNickname":
					alert("닉네임이 유효하지 않습니다"); break;
			}

			document.getElementById(key).focus();

			e.preventDefault();
			return;
		}
	}
});
