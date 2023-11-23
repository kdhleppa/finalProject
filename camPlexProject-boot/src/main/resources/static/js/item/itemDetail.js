document.addEventListener('DOMContentLoaded', function () {
    var quantityInput = document.getElementById('quantity');
    var quantityDisplay = document.getElementById('quantityDisplay');
    
    // '+' 버튼 클릭 이벤트
    document.getElementById('plusBtn').addEventListener('click', function () {
        quantityInput.value = parseInt(quantityInput.value) + 1;
        quantityDisplay.textContent = quantityInput.value;
        updateTotal(); // 총액 업데이트
    });

    // '-' 버튼 클릭 이벤트
    document.getElementById('minusBtn').addEventListener('click', function () {
        if (quantityInput.value > 0) {
            quantityInput.value = parseInt(quantityInput.value) - 1;
            quantityDisplay.textContent = quantityInput.value;
            updateTotal(); // 총액 업데이트
        }
    });

    // 총액 계산 및 업데이트 함수
    function updateTotal() {
        var itemPrice = document.getElementById('itemPrice').innerText;
        var quantity = parseInt(document.getElementById('quantity').value);

        // 원(KRW) 기호와 쉼표를 제거하고 숫자로 변환
        itemPrice = parseInt(itemPrice.replace(/₩|,/g, ''));

        var totalPrice = itemPrice * quantity;

        // 결과를 형식에 맞게 표시 (예: ₩240,000)
        document.querySelector('.paymentTextContainer p').innerText = '₩' + totalPrice.toLocaleString();
    }

    // 페이지 로드 시 초기 총액 설정
    updateTotal();
});




