package sa.githubclient.api;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import sa.githubclient.api.models.User;

public interface Api {

    @GET("/users/{username}")
    Observable<User> getUserPublicProfile(@Path("username") String user);

    @GET("/users/{username}/repos")
    Observable<List<Repository>> getRepositories(@Path("username") String user);

}
