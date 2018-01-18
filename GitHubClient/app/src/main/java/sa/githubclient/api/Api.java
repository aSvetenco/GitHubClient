package sa.githubclient.api;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import sa.githubclient.api.models.Branch;
import sa.githubclient.api.models.Contributor;
import sa.githubclient.api.models.Repository;

public interface Api {

    @GET("/users/{user}/repos")
    Observable<List<Repository>> getRepositories(@Path("user") String user);

    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> getContributors(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/branches")
    Observable<List<Branch>> getBranches(@Path("owner") String owner, @Path("repo") String repo);
}
