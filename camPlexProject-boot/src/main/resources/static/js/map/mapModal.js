const mapContainer = document.getElementById('mapContainer');

// 모달 창 esc버튼 누르면 사라짐
(function () {
	document.addEventListener('keydown', function (e) {
		const keyCode = e.keyCode;

		if (keyCode == 27) {
			mapContainer.classList.add('hidden');
		}
	})
})();

// 모달 창 띄우기 함수
function mapModal() {
	mapContainer.classList.remove('hidden');
}


var container = document.getElementById('map');
var options = {
	center: new kakao.maps.LatLng(33.450701, 126.570667),
	level: 3
};

var map = new kakao.maps.Map(container, options);
