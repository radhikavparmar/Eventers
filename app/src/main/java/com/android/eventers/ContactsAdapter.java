package com.android.eventers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by radhikaparmar on 21/06/17.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    private static final int CLICK = 0;
    private static final int CHECK_CLICK = 1;
    private static final int LONG_CLICK = 2;

    private ArrayList<Contacts> mArrayList;
    final private ListItemClickListener mOnClickListener;




    public interface ListItemClickListener{

        void onListItemClick(int clickedItemIndex, int whichClick);

    }

    public ContactsAdapter( ArrayList<Contacts> arrayList,ListItemClickListener listener) {
        mOnClickListener = listener;
        this.mArrayList = arrayList;

    }


    public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {



        public TextView name;
        public CheckBox select;

        public ContactsViewHolder(View view) {
            super(view);


            name = (TextView)view.findViewById(R.id.name_textView_in_card);
            select = (CheckBox) view.findViewById(R.id.checkBox_in_card);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition,CLICK);


        }

        @Override
        public boolean onLongClick(View view) {
            int clickedPosition = getAdapterPosition();

            mOnClickListener.onListItemClick(clickedPosition,LONG_CLICK);
            return true;
        }
    }
    @Override
    public ContactsAdapter.ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.contacts_cardview;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        ContactsViewHolder viewHolder = new ContactsViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ContactsViewHolder holder, final int position) {

        final int pos = position;
        holder.name.setText(mArrayList.get(position).getName());
        holder.select.setChecked(mArrayList.get(position).getFlag());
        holder.select.setTag(mArrayList.get(position));

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                Contacts contact = (Contacts) cb.getTag();

                contact.setFlag(cb.isChecked());
                mArrayList.get(pos).setFlag(cb.isChecked());
                if(cb.isChecked()) {
                    mOnClickListener.onListItemClick(pos, CHECK_CLICK);
                }
                //Toast.makeText(view.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return mArrayList.size();
    }

    //method to access in activity after updating selection
    public ArrayList<Contacts> getContactsList() {
        return mArrayList;
    }

}
