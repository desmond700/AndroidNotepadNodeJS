package com.example.androidnotepadnodejs;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidnotepadnodejs.util.Datetime;
import com.example.androidnotepadnodejs.util.HttpRetrieveNoteDetailsAsync;
import com.example.androidnotepadnodejs.util.HttpRetrieveNotesAsync;
import com.example.androidnotepadnodejs.util.Note;

import java.util.ArrayList;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private String mTitle;
    private String mNote;
    private String mDate;
    private Activity activity;
    private Datetime mDatetime;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            activity = this.getActivity();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.item_detail, container, false);
        final CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);

        AsyncTask<String, Void, String> task = new HttpRetrieveNoteDetailsAsync(new HttpRetrieveNoteDetailsAsync.AsyncResponse() {
            @Override
            public void processFinish(Note output) {
                mTitle = output.title;
                mNote = output.note;
                mDate = output.date;
                mDatetime = new Datetime(mDate);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(mTitle);
                }

                // Show the dummy content as text in a TextView.
                if (mNote != null) {
                    ((TextView) rootView.findViewById(R.id.item_detail)).setText(mNote);
                    ((TextView) rootView.findViewById(R.id.item_detail_date)).setText(mDatetime.getDateTime());
                }
            }

        }, getContext(), getArguments().getString(ARG_ITEM_ID)).execute();

        return rootView;
    }

    public void readNote(String id){





    }
}
