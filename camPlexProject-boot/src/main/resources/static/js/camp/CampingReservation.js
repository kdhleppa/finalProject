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
	console.log(slideLength)
  if (currentIndex !== slideLength-1) {
    moveSlide(currentIndex + 1)
  }

})
