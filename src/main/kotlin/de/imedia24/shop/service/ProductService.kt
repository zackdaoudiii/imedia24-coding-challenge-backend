package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.entity.ProductEntity.Companion.toProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.Objects

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        val productEntity:ProductEntity? = productRepository.findBySku(sku)
        if(productEntity != null ){
            return productEntity.toProductResponse()
        }
        else throw Exception("product not found")
    }

    fun findProductsBySkus(sku: Array<String>): Array<ProductEntity>? {
        val productEntity:Array<ProductEntity>? = productRepository.findProductsBySkus(sku)
        if(!productEntity.isNullOrEmpty() ){
            return productEntity  // TODO convert to ProductResponse :)
        }
        else throw Exception("product not found")
    }
    fun create(productResponse: ProductResponse): ProductResponse? {
        return productRepository.save(productResponse.toProductEntity()).toProductResponse()
    }

    fun update(productResponse: ProductResponse , sku : String): ProductResponse? {
        val productEntity : ProductEntity? = productRepository.findBySku(sku)
        if(productEntity !=  null ) {
            if(productEntity.sku == productResponse.sku){
                return productRepository.save(productResponse.toProductEntity()).toProductResponse()
            }
            else throw Exception("SKU is unchangeable")
        }
        else throw Exception("product not found")
    }
}
