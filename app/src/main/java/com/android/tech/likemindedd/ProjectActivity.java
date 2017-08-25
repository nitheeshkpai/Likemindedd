package com.android.tech.likemindedd;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;


public class ProjectActivity extends Activity{

    private Gson gson;
    ProjectInfo projectInfo;
    private List<FileInfo> fileInfoList = new ArrayList<>();

    @Bind(R.id.cover_image)
    ImageView coverImage;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.team_title)
    TextView teamTitle;

    @Bind(R.id.usp)
    TextView usp;

    @Bind(R.id.description)
    TextView description;

    @Bind(R.id.release_date)
    TextView releaseDate;

    @Bind(R.id.engine)
    TextView engine;

    @Bind(R.id.development_stage)
    TextView developmentStage;

    @Bind(R.id.progress)
    View progressView;

    @Bind(R.id.progress_parent)
    View progressViewParent;

    @Bind(R.id.project_view)
    View projectView;

    ViewPager viewPager;

    PagerAdapter adapter;
    CircleIndicator indicator;

    String projectName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);

        projectName = getIntent().getStringExtra("projectName");

        viewPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        showProgress(true);
        makeNetworkRequest();

    }

    private void makeNetworkRequest() {

        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        gson = gsonBuilder.create();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.apilikemindedd.xyz/projects/"+projectName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject projectJSON = null;
                        try {
                            JSONObject JSONObj = new JSONObject(response);

                            projectJSON = JSONObj.getJSONObject("data").getJSONObject("details");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Type type = new TypeToken<ProjectInfo>() {
                        }.getType();
                        try {
                            projectInfo = gson.fromJson(projectJSON.toString(), type);
                        } catch (JsonSyntaxException e) {
                            Toast.makeText(ProjectActivity.this, "Bad data from Server!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        updateUI();
                        getImages();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProjectActivity.this);
                alertDialogBuilder.setMessage(ProjectActivity.this.getResources().getString(R.string.dialog_message_no_network))
                        .setTitle(ProjectActivity.this.getResources().getString(R.string.no_network));
                alertDialogBuilder.setPositiveButton("OK", null);
                AlertDialog dialog = alertDialogBuilder.create();
                if(dialog != null)
                    dialog.show();
                finish();
            }
        });
        queue.add(stringRequest);
    }

    private void getImages() {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.apilikemindedd.xyz/projects/"+projectInfo.getId()+"/files",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray filesJSONArray = null;
                        try {
                            JSONObject JSONObj = new JSONObject(response);

                            filesJSONArray = JSONObj.getJSONObject("data").getJSONArray("files");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Type type = new TypeToken<List<FileInfo>>() {
                        }.getType();
                        try {
                            fileInfoList = gson.fromJson(filesJSONArray.toString(), type);
                        } catch (JsonSyntaxException e) {
                            Toast.makeText(ProjectActivity.this, "Bad data from Server!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        updateGalleryView();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProjectActivity.this);
                alertDialogBuilder.setMessage(ProjectActivity.this.getResources().getString(R.string.dialog_message_no_network))
                        .setTitle(ProjectActivity.this.getResources().getString(R.string.no_network));
                alertDialogBuilder.setPositiveButton("OK", null);
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
                finish();
            }
        });
        queue.add(stringRequest);
    }

    private void updateGalleryView() {
        int videoIndex = -1;
        for(int i = 0; i<fileInfoList.size(); i++) {
            if(fileInfoList.get(i).getType().equals("video"))
                videoIndex = i;
        }

        FileInfo videoInfo = fileInfoList.get(videoIndex);
        fileInfoList.remove(videoIndex);
        fileInfoList.add(videoInfo);

        adapter = new ViewPagerAdapter(ProjectActivity.this, fileInfoList);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

    }

    private void updateUI() {
        showProgress(false);
        title.setText(projectInfo.getTitle());

        if(projectInfo.getTeamTitle() != null)
            teamTitle.setText(projectInfo.getTeamTitle());

        if(projectInfo.getUsp() != null)
            usp.setText(projectInfo.getUsp());

        Glide.with(this).load(projectInfo.getCoverPath()).into(coverImage);

        if(projectInfo.getDescription() != null)
            description.setText(projectInfo.getDescription());

        if(projectInfo.getReleaseDate() != null)
            releaseDate.setText(projectInfo.getReleaseDate());

        if(projectInfo.getEngine() != null)
            engine.setText(projectInfo.getEngine());

        if(projectInfo.getDevelopmentStage() != null)
         developmentStage.setText(projectInfo.getDevelopmentStage());
    }

    @OnClick(R.id.demolink)
    public void onClickDemolink() {
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(projectInfo.getDemoLink()));
        startActivity(browserIntent);
    }

    @OnClick(R.id.presskit)
    public void onClickPresskit() {
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(projectInfo.getPresskitLink()));
        startActivity(browserIntent);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            projectView.setVisibility(show ? View.GONE : View.VISIBLE);
            projectView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    projectView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressViewParent.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressViewParent.setVisibility(show ? View.VISIBLE : View.GONE);
            projectView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
