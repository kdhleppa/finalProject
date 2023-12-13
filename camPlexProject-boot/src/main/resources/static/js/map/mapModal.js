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

var mapContainerDiv = document.getElementById('map'), // 지도를 표시할 div 
mapOption = { 
	  center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	  level: 4 // 지도의 확대 레벨
  };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainerDiv, mapOption); 

// 모달 창 띄우기 함수
function mapModal() {
	mapContainer.classList.remove('hidden');
}

function resizeMap() {
    var mapContainerDiv = document.getElementById('map');
    mapContainerDiv.style.width = '1000px';
    mapContainerDiv.style.height = '1000px'; 
}

function relayout() {    
    
    // 지도를 표시하는 div 크기를 변경한 이후 지도가 정상적으로 표출되지 않을 수도 있습니다
    // 크기를 변경한 이후에는 반드시  map.relayout 함수를 호출해야 합니다 
    // window의 resize 이벤트에 의한 크기변경은 map.relayout 함수가 자동으로 호출됩니다
    map.relayout();
}