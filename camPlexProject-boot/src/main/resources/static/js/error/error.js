
const starBtn = document.getElementById('starBtn')
let temp = 0;
	
starBtn.addEventListener("click", e => {

	if(temp == 0){
		
		e.target.setAttribute('src', "/images/iconImg/marsh4.png")
		setTimeout(function(){e.target.setAttribute('src', "/images/iconImg/marsh3.png")}, 300)
		temp = 1;
		
	} else {
		
		e.target.setAttribute('src', "/images/iconImg/marsh1.png")
		temp = 0;
	}
	
})
		



