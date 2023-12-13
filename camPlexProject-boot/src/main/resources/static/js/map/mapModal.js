const mapContainer = document.getElementById('mapContainer');

// 모달 창 esc버튼 누르면 사라짐
(function(){
	document.addEventListener('keydown', function(e){
	  const keyCode = e.keyCode;
  
	  if(keyCode == 27){
		mapContainer.classList.add('hidden');
	  }
	})
  })();

var Container = document.getElementById('map'), // 지도를 표시할 div 
mapOption = { 
	  center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	  level: 3 // 지도의 확대 레벨
  };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(Container, mapOption); 

// 모달 창 띄우기 함수
function mapModal() {
	mapContainer.classList.remove('hidden');
}
