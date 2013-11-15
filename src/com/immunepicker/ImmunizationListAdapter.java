package com.immunepicker;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gatesnfc.R;

public class ImmunizationListAdapter extends BaseAdapter {

	private Context context;
	List<Immunization> Immunizations;
	LayoutInflater inflater;
	private boolean godMode; // allows changing all the old records too!
	
	public static final String DATEFORMAT = "MMM dd, yyyy";

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
		return Immunizations.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	public void setGodMode(boolean b) {
		godMode = b;
	}

	/**
	 * Return row for each Immunization
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View cellView = convertView;
		Cell cell;
		Immunization immunization = Immunizations.get(position);

		if (convertView == null) {
			cell = new Cell();
			cellView = inflater.inflate(R.layout.immune_row, null);
			cell.immune_name = (TextView) cellView.findViewById(R.id.row_title);
			cell.immune_date = (TextView) cellView.findViewById(R.id.row_date);
			cellView.setTag(cell);
		} else {
			cell = (Cell) cellView.getTag();
		}

		cell.immune_name.setText(immunization.getName());
		cell.immune_date.setText(DateFormat.format(DATEFORMAT, immunization.getDate()).toString());
		
		/*// Greyed out or not?
		if(immunization.isGreyed()) {
			cellView.setBackgroundColor(Color.parseColor("#FFFFFF"));
		} else {
			cellView.setBackgroundColor(Color.parseColor("#FF22FF"));
		}*/
		return cellView;
	}
	
	@Override
	public boolean isEnabled(int position) {
		if(Immunizations.get(position).isGreyed() && !godMode) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * Holder for the cell
	 * 
	 */
	static class Cell {
		public TextView immune_name;
		public TextView immune_date;
	}

}