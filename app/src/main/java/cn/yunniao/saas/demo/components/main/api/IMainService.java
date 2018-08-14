package cn.yunniao.saas.demo.components.main.api;

import java.util.List;

import cn.yunniao.saas.demo.net.response.ResponseData;
import cn.yunniao.saas.demo.components.main.bean.Repo;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IMainService {

	@GET("api/main.json")
	Observable<ResponseData<List<Repo>>> listRepos();

	@GET("api/list.json")
	Observable<ResponseData<List<String>>> listItem();
}
