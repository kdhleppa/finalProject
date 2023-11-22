//휴대폰번호 인증번호 보내기 버튼 클릭 이벤트
$('#checkTel').click(function () {
    var to = $('#inputTel').val();

	// 확인 버튼 클릭 이벤트
    $('#checkTel').click(function () {
		
        $('#telMessage').text('인증번호가 발송되었습니다.');
        
    });

    $.ajax({
        url: "/memberTelCheck",
        type: "POST",
        data: "to=" + to,
        dataType: "json",
        success: function (data) {
			
            const checkNum = data;
            alert('checkNum:' + checkNum);


            // 인증하기 버튼 클릭 이벤트
            $('#checkTelAuthkey').click(function () {
				
                const userNum = $('#inputAuthkey').val();
                if (checkNum == userNum) {
					
                    $('#authkeyMessage').text('인증 성공하였습니다.');
                    
                } else {
					
                    $('#authkeyMessage').text('인증 실패하였습니다. 다시 입력해주세요.');
                
                }
            });
        },
        
        error: function () {
			
            alert("에러");
        
        }
        
    });
});
