package com.telenor.products.api.rest;

import com.telenor.products.domain.Product;
import com.telenor.products.exception.DataFormatException;
import com.telenor.products.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 * Filter and CRUD on products
 */

@RestController
@RequestMapping(value = "/product")
@Api(tags = {"products"})
public class ProductController extends AbstractRestHandler {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get list of all products by filter",
            notes = "filters are not mandatory and can use all combinations")
    @ResponseBody
    public List<Product> getAllProduct(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "min_price", required = false) String minPrice,
            @RequestParam(value = "max_price", required = false) String maxPrice,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "property", required = false) String property,
            @RequestParam(value = "property:color", required = false) String propertyColor,
            @RequestParam(value = "property:gb_limit_min", required = false) String propertyGbLimitMin,
            @RequestParam(value = "property:gb_limit_max", required = false) String propertyGbLimitMax
    ) {
        //   System.out.println(type + "," + minPrice + "," + maxPrice + "," + city + "," + property + "," + propertyColor + "," + propertyGbLimitMin + "," + propertyGbLimitMax);
        return this.productService.getAllProducts(type, minPrice, maxPrice, city, property, propertyColor, propertyGbLimitMin, propertyGbLimitMax);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a product resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createProduct(@RequestBody Product product,
                              HttpServletRequest request, HttpServletResponse response) {
        Product createdProduct = this.productService.createProduct(product);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdProduct.getId()).toString());
    }



    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single product.", notes = "You have to provide a valid product ID.")
    @ResponseBody
    Product getProduct(@ApiParam(value = "The ID of the product.", required = true)
                       @PathVariable("id") Long id,
                       HttpServletRequest request, HttpServletResponse response) {
        Product product = this.productService.getProduct(id);
        checkResourceFound(product);
        return product;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a product resource.", notes = "You have to provide a valid product ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateProduct(@ApiParam(value = "The ID of the existing product resource.", required = true)
                              @PathVariable("id") Long id, @RequestBody Product product,
                              HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.productService.getProduct(id));
        if (id != product.getId()) throw new DataFormatException("ID doesn't match!");
        this.productService.updateProduct(product);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a product resource.", notes = "You have to provide a valid product ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteProduct(@ApiParam(value = "The ID of the existing product resource.", required = true)
                              @PathVariable("id") Long id) {
        checkResourceFound(this.productService.getProduct(id));
        this.productService.deleteProduct(id);
    }
}
