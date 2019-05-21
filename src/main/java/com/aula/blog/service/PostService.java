package com.aula.blog.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.aula.blog.model.Post;
import com.aula.blog.repository.PostRepository;

import javassist.expr.NewArray;


@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;
	
	private List<Post> posts = new ArrayList<Post>();
	
	public List<Post> findAll() {
		return repository.findAll();
	}
	
	 public Page<Post> findPaginated(Pageable pageable) {
	        int pageSize = pageable.getPageSize();
	        int currentPage = pageable.getPageNumber();
	        int startItem = currentPage * pageSize;
	        List<Post> list;
	        posts = this.findAll();
	 
	        if (posts.size() < startItem) {
	            list = Collections.emptyList();
	        } else {
	            int toIndex = Math.min(startItem + pageSize, posts.size());
	            list = posts.subList(startItem, toIndex);
	        }
	 
	        Page<Post> postPage
	          = new PageImpl<Post>(list, PageRequest.of(currentPage, pageSize, Sort.by("id").descending()), posts.size());
	 
	        return postPage;
	    }
	 
	public Post findOne(Long id) {
		Post post = repository.findById(id).get();
		return post;
	}
	
	public Post save(Post post) {
		return repository.saveAndFlush(post);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
