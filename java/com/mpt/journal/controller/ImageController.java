package com.mpt.journal.controller;

import com.mpt.journal.entity.ProductEntity;
import com.mpt.journal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {

    @Autowired
    private ProductService productService;

    @GetMapping("/shop/product/image/{id}")
    @ResponseBody
    public byte[] getProductImage(@PathVariable Long id) {
        ProductEntity product = productService.getById(id);
        if (product != null && product.getImage() != null) {
            return product.getImage();
        }
        // Возвращаем placeholder изображение или пустой массив
        return new byte[0];
    }

    @GetMapping(value = "/shop/product/image/{id}/content", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getProductImageWithContentType(@PathVariable Long id) {
        return getProductImage(id);
    }
}