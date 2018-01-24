package sa.githubclient.utils;


import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

public abstract class BaseRVAdapter<D> extends RecyclerView.Adapter<BaseViewHolder<D>> {
  protected List<D> items = new ArrayList<>();
  private PublishSubject<View> publishSubject = PublishSubject.create();

  public BaseRVAdapter() {
  }

  public Observable<View> getViewClickedObservable() {
    return publishSubject.asObservable();
  }

  public D getItem(int position) {
    return items.get(position);
  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  protected View inflateView(ViewGroup viewGroup, @LayoutRes int layoutId) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    RxView.clicks(view).takeUntil(RxView.detaches(viewGroup)).map(aVoid -> view).subscribe(publishSubject);
    return view;
  }

  public List<D> getItems() {
    return items;
  }

  public void setItems(List<D> items) {
    this.items = items;
    notifyDataSetChanged();
  }
  public void removeItem(int position) {
    this.items.remove(position);
    notifyDataSetChanged();
  }
  public void swapData(@NonNull List<D> items) {
    this.items.clear();
    this.items.addAll(items);
    notifyDataSetChanged();
  }

  public void clearData() {
    items.clear();
    notifyDataSetChanged();
  }

  public void addItems(@NonNull List<D> items) {
    if (items.isEmpty()) {
      return;
    }
    int itemsCount = getItemCount();
    this.items.addAll(items);
    notifyItemRangeChanged(itemsCount, items.size() - 1);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }


}