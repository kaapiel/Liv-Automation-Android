package br.com.aguido.livautomation.service.livautomation.usertransactions;

import br.com.aguido.livautomation.service.livautomation.usertransactions.model.UserTransactionsResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by selem.gomes on 29/10/15.
 */
public interface UserTransactionsService {

    @GET("/api/customer/points/transactions/")
    void listUserTransactions(@Header("Authorization") String authorization,
                              @Query("from") String dateInit,
                              @Query("to") String dateFinish,
                              Callback<UserTransactionsResponse> cb);
}
