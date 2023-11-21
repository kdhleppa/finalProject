const wrap = document.getElementsByClassName('mainImage')[0]; // 보일 영역
const container = document.getElementsByClassName('mainContent');
let page = 0; // 영역 포지션 초기값
const lastPage = container.length ; // 마지막 페이지

window.addEventListener('wheel',(e)=>{
    e.preventDefault();
    if(e.deltaY > 0){
        page++;
    }else if(e.deltaY < 0){
        page--;
    }
    if(page < 0){
        page=0;
    }else if(page > lastPage){
        page = lastPage;
    }
    console.log(e.deltaY)
    wrap.style.top = page * -100 + 'vh';
},{passive:false}); // 디폴트 기능 제거 - 스크롤