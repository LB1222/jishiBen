package com.example.jishiben.Adpter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jishiben.EditActivity;
import com.example.jishiben.R;
import com.example.jishiben.Util.ToastUtil;
import com.example.jishiben.bean.Note;
import com.example.jishiben.date.NoteDbOpenHelper;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Note> mNoteList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    private int viewType;
    //一个代表网格布局一个代表线性布局
    public static int TYPE_LINEAR_LAYOUT = 0;
    public static int TYPE_GRID_LAYOUT = 1;



    public MyAdapter(Context context, List<Note> mNoteList){
        this.mNoteList = mNoteList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        //调用数据库
        mNoteDbOpenHelper = new NoteDbOpenHelper(mContext);
    }
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
//        MyViewHolder myViewHolder = new MyViewHolder(view);
//        return myViewHolder;
//    }


//        Note note = mNoteList.get(position);
//        holder.mTvTitle.setText(note.getTitle());
//        holder.mTvContext.setText(note.getContent());
//        holder.mTvTime.setText(note.getCreatedTime());
//        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, EditActivity.class);
//                intent.putExtra("note",note);
//                mContext.startActivity(intent);
//            }
//        });
//        //长按弹窗
//        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                //弹框样式
//                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
//               //躺床调用
//                View view = mLayoutInflater.inflate(R.layout.list_item_tc, null);
//
//                TextView tvDelete = view.findViewById(R.id.tv_delete);
//                TextView tvEdit = view.findViewById(R.id.tv_edit);
//
//                tvDelete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int row  = mNoteDbOpenHelper.deleteFromDbById(note.getId());
//                        if (row > 0){
//                            removeDate(position);
//
//                            ToastUtil.toastshort(mContext,"删除成功！");
//                        }else {
//                            ToastUtil.toastshort(mContext,"删除失败！");
//                        }
//                        dialog.dismiss();//弹框消失
//                    }
//                });
//                tvEdit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, EditActivity.class);
//                        intent.putExtra("note",note);
//                        mContext.startActivity(intent);
//                        dialog.dismiss();//弹框消失
//                    }
//                });
//                dialog.setContentView(view);
//                dialog.show();
//
//                return false;
//            }
//        });


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LINEAR_LAYOUT) {
            View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        } else if (viewType == TYPE_GRID_LAYOUT) {
            View view = mLayoutInflater.inflate(R.layout.list_item_liner, parent, false);
            MyGrideViewHolder myGrideViewHolder = new MyGrideViewHolder(view);
            return myGrideViewHolder;

        }
        return null;


    }
//    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        mAdapter.onBindViewHolder(holder, position);
//
//        if (!isFirstOnly || holder.getAdapterPosition() > mLastPosition) {
//            for (Animator anim : getAnimators(holder.itemView)) {
//                anim.setDuration(mDuration).start();
//                anim.setInterpolator(mInterpolator);
//            }
//            mLastPosition = holder.getAdapterPosition();
//        } else {
//            ViewHelper.clear(holder.itemView);
//        }
//    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //  mAdapter.onBindViewHolder(holder, position);
        if (holder == null){
            return;
        }
        if (holder instanceof MyViewHolder){
            bindMyViewHolder((MyViewHolder) holder,position);
            
        } else if (holder instanceof  MyGrideViewHolder) {

            bindGridViewHolder((MyGrideViewHolder) holder,position);
        }

    }
    private void bindMyViewHolder(MyViewHolder holder,int position){

        Note note = mNoteList.get(position);

        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContext.setText(note.getContent());
        holder.mTvTime.setText(note.getCreatedTime());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("note", note);
                mContext.startActivity(intent);
            }
        });

        //长按弹窗
        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //弹框样式
                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                //躺床调用
                View view = mLayoutInflater.inflate(R.layout.list_item_tc, null);

                TextView tvDelete = view.findViewById(R.id.tv_delete);
                TextView tvEdit = view.findViewById(R.id.tv_edit);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int row  = mNoteDbOpenHelper.deleteFromDbById(note.getId());
                        if (row > 0){
                            removeDate(position);

                            ToastUtil.toastshort(mContext,"删除成功！");
                        }else {
                            ToastUtil.toastshort(mContext,"删除失败！");
                        }
                        dialog.dismiss();//弹框消失
                    }
                });
                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra("note",note);
                        mContext.startActivity(intent);
                        dialog.dismiss();//弹框消失
                    }
                });
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                return true;
            }
        });

    }
    private  void bindGridViewHolder(MyGrideViewHolder holder,int position){
        Note note = mNoteList.get(position);

        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContext.setText(note.getContent());
        holder.mTvTime.setText(note.getCreatedTime());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("note", note);
                mContext.startActivity(intent);
            }
        });

        //长按弹窗
        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //弹框样式
                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                //躺床调用
                View view = mLayoutInflater.inflate(R.layout.list_item_tc, null);

                TextView tvDelete = view.findViewById(R.id.tv_delete);
                TextView tvEdit = view.findViewById(R.id.tv_edit);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int row  = mNoteDbOpenHelper.deleteFromDbById(note.getId());
                        if (row > 0){
                            removeDate(position);

                            ToastUtil.toastshort(mContext,"删除成功！");
                        }else {
                            ToastUtil.toastshort(mContext,"删除失败！");
                        }
                        dialog.dismiss();//弹框消失
                    }
                });
                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra("note",note);
                        mContext.startActivity(intent);
                        dialog.dismiss();//弹框消失
                    }
                });
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }


    public void refreshDate(List<Note> notes){
        this.mNoteList = notes;
        notifyDataSetChanged();
    }
    public void removeDate(int pos){
        mNoteList.remove(pos);
        notifyItemRemoved(pos);
    }

    //    public int getViewType() {
//        return viewType;
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        TextView mTvContext;
        TextView mTvTime;
        ViewGroup rlContainer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTvTitle = itemView.findViewById(R.id.tv_title);
            this.mTvContext = itemView.findViewById(R.id.tv_context);
            this.mTvTime = itemView.findViewById(R.id.tv_time);
            this.rlContainer = itemView.findViewById(R.id.rl_item_container);
        }
        }
    class MyGrideViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        TextView mTvContext;
        TextView mTvTime;
        ViewGroup rlContainer;
        public MyGrideViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTvTitle = itemView.findViewById(R.id.tv_title);
            this.mTvContext = itemView.findViewById(R.id.tv_context);
            this.mTvTime = itemView.findViewById(R.id.tv_time);
            this.rlContainer = itemView.findViewById(R.id.rl_item_container);
        }
    }
    }

