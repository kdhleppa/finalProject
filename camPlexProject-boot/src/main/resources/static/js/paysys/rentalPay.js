document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.minusBtn, .plusBtn').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var index = this.dataset.index;
            var change = this.classList.contains('plusBtn') ? 1 : -1;
            updateQuantityAndTotal(index, change);
        });
    });
    updateStayTotal();
    updateOverallTotal();
});

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
            totalPriceElement.textContent = discountedPrice.toLocaleString() + '원';
        } else {
            // 할인율이 적용되지 않는 경우
            totalPriceElement.textContent = newTotalPrice.toLocaleString() + '원';
        }
    }

    updateStayTotal();
}



function updateStayTotal() {
	var totalPriceElements = document.querySelectorAll("[id^='totalPrice']");
    totalPriceElements.forEach(function(element, index) {
            var stayDay = document.getElementById("stayDay" + index).value;
            var totalPrice = parseInt(element.textContent.replace(/₩|원|,/g, ''));

            var stayTotal = totalPrice * parseInt(stayDay);

            document.getElementById("stayTotal" + index).innerText = stayTotal.toLocaleString() + '원';
            updateOverallTotal()
        });
  
};

function updateOverallTotal() {
    var total = 0;
    console.log("hi");
    document.querySelectorAll('.itemListTB p[id^="stayTotal"]').forEach(function(p) {
        var price = parseInt(p.textContent.replace(/₩|원|,/g, '')) || 0;
        
        console.log(price);
        total += price;
    });
    document.getElementById('overallTotal').textContent = total.toLocaleString() + '원';
    document.getElementById('price').value = total;
}

function deleteBtn (element) {
	var index = element.getAttribute('data-index');
	var tr = document.getElementById('tr' + index);
	tr.remove();
	updateOverallTotal();
		
}
document.getElementById('payBtn').addEventListener('click', function(event) {
    var priceValue = document.getElementById('price').value;
    if(priceValue == "0") {
        alert("선택된 상품이 없습니다.");
        event.preventDefault();
    }
});

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
    option.innerText = "국민은행 004402111111 이재경";
    const option2 = document.createElement('option');
    option2.setAttribute("value", "toss")
    option2.innerText = "토스뱅크 100001111111 최규연";

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