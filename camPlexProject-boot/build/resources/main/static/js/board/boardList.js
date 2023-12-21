const insertBtn = document.querySelector("#insertBtn");

// 글쓰기 버튼 클릭 시
if(insertBtn != null) { // 로그인 여부에 따라 insertBtn에 있는가 없는가에 대한 예외 처리
	insertBtn.addEventListener("click", () => {
		
		if(memberType != 'M' && location.pathname.split("/")[2] == 'N') {
			alert("공지사항은 관리자만 작성 가능합니다.")
			return;
		}
		
		location.href = `/board2/${location.pathname.split("/")[2]}/insert`;

	});
}

const boardSort = document.querySelectorAll('input[name="boardSort"]');
let overlayScreen = document.querySelector(`.overlayScreen`);
	
for(var i = 0 ; i < boardSort.length ; i++) {
	
	boardSort[i].addEventListener("change", e => {
		
		let boardList = document.querySelectorAll('.boardList')
		
		overlayScreen.addEventListener("animationend", function handler() {
			this.removeEventListener("animationend", handler);
			this.classList.remove("play");
		})
		
		if(e.target.value == 'recent') {
			
			fetch("/board/{boardType:[A-Z]+}")
			.then(resp => resp.json())
			.then(data => {
				
				e.defaultPrevented();
				
			})
			
		}
		
		if(e.target.value == 'readCount') {
			
			for(var i = 0; i < boardSort.length; i++) {
				boardSort[i].classList.remove('clicked')
			}
			
			e.target.classList.add('clicked')
			
			fetch("/board/orderReadCount")
			.then(resp => resp.json())
			.then(data => {
				
				console.log(data);
				
			})
		}
		
		if(e.target.value == 'likeCount') {
			
			for(var i = 0; i < boardSort.length; i++) {
				boardSort[i].classList.remove('clicked')
			}
			
			e.target.classList.add('clicked')
			
			fetch("/board/orderLikeCount")
			.then(resp => resp.json())
			.then(data => {
				
				console.log(data);
				
			})
		}
		
	})
}