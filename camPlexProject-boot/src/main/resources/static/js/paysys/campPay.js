const payBy = document.querySelectorAll('input[name="payBy"]');
let payFrm = document.getElementById('payFrm')

for(var i = 0 ; i <payBy.length ; i ++){

    payBy[i].addEventListener("change", (e) => {
        
        const bankAccountContainer = document.getElementById('bankAccountContainer');
    
        if(e.target.value == 'b'){
            makeAccount();
            payFrm.action ="payDone"

            payFrm.addEventListener("submit", (e) => {
                
                pay(e)    
				
            })

        } else if (e.target.value == 'k'){
            payFrm.action ="kakao"
            bankAccountContainer.innerHTML = "";
        } else if (e.target.value == 'n'){
            alert("현재 점검 중입니다.")
            e.target.checked = false;
        }
    })
}

const paybyRadio = document.querySelectorAll('input[name="payBy"]:checked').value;

payFrm.addEventListener("submit", e => {
	
	if(paybyRadio == null){
		alert("결제수단을 선택해주세요")
		e.preventDefault();
		return;
	}
	
})


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
    option.innerText = "국민은행 00440211111111 이재경";
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



