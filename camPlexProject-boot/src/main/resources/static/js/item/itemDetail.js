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


function submitForm(action) {
	document.getElementById('payFrm').action = action;
	document.getElementById('payFrm').submit();
}