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
	
	moreImgDiv.innerHTML = '';
	
	const file = e.target.files;
	
	console.log(file);
	
	if(file != undefined) {
		
		
		for(let i = 0; i < file.length; i++) {
			
			const file = e.target.files[i];
			
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
			
	}
		
	
});

// 더 보기 이미지 삭제 버튼
const campUploadModalDeleteBtn = document.getElementById('campUploadModalDeleteBtn');

campUploadModalDeleteBtn.addEventListener('click', () => {
	moreImgDiv.innerHTML = '';
});


// 모달 등록하기 버튼
const modalUploadBtn = document.getElementById('modalUploadBtn');

const campDeName = document.getElementById('campDeName');
const capacity = document.getElementById('capacity');
const fullCapacity = document.getElementById('fullCapacity');
const campDePrice = document.getElementById('campDePrice');


modalUploadBtn.addEventListener('click', e => {
	
	if(campDeName.value == "") {
		alert("구역명(호수)를 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(capacity.value == "") {
		alert("수용 인원을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(fullCapacity.value == "") {
		alert("최대 인원을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(campDePrice.value == "") {
		alert("구역명(호수)의 가격을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	// 모달에서 디테일 업로드 페이지로 정보 ajax로 보내기
	
	const campDeThumbnail = document.getElementById('campingSiteThumbnailInput');
	
	
	fetch("/camp2/insertDeCamp?campDeThumbnail=" + campDeThumbnail + "&campDeName=" + campDeName
	 + "&capacity=" + capacity + "&fullCapacity" + fullCapacity + "&campDePrice" + campDePrice)
	.then(resp => resp.json())
	.then(list => {
		
		// Enroll Site 정보를 감싸고 있는 Sec
		const enrollSiteSec = document.getElementById('enrollSiteSec');
		
		
	});

});











