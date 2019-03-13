package com.aula.blog.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aula.blog.model.Post;
import com.aula.blog.repository.PostRepository;


@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;
	
	public List<Post> findAll() {
		return repository.findAll();
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
