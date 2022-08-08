package com.bridgelabz.reactivemongo.util;

import com.bridgelabz.reactivemongo.dto.ProductDTO;
import com.bridgelabz.reactivemongo.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtil {

    /**
     * To map entity to DTO
     * @param product
     * @return
     */
    public static ProductDTO entityToDto(Product product){
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    /**
     * To map DTO to entity
     * @param productDTO
     * @return
     */
    public static Product dtoToEntity(ProductDTO productDTO){
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
