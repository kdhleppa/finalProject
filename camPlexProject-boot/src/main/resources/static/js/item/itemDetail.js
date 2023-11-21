//+-버튼 반응
document.addEventListener('DOMContentLoaded', function () {
    var quantityInput = document.getElementById('quantity');
    var quantityDisplay = document.getElementById('quantityDisplay');
    
    document.getElementById('plusBtn').addEventListener('click', function () {
        quantityInput.value = parseInt(quantityInput.value) + 1;
        quantityDisplay.textContent = quantityInput.value;
    });

    document.getElementById('minusBtn').addEventListener('click', function () {
        if (quantityInput.value > 0) {
            quantityInput.value = parseInt(quantityInput.value) - 1;
            quantityDisplay.textContent = quantityInput.value;
        }
    });
});


document.getElementById('cartBtn').addEventListener('click', function () {
    var formData = new FormData(document.querySelector('.productContentContainer'));
    // AJAX 요청을 이용하여 formData를 서버에 전송
    // 예: axios.post('/cart/add', formData);
});

document.getElementById('wishBtn').addEventListener('click', function () {
    var formData = new FormData(document.querySelector('.productContentContainer'));
    // AJAX 요청을 이용하여 formData를 서버에 전송
    // 예: axios.post('/wishlist/add', formData);
});
