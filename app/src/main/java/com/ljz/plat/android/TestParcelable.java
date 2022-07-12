package com.ljz.plat.android;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

class TestParcelable implements Parcelable {
   @NonNull
   private final String[] strings;
   @NonNull
   private final String string;
   @NonNull
   private final TestParcelable2 testParcelable2;

   protected TestParcelable(Parcel in) {
      strings = in.createStringArray();
      string = in.readString();
      testParcelable2 = in.readParcelable(TestParcelable2.class.getClassLoader());
   }

   public static final Creator<TestParcelable> CREATOR = new Creator<TestParcelable>() {
      @Override
      public TestParcelable createFromParcel(Parcel in) {
         return new TestParcelable(in);
      }

      @Override
      public TestParcelable[] newArray(int size) {
         return new TestParcelable[size];
      }
   };

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeStringArray(strings);
      dest.writeString(string);
      dest.writeParcelable(testParcelable2, flags);
   }
}
