const prev = document.getElementById('left');
const next = document.getElementById('right');
const slideBox = document.querySelector('.ceoPicSlideBox');
const slide = document.querySelectorAll('.ceoPicContent');
const slideLength = slide.length
let currentIndex = 0;

const moveSlide = function(num) {
	slideBox.style.transform = `translateX(${-num * 250}px)`;
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

for (let i = 0; i < inputImage.length; i++) {

	// 파일이 선택되거나, 선택 후 취소 되었을 때
	inputImage[i].addEventListener('change', e => {

		const file = e.target.files[0]; // 선택된 파일의 데이터

		if (file != undefined) { // 파일이 선택 되었을 때

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
		if (preview[i].getAttribute("src") != "") {

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

for (var i = 0; i < ceoPicDeleteBtn.length; i++) {

	const tempNo = imgNo[i].value;

	let img = document.getElementById(`img[${tempNo}]`)

	ceoPicDeleteBtn[i].addEventListener("click", () => {

		if (confirm("삭제하시겠습니까?")) {

			fetch("/camp/ceoPicDelete?imgNo=" + tempNo)
				.then(resp => resp.text())
				.then(count => {

					console.log(count)

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

//----------------------------- 달력 --------------------------------------------------
let checkInDate = document.getElementById('checkInDate')
let checkOutDate = document.getElementById('checkOutDate')

document.addEventListener('DOMContentLoaded', function() {
	var calendarNext = document.getElementById('calendar-next');

	var currentDate = new Date();
	var currentMonth = currentDate.getMonth() + 1;
	var currentYear = currentDate.getFullYear();
	var startDate = null;
	var exitDate = null;
	var selectingStartDate = true;

	createCalendar(calendarNext, currentYear, currentMonth);

	// 달력 생성 함수
	function createCalendar(container, year, month) {
		var calendarHeader = document.createElement('h2');

		if (month == 0) {
			month = 12;
			year = year - 1;
		}

		calendarHeader.textContent = `${year}년 ${month}월`;

		var calendarTable = document.createElement('table');
		calendarTable.classList.add('calendar');

		var tableHead = document.createElement('thead');
		var daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

		var headerRow = document.createElement('tr');
		daysOfWeek.forEach(day => {
			var dayTitle = document.createElement('th');
			dayTitle.textContent = day;
			headerRow.appendChild(dayTitle);
		});

		tableHead.appendChild(headerRow);
		calendarTable.appendChild(tableHead);

		var tableBody = document.createElement('tbody');

		var firstDay = new Date(year, month - 1, 1);
		var lastDay = new Date(year, month, 0);
		var daysInMonth = lastDay.getDate();

		var currentDay = 1;
		var isFirstRow = true;

		while (currentDay <= daysInMonth) {
			var calendarRow = document.createElement('tr');

			for (var i = 0; i < 7; i++) {
				var calendarCell = document.createElement('td');

				if (isFirstRow && i < firstDay.getDay()) {
					calendarCell.classList.add('empty');
				} else if (currentDay > daysInMonth) {
					calendarCell.classList.add('empty');
				} else {
					calendarCell.textContent = currentDay;
					currentDay++;

					// Add click event listener to each calendar cell
					calendarCell.addEventListener('click', function (event) {
						handleDateClick(event, container);
					});
				}

				calendarRow.appendChild(calendarCell);
			}

			isFirstRow = false;
			tableBody.appendChild(calendarRow);
		}

		calendarTable.appendChild(tableBody);

		container.innerHTML = '';
		container.appendChild(calendarHeader);
		container.appendChild(calendarTable);
	}


	// 달력 클릭 함수
	function handleDateClick(event, container) {
		var clickedCell = event.target;
		var clickedDate = parseInt(clickedCell.textContent);

		if (selectingStartDate) {
			startDate = new Date(currentYear, currentMonth - 1, clickedDate);
			exitDate = new Date(currentYear, currentMonth - 1, clickedDate);
			selectingStartDate = true;
		} 

		applyDateRangeStyle(container);
	}

	function applyDateRangeStyle(container) {

		var calendarCells = container.querySelectorAll('.calendar td');

		calendarCells.forEach(cell => {
			cell.classList.remove('selected-start');
		});

		calendarCells.forEach(cell => {
			var cellDate = new Date(currentYear, currentMonth - 1, parseInt(cell.textContent));
			var stayDate = document.querySelector('input[name=stayDate]:checked').value;
			cellDate.setHours(0, 0, 0, 0);
			
			if (startDate && cellDate.getTime() === startDate.getTime()) {
				
				cell.classList.add('selected-start');
				exitDate = new Date(startDate);
			
				exitDate.setDate(startDate.getDate()+parseInt(stayDate));
	
				checkInDate.innerText =
					startDate.getFullYear() + "년 "
					+ (startDate.getMonth() + 1) + "월 "
					+ startDate.getDate() + "일 "
					+ checkIn + "시";
				
				checkOutDate.innerText =
					exitDate.getFullYear() + "년 "
					+ (exitDate.getMonth() + 1) + "월 "
					+ exitDate.getDate() + "일 "
					+ checkOut + "시";
					
					
					// ajax로 예약 가능한 숙소 보여주기
					
					console.log(startDate);
					console.log(exitDate);					
					
					var tempSDate = new Date;
					var tempEDate = new Date;
					
					tempSDate = startDate.getFullYear() + '-' 
								+ (startDate.getMonth()+1) + '-'
								+ startDate.getDate()
								
					tempEDate = exitDate.getFullYear() + '-' 
								+ (exitDate.getMonth()+1) + '-'
								+ exitDate.getDate()
					
					fetch("/camp/showCampDe?entDate=" + tempSDate 
										 + "&outDate=" + tempEDate 
										 + "&campNo=" + campNo)
					.then(resp => resp.json())
					.then(data => {
						
						const campDetailContainer = document.getElementById('campDetailContainer');
						campDetailContainer.innerHTML = '';

						for(var campDe of data){
						
							const campSite = document.createElement('div');
							campSite.classList.add("campSite");

							const campStieRadio = document.createElement("input");
							campStieRadio.classList.add("campStieRadio");
							campStieRadio.setAttribute("type", "radio");
							campStieRadio.setAttribute("name", "campDeNo");
							campStieRadio.setAttribute("id", `campNo${campDe.campDeNo}`);
							campStieRadio.setAttribute("value", `${campDe.campDeNo}`);

							const campSiteBox = document.createElement('label');
							campSiteBox.classList.add('campSiteBox');
							campSiteBox.setAttribute("for", `campNo${campDe.campDeNo}`);

							const campSiteImgWrapper = document.createElement('div');
							campSiteImgWrapper.classList.add('campSiteImgWrapper');

							const campSiteImg = document.createElement('img');
							campSiteImg.setAttribute('src', `${campDe.campDeThumbnail}`)

							campSiteImgWrapper.append(campSiteImg);

							const campSiteInfoContainer = document.createElement('div')
							campSiteInfoContainer.classList.add('campSiteInfoContainer')

							const campDeName = document.createElement('p');
							campDeName.innerText = `${campDe.campDeName}`;

							const campSiteInfo = document.createElement('div');
							campSiteInfo.classList.add('campSiteInfo');

							const campSiteInfoDetail = document.createElement('div');
							campSiteInfoDetail.classList.add('campSiteInfoDetail');

							const count = document.createElement('p');
							count.innerText = '수용 인원 ';
							
							const capacity = document.createElement('input');
							capacity.setAttribute('type', 'text')
							capacity.setAttribute('value', `${campDe.capacity}명`);
							capacity.setAttribute('disabled', 'ture');

							const campSitePrice = document.createElement('p');
							campSitePrice.classList.add('campSitePrice');
							campSitePrice.innerText = `${(campDe.campDePrice*stayDate).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원`

							count.append(capacity);

							campSiteInfoDetail.append(count);

							campSiteInfo.append(campSiteInfoDetail, campSitePrice);

							campSiteInfoContainer.append(campDeName, campSiteInfo);

							campSiteBox.append(campSiteImgWrapper, campSiteInfoContainer);

							campSite.append(campStieRadio, campSiteBox);

							campDetailContainer.append(campSite);
						}
						
					})
					
					
			}

		});
	}


	// 달력 좌우 이동

	// Previous month button click event
	document.getElementById('prev-month').addEventListener('click', function() {
		if (currentMonth === 1) {
			currentYear--;
			currentMonth = 12;
		} else {
			currentMonth--;
		}
		createCalendar(calendarNext, currentYear, currentMonth);
		applyDateRangeStyle(calendarNext);
	});

	// Next month button click event
	document.getElementById('next-month').addEventListener('click', function() {
		if (currentMonth === 12) {
			currentYear++;
			currentMonth = 1;
		} else {
			currentMonth++;
		}
		createCalendar(calendarNext, currentYear, currentMonth);
		applyDateRangeStyle(calendarNext);
	});

	
	var stayDateRadios = document.getElementsByName('stayDate');
    	stayDateRadios.forEach(function (radio) {
        radio.addEventListener('change', function () {
            applyDateRangeStyle(calendarNext);
        });
    });
	
	

});



