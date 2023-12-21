// 미리보기 관련 요소 모두 얻어오기

// img 5개
const preview = document.getElementsByClassName("preview"); 

// file 5개
const inputImage = document.getElementsByClassName("inputImage"); 

// x버튼 5개
const deleteImage = document.getElementsByClassName("deleteImgDiv"); 

// -> 위에 얻어온 요소들의 개수가 같음 == 인덱스가 일치함


for(let i=0 ; i< inputImage.length ; i++){

    // 파일이 선택되거나, 선택 후 취소 되었을 때
    inputImage[i].addEventListener('change', e => {

        const file = e.target.files[0]; // 선택된 파일의 데이터

        if(file != undefined){ // 파일이 선택 되었을 때

            const reader = new FileReader(); // 파일을 읽는 객체

            reader.readAsDataURL(file);
            // 지정된 파일을 읽은 후 result 변수에 URL 형식으로 저장

            reader.onload = e => { // 파일을 다 읽은 후 수행
                preview[i].setAttribute("src", e.target.result);
            }

        } else { // 선택 후 취소 되었을 때
                // -> 선택된 파일 없음 -> 미리보기 삭제
            preview[i].removeAttribute("src");
        }
    });


    // 미리보기 삭제 버튼(X버튼)
    deleteImage[i].addEventListener('click', () => {

        // 미리보기 이미지가 있을 경우
        if(preview[i].getAttribute("src") != ""){

            // 미리보기 삭제
            preview[i].removeAttribute("src");

            // input type="file" 태그의 value를 삭제
            // **  input type="file" 의 value는 ""(빈칸)만 대입 가능 **
            inputImage[i].value = "";  
        }

    });
}

// 제출 시 비어있는 input값 확인

// 업로드 버튼 form
const campingDetailUploadForm = document.getElementById('campingDetailUploadForm');


// 캠핑명
const inputCampingName = document.getElementById('inputCampingName');

// 캠핑장 설명
const introduceTextArea = document.getElementById('introduceTextArea');

// 매너 타임
const mannerTimeInput = document.getElementById('mannerTimeInput');

// Etc Info
const campAddress = document.getElementById('campAddress');
const campPhone = document.getElementById('campPhone');
const checkIn = document.getElementById('checkIn');
const checkOut = document.getElementById('checkOut');
const CEONum = document.getElementById('CEONum');
const etcInfoTextArea = document.getElementById('etcInfoTextArea');
const categorySelect = document.getElementById('categorySelect');
const campOption = document.querySelectorAll('input[name=campOption]');
const campAroundView = document.querySelectorAll('input[name=campAroundView]');

campingDetailUploadForm.addEventListener('submit', e => {
	
	if(inputCampingName.value.trim() == "") {
		let inputCampingNameTop= inputCampingName.offsetTop;
		alert("캠핑장 이름을 입력해주세요.");
		inputCampingName.focus();
		window.scrollTo({top:inputCampingName, behavior:'smooth'});
		e.preventDefault();
		return;
	}
	
	for(var i = 0 ; i < preview.length - 1 ; i++){

		if(preview[i].getAttribute('src') == "" || preview[i].getAttribute('src') == null){
			let previewSc = preview[i].offsetTop;
			alert(i+1 + "번째 사진을 입력해주세요");
			preview[i].focus();
			window.scrollTo({top:previewSc - 320, behavior:'smooth'});
			e.preventDefault();
			return;
		}
	}

	if(introduceTextArea.value.trim() == "") {
		let introduceTextAreaSc = introduceTextArea.offsetTop;
		alert("캠핑장에 대한 설명을 입력해주세요. ");
		introduceTextArea.focus();
		window.scrollTo({top:introduceTextAreaSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}
	
	const optionArr = [];

	for(var i = 0 ; i < campOption.length ; i ++){

		if(campOption[i].checked == true){
			optionArr.push(campOption[i])
		};
	}

	if(optionArr == ""){
		let iputImgOfDetailMapSc = document.getElementById('iputImgOfDetailMap').offsetTop;
		alert("한 개 이상의 부대 시설을 선택해주세요");
		window.scrollTo({top:iputImgOfDetailMapSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}

	const viewArr = [];

	for(var i = 0 ; i < campAroundView.length ; i ++){

		if(campAroundView[i].checked == true){
			viewArr.push(campAroundView[i])
		};
	}
	if(viewArr == ""){
		let iputImgOfDetailMapSc = document.getElementById('iputImgOfDetailMap').offsetTop;
		alert("한 개 이상의 주변 환경을 선택해주세요");
		window.scrollTo({top:iputImgOfDetailMapSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}

	if(mannerTimeInput.value.trim() == "") {
		let mannerTimeInputSc = mannerTimeInput.offsetTop;
		alert("매너 타임을 입력해주세요.");
		mannerTimeInput.focus();
		window.scrollTo({top:mannerTimeInputSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}
	
	if(campAddress.value.trim() == "") {
		let campAddressSc = campAddress.offsetTop;
		alert("캠핑장 주소를 입력해주세요.");
		campAddress.focus();
		window.scrollTo({top:campAddressSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}

	const addressRegex = /^(경기도|강원도|충청북도|충청남도|경상북도|경상남도|전라북도|전라남도|제주특별자치도|서울특별시|부산광역시|대구광역시|인천광역시|광주광역시|대전광역시|울산광역시)\s[가-힣]+(시|군|구)?/;

	
	if(!addressRegex.test(campAddress.value)) {
		let campAddressSc = campAddress.offsetTop;
		alert("캠핑장 주소가 잘못되었습니다.");
		campAddress.focus();
		window.scrollTo({top:campAddressSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}

	if(campPhone.value.trim() == "") {
		let campPhoneSc = campPhone.offsetTop;
		alert("캠핑장 전화 번호를 입력해주세요.");
		campPhone.focus();
		window.scrollTo({top:campPhoneSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}

	const regEx = /^[0-9]+$/;
	if(!regEx.test(campPhone.value)) {
		let campPhoneSc = campPhone.offsetTop;
		alert("전화 번호는 숫자만 입력해주세요.");
		campPhone.focus();
		window.scrollTo({top:campPhoneSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}
	
	if(checkIn.value.trim() == "") {
		let checkInSc = checkIn.offsetTop;
		alert("체크인 시간을 입력해주세요.");
		checkIn.focus();
		window.scrollTo({top:checkInSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}
	
	if(checkOut.value.trim() == "") {
		let checkOutSc = checkOut.offsetTop;
		alert("체크아웃 시간을 입력해주세요.");
		checkOut.focus();
		window.scrollTo({top:checkOutSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}

	if(categorySelect.value == "noLoad") {
		let categorySelectSc = categorySelect.offsetTop;
		console.log(categorySelect.Value);
		alert("카테고리를 선택해 주세요.");
		categorySelect.focus();
		window.scrollTo({top:categorySelectSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}
	
	if(CEONum.value.trim() == "") {
		let CEONumSc = CEONum.offsetTop;
		alert("CEO회원 번호를 입력해주세요.");
		CEONum.focus();
		window.scrollTo({top:CEONumSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}

	if(!regEx.test(CEONum.value)) {
		let CEONumSc = CEONum.offsetTop;
		alert("CEO회원 번호는 숫자만 입력해주세요.");
		CEONum.focus();
		window.scrollTo({top:CEONumSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}

	let ceoCheckFlag = document.getElementById('ceoCheckFlag');

	if(ceoCheckFlag.value == "false") {
		let CEONumSc = CEONum.offsetTop;
		alert("CEO회원 번호를 확인해주세요");
		CEONum.focus();
		window.scrollTo({top:CEONumSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}

	// const checkCeo = "";

	// fetch("/camp2/checkCeo?memberNo=" + CEONum.value)
	// .then(resp => resp.text())
	// .then(checkFl => {

	// 	console.log("checkFl ::" + checkFl)

	// 	if(checkFl == "true") {
	// 		checkCeo = "true";
	// 	}else {
	// 		checkCeo = "false";
	// 	}


	// })
	// .catch(err => console.log(err));

	// if(checkCeo == "false") {
	// 	alert("CEO회원의 번호가 아닙니다.");
	// 	CEONum.focus();
	// 	e.preventDefault();
	// 	return;
	// }

	
	
	if(etcInfoTextArea.value.trim() == "") {
		let etcInfoTextAreaSc = etcInfoTextArea.offsetTop;
		alert("캠핑장 Info를 입력해주세요.");
		etcInfoTextArea.focus();
		window.scrollTo({top:etcInfoTextAreaSc - 320, behavior:'smooth'});
		e.preventDefault();
		return;
	}
	
	
	
});


function checkCeoF() {

	console.log("CEONum ::"+ CEONum.value);

	const ceoCheckFlag = document.getElementById('ceoCheckFlag');

	if(CEONum.value == "") {
		ceoCheckFlag.removeAttribute('value');
		ceoCheckFlag.setAttribute('value', 'false');
		alert("CEO회원의 번호를 입력해주세요.");
		CEONum.focus();
		return;
	}

	fetch("/camp2/checkCeo?memberNo=" + CEONum.value)
	.then(resp => resp.text())
	.then(checkFl => {

		console.log("checkFl ::" + checkFl);

		if(checkFl == "false") {
			ceoCheckFlag.removeAttribute('value');
			ceoCheckFlag.setAttribute('value', 'false');
			alert("CEO회원의 번호가 아닙니다.");
			CEONum.focus();
			CEONum.value = "";
			e.preventDefault();
		}else {
			console.log("ceoCheckFlag ::" + ceoCheckFlag.value);
			ceoCheckFlag.removeAttribute('value');
			ceoCheckFlag.setAttribute('value', 'true');
			console.log("ceoCheckFlag ::" + ceoCheckFlag.value);
			alert("CEO회원의 번호가 맞습니다.");
		}

	})
	.catch(err => console.log(err));

}










