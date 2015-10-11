package patienthub.binary.com.patienthub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import patienthub.binary.com.patienthub.R;
import patienthub.binary.com.patienthub.data.Dosage;

/**
 * Created by Mark on 9/10/2015.
 */
public class MedicationListAdapter extends BaseAdapter {

    Context context;
    List<Dosage> data;
    private static LayoutInflater inflater = null;

    public MedicationListAdapter(Context context, List<Dosage> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.medication_cusom_listview_item, null);
        TextView name = (TextView) vi.findViewById(R.id.row_medicationName);
        name.setText(data.get(position).getTreatment_name());

        TextView qty = (TextView) vi.findViewById(R.id.dosageQuantity);
        qty.setText(data.get(position).getQuantity() +" "+data.get(position).getUnit());
        return vi;
    }

}
