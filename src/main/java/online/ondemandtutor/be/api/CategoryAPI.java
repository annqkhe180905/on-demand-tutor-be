package online.ondemandtutor.be.api;

import online.ondemandtutor.be.entity.Category;
import online.ondemandtutor.be.model.CategoryRequest;
import online.ondemandtutor.be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class    CategoryAPI {
    @Autowired
    CategoryService categoryService;

    @GetMapping("category")
    public ResponseEntity<List<Category>> showAll() {
        List<Category> printAll = categoryService.findAll();
        return ResponseEntity.ok(printAll);
    }

    @PostMapping("category")
    public ResponseEntity create(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(category);
    }

    @PutMapping("category/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.updateCategory(categoryRequest, id);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity changeStatus(@PathVariable long id) {
        Category category = categoryService.changeCategoryStatus(id);
        return ResponseEntity.ok(category);
    }


}
