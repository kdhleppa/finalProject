const classification = document.getElementById('classification');
const contentSecCenterSec = document.querySelector('.contentSecCenterSec');

// 캠핑장 정렬 
classification.addEventListener('change', (e) => {
	
	contentSecCenterSec.innerHTML = "";

	fetch("/item/order?category=" + e.target.value)
	.then(resp => resp.json())
	.then(data => {

		for(var item of data){

			const contentDetailSec = document.createElement('section');
			contentDetailSec.classList.add('contentDetailSec');

			const contentImgeSec = document.createElement('contentImgeSec');
			contentImgeSec.classList.add('contentImgeSec');

			const aTag = document.createElement('a');
			aTag.setAttribute("src", `/item/itemDetail/${item.itemNo}`)

			const contentImg = document.createElement('img');
			contentImg.classList.add('contentImg');
			contentImg.setAttribute("src", item.thumbnail);

			aTag.append(contentImg);

			contentImgeSec.append(aTag);


			const contentInfoSec = document.createElement('section');
			contentInfoSec.classList.add('contentInfoSec');

			const contentNameA = document.createElement('a')
			contentNameA.classList.add('contentNameA');
			contentNameA.setAttribute("src", `/item/itemDetail/${item.itemNo}`)

			const contentTitle = document.createElement('section');
			contentTitle.classList.add('contentInfo');
			contentTitle.setAttribute("id", "contentTitle");
			contentTitle.innerText = item.itemName;

			contentNameA.append(contentTitle)

			const contentComment = document.createElement('section');
			contentComment.classList.add('contentInfo');
			contentComment.setAttribute("id", "contentComment");
			contentComment.innerText = item.itemDescription

			const contentInfo = document.createElement('section');
			contentInfo.classList.add('contentInfo');

			if(item.discountRate != 0){
				const del = document.createElement('del');
				del.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

				const discountRate = document.createElement('section');
				discountRate.classList.add('contentInfo');
				discountRate.setAttribute("id", "discountRate")
				discountRate.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

				const ratio = document.createElement('p')
				ratio.style.color = 'red';
				ratio.innerText = `(${item.discountRate}%↓)`

				contentInfo.append(del)

				discountRate.append(ratio);

				contentInfoSec.append(contentNameA, contentComment, contentInfo, discountRate)

				contentDetailSec.append(contentImgeSec, contentInfoSec)

			} else {
				const pTag = document.createElement('p');
				pTag.innerText = "￦ " +item.itemPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

				contentInfo.append(pTag);

				contentInfoSec.append(contentNameA, contentComment, contentInfo)

				contentDetailSec.append(contentImgeSec, contentInfoSec)
			}


			contentSecCenterSec.append(contentDetailSec)

		}


		
		})	
	
})
