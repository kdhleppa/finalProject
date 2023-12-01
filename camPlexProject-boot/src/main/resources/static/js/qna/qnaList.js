const qnaAnswerFrm = document.getElementById("qnaAnswerFrm");
const QNAAnswer = document.getElementById("QNAAnswer");

	qnaAnswerFrm.addEventListener('submit', e => {
	
	if(QNAAnswer.value.trim().length == 0){
		alert("답변을 입력해주세요")
        QNAAnswer.value = "";
        QNAAnswer.focus();
        e.preventDefault();
        return;
	}
	
	alert("답변이 완료되었습니다.")
	document.querySelector("[name='insertAnswer']").value
	 = Array.from(insertAnswer);
	e.preventDefault();
	return;
});

document.addEventListener('DOMContentLoaded', function () {
    var list = /*[[${list}]]*/ []; // Thymeleaf를 통해 백엔드에서 가져온 리스트
    var currentIndex = 4; // 초기로 4개의 항목을 표시

    function showItems(startIndex, endIndex) {
        var qnaContainer = document.getElementById('formContainer');
        for (var i = startIndex; i < endIndex && i < list.length; i++) {
            var qna = list[i];

            // 여기에 각 항목을 표시하는 코드 작성 (기존 Thymeleaf 코드 그대로 사용)

            // 예시: 문의번호, 닉네임, 제목 표시
            var qnaListHead = document.createElement('div');
            qnaListHead.className = 'qnaListHead';

            var qnaNo = document.createElement('div');
            qnaNo.className = 'qnaNo';
            qnaNo.innerHTML = '<p id="QNANo" name="QNANo" class="form-control">' + qna.QNANo + '</p>';
            qnaListHead.appendChild(qnaNo);

            var memberNickname = document.createElement('div');
            memberNickname.className = 'memberNickname';
            memberNickname.innerHTML = '<p id="memberNickname" name="memberNickname" class="form-control">' + qna.memberNickname + '</p>';
            qnaListHead.appendChild(memberNickname);

            var qnaTitle = document.createElement('div');
            qnaTitle.className = 'qnaTitle';
            qnaTitle.innerHTML = '<p id="QNATitle" name="QNATitle" class="form-control">' + qna.QNATitle + '</p>';
            qnaListHead.appendChild(qnaTitle);

            qnaContainer.appendChild(qnaListHead);

            // 여기에 내용과 답변 부분 표시하는 코드 작성 (기존 Thymeleaf 코드 그대로 사용)

            // 예시: 내용 표시
            var qnaContent = document.createElement('div');
            qnaContent.className = 'qnaContent';
            qnaContent.innerHTML = '<p id="QNAContent" name="QNAContent" class="form-control">' + qna.QNAContent + '</p>';
            qnaContainer.appendChild(qnaContent);

            // 예시: 답변 폼 표시
            var qnaAnswerForm = document.createElement('form');
            qnaAnswerForm.className = 'qnaAnswer';
            // ... (답변 폼 관련 코드 작성, Thymeleaf 코드 그대로 사용)
            qnaContainer.appendChild(qnaAnswerForm);
        }
    }

    function loadMoreItems() {
        var endIndex = currentIndex + 4;
        if (endIndex > list.length) {
            endIndex = list.length;
        }
        showItems(currentIndex, endIndex);
        currentIndex = endIndex;
    }

    // 초기 4개 항목 표시
    showItems(0, currentIndex);

    // 더보기 버튼 클릭 시 추가 항목 로드
    document.getElementById('loadMoreBtn').addEventListener('click', loadMoreItems);
});