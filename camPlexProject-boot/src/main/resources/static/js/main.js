const wrap = document.getElementsByClassName('mainImage')[0]; // 보일 영역
const container = document.getElementsByClassName('mainContent');
let page = 0; // 영역 포지션 초기값
const lastPage = container.length -1; // 마지막 페이지
let isScrolling = false;

window.addEventListener('wheel',(e)=>{
    console.log(e.deltaY)
    e.preventDefault();
    
    if (!isScrolling) {
        if (e.deltaY > 0) {
            page++;
        } else if (e.deltaY < 0) {
            page--;
        }

        if (page < 0) {
            page = 0;
        } else if (page > lastPage) {
            page = lastPage;
        }

        // 섹션 스크롤
        wrap.style.top = page * -100 + 'vh';

        // 스크롤 플래그 설정
        isScrolling = true;

        // 일정 시간이 지난 후 스크롤 플래그 초기화
        setTimeout(() => {
            isScrolling = false;
        }, 800); // 800 밀리초 (0.8초) 후 스크롤 플래그 초기화
        
    }
}, { passive: false });
	
document.getElementById('main1Btn').addEventListener("click", () => {
    
    
    const main1 = document.getElementById('main1').offsetHeight;
    window.scrollTo({main1, behaviors:'smooth'});

})