package de.imedia24.shop.controller

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/products/")
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("findbysku/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductsBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @GetMapping("findbyskus{skus}",produces = ["application/json;charset=utf-8"])
    fun findProductsBySkus(
            @RequestParam("skus")  skus:Array<String>
    ) : ResponseEntity<Array<ProductEntity>>{
        logger.info("Request | GET | for products $skus")
       val product : Array<ProductEntity>? = productService.findProductsBySkus(skus)
      return if(product.isNullOrEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @PostMapping(produces = ["application/json;charset=utf-8"])
    fun createProduct(
            @RequestBody productResponse: ProductResponse,
    ) :ResponseEntity<ProductResponse?>{
        logger.info("Request | POST | for products $productResponse")

        val product:ProductResponse? = productService.create(productResponse)
      return  if(product == null) {
            ResponseEntity.badRequest().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @PutMapping("{sku}",produces = ["application/json;charset=utf-8"])
    fun updateProduct(
        @RequestBody productResponse: ProductResponse,@PathVariable("sku") sku: String
    ) : ResponseEntity<ProductResponse?>{
        logger.info("Request | PUT | for products $productResponse")

        val product = productService.update(productResponse,sku)
        return if(product == null) {
            ResponseEntity.badRequest().build()
        } else {
           return ResponseEntity.ok(product)
        }
    }



}
