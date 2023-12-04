let contentDetail = document.querySelectorAll('.contentDetailSec')
const bBlank = document.getElementsByClassName('bBlank')
const mainContent = document.querySelector('.mainContent')
let contentCount = 6;

const contentLength = contentDetail.length;

more(contentLength)


//  더보기 함수
function more(contentLength){
	
	bBlank[0].innerHTML = "";

	if(contentLength > contentCount){
		
		const btn = document.createElement('button');
		btn.classList.add('moreBtn')
		btn.setAttribute("type", "button")
		btn.innerHTML = "더 보기<br>∨";
		
		bBlank[0].append(btn)

		btn.addEventListener("click", () => {
				
			mainContent.style.height = mainContent.offsetHeight+352+'px';
			
			contentCount += 3;
			more(contentLength)
		})


	} else if(contentLength < contentCount && contentCount > 6) {

		const btn = document.createElement('button');
		btn.classList.add('moreBtn')
		btn.setAttribute("type", "button")
		btn.innerHTML = "접기<br>∧";
		
		bBlank[0].append(btn)

		btn.addEventListener("click", () => {
				
			mainContent.style.height ='1000px';
			
			contentCount = 6;
	
			more(contentLength)
		})

	} else {
		bBlank[0].innerHTML = "";
		more(contentLength)
	}
	
}


const classification = document.getElementById('classification');
const contentSec = document.querySelector('.contentSec');

// 캠핑장 정렬 
classification.addEventListener('change', (e) => {
	
	contentSec.innerHTML = "";

	fetch("/camp/order?category=" + e.target.value)
	.then(resp => resp.json())
	.then(data => {

		for(var camp of data){

			const contentDetailSec = document.createElement('section');
			contentDetailSec.classList.add('contentDetailSec');

			const contentImageDiv = document.createElement('div');
			contentImageDiv.classList.add('contentImageDiv');

			const contentImage = document.createElement('img');
			contentImage.classList.add('contentImage')
			contentImage.setAttribute("src", `${camp.thumbnail}`)
			contentImage.setAttribute("onclick", `location.href = /camp/${camp.campNo}`)

			contentImageDiv.append(contentImage);


			const contentDetailWriteSec = document.createElement('a');
			contentDetailWriteSec.classList.add('contentDetailWriteSec');
			contentDetailWriteSec.setAttribute("href", `/camp/${camp.campNo}`)

			const contentDetailWriteSecCenterSec = document.createElement('section');
			contentDetailWriteSecCenterSec.classList.add('contentDetailWriteSecCenterSec')

			const text = document.createElement('p');
			text.innerText = camp.campName;

			const star = document.createElement('div');
			star.classList.add('star');

			const image = document.createElement('img');
			image.setAttribute("src", "/images/iconImg/star-fill.png")

			const starCount = document.createElement('span')
			starCount.innerText = camp.campingStar

			star.append(image, starCount);

			contentDetailWriteSecCenterSec.append(text, star);

			contentDetailWriteSec.append(contentDetailWriteSecCenterSec);

			contentDetailSec.append(contentImageDiv, contentDetailWriteSec);

			contentSec.append(contentDetailSec);
		}

		more(data.length)
	})	
	
})

const searchCamp = document.getElementById('searchCamp');

searchCamp.addEventListener("keyup", (e) => {

	contentSec.innerHTML = "";

	fetch("/camp/searchCamp?input=" + e.target.value)
	.then(resp => resp.json())
	.then(data => {

		if(data.length == 0){

			const none = document.createElement('h1')
			none.innerText = "검색결과가 없습니다";
			contentSec.append(none);
			
		} else{

			for(var camp of data){

				const contentDetailSec = document.createElement('section');
				contentDetailSec.classList.add('contentDetailSec');
	
				const contentImageDiv = document.createElement('div');
				contentImageDiv.classList.add('contentImageDiv');
	
				const contentImage = document.createElement('img');
				contentImage.classList.add('contentImage')
				contentImage.setAttribute("src", `${camp.thumbnail}`)
				contentImage.setAttribute("onclick", `location.href = /camp/${camp.campNo}`)
	
				contentImageDiv.append(contentImage);
	
	
				const contentDetailWriteSec = document.createElement('a');
				contentDetailWriteSec.classList.add('contentDetailWriteSec');
				contentDetailWriteSec.setAttribute("href", `/camp/${camp.campNo}`)
	
				const contentDetailWriteSecCenterSec = document.createElement('section');
				contentDetailWriteSecCenterSec.classList.add('contentDetailWriteSecCenterSec')
	
				const text = document.createElement('p');
				text.innerText = camp.campName;
	
				const star = document.createElement('div');
				star.classList.add('star');
	
				const image = document.createElement('img');
				image.setAttribute("src", "/images/iconImg/star-fill.png")
	
				const starCount = document.createElement('span')
				starCount.innerText = camp.campingStar
	
				star.append(image, starCount);
	
				contentDetailWriteSecCenterSec.append(text, star);
	
				contentDetailWriteSec.append(contentDetailWriteSecCenterSec);
	
				contentDetailSec.append(contentImageDiv, contentDetailWriteSec);
	
				contentSec.append(contentDetailSec);
			}
		}
		
		more(data.length)
		
	})




})

const topBar = document.querySelector(".topBar")
const mainTitle = document.getElementById("mainTitle")
const tempX = 0;

window.addEventListener("scroll", (e)=> {

	if(searchCamp.getBoundingClientRect().bottom != 260){
		topBar.style.height = '100px';
		topBar.style.backgroundColor = "transparent"; 
		mainTitle.style.lineHeight = "0px";
		mainTitle.style.margin = "0px";
		mainTitle.style.marginBottom = "10px";
		mainTitle.style.marginTop = "10px";
	} else {
		topBar.style.height = '170px';
		topBar.style.backgroundColor = "rgb(216, 216, 216)"; 
		mainTitle.style.lineHeight = "60px";
		mainTitle.style.margin = "20px 0";
	}

	console.log(searchCamp.getBoundingClientRect().bottom)

})

