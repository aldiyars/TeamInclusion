package kz.teamInclusion.Inclusion.controllers;

import kz.teamInclusion.Inclusion.model.Category;
import kz.teamInclusion.Inclusion.model.Blog;
import kz.teamInclusion.Inclusion.model.Users;
import kz.teamInclusion.Inclusion.repository.CategoryRepositoty;
import kz.teamInclusion.Inclusion.repository.BlogRepositoty;
import kz.teamInclusion.Inclusion.repository.RoleRepository;
import kz.teamInclusion.Inclusion.repository.UserRepository;
import kz.teamInclusion.Inclusion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CategoryRepositoty categoryRepositoty;
    @Autowired
    BlogRepositoty blogRepositoty;
    @Autowired
    UserService userService;
    @Value("${upload.path}")
    private String uploadPath ;

    @GetMapping(path = "/")
    //@PreAuthorize("isAuthenticated()")
    public String getAllUsers(Model model){
        List<Users> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "index";
    }


    @GetMapping(path = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("user", getUserData());
        return "profile";
    }

    public Users getUserData(){
        Users userData = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            userData = userRepository.findByEmail(secUser.getUsername());
        }
        return userData;
    }

    //BlogCategory
    @GetMapping(path = "/category")
    public String createCategory(Model model){
        List<Category> categories = categoryRepositoty.findAll();
        model.addAttribute("category", categories);
        return "category";
    }

    @PostMapping(path = "/createCategory")
    public String createCategory(@RequestParam(name = "name") String name){
        String redirect = "redirect:/category?error";
        Category category = categoryRepositoty.findCategoryByName(name);

        if (category==null){
            category = new Category(null,name);
            categoryRepositoty.save(category);
            redirect = "redirect:/category?success";
        }
        return redirect;
    }

    //BlogPost
    @GetMapping(path = "/blog")
    @PreAuthorize("isAuthenticated()")
    public String blog(Model model){
        List<Category> categories = categoryRepositoty.findAll();
        model.addAttribute("cat", categories);
        List<Blog> blogs = blogRepositoty.findAll();
        model.addAttribute("blog", blogs);

        Users users = new Users();
        users = userRepository.findByEmail(getUserData().getEmail());
        model.addAttribute("userid",users);
        return "blog";
    }

    @PostMapping(path = "/createblog")
    //@PreAuthorize("hasAnyRole('role_admin')")
    public String createBlog(@RequestParam(name = "title") String title,
                             @RequestParam(name = "desc") String desc,
                             @RequestParam(name = "category") int catId,
                             @RequestParam(name = "user") int userId,
                             @RequestParam(name = "file") MultipartFile file) throws IOException {
        String redirect = "redirect:/blog?error";
        Category category = categoryRepositoty.getOne((long) catId);
        Users user = userRepository.getOne((long) userId);
        Date date = new Date();

        if (title!=null) {
            Blog blog = new Blog(null, title, null, desc, date, category, user);
            if (file != null && !file.getOriginalFilename().isEmpty()){
                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String uiidFile = UUID.randomUUID().toString();
                String resultFileName = uiidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                blog.setImage(resultFileName);
            }
            blogRepositoty.save(blog);
            redirect = "redirect:/blog?success";
        }
        return redirect;
    }

    @GetMapping(path = "/details/{id}")
    public String detailsBlog(Model model, @PathVariable(name = "id") Long id){
        Blog blog = blogRepositoty.getOne(id);
        model.addAttribute("detailsblog", blog);
        return "detailsblog";
    }

    @PostMapping(path = "deleteblog")
    public String deleteBlog(@RequestParam(name = "id") Long id){
        String redirect = "redirect:/blog?DeleteBlogError";
        Blog blog = blogRepositoty.getOne(id);
        if (blog!=null){
            blogRepositoty.delete(blog);
            redirect = "redirect:/blog?DeleteBlogSuccess";
        }
        return redirect;
    }

    @GetMapping(path = "/editblog/{id}")
    public String editBlog(Model model, @PathVariable(name = "id") Long id){
        List<Category> categories = categoryRepositoty.findAll();
        model.addAttribute("categoryeditblog", categories);
        Blog blog = blogRepositoty.getOne(id);
        model.addAttribute("editblog", blog);
        return  "editblog";
    }

    @PostMapping(path = "/editblog")
    public String editBlog(@RequestParam(name = "id") Long id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "content") String content,
                           @RequestParam(name = "category") Long categoryId){
        String redirect = "redirect:/details/" + id + "?Error";
        Blog blog = blogRepositoty.getOne(id);
        Category category = categoryRepositoty.getOne(categoryId);
        if(blog!=null){
            blog.setTitle(title);
            blog.setContent(content);
            blog.setCategory(category);
            blog.setPost_date(new Date());
            blogRepositoty.save(blog);
            redirect = "redirect:/details/" + id + "?SaveBlogSuccess";
        }
        return redirect;
    }
}
