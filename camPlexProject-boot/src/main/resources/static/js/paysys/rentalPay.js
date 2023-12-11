/*function updateQuantity(button, change) {
    var index = button.id.replace(/(plusBtn|minusBtn)/, '');
    var quantityElement = document.getElementById('quantity' + index);
    var totalPriceElement = document.getElementById('totalPrice' + index);

    var currentQuantity = parseInt(quantityElement.textContent);
    var itemPrice = parseInt(button.dataset.price); // 가격을 data-price 속성에서 가져옴

    var newQuantity = currentQuantity + change;
    if (newQuantity > 0) {
        quantityElement.textContent = newQuantity;
        var newTotalPrice = newQuantity * itemPrice;
        totalPriceElement.textContent = newTotalPrice + '원';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    // '+' 및 '-' 버튼 이벤트 리스너
    document.querySelectorAll('.qBtn').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var change = this.id.includes('plusBtn') ? 1 : -1;
            updateQuantity(this, change);
        });
    });
});*/


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


function updateQuantityAndTotal(index, change) {
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
}

function updateOverallTotal() {
    var total = 0;
    document.querySelectorAll('.itemListTB td[id^="totalPrice"]').forEach(function(td) {
        var price = parseInt(td.textContent.replace('원', '')) || 0;
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
    option.innerText = "국민은행 00440204106870 이재경";
    const option2 = document.createElement('option');
    option2.setAttribute("value", "toss")
    option2.innerText = "토스뱅크 100001065362 최규연";

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