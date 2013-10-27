package com.example.gatesnfc.existing;

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
	List<Curr_Immunization> Immunizations;
	LayoutInflater inflater;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param Immunizations
	 */
	public ImmunizationListAdapter(Context context, List<Curr_Immunization> Immunizations) {
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