const checkObj = {
    "cmapName" : false,
    "campAdress" : false,
    "ceoName" : false,
    "businessLicense" : false,
    "tourLicenseInput" : false,
    "accountHolderInput" : false,
    "bankName" : false,
    "ceoAccount" : false
};

const cmapName = document.getElementById("cmapName");

cmapName.addEventListener("input", () => {
	
	if(cmapName.value.trim().length == 0){
        checkObj.cmapName = false;
    } else {
		checkObj.cmapName = true;
	}
    
})

const campAdress = document.getElementById("campAdress");

campAdress.addEventListener("input", () => {
	
	if(campAdress.value.trim().length == 0){
        checkObj.campAdress = false;
    } else {
		checkObj.campAdress = true;
	}
    
})

const ceoName = document.getElementById("ceoName");

ceoName.addEventListener("input", () => {
	
	if(ceoName.value.trim().length == 0){
        checkObj.ceoName = false;
    } else {
		checkObj.ceoName = true;
	}
    
})

const businessLicense = document.getElementById("businessLicense");

businessLicense.addEventListener("input", () => {
	
	if(businessLicense.value.trim().length == 0){
        checkObj.businessLicense = false;
    } else {
		checkObj.businessLicense = true;
	}
    
})

const tourLicenseInput = document.getElementById("tourLicenseInput");

tourLicenseInput.addEventListener("change", () => {
	
	if(tourLicenseInput.value.trim().length == 0){
        checkObj.tourLicenseInput = false;
    } else {
		checkObj.tourLicenseInput = true;
	}
    
})

const accountHolder = document.getElementById("accountHolderInput");

accountHolder.addEventListener("input", () => {
	
	if(accountHolder.value.trim().length == 0){
        checkObj.accountHolderInput = false;
    } else {
		checkObj.accountHolderInput = true;
	}
    
})

const bankName = document.getElementById("bankName");

bankName.addEventListener("input", () => {
	
	if(bankName.value.trim().length == 0){
        checkObj.bankName = false;
    } else {
		checkObj.bankName = true;
	}
    
})

const ceoAccount = document.getElementById("ceoAccount");

ceoAccount.addEventListener("input", () => {
	
	if(ceoAccount.value.trim().length == 0){
        checkObj.ceoAccount = false;
    } else {
		checkObj.ceoAccount = true;
	}
    
})



// 회원 가입 form태그가 제출 되었을 때
document.getElementById("levelUpFrom").addEventListener("submit", e=>{

    for(let key in checkObj){

        if(!checkObj[key]){ 

            switch(key){
            case "cmapName": 
                alert("입력 가능한 정보는 모두 필수입니다! \n캠핑장 명을 입력해주세요."); break;
                
            case "campAdress": 
				alert("입력 가능한 정보는 모두 필수입니다! \n캠핑장 주소를 입력해주세요."); break;

            case "ceoName": 
				alert("입력 가능한 정보는 모두 필수입니다! \n대표자를 입력해주세요."); break;
                
            case "businessLicense": 
				alert("입력 가능한 정보는 모두 필수입니다! \n사업자 등록 번호를 입력해주세요."); break;
                
            case "tourLicenseInput": 
				alert("입력 가능한 정보는 모두 필수입니다! \n관광사업 등록증 사본을 등록해주세요."); break;
                
            case "accountHolderInput": 
				alert("입력 가능한 정보는 모두 필수입니다! \n예금주를 입력해주세요."); break;
                
            case "bankName": 
				alert("입력 가능한 정보는 모두 필수입니다! \n은행을 입력해주세요."); break;
                
            case "ceoAccount": 
				alert("입력 가능한 정보는 모두 필수입니다! \n계좌번호를 입력해주세요."); break;
            
            }

            document.getElementById(key).focus();

            e.preventDefault();
            return;
        }
    }
});
