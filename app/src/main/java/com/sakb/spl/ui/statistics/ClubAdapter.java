package com.sakb.spl.ui.statistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sakb.spl.R;
import com.sakb.spl.data.model.AllDataItem;

import java.util.ArrayList;
import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<AllDataItem> clubList;
    private List<AllDataItem> filteredClubList;
    private Context context;

    public ClubAdapter(Context context, List<AllDataItem> clubList) {
        this.context = context;
        this.clubList = clubList;
        this.filteredClubList = clubList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }

        return TYPE_ROW;
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_club, viewGroup, false);
            return new ClubViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_club_colorful,
                    viewGroup, false);
            return new ClubViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ClubViewHolder holder, int position) {
        AllDataItem club = filteredClubList.get(position);
        holder.txtName.setText(club.getName());
        holder.txtClub.setText(club.getTeam());
        holder.txtPrice.setText(club.getCost().toString());
        holder.txtSell.setText(club.getSellCost().toString());
        holder.txtBuy.setText(club.getBuyCost().toString());
        holder.txtFrom.setText(club.getForm().toString());
        holder.txtPoint.setText(club.getPoint().toString());
        holder.txtNextMatch.setText(club.getTeamCode());
        if(club.getStatePlayer().equals("normal")){
            holder.tvStates.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_info));
        }else {
            holder.tvStates.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_danger));
        }
    }

    @Override
    public int getItemCount() {
        return filteredClubList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredClubList = clubList;
                } else {
                    List<AllDataItem> filteredList = new ArrayList<>();
                    for (AllDataItem club : clubList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (club.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(club);
                        }
                    }

                    filteredClubList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredClubList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredClubList = (ArrayList<AllDataItem>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtClub, txtSell, txtBuy, txtFrom, txtPoint, txtNextMatch, txtPrice;
        public AppCompatImageView tvStates;
        public ClubViewHolder(View view) {
            super(view);
            tvStates = view.findViewById(R.id.tvStates);
            txtName = view.findViewById(R.id.txtName);
            txtClub = view.findViewById(R.id.txtClub);
            txtSell = view.findViewById(R.id.txtSell);
            txtBuy = view.findViewById(R.id.txtBuy);
            txtFrom = view.findViewById(R.id.txtFrom);
            txtPoint = view.findViewById(R.id.txtPoint);
            txtNextMatch = view.findViewById(R.id.txtNextMatch);
            txtPrice = view.findViewById(R.id.txtPrice);
        }
    }
}