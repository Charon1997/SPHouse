package nexuslink.charon.sphouse.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.bean.EatBean;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/27 23:41
 * 修改人：Charon
 * 修改时间：2017/8/27 23:41
 * 修改备注：
 */

public class EatRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<EatBean> eatBeanList;

    public EatRecyclerViewAdapter(List<EatBean> eatBeanList) {
        this.eatBeanList = eatBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_eat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).mEtPickTime.setText(getTime(eatBeanList.get(position).getFoodTime()));
        ((MyViewHolder)holder).mTvFoodIntake.setText(getIntake(eatBeanList.get(position).getFoodIntake()));
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    private String getIntake(int foodIntake) {
        return foodIntake + "g";
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mEtPickTime;
        private TextView mTvFoodIntake;
        public MyViewHolder(View itemView) {
            super(itemView);
            mEtPickTime = (TextView) itemView.findViewById(R.id.pick_time_text_eat);
            mTvFoodIntake = (TextView) itemView.findViewById(R.id.intake_text_eat);
        }
    }

    @Override
    public int getItemCount() {
        return eatBeanList.size();
    }
}
