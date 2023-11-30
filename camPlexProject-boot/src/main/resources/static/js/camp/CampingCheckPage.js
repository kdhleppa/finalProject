const contentDetail = document.querySelectorAll('.contentDetailSec')
const bBlank = document.getElementsByClassName('bBlank')
let contentCount = 6;

document.addEventListener('DOMContentLoaded', function() {
	
	
	if(contentDetail.length > contentCount){
		
		const btn = document.createElement('button');
		btn.classList.add('moreBtn')
		btn.setAttribute("type", "button")
		btn.innerHTML = "더 보기<br>∨";
		
		bBlank[0].append(btn)
	}
	
	const moreBtn = document.getElementsByClassName('moreBtn')
	const mainContent = document.querySelector('.mainContent')
	
	moreBtn[0].addEventListener("click", () => {
				
		mainContent.style.height = mainContent.offsetHeight+352+'px';
		
		contentCount += 3;

		bBlank[0].innerHTML = "";
	})
	
});