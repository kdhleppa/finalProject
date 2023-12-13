
// 모달 창 esc버튼 누르면 사라짐
const mapContainer = document.getElementById('mapContainer');

(function(){
	document.addEventListener('keydown', function(e){
	  const keyCode = e.keyCode;
  
	  if(keyCode == 27){
		mapContainer.classList.add('hidden');
	  }
	})
  })();
  
// 모달 창 띄우기 함수
function mapModal() {
	mapContainer.classList.remove('hidden');

	// 카카오 맵 api
	var mapKko = document.getElementById('mapKko'), // 지도를 표시할 div 
	mapOption = { 
		center: new kakao.maps.LatLng(37.07, 127.8), // 지도의 중심좌표
		level: 11 // 지도의 확대 레벨
	};

	// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
	var map = new kakao.maps.Map(mapKko, mapOption);

	var geocoder = new kakao.maps.services.Geocoder();

	fetch("/camp2/selectCampAddress")
  	.then(resp => resp.json())
  	.then(addressList => {})
	.catch(err => console.log(err));

	// 주소로 좌표를 검색합니다
	geocoder.addressSearch('제주특별자치도 제주시 첨단로 242', function(result, status) {

		// 정상적으로 검색이 완료됐으면 
		if (status === kakao.maps.services.Status.OK) {

			var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

			// 결과값으로 받은 위치를 마커로 표시합니다
			var marker = new kakao.maps.Marker({
				map: map,
				position: coords
			});

			// 인포윈도우로 장소에 대한 설명을 표시합니다
			var infowindow = new kakao.maps.InfoWindow({
				content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
			});
			infowindow.open(map, marker);

		} 
	});    

};
  

