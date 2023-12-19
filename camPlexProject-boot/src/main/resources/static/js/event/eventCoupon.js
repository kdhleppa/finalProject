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


function scracth(_id, option = {}){
    const canvas = document.getElementById(_id)
    let ctx = canvas.getContext('2d')
    let width = canvas.width
    let height = canvas.height
    let size = option.size || 68
    let row = height / size / 3
    let column = width / size / 3
    let maxSize = row * column
	
    let inSideArray = []
    let dataArray = []

    ctx.save()
    ctx.beginPath()
    ctx.fillStyle='lightgreen';
    ctx.rect(0,0,width,height)
    ctx.font = '24px Arial';
    ctx.fill();
    ctx.closePath()
    ctx.restore()      

    let stopDrawing = false
    let inter = null
    function _isInside(x1, y1){

        if(inSideArray.length >= maxSize){  //총 크기에 원이 다다른 경우
            stopDrawing = true  //그만그려

            let i = 1
            inter = setInterval(() => {  //페이드 인 아웃 효과 입니다
                ctx.save()
                ctx.beginPath()
                ctx.clearRect(0,0,width,height)
                ctx.rect(0,0,width,height)
                ctx.fillStyle = `rgba(0,0,0,${i})`
                ctx.fill()
                ctx.closePath()
                ctx.restore()                 
                
                if(i <= 0) {
                    clearInterval(inter)
                    inter = null
                }

                dataArray.forEach(item => {
                    ctx.save()
                    ctx.beginPath()
                    ctx.globalCompositeOperation= 'destination-out'
                    ctx.arc(item.x, item.y, size, (Math.PI/180)*0, (Math.PI/180)* 360 , false)
                    ctx.fill()
                    ctx.closePath()
                    ctx.restore()                      
                })
                i -= 0.1
            }, 40)
        }


        if(stopDrawing) return

        let check = inSideArray.filter(arg =>{  //조사합니다 대상원이 포함되는지
            let x = arg.x - x1
            let y = arg.y - y1
            let my_len = Math.sqrt(Math.abs(x * x) + Math.abs(y * y))
            return my_len < size
        })

        let json = {x : x1, y : y1, target: false}
        if(!check || check.length ==0){
            json.target = true
            inSideArray.push(json)  //대상원을 추가 합니다
        }
        dataArray.push(json)  //다시 그리기용(페이드인 아웃용) 배열에 넣습니다
    }

    //그리는 함수 입니다
    function _drawding(_x, _y){
        ctx.save()
        ctx.beginPath()
        ctx.globalCompositeOperation= 'destination-out'
        ctx.arc(_x, _y, size, (Math.PI/180)*0, (Math.PI/180)* 360 , false)
        ctx.fill()
        ctx.closePath()
        ctx.restore()  
    }    

    canvas.addEventListener('mousemove', (event) =>{
        if(!ctx || stopDrawing) return
        let x1 = event.clientX - canvas.parentElement.offsetLeft || canvas.offsetLeft
        let y1 = event.clientY - canvas.parentElement.offsetTop || canvas.offsetTop
        _isInside(x1, y1)
        _drawding(x1, y1)
    })
    
    return {
        reDraw : (arg)=>{
            if(!inter){
                //초기화를 합니다 && 그리기 입니다.
                ctx.save()
                ctx.beginPath()
                ctx.clearRect(0,0,width,height)
                ctx.rect(0,0,width,height)
                ctx.fillStyle = `rgba(0,0,0,1)`
                ctx.fill()
                ctx.closePath()
                ctx.restore()               
                stopDrawing = false
                inter = null
                inSideArray = inSideArray.filter( (arg)=> false)
                dataArray = dataArray.filter( (arg)=> false)
            }

            if(arg && arg instanceof Function){
                arg(stopDrawing)
                console.log(inter)
            }
        }
    }
}

let sct = scracth('canvas')

document.getElementById('id_btn').addEventListener('click', function() {
      document.querySelector('.scratchZone').style.display = 'block';
    });
