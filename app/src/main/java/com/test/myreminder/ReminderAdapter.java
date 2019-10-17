package com.test.myreminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

class ReminderAdapter extends ListAdapter<Reminder, ReminderAdapter.ReminderHolder>
{
    //private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    public ReminderAdapter()
    {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Reminder> DIFF_CALLBACK = new DiffUtil.ItemCallback<Reminder>()
    {
        @Override
        public boolean areItemsTheSame(@NonNull Reminder oldItem, @NonNull Reminder newItem)
        {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Reminder oldItem, @NonNull Reminder newItem)
        {
            return oldItem.getName().equals(newItem.getName())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriority() == newItem.getPriority()
                    && oldItem.getDate().equals(newItem.getDate())
                    && oldItem.getTime().equals(newItem.getTime());
        }
    };

    class ReminderHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView remDate;
        private TextView remTime;

        public ReminderHolder(View itemView)
        {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            remDate = itemView.findViewById(R.id.text_view_date);
            remTime = itemView.findViewById(R.id.text_view_time);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION)
                {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_item, parent, false);
        return new ReminderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderHolder holder, int position)
    {
        Reminder currentReminder = getItem(position);
        holder.textViewTitle.setText(currentReminder.getName());
        holder.textViewDescription.setText(currentReminder.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentReminder.getPriority()));
        holder.remTime.setText((currentReminder.getTime()));
        holder.remDate.setText(currentReminder.getDate());
    }

    public Reminder getReminderAt(int position)
    {
        return getItem(position);
    }

    public interface OnItemClickListener
    {
        void onItemClick(Reminder reminder);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }
}

