document.addEventListener('DOMContentLoaded', function () {
    // 모든 '+' 버튼에 대한 이벤트 리스너 추가
    document.querySelectorAll('[id^="plusBtn"]').forEach(function (plusBtn) {
        plusBtn.addEventListener('click', function () {
            var index = this.id.replace('plusBtn', '');
            updateQuantityAndCart(index, 1);
            
        });
    });

    // 모든 '-' 버튼에 대한 이벤트 리스너 추가
    document.querySelectorAll('[id^="minusBtn"]').forEach(function (minusBtn) {
        minusBtn.addEventListener('click', function () {
            var index = this.id.replace('minusBtn', '');
            updateQuantityAndCart(index, -1);
        });
    });
    
});

window.onload = function() {
    // "index-input" 클래스를 가진 모든 요소를 선택
    var inputs = document.querySelectorAll('input.index-input');

    // 각 input 요소에 대해 반복
    inputs.forEach(function(input) {
        var index = input.value;
        updateTotal(index);
    });
    
    
    var rsvInfos = document.querySelectorAll('[id^="rsvInfo"]');

    rsvInfos.forEach(function(rsvInfo) {
        var rsvInfoIndex = rsvInfo.id.replace('rsvInfo', '');
        var cartItemRows = document.querySelectorAll('tr[id="cartItemTr' + rsvInfoIndex + '"]');

        if (cartItemRows.length === 0) {
            rsvInfo.style.display = 'none'; // 혹은 rsvInfo.remove(); 로 완전히 제거
        }
    });
};






function updateQuantityAndCart(index, change) {
    var quantityDisplay = document.getElementById('quantityDisplay' + index);
    var cartItemNo = document.getElementById('cartItemNo' + index).value
    var currentQuantity = parseInt(quantityDisplay.textContent);
    var newQuantity = Math.max(0, currentQuantity + change);
    quantityDisplay.textContent = newQuantity;

    // Fetch API를 사용하여 서버에 변경사항 전송
    fetch('/paysys/quantityUpdateCart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ cartItemNo: cartItemNo, itemQuantity: newQuantity })
    })
    .then(response => response.json())
    .then(data => {
        if (data && data.message === "성공") {
        document.getElementById('quantityDisplay' + index).textContent = newQuantity;
        updateTotal(index);
        } else {
			console.log("에러 삭제실패")
		}
    })
    .catch(error => {
        // 오류 처리
        console.error('장바구니 업데이트 실패:', error);
    });
}




function updateTotal(index) {
	var itemPrice = document.getElementById('itemPrice'+index).value
	var quantity = parseInt(document.getElementById('quantityDisplay'+index).textContent)
	
	var totalPrice = itemPrice * quantity;
	console.log(totalPrice);
	document.getElementById('totalPriceContainer' + index).querySelector('p').innerText = '₩' + totalPrice.toLocaleString();
}




