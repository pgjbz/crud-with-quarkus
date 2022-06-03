package com.ciandt.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.ciandt.models.Product;

@Path("/products")
public class ProductController {

    @GET
    public List<Product> getAll() {
        return Product.listAll();
    }

    @GET
    @Path("{id}")
    @Transactional
    public Product findById(@PathParam(value = "id") Long id) {
        return Product.findById(id);
    }

    @POST
    @Transactional
    public Product save(Product product) {
        product.persist();
        return product;
    }

    @PUT
    @Path("{id}")
    public void update(@PathParam(value = "id") Long id, Product product) {
        Product.findByIdOptional(id).ifPresent(p -> {
            var fromDb = (Product) p;
            fromDb.name = product.name;
            fromDb.price = product.price;
            fromDb.persist();
        });
    }

    @DELETE
    @Path("{id}")
    public void deleteById(Long id) {
        Product.deleteById(id);
    }

}
