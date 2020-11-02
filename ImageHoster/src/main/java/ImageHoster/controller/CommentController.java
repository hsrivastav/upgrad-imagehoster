package ImageHoster.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;

@Controller
public class CommentController {
	 @Autowired
	    private CommentService commentService;
	 @Autowired
	    private ImageService imageService;
	 
	 @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
	    public String createComment(@PathVariable("imageId") Integer imageId,@PathVariable("imageTitle") String imageTitle,Comment comment,HttpSession session) {
		 User user = (User) session.getAttribute("loggeduser");
		 Image image = imageService.getImageById(imageId);
		 comment.setUser(user);
		 comment.setImage(image);
		 comment.setCreatedDate(new Date());
		 commentService.addComment(comment);
	    
		
	    return "redirect:/images/"+imageId+"/"+imageTitle;
	    }

	  @RequestMapping("/images/{id}/{title}")
	    public String commentResult(@PathVariable("id") Integer id,Model model) {
		  return "redirect:/images/"+id;
	    }
}
