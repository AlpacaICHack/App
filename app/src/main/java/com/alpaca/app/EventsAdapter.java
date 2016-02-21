package com.alpaca.app;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class EventsAdapter extends BaseAdapter{

    private List<Event> events;
    private Context context;

    public EventsAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item_event, null);
        }
        //Assign widgets to variables
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView description = (TextView) v.findViewById(R.id.description);
        TextView date = (TextView) v.findViewById(R.id.date);
        ImageView image = (ImageView) v.findViewById(R.id.eventImage);

        Event event = events.get(i);

        //Customise textviews
/*        name.setMaxLines(1);
        name.setEllipsize(TextUtils.TruncateAt.END);
        description.setMaxLines(3);
        description.setEllipsize(TextUtils.TruncateAt.END);*/

        //Draw information
        name.setText(event.getEventName());
        description.setText(event.getEventDescription());
        date.setText(event.getEventDate());
        ImageLoader.getInstance().displayImage(event.getPictureURL(), image);
        return v;
    }
}
