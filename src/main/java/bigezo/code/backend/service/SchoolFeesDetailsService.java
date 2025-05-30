package bigezo.code.backend.service;

import bigezo.code.backend.model.SchoolFeesDetails;
import bigezo.code.backend.model.SchoolFeesSetting;
import bigezo.code.backend.repository.SchoolFeesDetailsRepository;
import bigezo.code.backend.service.SchoolFeesSettingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SchoolFeesDetailsService {
    private static final Logger logger = Logger.getLogger(SchoolFeesDetailsService.class.getName());

    @Autowired
    private SchoolFeesDetailsRepository repository;

    @Autowired
    private SchoolFeesSettingService schoolFeesSettingService;

    public List<SchoolFeesDetails> getAllDetails() {
        return repository.findAll();
    }

    public Optional<SchoolFeesDetails> getDetailsById(Long id) {
        return repository.findById(id);
    }

    public SchoolFeesDetails saveDetails(SchoolFeesDetails details) {
        return repository.save(details);
    }
    public List<SchoolFeesDetails> saveDetailsList(List<SchoolFeesDetails> detailsList) {
        return repository.saveAll(detailsList);
    }

    public List<SchoolFeesDetails> getDetailsByFeesId(Long feesId) {
        return repository.findBySchoolFeesSettingId(feesId);
    }

    public void deleteDetails(Long id) {
        repository.deleteById(id);
    }

    public void deleteDetailsByFeesId(Long feesId) {
        try {
            SchoolFeesSetting setting = schoolFeesSettingService.getSettingById(feesId)
                .orElseThrow(() -> new EntityNotFoundException("School Fees Setting not found with ID: " + feesId));
            schoolFeesSettingService.deleteSetting(feesId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete school fees details for fees ID: " + feesId, e);
        }
    }
}
