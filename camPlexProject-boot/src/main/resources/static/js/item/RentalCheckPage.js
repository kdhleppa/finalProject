const classification = document.getElementById('classification');
const contentSecCenterSec = document.querySelector('.contentSecCenterSec');
let overlayScreen = document.querySelector(`.overlayScreen`);

// 캠핑장 정렬 
classification.addEventListener('change', (e) => {
	
	contentSecCenterSec.innerHTML = "";

	overlayScreen.classList.add("play");
		
	overlayScreen.addEventListener("animationend", function handler() {
		this.removeEventListener("animationend", handler);
		this.classList.remove("play");
	})

	fetch("/item/order?category=" + e.target.value)
	.then(resp => resp.json())
	.then(data => {

		for(var item of data){

			const contentDetailSec = document.createElement('section');
			contentDetailSec.classList.add('contentDetailSec');

			const contentImgeSec = document.createElement('contentImgeSec');
			contentImgeSec.classList.add('contentImgeSec');

			const aTag = document.createElement('a');
			aTag.setAttribute("href", `/item/itemDetail/${item.itemNo}`)

			const contentImg = document.createElement('img');
			contentImg.classList.add('contentImg');
			contentImg.setAttribute("src", item.thumbnail);

			aTag.append(contentImg);

			contentImgeSec.append(aTag);


			const contentInfoSec = document.createElement('section');
			contentInfoSec.classList.add('contentInfoSec');

			const contentNameA = document.createElement('a')
			contentNameA.classList.add('contentNameA');
			contentNameA.setAttribute("src", `/item/itemDetail/${item.itemNo}`)

			const contentTitle = document.createElement('section');
			contentTitle.classList.add('contentInfo');
			contentTitle.setAttribute("id", "contentTitle");
			contentTitle.innerText = item.itemName;

			contentNameA.append(contentTitle)

			const contentComment = document.createElement('section');
			contentComment.classList.add('contentInfo');
			contentComment.setAttribute("id", "contentComment");
			contentComment.innerText = item.itemDescription

			const contentInfo = document.createElement('section');
			contentInfo.classList.add('contentInfo');

			if(item.discountRate != 0){
				const del = document.createElement('del');
				del.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

				const discountRate = document.createElement('section');
				discountRate.classList.add('contentInfo');
				discountRate.setAttribute("id", "discountRate")
				discountRate.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

				const ratio = document.createElement('p')
				ratio.style.color = 'red';
				ratio.innerText = `(${item.discountRate}%↓)`

				contentInfo.append(del)

				discountRate.append(ratio);

				contentInfoSec.append(contentNameA, contentComment, contentInfo, discountRate)

				contentDetailSec.append(contentImgeSec, contentInfoSec)

			} else {
				const pTag = document.createElement('p');
				pTag.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

				contentInfo.append(pTag);

				contentInfoSec.append(contentNameA, contentComment, contentInfo)

				contentDetailSec.append(contentImgeSec, contentInfoSec)
			}


			contentSecCenterSec.append(contentDetailSec)

		}


		
		})	
	
})


const itemInput = document.getElementById('itemInput');

itemInput.addEventListener("keyup", (e) => {

	contentSecCenterSec.innerHTML = "";

	overlayScreen.classList.add("play");
		
	overlayScreen.addEventListener("animationend", function handler() {
		this.removeEventListener("animationend", handler);
		this.classList.remove("play");
	})

	fetch("/item/search?input=" + e.target.value)
	.then(resp => resp.json())
	.then(data => {

		if(data.length == 0){

			const none = document.createElement('h1')
			none.innerText = "검색결과가 없습니다";
			contentSecCenterSec.append(none);
			
		} else{
		
			for(var item of data){

				const contentDetailSec = document.createElement('section');
				contentDetailSec.classList.add('contentDetailSec');
	
				const contentImgeSec = document.createElement('contentImgeSec');
				contentImgeSec.classList.add('contentImgeSec');
	
				const aTag = document.createElement('a');
				aTag.setAttribute("href", `/item/itemDetail/${item.itemNo}`)
	
				const contentImg = document.createElement('img');
				contentImg.classList.add('contentImg');
				contentImg.setAttribute("src", item.thumbnail);
	
				aTag.append(contentImg);
	
				contentImgeSec.append(aTag);
	
	
				const contentInfoSec = document.createElement('section');
				contentInfoSec.classList.add('contentInfoSec');
	
				const contentNameA = document.createElement('a')
				contentNameA.classList.add('contentNameA');
				contentNameA.setAttribute("src", `/item/itemDetail/${item.itemNo}`)
	
				const contentTitle = document.createElement('section');
				contentTitle.classList.add('contentInfo');
				contentTitle.setAttribute("id", "contentTitle");
				contentTitle.innerText = item.itemName;
	
				contentNameA.append(contentTitle)
	
				const contentComment = document.createElement('section');
				contentComment.classList.add('contentInfo');
				contentComment.setAttribute("id", "contentComment");
				contentComment.innerText = item.itemDescription
	
				const contentInfo = document.createElement('section');
				contentInfo.classList.add('contentInfo');
	
				if(item.discountRate != 0){
					const del = document.createElement('del');
					del.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
	
					const discountRate = document.createElement('section');
					discountRate.classList.add('contentInfo');
					discountRate.setAttribute("id", "discountRate")
					discountRate.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
	
					const ratio = document.createElement('p')
					ratio.style.color = 'red';
					ratio.innerText = `(${item.discountRate}%↓)`
	
					contentInfo.append(del)
	
					discountRate.append(ratio);
	
					contentInfoSec.append(contentNameA, contentComment, contentInfo, discountRate)
	
					contentDetailSec.append(contentImgeSec, contentInfoSec)
	
				} else {
					const pTag = document.createElement('p');
					pTag.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
	
					contentInfo.append(pTag);
	
					contentInfoSec.append(contentNameA, contentComment, contentInfo)
	
					contentDetailSec.append(contentImgeSec, contentInfoSec)
				}
	
	
				contentSecCenterSec.append(contentDetailSec)
	
			}
		
		}

	})

})

const categoryBtn = document.querySelectorAll(".categoryBtn")


for(var i = 0 ; i < categoryBtn.length ; i++){

	categoryBtn[i].addEventListener("click", (e) => {

		contentSecCenterSec.innerHTML = "";

		overlayScreen.classList.add("play");
		
		overlayScreen.addEventListener("animationend", function handler() {
		this.removeEventListener("animationend", handler);
		this.classList.remove("play");
		})

		for(var btn of categoryBtn){
			btn.classList.remove('clicked')
		}

		e.target.classList.add('clicked')

		fetch("/item/category?category=" + e.target.value)
		.then(resp => resp.json())
		.then(data => {

			if(data.length == 0){

				const none = document.createElement('h1')
				none.innerText = "물품이 없습니다";
				contentSecCenterSec.append(none);
				
			} else{
			
				for(var item of data){
	
					const contentDetailSec = document.createElement('section');
					contentDetailSec.classList.add('contentDetailSec');
		
					const contentImgeSec = document.createElement('contentImgeSec');
					contentImgeSec.classList.add('contentImgeSec');
		
					const aTag = document.createElement('a');
					aTag.setAttribute("href", `/item/itemDetail/${item.itemNo}`)
		
					const contentImg = document.createElement('img');
					contentImg.classList.add('contentImg');
					contentImg.setAttribute("src", item.thumbnail);
		
					aTag.append(contentImg);
		
					contentImgeSec.append(aTag);
		
		
					const contentInfoSec = document.createElement('section');
					contentInfoSec.classList.add('contentInfoSec');
		
					const contentNameA = document.createElement('a')
					contentNameA.classList.add('contentNameA');
					contentNameA.setAttribute("src", `/item/itemDetail/${item.itemNo}`)
		
					const contentTitle = document.createElement('section');
					contentTitle.classList.add('contentInfo');
					contentTitle.setAttribute("id", "contentTitle");
					contentTitle.innerText = item.itemName;
		
					contentNameA.append(contentTitle)
		
					const contentComment = document.createElement('section');
					contentComment.classList.add('contentInfo');
					contentComment.setAttribute("id", "contentComment");
					contentComment.innerText = item.itemDescription
		
					const contentInfo = document.createElement('section');
					contentInfo.classList.add('contentInfo');
		
					if(item.discountRate != 0){
						const del = document.createElement('del');
						del.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
		
						const discountRate = document.createElement('section');
						discountRate.classList.add('contentInfo');
						discountRate.setAttribute("id", "discountRate")
						discountRate.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
		
						const ratio = document.createElement('p')
						ratio.style.color = 'red';
						ratio.innerText = `(${item.discountRate}%↓)`
		
						contentInfo.append(del)
		
						discountRate.append(ratio);
		
						contentInfoSec.append(contentNameA, contentComment, contentInfo, discountRate)
		
						contentDetailSec.append(contentImgeSec, contentInfoSec)
		
					} else {
						const pTag = document.createElement('p');
						pTag.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
		
						contentInfo.append(pTag);
		
						contentInfoSec.append(contentNameA, contentComment, contentInfo)
		
						contentDetailSec.append(contentImgeSec, contentInfoSec)
					}
		
		
					contentSecCenterSec.append(contentDetailSec)
		
				}
			
			}




		})
	})

}

let contentDetailSec = document.querySelectorAll('.contentDetailSec')
const contentLength = contentDetailSec.length;
const contentContainer = document.querySelector(".contentContainer");
const bBlank = document.getElementsByClassName('bBlank')
let contentCount = 8;

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
				
			contentContainer.style.height = contentContainer.offsetHeight+500+'px';
			
			contentCount += 4;
			more(contentLength)
		})


	} else if(contentLength < contentCount && contentCount > 6) {

		const btn = document.createElement('button');
		btn.classList.add('moreBtn')
		btn.setAttribute("type", "button")
		btn.innerHTML = "접기<br>∧";
		
		bBlank[0].append(btn)

		btn.addEventListener("click", () => {
				
			contentContainer.style.height ='1000px';
			
			contentCount = 8;
	
			more(contentLength)
		})

	} else {
		bBlank[0].innerHTML = "";
		more(contentLength)
	}
	
}