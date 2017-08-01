package fastcampus.team1.logintest2;

import fastcampus.team1.logintest2.Model.Login;
import fastcampus.team1.logintest2.Model.LoginResult;
import fastcampus.team1.logintest2.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by jhjun on 2017-07-31.
 */

public interface UserService {
    @POST("member/")
    Call<User> createUser(@Body User user);

    @POST("member/login/")
    Call<LoginResult> createLogin(@Body Login login);
}
