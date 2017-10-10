package restController;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Com.collaborate.Model.BlogPost;
import Com.collaborate.Model.User;
import Com.collaborate.service.BlogPostService;
import Com.collaborate.service.UserService;

@Controller
public class BlogPostController {
	@Autowired
	public BlogPostService blogPostService;
	@Autowired
	private UserService userService;
	@RequestMapping(value="/addblogpost",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogPost(@RequestBody BlogPost blogPost, HttpSession session)
	String username=(String)session.getAttribute("username");
	if(username==null)
	{
		Error error=new Error("UnAuthorisation access..");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	blogPost.setPostedOn(new Date());
	User postedBy=userService.getUserByUsername(username);
	blogPost.setPostedBy(postedBy);
	try{
		blogPostService.addBlogpost(blogPost);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	catch(Exception e){
	Error error=new Error("Unable to saave blog post details..");
		 return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}

}

@RequestMapping(value="/getblogs/(approved)",method=RequestMethod.GET)
public ResponseEntity<?> getBlogs(@PathVariable int approved){
	String username=(String)session.getAttribute("username");
	if(username==null){
		Error error=new Error("Unauthorized access..");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	List<BlogPost> blogs=blogPostService.getBlogs(approved);
	return new ResponseEntity<List<BlogPost>>(blogs,HttpStatus.OK);
}
public ResponseEntity<?> getBlogById(@PathVariable int id,HttpSession session){
	String username=(String)session.getAttribute("username");
	if(username==null){
		Error error=new Error("Unauthorized access..");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
}
}