const getPrize = function() {
    //랜덤값 생성 (1~100)
    const ranNum = Math.floor((Math.random() * 99) +1)

    //경품 생성
    const gift = ['1등 30% 쿠폰 당첨!', '2등 20% 쿠폰 당첨!', '3등 10% 쿠폰 당첨!', '꽝! 다음 기회에..']
    //확률 생성
    const pbt = [5, 15, 30, 50]
    //리턴 경품 값
    let res = ''

    for (let i = 0; i < gift.length; i++) {

        if(pbt[i] >= ranNum){
            res = gift[i]
            return res
        }
        else if (pbt[pbt.length - 1] < ranNum) {
            res = gift[gift.length - 1]
            return res
        }
    }
}

 document.addEventListener('DOMContentLoaded', () => {
	if(loginMemberNo == null){
        alert("로그인 후 이용해주세요")
        window.history.back();
    }
	 
    const btnTag = document.querySelector('#id_btn')
    const outTag = document.querySelector('#id_out')
    
	    btnTag.addEventListener('click', (event) => {
	    const result = getPrize()
	    outTag.textContent = `${result}`
	    btnTag.disabled = true;
	    
	})
})

