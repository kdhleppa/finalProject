document.addEventListener('DOMContentLoaded', function () {
    var quantityInput = document.getElementById('quantity');
    var quantityInput2 = document.getElementById('quantity2');
    var quantityDisplay = document.getElementById('quantityDisplay');
    
    // '+' 버튼 클릭 이벤트
    document.getElementById('plusBtn').addEventListener('click', function () {
        quantityInput.value = parseInt(quantityInput.value) + 1;
        quantityInput2.value = parseInt(quantityInput.value) + 1;
        quantityDisplay.textContent = quantityInput.value;
        updateTotal(); // 총액 업데이트
    });

    // '-' 버튼 클릭 이벤트
    document.getElementById('minusBtn').addEventListener('click', function () {
        if (quantityInput.value > 1) {
            quantityInput.value = parseInt(quantityInput.value) - 1;
            quantityInput.value2 = parseInt(quantityInput.value) - 1;
            quantityDisplay.textContent = quantityInput.value;
            updateTotal(); // 총액 업데이트
        }
    });

	// 총액 계산 및 업데이트 함수
 	function updateTotal() {
        var itemPrice = document.getElementById('itemPrice').innerText;
        var quantity = parseInt(document.getElementById('quantity').value);

        // 원(KRW) 기호와 쉼표를 제거하고 숫자로 변환
        var tempPrice = parseInt(itemPrice.replace(/￦|,/g, ''));

        var totalPrice = tempPrice * quantity;

        // 결과를 형식에 맞게 표시 (예: ₩240,000)
        document.querySelector('.paymentTextContainer p').innerText = '￦' + totalPrice.toLocaleString();
    }	


    // 페이지 로드 시 초기 총액 설정
    updateTotal();
});


var wishBtnClicked = false;
document.querySelector(".wishBtn").addEventListener("click", function() {
    wishBtnClicked = true;
});
document.getElementById("payFrm").onsubmit = function() {
    var selectedValue = document.getElementById("defaultOpt").value;
	

	
    if (!wishBtnClicked && selectedValue === "default") {
        alert("예약된 캠핑장이 선택되어있어야 합니다.");
        return false;
    }
};


