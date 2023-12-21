
let container = document.getElementById("container")
let count = document.querySelectorAll(".objectSection").length;

function confirm(paymentNo){

    container.innerHTML = "";

    fetch("/member/confirmPay?paymentNo=" + paymentNo)
    .then(resp => resp.json())
    .then(data => {

        if(count > data.length){
            alert('승인 완료')
            count = data.length
        }else {
            alert('승인 실패')
        }

        for(info of data){

            const objectSection = document.createElement('section');
            objectSection.setAttribute("class", "objectSection");

            const empty3_1 = document.createElement('section');
            empty3_1.classList.add('empty3');
            const empty3_2 = document.createElement('section');
            empty3_2.classList.add('empty3');

            const empty4_1 = document.createElement('section');
            empty4_1.classList.add('empty4');
            const empty4_2 = document.createElement('section');
            empty4_2.classList.add('empty4');
            const empty4_3 = document.createElement('section');
            empty4_3.classList.add('empty4');
            const empty4_4 = document.createElement('section');
            empty4_4.classList.add('empty4');

            const objectListSection = document.createElement('section');
            objectListSection.classList.add('objectListSection');

            const objectList = document.createElement('section');
            objectList.setAttribute('id', 'objectList');

            const reservationInfo1 = document.createElement('section');
            reservationInfo1.classList.add('reservationInfo');
            const icon1 = document.createElement('img')
            icon1.classList.add('icon')
            icon1.setAttribute('src', '/images/memberImg/carbon_map.png')
            const text1 = document.createElement('p')
            text1.classList.add('info')
            text1.innerText = ` ${info.CAMP_NAME} ( ${info.CAMP_DE_NAME} )`

            const reservationInfo2 = document.createElement('section');
            reservationInfo2.classList.add('reservationInfo');
            const icon2 = document.createElement('img')
            icon2.classList.add('icon')
            icon2.setAttribute('src', '/images/memberImg/Group.png')
            const text2 = document.createElement('p')
            text2.classList.add('info')
            text2.innerHTML = `Date &nbsp; :&nbsp;  ${info.CHECKIN_DATE} ~ ${info.CHECKOUT_DATE}`

            const reservationInfo3 = document.createElement('section');
            reservationInfo3.classList.add('reservationInfo');
            const icon3 = document.createElement('img')
            icon3.classList.add('icon')
            icon3.setAttribute('src', '/images/memberImg/tdesign_member.png')
            const text3 = document.createElement('p')
            text3.classList.add('info')
            text3.innerText = `${info.SENDER_NAME}`

            const reservationInfo4 = document.createElement('section');
            reservationInfo4.classList.add('reservationInfo');
            const icon4 = document.createElement('img')
            icon4.classList.add('icon')
            icon4.setAttribute('src', '/images/memberImg/mingcute_bank-card-line.png')
            const text4 = document.createElement('p')
            text4.classList.add('info')
            text4.innerText = `요청계좌 : ${info.BANK_ACCOUNT}`

            reservationInfo1.append(icon1, empty4_1, text1);
            reservationInfo2.append(icon2, empty4_2, text2);
            reservationInfo3.append(icon3, empty4_3, text3);
            reservationInfo4.append(icon4, empty4_4, text4);

            const hr = document.createElement('hr');

            objectList.append(reservationInfo1, reservationInfo2, reservationInfo3, reservationInfo4, hr)

            const backBtn = document.createElement('div');
            backBtn.setAttribute('id', 'backBtn');

            const clickBtn = document.createElement('button');
            clickBtn.classList.add('clickBtn');
            clickBtn.setAttribute('type', 'button');
            clickBtn.setAttribute('onclick', `confirm(${info.PAYMENT_NO})`)
            clickBtn.innerText = '입금 확인'

            backBtn.append(clickBtn);

            objectListSection.append(objectList, empty3_1, backBtn);

            objectSection.append(empty3_2, objectListSection);

            container.append(objectSection);
        }


    })


}