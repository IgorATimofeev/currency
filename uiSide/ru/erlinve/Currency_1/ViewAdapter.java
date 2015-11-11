package ru.erlinve.Currency_1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastian on 11/2/15.
 */
public class ViewAdapter extends BaseAdapter {

    private static final String TAG = ViewAdapter.class.getName();
    private static final int MAX_FRACTION_DIGITS = 5;

    private Context context;
    private List<ValuteDataParcel> valuteDataList = new ArrayList<ValuteDataParcel>();

    public ViewAdapter(Context c, List<ValuteDataParcel> list)
    {
        context = c;
        valuteDataList = list;
        Log.e(TAG, String.valueOf(valuteDataList.size()));
    }

    @Override
    public int getCount() {
        return valuteDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return valuteDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView;

        final ValuteDataParcel valuteData = valuteDataList.get(position);

        NumberFormat formatter = NumberFormat.getCurrencyInstance();


        formatter.setMaximumFractionDigits(MAX_FRACTION_DIGITS);

        Log.e(TAG, valuteData.getValue() + valuteData.getCharCode());

//        if (convertView == null) {
//
//            convertView = LayoutInflater.from(context).inflate(R.layout.itemlist, parent, false);
//            ((TextView) convertView.findViewById(R.id.textValuteName)).setText(valuteData.getName());
//            ((TextView) convertView.findViewById(R.id.textValuteValue)).setText(String.valueOf(valuteData.getValue()));
//            ((TextView) convertView.findViewById(R.id.textCharCode)).setText(valuteData.getCharCode());
//            if(valuteData.getNominal()>1) {
//                ((TextView) convertView.findViewById(R.id.textNominalValue)).setText("for " + valuteData.getNominal());
//            }
//
//            int id = context.getResources()
//                    .getIdentifier(valuteData.getCharCode().toLowerCase(),
//                            "drawable", this.context.getPackageName());
//            if(id==0) {
//                id = context.getResources()
//                        .getIdentifier("xxx",
//                                "drawable", this.context.getPackageName());
//            }
//        ((ImageView) convertView.findViewById(R.id.imageFlag))
//                            .setImageResource(id);
//
//
//        }
//          else {
//
//            convertView = LayoutInflater.from(context).inflate(R.layout.itemlist, parent, false);
//            ((TextView) convertView.findViewById(R.id.textValuteName)).setText(valuteData.getName());
//            ((TextView) convertView.findViewById(R.id.textValuteValue)).setText(formatter.format(valuteData.getValue()));
//            ((TextView) convertView.findViewById(R.id.textCharCode)).setText(valuteData.getCharCode());
//            if(valuteData.getNominal()>1) {
//                ((TextView) convertView.findViewById(R.id.textNominalValue)).setText("for " + valuteData.getNominal());
//            }
//
//            if(valuteData.getCharCode().equals("TRY")) {
//                valuteData.setCharCode("tryl");
//            }
//
//            int id = context.getResources()
//                    .getIdentifier(valuteData.getCharCode().toLowerCase(),
//                            "drawable", this.context.getPackageName());
//            if(id==0) {
//                id = context.getResources()
//                        .getIdentifier("xxx",
//                                "drawable", this.context.getPackageName());
//            }
//            ((ImageView) convertView.findViewById(R.id.imageFlag))
//                    .setImageResource(id);
//        }



        convertView = LayoutInflater.from(context).inflate(R.layout.itemlist, parent, false);
        ((TextView) convertView.findViewById(R.id.textValuteName)).setText(valuteData.getName());
        ((TextView) convertView.findViewById(R.id.textValuteValue)).setText(String.valueOf(valuteData.getValue()));
        ((TextView) convertView.findViewById(R.id.textCharCode)).setText(valuteData.getCharCode());
        if(valuteData.getNominal()>1) {
            ((TextView) convertView.findViewById(R.id.textNominalValue)).setText("for " + valuteData.getNominal());
        }
//TODO THIS BYCICLE!!!
        if(valuteData.getCharCode().equals("TRY")) {
            valuteData.setCharCode("tryl");
        }

        int id = context.getResources()
                .getIdentifier(valuteData.getCharCode().toLowerCase(),
                        "drawable", this.context.getPackageName());
        if(id==0) {
            id = context.getResources()
                    .getIdentifier("xxx",
                            "drawable", this.context.getPackageName());
        }
        ((ImageView) convertView.findViewById(R.id.imageFlag))
                .setImageResource(id);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, valuteData.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        return convertView;
    }
}
