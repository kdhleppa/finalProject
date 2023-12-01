package com.camplex.project.kakao.model.vo;

import lombok.Data;

@Data
public class AmountVO {

	private Integer total, tax_free, vat, point, discount;
}
