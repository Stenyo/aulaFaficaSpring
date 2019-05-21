package com.aula.blog.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.aula.blog.model.Post;
import com.aula.blog.service.PostService;

@Controller
public class PostController {
	
	@Autowired
	private PostService service;
	
	
	 @RequestMapping(value = "/list", method = RequestMethod.GET)
	    public ModelAndView listPost(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
	        final int currentPage = page.orElse(1);
	        final int pageSize = size.orElse(5);

	        Page<Post> postPage = service.findPaginated(PageRequest.of(currentPage - 1, pageSize));
	        ModelAndView mv = new ModelAndView("/post");
	        mv.addObject("postPage", postPage);

	        int totalPages = postPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            mv.addObject("pageNumbers", pageNumbers);
	        }

	        return mv;
	    }
	@GetMapping("/add")
	public ModelAndView add(Post post) {
	
		ModelAndView mv = new ModelAndView("/postAdd");
		mv.addObject("post", post);
		mv.addObject("stringTeste", "asd");
		
		return mv;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		
		return add(service.findOne(id));
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		
		service.delete(id);
		return new ModelAndView("redirect:/list");
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Post post, BindingResult result) {
	
		if(result.hasErrors()) {
			return add(post);
		}
		
		service.save(post);
		
		
		return new ModelAndView("redirect:/list");
	}
	
}