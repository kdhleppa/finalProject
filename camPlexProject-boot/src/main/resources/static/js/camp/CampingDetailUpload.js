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


campingDetailUploadForm.addEventListener('submit', e => {
	
	if(inputCampingName.value == "") {
		alert("캠핑장 이름을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(introduceTextArea.value == "") {
		alert("캠핑장에 대한 설명을 입력해주세요. ");
		e.preventDefault();
		return;
	}
	
	if(mannerTimeInput.value == "") {
		alert("매너 타임을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(campAddress.value == "") {
		alert("캠핑장 주소를 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(campPhone.value == "") {
		alert("캠핑장 번호를 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(checkIn.value == "") {
		alert("체크인 시간을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(checkOut.value == "") {
		alert("체크아웃 시간을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(CEONum.value == "") {
		alert("CEO 넘버를 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(etcInfoTextArea.value == "") {
		alert("캠핑장 Info를 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	
	
});












