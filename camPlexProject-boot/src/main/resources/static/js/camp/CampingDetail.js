// 좋아요 슬라이드
const prev = document.getElementById('left');
const next = document.getElementById('right');
const slideBox = document.querySelector('.ceoPicSlideBox');
const slide = document.querySelectorAll('.ceoPicContent');
const slideLength = slide.length
let currentIndex = 0;

const moveSlide = function (num) {
  slideBox.style.transform = `translateX(${-num * 250}px)`;
  currentIndex = num;
}

prev.addEventListener('click', () => {
  if (currentIndex !== 0) {
    moveSlide(currentIndex - 1)
  }
})

next.addEventListener('click', () => {
  if (currentIndex !== slideLength - 4) {
    moveSlide(currentIndex + 1)
  }

})
