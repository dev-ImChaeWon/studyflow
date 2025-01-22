package com.studyflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.BillManagementDTO;
import com.studyflow.entity.BillManagement;
import com.studyflow.repository.BillManagementRepository;

import jakarta.transaction.Transactional;

@Service
public class BillManagementService {

	BillManagementRepository bilr;

	@Autowired
	public BillManagementService(BillManagementRepository bilr) {
		this.bilr = bilr;
	}

	// 학생-과목 id로 수납 정보 가져오는 메서드
    public BillManagementDTO getBillManagementById(Integer id) {
        Optional<BillManagement> billManagement = bilr.findById(id);
        if (billManagement.isPresent()) {
            return convertToDTO(billManagement.get());
        } else {
            // 예외 처리나 null 반환을 할 수 있습니다. 예시로 null을 반환.
            return null;
        }
    }

    // BillManagement -> BillManagementDTO
    private BillManagementDTO convertToDTO(BillManagement billManagement) {
        BillManagementDTO dto = new BillManagementDTO();
        dto.setBillId(billManagement.getBillId());
        dto.setPay(billManagement.getPay());
        dto.setIsPay(billManagement.getIsPay());
        dto.setPayDate(billManagement.getPayDate());
        return dto;
    }

	// 모든 수납 정보 가져오는 메서드
	public List<BillManagementDTO> getBillManagement() {
		List<BillManagement> billList = bilr.findAll();
		List<BillManagementDTO> billDTOList = new ArrayList<>();

		for (BillManagement bm : billList) {
			BillManagementDTO billDTO = new BillManagementDTO();
			billDTO.setBillId(bm.getBillId());
			billDTO.setPayDate(bm.getPayDate());
			billDTO.setIsPay(bm.getIsPay());
			billDTO.setPay(bm.getPay());
			billDTOList.add(billDTO);
		}

		return billDTOList;
	}

	// 수납 정보 생성 및 수정 메서드
	public BillManagementDTO updateBillManagement(BillManagementDTO billDTO) {
		Optional<BillManagement> optionalBillManagement = bilr.findById(billDTO.getBillId());
		BillManagement billManagement;

		if (optionalBillManagement.isPresent()) {
			billManagement = optionalBillManagement.get();
			billManagement.setIsPay(billDTO.getIsPay());
			billManagement.setPayDate(billDTO.getPayDate());
			if (billDTO.getPay() == 1) {
				billManagement.setPay(billManagement.getPay());
			} else {
				billManagement.setPay(billDTO.getPay());
			}
		} else {
			billManagement = new BillManagement();
			Date now = new Date();
			billManagement.setBillId(billDTO.getBillId());
			billManagement.setIsPay(billDTO.getIsPay());
			billManagement.setPay(billDTO.getPay());
			billManagement.setPayDate(now);
		}

		bilr.save(billManagement);

		return billDTO;

	}

}
