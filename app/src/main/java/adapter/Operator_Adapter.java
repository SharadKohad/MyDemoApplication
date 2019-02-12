package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.logicaltech.mydemoapplication.R;
import com.logicaltech.mydemoapplication.activity.DTHActivity;
import com.logicaltech.mydemoapplication.activity.RechargeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import model.Operator_Model;

public class Operator_Adapter extends RecyclerView.Adapter<Operator_Adapter.RecyclerViewHolder>
{
    public ArrayList<Operator_Model> orderList;
    public Context mContext;
    String operatorType;
    public Operator_Adapter(ArrayList<Operator_Model> orderList , Context context)
    {
        this.orderList = orderList;
        mContext = context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.operator_type, parent, false);
        return new Operator_Adapter.RecyclerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position)
    {
        final Operator_Model account_model = orderList.get(position);
        holder.TV_Operator_Type.setText(account_model.getOperate());
        Picasso.with(mContext).load(account_model.getIcon()).placeholder(R.drawable.profile_icon).into(holder.circleImageViewOperatorType);

        operatorType = account_model.getType();
        holder.LinearLayout_Operator_Type.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (operatorType.equals("1"))
                {
                    Intent intent=new Intent(mContext,RechargeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("token","1");
                    intent.putExtra("operator",orderList.get(position).getOperate());
                    mContext.getApplicationContext().startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(mContext,DTHActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("token","1");
                    intent.putExtra("operator",orderList.get(position).getOperate());
                    mContext.getApplicationContext().startActivity(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return orderList.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView TV_Operator_Type;
        LinearLayout LinearLayout_Operator_Type;
        CircleImageView  circleImageViewOperatorType;
        public RecyclerViewHolder(View itemView)
        {
            super(itemView);
            TV_Operator_Type = (TextView) itemView.findViewById(R.id.text_view_operator_name);
            LinearLayout_Operator_Type = (LinearLayout) itemView.findViewById(R.id.linear_layout_operator_type);
            circleImageViewOperatorType = (CircleImageView) itemView.findViewById(R.id.img_operator_type_image);
        }
    }
}
