package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoryByIsDeletedFalse();
    Category findCategoryByIdAndIsDeletedFalse(long idCategory);
}
