// 모달 열기
function qmodalOpen() {
    document.querySelector('.qmodal_wrap').style.display = 'block';
    document.querySelector('.qmodal_background').style.display = 'block';
}

// 모달 끄기
function qmodalClose() {
    document.querySelector('.qmodal_wrap').style.display = 'none';
    document.querySelector('.qmodal_background').style.display = 'none';
}


//버튼 클릭리스너 달기
document.querySelector('#qmodal_btn').addEventListener('click', qmodalOpen);
document.querySelector('.qmodal_close').addEventListener('click', qmodalClose);

const qmodalContainer = document.getElementById("qmodalContainer");
const qmodalBack = document.getElementById("qmodalBack");


(function(){
	document.addEventListener('keydown', function(e){
		const keyCode = e.keyCode;
		
		if(keyCode == 27){
			qmodalContainer.style.display = 'none';
			qmodalBack.style.display = 'none';
		}
	})
	
})();