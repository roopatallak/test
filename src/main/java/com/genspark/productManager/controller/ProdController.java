package com.genspark.productManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genspark.productManager.entity.Product;
import com.genspark.productManager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class ProdController {

    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("name", "roopa");
        return "index";
    }

    //Display Product List
    @GetMapping("/products")
    public String viewProducts(Model model) {
        model.addAttribute("prodList", service.getAllProducts());
        return "products";
    }

    @GetMapping("/newProduct")
    public String showNewProductForm(Model model) {
        //Create a product to bind data
        Product prod = new Product();
        model.addAttribute("product", prod);
        return ("new_product");

    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        //Save product
        service.saveProduct(product);
        return("redirect:/products");

    }

    @GetMapping("/showFormUpdate/{id}")
    public String showFormUpdate(@PathVariable(value = "id") int productId, Model model) {
        //Get Product from the service
        Product prod = service.getProductById(productId);
        //pre-populate form data
        model.addAttribute("product", prod);
        return ("update_product");
    }

    @GetMapping("deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") int id) {
        //Delete the product by ID
        service.deleteProductById(id);
        return("redirect:/products");
    }

    //Test File Upload
    @Value("${file.upload.dir}")
    String FILE_DIR;


    //@RequestMapping(value="/uploadFile", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/uploadFile")
    public ResponseEntity<Object> fileUpload(@RequestParam("file")MultipartFile file, @RequestParam("test_obj") String test_obj) throws IOException{
        //System.out.println("Here..");
        String filename = file.getOriginalFilename();
        File myFile = new File(FILE_DIR + filename);

        System.out.println("File name is : " + myFile.getName());
        System.out.println("Test object is " + test_obj);
        myFile.createNewFile();

        FileOutputStream fos = null;
        fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();

        ObjectMapper mapper = new ObjectMapper();
        Product prod = mapper.readValue(test_obj, Product.class);
        System.out.println("Product resolved: " + prod.getProductName());

        return new ResponseEntity<Object>("File Uploaded Successfully.", HttpStatus.OK);

    }

    @GetMapping("/newList/{str}")
    public String viewProductsStartingWith( @PathVariable(value = "str") String str,  Model model) {
        model.addAttribute("prodList", service.getProductStartingWith(str));
        return "products_startwith";
    }

}
