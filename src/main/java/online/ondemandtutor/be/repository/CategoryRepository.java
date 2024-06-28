package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoryByIsDeletedFalse();
    Category findCategoryByIdAndIsDeletedFalse(long idCategory);
}
