const modalDeleteBtn = document.getElementById("modalDeleteBtn");
const modalContainer = document.getElementById("modalContainer");
const enrollSiteBtn = document.getElementById('enrollSiteBtn');

// 모달 창 내리기
modalDeleteBtn.addEventListener('click', () => {
	modalContainer.classList.add('hidden');
});

// 모달 창 띄우기
enrollSiteBtn.addEventListener('click', () => {
	modalContainer.classList.remove('hidden');
});

// 모달 창 esc버튼 누르면 사라짐
(function(){
  document.addEventListener('keydown', function(e){
    const keyCode = e.keyCode;

    if(keyCode == 27){
      modalContainer.classList.add('hidden');
    }
  })
})();

// 모달 창 썸네일 이미지
const previewcampingSiteThumbnail = document.getElementById('previewcampingSiteThumbnail');
const campingSiteThumbnailInput = document.getElementById('campingSiteThumbnailInput');

campingSiteThumbnailInput.addEventListener('change', e => {
	
	const file = e.target.files[0];
	
	if(file != undefined) {
		
		const reader = new FileReader();
		
		reader.readAsDataURL(file);
		
		reader.onload = e => {
			previewcampingSiteThumbnail.setAttribute("src", e.target.result);
		}
		
	} else {
		previewcampingSiteThumbnail.removeAttribute("src");
	}
	
});

// 모달 창 더 보기 이미지

// 더 보기 이미지 버튼
const etcImgPlusInput = document.getElementById('etcImgPlusInput');

// 더 보기 이미지 div
const moreImgDiv = document.getElementById('moreImgDiv');


etcImgPlusInput.addEventListener('change', e => {
	
	const file = e.target.files[0]; // 파일 데이터
	
	if(file != undefined) {
		
		const reader = new FileReader();
		
		reader.readAsDataURL(file); // result변수에 URL형식 저장
		
		reader.onload = e => {
			
			const moreImgDetailSec = document.createElement('section');
			moreImgDetailSec.classList.add('moreImgDetailSec');
			
			const moreImgDetailDiv = document.createElement('div');
			moreImgDetailDiv.classList.add('moreImgDetailDiv');
			
			const moreImg = document.createElement('img');
			moreImg.classList.add('moreImg');
			
			
			moreImgDetailDiv.append(moreImg);
			moreImgDetailSec.append(moreImgDetailDiv);
			
			moreImgDiv.append(moreImgDetailSec);
			
			moreImg.setAttribute("src", e.target.result);
			
		}
		
	}
	
});

// 더 보기 이미지 삭제 버튼
const campUploadModalDeleteBtn = document.getElementById('campUploadModalDeleteBtn');

campUploadModalDeleteBtn.addEventListener('click', () => {
	moreImgDiv.innerHTML = '';
});










