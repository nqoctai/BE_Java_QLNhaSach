package doancuoiki.db_cnpm.QuanLyNhaSach.services;


import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Supplier;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSupplier(){
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id){
        return supplierRepository.findById(id).orElse(null);
    }


}
