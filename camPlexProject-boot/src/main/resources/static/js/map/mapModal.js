// 모달 창 esc버튼 누르면 모달 사라짐
const mapContainer = document.getElementById('mapContainer');

// esc 버튼 누르면 모달 사라짐
(function(){
	document.addEventListener('keydown', function(e){
	  const keyCode = e.keyCode;
  
	  if(keyCode == 27){
		mapContainer.classList.add('hidden');
	  }
	})
  })();
  
function modalHidden() {
	mapContainer.classList.add('hidden');
}
  
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

	fetch("/camp2/selectForMapOfCamp")
  	.then(resp => resp.json())
  	.then(campList => {

		
		for(let i in campList) {
			// 주소로 좌표를 검색합니다
			geocoder.addressSearch(campList[i].campAddress, function(result, status) {

				// 정상적으로 검색이 완료됐으면 
				if (status === kakao.maps.services.Status.OK) {

					var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

					// 결과값으로 받은 위치를 마커로 표시합니다
					var marker = new kakao.maps.Marker({
						map: map,
						position: coords
					});

					// 커스텀 오버레이에 표시할 내용입니다     
					// HTML 문자열 또는 Dom Element 입니다 
					var content = '<div class ="mapCampName" onclick="mapInfoClick(' + campList[i].campNo + ')">' + campList[i].campName +'</div>';

					// 커스텀 오버레이가 표시될 위치입니다 
					var position = new kakao.maps.LatLng(result[0].y, result[0].x);  

					// 커스텀 오버레이를 생성합니다
					var customOverlay = new kakao.maps.CustomOverlay({
						position: marker.getPosition(),
						content: content   
					});

					// 커스텀 오버레이를 지도에 표시합니다
					customOverlay.setMap(map);

				} 
			});    
		}


	})
	.catch(err => console.log(err));

};


// 버튼 클릭시 관련 캠프장 띄우기 함수
function campType(category) {

	console.log("category ::" +category);

	// 카카오 맵 api
	var mapKko = document.getElementById('mapKko'), // 지도를 표시할 div 
	mapOption = { 
		center: new kakao.maps.LatLng(37.07, 127.8), // 지도의 중심좌표
		level: 11 // 지도의 확대 레벨
	};

	// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
	var map = new kakao.maps.Map(mapKko, mapOption);

	var geocoder = new kakao.maps.services.Geocoder();

	fetch("/camp2/selectForMapOfCampCategory?category=" + category)
  	.then(resp => resp.json())
  	.then(campList => {

		console.log(campList);
		
		for(let i in campList) {
			// 주소로 좌표를 검색합니다
			geocoder.addressSearch(campList[i].campAddress, function(result, status) {

				// 정상적으로 검색이 완료됐으면 
				if (status === kakao.maps.services.Status.OK) {

					var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

					// 결과값으로 받은 위치를 마커로 표시합니다
					var marker = new kakao.maps.Marker({
						map: map,
						position: coords
					});

					// 커스텀 오버레이에 표시할 내용입니다     
					// HTML 문자열 또는 Dom Element 입니다 
					var content = '<div class ="mapCampName" onclick="mapInfoClick(' + campList[i].campNo + ')">' + campList[i].campName +'</div>';

					// 커스텀 오버레이가 표시될 위치입니다 
					var position = new kakao.maps.LatLng(result[0].y, result[0].x);  

					// 커스텀 오버레이를 생성합니다
					var customOverlay = new kakao.maps.CustomOverlay({
						position: marker.getPosition(),
						content: content   
					});

					// 커스텀 오버레이를 지도에 표시합니다
					customOverlay.setMap(map);

				} 
			});    
		}


	})
	.catch(err => console.log(err));

};

function mapInfoClick(campNo) {
	console.log("campNo ::" + campNo);
	location.href = "/camp/" + campNo;
}


  

