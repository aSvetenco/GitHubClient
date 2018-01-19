package sa.githubclient.screens.repos;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import sa.githubclient.R;
import sa.githubclient.api.models.Repository;
import sa.githubclient.utils.BaseRVAdapter;
import sa.githubclient.utils.BaseViewHolder;

public class ReposRVAdapter extends BaseRVAdapter<Repository> {
    @Override
    public BaseViewHolder<Repository> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReposVH(inflateView(parent, R.layout.item_repository));
    }

    static class ReposVH extends BaseViewHolder<Repository> {

        @BindView(R.id.repo_name)
        TextView repoName;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.language)
        TextView language;
        @BindView(R.id.stargazers_count)
        TextView starCount;
        @BindView(R.id.forks)
        TextView forks;
        @BindView(R.id.license)
        TextView license;


        public ReposVH(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Repository data) {
            repoName.setText(data.getName());
            description.setText(data.getDescription().toString());
            language.setText(data.getLanguage());
            starCount.setText(data.getStargazersCount());
            forks.setText(data.getForksCount());
            license.setText(data.getLicense().getName());

        }
    }
}
