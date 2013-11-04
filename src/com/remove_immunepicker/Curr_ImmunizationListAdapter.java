package com.remove_immunepicker;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gatesnfc.R;

public class Curr_ImmunizationListAdapter extends BaseAdapter {

	private Context context;
	List<Curr_Immunization> Immunizations;
	LayoutInflater inflater;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param Immunizations
	 */
	public Curr_ImmunizationListAdapter(Context context, List<Curr_Immunization> Immunizations) {
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
		Curr_Immunization Immunization = Immunizations.get(position);

		if (convertView == null) {
			cell = new Cell();
			cellView = inflater.inflate(R.layout.row, null);
			cell.textView = (TextView) cellView.findViewById(R.id.row_title);
			cell.imageView = (ImageView) cellView.findViewById(R.id.row_icon);
			cellView.setTag(cell);
		} else {
			cell = (Cell) cellView.getTag();
		}
		
		if(Immunization.getTF())
		{
			cellView.setBackgroundColor(Color.parseColor("#6dcde5"));
		}
		else
		{
			cellView.setBackgroundColor(Color.parseColor("#00000000"));
		}

		cell.textView.setText(Immunization.getName());
		
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