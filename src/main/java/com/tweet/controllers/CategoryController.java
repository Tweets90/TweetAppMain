package com.tweet.controllers;

import com.tweet.entites.Category;
import com.tweet.payloads.ApiResponse;
import com.tweet.payloads.CategoryDto;
import com.tweet.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    //create

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
       CategoryDto createCategory= this.categoryService.createCategory(categoryDto);
       return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }


    //update
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto , @PathVariable Integer catId){
        CategoryDto updatedCategory= this.categoryService.updateCategory(categoryDto,catId);
        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
    }


    //delete
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully !!", true), HttpStatus.OK);
    }


    //get
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
        CategoryDto categoryDto1= this.categoryService.getCategory(catId);
        return new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.OK);
    }


    //getAlll

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<CategoryDto> categories= this.categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

}
