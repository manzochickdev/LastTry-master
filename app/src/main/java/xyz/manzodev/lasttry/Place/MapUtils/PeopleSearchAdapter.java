package xyz.manzodev.lasttry.Place.MapUtils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import xyz.manzodev.lasttry.DatabaseHandle;
import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.Utils.PersonSearch;
import xyz.manzodev.lasttry.databinding.MapLayoutPeopleSearchAutocompleteBinding;

public class PeopleSearchAdapter extends ArrayAdapter<Model> {
    private Context context;
    public PeopleSearchAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return peopleFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.map_layout_people_search_autocomplete,parent,false);
        }
        MapLayoutPeopleSearchAutocompleteBinding mapLayoutPeopleSearchAutocompleteBinding = DataBindingUtil.bind(convertView);
        Model model = getItem(position);
        mapLayoutPeopleSearchAutocompleteBinding.setModel(model);
        Address address = DatabaseHandle.getInstance(context).getAddress(model.id);
        if (address!=null) mapLayoutPeopleSearchAutocompleteBinding.setAddress(address);
        return mapLayoutPeopleSearchAutocompleteBinding.getRoot();
    }

    private Filter peopleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<Model> suggestions = new ArrayList<>();

            if (constraint==null || constraint.length()==0){
                suggestions.addAll(PersonSearch.getInstance(context).getModels());

            }
            else {
                String query = constraint.toString().toLowerCase().trim();
                ArrayList<Model> models = PersonSearch.getInstance(context).onSearchListener(query);
                suggestions.addAll(models);
            }

            filterResults.values = suggestions;
            filterResults.count = suggestions.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count>0){
                clear();
                addAll((List) results.values);
                notifyDataSetChanged();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Model)resultValue).getName();
        }
    };
}
