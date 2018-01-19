package sa.githubclient.screens.main;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import sa.githubclient.R;
import sa.githubclient.api.models.UserData;
import sa.githubclient.utils.BaseRVAdapter;
import sa.githubclient.utils.BaseViewHolder;

public class UserDataRVAdapter extends BaseRVAdapter<UserData> {
    @Override
    public BaseViewHolder<UserData> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserDataVH(inflateView(parent, R.layout.item_user_data));
    }

    static class UserDataVH extends BaseViewHolder<UserData> {

        @BindView(R.id.data_name)
        TextView dataName;
        @BindView(R.id.data_value)
        TextView dataValue;

        public UserDataVH(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(UserData data) {
            if (data.getDataValue() != null) {
                dataName.setText(data.getDataName());
                dataValue.setText(data.getDataValue());
            }
        }
    }
}
