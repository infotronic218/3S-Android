package sous.etis.tech.com.ui.dashboard;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sous.etis.tech.com.R;

public class GridViewAdapter extends BaseAdapter {
    private Context context ;
    private  LayoutInflater inflater ;
    private  View root ;
    private List<ViewType>data ;

    public GridViewAdapter(Context context, List<ViewType> data){
        this.context = context;
        this.data = data ;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        inflater = LayoutInflater.from(this.context);
        root = inflater.inflate(R.layout.grid_item, null, false);
        TextView textViewTitle = root.findViewById(R.id.df_grid_item_title_textView);
        ImageView imageView = root.findViewById(R.id.df_grid_item_imageView);

        textViewTitle.setText(data.get(i).getName());
        imageView.setImageDrawable(context.getResources().getDrawable(data.get(i).getImage()));

        return root;
    }
}
