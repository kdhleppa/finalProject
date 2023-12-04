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

const campDeName = document.getElementById('campDeName').value;
const capacity = document.getElementById('capacity').value;
const fullCapacity = document.getElementById('fullCapacity').value;
const campDePrice = document.getElementById('campDePrice').value;


modalUploadBtn.addEventListener('click', e => {
	

	if(campDeName == null) {
		alert("구역명(호수)를 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(capacity == null) {
		alert("수용 인원을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(fullCapacity == null) {
		alert("최대 인원을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	if(campDePrice == null) {
		alert("구역명(호수)의 가격을 입력해주세요.");
		e.preventDefault();
		return;
	}
	
	// 모달에서 디테일 업로드 페이지로 정보 ajax로 보내기
	
	const campDeImges = document.getElementsByName('campDeImges');
	console.log(campDeName);
		
	fetch("/camp2/insertDeCamp?campDeName=" + campDeName
	 + "&capacity=" + capacity + "&fullCapacity=" + fullCapacity + "&campDePrice=" + campDePrice)
	.then(resp => resp.json())
	.then(map => {
		
		// Enroll Site 정보를 감싸고 있는 Sec
		const enrollSiteSec = document.getElementById('enrollSiteSec');
		enrollSiteSec.classList.add('enrollSiteSec');
		enrollSiteSec.setAttribute('id', enrollSiteSec);
		
		enrollSiteSec.innerHTML = '';
		
		// 장소 추가 append ------------------------------
		
		for(var i in campDetailImageList) {
			
			const plusCampingSiteSec = document.createElement('section');
			plusCampingSiteSec.classList.add('plusCampingSiteSec');
			
			const plusCampingSiteDiv = document.createElement('div');
			plusCampingSiteDiv.classList.add('plusCampingSiteDiv');
			
			const plusCampingSiteDivLeftSec = document.createElement('section');
			plusCampingSiteDivLeftSec.classList.add('plusCampingSiteDivLeftSec');
			
			const plusCampingSampleImg = document.createElement('img');
			plusCampingSampleImg.classList.add('plusCampingSampleImg');
			plusCampingSampleImg.src = campDetailImageList[i].campDeImgPath;
			
			plusCampingSiteDivLeftSec.append(plusCampingSampleImg);
			
			const plusCampingSiteDivRightSec = document.createElement('section');
			plusCampingSiteDivRightSec.classList.add('plusCampingSiteDivRightSec');
			
			const plusCampingSiteDivSiteNameSec = document.createElement('section');
			plusCampingSiteDivSiteNameSec.classList.add('plusCampingSiteDivSiteNameSec');
			plusCampingSiteDivSiteNameSec.innerText = campDetailList.campDeName;
			
			const plusCampingSiteDivEtcInfoSec = document.createElement('section');
			plusCampingSiteDivEtcInfoSec.classList.add('plusCampingSiteDivEtcInfoSec');
			
			const plusCampingSiteDivEtcInfoLeftSec = document.createElement('section');
			plusCampingSiteDivEtcInfoLeftSec.classList.add('plusCampingSiteDivEtcInfoLeftSec');
			
			const capacityTitleSec = document.createElement('section');
			capacityTitleSec.classList.add('capacityTitleSec');
			capacityTitleSec.innerText = '수용 인원';
			
			const capacityOutputSec = document.createElement('section');
			capacityOutputSec.classList.add('capacityOutputSec');
			capacityOutputSec.innerText = campDetailList.capacity;
			
			plusCampingSiteDivEtcInfoLeftSec.append(capacityTitleSec);
			plusCampingSiteDivEtcInfoLeftSec.append(capacityOutputSec);
			
			const plusCampingSiteDivEtcInfoRightSec = document.createElement('section');
			plusCampingSiteDivEtcInfoRightSec.classList.add('plusCampingSiteDivEtcInfoRightSec');
			plusCampingSiteDivEtcInfoRightSec.innerText = campDetailList.campDePrice + '원 ~';
			
			plusCampingSiteDivEtcInfoSec.append(plusCampingSiteDivEtcInfoLeftSec);
			plusCampingSiteDivEtcInfoSec.append(plusCampingSiteDivEtcInfoRightSec);
			
			plusCampingSiteDivRightSec.append(plusCampingSiteDivSiteNameSec);
			plusCampingSiteDivRightSec.append(plusCampingSiteDivEtcInfoSec);
			
			const plusCampingSiteDeleteBtnSec = document.createElement('section');
			plusCampingSiteDeleteBtnSec.classList.add('plusCampingSiteDeleteBtnSec');
			
			const plusCampingSiteDeleteBtn = document.createElement('img');
			plusCampingSiteDeleteBtn.classList.add('plusCampingSiteDeleteBtn');
			plusCampingSiteDeleteBtn.setAttribute('src', '/images/iconImg/campingUploadDelete.png');
			
			plusCampingSiteDeleteBtnSec.append(plusCampingSiteDeleteBtn);
			
			plusCampingSiteDiv.append(plusCampingSiteDivLeftSec);
			plusCampingSiteDiv.append(plusCampingSiteDivRightSec);
			plusCampingSiteDiv.append(plusCampingSiteDeleteBtnSec);
			
			plusCampingSiteSec.append(plusCampingSiteDiv);
			
			enrollSiteSec.append(plusCampingSiteSec);
			
		}
		
		
		modalContainer.classList.add('hidden');
		
		
	})
	.catch(err => console.log(err));
});











