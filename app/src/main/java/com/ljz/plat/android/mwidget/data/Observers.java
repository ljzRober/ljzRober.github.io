package com.ljz.plat.android.mwidget.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import java.util.HashSet;
import java.util.Set;

public class Observers<T> {
   private final Set<Observer<T>> mSet = new HashSet<>();
   private T data;

   public void observe(@NonNull Observer<T> observer) {
      mSet.add(observer);
   }

   public void setValue(@NonNull T data) {
      this.data = data;
      mSet.forEach(observer -> observer.onChanged(data));
   }

   public T getValue() {
      return data;
   }
}
