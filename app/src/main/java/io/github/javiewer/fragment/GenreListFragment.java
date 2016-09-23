package io.github.javiewer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.javiewer.R;
import io.github.javiewer.adapter.GenreAdapter;
import io.github.javiewer.adapter.item.Genre;
import io.github.javiewer.network.AVMO;
import io.github.javiewer.view.ViewUtil;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class GenreListFragment extends Fragment {

    protected List<Genre> genres = new ArrayList<>();

    @Bind(R.id.genre_recycler_view)
    public RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private StaggeredGridLayoutManager mLayoutManager;

    public GenreListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mRecyclerView.setPadding(
                ViewUtil.dpToPx(1),
                ViewUtil.dpToPx(1),
                ViewUtil.dpToPx(1),
                ViewUtil.dpToPx(1)
        );

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(this.mAdapter = new GenreAdapter(genres, this.getActivity()));

        RecyclerView.ItemAnimator animator = new SlideInUpAnimator();
        animator.setAddDuration(300);
        mRecyclerView.setItemAnimator(animator);

        this.mAdapter.notifyItemRangeChanged(0, this.mAdapter.getItemCount());
    }

    public Call<ResponseBody> getCall(int page) {
        return AVMO.INSTANCE.getActresses(page);
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }
}
