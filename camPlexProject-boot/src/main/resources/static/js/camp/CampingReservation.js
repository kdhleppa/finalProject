const prev = document.getElementById('left');
const next = document.getElementById('right');
const slideBox = document.querySelector('.detailImgSlideBox');
const slide = document.querySelectorAll('.detailImgContainer');
const slideLength = slide.length
let currentIndex = 0;

const moveSlide = function (num) {
  slideBox.style.transform = `translateX(${-num * 360}px)`;
  currentIndex = num;
}

prev.addEventListener('click', () => {
  if (currentIndex !== 0) {
    moveSlide(currentIndex - 1)
  }
})

next.addEventListener('click', () => {
  if (currentIndex !== slideLength-1) {
    moveSlide(currentIndex + 1)
  }

})


const chooseNumberBtn = document.getElementById("chooseNumberBtn");
const modalContainerPopup = document.getElementById('modalContainerPopup');

chooseNumberBtn.addEventListener("click", () => {

	modalContainerPopup.classList.remove('hidden');

})

window.addEventListener('click', (e) => {

	e.target === modalContainerPopup ? modalContainerPopup.classList.add('hidden') : false
	
});


const adultPlus = document.getElementById('adultPlus')
const adultMinus = document.getElementById('adultMinus')
const adultNumber = document.getElementById('adultNumber')
const kidPlus = document.getElementById('kidPlus')
const kidMinus = document.getElementById('kidMinus')
const kidNumber = document.getElementById('kidNumber')
let adultSum = 0;
let kidSum = 0;

adultPlus.addEventListener("click", ()=>{
	
		if(adultSum + kidSum == fullCapacity){
			alert("수용인원을 초과했습니다.")
			return;
		}
	
		adultNumber.innerText ++;
		adultSum++;
	
})

adultMinus.addEventListener("click", () => {
	
	if(adultSum == 0){
		return;
	}else{
		adultNumber.innerText --;
		adultSum--;
	}
})


kidPlus.addEventListener("click", ()=>{
	
	if(adultSum + kidSum == fullCapacity){
			alert("수용인원을 초과했습니다.")
			return;
		}
	
		kidNumber.innerText ++;
		kidSum++;
	
})

kidMinus.addEventListener("click", () => {
	
	if(kidSum == 0){
		return;
	}else{
		kidNumber.innerText --;
		kidSum--;
	}
})


const confirmNumber = document.getElementById('confirmNumber');
const adultCount = document.getElementById('adultCount');
const kidCount = document.getElementById('kidCount');
const additionalCount = document.getElementById('additionalCount');

confirmNumber.addEventListener("click", () => {
	
	adultCount.value = adultSum;
	kidCount.value = kidSum;
	
	if((adultSum + kidSum) > capacity){
		additionalCount.value = (adultSum + kidSum - capacity);
	}
	
	modalContainerPopup.classList.add('hidden')
})


const reservationFrm = document.getElementById('reservationFrm')
const customerName = document.getElementById('customerName')
const customerTel = document.getElementById('customerTel')

reservationFrm.addEventListener("submit", (e) => {
	
	const regEx = /^[0-9]{10,11}$/;
	
	if(customerName.value == ""){
		alert("이름을 입력해주세요")
		e.preventDefault();
		return;
	} else if(customerTel.value == ""){
		alert("전화번호를 입력해주세요")
		e.preventDefault();
		return;
	} else if(!regEx.test(customerTel.value)){
		alert("유효한 전화번호를 입력해주세요")
		e.preventDefault();
		return;
	} else if (adultCount.value == "0" && kidCount.value == "0"){
		alert("숙박인원을 입력해주세요")
		e.preventDefault();
		return;
	}
	
	
	}
	
	
)
