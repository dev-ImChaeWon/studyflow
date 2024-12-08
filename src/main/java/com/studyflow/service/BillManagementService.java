package com.studyflow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.BillManagementDTO;
import com.studyflow.entity.BillId;
import com.studyflow.entity.BillManagement;
import com.studyflow.entity.StudentSubject;
import com.studyflow.repository.BillManagementRepository;

import jakarta.transaction.Transactional;

@Service
public class BillManagementService {

	BillManagementRepository bilr;

	@Autowired
	public BillManagementService(BillManagementRepository bilr) {
		this.bilr = bilr;
	}

	// 수납 정보 가져오는 메서드
	public List<BillManagementDTO> getBillManagement() {
		List<BillManagement> billList = bilr.findAll();
		List<BillManagementDTO> billDTOList = new ArrayList<>();

		for (BillManagement bm : billList) {
			BillManagementDTO billDTO = new BillManagementDTO();
			billDTO.setBillId(bm.getId().getBillId());
			billDTO.setPayDate(bm.getPayDate());
			billDTO.setIsPay(bm.getIsPay());
			billDTO.setPay(bm.getPay());
			billDTOList.add(billDTO);
		}

		return billDTOList;
	}

	// 수납 정보 생성 및 수정 메서드
	@Transactional
	public BillManagementDTO updateBillManagement(BillManagementDTO billDTO) {
		BillId billId = new BillId();
		billId.setBillId(billDTO.getBillId());
		Optional<BillManagement> optionalBillManagement = bilr.findById(billId);
		BillManagement billManagement;

		if (optionalBillManagement.isPresent()) {
			billManagement = optionalBillManagement.get();
			billManagement.setIsPay(billDTO.getIsPay());
			billManagement.setPay(billDTO.getPay());
			billManagement.setPayDate(billDTO.getPayDate());
		} else {
			billManagement = new BillManagement();
			billManagement.setId(billId);
			billManagement.setIsPay(billDTO.getIsPay());
			billManagement.setPay(billDTO.getPay());
			billManagement.setPayDate(billDTO.getPayDate());
		}

		bilr.save(billManagement);

		return billDTO;

	}

}
