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
            if (data.getDescription() != null) {
                description.setVisibility(View.VISIBLE);
                description.setText(data.getDescription().toString());
            }
            if (data.getLanguage() != null) {
                language.setVisibility(View.VISIBLE);
                language.setText(data.getLanguage());
            }
            if (data.getStargazersCount() != null && data.getStargazersCount() != 0) {
                starCount.setVisibility(View.VISIBLE);
                starCount.setText(String.valueOf(data.getStargazersCount()));
            }
            if (data.getForksCount() != null && data.getForksCount() != 0) {
                forks.setVisibility(View.VISIBLE);
                forks.setText(String.valueOf(data.getForksCount()));
            }
            if (data.getLicense() != null) {
                license.setVisibility(View.VISIBLE);
                license.setText(data.getLicense().getSpdxId());
            }
        }
    }
}
