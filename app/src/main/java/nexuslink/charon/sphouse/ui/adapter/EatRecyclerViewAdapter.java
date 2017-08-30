package nexuslink.charon.sphouse.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.bean.EatBean;
import nexuslink.charon.sphouse.config.OnEatItemOnClickListener;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/27 23:41
 * 修改人：Charon
 * 修改时间：2017/8/27 23:41
 * 修改备注：
 */

public class EatRecyclerViewAdapter extends RecyclerView.Adapter implements OnEatItemOnClickListener {
    private OnEatItemOnClickListener itemOnClickListener;
    private static final String TAG = EatRecyclerViewAdapter.class.getSimpleName();
    private List<EatBean> eatBeanList;
    private int position;


    public EatRecyclerViewAdapter(List<EatBean> eatBeanList) {
        this.eatBeanList = eatBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_eat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: "+getTime(eatBeanList.get(position).getFoodTime()));
        ((MyViewHolder)holder).mEtPickTime.setText(getTime(eatBeanList.get(position).getFoodTime()));
        ((MyViewHolder)holder).mTvFoodIntake.setText(getIntake(eatBeanList.get(position).getFoodIntake()));

        ((MyViewHolder)holder).mLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(position);
                return false;
            }
        });

        ((MyViewHolder)holder).mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v,position);
            }
        });
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    private String getIntake(int foodIntake) {
        return foodIntake + "g";
    }

    @Override
    public void onItemClick(View view, int position) {
        if (itemOnClickListener != null) {
            itemOnClickListener.onItemClick(view,position);
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView mEtPickTime;
        private TextView mTvFoodIntake;
        private LinearLayout mLinearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            mEtPickTime = (TextView) itemView.findViewById(R.id.pick_time_text_eat);
            mTvFoodIntake = (TextView) itemView.findViewById(R.id.intake_text_eat);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_lin_eat);

            mLinearLayout.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, 0, 0, "编辑");
            menu.add(0, 1, 0, "删除");
        }
    }

    @Override
    public int getItemCount() {
        return eatBeanList.size();
    }

    public int getPosition() {
        return position;
    }

    private void setPosition(int position) {
        this.position = position;
    }

    public void setOnEatItemOnClickListener(OnEatItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }
}
