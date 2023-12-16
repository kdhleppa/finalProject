const openModalLink = document.querySelectorAll(".openModalLink");


for(var i = 0 ; i < openModalLink.length ; i++){
	
	openModalLink[i].addEventListener('click', e => {
		
		
		console.log("fff")
		
		/*if(loginMemberNo != null){ 
			openModalLinkOpen();
			e.preventDefault();
			return;
	    } else {
			alert("로그인 후 이용해주세요.");
	        return;
		}*/
		
	});
	
	
}


// 모달 열기
function openModalLinkOpen() {
    document.querySelector('.openModalLink_wrap').style.display = 'block';
    document.querySelector('.openModalLink_background').style.display = 'block';
    
}

// 모달 끄기
function openModalLinkClose() {
    document.querySelector('.openModalLink_wrap').style.display = 'none';
    document.querySelector('.openModalLink_background').style.display = 'none';
}


//버튼 클릭리스너 달기
document.querySelector('.openModalLink_close').addEventListener('click', myModalClose);

const openModalLinkContainer = document.getElementById("openModalLinkContainer");
const openModalLinkBack = document.getElementById("openModalLinkBack");


(function(){
	document.addEventListener('keydown', function(e){
		const keyCode = e.keyCode;
		
		if(keyCode == 27){
			openModalLinkContainer.style.display = 'none';
			openModalLinkBack.style.display = 'none';
		}
	})
	
})();

