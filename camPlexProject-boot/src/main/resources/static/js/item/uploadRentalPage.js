// 이미지 미리보기 -----------------------------------

const preview = document.getElementsByClassName("preview");
const inputImage = document.getElementsByClassName("inputImage");

console.log(inputImage);

for(let i=0 ; i< inputImage.length ; i++) {

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

}





const itemUploadForm = document.getElementById('itemUploadForm');

itemUploadForm.addEventListener('submit', (e) => {
	
	const inputItemName = document.getElementById('inputItemName').value;
	const inputItemDescription = document.getElementById('inputItemDescription').value;
	const inputItemPrice = document.getElementById('inputItemPrice').value;
	const inputItemLoanCount = document.getElementById('inputItemLoanCount').value;
	const selectItemType = document.getElementById('selectItemType').value;
	

	if(inputItemName == "") {
		alert("상품명을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(inputItemDescription == "") {
		alert("상품 간단 설명을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(inputItemPrice == "") {
		alert("상품 가격을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(inputItemLoanCount == "") {
		alert("상품 제고 수량을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(selectItemType == 'noSelect') {
		alert("상품 타입을 선택해주세요.");
		e.preventDefault();
		return;
	}
	
});