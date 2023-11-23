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
  if(slideLength <= 4){
	  return;
  }
  
  if (currentIndex !== slideLength - 4) {
    moveSlide(currentIndex + 1)
  }

})

const reservationFrm = document.getElementById("reservationFrm");
const campDeNo = document.getElementsByName("campDeNo");

reservationFrm.addEventListener("submit", e => {
	
	var isChecked = false;
	
    for (var i = 0; i < campDeNo.length; i++) {
      if (campDeNo[i].checked) {
        isChecked = true;
        break;
      }
    }

    if (!isChecked) {
      alert("캠핑장을 선택해주세요.");
      e.preventDefault();
      return false;
    }

    return true;
	
})

const ceoPicUploadBtn = document.getElementById("ceoPicUploadBtn");
const modalContainerPopup = document.getElementById('modalContainerPopup');
const closeBtnPopup = document.getElementById('closeBtnPopup');
const campNoHidden = document.getElementById('campNoHidden');


ceoPicUploadBtn.addEventListener("click", () => {

  modalContainerPopup.classList.remove('hidden');
  campNoHidden.value = campNo;
  
})

closeBtnPopup.addEventListener('click', () => {

  modalContainerPopup.classList.add('hidden');
});



const preview = document.getElementsByClassName("preview"); 
const inputImage = document.getElementsByClassName("inputImage"); 
const deleteImage = document.getElementsByClassName("delete-image"); 

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


// ceo사진 삭제

const ceoPicDeleteBtn = document.getElementsByClassName('ceoPicDeleteBtn');
const ceoPicContent = document.getElementsByClassName('ceoPicContent')
const imgNo = document.getElementsByClassName('imgNo')

for(var i = 0 ; i < ceoPicDeleteBtn.length ; i++ ){
	
  const tempNo = imgNo[i].value;

  let img = document.getElementById(`img${tempNo}`)
  
	ceoPicDeleteBtn[i].addEventListener("click", () => {

    if(confirm("삭제하시겠습니까?")){

      fetch("/camp/ceoPicDelete?imgNo=" + tempNo)
        .then(resp => resp.text())
        .then(count => {
  
          if (count > 0) {
            alert("사진이 삭제되었습니다.")
            img.remove();
          }
        })

      } else {

        alert("삭제를 취소했습니다.");

      } 
  })
}



