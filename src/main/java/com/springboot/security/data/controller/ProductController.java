package com.springboot.security.data.controller;

import com.springboot.security.data.dto.ChangeProductNameDto;
import com.springboot.security.data.dto.ProductDto;
import com.springboot.security.data.dto.ProductResponseDto;
import com.springboot.security.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get product by number", description = "상품 번호로 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없습니다.")
    })
    @GetMapping
    public ResponseEntity<ProductResponseDto> getProduct(@RequestParam Long number) {
        long currentTime = System.currentTimeMillis();
        log.info("[getProduct] request Data :: productId : {}", number);
        ProductResponseDto productResponseDto = productService.getProduct(number);

        log.info(
                "[getProduct] response Data :: productId : {}, productName : {}, productPrice : {}, productStock : {}",
                productResponseDto.getNumber(), productResponseDto.getName(),
                productResponseDto.getPrice(), productResponseDto.getStock());
        log.info("[getProduct] Response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @Operation(summary = "Create a new product", description = "새로운 상품을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 발급 받은 access_token", required = true, in = ParameterIn.HEADER)
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestHeader("X-AUTH-TOKEN") String authToken, @RequestBody ProductDto productDto) {
        long currentTime = System.currentTimeMillis();
        ProductResponseDto productResponseDto = productService.saveProduct(productDto);

        log.info("[createProduct] Response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @Operation(summary = "Change product name", description = "상품의 이름을 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상적으로 변경되었습니다."),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없습니다.")
    })
    @Parameters({
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 발급 받은 access_token", required = true, in = ParameterIn.HEADER)
    })
    @PutMapping
    public ResponseEntity<ProductResponseDto> changeProductName(
            @RequestHeader("X-AUTH-TOKEN") String authToken, @RequestBody ChangeProductNameDto changeProductNameDto) throws Exception {
        long currentTime = System.currentTimeMillis();
        log.info("[changeProductName] request Data :: productNumber : {}, productName : {}",
                changeProductNameDto.getNumber(), changeProductNameDto.getName());

        ProductResponseDto productResponseDto = productService.changeProductName(
                changeProductNameDto.getNumber(),
                changeProductNameDto.getName());

        log.info(
                "[changeProductName] response Data :: productNumber : {}, productName : {}, productPrice : {}, productStock : {}",
                productResponseDto.getNumber(), productResponseDto.getName(),
                productResponseDto.getPrice(), productResponseDto.getStock());
        log.info("[changeProductName] response Time : {}ms",
                System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @Operation(summary = "Delete product by number", description = "상품 번호로 상품을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없습니다.")
    })
    @Parameters({
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 발급 받은 access_token", required = true, in = ParameterIn.HEADER)
    })
    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestHeader("X-AUTH-TOKEN") String authToken, @RequestParam Long number) throws Exception {
        long currentTime = System.currentTimeMillis();
        log.info("[deleteProduct] request Data :: productNumber : {}", number);

        productService.deleteProduct(number);

        log.info("[deleteProduct] response Time : {}ms",
                System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
