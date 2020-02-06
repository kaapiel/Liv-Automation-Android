package br.com.aguido.livautomation.service.livautomation.productCatalog;

import br.com.aguido.livautomation.service.livautomation.LivautomationResponse;
import br.com.aguido.livautomation.service.livautomation.productCatalog.category.model.Categories;
import br.com.aguido.livautomation.service.livautomation.productCatalog.product.AddItemToCartRequest;
import br.com.aguido.livautomation.service.livautomation.productCatalog.product.ProductRequest;
import br.com.aguido.livautomation.service.livautomation.productCatalog.product.ProductResponse;
import br.com.aguido.livautomation.service.livautomation.productCatalog.search.model.ResultSearchResponse;
import br.com.aguido.livautomation.service.livautomation.productCatalog.subcategory.SubcategoryRequest;
import br.com.aguido.livautomation.service.livautomation.productCatalog.subcategory.model.Subcategory;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by vilmar.filho on 5/11/16.
 */
public interface ProductCatalogService {

    @GET("/api/catalog/products/categories")
    void getCategories(@Header("Authorization") String token,
                       @Header("Cookie") String jSessionId,
                       Callback<Categories> cb);

    @POST("/api/catalog/products/categories")
    void getCategoriesByID(@Header("Authorization") String token,
                           @Header("Cookie") String jSessionId,
                           @Body SubcategoryRequest subcategoryRequest,
                           Callback<Subcategory> cb);

    @GET("/api/catalog/products")
    void searchProducts(@Header("Authorization") String token,
                        @Header("Cookie") String jSessionId,
                        @Query("No") int offset,
                        @Query("Nrpp") int itemPerPage,
                        @Query("Ntt") String term,
                        Callback<ResultSearchResponse> cb);

    @POST("/api/catalog/products")
    void getProductByID(@Header("Authorization") String token,
                        @Header("Cookie") String jSessionId,
                        @Body ProductRequest productRequest,
                        Callback<ProductResponse> cb);

    @POST("/api/shoppingcart/items")
    void addProductToCart(@Header("Authorization") String token,
                          @Header("Cookie") String jSessionId,
                          @Body AddItemToCartRequest request,
                          Callback<LivautomationResponse> cb);

}
