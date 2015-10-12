package patienthub.binary.com.patienthub.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import patienthub.binary.com.patienthub.R;
import patienthub.binary.com.patienthub.data.Dosage;

/**
 * Created by Mark on 9/10/2015.
 */
public class MedicationListAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
    public SparseBooleanArray mCheckStates;
    Context context;
    List<Dosage> data;
    private static LayoutInflater inflater = null;
    public List<CheckBox> mCheckBoxes;
    public MedicationListAdapter(Context context, List<Dosage> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCheckStates = new SparseBooleanArray(data.size());
        mCheckBoxes = new ArrayList<CheckBox>();
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

        CheckBox chkbox = (CheckBox) vi.findViewById(R.id.checkbox_1);
        this.setChecked(position,false);
        chkbox.setTag(position);
        chkbox.setChecked(mCheckStates.get(position, false));
        chkbox.setOnCheckedChangeListener(this);
        mCheckBoxes.add(chkbox);
        return vi;
    }

    public boolean isChecked(int position) {
        return mCheckStates.get(position, false);
    }

    public void setChecked(int position, boolean isChecked) {
        mCheckStates.put(position, isChecked);

    }

    public void toggle(int position) {
        setChecked(position, !isChecked(position));

    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {

        mCheckStates.put((Integer) buttonView.getTag(), isChecked);

    }

    public void setCheckBoxState(boolean bool){
        for(CheckBox chkbox : mCheckBoxes){
            chkbox.setChecked(bool);
        }
    }

}
