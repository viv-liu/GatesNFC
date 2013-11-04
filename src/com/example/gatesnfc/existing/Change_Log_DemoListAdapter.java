package com.example.gatesnfc.existing;

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
import com.immunepicker.*;

public class Change_Log_DemoListAdapter extends BaseAdapter {
	//TODO: Actually use this class or delete it
	private Context context;
	LayoutInflater inflater;
	private String item;
	private int type;
	private static final String DATEFORMAT = "MMM dd, yyyy";

	boolean mStatus;
	/**
	 * Constructor
	 * 
	 * @param context
	 * @param Immunizations
	 */
	public Change_Log_DemoListAdapter(Context context, String item, int type) {
		super();
		this.context = context;
		this.item = item;
		this.type = type;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * Return row for each Immunization
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View cellView = convertView;
		Cell cell;

		if (convertView == null) {
			cell = new Cell();
			cellView = inflater.inflate(R.layout.change_log_row, null);
			cell.immune_name = (TextView) cellView.findViewById(R.id.change_row_title);
			cell.immune_date = (TextView) cellView.findViewById(R.id.change_row_date);
			cell.immune_old_name = (TextView) cellView.findViewById(R.id.change_row_old_title);
			cell.immune_old_date = (TextView) cellView.findViewById(R.id.change_row_old_date);
			cellView.setTag(cell);
		} else {
			cell = (Cell) cellView.getTag();
		}
		
		switch (this.type) {
		case 0: //Name
			cell.immune_old_name.setText(ExistingActivity.p_reset.firstName + ExistingActivity.p_reset.lastName);
			break;
		case 1: //Date of Birth
			cell.immune_old_name.setText( DateFormat.format(DATEFORMAT, ExistingActivity.p_existing.birthday).toString());
			break;
		case 2: //MomName
			cell.immune_old_name.setText(ExistingActivity.p_reset.mom_firstName + ExistingActivity.p_reset.mom_lastName);
			break;
		case 3: //DadName
			cell.immune_old_name.setText(ExistingActivity.p_reset.dad_firstName + ExistingActivity.p_reset.dad_lastName);
			break;
		case 4: //Address
			cell.immune_old_name.setText(ExistingActivity.p_reset.getAddressString());
			break;
		case 5: //Notes
			cell.immune_old_name.setText(ExistingActivity.p_reset.getNotes());
			break;
		}
		
		cell.immune_name.setText(item);
		cell.immune_name.setBackgroundColor(Color.parseColor("#13ED1A"));
		return cellView;
	}

	/**
	 * Holder for the cell
	 * 
	 */
	static class Cell {
		public TextView immune_old_date;
		public TextView immune_old_name;
		public TextView immune_name;
		public TextView immune_date;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}