package com.example.gatesnfc.existing;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gatesnfc.R;
import com.immunepicker.*;

public class Change_Log_ImmunizationListAdapter extends BaseAdapter {

	private Context context;
	List<Immunization> Immunizations;
	LayoutInflater inflater;

	boolean mStatus;
	/**
	 * Constructor
	 * 
	 * @param context
	 * @param Immunizations
	 */
	public Change_Log_ImmunizationListAdapter(Context context, List<Immunization> Immunizations) {
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
			cellView = inflater.inflate(R.layout.change_log_row, null);
			cell.immune_name = (TextView) cellView.findViewById(R.id.change_row_title);
			cell.immune_date = (TextView) cellView.findViewById(R.id.change_row_date);
			cell.immune_old_name = (TextView) cellView.findViewById(R.id.change_row_old_title);
			cell.immune_old_date = (TextView) cellView.findViewById(R.id.change_row_old_date);
			cellView.setTag(cell);
		} else {
			cell = (Cell) cellView.getTag();
		}
		
		if (mStatus)
		{
			cell.immune_name.setText(Immunization.getName());
			cell.immune_name.setBackgroundColor(Color.parseColor("#13ED1A"));
			cell.immune_date.setText("The Date is Here");
			cell.immune_date.setBackgroundColor(Color.parseColor("#13ED1A"));
			cell.immune_old_name.setText(Immunization.getName());
			cell.immune_old_date.setText("The Old Date is Here");
			//TODO: create a find by Date and place it in there
		}
		else
		{
			cell.immune_name.setText(Immunization.getName());
			cell.immune_date.setText("The Date is Here");
			cell.immune_old_name.setText(Immunization.getName());
			cell.immune_old_name.setBackgroundColor(Color.parseColor("#ED9213"));
			cell.immune_old_date.setText("The Old Date is Here");
			cell.immune_old_date.setBackgroundColor(Color.parseColor("#ED9213"));
		}


		
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

}