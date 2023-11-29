const payBy = document.querySelectorAll('input[name="payBy"]');

for(var i = 0 ; i <payBy.length ; i ++){

    payBy[i].addEventListener("change", (e) => {
        
        const bankAccountContainer = document.getElementById('bankAccountContainer');
    
        if(e.target.value == 'b'){
            makeAccount();
        } else{
            bankAccountContainer.innerHTML = "";
        }
    })
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


const payFrm = document.getElementById('payFrm')
const payBtn = document.getElementById('payBtn')

payFrm.addEventListener("submit", () => {
    const payBy = document.querySelector('input[name="payBy"]:checked').value;

    pay(payBy)

    function pay(payBy, event){

        if(payBy == 'k'){

            console.log("카카오")

        }


        if(payBy == 'n'){

            console.log("네이버")
            
        }
        
        
        if(payBy == 'b'){
            
            const senderName = document.getElementById('senderName');

            if(senderName.value == ""){
                alert("이름을 입력해주세요")
                event.preventDefault();
                return;
            }

        }

    }


})
