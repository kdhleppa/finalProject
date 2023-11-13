
const loginFrm = document.getElementById("loginFrm");

const memberEmail = document.querySelector("#loginFrm input[name='memberEmail']");
const memberPw = document.querySelector("#loginFrm input[name='memberPw']");

if(loginFrm != null){
    // 로그인 시도를 할 때
    loginFrm.addEventListener("submit", e => {

        // 이메일이 입력되지 않은 경우
        // 문자열.trim() : 문자열 좌우 공백 제거
        if(memberEmail.value.trim().length == 0){
            alert("이메일을 입력해주세요.");

            memberEmail.value = ""; // 잘못 입력된 값(공백) 제거
            memberEmail.focus(); // 이메일 input태그에 초점을 맞춤

            e.preventDefault(); // (기본이벤트 제거 : 제출 못하게하기)
            return; 
        }


        // 비밀번호가 입력되지 않은 경우
        if(memberPw.value.trim().length == 0){
            alert("비밀번호를 입력해주세요.");

            memberPw.value = ""; // 잘못 입력된 값(공백) 제거
            memberPw.focus(); // 이메일 input태그에 초점을 맞춤

            e.preventDefault(); // 제출 못하게하기
            return; 
        }


    });
}

//-----------------

// fetch API : 웹 브라우저에서 서버로 HTTP 요청을 하게해주는 최신 인터페이스
/**
 * 
 * fetch(url)
 * .then(response => response.json() / response.text())
 * .then(data => console.log(data))   
 * .catch(error => console.log(error));
 * 
 * 첫 번째 then() 함수는 서버 요청에 대한 응답이 왔을때 실행됨
 * - 응답받은 데이터가 반환되는 값이 단순 자료형 1개면 text().
 * 객체(Map)면 json()으로 파싱한 후 다음 then() 함수로 넘겨준다.
 * 
 * 두번째 then() 함수는 response.json()/text()으로 상황에 맞게
 * 데이터가 파싱 완료되면 실행.
 * 파싱된 데이터가 전달되며, 이 값을 로직에 맞게 가공한다.
 * 
 * 
 */

// 닉네임이 일치하는 회원의 전화번호 조회
const inputNickname = document.getElementById("inputNickname");
const btn1 = document.getElementById("btn1");
const result1 = document.getElementById("result1");

btn1.addEventListener("click", ()=> {

    // fetch API를 이용해서 ajax
    // GET방식 요청 (파라미터를 쿼리스트링으로 추가)

    // Promise : 비동기 함수 호출 또는 연산이 완료되었을 때
    //           이후에 처리할 함수나 에러를 처리하기 위한
    //          함수를 설정하는 모듈
    //          -> 비동기 연산의 최종 결과 객체

    fetch("/selectMemberTel?nickname=" + inputNickname.value)
    .then(resp => resp.text()) // 응답 객체(자료형 하나일때)를 문자열 형식으로 파싱
    .then(data => {
        // 데이터 가공
        console.log(data);
        result1.innerText = data;
    })
    .catch(err => console.log(err));

})

// fetch() API 를 이용한 POST 방식 요청

// 이메일을 입력받아 일치하는 회원의 정보를 조회
const inputEmail = document.getElementById("inputEmail");
const btn2 = document.getElementById("btn2");
const result2 = document.getElementById("result2");

btn2.addEventListener("click", ()=>{
    
    // JSON.stringify() : JS 객체 -> JSON
    // JSON.parse()     : JSON -> JS 객체

    // JSON : Javascript 객체 문법으로, 구조화된 데이터를 표현하기 위한
    //          문자 기반의 표준 포맷이다.
    //          서버에서 클라이언트로 데이터를 전송하여 표현하거나,
    //          그 반대의 경우에 사용한다.

    // POST 방식
    fetch("/selectMember", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
                    // 요청 보내는 자원을 명시
                    // -> js 객체를 json 형식으로 만들어 파라미터로 전달
        body : JSON.stringify({"email" : inputEmail.value})
    })
    .then(resp => resp.json()) // 응답 객체를 자바스크립트 객체 형태로
                                // 파싱하는것
    .then(member => {
        console.log(member);
    })
    .catch(err => console.log(err));
});


//---------------------------------------------

// 웹소켓 테스트
// 1. SockJS 라이브러리 추가

// 2. SockJS를 이용해서 클라이언트용 웹소켓 객체 생성
/*
let testSock = new SockJS("/testSock");

function sendMessage(name, str) {

    // 매게변수를 JS 객체에 저장
    let obj = {}; // 비어있는 객체

    obj.name = name; // 객체에 일치하는 key가  있는지
    obj.str = str;

    // console.log(obj);

    testSock.send(JSON.stringify(obj));// JS객체 -> JSON
     


}

// 웹소켓 객체(testSock)가 서버로 부터 전달받은 메세지가 있는 경우
testSock.onmessage = e => {

    // e : 이벤트 객체
    // e.data : 전달받은 메세지 (JSON)

    let obj = JSON.parse(e.data) // JSON -> JS객체

    console.log(`보낸사람 : ${obj.name} / ${obj.str}`);

}
*/

// 자바스크립트로 쿠키 얻어오기
function getCookie(key) {
	
	// savedId=user01@kh.or.kr; test=가나다; aaa=100
	const cookies = document.cookie;
	const cookieList = cookies.split("; ").map(cookie => cookie.split("="));
	// 쿠키배열을 쪼개기
	
	// 배열.map() : 배열의 모든 요소를 순차접근하여 특정 함수 수행 후
	//				수행 결과를 이용해서 새로운 배열을 만드는 함수
	
	// [['savedId','user01@kh.or.kr'], ['test', '가나다'],['aaa', '100']]
	
	console.log(cookies);
	const obj = {};
	
	for(let i = 0; i<cookieList.length; i++) {
		obj[cookieList[i][0]] = cookieList[i][1];
		
	}
	return obj[key]; 
}

// 쿠키에 saveId가 있을 경우
if(document.querySelector("input[name='memberEmail']") != null) {
	// 화면에 memberEmail 입력박스가 있을 경우
	
	const saveId = getCookie("saveId");
	
	// 있으면 이메일값, 없으면 undefined
	if(saveId != undefined) {
		document.querySelector("input[name='memberEmail']").value = saveId;
		document.querySelector("input[name='saveId']").checked = true;
	}
}