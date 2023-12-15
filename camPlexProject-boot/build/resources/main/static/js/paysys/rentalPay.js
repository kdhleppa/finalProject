document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.minusBtn, .plusBtn').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var index = this.dataset.index;
            var change = this.classList.contains('plusBtn') ? 1 : -1;
            console.log("index", index);
            console.log("change", change);
            updateQuantityAndTotal(index, change);
        });
    });
    
    updateOverallTotal();
});


/*function updateQuantityAndTotal(index, change) {
    var quantityElement = document.getElementById('quantity' + index);
    var totalPriceElement = document.getElementById('totalPrice' + index);
    var currentQuantity = parseInt(quantityElement.textContent) || 0;
    var itemPrice = parseInt(document.querySelector('[data-index="' + index + '"]').dataset.price) || 0;
	var rentalItemQuantity = document.getElementById('rentalItemQuantity' + index);



    var newQuantity = currentQuantity + change;
    if (newQuantity > 0) {
        quantityElement.textContent = newQuantity;
        rentalItemQuantity.value = newQuantity;
        var newTotalPrice = newQuantity * itemPrice;
        totalPriceElement.textContent = newTotalPrice + '원';
    }

    updateOverallTotal();
}*/

function updateQuantityAndTotal(index, change) {
    var quantityElement = document.getElementById('quantity' + index);
    var totalPriceElement = document.getElementById('totalPrice' + index);
    var discountRateElement = document.getElementById('discountRate' + index);
    var currentQuantity = parseInt(quantityElement.textContent) || 0;
    var itemPrice = parseFloat(document.querySelector('[data-index="' + index + '"]').dataset.price) || 0;
    var discountRate = discountRateElement.value;
    console.log(discountRate);
    // 새 수량 계산
    var newQuantity = currentQuantity + change;
    if (newQuantity > 0) {
        quantityElement.textContent = newQuantity;

        // 새 총 가격 계산
        var newTotalPrice = newQuantity * itemPrice;

        // 할인율이 적용되는 경우
        if (discountRate > 0) {
            var discountedPrice = newTotalPrice * (1 - discountRate / 100);
            totalPriceElement.textContent = '₩' + discountedPrice.toLocaleString() + '원';
        } else {
            // 할인율이 적용되지 않는 경우
            totalPriceElement.textContent = '₩' + newTotalPrice.toLocaleString() + '원';
        }
    }

    updateOverallTotal();
}


function updateOverallTotal() {
    var total = 0;
    document.querySelectorAll('.itemListTB td[id^="totalPrice"]').forEach(function(td) {
        var price = parseInt(td.textContent.replace(/₩|원|,/g, '')) || 0;
        total += price;
    });
    document.getElementById('overallTotal').textContent = total + '원';
    document.getElementById('price').value = total;
}

function deleteBtn (element) {
	var index = element.getAttribute('data-index');
	var tr = document.getElementById('tr' + index);
	tr.remove();
	updateOverallTotal();
		
}


const payBy = document.querySelectorAll('input[name="payBy"]');
let payFrm = document.getElementById('payFrm')

for(var i = 0 ; i <payBy.length ; i ++){

    payBy[i].addEventListener("change", (e) => {
        
        const bankAccountContainer = document.getElementById('bankAccountContainer');
    
        if(e.target.value == 'b'){
            makeAccount();
            payFrm.action ="/paysys/payDoneRental"

            payFrm.addEventListener("submit", (e) => {
                
                pay(e)    


            })

        } else if (e.target.value == 'k'){
            payFrm.action ="/kakaoRentalPay"
            bankAccountContainer.innerHTML = "";
        } else if (e.target.value == 'n'){
            alert("현재 점검 중입니다.")
            e.target.checked = false;
        }
    })
}

function pay(e){
        
    const senderName = document.getElementById('senderName');

    if(senderName.value.trim().length == 0){
        alert("이름을 입력해주세요")
        e.preventDefault();
        return;
    }

}


function makeAccount() {

    const bankAccountContainer = document.getElementById('bankAccountContainer');

    const bankAccount = document.createElement('div');
    bankAccount.classList.add('bankAccount');

    const account = document.createElement('div');
    account.classList.add('account');

    const accountName = document.createElement('p');
    accountName.innerText = "입금 계좌 *";

    const select = document.createElement('select');
    select.setAttribute("name", "bank")

    const option = document.createElement('option');
    option.setAttribute("value", "kb")
    option.innerText = "국민은행 0044011111111 이재경";
    const option2 = document.createElement('option');
    option2.setAttribute("value", "toss")
    option2.innerText = "토스뱅크 100011111111 최규연";

    select.append(option, option2)

    account.append(accountName, select);

    const host = document.createElement('div');
    host.classList.add('host');

    const hostName = document.createElement('p')
    hostName.innerText = "입금자 명 *";

    const input = document.createElement('input')
    input.setAttribute("type", "text")
    input.setAttribute("name", "senderName")
    input.setAttribute("id", "senderName")

    host.append(hostName, input);

    bankAccount.append(account, host);

    bankAccountContainer.append(bankAccount)
}