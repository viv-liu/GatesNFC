package com.immunepicker;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gatesnfc.R;
import com.example.gatesnfc.R.drawable;

public class ImmunizationListAdapter extends BaseAdapter {

	private Context context;
	List<Immunization> Immunizations;
	LayoutInflater inflater;

	/**
	 * The drawable image name has the format "flag_$ImmunizationCode". We need to
	 * load the drawable dynamically from Immunization code. Code from
	 * http://stackoverflow.com/
	 * questions/3042961/how-can-i-get-the-resource-id-of
	 * -an-image-if-i-know-its-name
	 * 
	 * @param drawableName
	 * @return
	 */
	private int getResId(String drawableName) {

		try {
			//Trying to get Pictures. No need to get pictures atm
//			Class<drawable> res = R.drawable.class;
//			Field field = res.getField(drawableName);
//			int drawableId = field.getInt(null);
//			return drawableId;
		} catch (Exception e) {
			Log.e("ImmunizationPICKER", "Failure to get drawable id.", e);
		}
		return -1;
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param Immunizations
	 */
	public ImmunizationListAdapter(Context context, List<Immunization> Immunizations) {
		super();
		this.context = context;
		this.Immunizations = Immunizations;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Immunizations.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Return row for each Immunization
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View cellView = convertView;
		Cell cell;
		Immunization Immunization = Immunizations.get(position);

		if (convertView == null) {
			cell = new Cell();
			cellView = inflater.inflate(R.layout.row, null);
			cell.textView = (TextView) cellView.findViewById(R.id.row_title);
			cell.imageView = (ImageView) cellView.findViewById(R.id.row_icon);
			cellView.setTag(cell);
		} else {
			cell = (Cell) cellView.getTag();
		}

		cell.textView.setText(Immunization.getName());

		// Load drawable dynamically from Immunization code
//		String drawableName = "flag_"
//				+ Immunization.getCode().toLowerCase(Locale.ENGLISH);
//		cell.imageView.setImageResource(getResId(drawableName));
		return cellView;
	}

	/**
	 * Holder for the cell
	 * 
	 */
	static class Cell {
		public TextView textView;
		public ImageView imageView;
	}

}