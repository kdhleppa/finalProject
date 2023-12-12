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


const askTo = document.querySelectorAll('input[name="askTo"]');
const textItemSection = document.querySelector(".textItemSection")

for(var i = 0 ; i < askTo.length ; i++){

	askTo[i].addEventListener("change", e => {
		
		let textList = document.querySelectorAll('.textList')
		const contentLength = textList.length;
		const moreBtnWrapper = document.getElementsByClassName('moreBtnWrapper')
		let contentCount = 5;

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
						
					textItmeList.style.height ='200px';
					
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
				
				for(qna of data){

					const textList = document.createElement('section');
					textList.classList.add('textList');

					if(data.length == 0){
						const p = document.createElement('p');
						p.innerText = "문의 내역이 없습니다.";

						textList.append(p);

						textItmeList.append(textList);

					} else{

						const a = document.createElement('a');
						a.setAttribute("id", `qna${qna.qnano}`)
						a.innerHTML = `${qna.qnacreateDate} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${qna.qnatitle}`

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
				
				for(qna of data){

					const textList = document.createElement('section');
					textList.classList.add('textList');

					if(data.length == 0){
						const p = document.createElement('p');
						p.innerText = "문의 내역이 없습니다.";

						textList.append(p);

						textItmeList.append(textList);

					} else{

						const a = document.createElement('a');
						a.setAttribute("id", `qna${qna.ceoQnaNo}`)
						a.innerHTML = `${qna.ceoQnaCreateDate}&nbsp; (${qna.campName}) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${qna.ceoQnaTitle}`

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
			})

		}

	})

}

let textList = document.querySelectorAll('.textList')
const contentLength = textList.length;
const moreBtnWrapper = document.getElementsByClassName('moreBtnWrapper')
const textItmeList = document.querySelector(".textItmeList")
let contentCount = 5;

more(contentLength)

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
				
			textItmeList.style.height ='200px';
			
			contentCount = 5;
	
			more(contentLength)
		})

	} 
	
}