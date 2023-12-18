move()
let textList = document.querySelectorAll('.textList')
let contentLength = textList.length;
let moreBtnWrapper = document.getElementsByClassName('moreBtnWrapper')
let contentCount = 5;
let textItmeList = document.querySelector('.textItmeList')

function more(contentLength){

	moreBtnWrapper[0].innerHTML = "";

	if(contentLength <= 5) {

		return;

	}else if(contentLength > contentCount){
		
		const btn = document.createElement('button');
		btn.classList.add('moreBtn')
		btn.setAttribute("type", "button")
		btn.innerHTML = "더 보기<br>∨";
		
		moreBtnWrapper[0].append(btn)

		btn.addEventListener("click", () => {
				
			textItmeList.style.height = textItmeList.offsetHeight+200+'px';
			
			contentCount += 5;
			more(contentLength)
		})


	} else if(contentLength < contentCount && contentCount > 8) {

		const btn = document.createElement('button');
		btn.classList.add('moreBtn')
		btn.setAttribute("type", "button")
		btn.innerHTML = "접기<br>∧";
		
		moreBtnWrapper[0].append(btn)

		btn.addEventListener("click", () => {
				
			textItmeList.style.height ='190px';
			
			contentCount = 5;
	
			more(contentLength)
		})

	} 
	
}
more(contentLength)

function move(){

	let prev = document.getElementById('left');
	let next = document.getElementById('right');
	let itemWrapper = document.querySelector('.itemWrapper');
	let itemSection = document.querySelectorAll('.itemSection');
	let itemSectionlength = document.querySelector('.itemSection');
	let slideLength = itemSection.length
	let currentIndex = 0;
	
	const moveSlide = function (num) {
		itemWrapper.style.transform = `translateX(${-num * 215}px)`;
		currentIndex = num;
	}
	
	prev.addEventListener("click", () => {
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
	
}

const askTo = document.querySelectorAll('input[name="askTo"]');
const textItemSection = document.querySelector(".textItemSection")
let overlayScreen = document.querySelector(`.overlayScreen`);


for(var i = 0 ; i < askTo.length ; i++){

	askTo[i].addEventListener("change", e => {
		
		let textList = document.querySelectorAll('.textList')
		const contentLength = textList.length;
		const moreBtnWrapper = document.getElementsByClassName('moreBtnWrapper')
		let contentCount = 5;

		overlayScreen.classList.add("play");

		overlayScreen.addEventListener("animationend", function handler() {
			this.removeEventListener("animationend", handler);
			this.classList.remove("play");
		})


		function more(contentLength){

			moreBtnWrapper[0].innerHTML = "";

			if(contentLength <= 5) {

				return;

			}else if(contentLength > contentCount){
				
				const btn = document.createElement('button');
				btn.classList.add('moreBtn')
				btn.setAttribute("type", "button")
				btn.innerHTML = "더 보기<br>∨";
				
				moreBtnWrapper[0].append(btn)

				btn.addEventListener("click", () => {
						
					textItmeList.style.height = textItmeList.offsetHeight+200+'px';
					
					contentCount += 5;
					more(contentLength)
				})


			} else if(contentLength < contentCount && contentCount > 8) {

				const btn = document.createElement('button');
				btn.classList.add('moreBtn')
				btn.setAttribute("type", "button")
				btn.innerHTML = "접기<br>∧";
				
				moreBtnWrapper[0].append(btn)

				btn.addEventListener("click", () => {
						
					textItmeList.style.height ='190px';
					
					contentCount = 5;
			
					more(contentLength)
				})

			} 
			
		}

		for(var i = 0 ; i<askTo.length ; i ++){
			askTo[i].classList.remove('clicked')
		}

		e.target.classList.add('clicked')
		
		textItemSection.innerHTML = "";

		const empty2 = document.createElement('section');
		empty2.classList.add('empty2');
		
		const textItmeList = document.createElement('section');
		textItmeList.classList.add('textItmeList');
		
		if(e.target.value == 'admin'){

			fetch("/member/selectQna")
			.then(resp => resp.json())
			.then(data => {
				
				
				if(data.length == 0){
					const textList = document.createElement('section');
					textList.classList.add('textList');
					
					const p = document.createElement('p');
					p.innerText = "문의 내역이 없습니다.";
					
					textList.append(p);
					
					textItmeList.append(textList);
					
				} else{
					
					for(qna of data){

						const textList = document.createElement('section');
						textList.classList.add('textList');
	
						const a = document.createElement('a');
						a.setAttribute("id", `qna_${qna.qnano}`)
						a.innerHTML = `${qna.qnacreateDate} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${qna.qnatitle}`
						a.classList.add('qnaBtn');

						if(qna.answerFlag == 'Y'){
							const done = document.createElement('a');
							done.innerText = "답변 완료";
							textList.append(a, done);
						}else{
							const notYet = document.createElement('a');
							notYet.innerText = "답변 대기중";
							textList.append(a, notYet);
						}

						textItmeList.append(textList);
					}


				}
				
				textItemSection.append(textItmeList, empty2);
				textItemSection.prepend(empty2);
				more(data.length)
				modal()
				
			})


		}

		if(e.target.value == 'ceo'){

			for(var i = 0 ; i<askTo.length ; i ++){
				askTo[i].classList.remove('clicked')
			}
			
			e.target.classList.add('clicked')

			fetch("/member/selectCeoQna")
			.then(resp => resp.json())
			.then(data => {

				if(data.length == 0){

					const textList = document.createElement('section');
					textList.classList.add('textList');

					const p = document.createElement('p');
					p.innerText = "문의 내역이 없습니다.";

					textList.append(p);

					textItmeList.append(textList);

				}else{

					for(qna of data){
	
						const textList = document.createElement('section');
						textList.classList.add('textList');
	
						const a = document.createElement('a');
						a.setAttribute("id", `qna_${qna.ceoQnaNo}`)
						a.innerHTML = `${qna.ceoQnaCreateDate}&nbsp; (${qna.campName}) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${qna.ceoQnaTitle}`
						a.classList.add('qnaBtn');
	
						if(qna.ceoAnswerFlag == 'Y'){
							const done = document.createElement('a');
							done.innerText = "답변 완료";
							textList.append(a, done);
						}else{
							const notYet = document.createElement('a');
							notYet.innerText = "답변 대기중";
							textList.append(a, notYet);
						}
	
						textItmeList.append(textList);
	
					}

				}
				
				textItemSection.append(textItmeList, empty2);
				textItemSection.prepend(empty2);
				more(data.length)
				modalCeo()
			})

		}

	})

}

const wishListItems = document.getElementById('wishListItems')
const wish = document.querySelectorAll('input[name="wish"]');
let overlayScreenWish = document.querySelector(`.overlayScreenWish`);

for(var i = 0 ; i < wish.length ; i++){
	
	wish[i].addEventListener("change", e => {
		for(var i = 0 ; i<wish.length ; i ++){
			wish[i].classList.remove('clicked')
		}
		
		e.target.classList.add('clicked')
		
		wishListItems.innerHTML ="";
		
		if(e.target.value == 'item'){
			
			fetch("/member/selectItemWish")
			.then(resp => resp.json())
			.then(data => {
				
				if(data.length == 0){

					const empty = document.createElement('section');
					empty.classList.add('empty2');

					const empty2 = document.createElement('section');
					empty2.classList.add('empty2');

					const left = document.createElement('button');
					left.classList.add('arrow');
					left.setAttribute("type", "button");
					left.setAttribute("id", "left")

					const right = document.createElement('button');
					right.classList.add('arrow');
					right.setAttribute("type", "button");
					right.setAttribute("id", "right")

					empty.append(left)
					empty2.append(right)

					const overlayScreenWish = document.createElement('div')
					overlayScreenWish.classList.add('overlayScreenWish')

					const itemSection = document.createElement('section');
					itemSection.classList.add('itemSection')

					const p = document.createElement('p')
					p.innerText = "관심 상품이 없습니다.";
					
					itemSection.append(p)

					overlayScreenWish.append(itemSection)

					wishListItems.append(empty, overlayScreenWish, empty2)

					overlayScreenWish.classList.add("play");
			
					overlayScreenWish.addEventListener("animationend", function handler() {
						this.removeEventListener("animationend", handler);
						this.classList.remove("play");
					})

				} else {

					const empty = document.createElement('section');
					empty.classList.add('empty2');

					const empty2 = document.createElement('section');
					empty2.classList.add('empty2');

					const left = document.createElement('button');
					left.classList.add('arrow');
					left.setAttribute("type", "button");
					left.setAttribute("id", "left")
					
					const right = document.createElement('button');
					right.classList.add('arrow');
					right.setAttribute("type", "button");
					right.setAttribute("id", "right")
					
					if(data.length > 4) {
						const leftIcon = document.createElement('img');
						leftIcon.setAttribute('src', '/images/iconImg/left.png');
						
						const rightIcon = document.createElement('img');
						rightIcon.setAttribute('src', '/images/iconImg/right.png');

						left.append(leftIcon)
						right.append(rightIcon)

						empty.append(left)
						empty2.append(right)
					}

					const overlayScreenWish = document.createElement('div')
					overlayScreenWish.classList.add('overlayScreenWish')

					const item = document.createElement('div');
					item.classList.add('item')

					const itemWrapper = document.createElement('section');
					itemWrapper.classList.add('itemWrapper');

					for(let temp of data){

						const itemSection = document.createElement('section');
						itemSection.classList.add('itemSection');

						const wishItem = document.createElement('img');
						wishItem.classList.add('wishItem')	
						wishItem.setAttribute('src', `${temp.thumbnail}`);
						wishItem.setAttribute('onclick', `location.href='/item/itemDetail/${temp.itemNo}'`)

						const a = document.createElement('a')
						a.innerText = `${temp.itemName}`;

						itemSection.append(wishItem, a);

						itemWrapper.append(itemSection);
					}

					item.append(itemWrapper);

					overlayScreenWish.append(item)

					wishListItems.append(empty, overlayScreenWish, empty2)

					overlayScreenWish.classList.add("play");
			
					overlayScreenWish.addEventListener("animationend", function handler() {
						this.removeEventListener("animationend", handler);
						this.classList.remove("play");
					})
				}
				move()

			})


		}

		if(e.target.value == 'camp'){
			
			fetch("/member/selectCampWish")
			.then(resp => resp.json())
			.then(data => {
				
				if(data.length == 0){

					const empty = document.createElement('section');
					empty.classList.add('empty2');

					const empty2 = document.createElement('section');
					empty2.classList.add('empty2');

					const left = document.createElement('button');
					left.classList.add('arrow');
					left.setAttribute("type", "button");
					left.setAttribute("id", "left")

					const right = document.createElement('button');
					right.classList.add('arrow');
					right.setAttribute("type", "button");
					right.setAttribute("id", "right")

					empty.append(left)
					empty2.append(right)

					const overlayScreenWish = document.createElement('div')
					overlayScreenWish.classList.add('overlayScreenWish')

					const itemSection = document.createElement('section');
					itemSection.classList.add('itemSection')

					const p = document.createElement('p')
					p.innerText = "관심 상품이 없습니다.";
					
					itemSection.append(p)

					overlayScreenWish.append(itemSection)

					wishListItems.append(empty, overlayScreenWish, empty2)

					overlayScreenWish.classList.add("play");
			
					overlayScreenWish.addEventListener("animationend", function handler() {
						this.removeEventListener("animationend", handler);
						this.classList.remove("play");
					})

				} else {

					const empty = document.createElement('section');
					empty.classList.add('empty2');

					const empty2 = document.createElement('section');
					empty2.classList.add('empty2');

					const left = document.createElement('button');
					left.classList.add('arrow');
					left.setAttribute("type", "button");
					left.setAttribute("id", "left")
					
					const right = document.createElement('button');
					right.classList.add('arrow');
					right.setAttribute("type", "button");
					right.setAttribute("id", "right")
					
					if(data.length > 4) {
						const leftIcon = document.createElement('img');
						leftIcon.setAttribute('src', '/images/iconImg/left.png');
						
						const rightIcon = document.createElement('img');
						rightIcon.setAttribute('src', '/images/iconImg/right.png');

						left.append(leftIcon)
						right.append(rightIcon)

						empty.append(left)
						empty2.append(right)
					}

					const overlayScreenWish = document.createElement('div')
					overlayScreenWish.classList.add('overlayScreenWish')

					const item = document.createElement('div');
					item.classList.add('item')

					const itemWrapper = document.createElement('section');
					itemWrapper.classList.add('itemWrapper');

					for(let temp of data){

						const itemSection = document.createElement('section');
						itemSection.classList.add('itemSection');

						const wishItem = document.createElement('img');
						wishItem.classList.add('wishItem')	
						wishItem.setAttribute('src', `${temp.campThumbnail}`);
						wishItem.setAttribute('onclick', `location.href='/camp/${temp.campNo}'`)

						const a = document.createElement('a')
						a.innerText = `${temp.campName}`;

						itemSection.append(wishItem, a);

						itemWrapper.append(itemSection);
					}

					overlayScreenWish.append(item)
					
					item.append(itemWrapper);

					wishListItems.append(empty, overlayScreenWish, empty2)

					overlayScreenWish.classList.add("play");
			
					overlayScreenWish.addEventListener("animationend", function handler() {
						this.removeEventListener("animationend", handler);
						this.classList.remove("play");
					})
					
					move()
				}

			})




		}
	})

}

modal()

function modal(){
	let qnaBtn = document.querySelectorAll(".qnaBtn");
	let modalContainerPopup = document.getElementById('modalContainerPopup');
	let QNAAnswerTitle = document.getElementById('QNAAnswerTitle')
	let QNAAnswerContent = document.getElementById('QNAAnswerContent')
	let QNAAnswer = document.getElementById('QNAAnswer')
	
	for(var i = 0 ; i <qnaBtn.length ; i++){
	
		qnaBtn[i].addEventListener("click", e => {
	
			modalContainerPopup.classList.remove('hidden');
	
			const qnaNo = (e.target.id).split("_")[1]
			
			fetch("/member/selectQnaOne?qnaNo=" + qnaNo)
			.then(resp => resp.json())
			.then(data => {
	
				QNAAnswerTitle.innerText=`${data.qnatitle}`
				QNAAnswerContent.innerText = `${data.qnacontent}`
	
				if(data.qnaanswer == null){
	
					QNAAnswer.innerText = "답변 대기중"
	
				} else {
	
					QNAAnswer.innerText = `${data.qnaanswer}`
	
				}
	
	
			})
	
	
		})
	
		window.addEventListener('click', (e) => {
		
			e.target === modalContainerPopup ? modalContainerPopup.classList.add('hidden') : false
			
		});
	
	}
}

modalCeo()

function modalCeo(){
	let qnaBtn = document.querySelectorAll(".qnaBtn");
	let modalContainerPopup = document.getElementById('modalContainerPopup');
	let QNAAnswerTitle = document.getElementById('QNAAnswerTitle')
	let QNAAnswerContent = document.getElementById('QNAAnswerContent')
	let QNAAnswer = document.getElementById('QNAAnswer')
	
	for(var i = 0 ; i <qnaBtn.length ; i++){
	
		qnaBtn[i].addEventListener("click", e => {
	
			modalContainerPopup.classList.remove('hidden');
	
			const ceoQnaNo = (e.target.id).split("_")[1]
			
			fetch("/member/selectCeoQnaOne?ceoQnaNo=" + ceoQnaNo)
			.then(resp => resp.json())
			.then(data => {
	
				QNAAnswerTitle.innerText=`${data.ceoQnaTitle}`
				QNAAnswerContent.innerText = `${data.ceoQnaContent}`
	
				if(data.qnaanswer == null){
	
					QNAAnswer.innerText = "답변 대기중"
	
				} else {
	
					QNAAnswer.innerText = `${data.qnaanswer}`
	
				}
	
	
			})
	
	
		})
	
		window.addEventListener('click', (e) => {
		
			e.target === modalContainerPopup ? modalContainerPopup.classList.add('hidden') : false
			
		});
	
	}
}






