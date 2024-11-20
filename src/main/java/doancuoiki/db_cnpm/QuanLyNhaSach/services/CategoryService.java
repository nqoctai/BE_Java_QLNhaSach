package doancuoiki.db_cnpm.QuanLyNhaSach.services;

import java.util.ArrayList;
import java.util.List;

import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ViewDB.View1DTO;
import org.springframework.stereotype.Service;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Category;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<View1DTO> getView1Data() {
        List<Object[]> rawData = categoryRepository.getView1();
        List<View1DTO> result = new ArrayList<>();

        for (Object[] row : rawData) {
            String theLoai = (String) row[0]; // Cột "TheLoai"
            Long soLuongBan = ((Number) row[1]).longValue(); // Cột "SoLuongBan"
            result.add(new View1DTO(theLoai, soLuongBan));
        }

        return result;
    }
}
