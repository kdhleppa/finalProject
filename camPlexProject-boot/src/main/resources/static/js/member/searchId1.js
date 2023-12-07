const checkObj = {
	"inputName": false,
	"inputTel": false,
	"inputAuthkey": false
};



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