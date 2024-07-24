package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.Entities.Contact;
import com.smart.Entities.User;
import com.smart.dao.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@ModelAttribute     // now this data will be common for all the handlers
	public void addCommonData(Model model, Principal principal) {
		
		String userName =principal.getName();
		System.out.println("UserName:"+userName);
		
		User user = userRepository.getUserByUserName(userName);
		
		System.out.println("User: "+user);
		
		model.addAttribute("user",user);
	}

	// Dash_board home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		
		model.addAttribute("title","User Dashboard");
		
		
		return "General/user_dashboard";
	}
	
	//open add form handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact", new Contact());
		
		return "General/add_contact_form";
	}
	
	
	//processing add contact from
	
	@PostMapping("/process-contact")
	public String processContact(@Valid @ModelAttribute Contact contact,BindingResult result, @RequestParam("image") MultipartFile file, Principal principal) {
		
		
		try {

			if (result.hasErrors()) {
		
				return "General/add_contact_form";
			}

			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);
		
		//process and uploading file
		
		if(file.isEmpty()) {
			//if the file is empty then try our message
			
		}else {
			//file the file to folder and update name to contact
			contact.setImage(file.getOriginalFilename());
			
			File saveFile =new ClassPathResource("static/images").getFile();
			
			Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("image uploaded");
		}
		
		//setting user in contact
		contact.setUser(user);
		
		//setting contact in user
		user.getContacts().add(contact);
		
		this.userRepository.save(user);
		
		System.out.println("Added to data base");
		  
		System.out.println("Data "+contact);
		}catch(Exception e) {
			System.out.println("ERROR :"+e.getMessage());
			e.printStackTrace();
		}
		return "General/add_contact_form";
	}
}




//with the help of "Principal principal" we get unique identifier of user Entity
