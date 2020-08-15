package club.plus1.covid.ui;

import android.widget.TextView;

import club.plus1.covid.App;
import club.plus1.covid.R;
import club.plus1.covid.data.Detail;

public class ListSorting {

    public static final String COUNTRY = "country";
    public static final String TOTAL_CONFIRMED = "totalConfirmed";
    public static final String TOTAL_DEATHS = "totalDeaths";
    public static final String TOTAL_RECOVERED = "totalRecovered";

    ListModel model;
    public int country;
    public int confirmed;
    public int deaths;
    public int recovered;
    public TextView textCountry;
    public TextView textConfirmed;
    public TextView textDeaths;
    public TextView textRecovered;

    public ListSorting(ListModel model, int country, int confirmed, int deaths, int recovered){
        this.model = model;
        this.country = country;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public void sorting(){
        if (country != 0)
            sortData(COUNTRY, country);
        if (confirmed != 0)
            sortData(TOTAL_CONFIRMED, confirmed);
        if (deaths != 0)
            sortData(TOTAL_DEATHS, deaths);
        if (recovered != 0)
            sortData(TOTAL_RECOVERED, recovered);
    }

    public void setIcons(){
        setIcon(textCountry, country);
        setIcon(textConfirmed, confirmed);
        setIcon(textDeaths, deaths);
        setIcon(textRecovered, recovered);
    }

    private void sortData(String sortColumn, int direction){
        new Thread(() -> {
            model.list.clear();
            model.copy.clear();
            switch (sortColumn) {
                case COUNTRY:
                    model.list = App.db.detailDao().sortCountries(direction);
                    break;
                case TOTAL_CONFIRMED:
                    model.list = App.db.detailDao().sortConfirmed(direction);
                    break;
                case TOTAL_DEATHS:
                    model.list = App.db.detailDao().sortDeaths(direction);
                    break;
                case TOTAL_RECOVERED:
                    model.list = App.db.detailDao().sortRecovered(direction);
                    break;
                default:
                    model.list = App.db.detailDao().readAll();
                    break;
            }
            model.copy.addAll(model.list);
            model.list.add(0, new Detail(model.all));
            model.adapter.filter();
            ListAdapter.handler.sendEmptyMessage(0);
        }).start();
    }

    void setIcon(TextView textView, int sortType){
        int sortRes;
        if (sortType > 0)
            if (sortType == 2)
                sortRes = R.drawable.sort_az;
            else
                sortRes = R.drawable.sort_asc;
        else if (sortType < 0)
            if (sortType == -2)
                sortRes = R.drawable.sort_za;
            else
                sortRes = R.drawable.sort_desc;
        else
            sortRes = R.drawable.empty;
        textView.setCompoundDrawablesWithIntrinsicBounds(sortRes, 0, 0, 0);
    }

    void changeSorting(String sortColumn){
        switch (sortColumn) {
            case COUNTRY:
                country = (country == 2 ? -2 : 2);
                confirmed = 0;
                deaths = 0;
                recovered = 0;
                break;
            case TOTAL_CONFIRMED:
                country = 0;
                confirmed = (confirmed == -1 ? 1 : -1);
                deaths = 0;
                recovered = 0;
                break;
            case TOTAL_DEATHS:
                country = 0;
                confirmed = 0;
                deaths = (deaths == -1 ? 1 : -1);
                recovered = 0;
                break;
            case TOTAL_RECOVERED:
                country = 0;
                confirmed = 0;
                deaths = 0;
                recovered = (recovered == -1 ? 1 : -1);
                break;
            default:
                break;
        }
        sorting();
        setIcons();
    }
}
