let contentDetail = document.querySelectorAll('.contentDetailSec')
let overlayScreen = document.querySelector(`.overlayScreen`);
const bBlank = document.getElementsByClassName('bBlank');
const mainContent = document.querySelector('.mainContent');
let contentCount = 6;
const contentLength = contentDetail.length;

const categoryBtn = document.querySelectorAll(".categoryBtn");
const searchCamp = document.getElementById('searchCamp');
const classification = document.getElementById('classification');
const contentSec = document.querySelector('.contentSec');


more(contentLength)

//  더보기 함수
function more(contentLength){
	
	bBlank[0].innerHTML = "";

	if(contentLength <= 6) {

		return;

	} else if(contentLength > contentCount){
		
		const btn = document.createElement('button');
		btn.classList.add('moreBtn')
		btn.setAttribute("type", "button")
		btn.innerHTML = "더 보기<br>∨";
		
		bBlank[0].append(btn)

		btn.addEventListener("click", () => {

			mainContent.style.height = mainContent.offsetHeight+704+'px';
			
			contentCount += 6

			more(contentLength);

		})

	} else if(contentLength <= contentCount && contentCount > 6) {

		const btn = document.createElement('button');
		btn.classList.add('moreBtn')
		btn.setAttribute("type", "button")
		btn.innerHTML = "접기<br>∧";
		
		bBlank[0].append(btn)

		btn.addEventListener("click", () => {
				
			overlayScreen.classList.add("play");
		
			overlayScreen.addEventListener("animationend", function handler() {
				this.removeEventListener("animationend", handler);
				this.classList.remove("play");
			})

			mainContent.style.height ='1000px';

			contentCount = 6;

			more(contentLength)

		})

	} 
	
}

// 캠핑장 정렬 
classification.addEventListener('change', (e) => {
	
	contentSec.innerHTML = "";

	for(var btn of categoryBtn){
		btn.classList.remove('clicked')
	}

	searchCamp.value ="";

	overlayScreen.classList.add("play");
		
	overlayScreen.addEventListener("animationend", function handler() {
		this.removeEventListener("animationend", handler);
		this.classList.remove("play");
	})


	fetch("/camp/order?category=" + e.target.value)
	.then(resp => resp.json())
	.then(data => {

		for(var camp of data){

			const contentDetailSec = document.createElement('section');
			contentDetailSec.classList.add('contentDetailSec');

			const contentImageDiv = document.createElement('div');
			contentImageDiv.classList.add('contentImageDiv');

			const contentImage = document.createElement('img');
			contentImage.classList.add('contentImage');
			contentImage.setAttribute("src", `${camp.thumbnail}`);
			contentImage.setAttribute("onclick", `location.href = '/camp/${camp.campNo}'`);

			contentImageDiv.append(contentImage);


			const contentDetailWriteSec = document.createElement('a');
			contentDetailWriteSec.classList.add('contentDetailWriteSec');
			contentDetailWriteSec.setAttribute("href", `/camp/${camp.campNo}`);

			const contentDetailWriteSecCenterSec = document.createElement('section');
			contentDetailWriteSecCenterSec.classList.add('contentDetailWriteSecCenterSec');

			const div = document.createElement('div');

			const text = document.createElement('p');
			text.innerText = camp.campName;

			const address = document.createElement('p');
			address.setAttribute("id", "address");
			address.innerText = `${camp.campAddress}`;

			div.append(text, address);

			const star = document.createElement('div');
			star.classList.add('star');

			const image = document.createElement('img');
			image.setAttribute("src", "/images/iconImg/marsh3.png")

			const starCount = document.createElement('span')
			starCount.innerText = camp.campingStar

			star.append(image, starCount);

			if(memberType == 'M'){
				const deleteCampFrm = document.createElement('form');
				deleteCampFrm.classList.add('deleteCampFrm');
				deleteCampFrm.setAttribute("action", "/camp2/deleteCamp")

				const campDelete = document.createElement('button');
				campDelete.classList.add('campDelete');
				campDelete.innerText = "삭제";

				const campNo = document.createElement('input');
				campNo.setAttribute("type", "hidden");
				campNo.setAttribute("name", "campNo");
				campNo.setAttribute("value", `${camp.campNo}`);

				deleteCampFrm.append(campDelete, campNo);

				contentDetailWriteSecCenterSec.append(div, deleteCampFrm, star);

			} else{
				
				contentDetailWriteSecCenterSec.append(div, star);

			}

			contentDetailWriteSec.append(contentDetailWriteSecCenterSec);

			contentDetailSec.append(contentImageDiv, contentDetailWriteSec);

			contentSec.append(contentDetailSec);
		}
		console.log(data.length)
		more(data.length)
	})	
	
})


searchCamp.addEventListener("keyup", (e) => {

	overlayScreen.classList.add("play");
		
	overlayScreen.addEventListener("animationend", function handler() {
		this.removeEventListener("animationend", handler);
		this.classList.remove("play");
	})

	contentSec.innerHTML = "";
	
	for(var btn of categoryBtn){
		btn.classList.remove('clicked')
	}

	fetch("/camp/searchCamp?input=" + e.target.value)
	.then(resp => resp.json())
	.then(data => {

		if(data.length == 0){

			const contentSecCenterSec = document.createElement('section')
			contentSecCenterSec.classList.add('contentSecCenterSec')

			const none = document.createElement('h1')
			none.innerText = "해당 캠핑장이 없습니다";

			contentSecCenterSec.append(none)

			contentSec.append(contentSecCenterSec);
			
		} else{

			for(var camp of data){

				const contentDetailSec = document.createElement('section');
				contentDetailSec.classList.add('contentDetailSec');

				const contentImageDiv = document.createElement('div');
				contentImageDiv.classList.add('contentImageDiv');

				const contentImage = document.createElement('img');
				contentImage.classList.add('contentImage');
				contentImage.setAttribute("src", `${camp.thumbnail}`);
				contentImage.setAttribute("onclick", `location.href = '/camp/${camp.campNo}'`);

				contentImageDiv.append(contentImage);


				const contentDetailWriteSec = document.createElement('a');
				contentDetailWriteSec.classList.add('contentDetailWriteSec');
				contentDetailWriteSec.setAttribute("href", `/camp/${camp.campNo}`);

				const contentDetailWriteSecCenterSec = document.createElement('section');
				contentDetailWriteSecCenterSec.classList.add('contentDetailWriteSecCenterSec');

				const div = document.createElement('div');

				const text = document.createElement('p');
				text.innerText = camp.campName;

				const address = document.createElement('p');
				address.setAttribute("id", "address");
				address.innerText = `${camp.campAddress}`;

				div.append(text, address);

				const star = document.createElement('div');
				star.classList.add('star');

				const image = document.createElement('img');
				image.setAttribute("src", "/images/iconImg/marsh3.png")

				const starCount = document.createElement('span')
				starCount.innerText = camp.campingStar

				star.append(image, starCount);

				if(memberType == 'M'){
					const deleteCampFrm = document.createElement('form');
					deleteCampFrm.classList.add('deleteCampFrm');
					deleteCampFrm.setAttribute("action", "/camp2/deleteCamp")

					const campDelete = document.createElement('button');
					campDelete.classList.add('campDelete');
					campDelete.innerText = "삭제";

					const campNo = document.createElement('input');
					campNo.setAttribute("type", "hidden");
					campNo.setAttribute("name", "campNo");
					campNo.setAttribute("value", `${camp.campNo}`);

					deleteCampFrm.append(campDelete, campNo);

					contentDetailWriteSecCenterSec.append(div, deleteCampFrm, star);

				} else{
					
					contentDetailWriteSecCenterSec.append(div, star);

				}

				contentDetailWriteSec.append(contentDetailWriteSecCenterSec);

				contentDetailSec.append(contentImageDiv, contentDetailWriteSec);

				contentSec.append(contentDetailSec);
			}
		}
		more(data.length)
		
	})


})

// const topBar = document.querySelector(".topBar")
// const mainTitle = document.getElementById("mainTitle")
// const tempX = searchCamp.getBoundingClientRect().bottom;

// window.addEventListener("scroll", (e)=> {

// 	if(searchCamp.getBoundingClientRect().bottom != 327){
// 		topBar.style.height = '100px';
// 		topBar.style.backgroundColor = "transparent"; 
// 		mainTitle.style.lineHeight = "0px";
// 		mainTitle.style.margin = "0px";
// 		mainTitle.style.marginBottom = "10px";
// 		mainTitle.style.marginTop = "10px";
// 	} else {
// 		topBar.style.height = '170px';
// 		topBar.style.backgroundColor = "rgb(216, 216, 216)"; 
// 		mainTitle.style.lineHeight = "60px";
// 		mainTitle.style.margin = "20px 0";
// 	}

// 	console.log(searchCamp.getBoundingClientRect().bottom)

// })



for(var i = 0 ; i < categoryBtn.length ; i++){

	categoryBtn[i].addEventListener("click", (e) => {

		overlayScreen.classList.add("play");
		
		overlayScreen.addEventListener("animationend", function handler() {
			this.removeEventListener("animationend", handler);
			this.classList.remove("play");
		})
		  
		contentSec.innerHTML = "";

		searchCamp.value ="";

		for(var btn of categoryBtn){
			btn.classList.remove('clicked')
		}

		e.target.classList.add('clicked')

		fetch("/camp/category?category=" + e.target.value)
		.then(resp => resp.json())
		.then(data => {

			if(data.length == 0){

				const contentSecCenterSec = document.createElement('section')
				contentSecCenterSec.classList.add('contentSecCenterSec')

				const none = document.createElement('h1')
				none.innerText = "해당 캠핑장이 없습니다";

				contentSecCenterSec.append(none)

				contentSec.append(contentSecCenterSec);
				
			} else{

				for(var camp of data){
	
					const contentDetailSec = document.createElement('section');
					contentDetailSec.classList.add('contentDetailSec');

					const contentImageDiv = document.createElement('div');
					contentImageDiv.classList.add('contentImageDiv');

					const contentImage = document.createElement('img');
					contentImage.classList.add('contentImage');
					contentImage.setAttribute("src", `${camp.thumbnail}`);
					contentImage.setAttribute("onclick", `location.href = '/camp/${camp.campNo}'`);

					contentImageDiv.append(contentImage);


					const contentDetailWriteSec = document.createElement('a');
					contentDetailWriteSec.classList.add('contentDetailWriteSec');
					contentDetailWriteSec.setAttribute("href", `/camp/${camp.campNo}`);

					const contentDetailWriteSecCenterSec = document.createElement('section');
					contentDetailWriteSecCenterSec.classList.add('contentDetailWriteSecCenterSec');

					const div = document.createElement('div');

					const text = document.createElement('p');
					text.innerText = camp.campName;

					const address = document.createElement('p');
					address.setAttribute("id", "address");
					address.innerText = `${camp.campAddress}`;

					div.append(text, address);

					const star = document.createElement('div');
					star.classList.add('star');

					const image = document.createElement('img');
					image.setAttribute("src", "/images/iconImg/marsh3.png")

					const starCount = document.createElement('span')
					starCount.innerText = camp.campingStar

					star.append(image, starCount);

					if(memberType == 'M'){
						const deleteCampFrm = document.createElement('form');
						deleteCampFrm.classList.add('deleteCampFrm');
						deleteCampFrm.setAttribute("action", "/camp2/deleteCamp")

						const campDelete = document.createElement('button');
						campDelete.classList.add('campDelete');
						campDelete.innerText = "삭제";

						const campNo = document.createElement('input');
						campNo.setAttribute("type", "hidden");
						campNo.setAttribute("name", "campNo");
						campNo.setAttribute("value", `${camp.campNo}`);

						deleteCampFrm.append(campDelete, campNo);

						contentDetailWriteSecCenterSec.append(div, deleteCampFrm, star);

					} else{
						
						contentDetailWriteSecCenterSec.append(div, star);

					}

					contentDetailWriteSec.append(contentDetailWriteSecCenterSec);

					contentDetailSec.append(contentImageDiv, contentDetailWriteSec);

					contentSec.append(contentDetailSec);
				}
			}
			
			more(data.length)

		})
	})

}

// **************************** 캠핑장 삭제 ***********************************

const deleteCampFrm = document.querySelectorAll(".deleteCampFrm")

for(var i = 0 ; i <deleteCampFrm.length ; i++){
	
	deleteCampFrm[i].addEventListener("submit", (e) => {
		
		if(!confirm("삭제 하시겠습니까?")){
			
			alert("삭제를 취소했습니다.")
			e.preventDefault();
			return;
		}
		
	})
	
}
