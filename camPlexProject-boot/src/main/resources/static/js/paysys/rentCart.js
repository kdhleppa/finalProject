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


function updateQuantityAndCart(index, change) {
    var quantityDisplay = document.getElementById('quantityDisplay' + index);
    var cartItemNo = document.getElementById('cartItemNo' + index)
    var currentQuantity = parseInt(quantityDisplay.textContent);
    var newQuantity = Math.max(0, currentQuantity + change);
    quantityDisplay.textContent = newQuantity;

    // Fetch API를 사용하여 서버에 변경사항 전송
    fetch('/paysys/quantityUpdateCart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ cartItemNo: cartItemNo, quantity: newQuantity })
    })
    .then(response => response.json())
    .then(data => {
        // 성공 응답 처리
        document.getElementById('quantityDisplay' + index).textContent = newQuantity;
        updateTotal(index);
        updateCartItemCount(data.totalItemsInCart);
    })
    .catch(error => {
        // 오류 처리
        console.error('장바구니 업데이트 실패:', error);
    });
}


document.addEventListener('DOMContentLoaded', function () {
    setupQuantityButtons(); // 페이지 로드 시 버튼 이벤트 리스너 설정
});