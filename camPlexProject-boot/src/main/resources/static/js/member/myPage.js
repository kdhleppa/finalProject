const prev = document.getElementById('left');
const next = document.getElementById('right');
const itemWrapper = document.querySelector('.itemWrapper');
const itemSection = document.querySelectorAll('.itemSection');
const itemSectionlength = document.querySelector('.itemSection');
const slideLength = itemSection.length
let currentIndex = 0;

// ceo 사진 이동
const moveSlide = function (num) {
	itemWrapper.style.transform = `translateX(${-num * 215}px)`;
	currentIndex = num;
}

prev.addEventListener('click', () => {
	if (currentIndex !== 0) {
		moveSlide(currentIndex - 1)
	}
})

next.addEventListener('click', () => {
	if (slideLength <= 4) {
		return;
	}

	if (currentIndex !== slideLength - 4) {
		moveSlide(currentIndex + 1)
	}

})