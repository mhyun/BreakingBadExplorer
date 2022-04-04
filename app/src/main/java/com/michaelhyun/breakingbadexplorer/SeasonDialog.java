package com.michaelhyun.breakingbadexplorer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.michaelhyun.breakingbadexplorer.adapters.CharactersAdapter;
import com.michaelhyun.breakingbadexplorer.model.Character;
import com.michaelhyun.breakingbadexplorer.viewmodels.BreakingBadViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SeasonDialog extends AppCompatDialogFragment {
    private CheckBox s1CheckBox;
    private CheckBox s2CheckBox;
    private CheckBox s3CheckBox;
    private CheckBox s4CheckBox;
    private CheckBox s5CheckBox;
    private SeasonDialogListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater .inflate(R.layout.season_dialog, null);

        s1CheckBox = view.findViewById(R.id.cb_season1);
        s2CheckBox = view.findViewById(R.id.cb_season2);
        s3CheckBox = view.findViewById(R.id.cb_season3);
        s4CheckBox = view.findViewById(R.id.cb_season4);
        s5CheckBox = view.findViewById(R.id.cb_season5);

        builder.setView(view)
                .setTitle("Season Filter")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BreakingBadViewModel breakingBadViewModel;
                        List<Character> characterList;
                        
                        s1CheckBox = view.findViewById(R.id.cb_season1);
                        s2CheckBox = view.findViewById(R.id.cb_season2);
                        s3CheckBox = view.findViewById(R.id.cb_season3);
                        s4CheckBox = view.findViewById(R.id.cb_season4);
                        s5CheckBox = view.findViewById(R.id.cb_season5);

                        List<Integer> seasonsList = new ArrayList<>();

                        if (s1CheckBox.isChecked()) {
                            seasonsList.add(1);
                        }
                        if (s2CheckBox.isChecked()) {
                            seasonsList.add(2);
                        }
                        if (s3CheckBox.isChecked()) {
                            seasonsList.add(3);
                        }
                        if (s4CheckBox.isChecked()) {
                            seasonsList.add(4);
                        }
                        if (s5CheckBox.isChecked()) {
                            seasonsList.add(5);
                        }

                        listener.applyFilter(seasonsList);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SeasonDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement SeasonDialogListener");
        }
    }

    public interface SeasonDialogListener {
        void applyFilter(List<Integer> seasonsList);
    }


}
