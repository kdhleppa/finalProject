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
		alert("캠핑장 이름을 입력해주세요.");
		inputCampingName.focus();
		e.preventDefault();
		return;
	}
	
	for(var i = 0 ; i < preview.length - 1 ; i++){

		if(preview[i].getAttribute('src') == "" || preview[i].getAttribute('src') == null){
			alert(i+1 + "번째 사진을 입력해주세요");
			preview[i].focus();
			e.preventDefault();
			return;
		}
	}

	if(introduceTextArea.value.trim() == "") {
		alert("캠핑장에 대한 설명을 입력해주세요. ");
		introduceTextArea.focus();
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
		alert("한 개 이상의 부대 시설을 선택해주세요");
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
		alert("한 개 이상의 주변 환경을 선택해주세요");
		e.preventDefault();
		return;
	}

	if(mannerTimeInput.value.trim() == "") {
		alert("매너 타임을 입력해주세요.");
		mannerTimeInput.focus();
		e.preventDefault();
		return;
	}
	
	if(campAddress.value.trim() == "") {
		alert("캠핑장 주소를 입력해주세요.");
		campAddress.focus();
		e.preventDefault();
		return;
	}
	
	if(campPhone.value.trim() == "") {
		alert("캠핑장 번호를 입력해주세요.");
		campPhone.focus();
		e.preventDefault();
		return;
	}

	const regEx = /^[0-9]+$/;
	if(!regEx.test(campPhone.value)) {
		alert("전화 번호는 숫자만 입력해주세요.");
		campPhone.focus();
		e.preventDefault();
		return;
	}
	
	if(checkIn.value.trim() == "") {
		alert("체크인 시간을 입력해주세요.");
		checkIn.focus();
		e.preventDefault();
		return;
	}
	
	if(checkOut.value.trim() == "") {
		alert("체크아웃 시간을 입력해주세요.");
		checkOut.focus();
		e.preventDefault();
		return;
	}

	if(categorySelect.value == "noLoad") {
		console.log(categorySelect.Value);
		alert("카테고리를 선택해 주세요.");
		categorySelect.focus();
		e.preventDefault();
		return;
	}
	
	if(CEONum.value.trim() == "") {
		alert("CEO회원 번호를 입력해주세요.");
		CEONum.focus();
		e.preventDefault();
		return;
	}

	if(!regEx.test(CEONum.value)) {
		alert("CEO회원 번호는 숫자만 입력해주세요.");
		campPhone.focus();
		e.preventDefault();
		return;
	}
	
	if(etcInfoTextArea.value.trim() == "") {
		alert("캠핑장 Info를 입력해주세요.");
		etcInfoTextArea.focus();
		e.preventDefault();
		return;
	}
	
	
	
});












