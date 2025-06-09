package com.telenor.products.api.rest;

import com.telenor.products.domain.Product;
import com.telenor.products.exception.DataFormatException;
import com.telenor.products.service.ProductService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/upload")
@Api(tags = {"data"})
public class UploadController extends AbstractRestHandler {

    @Autowired
    private ProductService productService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Upload the csv data file", notes = "make sure the format of the file")
    public void createProduct(@RequestParam("file") MultipartFile file) throws IOException {

        CsvParserSettings csvParserSettings = new CsvParserSettings();
        csvParserSettings.setHeaderExtractionEnabled(true);
        csvParserSettings.setLineSeparatorDetectionEnabled(true);
        CsvParser csvParser = new CsvParser(csvParserSettings);
        List<Record> records = csvParser.parseAllRecords(file.getInputStream());
        List<Product> productList = new ArrayList<>();
        records.forEach(record -> {
            Product product = new Product();
            try {
                product.setProductType(record.getString("Product type"));
                String productProperties = record.getString("Product properties");
                if (!StringUtils.isEmpty(productProperties)) {
                    if (productProperties.toLowerCase(Locale.ROOT).contains("color")) {
                        product.setProductProperty("color");
                        product.setPropertyColor(productProperties.split(":")[1]);
                    } else if (productProperties.toLowerCase(Locale.ROOT).contains("gb_limit")) {
                        product.setProductProperty("gb_limit");
                        String value = productProperties.split(":")[1];
                        product.setPropertyGbLimit(Integer.valueOf(value));
                    }
                }
                product.setPrice(record.getDouble("Price").intValue());
                product.setStoreAddress(record.getString("Store address"));
            } catch (Exception e) {
                throw new DataFormatException(e.getMessage() + ":data format issue while processing data from csv");
            }
            productList.add(product);
        });
        productList.forEach(product -> {
            this.productService.createProduct(product);
        });
    }

}
