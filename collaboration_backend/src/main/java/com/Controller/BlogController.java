package com.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Dao.BlogDao;
import com.Model.Blog;

@RestController
public class BlogController {
	
	private static final Logger log=LoggerFactory.getLogger(BlogController.class);
	
	@Autowired
	private BlogDao blogDao;
	
	
	@Autowired
	private Blog blog;
	
	@RequestMapping(value = "/blogs", method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> listAllBlogs() {
		log.debug("-->CALLING METHOD LIST ALL BLOG");
		List<Blog> blog = blogDao.list();
		
		if (blog.isEmpty()) {
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Blog>>(blog, HttpStatus.OK);

	}

	@RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
	public ResponseEntity<Blog> getuser(@PathVariable("id") String id) {
		log.debug("CALLING GET BLOG BY IDss");
		Blog blog = blogDao.get(id);
		if (blog == null) {
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog not found");
			return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value = "/createblogs/", method = RequestMethod.POST)
	public ResponseEntity<Blog> createblogs(@RequestBody Blog blog, HttpSession session) {
		log.debug("-->CALLING METHOD CREATE BLOG");
		String loggedInUserid = (String) session.getAttribute("loggedInUserId");
		blog.setUserid(loggedInUserid);
		blog.setStatus('N');
		blogDao.save(blog);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value = "/blog/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Blog> deleteblog(@PathVariable("id") String id) {
		log.debug("-->CALLING BLOG DELETE METHOD");
		Blog blog = blogDao.get(id);
		if (blog == null) {
			log.debug("-->BLOG DOES NOT EXISTS");
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog not found");
			return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
		}
		blogDao.delete(id);
		log.debug("-->USER DELETED SUCCESSFULLY");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

}


	

