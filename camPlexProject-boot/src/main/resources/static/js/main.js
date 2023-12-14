const wrap = document.getElementsByClassName('mainImage')[0]; // 보일 영역
const container = document.getElementsByClassName('mainContent');
let page = 1; // 영역 포지션 초기값
const lastPage = container.length; // 마지막 페이지
let isScrolling = false;

function scrollToSection(sectionId) {
    const section = document.getElementById(sectionId);
	
    if (section) {
        section.scrollIntoView({
            behavior: 'smooth'
        });
    }
    
}


let btn = document.querySelectorAll('.Btn')

document.getElementById('main1Btn').addEventListener("click", (e) => {
    
    page = 1;
})

document.getElementById('main2Btn').addEventListener("click", (e) => {
    page = 2;
})

document.getElementById('main3Btn').addEventListener("click", (e) => {
    page = 3;
})

document.getElementById('main4Btn').addEventListener("click", (e) => {
    page = 4;
})

document.getElementById('main5Btn').addEventListener("click", (e) => {
    page = 5;
})

window.addEventListener('wheel',(e)=>{
    
    e.preventDefault();
    
    if (e.deltaY > 0) {
        page++;
    } else if (e.deltaY < 0) {
        page--;
    }
    
    if (page < 1) {
        page = 1;
    } else if (page > lastPage) {
        page = lastPage;
    }
    
    // 섹션 스크롤
    location.href = "/#main" + page;
    
    // 스크롤 플래그 설정
    isScrolling = true;
    
    // 일정 시간이 지난 후 스크롤 플래그 초기화
    setTimeout(() => {
        isScrolling = false;
    }, 200); // 500 밀리초 (0.5초) 후 스크롤 플래그 초기화
    
    console.log(e.deltaY)
    console.log(page)
    console.log("#main" + page)
}, { passive: false });