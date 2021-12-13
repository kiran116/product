package com.telenor.products.test;

/**
 * Uses JsonPath: http://goo.gl/nwXpb, Hamcrest and MockMVC
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telenor.products.Application;
import com.telenor.products.api.rest.ProductController;
import com.telenor.products.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ProductControllerTest {

    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/product/[0-9]+";

    @InjectMocks
    ProductController controller;

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldCreateAndUpdateAndDelete() throws Exception {
        Product r1 = mockProduct("shouldCreateAndUpdate");
        byte[] r1Json = toJson(r1);
        //CREATE
        MvcResult result = mvc.perform(post("/product")
                        .content(r1Json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        Product r2 = mockProduct("shouldCreateAndUpdate2");
        r2.setId(id);
        byte[] r2Json = toJson(r2);

        //UPDATE
        result = mvc.perform(put("/product/" + id)
                        .content(r2Json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        //RETRIEVE updated
        mvc.perform(get("/product/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.productType", is(r2.getProductType())))
                .andExpect(jsonPath("$.storeAddress", is(r2.getStoreAddress())))
                .andExpect(jsonPath("$.productProperty", is(r2.getProductProperty())))
                .andExpect(jsonPath("$.price", is(r2.getPrice())));

        //DELETE
        mvc.perform(delete("/product/" + id))
                .andExpect(status().isNoContent());
    }


    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }


    private Product mockProduct(String prefix) {
        Product r = new Product();
        r.setStoreAddress(prefix + "_storeAddress");
        r.setProductProperty(prefix + "_productProperty");
        r.setProductType(prefix + "_productType");
        r.setPrice(new Random().nextInt(6));
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    // match redirect header URL (aka Location header)
    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return result -> {
            Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
            assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
        };
    }

}
