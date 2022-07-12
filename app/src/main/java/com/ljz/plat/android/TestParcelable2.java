package com.ljz.plat.android;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

class TestParcelable2 implements Parcelable {
   @NonNull
   private final String[] strings;

   protected TestParcelable2(Parcel in) {
      strings = in.createStringArray();
   }

   public static final Creator<TestParcelable2> CREATOR = new Creator<TestParcelable2>() {
      @Override
      public TestParcelable2 createFromParcel(Parcel in) {
         return new TestParcelable2(in);
      }

      @Override
      public TestParcelable2[] newArray(int size) {
         return new TestParcelable2[size];
      }
   };

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeStringArray(strings);
   }
}
