package com.studyflow.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.studyflow.dto.BillManagementDTO;
import com.studyflow.service.BillManagementService;

@RestController
@CrossOrigin(origins = "http://localhost:8088")
public class BillManagementController {

	BillManagementService bils;

	@Autowired
	public BillManagementController(BillManagementService bils) {
		this.bils = bils;
	}

	// 학생-과목 id로 수납 정보 가져오는 API
    @GetMapping("/api/bill-management/{id}")
    public BillManagementDTO getBillManagementById(@PathVariable Integer id) {
        return bils.getBillManagementById(id);
    }

	// 수납 관련 모든 정보 가져오는 API
	@GetMapping("/api/bill-management")
	public ResponseEntity<List<BillManagementDTO>> getBillManagement() {
		List<BillManagementDTO> res = bils.getBillManagement();

		return ResponseEntity.status(200).body(res);
	}

	// 수납 정보 생성 및 수정 API
	@PostMapping("/api/bill-management-update")
	public ResponseEntity<BillManagementDTO> updateBillManagement(@RequestBody BillManagementDTO billManagementDTO) {
		Date payDate;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = sdf.format(billManagementDTO.getPayDate());
			payDate = sdf.parse(dateString);
			billManagementDTO.setPayDate(payDate);
		} catch (ParseException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		BillManagementDTO updatedBillManagementDTO = bils.updateBillManagement(billManagementDTO);
		return ResponseEntity.status(201).body(bils.updateBillManagement(updatedBillManagementDTO));
	}

}
