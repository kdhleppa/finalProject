const boardTypeCheck = document.getElementById("boardTypeCheck").value;

const insertBtn = document.querySelector("#insertBtn");

// 글쓰기 버튼 클릭 시
if(insertBtn != null) { // 로그인 여부에 따라 insertBtn에 있는가 없는가에 대한 예외 처리
	insertBtn.addEventListener("click", () => {
		
		if(memberType != 'M' && location.pathname.split("/")[2] == 'N') {
			alert("공지사항은 관리자만 작성 가능합니다.")
			return;
		}
		
		location.href = `/board2/${location.pathname.split("/")[2]}/insert`;

	});
}

const boardSort = document.querySelectorAll('input[name="boardSort"]');

function createTableRow(board) {
    return `
        <tr id="tableTr">
            <td>${board.boardNo}</td>
            <td id="tdTitle">
                ${board.boardTitle}
            </td>
            <td>${board.memberNickname}</td>
            <td>${board.boardCreateDate}</td>
            <td><i class="fa-solid fa-eye"></i> ${board.boardReadCount}</td>
            <td><i id="boardLike" class="fa-solid fa-heart"></i> ${board.likeCount}</td>
        </tr>
    `;
}


function updateTable(data) {
    const tableBody = document.querySelector('.listTable tbody');
    
    tableBody.innerHTML = '';

    data.forEach(board => {
        const rowHtml = createTableRow(board);
        tableBody.insertAdjacentHTML('beforeend', rowHtml);
    });
}


for (let i = 0; i < boardSort.length; i++) {
    boardSort[i].addEventListener('change', (e) => {
        
		let tableTr = document.querySelectorAll('#tableTr');

        if (e.target.value == 'readCount') {
            for (let i = 0; i < boardSort.length; i++) {
                boardSort[i].classList.remove('clicked');
            }

            e.target.classList.add('clicked');

            fetch('/board/orderReadCount?boardType=' + boardTypeCheck)
            .then((resp) => resp.json())
            .then((data) => {
				
                updateTable(data);
                
            });
            
        }

        if(e.target.value == 'likeCount') {
			
			for(var i = 0; i < boardSort.length; i++) {
				boardSort[i].classList.remove('clicked')
			}
			
			e.target.classList.add('clicked')
			
			fetch("/board/orderLikeCount?boardType=" + boardTypeCheck)
			.then(resp => resp.json())
			.then(data => {
				
				updateTable(data);
				
			})
		}
		
    });
    
}


