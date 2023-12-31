document.addEventListener('DOMContentLoaded', function () {
    // 모든 '+' 버튼에 대한 이벤트 리스너 추가
    document.querySelectorAll('[id^="plusBtn"]').forEach(function (plusBtn) {
        plusBtn.addEventListener('click', function () {
            var index = this.id.replace('plusBtn', '');
            updateQuantityAndCart(index, 1);
            
        });
    });

    // 모든 '-' 버튼에 대한 이벤트 리스너 추가
    document.querySelectorAll('[id^="minusBtn"]').forEach(function (minusBtn) {
        minusBtn.addEventListener('click', function () {
            var index = this.id.replace('minusBtn', '');
            updateQuantityAndCart(index, -1);
        });
    });
    
    var selectAllCheckboxes = document.querySelectorAll('.selectAllCheckbox');
    selectAllCheckboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            // 해당 체크박스가 속한 테이블 내의 모든 개별 체크박스를 찾습니다.
            var table = checkbox.closest('table');
            var individualCheckboxes = table.querySelectorAll('.individualCheckbox');
            
            // 각 개별 체크박스의 상태를 전체 선택 체크박스의 상태와 일치시킵니다.
            individualCheckboxes.forEach(function (indCheckbox) {
                indCheckbox.checked = checkbox.checked;
            });
        });
    });
    
    document.querySelectorAll('.wishToCartBtn').forEach(function(button) {
        button.addEventListener('click', function() {
            var formId = button.getAttribute('data-form-id');
            var selectId = button.getAttribute('data-select-id');
      		var form = document.getElementById(formId);
      		var selectElement = document.getElementById(selectId);
            console.log('Form:', form);
            console.log('Select Element:', selectElement);
           
            

            if (selectElement) {
	            var selectedValue = selectElement.value;
	            if (selectedValue === 'default') {
	                alert('추가하려는 캠핑장을 선택해 주세요.');
	            } else {
	                form.submit();
	            }
	        } else {
	            console.log('No select element found');
	        }
	    });
	});
	
	
	document.querySelectorAll('.wishDeleteBtn').forEach(function(button) {
        button.addEventListener('click', function() {
            var formId = button.getAttribute('data-form-id');
      		var form = document.getElementById(formId);
            var newAction = '/paysys/wishDelete';
      		form.setAttribute('action', newAction);
            

            
	        form.submit();
	      
	    });
	});
    
    
    
    
});





window.onload = function() {
    // "index-input" 클래스를 가진 모든 요소를 선택
    var inputs = document.querySelectorAll('input.index-input');


    // 각 input 요소에 대해 반복
    inputs.forEach(function(input) {
        var index = input.value;
        updateTotal(index);
    });
    
   
    
    var rsvInfos = document.querySelectorAll('[id^="rsvInfo"]');
	var payForm = document.getElementById('payForm');
    var allRsvInfosRemoved = true;
    
    rsvInfos.forEach(function(rsvInfo) {
        var rsvInfoIndex = rsvInfo.id.replace('rsvInfo', '');
        var cartItemRows = document.querySelectorAll('tr[id="cartItemTr' + rsvInfoIndex + '"]');

        if (cartItemRows.length === 0) {
            rsvInfo.remove();
        } else {
            allRsvInfosRemoved = false;
        }
    });
   if (allRsvInfosRemoved) {
        var noItemsTable = document.createElement('table');
        noItemsTable.className = 'shoppingCartTB';
        noItemsTable.innerHTML = `
            <tr>
                <th></th>
                <th></th>
                <th>상품명</th>
                <th>캠핑장 선택</th>
                <th>수량</th>
                <th>금액</th>
                <th>취소</th>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td>등록된 상품이 없습니다.</td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
			    <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
        	</tr>
        `;
        payForm.insertBefore(noItemsTable, payForm.firstChild);
    }
    
};





document.getElementById('submitBtn').addEventListener('click', function() {
    var checkboxes = document.querySelectorAll('#payForm input[type=checkbox]');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = true;
    }

    // 폼 제출
    document.getElementById('payForm').submit();

});

function updateQuantityAndCart(index, change) {
    var quantityDisplay = document.getElementById('quantityDisplay' + index);
    var cartItemNo = document.getElementById('cartItemNo' + index).value
    var currentQuantity = parseInt(quantityDisplay.textContent);
    var newQuantity = Math.max(1, currentQuantity + change);
    quantityDisplay.textContent = newQuantity;

    // Fetch API를 사용하여 서버에 변경사항 전송
    fetch('/paysys/quantityUpdateCart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ cartItemNo: cartItemNo, itemQuantity: newQuantity })
    })
    .then(response => response.json())
    .then(data => {
        if (data && data.message === "성공") {
        document.getElementById('quantityDisplay' + index).textContent = newQuantity;
        updateTotal(index);
        } else {
			console.log("에러 삭제실패")
		}
    })
    .catch(error => {
        // 오류 처리
        console.error('장바구니 업데이트 실패:', error);
    });
}



function updateTotal(index) {
    var itemPrice = parseFloat(document.getElementById('itemPrice'+index).value);
    var quantity = parseInt(document.getElementById('quantityDisplay'+index).textContent);
    var discountRate = parseFloat(document.getElementById('discountRate'+index).value);
    var totalPrice = itemPrice * quantity;
    
    if (discountRate === 0) {
        document.getElementById('totalPriceContainer' + index).querySelector('p').innerText = '₩' + totalPrice.toLocaleString();
    } else {
        var discountedItemPrice = itemPrice * (1 - (discountRate / 100));
        var totalDiscountedPrice = discountedItemPrice * quantity;
        document.getElementById('totalPriceContainer' + index).innerHTML =
            '<del>₩' + totalPrice.toLocaleString() + '</del><br>' +
            '<p style="color: red;">(' + discountRate + '%↓)</p>' +
            '₩' + totalDiscountedPrice.toLocaleString();
    }
}	
	
	
	


function moveItem(button, cartItemIndex) {
    var form = button.closest('form'); // 버튼이 있는 form을 찾습니다.
    if (!form) {
        console.error('Form not found');
        return; // 폼이 없으면 함수를 종료합니다.
        }
    var trElement = button.closest('tr'); // 버튼이 있는 행(tr)을 찾습니다.


	 
    // 현재 행의 캠핑장 예약 번호와 cartItemNo 값을 추출합니다.
    var selectedReservationNo = trElement.querySelector('select[name="reservationNo' + cartItemIndex + '"]').value;
    if (selectedReservationNo === 'default') {
	        alert('캠핑장을 선택해주세요.');
	        return;
	    }
    
    var cartItemNo = form.querySelector('input[id="cartItemNo' + cartItemIndex + '"]').value;
	var itemNo = form.querySelector('input[id="itemNo' + cartItemIndex + '"]').value;
    // 선택된 캠핑장의 예약 번호와 cartItemNo를 hidden input으로 form에 추가합니다.
    addHiddenInput(form, 'reservationNo', selectedReservationNo);
    addHiddenInput(form, 'cartItemNo', cartItemNo);
    addHiddenInput(form, 'itemNo', itemNo);
    form.action = '/paysys/rentCart/moveItemToOtherSite';

    form.submit(); // form을 제출합니다.
}

function moveWish(button, rsvInfoIndex, cartItemIndex) {
	var form = button.closest('form'); 
    var itemNo = form.querySelector('input[id="itemNo' + cartItemIndex + '"]').value;
    var cartItemNo = form.querySelector('input[id="cartItemNo' + cartItemIndex + '"]').value;
    var reservationNo = form.querySelector('input[id="currentReservationNo' + rsvInfoIndex + '"]').value;
    
    addHiddenInput(form, 'itemNo', itemNo);
    addHiddenInput(form, 'reservationNo', reservationNo);
    addHiddenInput(form, 'cartItemNo', cartItemNo);
    form.action = '/member/wishlist/insert2';
    
    form.submit();
    
    
     
}

function deleteCart(button, rsvInfoIndex, cartItemIndex) {
	var form = button.closest('form'); 
	var cartItemNo = form.querySelector('input[id="cartItemNo' + cartItemIndex + '"]').value;
	addHiddenInput(form, 'cartItemNo', cartItemNo);
	 form.action = '/paysys/rentCart/deleteCart';
    
    form.submit();
}




function deleteAllCart(){
	if (confirm('장바구니를 비우시겠습니까?')) {
		
		var form = this.closest('form');
			
		form.action = '/paysys/rentCart/deleteAllCart'
		form.submit();
	} else {
		
	}
}



//한번만 쓰고 버릴 히든창 생성
function addHiddenInput(form, name, value) {
    var input = document.createElement('input');
    input.setAttribute('type', 'hidden');
    input.setAttribute('name', name);
    input.setAttribute('value', value);
    form.appendChild(input);
}



