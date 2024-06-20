package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Category;
import online.ondemandtutor.be.model.CategoryRequest;
import online.ondemandtutor.be.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    //CRUD: read
    public List<Category> findAll() {
        return categoryRepository.findCategoryByIsDeletedFalse();
    }

    //CRUD: create
    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setSubjectLevel(categoryRequest.getSubjectLevel());
        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }

    //CRUD: update
    public Category updateCategory(CategoryRequest categoryRequest, long id) {
        Category category = categoryRepository.findCategoryByIdAndIsDeletedFalse(id);
        category.setSubjectLevel(categoryRequest.getSubjectLevel());

        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }

    //CRUD: delete
    public Category changeCategoryStatus(long id) {
        Category category = categoryRepository.findCategoryByIdAndIsDeletedFalse(id);
        if (category != null) {
            category.setDeleted(true);
            Category newStatus = categoryRepository.save(category);
            return newStatus;
        }
        else {
            return null;
        }
    }
}
