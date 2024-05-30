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

    public List<Category> findAll() {
        return categoryRepository.findAccountByIsDeletedFalse();
    }

    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setSubject(categoryRequest.getSubject());
        category.setNumber(categoryRequest.getNumber());

        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }

    public Category updateCategory(CategoryRequest categoryRequest, long id) {
        Category category = categoryRepository.findAccountByIdAndIsDeletedFalse(id);
        category.setSubject(categoryRequest.getSubject());
        category.setNumber(categoryRequest.getNumber());

        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }

    public Category changeCategoryStatus(long id) {
        Category category = categoryRepository.findAccountByIdAndIsDeletedFalse(id);
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
