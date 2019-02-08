package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.logicaltech.mydemoapplication.R;
import com.logicaltech.mydemoapplication.activity.AccountDetailActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import model.Account_Model;

public class Account_Adapter extends RecyclerView.Adapter<Account_Adapter.RecyclerViewHolder>
{
    public ArrayList<Account_Model> orderList;
    public Context mContext;
    public Account_Adapter(ArrayList<Account_Model> orderList , Context context)
    {
        this.orderList = orderList;
        mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_detail_child_view, parent, false);
        return new Account_Adapter.RecyclerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position)
    {
        final Account_Model account_model = orderList.get(position);

        holder.TV_AccountName.setText("Account Name: "+account_model.getAc_name());
        holder.TV_AccountNumber.setText("Account No: "+account_model.getAc_no());
        holder.TV_BankName.setText("Branch Name: "+account_model.getBk_name());
        holder.TV_Branch_Name.setText("Bank Name: "+account_model.getBk_branch());
        holder.TV_Mobile_Name.setText("Mobile No"+account_model.getBank_mobile_no());

        holder.relativeLayout_Account_List.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(mContext,AccountDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("token","1");
                intent.putExtra("accoun_name",orderList.get(position).getAc_name());
                intent.putExtra("accoun_no",orderList.get(position).getAc_no());
                intent.putExtra("accoun_type",orderList.get(position).getAc_type());
                intent.putExtra("bank_name",orderList.get(position).getBk_name());
                intent.putExtra("branck_name",orderList.get(position).getBk_branch());
                intent.putExtra("ifsc_code",orderList.get(position).getBk_ifsc());
                intent.putExtra("mobile_no",orderList.get(position).getBank_mobile_no());

                mContext.getApplicationContext().startActivity(intent);
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
        TextView TV_AccountName,TV_AccountNumber,TV_BankName,TV_Branch_Name,TV_Mobile_Name;
        RelativeLayout relativeLayout_Account_List;
        public RecyclerViewHolder(View itemView)
        {
            super(itemView);
            TV_AccountName = (TextView) itemView.findViewById(R.id.text_view_account_name);
            TV_AccountNumber = (TextView) itemView.findViewById(R.id.text_view_account_number);
            TV_BankName = (TextView) itemView.findViewById(R.id.text_view_bank_name);
            TV_Branch_Name = (TextView) itemView.findViewById(R.id.text_view_branch_name);
            TV_Mobile_Name = (TextView) itemView.findViewById(R.id.text_view_mobile_name);
            relativeLayout_Account_List = (RelativeLayout)itemView.findViewById(R.id.relative_layout_account_list);
        }
    }
}
