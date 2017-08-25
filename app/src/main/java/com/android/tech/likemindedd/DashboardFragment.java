package com.android.tech.likemindedd;

/**
 * Created by nitheeshkpai on 7/31/17.
 * Class that handles all DB stuff used in Save Article feature
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    public static Fragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    ProjectsAdapter adapter;
    List <ProjectItemInfo> projectItemsInfoList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        projectItemsInfoList.add(new ProjectItemInfo("The Walking Eddie","https://www.apilikemindedd.xyz//images/projects/pinned_image_display/15f5iSspdo_1708_1503231968.jpg", "thewalkingeddie"));
        projectItemsInfoList.add(new ProjectItemInfo("The Nightmare From Beyond","https://www.apilikemindedd.xyz//images/projects/pinned_image_display/lMM70wMDzW_722_1502950074.png", "facethenightmare"));
        projectItemsInfoList.add(new ProjectItemInfo("Voxelaxy","https://www.apilikemindedd.xyz//images/projects/pinned_image_display/4x6XlUv3eT_1641_1501879985.png", "voxelaxy"));
        projectItemsInfoList.add(new ProjectItemInfo("Suicide Guy","https://www.apilikemindedd.xyz//images/projects/pinned_image_display/aB90aZanWO_1623_1501503949.png", "SuicideGuy"));
        projectItemsInfoList.add(new ProjectItemInfo("GeoQuest","https://www.apilikemindedd.xyz//images/projects/pinned_image_display/6RAvjxHAaS_1662_1502176925.png","GeoQuest"));

        adapter = new ProjectsAdapter(getActivity(), projectItemsInfoList);
        recyclerView.setAdapter(adapter);
    }
}