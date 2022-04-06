package com.example.classdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.classdemo.R;
import com.example.classdemo.Util.UtilValue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RelatedFragment extends Fragment {

    @BindView(R.id.relaInfo)
    TextView relaInfo;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_related, container, false);
        ButterKnife.bind(this, view);
        //            FileOutputStream outputStream = getActivity().openFileOutput(UtilValue.RELATEDTXTPATH, 0);
//            PrintStream printStream = new PrintStream(outputStream);
//            printStream.println("的是佛山第回复");
//            printStream.close();
        InputStream stream =  getActivity().getResources().openRawResource(R.raw.rela);
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNext()) {
            relaInfo.append(scanner.next() + "\n");
        }
        scanner.close();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this, view).unbind();
    }
}
