package com.myblog.blogapp.controller;

import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.service.PostService;
import com.myblog.blogapp.utils.AppConstant;
import com.sun.deploy.association.utility.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "PageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER,required=false) int pageNo,
            @RequestParam(value = "PageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,@PathVariable("id") long id){
        PostDto dto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("post entity delete successfully",HttpStatus.OK);
    }


}
