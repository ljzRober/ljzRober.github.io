package com.ljz.plat.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

class PermissionControl {

   public static final String GRANT_RESULTS = "grantResults";
   private static PermissionControl INSTANCE;
   @NonNull
   private final Context mContext;
   BlankFragment fragment = BlankFragment.newInstance(null, null);

   public static PermissionControl of() {
      if (INSTANCE == null) {
         throw new IllegalArgumentException("PermissionControl have not init");
      } else {
         return INSTANCE;
      }
   }

   public static PermissionControl of(@NonNull FragmentActivity fragmentActivity) {
      return new PermissionControl(fragmentActivity);
   }

   public static void register(@NonNull Context context) {
      if (INSTANCE == null) {
         INSTANCE = new PermissionControl(context);
      }
   }

   private PermissionControl(@NonNull Context context) {
      this.mContext = context;
   }

   public void requirePermission() {
      Intent intent = new Intent();
      intent.setClass(mContext, TransparentActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
      mContext.startActivity(intent);
   }

   public void requireFragmentPermission() {
      if (mContext instanceof FragmentActivity) {
         PermissionReceiver permissionReceiver = new PermissionReceiver();
         IntentFilter intentFilter = new IntentFilter(GRANT_RESULTS);
         LocalBroadcastManager.getInstance(mContext).registerReceiver(permissionReceiver, intentFilter);
         FragmentActivity fragmentActivity = (FragmentActivity) mContext;
         fragmentActivity.getSupportFragmentManager()
                 .beginTransaction()
                 .add(fragment, "FragmentActivity")
                 .commitNowAllowingStateLoss();
      }
   }

   private void destroyFragment() {
      if (mContext instanceof FragmentActivity) {
         FragmentActivity fragmentActivity = (FragmentActivity) mContext;
         fragmentActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commitNowAllowingStateLoss();
      }
   }

   private class PermissionReceiver extends BroadcastReceiver {

      @Override
      public void onReceive(Context context, Intent intent) {
         destroyFragment();
         Toast.makeText(context, Arrays.toString(Objects.requireNonNull(intent.getIntArrayExtra(GRANT_RESULTS))), Toast.LENGTH_SHORT).show();
      }
   }
}
