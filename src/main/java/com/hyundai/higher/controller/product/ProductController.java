package com.hyundai.higher.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.service.include.IncludeService;
import com.hyundai.higher.service.product.ProductService;

/**
 * @since : 2023. 03. 06.
 * @FileName: ProductController.java
 * @author : 신수진
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 06.     신수진       
 * </pre>
 */

@RequestMapping("/product")
@Controller
public class ProductController {
	
	@Autowired(required=true)
	private IncludeService iService;
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/list")
	public String productList(@RequestParam("dept1") String dept1no, @RequestParam(value="dept2", required=false, defaultValue = "") String dept2no, Model model) {
		
		
		model.addAttribute("categoryList", iService.categoryListAll());
		model.addAttribute("categoryListSub", service.categoryListSub(dept1no));
		model.addAttribute("productList", service.productList(dept1no, dept2no));
		
		return "product/list";
	}
	
}
