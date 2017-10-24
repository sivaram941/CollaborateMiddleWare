package restController;

import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Com.collaborate.service.UserService;

@Controller
public class UserController {
	@Autowired
private UserService userService;
	public UserController(){
		System.out.println("UserController Instantiated");
	}
	
	@RequestMapping(value="/registeruser",method=RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user){
		if(!userService.isUsernameValid(user.getUsername()))
		{
			Error error=new Error("username already exists,, please enter different username");
	 		return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
	    }
		if(!userService.isEmailValid(user.getEmail())
		{
			Error error=new Error("Email address already exists,, please enter different email");
	 		return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
		}
			
			boolean result=userService.registerUser(user);
		if(result)
		{
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		else{
			Error error=new Error("unable to register user details");
			return new ResponseEntity<User>(user,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?>login(@RequestBody User user,HttpSession session){
     User validUser=UserService.login(user);
	
		if(validUser==null){
		{
			Error error=new Error("Invalid username/password... ");
	 		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    }
		System.out.println("ONLINE STATUS BEFORE UPDATE"+validUser.isOnline());
		validUser.setOnline(true);
		try{
			userService.update(validUser);
		}
		catch(Exception e)
		{
			Error error=new Error("Unable to update Online status");
	 		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println("ONLINE STATUS AFTER UPDATE" +validUser.isOnline());
		session.setAttribute("username", validUser.getUsername());
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		@RequestMapping(value="/logout",method=RequestMethod.PUT)
		public ResponseEntity<?> logout(HttpSession session){
			String username=(String)session.getAttribute("username");
			System.out.println("Name of the user is"+username);
            if(username==null){
            Error error=new Error("Unauthorised access..please login..");
            return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
            }
		User user=userService.getUserByUsername(username);
		user.setOnline(false);
		userService.update(user);
		session.removeAttribute("username");
		session.invalidate();
		return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		@RequestMapping(value="/getuser",method=RequestMethod.GET)
		public ResponseEntity<?> getUser(HttpSession session){
			String username=(String)session.getAttribute("username");
			if(username==null){
				Error error=new Error("Unauthorised access..please login..");
				return new ResponseEntity<User>(user,HttpStatus.OK);
			}
		
		public ResponseEntity<?> updateUser(@RequestBody User user, HttpSession session){
			String username=(String)session.getAttribute("username");
			if(username==null){
				Error error=new Error("Unauthorised access..please login..");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			}
			if(!userService.isEmailValid(user.getEmail())){
				Error error=new Error("Email address already exists..please enter different email");
				return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
			}
		}
	}