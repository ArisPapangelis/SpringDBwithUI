package com.demo.SpringDBwithUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/app") // This means URL's start with /app (after Application path)
public class MainController {
    @Autowired // This means to get the bean called productRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private ProductRepository productRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewProduct (@RequestParam String name
            , @RequestParam Double weight, @RequestParam String availability
            , @RequestParam Double price, @RequestParam String category
            , @RequestParam String description) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Product p = new Product();
        p.setName(name);
        p.setWeight(weight);
        p.setAvailability(availability);
        p.setPrice(price);
        p.setCategory(category);
        p.setDescription(description);
        productRepository.save(p);
        return "Saved";
        //Sample Post: curl localhost:8080/app/add -d name=Chamomile -d weight=20.0 -d availability=Unavailable -d price=2.7 -d category=Monovarietal -d description=100%25%20organic%20chamomile%20from%20the%20mountains%20of%20Epirus
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Product> getAllProducts() {
        // This returns a JSON or XML with the users
        return productRepository.findAll();
    }
}